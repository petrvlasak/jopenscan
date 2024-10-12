(function ($) {
    $.wb_validation_onblur = {
        showError: function ($element, message) {
            var $validation = $.wb_validation;
            var $selfOrParent = $element;
            if ($validation.config.appendToParent) {
                $selfOrParent = $element.parent();
            }
            if ($selfOrParent.hasClass($validation.config.errorClass)) {
                $validation.hideError($element, $selfOrParent);
                $validation.removeErrorClass($element, $selfOrParent);
            }
            $validation.showError($element, $selfOrParent, message);
            $validation.addErrorClass($element, $selfOrParent);
        },
        hideError: function ($element) {
            var $validation = $.wb_validation;
            var $selfOrParent = $element;
            if ($validation.config.appendToParent) {
                $selfOrParent = $element.parent();
            }
            $validation.hideError($element, $selfOrParent);
            $validation.removeErrorClass($element, $selfOrParent);
        }
    };
})(jQuery);
