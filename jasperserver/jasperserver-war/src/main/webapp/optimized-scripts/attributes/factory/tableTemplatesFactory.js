define(["require","text!attributes/templates/attributesDesignerTemplate.htm","text!attributes/templates/attributesViewerTemplate.htm","text!attributes/templates/emptyViewerTemplate.htm"],function(t){var e=t("text!attributes/templates/attributesDesignerTemplate.htm"),a=t("text!attributes/templates/attributesViewerTemplate.htm"),r=t("text!attributes/templates/emptyViewerTemplate.htm");return function(t){return t=t||{},t.readOnly?t.empty?r:a:e}});