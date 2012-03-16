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
    var kendo = window.kendo,
        Observable = kendo.Observable,
        data = kendo.data,
        Model = data.Model,
        CHANGE = "change";

    function bindSelect(select, model) {
        select = $(select);

        var text = select.attr(kendo.attr("text-field")),
            value = select.attr(kendo.attr("value-field")),
            source = select.attr(kendo.attr("source"));

        if (model[source]) {
            source = model[source].call(model);
        } else {
            try {
                source = eval(source);
            } catch(e) {
                return;
            }
        }

        if ($.isArray(source)) {
            select.html(kendo.render(kendo.template('<option value="${'+ value +'}">${' + text + '}</option>'), source));
        }
    }

    var ModelViewBinder = Observable.extend({
        init: function(element, model, options) {
            var that = this;

            that.element = $(element);
            that.options = options || {};

            Observable.fn.init.call(that);

            that.model = model instanceof Model ? model : new (Model.define())(model);

            that.bind([CHANGE], that.options);

            var elements = that.element.find("input,select,textarea");
            if (!elements.length) {
                elements = that.element;
            }

            elements.bind(CHANGE, $.proxy(that._change, that))
                .each(function() {
                    var mapping = that._map(this);
                    if (mapping) {
                        mapping.bindView();
                    }
                });
        },

        bindModel: function() {
            var that = this,
                valid = true;

            that.element.find("input,select,textarea")
                .each(function() {
                    var mapping = that._map(this);
                    if (mapping) {
                        return valid = mapping.bindModel();
                    }
                });

            return valid;
        },

        _change: function(e) {
            var that = this,
                mapping = that._map(e.target);

            if (mapping) {
                mapping.bindModel();
            }
        },

        _map: function(target) {
            var that = this,
                model = that.model,
                options = that.options,
                element = $(target),
                field = element.attr(kendo.attr("field")) || element.attr("name"),
                setting = options[field] || {};

            if (field) {
                return {
                    bindView: function() {
                        var value = model.get(field);

                        if (setting.format) {
                            value = setting.format(value);
                        }

                        if (target.nodeName.toLowerCase() === "select") {
                            bindSelect(target, model);
                        }

                        if (element.is(":checkbox")) {
                            element.attr("checked", value === true);
                        } else {
                            element.val(value);
                        }
                    },
                    bindModel: function() {
                        var value = element.is(":checkbox") ? element.is(":checked") : target.value,
                            values = {};

                        if (setting.parse) {
                           value = setting.parse(value);
                        }

                        values[field] = value;

                        if (!that.trigger(CHANGE, { values: values })) {
                            model.set(field, value);
                            return true;
                        }

                        return false;
                    }
                }
            }
        }
    });

    data.ModelViewBinder = ModelViewBinder;
})(jQuery);
