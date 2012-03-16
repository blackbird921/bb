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
    * @name kendo.ui.ComboBox.Description
    *
    * @section
    *   <p>
    *       The ComboBox widget allows the selection from pre-defined values or entering a new value.
    *       It is a richer version of the standard HTML select, providing support for local and remote data binding, item templates,
    *       and configurable options for controlling the list behavior.
    *   </p>
    *   If you do not want to allow user input, use the <a href="../dropdownlist/index.html" title="Kendo UI DropDownList">Kendo UI DropDownList</a>.
    *
    *   <h3>Getting Started</h3>
    *   There are two basic ways to create a ComboBox:
    *   <ol>
    *       <li>From a basic HTML input element, using data binding to define the list items</li>
    *       <li>From a HTML select element, using HTML to define the list items</li>
    *   </ol>
    *   Regardless of the initialization technique, the resulting Kendo UI ComboBox will look and function identically.
    *
    * @exampleTitle Creating a combobox from existing input HTML element
    * @example
    * <!-- HTML -->
    * <input id="combobox" />
    *
    * @exampleTitle ComboBox initialization
    * @example
    *   $(document).ready(function(){
    *      $("#combobox").kendoComboBox([{text: "Item1", value: "1"}, {text: "Item2", value: "2"}]);
    *   });
    *
    * @exampleTitle Creating a combobox from existing select HTML element
    * @example
    * <!-- HTML -->
    * <select id="combobox">
    *     <option>Item 1</option>
    *     <option>Item 2</option>
    *     <option>Item 3</option>
    * </select>
    *
    * @exampleTitle ComboBox initialization
    * @example
    *   $(document).ready(function(){
    *       $("#combobox").kendoComboBox();
    *   });
    *
    * @section
    *   <h3>Binding to Data</h3>
    *   <p>
    *       The ComboBox can be bound to both local JavaScript Arrays and remote data via the
    *       Kendo DataSource component. Local JavaScript Arrays are appropriate for limited value
    *       options, while remote data binding is better for larger data sets. With remote binding,
    *       options will be loaded on-demand, similar to AutoComplete.
    *   </p>
    * @exampleTitle Binding to a remote OData service
    * @example
    *   $(document).ready(function() {
    *       $("#titles").kendoComboBox({
    *           index: 0,
    *           dataTextField: "Name",
    *           dataValueField: "Id",
    *           filter: "contains",
    *           dataSource: {
    *               type: "odata",
    *               severFiltering: true,
    *               serverPaging: true,
    *               pageSize: 20,
    *               transport: {
    *                   read: "http://odata.netflix.com/Catalog/Titles"
    *               }
    *           }
    *       });
    *   });
    *
    * @section
    *   <h3>Customizing Item Templates</h3>
    *   <p>
    *       ComboBox leverages Kendo UI high-performance Templates to give you complete control
    *       over item rendering. For a complete overview of Kendo UI Template capabilities and syntax,
    *       please review the <a href="../templates/index.html" title="Kendo UI Template">Kendo UI Template</a> demos and documentation.
    *   </p>
    * @exampleTitle Basic item template customization
    * @example
    *   <!-- HTML -->
    *   <input id="titles"/>
    *
    *   <!-- Template -->
    *   <script id="scriptTemplate" type="text/x-kendo-template">
    *       # if (data.BoxArt.SmallUrl) { #
    *           <img src="${ data.BoxArt.SmallUrl }" alt="${ data.Name }" />Title:${ data.Name }, Year: ${ data.Name }
    *       # } else { #
    *           <img alt="${ data.Name }" />Title:${ data.Name }, Year: ${ data.Name }
    *       # } #
    *   </script>
    *
    *   <!-- ComboBox initialization -->
    *   <script type="text/javascript">
    *       $(document).ready(function() {
    *           $("#titles").kendoComboBox({
    *               autoBind: false,
    *               dataTextField: "Name",
    *               dataValueField: "Id",
    *               template: $("#scriptTemplate").html(),
    *               dataSource: {
    *                   type: "odata",
    *                   severFiltering: true,
    *                   serverPaging: true,
    *                   pageSize: 20,
    *                   transport: {
    *                       read: "http://odata.netflix.com/Catalog/Titles"
    *                   }
    *               }
    *           });
    *       });
    *   </script>
    */
    var kendo = window.kendo,
        ui = kendo.ui,
        List = ui.List,
        Select = ui.Select,
        CLICK = "click",
        ATTRIBUTE = "disabled",
        CHANGE = "change",
        DEFAULT = "k-state-default",
        DISABLED = "k-state-disabled",
        FOCUSED = "k-state-focused",
        SELECT = "select",
        STATE_SELECTED = "k-state-selected",
        STATE_FILTER = "filter",
        STATE_ACCEPT = "accept",
        HOVER = "k-state-hover",
        HOVEREVENTS = "mouseenter mouseleave",
        NULL = null,
        proxy = $.proxy;

    var ComboBox = Select.extend(/** @lends kendo.ui.ComboBox.prototype */{
        /**
        * @constructs
        * @extends kendo.ui.Select
        * @param {DomElement} element DOM element
        * @param {Object} options Configuration options.
        * @option {kendo.data.DataSource|Object} [dataSource] Instance of DataSource or the data that the ComboBox will be bound to.
        * @option {Boolean} [enable] <true> Controls whether the ComboBox should be initially enabled.
        * @option {Number} [index] <-1> Defines the initial selected item.
        * @option {Boolean} [autoBind] <true> Controls whether to bind the widget on initialization.
        * @option {Boolean} [highlightFirst] <true> Controls whether the first item will be automatically highlighted.
        * @option {Boolean} [suggest] <false> Controls whether the ComboBox should automatically auto-type the rest of text.
        * @option {Number} [delay] <200> Specifies the delay in ms after which the ComboBox will start filtering dataSource.
        * @option {Number} [minLength] <1> Specifies the minimum characters that should be typed before the ComboBox activates
        * @option {String} [dataTextField] <"text"> Sets the field of the data item that provides the text content of the list items.
        * @option {String} [dataValueField] <"value"> Sets the field of the data item that provides the value content of the list items.
        * @option {String} [filter] <"none"> Defines the type of filtration. If "none" the ComboBox will not filter the items.
        * @option {Number} [height] <200> Define the height of the drop-down list in pixels.
        */
        init: function(element, options) {
            var that = this;

            options = $.isArray(options) ? { dataSource: options } : options;

            Select.fn.init.call(that, element, options);

            options = that.options;
            element = that.element.focus(function() {
                        that.input.focus();
                      });

            that._reset();

            that._wrapper();

            that._input();

            that._popup();

            that._accessors();

            that._dataSource();

            that._enable();

            that.bind([
                /**
                * Fires when the drop-down list is opened
                * @name kendo.ui.ComboBox#open
                * @event
                * @param {Event} e
                */
                /**
                * Fires when the drop-down list is closed
                * @name kendo.ui.ComboBox#close
                * @event
                * @param {Event} e
                */
                /**
                * Fires when the value has been changed.
                * @name kendo.ui.ComboBox#change
                * @event
                * @param {Event} e
                */
                CHANGE
            ], options);

            that.input.bind({
                keydown: proxy(that._keydown, that),
                focus: function() {
                    that._inputWrapper.addClass(FOCUSED);
                },
                blur: function() {
                    that._bluring = setTimeout(function() {
                        clearTimeout(that._typing);

                        that.text(that.text());
                        that._blur();

                        that._inputWrapper.removeClass(FOCUSED);
                    }, 100);
                }
            });

            that._old = that.value();

            if (options.autoBind) {
                that._select();
            } else if (element.is(SELECT)) {
                that.input.val(element.children(":selected").text());
            }
        },

        options: {
            name: "ComboBox",
            enable: true,
            index: -1,
            autoBind: true,
            delay: 200,
            dataTextField: "text",
            dataValueField: "value",
            minLength: 0,
            height: 200,
            highlightFirst: true,
            filter: "none",
            suggest: false
        },

        current: function(li) {
            var that = this,
                current = that._current;

            if (li === undefined) {
                return current;
            }

            that._selected = NULL;

            if (current) {
                current.removeClass(STATE_SELECTED);
            }

            Select.fn.current.call(that, li);
        },

        /**
        * Closes the drop-down list.
        * @name kendo.ui.ComboBox#close
        * @function
        * @example
        * combobox.close();
        */

        /**
        * Enables/disables the combobox widget
        * @param {Boolean} enable Desired state
        */
        enable: function(enable) {
            var that = this,
                input = that.input,
                element = that.element,
                wrapper = that._inputWrapper,
                arrow = that._arrow.parent();

            if (enable === false) {
                wrapper
                    .removeClass(DEFAULT)
                    .addClass(DISABLED)
                    .unbind(HOVEREVENTS);

                input.add(element).attr(ATTRIBUTE, ATTRIBUTE);
                arrow.unbind(CLICK);
            } else {
                wrapper
                    .removeClass(DISABLED)
                    .addClass(DEFAULT)
                    .bind(HOVEREVENTS, that._toggleHover);

                input.add(element).removeAttr(ATTRIBUTE);
                arrow.bind(CLICK, function() { that.toggle() });
            }
        },

        /**
        * Opens the drop-down list.
        * @example
        * combobox.open();
        */
        open: function() {
            var that = this,
                selected = that._selected;

            if (that.popup.visible()) {
                return;
            }

            if (!that.ul[0].firstChild || that._state === STATE_ACCEPT) {
                that._open = true;
                that._state = "";
                that._select();
            } else {
                that.popup.open();
                if (selected) {
                    that._scroll(selected[0]);
                }
            }
        },

        refresh: function() {
            var that = this,
                ul = that.ul,
                options = that.options,
                suggest = options.suggest,
                height = options.height,
                data = that._data(),
                length = data.length;

            ul[0].innerHTML = kendo.render(that.template, data);
            that._height(length);

            if (that.element.is(SELECT)) {
                that._options(data);
            }

            if (length) {
                if (suggest || options.highlightFirst) {
                    that.current($(that.ul[0].firstChild));
                }

                if (suggest) {
                    that.suggest(that._current);
                }
            }

            if (that._open) {
                that._open = false;
                that.toggle(!!length);
            }

            that._hideBusy();
        },

        /**
        * Selects drop-down list item and sets the value and the text of the combobox.
        * @param {jQueryObject | Number | Function} li LI element or index of the item or predicate function, which defines the item that should be selected.
        * @example
        * var combobox = $("#combobox").data("kendoComboBox");
        *
        * // selects by jQuery object
        * combobox.select(combobox.ul.children().eq(0));
        *
        * // selects by index
        * combobox.select(1);
        *
        * // selects item if its text is equal to "test" using predicate function
        * combobox.select(function(dataItem) {
        *     return dataItem.text === "test";
        * });
        */
        select: function(li) {
            var that = this,
                text,
                value,
                idx = that._highlight(li),
                data = that._data();

            if (idx !== -1) {
                that._selected = that._current.addClass(STATE_SELECTED);

                data = data[idx];
                text = that._text(data);
                value = that._value(data);

                that._prev = that.input[0].value = text;
                that._accessor(value != undefined ? value : text, idx);
            }
        },

        /**
        * Filters dataSource using the provided parameter and rebinds drop-down list.
        * @param {string} word The filter value.
        * @example
        * var combobox = $("#combobox").data("kendoComboBox");
        *
        * // Searches for item which has "In" in the name.
        * combobox.search("In");
        */
        search: function(word) {
            var that = this,
                word = word || that.text(),
                length = word.length,
                options = that.options,
                filter = options.filter;

            clearTimeout(that._typing);

            if (length >= options.minLength) {
                if (filter === "none") {
                    that._filter(word);
                } else {
                    that._open = true;
                    that._state = STATE_FILTER,
                    that.dataSource.filter( {
                        field: options.dataTextField,
                        operator: filter,
                        value: word
                    });
                }
            }
        },

        suggest: function(word) {
            var that = this,
                element = that.input[0],
                value = that.text(),
                caret = List.caret(element);


            if (typeof word !== "string") {
                word = word ? word.text() : "";
            }

            if (caret <= 0) {
                caret = value.toLowerCase().indexOf(word.toLowerCase()) + 1;
            }

            if (!word) {
                word = value.substring(0, caret);
            }

            if (word !== value) {
                that.text(word);
                List.selectText(element, caret, word.length);
            }
        },

        /**
        * Gets/Sets the text of the ComboBox.
        * @param {String} text The text to set.
        * @returns {String} The text of the combobox.
        * @example
        * var combobox = $("#combobox").data("kendoComboBox");
        *
        * // get the text of the combobox.
        * var text = combobox.text();
        */
        text: function (text) {
            var that = this,
                input = that.input[0];

            if (text !== undefined) {
                that.select(function(dataItem) {
                    return that._text(dataItem) === text;
                });

                if (!that._selected) {
                    that._custom(text);
                }

                input.value = text;
            } else {
                return input.value;
            }
        },

        /**
        * Toggles the drop-down list between opened and closed state.
        * @param {Boolean} toggle Defines the whether to open/close the drop-down list.
        * @example
        * var combobox = $("#combobox").data("kendoComboBox");
        *
        * // toggles the open state of the drop-down list.
        * combobox.toggle();
        */
        toggle: function(toggle) {
            var that = this;
            clearTimeout(that._bluring);
            that.input[0].focus();
            setTimeout( function () { that._toggle(toggle); }); // Fixes an annoying flickering issue in iOS.
        },

        /**
        * Gets/Sets the value of the combobox. If the value is undefined, text of the data item will be used.
        * @param {String} value The value to set.
        * @returns {String} The value of the combobox.
        * @example
        * var combobox = $("#combobox").data("kendoComboBox");
        *
        * // get the value of the combobox.
        * var value = combobox.value();
        *
        * // set the value of the combobox.
        * combobox.value("1"); //looks for item which has value "1"
        */
        value: function(value) {
            var that = this,
                idx,
                element = that.element;

            if (value !== undefined) {
                idx = that._index(value);

                if (idx > -1) {
                    that.select(idx);
                } else {
                    that.current(NULL);
                    that._custom(value);
                    that.text(value);
                }

                that._old = that._accessor();
            } else {
                return that._accessor();
            }
        },

        _accept: function(li) {
            var that = this;

            if (li && that.popup.visible()) {

                if (that._state === STATE_FILTER) {
                    that._state = STATE_ACCEPT;
                }

                setTimeout( function () { that._focus(li); });
            } else {
                that.text(that.text());
                that._change();
            }
        },

        _custom: function(value) {
            var that = this,
                element = that.element,
                custom = that._option;

            if (element.is(SELECT)) {
                if (!custom) {
                    custom = that._option = $("<option/>");
                    element.append(custom);
                }
                custom.text(value);
                custom[0].selected = true;
            } else {
                element.val(value);
            }
        },

        _filter: function(word) {
            var that = this,
                options = that.options,
                word = word.toLowerCase(),
                dataSource = that.dataSource,
                predicate = function (dataItem) {
                    var text = that._text(dataItem);
                    if (text !== undefined) {
                        text = text + "";
                        if (text !== "" && word === "") {
                            return false;
                        }

                        return text.toLowerCase().indexOf(word) === 0;
                    }
                };

            if (!that.ul[0].firstChild) {
                dataSource.one(CHANGE, function () { that.search(word); }).fetch();
                return;
            }

            if (that._highlight(predicate) !== -1) {
                if (options.suggest && that._current) {
                    that.suggest(that._current);
                }
                that.open();
            }

            that._hideBusy();
        },

        _highlight: function(li) {
            var that = this, idx;

            if (li == undefined) {
                return -1;
            }

            li = that._get(li);
            idx = List.inArray(li[0], that.ul[0]);

            if (idx == -1) {
                if (that.options.highlightFirst && !that.text()) {
                    li = $(that.ul[0].firstChild);
                } else {
                    li = NULL;
                }
            }

            that.current(li);

            return idx;
        },

        _input: function() {
            var that = this,
                element = that.element[0],
                wrapper = that.wrapper,
                SELECTOR = ".k-input",
                input;

            input = wrapper.find(SELECTOR);

            if (!input[0]) {
                wrapper.append('<span class="k-dropdown-wrap k-state-default"><input class="k-input" type="text" autocomplete="off"/><span class="k-select"><span class="k-icon k-arrow-down">select</span></span></span>')
                       .append(that.element);

                input = wrapper.find(SELECTOR);
            }

            input[0].style.cssText = element.style.cssText;
            input.addClass(element.className)
                 .val(element.value)
                 .css({
                    width: "100%",
                    height: "auto"
                 })
                 .show();

            that._focused = that.input = input;
            that._arrow = wrapper.find(".k-icon");
            that._inputWrapper = $(wrapper[0].firstChild)
        },

        _keydown: function(e) {
            var that = this;

            if (kendo.keys.TAB === e.keyCode) {
                that.text(that.input.val());

                if (that._state === STATE_FILTER && that._selected) {
                    that._state = STATE_ACCEPT;
                }

            } else if (!that._move(e)) {
               that._search();
            }
        },

        _search: function() {
            var that = this;
            clearTimeout(that._typing);

            that._typing = setTimeout(function() {
                var value = that.text();
                if (that._prev !== value) {
                    that._prev = value;
                    that.search(value);
                }
            }, that.options.delay);
        },

        _select: function() {
            var that = this;

            that.dataSource.one(CHANGE, function() {
                var value = that.value();
                if (value) {
                    that.value(value);
                } else {
                    that.select(that.options.index);
                }

                that._old = that.value();
            }).query();
        },

        _wrapper: function() {
            var that = this,
                element = that.element,
                wrapper;

            wrapper = element.parent();

            if (!wrapper.is("span.k-widget")) {
                wrapper = element.hide().wrap("<span />").parent();
            }

            wrapper[0].style.cssText = element[0].style.cssText;
            that.wrapper = wrapper.addClass("k-widget k-combobox k-header").show();
        }
    });

    ui.plugin(ComboBox);
})(jQuery);
