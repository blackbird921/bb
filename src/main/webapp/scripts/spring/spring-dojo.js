dojo.declare("Spring.DefaultEquals", null, {
    equals : function(/*Object*/other){
        if (other.declaredClass && other.declaredClass == this.declaredClass) {
            return true;
        }else{
            return false;
        }
    }
});

dojo.declare("Spring.ElementDecoration", [Spring.AbstractElementDecoration, Spring.DefaultEquals], {
    constructor : function(config) {
        this.widgetAttrs = {};
        this.copyFields = new Array('name', 'value', 'type', 'checked', 'selected', 'readOnly', 'disabled', 'alt', 'maxLength', 'class', 'title');
        dojo.mixin(this, config);
        this.element = dojo.byId(this.elementId);
        this.elementId = dojo.isString(this.elementId) ? this.elementId : this.elementId.id;
        if(this.widgetModule == "") {
            this.widgetModule = this.widgetType;
        }
    },

    apply : function(){
        if (dijit.byId(this.elementId)) {
            dijit.byId(this.elementId).destroyRecursive(false);
        }

        if (!this.element) {
            console.error("Could not apply " + this.widgetType + " decoration.  Element with id '" + this.elementId + "' not found in the DOM.");
        }
        else {
            /*
             * dijit.form.DateTextBox uses locale information when displaying a date and a
             * fixed date format when parsing or serializing date values coming from the
             * server-side. There is no way configure it to use a single date pattern,
             * which is the reason for the code below.
             */
            var datePattern = this.widgetAttrs['datePattern'];
            if (datePattern && this.widgetType == 'dijit.form.DateTextBox') {
                if (!this.widgetAttrs['value']) {
                    // Help dijit.form.DateTextBox parse the server side date value.
                    this.widgetAttrs['value'] = dojo.date.locale.parse(this.element.value, {selector : "date", datePattern : datePattern});
                }
                if (!this.widgetAttrs['serialize']) {
                    // Help dijit.form.DateTextBox format the date to send to the server side.
                    this.widgetAttrs['serialize'] = function(d, options){
                        return dojo.date.locale.format(d, {selector : "date", datePattern : datePattern});
                    }
                }
                // Add a constraint that specifies the date pattern to use when displaying a date
                // but don't interfere with any constraints that may have been specified.
                if (!this.widgetAttrs['constraints']) {
                    this.widgetAttrs['constraints'] = {};
                }
                if (!this.widgetAttrs['constraints'].datePattern) {
                    this.widgetAttrs['constraints'].datePattern = datePattern;
                }
            }
            for (var copyField in this.copyFields) {
                copyField = this.copyFields[copyField];
                if (!this.widgetAttrs[copyField] && this.element[copyField] &&
                    (typeof this.element[copyField] != 'number' ||
                        (typeof this.element[copyField] == 'number' && this.element[copyField] >= 0))) {
                    this.widgetAttrs[copyField] = this.element[copyField];
                }
            }
            if(this.element['style'] && this.element['style'].cssText){
                this.widgetAttrs['style'] = this.element['style'].cssText;
            }
            dojo.require(this.widgetModule);
            var widgetConstructor = dojo.eval(this.widgetType);
            this.widget = new widgetConstructor(this.widgetAttrs, this.element);
            this.widget.startup();
        }
        //return this to support method chaining
        return this;
    },

    validate : function(){
        if (!this.widget.isValid) {
            // some widgets cannot be validated
            return true;
        }
        var isValid = this.widget.isValid(false);
        if (!isValid) {
            this.widget.state = "Error";
            this.widget._setStateClass();
        }
        return isValid;
    }
});

dojo.declare("Spring.ValidateAllDecoration", [Spring.AbstractValidateAllDecoration, Spring.DefaultEquals], {
    constructor : function(config) {
        this.originalHandler = null;
        this.connection = null;
        dojo.mixin(this, config);
    },

    apply : function() {
        var element = dojo.byId(this.elementId);
        if (!element) {
            console.error("Could not apply ValidateAll decoration.  Element with id '" + this.elementId + "' not found in the DOM.");
        } else {
            this.originalHandler = element[this.event];
            var context = this;
            element[this.event] = function(event){
                context.handleEvent(event, context);
            };
        }
        return this;
    },

    cleanup : function(){
        dojo.disconnect(this.connection);
    },

    handleEvent : function(event, context){
        if (!Spring.validateAll()) {
            dojo.publish(this.elementId+"/validation", [false]);
            dojo.stopEvent(event);
        } else {
            dojo.publish(this.elementId+"/validation", [true]);
            if(dojo.isFunction(context.originalHandler)) {
                var result = context.originalHandler(event);
                if (result == false) {
                    dojo.stopEvent(event);
                }
            }
        }
    }
});
