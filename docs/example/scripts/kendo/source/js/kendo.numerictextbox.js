/*
* Kendo UI v2011.3.1129 (http://kendoui.com)
* Copyright 2011 Telerik AD. All rights reserved.
*
* Kendo UI commercial licenses may be obtained at http://kendoui.com/license.
* If you do not own a commercial license, this file shall be governed by the
* GNU General Public License (GPL) version 3. For GPL requirements, please
* review: http://www.gnu.org/copyleft/gpl.html
*/

(function($, undefined) {
    /**
    * @name kendo.ui.NumericTextBox.Description
    *
    * @section
    * <p>
    *    The NumericTextBox widget can convert an INPUT element into a numeric, percentage or currency textbox.
    *    The type is defined depending on the specified format. The widget renders spin buttons and with their help you can
    *    increment/decrement the value with a predefined step. The NumericTextBox widget accepts only numeric entries.
    *    The widget uses <em>kendo.culture.current</em> culture in order to determine number precision and other culture
    *    specific properties.
    * </p>
    *
    * <h3>Getting Started</h3>
    *
    * @exampleTitle Creating a NumericTextBox from existing INPUT element
    * @example
    * <!-- HTML -->
    * <input id="textbox" />
    *
    * @exampleTitle NumericTextBox initialization
    * @example
    *   $(document).ready(function(){
    *      $("#textbox").kendoNumericTextBox();
    *   });
    * @section
    *  <p>
    *      When a NumericTextBox is initialized, it will automatically wraps the input element with SPAN
    *      element and will render spin buttons.
    *  </p>
    *  <h3>Configuring NumericTextBox behaviors</h3>
    *  <p>
    *      NumericTextBox provides configuration options that can be easily set during initialization.
    *      Among the properties that can be controlled:
    *  </p>
    *  <ul>
    *      <li>Value of the NumericTextBox</li>
    *      <li>Min/Max values</li>
    *      <li>Increment step</li>
    *      <li>Precision of the number</li>
    *      <li>Number format. Any valid number format is allowed.</li>
    *  </ul>
    *  <p>
    *      To see a full list of available properties and values, review the Slider Configuration API documentation tab.
    *  </p>
    * @exampleTitle Customizing NumericTextBox defaults
    * @example
    *  $("#textbox").kendoNumericTextBox({
    *      value: 10,
    *      min: -10,
    *      max: 100,
    *      step: 0.75,
    *      format: "n",
    *      decimals: 3
    *  });
    * @section
    * @exampleTitle Create Currency NumericTextBox widget
    * @example
    *  $("#textbox").kendoNumericTextBox({
    *      format: "c2" //Define currency type and 2 digits precision
    *  });
    * @section
    * @exampleTitle Create Percentage NumericTextBox widget
    * @example
    *  $("#textbox").kendoNumericTextBox({
    *      format: "p",
    *      value: 0.15 // 15 %
    *  });
    */

    var kendo = window.kendo,
        keys = kendo.keys,
        ui = kendo.ui,
        Widget = ui.Widget,
        parse = kendo.parseFloat,
        touch = kendo.support.touch,
        CHANGE = "change",
        DISABLED = "disabled",
        INPUT = "k-input",
        TOUCHEND = "touchend",
        MOUSEDOWN = touch ? "touchstart" : "mousedown",
        MOUSEUP = touch ? "touchmove " + TOUCHEND : "mouseup mouseleave",
        HIDE = "k-hide-text",
        DEFAULT = "k-state-default",
        FOCUSED = "k-state-focused",
        HOVER = "k-state-hover",
        HOVEREVENTS = "mouseenter mouseleave",
        POINT = ".",
        SELECTED = "k-state-selected",
        STATEDISABLED = "k-state-disabled",
        NULL = null,
        proxy = $.proxy,
        decimals = {
            190 : ".",
            188 : ","
        };

    var NumericTextBox = Widget.extend(/** @lends kendo.ui.NumericTextBox.prototype */{
        /**
         * @constructs
         * @extends kendo.ui.Widget
         * @param {DomElement} element DOM element
         * @param {Object} options Configuration options
         * @option {Number} [value] <null> Specifies the value of the NumericTextBox widget.
         * @option {Number} [min] <null> Specifies the smallest value, which user can enter.
         * @option {Number} [max] <null> Specifies the biggest value, which user can enter.
         * @option {Number} [decimals] <null> Specifies the number precision. If not set precision defined by current culture is used.
         * @option {String} [format] <n> Specifies the format of the number. Any valid number format is allowed.
         * @option {String} [placeholder] <Enter value> Specifies the text displayed when the input is empty.
         * @option {String} [upArrowText] <Increase value> Specifies the title of the up arrow.
         * @option {String} [downArrowText] <Decrease value> Specifies the title of the down arrow.
         */
         init: function(element, options) {
             var that = this,
             isStep = options && options[step] !== undefined,
             min, max, step, value, format;

             Widget.fn.init.call(that, element, options);

             options = that.options;
             element = that.element.addClass(INPUT)
                           .bind({
                               keydown: proxy(that._keydown, that),
                               paste: proxy(that._paste, that),
                               blur: proxy(that._focusout, that)
                           });

             element.closest("form")
                    .bind("reset", function() {
                        setTimeout(function() {
                            that.value(element[0].value);
                        });
                    });

             that._wrapper();
             that._arrows();
             that._input();

             /**
             * Fires when the value is changed
             * @name kendo.ui.NumericTextBox#change
             * @event
             * @param {Event} e
             */
             that.bind(CHANGE, options);

             that._text.focus(proxy(that._click, that));

             min = parse(element.attr("min"));
             max = parse(element.attr("max"));
             step = parse(element.attr("step"));

             if (options.min === NULL && min !== NULL) {
                 options.min = min;
             }

             if (options.max === NULL && max !== NULL) {
                 options.max = max;
             }

             if (!isStep && step !== NULL) {
                 options.step = step;
             }

             format = options.format;
             if (format.slice(0,3) === "{0:") {
                 options.format = format.slice(3, format.length - 1);
             }

             value = options.value;
             that.value(value !== NULL ? value : element.val());

             that.enable(!element.is('[disabled]'));
         },

        options: {
            name: "NumericTextBox",
            min: NULL,
            max: NULL,
            value: NULL,
            step: 1,
            format: "n",
            upArrowText: "Increase value",
            downArrowText: "Decrease value"
        },

        /**
        * Enable/Disable the numerictextbox widget.
        * @param {Boolean} enable The argument, which defines whether to enable/disable tha numerictextbox.
        * @example
        * var textbox = $("#textbox").data("kendoNumericTextBox");
        *
        * // disables the numerictextbox
        * numerictextbox.enable(false);
        *
        * // enables the numerictextbox
        * numerictextbox.enable(true);
        */
        enable: function(enable) {
            var that = this,
                text = that._text,
                element = that.element;
                wrapper = that._inputWrapper,
                upArrow = that._upArrow,
                downArrow = that._downArrow;

            upArrow.unbind(MOUSEDOWN);
            downArrow.unbind(MOUSEDOWN);

            that._toggleText(true);

            if (enable === false) {
                wrapper
                    .removeClass(DEFAULT)
                    .addClass(STATEDISABLED)
                    .unbind(HOVEREVENTS);

                text.add(element).attr(DISABLED, DISABLED);
            } else {
                wrapper
                    .addClass(DEFAULT)
                    .removeClass(STATEDISABLED)
                    .bind(HOVEREVENTS, that._toggleHover);

                text.add(element).removeAttr(DISABLED);

                upArrow.bind(MOUSEDOWN, function(e) {
                    e.preventDefault();
                    that._spin(1);
                    that._upArrow.addClass(SELECTED);
                });

                downArrow.bind(MOUSEDOWN, function(e) {
                    e.preventDefault();
                    that._spin(-1);
                    that._downArrow.addClass(SELECTED);
                });
            }
        },

        /**
        * Gets/Sets the value of the numerictextbox.
        * @param {Number|String} value The value to set.
        * @returns {Number} The value of the numerictextbox.
        * @example
        * var numerictextbox = $("#textbox").data("kendoNumericTextBox");
        *
        * // get the value of the numerictextbox.
        * var value = numerictextbox.value();
        *
        * // set the value of the numerictextbox.
        * numerictextbox.value("10.20");
        */
        value: function(value) {
            var that = this;

            if (value === undefined) {
                return that._value;
            }

            that._update(value);
            that._old = that._value;
        },

        _adjust: function(value) {
            var that = this,
            options = that.options,
            min = options.min,
            max = options.max;

            if (min !== NULL && value < min) {
                value = min;
            } else if (max !== NULL && value > max) {
                value = max;
            }

            return value;
        },

        _arrows: function() {
            var that = this,
            arrows,
            options = that.options,
            element = that.element;

            arrows = element.siblings(".k-icon");

            if (!arrows[0]) {
                arrows = $(buttonHtml("up", options.upArrowText) + buttonHtml("down", options.downArrowText))
                        .insertAfter(element);

                arrows.wrapAll('<span class="k-select"/>');
            }

            arrows.bind(MOUSEUP, function(e) {
                if (!touch || kendo.eventTarget(e) != e.currentTarget || e.type === TOUCHEND) {
                    clearTimeout( that._spinning );
                }
                arrows.removeClass(SELECTED);
            });

            that._upArrow = arrows.eq(0);
            that._downArrow = arrows.eq(1);
        },

        _blur: function() {
            var that = this;

            that._toggleText(true);
            that._change(that.element.val());
        },

        _click: function(e) {
            var that = this;

            clearTimeout(that._focusing);
            that._focusing = setTimeout(function() {
                var input = e.target,
                    idx = caret(input),
                    value = input.value.substring(0, idx),
                    format = that._format(that.options.format),
                    group = format[","],
                    groupRegExp = new RegExp("\\" + group, "g"),
                    extractRegExp = new RegExp("([\\d\\" + group + "]+)(\\" + format[POINT] + ")?(\\d+)?"),
                    result = extractRegExp.exec(value),
                    caretPosition = 0;

                if (result) {
                    caretPosition = result[0].replace(groupRegExp, "").length;

                    if (value.indexOf("(") != -1 && that._value < 0) {
                        caretPosition++;
                    }
                }

                that._focusin();

                caret(that.element[0], caretPosition);
            });
        },

        _change: function(value) {
            var that = this;

            that._update(value);
            value = that._value;

            if (that._old != value) {
                that._old = value;
                that.trigger(CHANGE);

                // trigger the DOM change event so any subscriber gets notified
                that.element.trigger(CHANGE);
            }
        },

        _focusin: function() {
            var that = this;
            clearTimeout(that._bluring);
            that._toggleText(false);
            that.element.focus();
            that._inputWrapper.addClass(FOCUSED);
        },

        _focusout: function() {
            var that = this;
            that._bluring = setTimeout(function() {
                that._inputWrapper.removeClass(FOCUSED);
                that._blur();
            }, 100);
        },

        _format: function(format) {
            var that = this,
                options = that.options,
                numberFormat = kendo.culture().numberFormat;


            if (format.indexOf("c") > -1) {
                numberFormat = numberFormat.currency;
            } else if (format.indexOf("p") > -1) {
                numberFormat = numberFormat.percent;
            }

            return numberFormat;
        },

        _input: function() {
            var that = this,
                CLASSNAME = "k-formatted-value",
                element = that.element.show()[0],
                wrapper = that.wrapper,
                text;


            text = wrapper.find(POINT + CLASSNAME);

            if (!text[0]) {
                text = $("<input />").insertBefore(element).addClass(CLASSNAME);
            }

            element.type = "text";
            text[0].type = "text";

            text[0].style.cssText = element.style.cssText;
            that._text = text.attr("readonly", true).addClass(element.className);
        },

        _keydown: function(e) {
            var that = this,
                key = e.keyCode;

            if (key == keys.DOWN) {
                that._step(-1);
            } else if (key == keys.UP) {
                that._step(1);
            } else if (key == keys.ENTER) {
                that._change(that.element.val());
            }

            if (that._prevent(key) && !e.ctrlKey) {
                e.preventDefault();
            }
        },

        _paste: function(e) {
            var that = this,
                element = e.target,
                value = element.value;
            setTimeout(function() {
                if (parse(element.value) === NULL) {
                    that._update(value);
                }
            });
        },

        _prevent: function(key) {
            var that = this,
                prevent = true,
                min = that.options.min,
                element = that.element[0],
                value = element.value,
                separator = that._format(that.options.format)[POINT],
                idx = caret(element),
                end;

            if ((key > 16 && key < 21)
             || (key > 32 && key < 37)
             || (key > 47 && key < 58)
             || (key > 95 && key < 106)
             || key == 45 /* INSERT */
             || key == 46 /* DELETE */
             || key == keys.LEFT
             || key == keys.RIGHT
             || key == keys.TAB
             || key == keys.BACKSPACE
             || key == keys.ENTER) {
                prevent = false;
            } else if (decimals[key] === separator && value.indexOf(separator) == -1) {
                prevent = false;
            } else if ((min === NULL || min < 0) && value.indexOf("-") == -1 && (key == 189 || key == 109) && idx == 0) { //sign
                prevent = false;
            } else if (key == 110 && value.indexOf(separator) == -1) {
                end = value.substring(idx);

                element.value = value.substring(0, idx) + separator + end;
            }

            return prevent;
        },

        _spin: function(step, timeout) {
            var that = this;

            timeout = timeout || 500;

            clearTimeout( that._spinning );
            that._spinning = setTimeout(function() {
                that._spin(step, 50);
            }, timeout );

            that._step(step);
        },

        _step: function(step) {
            var that = this,
                element = that.element,
                value = parse(element.val()) || 0;

            if (document.activeElement != element[0]) {
                that._focusin();
            }

            value += that.options.step * parse(step);

            that._update(that._adjust(value));
        },

        _toggleHover: function(e) {
            if (!touch) {
                $(e.currentTarget).toggleClass(HOVER, e.type === "mouseenter");
            }
        },

        _toggleText: function(toggle) {
            var that = this;

            toggle = !!toggle;
            that._text.toggle(toggle);
            that.element.toggle(!toggle);
        },

        _update: function(value) {
            var that = this,
                options = that.options,
                format = options.format,
                decimals = options.decimals,
                numberFormat = that._format(format),
                isNotNull;

            if (decimals === undefined) {
                decimals = numberFormat.decimals;
            }

            value = parse(value);

            isNotNull = value !== NULL;

            if (isNotNull) {
                value = parseFloat(value.toFixed(decimals));
            }

            that._value = value = that._adjust(value);
            that._text.val(isNotNull ? kendo.toString(value, format) : options.placeholder);
            that.element.val(isNotNull ? value.toString().replace(POINT, numberFormat[POINT]) : "");
        },

        _wrapper: function() {
            var that = this,
                element = that.element,
                wrapper;

            wrapper = element.parent();

            if (!wrapper.is("span.k-widget")) {
                wrapper = element.hide().wrap('<span class="k-numeric-wrap k-state-default" />').parent();
                wrapper = wrapper.wrap("<span/>").parent();
            }

            wrapper[0].style.cssText = element[0].style.cssText;
            that.wrapper = wrapper.addClass("k-widget k-numerictextbox").show();
            that._inputWrapper = $(wrapper[0].firstChild);
        }
    });

    function buttonHtml(className, text) {
        return '<span unselectable="on" class="k-link"><span class="k-icon k-arrow-' + className + '" title="' + text + '">' + text + '</span></span>'
    }

    function caret(element, position) {
        var range,
            isPosition = position !== undefined;

        if (document.selection) {
            element.focus();
            var range = document.selection.createRange();
            if (isPosition) {
                range.move("character", position);
                range.select();
            } else {
                var rangeElement = element.createTextRange(),
                    rangeDuplicated = rangeElement.duplicate();
                    rangeElement.moveToBookmark(range.getBookmark());
                    rangeDuplicated.setEndPoint('EndToStart', rangeElement);

                position = rangeDuplicated.text.length;

            }
        } else if (element.selectionStart !== undefined) {
            if (isPosition) {
                element.focus();
                element.setSelectionRange(position, position);
            } else {
                position = element.selectionStart;
            }
        }

        return position;
    }

    ui.plugin(NumericTextBox);
})(jQuery);
