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
    * @name kendo.ui.DropDownList.Description
    *
    * @section
    *   <p>
    *       The DropDownList widget displays a list of values and allows the selection of a single value from the list.
    *       It is a richer version of the standard HTML select, providing support for local and remote data binding, item templates,
    *       and configurable options for controlling the list behavior.
    *   </p>
    *   If you want to allow user input, use the <a href="../combobox/index.html" title="Kendo UI ComboBox">Kendo UI ComboBox</a>.
    *
    *   <h3>Getting Started</h3>
    *   There are two basic ways to create a DropDownList:
    *   <ol>
    *       <li>From a basic HTML input element, using data binding to define the list items</li>
    *       <li>From a HTML select element, using HTML to define the list items</li>
    *   </ol>
    *   Regardless of the initialization technique, the resulting Kendo UI DropDownList will look and function identically.
    *
    * @exampleTitle Creating a dropdownlist from existing input HTML element
    * @example
    * <!-- HTML -->
    * <input id="dropdownlist" />
    *
    * @exampleTitle DropDownList initialization
    * @example
    *   $(document).ready(function(){
    *      $("#dropdownlist").kendoDropDownList([{text: "Item1", value: "1"}, {text: "Item2", value: "2"}]);
    *   });
    *
    * @exampleTitle Creating a dropdownlist from existing select HTML element
    * @example
    * <!-- HTML -->
    * <select id="dropdownlist">
    *     <option>Item 1</option>
    *     <option>Item 2</option>
    *     <option>Item 3</option>
    * </select>
    *
    * @exampleTitle DropDownList initialization
    * @example
    *   $(document).ready(function(){
    *       $("#dropdownlist").kendoDropDownList();
    *   });
    *
    * @section
    *   <h3>Binding to Data</h3>
    *   <p>
    *       The DropDownList can be bound to both local JavaScript Arrays and remote data via the
    *       Kendo DataSource component. Local JavaScript Arrays are appropriate for limited value
    *       options, while remote data binding is better for larger data sets. With remote binding,
    *       options will be loaded on-demand, similar to AutoComplete.
    *   </p>
    * @exampleTitle Binding to a remote OData service
    * @example
    *   $(document).ready(function() {
    *       $("#titles").kendoDropDownList({
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
    *       DropDownList leverages Kendo UI high-performance Templates to give you complete control
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
    *   <!-- DropDownList initialization -->
    *   <script type="text/javascript">
    *       $(document).ready(function() {
    *           $("#titles").kendoDropDownList({
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
        Select = ui.Select,
        ATTRIBUTE = "disabled",
        CHANGE = "change",
        SELECT = "select",
        FOCUSED = "k-state-focused",
        DEFAULT = "k-state-default",
        DISABLED = "k-state-disabled",
        SELECTED = "k-state-selected",
        HOVER = "k-state-hover",
        HOVEREVENTS = "mouseenter mouseleave",
        INPUTWRAPPER = ".k-dropdown-wrap",
        proxy = $.proxy;

    var DropDownList = Select.extend( /** @lends kendo.ui.DropDownList.prototype */ {
        /**
         * @constructs
         * @extends kendo.ui.Select
         * @param {DomElement} element DOM element
         * @param {Object} options Configuration options.
         * @option {kendo.data.DataSource|Object} [dataSource] Instance of DataSource or the data that the DropDownList will be bound to.
         * @option {Boolean} [enable] <true> Controls whether the DropDownList should be initially enabled.
         * @option {Number} [index] <0> Defines the initial selected item.
         * @option {Boolean} [autoBind] <true> Controls whether to bind the widget on initialization.
         * @option {Number} [delay] <500> Specifies the delay in ms before the search text typed by the end user is cleared.
         * @option {String} [dataTextField] <"text"> Sets the field of the data item that provides the text content of the list items.
         * @option {String} [dataValueField] <"value"> Sets the field of the data item that provides the value content of the list items.
         * @option {Number} [height] <200> Define the height of the drop-down list in pixels.
         * @option {String} [optionLabel] Define the text of the default empty item.
         */
        init: function(element, options) {
            var that = this,

            options = $.isArray(options) ? { dataSource: options } : options;

            Select.fn.init.call(that, element, options);

            options = that.options;
            element = that.element.focus(function() {
                that.wrapper.focus();
            });

            that._reset();

            that._word = "";

            that._wrapper();

            that._span();

            that._popup();

            that._accessors();

            that._dataSource();

            that._enable();

            that.bind([
                /**
                * Fires when the drop-down list is opened
                * @name kendo.ui.DropDownList#open
                * @event
                * @param {Event} e
                */
                /**
                * Fires when the drop-down list is closed
                * @name kendo.ui.DropDownList#close
                * @event
                * @param {Event} e
                */
                /**
                * Fires when the value has been changed.
                * @name kendo.ui.DropDownList#change
                * @event
                * @param {Event} e
                */
                CHANGE
            ], options);

            if (options.autoBind) {
                that.dataSource.fetch();
            } else if (element.is(SELECT)) {
                that.text(element.children(":selected").text());
            }
        },

        options: {
            name: "DropDownList",
            enable: true,
            index: 0,
            autoBind: true,
            delay: 500,
            dataTextField: "text",
            dataValueField: "value",
            height: 200
        },

        /**
        * Closes the drop-down list.
        * @name kendo.ui.DropDownList#close
        * @function
        * @example
        * dropdownlist.close();
        */

        /**
        * Enables/disables the dropdownlist widget
        * @param {Boolean} enable Desired state
        */
        enable: function(enable) {
            var that = this,
                element = that.element,
                wrapper = that.wrapper,
                dropDownWrapper = that._inputWrapper;

            if (enable === false) {
                element.attr(ATTRIBUTE, ATTRIBUTE);

                wrapper.unbind();

                dropDownWrapper
                    .removeClass(DEFAULT)
                    .addClass(DISABLED)
                    .unbind(HOVEREVENTS)

            } else {
                element.removeAttr(ATTRIBUTE, ATTRIBUTE);

                dropDownWrapper
                    .addClass(DEFAULT)
                    .removeClass(DISABLED)
                    .bind(HOVEREVENTS, that._toggleHover);

                wrapper
                    .bind({
                        keydown: proxy(that._keydown, that),
                        keypress: proxy(that._keypress, that),
                        focusin: function() {
                            that._inputWrapper.addClass(FOCUSED);
                            clearTimeout(that._bluring);
                        },
                        click: function() {
                            that.toggle();
                        },
                        focusout: function(e) {
                            that._bluring = setTimeout(function() {
                                that._blur();
                                that._inputWrapper.removeClass(FOCUSED);
                            }, 100);
                        }
                    });
            }
        },

        /**
        * Opens the drop-down list.
        * @example
        * dropdownlist.open();
        */
        open: function() {
            var that = this,
                current = that._current;

            if (!that.ul[0].firstChild) {
                that._open = true;
                that.dataSource.fetch();
            } else {
                that.popup.open();
                if (current) {
                    that._scroll(current[0]);
                }
            }
        },

        /**
        * Toggles the drop-down list between opened and closed state.
        * @param {Boolean} toggle Defines the whether to open/close the drop-down list.
        * @example
        * var dropdownlist = $("#dropdownlist").data("kendoDropDownList");
        *
        * // toggles the open state of the drop-down list.
        * dropdownlist.toggle();
        */
        toggle: function(toggle) {
            this._toggle(toggle);
        },

        refresh: function() {
            var that = this,
                value = that.value(),
                options = that.options,
                data = that._data(),
                length = data.length;

            that.ul[0].innerHTML = kendo.render(that.template, data);
            that._height(length);

            if (that.element.is(SELECT)) {
                that._options(data);
            }

            if (value) {
                that.value(value);
            } else {
                that.select(options.index);
            }

            that._old = that.value();

            if (that._open) {
                that.toggle(length);
            }

            that._hideBusy();
        },

        /**
        * Selects item, which starts with the provided parameter.
        * @param {string} word The search value.
        * @example
        * var dropdownlist = $("#dropdownlist").data("kendoDropDownList");
        *
        * // Selects item which starts with "In".
        * autocomplete.search("In");
        */
        search: function(word) {
            if(word){
                var that = this;
                word = word.toLowerCase();

                that.select(function(dataItem) {
                    var text = that._text(dataItem);
                    if (text !== undefined) {
                        return (text + "").toLowerCase().indexOf(word) === 0;
                    }
                });
            }
        },

        /**
        * Selects drop-down list item and sets the value and the text of the dropdownlist.
        * @param {jQueryObject | Number | Function} li LI element or index of the item or predicate function, which defines the item that should be selected.
        * @example
        * var dropdownlist = $("#dropdownlist").data("kendoDropDownList");
        *
        * // selects by jQuery object
        * dropdownlist.select(dropdownlist.ul.children().eq(0));
        *
        * // selects by index
        * dropdownlist.select(1);
        *
        * // selects item if its text is equal to "test" using predicate function
        * dropdownlist.select(function(dataItem) {
        *     return dataItem.text === "test";
        * });
        */
        select: function(li) {
            var that = this,
                element = that.element[0],
                current = that._current,
                data = that._data(),
                value,
                text,
                idx;

            li = that._get(li);

            if (li && li[0] && !li.hasClass(SELECTED)) {
                if (current) {
                    current.removeClass(SELECTED);
                }

                idx = ui.List.inArray(li[0], that.ul[0]);
                if (idx > -1) {
                    data = data[idx];
                    text = that._text(data);
                    value = that._value(data);

                    that.text(text);
                    that._accessor(value != undefined ? value : text, idx);
                    that.current(li.addClass(SELECTED));
                }
            }
        },

        /**
        * Gets/Sets the text of the dropdownlist.
        * @param {String} text The text to set.
        * @returns {String} The text of the dropdownlist.
        * @example
        * var dropdownlist = $("#dropdownlist").data("kendoDropDownList");
        *
        * // get the text of the dropdownlist.
        * var text = dropdownlist.text();
        */
        text: function (text) {
            var span = this.span;

            if (text !== undefined) {
                span.text(text);
            } else {
                return span.text();
            }
        },

        /**
        * Gets/Sets the value of the dropdownlist. The value will not be set if there is no item with such value. If value is undefined, text of the data item is used.
        * @param {String} value The value to set.
        * @returns {String} The value of the dropdownlist.
        * @example
        * var dropdownlist = $("#dropdownlist").data("kendoDropDownList");
        *
        * // get the value of the dropdownlist.
        * var value = dropdownlist.value();
        *
        * // set the value of the dropdownlist.
        * dropdownlist.value("1"); //looks for item which has value "1"
        */
        value: function(value) {
            var that = this,
                idx,
                element = that.element;

            if (value !== undefined) {
                idx = that._index(value);

                that.select(idx > -1 ? idx : 0);
                that._old = that._accessor();
            } else {
                return that._accessor();
            }
        },

        _accept: function(li) {
            this._focus(li);
        },

        _data: function() {
            var that = this,
                options = that.options,
                optionLabel = options.optionLabel,
                textField = options.dataTextField,
                valueField = options.dataValueField,
                data = that.dataSource.view(),
                length = data.length,
                first = optionLabel,
                idx = 0;

            if (optionLabel && length) {
                if (textField) {
                    first = {};
                    first[textField] = optionLabel;

                    if (valueField) {
                        first[valueField] = "";
                    }
                }

                first = [first];

                for (; idx < length; idx++) {
                    first.push(data[idx]);
                }
                data = first;
            }

            return data;
        },

        _keydown: function(e) {
            var that = this,
                key = e.keyCode,
                keys = kendo.keys,
                ul = that.ul[0];

            that._move(e);

            if (key === keys.HOME) {
                e.preventDefault();
                that.select(ul.firstChild);
            } else if (key === keys.END) {
                e.preventDefault();
                that.select(ul.lastChild);
            }
        },

        _keypress: function(e) {
            var that = this;

            setTimeout(function() {
                that._word += String.fromCharCode(e.keyCode || e.charCode);
                that._search();
            }, 0);
        },

        _search: function() {
            var that = this;
            clearTimeout(that._typing);

            that._typing = setTimeout(function() {
                that._word = "";
            }, that.options.delay);

            that.search(that._word);
        },

        _span: function() {
            var that = this,
                wrapper = that.wrapper,
                SELECTOR = ".k-input",
                span;

            span = wrapper.find(SELECTOR);

            if (!span[0]) {
                wrapper.append('<span class="k-dropdown-wrap k-state-default"><span class="k-input">&nbsp;</span><span class="k-select"><span class="k-icon k-arrow-down">select</span></span></span>')
                       .append(that.element);

                span = wrapper.find(SELECTOR);
            }

            that.span = span;
            that._arrow = wrapper.find(".k-icon");
            that._inputWrapper = $(wrapper[0].firstChild)
        },

        _wrapper: function() {
            var that = this,
                element = that.element,
                DOMelement = element[0],
                TABINDEX = "tabIndex",
                wrapper;

            wrapper = element.parent();

            if (!wrapper.is("span.k-widget")) {
                wrapper = element.wrap("<span />").parent();
            }

            if (!wrapper.attr(TABINDEX)) {
                wrapper.attr(TABINDEX, 0);
            }

            wrapper[0].style.cssText = DOMelement.style.cssText;
            element.hide();

            that._focused = that.wrapper = wrapper
                              .addClass("k-widget k-dropdown k-header")
                              .addClass(DOMelement.className);
        }
    });

    ui.plugin(DropDownList);
})(jQuery);
