/*
* Kendo UI v2011.3.1129 (http://kendoui.com)
* Copyright 2011 Telerik AD. All rights reserved.
*
* Kendo UI commercial licenses may be obtained at http://kendoui.com/license.
* If you do not own a commercial license, this file shall be governed by the
* GNU General Public License (GPL) version 3. For GPL requirements, please
* review: http://www.gnu.org/copyleft/gpl.html
*/

(function ($, undefined) {
    /**
     * @name kendo.ui.Slider.Description
     * @section
     *  <p>
     *      The Slider widget provides a rich input for selecting values or ranges of values.
     *      Unlike the plain HTML5 range input, the Slider presents a consistent experience across
     *      all browsers and has a rich API and event model.
     *  </p>
     *  <h3>Getting Started</h3>
     *  There are two basic types of Sliders:
     *  <ol>
     *      <li><strong>Slider</strong>, which presents one thumb and two opposing buttons for selecting a single value</li>
     *      <li><strong>RangeSlider</strong>, which present two thumbs for defining a range of values</li>
     *  </ol>
     *  <h4>Slider</h4>
     * @exampleTitle Create simple HTML input element
     * @example
     *  <input id="slider" />
     * @exampleTitle Initialize the Slider using a jQuery selector
     * @example
     *  $("#slider").kendoSlider();
     *
     * @section
     *  <h4>RangeSlider</h4>
     * @exampleTitle Create two simple HTML input elements in a div
     * @example
     *  <div id="rangeSlider">
     *      <input />
     *      <input />
     *  </div>
     *
     * @exampleTitle Initialize the RangeSlider using a jQuery selector targeting the div
     * @example
     *  $("#rangeSlider").kendoRangeSlider();
     *
     * @section
     *  <p>
     *      The RangeSlider requires two inputs to capture both ends of the value range. This
     *      benefits scenarios where JavaScript is disabled, in which case users will be presented
     *      with two inputs, still allowing them to input a valid range.
     *  </p>
     *
     *  <h3>Customizing Slider Behavior</h3>
     *  Many facets of the Slider and RangeSlider behavior can be configured via simple properties, including:
     *  <ul>
     *      <li>Min/Max values</li>
     *      <li>Orientation (horizontal or vertical)</li>
     *      <li>Small/Large step</li>
     *      <li>Tooltip format/placement</li>
     *  </ul>
     *  <p>
     *      To see a full list of available properties and values, review the Slider Configuration API documentation tab.
     *  </p>
     * @exampleTitle Customizing Slider default settings
     * @example
     *  $("#slider").kendoSlider({
     *      min:10,
     *      max:50,
     *      orientation: "vertical",
     *      smallStep: 1,
     *      largeStep: 10
     *  });
     *
     */
    var kendo = window.kendo,
        Widget = kendo.ui.Widget,
        Draggable = kendo.ui.Draggable,
        keys = kendo.keys,
        extend = $.extend,
        parse = kendo.parseFloat,
        proxy = $.proxy,
        math = Math,
        touch = kendo.support.touch,
        CHANGE = "change",
        SLIDE = "slide",
        MOUSE_DOWN = touch ? "touchstart" : "mousedown",
        MOUSE_UP = touch ? "touchend" : "mouseup",
        MOVE_SELECTION = "moveSelection",
        KEY_DOWN = "keydown",
        MOUSE_OVER = "mouseover",
        DRAG_HANDLE = ".k-draghandle",
        TRACK_SELECTOR = ".k-slider-track",
        TICK_SELECTOR = ".k-tick",
        STATE_SELECTED = "k-state-selected",
        STATE_DEFAULT = "k-state-default",
        STATE_DISABLED = "k-state-disabled",
        PRECISION = 3,
        DISABLED = "disabled";

    var SliderBase = Widget.extend({
        init: function(element, options) {
            var that = this;

            Widget.fn.init.call(that, element, options);

            options = that.options;

            that._distance = options.max - options.min;
            that._isHorizontal = options.orientation == "horizontal";
            that._position = that._isHorizontal ? "left" : "bottom";
            that._size = that._isHorizontal ? "width" : "height";
            that._outerSize = that._isHorizontal ? "outerWidth" : "outerHeight";

            options.tooltip.format = options.tooltip.enabled ? options.tooltip.format || "{0}" : "{0}";

            that._createHtml();
            that.wrapper = that.element.closest(".k-slider");
            that._trackDiv = that.wrapper.find(TRACK_SELECTOR);

            that._setTrackDivWidth();

            that._maxSelection = that._trackDiv[that._size]();

            var sizeBetweenTicks = that._maxSelection / ((options.max - options.min) / options.smallStep);
            var pixelWidths = that._calculateItemsWidth(math.floor(that._distance / options.smallStep));

            if (options.tickPlacement != "none" && sizeBetweenTicks >= 2) {
                that._trackDiv.before(createSliderItems(options, that._distance));
                that._setItemsWidth(pixelWidths);
                that._setItemsTitle();
                that._setItemsLargeTick();
            }

            that._calculateSteps(pixelWidths);

            that[options.enabled ? "enable" : "disable"]();

            that._keyMap = {
                37: step(-options.smallStep), // left arrow
                40: step(-options.smallStep), // down arrow
                39: step(+options.smallStep), // right arrow
                38: step(+options.smallStep), // up arrow
                35: setValue(options.max), // end
                36: setValue(options.min), // home
                33: step(+options.largeStep), // page up
                34: step(-options.largeStep)  // page down
            };

            that.bind([
                /**
                 * Fires when the slider value changes as a result of selecting a new value with the drag handle, buttons or keyboard.
                 * @name kendo.ui.Slider#change
                 * @event
                 * @param {Event} e
                 * @param {Number} e.value Represents the updated value of the slider.
                 */

                /**
                 * Fires when the rangeSlider value changes as a result of selecting a new value with one of the drag handles or the keyboard.
                 * @name kendo.ui.RangeSlider#change
                 * @event
                 * @param {Event} e
                 * @param {Number} e.values Represents the updated array of values of the first and second drag handle.
                 */
                CHANGE,

                /**
                 * Fires when the user drags the drag handle to a new position.
                 * @name kendo.ui.Slider#slide
                 * @event
                 * @param {Event} e
                 * @param {Number} e.value Represents the value from the current position of the drag handle.
                 */

                /**
                 * Fires when the user drags the drag handle to a new position.
                 * @name kendo.ui.RangeSlider#slide
                 * @event
                 * @param {Event} e
                 * @param {Number} e.values Represents an array of values of the current positions of the first and second drag handle.
                 */
                SLIDE], options);
        },

        options: {
            enabled: true,
            min: 0,
            max: 10,
            smallStep: 1,
            largeStep: 5,
            orientation: "horizontal",
            tickPlacement: "both",
            tooltip: { enabled: true, format: "{0}" }
        },

        _setTrackDivWidth: function() {
            var that = this,
                trackDivPosition = parseFloat(that._trackDiv.css(that._position), 10) * 2;

            that._trackDiv[that._size]((that.wrapper[that._size]() - 2) - trackDivPosition);
        },

        _setItemsWidth: function(pixelWidths) {
            var that = this,
                options = that.options,
                first = 0,
                last = pixelWidths.length - 1,
                items = that.wrapper.find(TICK_SELECTOR),
                i,
                paddingTop = 0,
                bordersWidth = 2,
                selection = 0;

            for (i = 0; i < items.length - 2; i++) {
                $(items[i + 1])[that._size](pixelWidths[i]);
            }

            if (that._isHorizontal) {
                $(items[first]).addClass("k-first")[that._size](pixelWidths[last - 1]);
                $(items[last]).addClass("k-last")[that._size](pixelWidths[last]);
            } else {
                $(items[last]).addClass("k-first")[that._size](pixelWidths[last]);
                $(items[first]).addClass("k-last")[that._size](pixelWidths[last - 1]);
            }

            if (that._distance % options.smallStep != 0 && !that._isHorizontal) {
                for (i = 0; i < pixelWidths.length; i++) {
                    selection += pixelWidths[i];
                }

                paddingTop = that._maxSelection - selection;
                paddingTop += parseFloat(that._trackDiv.css(that._position), 10) + bordersWidth;

                that.wrapper.find(".k-slider-items").css("padding-top", paddingTop);
            }
        },

        _setItemsTitle: function() {
            var that = this,
                options = that.options,
                items = that.wrapper.find(TICK_SELECTOR),
                titleNumber = options.min,
                i = that._isHorizontal ? 0 : items.length - 1,
                limit = that._isHorizontal ? items.length : -1,
                increment = that._isHorizontal ? 1 : -1;

            for (; i - limit != 0 ; i += increment) {
                $(items[i]).attr("title", kendo.format(options.tooltip.format, round(titleNumber)));
                titleNumber += options.smallStep;
            }
        },

        _setItemsLargeTick: function() {
            var that = this,
                options = that.options,
                i,
                items = that.wrapper.find(TICK_SELECTOR),
                item = {},
                step = round(options.largeStep / options.smallStep);

            if ((1000 * options.largeStep) % (1000 * options.smallStep) == 0) {
                if (that._isHorizontal) {
                    for (i = 0; i < items.length; i = round(i + step)) {
                        item = $(items[i]);

                        item.addClass("k-tick-large")
                            .html("<span class='k-label'>" + item.attr("title") + "</span>");
                    }
                } else {
                    for (i = items.length - 1; i >= 0; i = round(i - step)) {
                        item = $(items[i]);

                        item.addClass("k-tick-large")
                            .html("<span class='k-label'>" + item.attr("title") + "</span>");

                        if (i != 0 && i != items.length - 1) {
                            item.css("line-height", item[that._size]() + "px");
                        }
                    }
                }
            }
        },

        _calculateItemsWidth: function(itemsCount) {
            var that = this,
                options = that.options,
                trackDivSize = parseFloat(that._trackDiv.css(that._size)) + 1,
                pixelStep = trackDivSize / that._distance,
                itemWidth,
                pixelWidths,
                i;

            if ((that._distance / options.smallStep) - math.floor(that._distance / options.smallStep) > 0) {
                trackDivSize -= ((that._distance % options.smallStep) * pixelStep);
            }

            itemWidth = trackDivSize / itemsCount;
            pixelWidths = [];

            for (i = 0; i < itemsCount - 1; i++) {
                pixelWidths[i] = itemWidth;
            }

            pixelWidths[itemsCount - 1] = pixelWidths[itemsCount] = itemWidth / 2;
            return that._roundWidths(pixelWidths);
        },

        _roundWidths: function(pixelWidthsArray) {
            var balance = 0;

            for (i = 0; i < pixelWidthsArray.length; i++) {
                balance += (pixelWidthsArray[i] - math.floor(pixelWidthsArray[i]));
                pixelWidthsArray[i] = math.floor(pixelWidthsArray[i]);
            }

            balance = math.round(balance);

            return this._addAdditionalSize(balance, pixelWidthsArray);
        },

        _addAdditionalSize: function(additionalSize, pixelWidthsArray) {
            if (additionalSize == 0) {
                return pixelWidthsArray;
            }

            //set step size
            var step = parseFloat(pixelWidthsArray.length - 1) / parseFloat(additionalSize == 1 ? additionalSize : additionalSize - 1),
                i;

            for (i = 0; i < additionalSize; i++) {
                pixelWidthsArray[parseInt(math.round(step * i))] += 1;
            }

            return pixelWidthsArray;
        },

        _calculateSteps: function(pixelWidths) {
            var that = this,
                options = that.options,
                val = options.min,
                selection = 0,
                itemsCount = pixelWidths.length,
                i = 1,
                lastItem;

            pixelWidths.splice(0, 0, pixelWidths[itemsCount - 2] * 2);
            pixelWidths.splice(itemsCount -1, 1, pixelWidths.pop() * 2);

            that._pixelSteps = [selection];
            that._values = [val];

            if (itemsCount == 0) {
                return;
            }

            while (i < itemsCount) {
                selection += (pixelWidths[i - 1] + pixelWidths[i]) / 2;
                that._pixelSteps[i] = selection;
                that._values[i] = val += options.smallStep;

                i++;
            }

            lastItem = options.max % options.smallStep == 0 ? itemsCount - 1 : itemsCount;

            that._pixelSteps[lastItem] = that._maxSelection;
            that._values[lastItem] = options.max;
        },

        _getValueFromPosition: function(mousePosition, dragableArea) {
            var that = this,
                options = that.options,
                step = math.max(options.smallStep * (that._maxSelection / that._distance), 0),
                position = 0,
                halfStep = (step / 2),
                val = 0,
                i;

            if (that._isHorizontal) {
                position = mousePosition - dragableArea.startPoint;
            } else {
                position = dragableArea.startPoint - mousePosition;
            }

            if (that._maxSelection - ((parseInt(that._maxSelection % step) - 3) / 2) < position) {
                return options.max;
            }

            for (i = 0; i < that._pixelSteps.length; i++) {
                if (math.abs(that._pixelSteps[i] - position) - 1 <= halfStep) {
                    return round(that._values[i]);
                }
            }
        },

        _getDragableArea: function() {
            var that = this,
                offsetLeft = that._trackDiv.offset().left,
                offsetTop = that._trackDiv.offset().top;

            return {
                startPoint: that._isHorizontal ? offsetLeft : offsetTop + that._maxSelection,
                endPoint: that._isHorizontal ? offsetLeft + that._maxSelection : offsetTop
            };
        },

        _createHtml: function() {
            var that = this,
                element = that.element,
                options = that.options,
                inputs = element.find("input");

            if (inputs.length == 2) {
                inputs.eq(0).val(options.selectionStart);
                inputs.eq(1).val(options.selectionEnd);
            } else {
                element.val(options.value);
            }

            element.wrap(createWrapper(options, element, that._isHorizontal)).hide();

            if (options.showButtons) {
                element.before(createButton(options, "increase", that._isHorizontal))
                       .before(createButton(options, "decrease", that._isHorizontal));
            }

            element.before(createTrack(element));
        }
    });

    function createWrapper (options, element, isHorizontal) {
        var orientationCssClass = isHorizontal ? " k-slider-horizontal" : " k-slider-vertical",
            style = options.style ? options.style : element.attr("style"),
            cssClasses = element.attr("class") ? (" " + element.attr("class")) : "",
            tickPlacementCssClass = "";

        if (options.tickPlacement == "bottomRight") {
            tickPlacementCssClass = " k-slider-bottomright";
        } else if (options.tickPlacement == "topLeft") {
            tickPlacementCssClass = " k-slider-topleft";
        }

        style = style ? " style='" + style + "'" : "";

        return "<div class='k-widget k-slider" + orientationCssClass + cssClasses + "'" + style + ">" +
               "<div class='k-slider-wrap" + (options.showButtons ? " k-slider-buttons" : "") + tickPlacementCssClass +
               "'></div></div>";
    }

    function createButton (options, type, isHorizontal) {
        var buttonCssClass = "";

        if (type == "increase") {
            buttonCssClass = isHorizontal ? "k-arrow-next" : "k-arrow-up";
        } else {
            buttonCssClass = isHorizontal ? "k-arrow-prev" : "k-arrow-down";
        }

        return "<a class='k-button k-button-" + type + "'><span class='k-icon " + buttonCssClass +
               "' title='" + options[type + "ButtonTitle"] + "'>" + options[type + "ButtonTitle"] + "</span></a>";
    }

    function createSliderItems (options, distance) {
        var result = "<ul class='k-reset k-slider-items'>",
            count = math.floor(round(distance / options.smallStep)) + 1;

        for(i = 0; i < count; i++) {
            result += "<li class='k-tick'>&nbsp;</li>";
        }

        result += "</ul>";

        return result;
    }

    function createTrack (element) {
        var dragHandleCount = element.is("input") ? 1 : 2;

        return "<div class='k-slider-track'><div class='k-slider-selection'><!-- --></div>" +
               "<a href='javascript:void(0)' class='k-draghandle' title='Drag'>Drag</a>" +
               (dragHandleCount > 1 ? "<a href='javascript:void(0)' class='k-draghandle' title='Drag'>Drag</a>" : "") +
               "</div>";
    }

    function step(step) {
        return function (value) {
            return value + step;
        }
    }

    function setValue(value) {
        return function () {
            return value;
        }
    }

    function formatValue(value) {
        return (value + "").replace(".", kendo.cultures.current.numberFormat["."]);
    }

    function round(value) {
        value = parseFloat(value, 10);
        var power = math.pow(10, PRECISION || 0);
        return math.round(value * power) / power;
    }

    function parseAttr(element, name) {
        return parse(element.getAttribute(name)) || undefined;
    }

    var Slider = SliderBase.extend(/** @lends kendo.ui.Slider.prototype */{
        /**
         * @constructs
         * @extends kendo.ui.Widget
         * @param {DomElement} element DOM element
         * @param {Object} options Configuration options.
         * @option {Boolean} [enabled] <true> Can be used to enable/disable the slider.
         * @option {Number} [min] <0> The minimum value of the slider.
         * @option {Number} [max] <10> The maximum value of the slider.
         * @option {Boolean} [showButtons] <true> Can be used to show or hide the slider increase and decrease buttons. The buttons are used to increase or decrease the value. They are not available in the RangeSlider.
         * @option {Object} [tooltip] Confituration of the slider tooltip.
         * @option {Boolean} [tooltip.enabled] <true> Can be used to enable/disable the tooltip.
         * @option {String} [tooltip.format] <"{0}"> Can be used to formatting of the text of the tooltip. Note that the applied format will also influence the appearance of the slider tick labels.
         * @option {Number} [value] <0> The value of the slider.
         * @option {String} [orientation] <"horizontal"> The orientation of the slider. Available options are "horizontal" and "vertical".
         * @option {String} [tickPlacement] <"both"> the location of the tick marks in the widget. Available options are:
         *     <dl>
         *         <dt>
         *              "topLeft"
         *         </dt>
         *         <dd>
         *              Tick marks are located on the top of the horizontal widget or on the left of the vertical widget.
         *         </dd>
         *         <dt>
         *              "bottomRight"
         *         </dt>
         *         <dd>
         *              Tick marks are located on the bottom of the horizontal widget or on the right side of the vertical widget.
         *         </dd>
         *         <dt>
         *              "both"
         *         </dt>
         *         <dd>
         *              Tick marks are located on both sides of the widget.
         *         </dd>
         *     </dl>
         * @option {Number} [smallStep] <1> The small step of the slider. The Value will be changed with SmallStep when the end user:
         *     <ul>
         *         <li>
         *             clicks on the Slider buttons
         *         </li>
         *         <li>
         *             presses the arrow keys (the drag handle must be focused)
         *         </li>
         *         <li>
         *             drag the drag handle
         *         </li>
         *     </ul>
         * @option {Number} [largeStep] <5> The delta with which the value will change when the user presses the Page Up or Page Down key (the drag handle must be focused). Note that the allied largeStep will also set large tick for every large step.
         * @option {String} [increaseButtonTitle] <"Increase"> The title of the increase button of the slider.
         * @option {String} [decreaseButtonTitle] <"Decrease"> The title of the decrease button of the slider.
         */
        init: function(element, options) {
            var that = this,
                dragHandle;

            element.type = "text";

            options = extend({}, {
                value: parseAttr(element, "value"),
                min: parseAttr(element, "min"),
                max: parseAttr(element, "max"),
                smallStep: parseAttr(element, "step")
            }, options);

            SliderBase.fn.init.call(that, element, options);
            options = that.options;

            that._setValueInRange(options.value);
            dragHandle = that.wrapper.find(DRAG_HANDLE);

            new Slider.Selection(dragHandle, that, options);
            that._drag = new Slider.Drag(dragHandle, "", that, options);
        },

        options: {
            name: "Slider",
            value: 0,
            showButtons: true,
            increaseButtonTitle: "Increase",
            decreaseButtonTitle: "Decrease"
        },

        /**
         * Enables the slider.
         * @example
         * var slider = $("#slider").data("kendoSlider");
         *
         * // enables the slider
         * slider.enable();
         */
        enable: function () {
            var that = this,
                options = that.options,
                clickHandler,
                move;

            that.wrapper
                .removeAttr(DISABLED)
                .removeClass(STATE_DISABLED)
                .addClass(STATE_DEFAULT);

            clickHandler = function (e) {
                if ($(e.target).hasClass("k-draghandle")) {
                    $(e.target).addClass(STATE_SELECTED);
                    return;
                }

                var location = kendo.touchLocation(e),
                    mousePosition = that._isHorizontal ? location.x : location.y,
                    dragableArea = that._getDragableArea();

                that._update(that._getValueFromPosition(mousePosition, dragableArea));

                that._drag.dragstart(e);
            };

            that.wrapper
                .find(TICK_SELECTOR).bind(MOUSE_DOWN, clickHandler)
                .end()
                .find(TRACK_SELECTOR).bind(MOUSE_DOWN, clickHandler);

            that.wrapper.find(DRAG_HANDLE).bind(MOUSE_UP, function (e) {
                $(e.target).removeClass(STATE_SELECTED);
            });

            move = proxy(function (e, sign) {
                var index = math.ceil(options.value / options.smallStep) - math.abs(options.min);

                if (index >= that._values.length - 1 || index <= 0) {
                    that._setValueInRange(options.value + (sign * options.smallStep));
                } else {
                    that._setValueInRange(that._values[index + (sign * 1)]);
                }
            }, that);

            if (options.showButtons) {
                var mouseDownHandler = proxy(function(e, sign) {
                    if (e.which == 1 || (touch && e.which == 0)) {
                        move(e, sign);

                        this.timeout = setTimeout(proxy(function () {
                            this.timer = setInterval(function () {
                                move(e, sign)
                            }, 60);
                        }, this), 200);
                    }
                }, that);

                that.wrapper.find(".k-button")
                    .bind(MOUSE_UP, proxy(function (e) {
                        this._clearTimer();
                    }, that))
                    .bind(MOUSE_OVER, function (e) {
                        $(e.currentTarget).addClass("k-state-hover");
                    })
                    .bind("mouseout", proxy(function (e) {
                        $(e.currentTarget).removeClass("k-state-hover");
                        this._clearTimer();
                    }, that))
                    .eq(0)
                    .bind(MOUSE_DOWN, proxy(function (e) {
                        mouseDownHandler(e, 1);
                    }, that))
                    .click(false)
                    .end()
                    .eq(1)
                    .bind(MOUSE_DOWN, proxy(function (e) {
                        mouseDownHandler(e, -1);
                    }, that))
                    .click(false);
            }

            that.wrapper
                .find(DRAG_HANDLE).bind(KEY_DOWN, proxy(this._keydown, that));

            options.enabled = true;
        },

        /**
         * Disables the slider.
         * @example
         * var slider = $("#slider").data("kendoSlider");
         *
         * // disables the slider
         * slider.disable();
         */
        disable: function () {
            var that = this;

            that.wrapper
                .attr(DISABLED, DISABLED)
                .removeClass(STATE_DEFAULT)
                .addClass(STATE_DISABLED);

            that.wrapper
                .find(".k-button")
                .unbind(MOUSE_DOWN)
                .bind(MOUSE_DOWN, false)
                .unbind(MOUSE_UP)
                .bind(MOUSE_UP, false)
                .unbind("mouseleave")
                .bind("mouseleave", false)
                .unbind(MOUSE_OVER)
                .bind(MOUSE_OVER, false);

            that.wrapper
                .find(TICK_SELECTOR).unbind(MOUSE_DOWN)
                .end()
                .find(TRACK_SELECTOR).unbind(MOUSE_DOWN);

            that.wrapper
                .find(DRAG_HANDLE)
                .unbind(MOUSE_UP)
                .unbind(KEY_DOWN)
                .bind(KEY_DOWN, false);

            that.options.enabled = false;
        },

        _update: function (val) {
            var that = this,
                change = that.value() != val;

            that.value(val);

            if (change) {
                that.trigger(CHANGE, { value: that.options.value });
            }
        },

        /**
         * The value method gets or sets the value of the slider.
         * The value method accepts a {String} or a {Number} as parameters, and returns a {Nubmer}.
         * @example
         * var slider = $("#slider").data("kendoSlider");
         *
         * // Get or sets the value of the slider
         * slider.value();
         */
        value: function (val) {
            var that = this,
                options = that.options;

            val = round(val);
            if (isNaN(val)) {
                return options.value;
            }

            if (val >= options.min && val <= options.max) {
                if (options.value != val) {
                    that.element.attr("value", formatValue(val));
                    options.value = val;
                    that.refresh();
                }
            }
        },

        refresh: function () {
            this.trigger(MOVE_SELECTION, { value: this.options.value });
        },

        _clearTimer: function (e) {
            clearTimeout(this.timeout);
            clearInterval(this.timer);
        },

        _keydown: function (e) {
            var that = this;

            if (e.keyCode in that._keyMap) {
                that._setValueInRange(that._keyMap[e.keyCode](that.options.value));
                e.preventDefault();
            }
        },

        _setValueInRange: function (val) {
            var that = this,
                options = that.options;

            val = round(val);
            if (isNaN(val)) {
                that._update(options.min);
                return;
            }

            val = math.max(math.min(val, options.max), options.min);
            that._update(val);
        }
    });

    Slider.Selection = function (dragHandle, that, options) {
        function moveSelection (val) {
            var selectionValue = val - options.min,
                index = math.ceil(selectionValue / options.smallStep),
                selection = parseInt(that._pixelSteps[index]),
                selectionDiv = that._trackDiv.find(".k-slider-selection"),
                halfDragHanndle = parseInt(dragHandle[that._outerSize]() / 2, 10);

            selectionDiv[that._size](selection);
            dragHandle.css(that._position, selection - halfDragHanndle);
        }

        moveSelection(options.value);

        that.bind([CHANGE, SLIDE, MOVE_SELECTION], function (e) {
            moveSelection(parseFloat(e.value, 10));
        });
    };

    Slider.Drag = function (dragHandle, type, owner, options) {
        var that = this;
        that.owner = owner;
        that.options = options;
        that.dragHandle = dragHandle;
        that.dragHandleSize = dragHandle[owner._outerSize]();
        that.type = type;

        that.draggable = new Draggable(dragHandle, {
            dragstart: proxy(that._dragstart, that),
            drag: proxy(that.drag, that),
            dragend: proxy(that.dragend, that)
        });

        dragHandle.click(false);
    };

    Slider.Drag.prototype = {
        dragstart: function (e) {
            this.draggable._startDrag(e);
        },

        _dragstart: function (e) {
            var that = this,
                owner = that.owner,
                options = that.options;

            if (!options.enabled) {
                e.preventDefault();
                return false;
            }

            owner.element.unbind(MOUSE_OVER);
            that.dragHandle.addClass(STATE_SELECTED);

            that.dragableArea = owner._getDragableArea();
            that.step = math.max(options.smallStep * (owner._maxSelection / owner._distance), 0);

            if (that.type) {
                that.selectionStart = options.selectionStart;
                that.selectionEnd = options.selectionEnd;
                owner._setZIndex(that.type);
            } else {
                that.oldVal = that.val = options.value;
            }

            if (options.tooltip.enabled) {
                that.tooltipDiv = $("<div class='k-widget k-tooltip'><!-- --></div>").appendTo(document.body);
                var html = "";

                if (that.type) {
                    var formattedSelectionStart = kendo.format(options.tooltip.format, that.selectionStart),
                        formattedSelectionEnd = kendo.format(options.tooltip.format, that.selectionEnd);

                    html = formattedSelectionStart + " - " + formattedSelectionEnd;
                } else {
                    that.tooltipInnerDiv = "<div class='k-callout k-callout-" + (owner._isHorizontal ? "s" : "e") + "'><!-- --></div>";
                    html = kendo.format(options.tooltip.format, that.val) + that.tooltipInnerDiv;
                }

                that.tooltipDiv.html(html);

                that.moveTooltip();
            }
        },

        drag: function (e) {
            var that = this,
                owner = that.owner,
                options = that.options,
                location = kendo.touchLocation(e),
                startPoint = that.dragableArea.startPoint,
                endPoint = that.dragableArea.endPoint;

            if (owner._isHorizontal) {
                that.val = that.constrainValue(location.x, startPoint, endPoint, location.x >= endPoint);
            } else {
                that.val = that.constrainValue(location.y, endPoint, startPoint, location.y <= endPoint);
            }

            if (that.oldVal != that.val) {
                that.oldVal = that.val;

                if (that.type) {
                    if (that.type == "firstHandle") {
                        if (that.val < that.selectionEnd) {
                            that.selectionStart = that.val;
                        } else {
                            that.selectionStart = that.selectionEnd = that.val;
                        }
                    } else {
                        if (that.val > that.selectionStart) {
                            that.selectionEnd = that.val;
                        } else {
                            that.selectionStart = that.selectionEnd = that.val;
                        }
                    }

                    owner.trigger(SLIDE, { values: [that.selectionStart, that.selectionEnd] });

                    if (options.tooltip.enabled) {
                        var formattedSelectionStart = kendo.format(options.tooltip.format, that.selectionStart),
                            formattedSelectionEnd = kendo.format(options.tooltip.format, that.selectionEnd);

                        that.tooltipDiv.html(formattedSelectionStart + " - " + formattedSelectionEnd );
                    }
                } else {
                    owner.trigger(SLIDE, { value: that.val });

                    if (options.tooltip.enabled) {
                        that.tooltipDiv.html(kendo.format(options.tooltip.format, that.val) + that.tooltipInnerDiv);
                    }
                }

                if (options.tooltip.enabled) {
                    that.moveTooltip();
                }
            }
        },

        dragend: function (e) {
            var that = this,
                owner = that.owner;

            if (e.keyCode == kendo.keys.ESC) {
                owner.refresh();
            } else {
                if (that.type) {
                    owner._update(that.selectionStart, that.selectionEnd);
                } else {
                    owner._update(that.val);
                }
            }

            if (owner.options.tooltip.enabled) {
                that.tooltipDiv.remove();
            }

            that.dragHandle.removeClass(STATE_SELECTED);
            owner.element.bind(MOUSE_OVER);

            return false;
        },

        moveTooltip: function () {
            var that = this,
                owner = that.owner,
                positionTop = 0,
                positionLeft = 0,
                dragHandleOffset = that.dragHandle.offset(),
                margin = 4,
                callout = that.tooltipDiv.find(".k-callout"),
                padding;

            if (that.type) {
                var dragHandles = owner.wrapper.find(DRAG_HANDLE),
                    firstDragHandleOffset = dragHandles.eq(0).offset(),
                    secondDragHandleOffset = dragHandles.eq(1).offset();

                if (owner._isHorizontal) {
                    positionTop = secondDragHandleOffset.top;
                    positionLeft = firstDragHandleOffset.left + ((secondDragHandleOffset.left - firstDragHandleOffset.left) / 2);
                } else {
                    positionTop = firstDragHandleOffset.top + ((secondDragHandleOffset.top - firstDragHandleOffset.top) / 2);
                    positionLeft = secondDragHandleOffset.left;
                }
            } else {
                positionTop = dragHandleOffset.top;
                positionLeft = dragHandleOffset.left;
            }
            if (owner._isHorizontal) {
                positionLeft -= parseInt((that.tooltipDiv.outerWidth() - that.dragHandle[owner._outerSize]()) / 2);
                positionTop -= that.tooltipDiv.outerHeight() + callout.height() + margin;
            } else {
                positionTop -= parseInt((that.tooltipDiv.outerHeight() - that.dragHandle[owner._outerSize]()) / 2);
                positionLeft -= that.tooltipDiv.outerWidth() + callout.width() + margin;
            }

            that.tooltipDiv.css({ top: positionTop, left: positionLeft });
        },

        constrainValue: function (position, min, max, maxOverflow) {
            var that = this,
                val = 0;

            if (min < position && position < max) {
                val = that.owner._getValueFromPosition(position, that.dragableArea);
            } else
                if (maxOverflow) {
                    val = that.options.max;
                } else {
                    val = that.options.min;
                }

            return val;
        }

    };

    kendo.ui.plugin(Slider);

    //
    // RangeSlider
    //

    var RangeSlider = SliderBase.extend(/** @lends kendo.ui.RangeSlider.prototype */{
        /**
         * @constructs
         * @extends kendo.ui.Widget
         * @param {DomElement} element DOM element
         * @param {Object} options Configuration options.
         * @option {Boolean} [enabled] <true> Can be used to enable/disable the rangeSlider.
         * @option {Number} [min] <0> The minimum value of the rangeSlider.
         * @option {Number} [max] <10> The maximum value of the rangeSlider.
         * @option {Object} [tooltip] Confituration of the Rangelider tooltip.
         * @option {Boolean} [tooltip.enabled] <true> Can be used to enable/disable the tooltip.
         * @option {String} [tooltip.format] <"{0}"> Can be used to formatting of the text of the tooltip. Note that the applied format will also influence the appearance of the rangeSlider tick labels.
         * @option {Number} [selectionStart] <0> The selection start value of the rangeSlider.
         * @option {Number} [selectionEnd] <10> The selection end value of the rangeSlider.
         * @option {String} [orientation] <"horizontal"> The orientation of the rangeSlider. Available options are "horizontal" and "vertical".
         * @option {String} [tickPlacement] <"both"> the location of the tick marks in the widget. Available options are:
         *     <dl>
         *         <dt>
         *              "topLeft"
         *         </dt>
         *         <dd>
         *              Tick marks are located on the top of the horizontal widget or on the left of the vertical widget.
         *         </dd>
         *         <dt>
         *              "bottomRight"
         *         </dt>
         *         <dd>
         *              Tick marks are located on the bottom of the horizontal widget or on the right side of the vertical widget.
         *         </dd>
         *         <dt>
         *              "both"
         *         </dt>
         *         <dd>
         *              Tick marks are located on both sides of the widget.
         *         </dd>
         *     </dl>
         * @option {Number} [smallStep] <1> The small step of the rangeSlider. The Value will be changed with SmallStep when the end user:
         *     <ul>
         *         <li>
         *             clicks on the Slider buttons
         *         </li>
         *         <li>
         *             presses the arrow keys (the drag handle must be focused)
         *         </li>
         *         <li>
         *             drag the drag handle
         *         </li>
         *     </ul>
         * @option {Number} [largeStep] <5> The delta with which the value will change when the user presses the Page Up or Page Down key (the drag handle must be focused). Note that the allied largeStep will also set large ticks for every large step.
         */
        init: function(element, options) {
            var that = this,
                inputs = $(element).find("input"),
                firstInput = inputs.eq(0)[0],
                secondInput = inputs.eq(1)[0];

            firstInput.type = "text";
            secondInput.type = "text";

            options = extend({}, {
                selectionStart: parseAttr(firstInput, "value"),
                min: parseAttr(firstInput, "min"),
                max: parseAttr(firstInput, "max"),
                smallStep: parseAttr(firstInput, "step")
            }, {
                selectionEnd: parseAttr(secondInput, "value"),
                min: parseAttr(secondInput, "min"),
                max: parseAttr(secondInput, "max"),
                smallStep: parseAttr(secondInput, "step")
            }, options);

            SliderBase.fn.init.call(that, element, options);
            options = that.options;
            that._setValueInRange(options.selectionStart, options.selectionEnd);

            var dragHandles = that.wrapper.find(DRAG_HANDLE);

            new RangeSlider.Selection(dragHandles, that, options);
            that._firstHandleDrag = new Slider.Drag(dragHandles.eq(0), "firstHandle", that, options);
            that._lastHandleDrag = new Slider.Drag(dragHandles.eq(1), "lastHandle" , that, options);
        },

        options: {
            name: "RangeSlider",
            selectionStart: 0,
            selectionEnd: 10
        },

        /**
         * Enables the rangeSlider.
         * @example
         * var rangeSlider = $("#rangeSlider").data("kendoRangeSlider");
         *
         * // enables the rangeSlider
         * rangeSlider.enable();
         */
        enable: function () {
            var that = this,
                options = that.options,
                clickHandler;

            that.wrapper
                .removeAttr(DISABLED)
                .removeClass(STATE_DISABLED)
                .addClass(STATE_DEFAULT);

            clickHandler = function (e) {
                if ($(e.target).hasClass("k-draghandle")) {
                    $(e.target).addClass(STATE_SELECTED);
                    return;
                }

                var location = kendo.touchLocation(e),
                    mousePosition = that._isHorizontal ? location.x : location.y,
                    dragableArea = that._getDragableArea(),
                    val = that._getValueFromPosition(mousePosition, dragableArea);

                if (val < options.selectionStart) {
                    that._setValueInRange(val, options.selectionEnd);
                    that._firstHandleDrag.dragstart(e);
                } else if (val > that.selectionEnd) {
                    that._setValueInRange(options.selectionStart, val);
                    that._lastHandleDrag.dragstart(e);
                } else {
                    if (val - options.selectionStart <= options.selectionEnd - val) {
                        that._setValueInRange(val, options.selectionEnd);
                        that._firstHandleDrag.dragstart(e);
                    } else {
                        that._setValueInRange(options.selectionStart, val);
                        that._lastHandleDrag.dragstart(e);
                    }
                }
            };

            that.wrapper
                .find(TICK_SELECTOR).bind(MOUSE_DOWN, clickHandler)
                .end()
                .find(TRACK_SELECTOR).bind(MOUSE_DOWN, clickHandler);

            that.wrapper.find(DRAG_HANDLE).bind(MOUSE_UP, function (e) {
                $(e.target).removeClass(STATE_SELECTED);
            });

            that.wrapper.find(DRAG_HANDLE)
                .eq(0).bind(KEY_DOWN,
                    proxy(function(e) {
                        this._keydown(e, "firstHandle");
                    }, that)
                )
                .end()
                .eq(1).bind(KEY_DOWN,
                    proxy(function(e) {
                        this._keydown(e, "lastHandle");
                    }, that)
                );

            that.options.enabled = true;
        },

        /**
         * Disables the rangeSlider.
         * @example
         * var rangeSlider = $("#rangeSlider").data("kendoRangeSlider");
         *
         * // disables the rangeSlider
         * rangeSlider.disable();
         */
        disable: function () {
            var that = this,
                options = that.options;

            that.wrapper
                .attr(DISABLED, DISABLED)
                .removeClass(STATE_DEFAULT)
                .addClass(STATE_DISABLED);

            that.wrapper
                .find(TICK_SELECTOR).unbind(MOUSE_DOWN)
                .end()
                .find(TRACK_SELECTOR).unbind(MOUSE_DOWN);

            that.wrapper
                .find(DRAG_HANDLE)
                .unbind(MOUSE_UP)
                .unbind(KEY_DOWN)
                .bind(KEY_DOWN, false);

            that.options.enabled = false;
        },

        _keydown: function (e, handle) {
            var that = this,
                selectionStartValue = that.options.selectionStart,
                selectionEndValue = that.options.selectionEnd;

            if (e.keyCode in that._keyMap) {
                if (handle == "firstHandle") {
                    selectionStartValue = that._keyMap[e.keyCode](selectionStartValue);

                    if (selectionStartValue > selectionEndValue) {
                        selectionEndValue = selectionStartValue;
                    }
                } else {
                    selectionEndValue = that._keyMap[e.keyCode](selectionEndValue);

                    if (selectionStartValue > selectionEndValue) {
                        selectionStartValue = selectionEndValue;
                    }
                }

                that._setValueInRange(selectionStartValue, selectionEndValue);
                e.preventDefault();
            }
        },

        _update: function (selectionStart, selectionEnd) {
            var that = this,
                values = that.values();

            var change = values[0] != selectionStart || values[1] != selectionEnd;

            that.values(selectionStart, selectionEnd);

            if (change) {
                that.trigger(CHANGE, { values: [selectionStart, selectionEnd] });
            }
        },

        /**
         * The values method gets or sets the selection start and end of the RangeSlider. The values method accepts {String}, {Number} or {Array} object as parameters, and returns a {Array} object with start and end selection values.
         * @example
         * var rangeSider = $("#rangeSlider").data("kendoRangeSlider");
         *
         * // Get or sets the selection start and end of the rangeSlider
         * rangeSlider.values();
         */
        values: function () {
            var that = this,
                options = that.options,
                selectionStart = 0,
                selectionEnd = 0;

            if (arguments.length == 0) {
                return [options.selectionStart, options.selectionEnd];
            } else if (arguments.length == 1 && $.isArray(arguments[0])) {
                selectionStart = arguments[0][0];
                selectionEnd = arguments[0][1];
            } else {
                selectionStart = round(arguments[0]);
                selectionEnd = round(arguments[1]);
            }

            if (selectionStart >= options.min && selectionStart <= options.max
            && selectionEnd >= options.min && selectionEnd <= options.max && selectionStart <= selectionEnd) {
                if (options.selectionStart != selectionStart || options.selectionEnd != selectionEnd) {
                    that.element.find("input")
                                .eq(0).attr("value", formatValue(selectionStart))
                                .end()
                                .eq(1).attr("value", formatValue(selectionEnd));

                    options.selectionStart = selectionStart;
                    options.selectionEnd = selectionEnd;
                    that.refresh();
                }
            }
        },

        refresh: function() {
            var that = this,
                options = that.options;

            that.trigger(MOVE_SELECTION, { values: [options.selectionStart, options.selectionEnd] });

            if (options.selectionStart == options.max && options.selectionEnd == options.max) {
                that._setZIndex("firstHandle");
            }
        },

        _setValueInRange: function (selectionStart, selectionEnd) {
            var options = this.options;

            selectionStart = math.max(math.min(selectionStart, options.max), options.min);

            selectionEnd = math.max(math.min(selectionEnd, options.max), options.min);

            if (selectionStart == options.max && selectionEnd == options.max) {
                this._setZIndex("firstHandle");
            }

            this._update(math.min(selectionStart, selectionEnd), math.max(selectionStart, selectionEnd));
        },

        _setZIndex: function (type) {
            this.wrapper.find(DRAG_HANDLE).each(function (index) {
                $(this).css("z-index", type == "firstHandle" ? 1 - index : index);
            });
        }
    });

    RangeSlider.Selection = function (dragHandles, that, options) {
        function moveSelection(values) {
            var selectionStartValue = values[0] - options.min,
                selectionEndValue = values[1] - options.min,
                selectionStartIndex = math.ceil(selectionStartValue / options.smallStep),
                selectionEndIndex = math.ceil(selectionEndValue / options.smallStep),
                selectionStart = that._pixelSteps[selectionStartIndex],
                selectionEnd = that._pixelSteps[selectionEndIndex],
                halfHandle = parseInt(dragHandles.eq(0)[that._outerSize]() / 2, 10);

            dragHandles.eq(0).css(that._position, selectionStart - halfHandle)
                       .end()
                       .eq(1).css(that._position, selectionEnd - halfHandle);

            makeSelection(selectionStart, selectionEnd);
        }

        function makeSelection(selectionStart, selectionEnd) {
            var selection = 0,
                selectionPosition = 0,
                selectionDiv = that._trackDiv.find(".k-slider-selection");

            selection = math.abs(selectionStart - selectionEnd);
            selectionPosition = selectionStart < selectionEnd ? selectionStart : selectionEnd;

            selectionDiv[that._size](selection);
            selectionDiv.css(that._position, selectionPosition - 1);
        }

        moveSelection(that.values());

        that.bind([ CHANGE, SLIDE, MOVE_SELECTION ], function (e) {
            moveSelection(e.values);
        });
    };

    kendo.ui.plugin(RangeSlider);

})(jQuery);
