Spring = {};

Spring.debug = true;

Spring.decorations = {};

Spring.decorations.applied = false;

Spring.initialize = function(){
    Spring.applyDecorations();
};

Spring.addDecoration = function(/*Object*/decoration){
    //Spring.decorations.push(decoration);

    if (!Spring.decorations[decoration.elementId]) {
        Spring.decorations[decoration.elementId] = [];
        Spring.decorations[decoration.elementId].push(decoration);
    } else {
        var replaced = false;
        for(var i = 0; i<Spring.decorations[decoration.elementId].length; i++) {
            var existingDecoration = Spring.decorations[decoration.elementId][i];
            if(existingDecoration.equals(decoration)) {
                if (existingDecoration.cleanup != undefined) {
                    existingDecoration.cleanup();
                }
                Spring.decorations[decoration.elementId][i] = decoration;
                replaced=true;
                break;
            }
        }
        if (!replaced) {
            Spring.decorations[decoration.elementId].push(decoration);
        }
    }

    if(Spring.decorations.applied) {
        decoration.apply();
    }
};

Spring.applyDecorations = function(){
    if (!Spring.decorations.applied) {
        for (var elementId in Spring.decorations) {
            for (var x = 0; x < Spring.decorations[elementId].length; x++) {
                Spring.decorations[elementId][x].apply();
            }
        }
        Spring.decorations.applied = true;
    }
};

Spring.validateAll = function(){
    var valid = true;
    for (var elementId in Spring.decorations) {
        for (var x = 0; x < Spring.decorations[elementId].length; x++) {
            if (Spring.decorations[elementId][x].widget && !Spring.decorations[elementId][x].validate()) {
                valid = false;
            }
        }
    }
    return valid;
};

Spring.validateRequired = function(){
    var valid = true;
    for (var elementId in Spring.decorations) {
        for (var x = 0; x < Spring.decorations[elementId].length; x++) {
            if (Spring.decorations[elementId][x].widget && Spring.decorations[elementId][x].isRequired() &&
                !Spring.decorations[elementId][x].validate()) {
                valid = false;
            }
        }
    }
    return valid;
};

Spring.AbstractElementDecoration = function(){};

Spring.AbstractElementDecoration.prototype = {

    elementId : "",
    widgetType : "",
    widgetModule : "",
    widget : null,
    widgetAttrs : {},

    apply : function(){},

    validate : function(){},

    isRequired : function(){},

    equals : function(/*Object*/other){}
};

Spring.AbstractValidateAllDecoration = function(){};

Spring.AbstractValidateAllDecoration.prototype = {

    event : "",
    elementId : "",

    apply : function() {},

    cleanup : function(){},

    handleEvent : function(event){},

    equals : function(/*Object*/other){}
};
