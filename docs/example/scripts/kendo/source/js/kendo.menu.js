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
     * @name kendo.ui.Menu.Description
     *
     * @section
     *  <p>
     *      The Menu widget displays hierarchical data as a multi-level menu. Menus provide
     *      rich styling for unordered lists of items, and can be used for both navigation and
     *      executing JavaScript commands. Items can be defined and initialized from HTML, or
     *      the rich Menu API can be used to add and remove items.
     *  </p>
     *
     *  <h3>Getting Started</h3>
     * @exampleTitle Create a simple HTML hierarchical list of items
     * @example
     * <ul id="menu">
     *     <li>Item 1
     *         <ul>
     *             <li>Item 1.1</li>
     *             <li>Item 1.2</li>
     *         </ul>
     *     </li>
     *     <li>Item 2</li>
     * </ul>
     *
     * @exampleTitle Initialize Kendo Menu using jQuery selector
     * @example
     * var menu = $("#menu").kendoMenu();
     *
     * @section
     *  <h3>Customizing Menu Animations</h3>
     *  <p>
     *      By default, the Menu uses a slide animation to expand and reveal sub-items as the
     *      mouse hovers. Animations can be easily customized using configuration properties, changing
     *      the animation style and delay. Menu items can also be configured to open on click instead of on hover.
     *  </p>
     *
     * @exampleTitle Changing Menu animation and open behavior
     * @example
     * $("#menu").kendoMenu({
     *      animation: {
     *        open : {effects: fadeIn},
     *        hoverDelay: 500
     *      },
     *      openOnClick: true
     *  });
     *
     *  @section
     *   <h3>Dynamically configuring Menu items</h3>
     *   <p>
     *          The Menu API provides several methods for dynamically adding or removing Items.
     *          To add items, provide the new item as a JSON object along with a reference item that
     *          will be used to determine the placement in the hierarchy.
     *  </p>
     *  <br/>
     *  <p>
     *          A reference item is simply a target Menu Item HTML element that already exists in
     *          the Menu. Any valid jQuery selector can be used to obtain a reference to the target
     *          item. For examples, see the <a href="../menu/api.html" title="Menu API demos">Menu API demos</a>.
     *          Removing an item only requires a reference to the target element that should be removed.
     *  </p>
     * @exampleTitle Dynamically add a new root Menu item
     * @example
     *  var menu = $("#menu").kendoMenu().data("kendoMenu");
     *
     *  menu.insertAfter(
     *      { text: "New Menu Item" },
     *      menu.element.children("li:last")
     *  );
     *
     */
    var kendo = window.kendo,
        ui = kendo.ui,
        touch = kendo.support.touch,
        extend = $.extend,
        proxy = $.proxy,
        each = $.each,
        template = kendo.template,
        Widget = ui.Widget,
        excludedNodesRegExp = /^(ul|a|div)$/i,
        IMG = "img",
        OPEN = "open",
        MENU = "k-menu",
        LINK = "k-link",
        LAST = "k-last",
        CLOSE = "close",
        CLICK = "click",
        TIMER = "timer",
        FIRST = "k-first",
        IMAGE = "k-image",
        EMPTY = ":empty",
        SELECT = "select",
        ZINDEX = "zIndex",
        MOUSEENTER = "mouseenter",
        MOUSELEAVE = "mouseleave",
        KENDOPOPUP = "kendoPopup",
        SLIDEINRIGHT = "slideIn:right",
        DEFAULTSTATE = "k-state-default",
        DISABLEDSTATE = "k-state-disabled",
        disabledSelector = ".k-item.k-state-disabled",
        itemSelector = ".k-item:not(.k-state-disabled)",
        linkSelector = ".k-item:not(.k-state-disabled) > .k-link",

        templates = {
            group: template(
                "<ul class='#= groupCssClass(group) #'#= groupAttributes(group) #>" +
                    "#= renderItems(data) #" +
                "</ul>"
            ),
            itemWrapper: template(
                "<#= tag(item) # class='#= textClass(item) #'#= textAttributes(item) #>" +
                    "#= image(item) ##= sprite(item) ##= text(item) #" +
                    "#= arrow(data) #" +
                "</#= tag(item) #>"
            ),
            item: template(
                "<li class='#= wrapperCssClass(group, item) #'>" +
                    "#= itemWrapper(data) #" +
                    "# if (item.items) { #" +
                    "#= subGroup({ items: item.items, menu: menu, group: { expanded: item.expanded } }) #" +
                    "# } #" +
                "</li>"
            ),
            image: template("<img class='k-image' alt='' src='#= imageUrl #' />"),
            arrow: template("<span class='#= arrowClass(item, group) #'></span>"),
            sprite: template("<span class='k-sprite #= spriteCssClass #'></span>"),
            empty: template("")
        },

        rendering = {
            /** @ignore */
            wrapperCssClass: function (group, item) {
                var result = "k-item",
                    index = item.index;

                if (item.enabled === false) {
                    result += " k-state-disabled";
                } else {
                    result += " k-state-default";
                }

                if (group.firstLevel && index == 0) {
                    result += " k-first"
                }

                if (index == group.length-1) {
                    result += " k-last";
                }

                return result;
            },
            /** @ignore */
            textClass: function(item) {
                return LINK;
            },
            /** @ignore */
            textAttributes: function(item) {
                return item.url ? " href='" + item.url + "'" : "";
            },
            /** @ignore */
            arrowClass: function(item, group) {
                var result = "k-icon";

                if (group.horizontal) {
                    result += " k-arrow-down";
                } else {
                    result += " k-arrow-right";
                }

                return result;
            },
            /** @ignore */
            text: function(item) {
                return item.encoded === false ? item.text : kendo.htmlEncode(item.text);
            },
            /** @ignore */
            tag: function(item) {
                return item.url ? "a" : "span";
            },
            /** @ignore */
            groupAttributes: function(group) {
                return group.expanded !== true ? " style='display:none'" : "";
            },
            /** @ignore */
            groupCssClass: function(group) {
                return "k-group";
            }
        };

    function getEffectOptions(item) {
        var parent = item.parent();
        return {
            effects: parent.hasClass(MENU) ? parent.hasClass(MENU + "-vertical") ? SLIDEINRIGHT : "slideIn:down" : SLIDEINRIGHT
        };
    }

    function contains(parent, child) {
        try {
            return $.contains(parent, child);
        } catch (e) {
            return false;
        }
    }

    function updateItemClasses (item) {
        item = $(item);

        item
            .children(IMG)
            .addClass(IMAGE);
        item
            .children("a")
            .addClass(LINK)
            .children(IMG)
            .addClass(IMAGE);
        item
            .filter(":not([disabled])")
            .addClass(DEFAULTSTATE);
        item
            .filter(".k-separator:empty")
            .append("&nbsp;");
        item
            .filter("li[disabled]")
            .addClass(DISABLEDSTATE)
            .removeAttr("disabled");
        item
            .children("a:focus")
            .parent()
            .addClass("k-state-active");

        if (!item.children("." + LINK).length) {
            item
                .contents()      // exclude groups, real links, templates and empty text nodes
                .filter(function() { return (!this.nodeName.match(excludedNodesRegExp) && !(this.nodeType == 3 && !$.trim(this.nodeValue))); })
                .wrapAll("<span class='" + LINK + "'/>");
        }

        updateArrow(item);
        updateFirstLast(item);
    }

    function updateArrow (item) {
        item = $(item);

        item.find(".k-icon").remove();

        item.filter(":has(.k-group)")
            .children(".k-link:not(:has([class*=k-arrow]))")
            .each(function () {
                var item = $(this),
                    parent = item.parent().parent();

                item.append("<span class='k-icon " + (parent.hasClass(MENU + "-horizontal") ? "k-arrow-down" : "k-arrow-next") + "'/>");
            });
    }

    function updateFirstLast (item) {
        item = $(item);

        item.filter(".k-first:not(:first-child)").removeClass(FIRST);
        item.filter(".k-last:not(:last-child)").removeClass(LAST);
        item.filter(":first-child").addClass(FIRST);
        item.filter(":last-child").addClass(LAST);
    }

    var Menu = Widget.extend({/** @lends kendo.ui.Menu.prototype */
        /**
         * Creates a Menu instance.
         * @constructs
         * @extends kendo.ui.Widget
         * @class Menu UI widget
         * @param {Selector} element DOM element
         * @param {Object} options Configuration options.
         * @option {Object} [animation] A collection of <b>Animation</b> objects, used to change default animations. A value of false will disable all animations in the widget.
         * @option {Animation} [animation.open] The animation that will be used when opening sub menus.
         * @option {Animation} [animation.close] The animation that will be used when closing sub menus.
         * @option {String} [orientation] <"horizontal"> Root menu orientation.
         * @option {Boolean} [openOnClick] <false> Specifies that the root sub menus will be opened on item click.
         * @option {Number} [hoverDelay] <100> Specifies the delay in ms before the menu is opened/closed - used to avoid accidental closure on leaving.
         */
        init: function(element, options) {
            element = $(element);
            var that = this;

            Widget.fn.init.call(that, element, options);

            options = that.options;

            if (that.element.is(EMPTY)) {
                that.element.append($(Menu.renderGroup({
                    items: options.dataSource,
                    group: {
                        firstLevel: true,
                        horizontal: that.element.hasClass(MENU + "-horizontal"),
                        expanded: true
                    },
                    menu: {}
                })).children());
            }

            that._updateClasses();

            if (options.animation === false) {
                options.animation = { open: { show: true, effects: {} }, close: { hide:true, effects: {} } };
            }

            that.nextItemZIndex = 100;

            element.delegate(disabledSelector, CLICK, false);

            element.delegate(itemSelector, MOUSEENTER, proxy(that._mouseenter, that))
                   .delegate(itemSelector, MOUSELEAVE, proxy(that._mouseleave, that))
                   .delegate(itemSelector, CLICK, proxy(that._click , that));

            element.delegate(linkSelector, MOUSEENTER + " " + MOUSELEAVE, that._toggleHover);

            $(document).click(proxy( that._documentClick, that ));
            that.clicked = false;

            that.bind([
                /**
                 * Fires before a sub menu gets opened.
                 * @name kendo.ui.Menu#open
                 * @event
                 * @param {Event} e
                 * @param {Element} e.item The opened item
                 */
                OPEN,
                /**
                 * Fires after a sub menu gets closed.
                 * @name kendo.ui.Menu#close
                 * @event
                 * @param {Event} e
                 * @param {Element} e.item The closed item
                 */
                CLOSE,
                /**
                 * Fires when a menu item gets selected.
                 * @name kendo.ui.Menu#select
                 * @event
                 * @param {Event} e
                 * @param {Element} e.item The selected item
                 */
                SELECT
            ], that.options);
        },
        options: {
            name: "Menu",
            animation: {
                open: {
                    duration: 200,
                    show: true
                },
                close: { // if close animation effects are defined, they will be used instead of open.reverse
                    duration: 100,
                    show: false,
                    hide: true
                }
            },
            orientation: "horizontal",
            openOnClick: false,
            hoverDelay: 100
        },

        /**
         * Enables/disables a Menu item
         * @param {Selector} element Target element
         * @param {Boolean} enable Desired state
         */
        enable: function (element, enable) {
            this._toggleDisabled(element, enable !== false);
        },

        /**
         * Disables a Menu item
         * @param {Selector} element Target element
         */
        disable: function (element) {
            this._toggleDisabled(element, false);
        },

        /**
         * Appends a Menu item in the specified referenceItem's sub menu
         * @param {Selector} item Target item, specified as a JSON object. Can also handle an array of such objects.
         * @param {Item} referenceItem A reference item to append the new item in
         * @example
         * menu.append(
         *     [{
         *         text: "Item 1"
         *     },
         *     {
         *         text: "Item 2"
         *     }],
         *     referenceItem
         * );
         */
        append: function (item, referenceItem) {
            referenceItem = $(referenceItem);

            var inserted = this._insert(item, referenceItem, referenceItem.length ? referenceItem.find("> .k-group, .k-animation-container > .k-group") : null);

            each(inserted.items, function () {
                inserted.group.append(this);
                updateFirstLast(this);
            });

            updateArrow(referenceItem);
            updateFirstLast(inserted.group.find(".k-first, .k-last"));
        },

        /**
         * Inserts a Menu item before the specified referenceItem
         * @param {Selector} item Target item, specified as a JSON object. Can also handle an array of such objects.
         * @param {Selector} referenceItem A reference item to insert the new item before
         * @example
         * menu.insertBefore(
         *     [{
         *         text: "Item 1"
         *     },
         *     {
         *         text: "Item 2"
         *     }],
         *     referenceItem
         * );
         */
        insertBefore: function (item, referenceItem) {
            referenceItem = $(referenceItem);

            var inserted = this._insert(item, referenceItem, referenceItem.parent());

            each(inserted.items, function () {
                referenceItem.before(this);
                updateFirstLast(this);
            });

            updateFirstLast(referenceItem);
        },

        /**
         * Inserts a Menu item after the specified referenceItem
         * @param {Selector} item Target item, specified as a JSON object. Can also handle an array of such objects.
         * @param {Selector} referenceItem A reference item to insert the new item after
         * @example
         * menu.insertAfter(
         *     [{
         *         text: "Item 1"
         *     },
         *     {
         *         text: "Item 2"
         *     }],
         *     referenceItem
         * );
         */
        insertAfter: function (item, referenceItem) {
            referenceItem = $(referenceItem);

            var inserted = this._insert(item, referenceItem, referenceItem.parent());

            each(inserted.items, function () {
                referenceItem.after(this);
                updateFirstLast(this);
            });

            updateFirstLast(referenceItem);
        },

        _insert: function (item, referenceItem, parent) {
            var that = this;

            if (!referenceItem || !referenceItem.length) {
                parent = that.element;
            }

            var plain = $.isPlainObject(item),
                items,
                groupData = {
                    firstLevel: parent.hasClass(MENU),
                    horizontal: parent.hasClass(MENU + "-horizontal"),
                    expanded: true,
                    length: parent.children().length
                };

            if (referenceItem && !parent.length) {
                parent = $(Menu.renderGroup({ group: groupData })).appendTo(referenceItem);
            }

            if (plain || $.isArray(item)) { // is JSON
                items = $.map(plain ? [ item ] : item, function (value, idx) {
                            return $(Menu.renderItem({
                                group: groupData,
                                item: extend(value, { index: idx })
                            }));
                        });
            } else {
                items = $(item);

                updateItemClasses(items);
            }

            return { items: items, group: parent };
        },

        /**
         * Removes the specified Menu item/s from the Menu
         * @param {Selector} element Target item selector.
         * @example
         * menu.remove("#Item1");
         */
        remove: function (element) {
            element = $(element);

            var that = this,
                parent = element.parentsUntil(that.element, ".k-item"),
                group = element.parent("ul");

            element.remove();

            if (group && !group.children(".k-item").length) {
                var container = group.parent(".k-animation-container");
                container.length ? container.remove() : group.remove();
            }

            if (parent.length) {
                parent = parent.eq(0);

                updateArrow(parent);
                updateFirstLast(parent);
            }
        },

        /**
         * Opens the sub menu of the specified Menu item/s
         * @param {Selector} element Target item selector.
         * @example
         * menu.open("#Item1");
         */
        open: function (element) {
            var that = this;

            $(element).each(function () {
                var li = $(this);

                clearTimeout(li.data(TIMER));

                li.data(TIMER, setTimeout(function () {
                    var ul = li.find(".k-group:first:hidden"), popup;

                    if (ul[0]) {
                        li.data(ZINDEX, li.css(ZINDEX));
                        li.css(ZINDEX, that.nextItemZIndex ++);

                        popup = ul.data(KENDOPOPUP);
                        var parentHorizontal = li.parent().hasClass(MENU + "-horizontal");

                        if (!popup) {
                            popup = ul.kendoPopup({
                                origin: parentHorizontal ? "bottom left" : "top right",
                                position: "top left",
                                collision: parentHorizontal ? "fit" : "fit flip",
                                anchor: li,
                                appendTo: li,
                                animation: {
                                    open: extend( getEffectOptions(li), that.options.animation.open),
                                    close: that.options.animation.close
                                }
                            }).data(KENDOPOPUP);
                        }

                        popup.open();
                    }

                }, that.options.hoverDelay));
            });
        },

        /**
         * Closes the sub menu of the specified Menu item/s
         * @param {Selector} element Target item selector.
         * @example
         * menu.close("#Item1");
         */
        close: function (element) {
            var that = this;

            $(element).each(function () {
                var li = $(this);

                clearTimeout(li.data(TIMER));

                li.data(TIMER, setTimeout(function () {
                    var ul = li.find(".k-group:first:visible"), popup;
                    if (ul[0]) {
                        li.css(ZINDEX, li.data(ZINDEX));
                        li.removeData(ZINDEX);

                        popup = ul.data(KENDOPOPUP);
                        popup.close();
                    }
                }, that.options.hoverDelay));
            });
        },

        _toggleDisabled: function (element, enable) {
            $(element).each(function () {
                $(this)
                    .toggleClass(DEFAULTSTATE, enable)
                    .toggleClass(DISABLEDSTATE, !enable);
            });
        },

        _toggleHover: function(e) {
            var target = $(e.currentTarget);

            if (!target.parents("li." + DISABLEDSTATE).length) {
                target.toggleClass("k-state-hover", e.type == MOUSEENTER);
            }
        },

        _updateClasses: function() {
            var that = this;

            that.element.addClass("k-widget k-reset k-header " + MENU).addClass(MENU + "-" + that.options.orientation);

            var items = that.element
                            .find("ul")
                            .addClass("k-group")
                            .end()
                            .find("li")
                            .addClass("k-item");

            items.each(function () {
                updateItemClasses(this);
            });
        },

        _mouseenter: function (e) {
            var that = this,
                element = $(e.currentTarget),
                hasChildren = (element.children(".k-animation-container").length || element.children(".k-group").length);

            if (!that.options.openOnClick || that.clicked) {
                if (!contains(e.currentTarget, e.relatedTarget) && hasChildren) {
                    if (that.trigger(OPEN, { item: element[0] }) === false) {
                        that.open(element);
                    }
                }
            }

            if (that.options.openOnClick && that.clicked) {
                that.trigger(CLOSE, { item: element[0] });

                element.siblings().each(proxy(function (_, sibling) {
                    that.close(sibling);
                }, that));
            }
        },

        _mouseleave: function (e) {
            var that = this,
                element = $(e.currentTarget),
                hasChildren = (element.children(".k-animation-container").length || element.children(".k-group").length);

            if (!that.options.openOnClick && !contains(e.currentTarget, e.relatedTarget) && hasChildren) {
                if (that.trigger(CLOSE, { item: element[0] }) === false) {
                    that.close(element);
                }
            }
        },

        _click: function (e) {
            var that = this, openHandle;

            var element = $(e.currentTarget);

            if (element.hasClass(DISABLEDSTATE)) {
                e.preventDefault();
                return;
            }

            if (!e.handled) // We shouldn't stop propagation.
                that.trigger(SELECT, { item: element[0] });

            e.handled = true;

            if (!element.parent().hasClass(MENU) || (!that.options.openOnClick && !touch)) {
                return;
            }

            e.preventDefault();

            that.clicked = true;
            openHandle = element.children(".k-animation-container, .k-group").is(":visible") ? CLOSE : OPEN;

            that.trigger(openHandle, { item: element[0] });
            that[openHandle](element);
        },

        _documentClick: function (e) {
            var that = this;

            if (contains(that.element[0], e.target)) {
                return;
            }

            if (that.clicked) {
                that.clicked = false;
                that.close(that.element.find(".k-item>.k-animation-container:visible").parent());
            }
        }
    });

    // client-side rendering
    extend(Menu, {
        renderItem: function (options) {
            options = extend({ menu: {}, group: {} }, options);

            var empty = templates.empty,
                item = options.item,
                menu = options.menu;

            return templates.item(extend(options, {
                image: item.imageUrl ? templates.image : empty,
                sprite: item.spriteCssClass ? templates.sprite : empty,
                itemWrapper: templates.itemWrapper,
                arrow: item.items ? templates.arrow : empty,
                subGroup: Menu.renderGroup
            }, rendering));
        },

        renderGroup: function (options) {
            return templates.group(extend({
                renderItems: function(options) {
                    var html = "",
                        i = 0,
                        items = options.items,
                        len = items ? items.length : 0,
                        group = extend({ length: len }, options.group);

                    for (; i < len; i++) {
                        html += Menu.renderItem(extend(options, {
                            group: group,
                            item: extend({ index: i }, items[i])
                        }));
                    }

                    return html;
                }
            }, options, rendering));
        }
    });

    kendo.ui.plugin(Menu);

})(jQuery);
