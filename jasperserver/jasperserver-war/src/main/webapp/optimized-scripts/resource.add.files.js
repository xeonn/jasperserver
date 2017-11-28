var addFileResource={PAGE_ID:"addResourceFile",TYPE_ID:"fileType",PATH_ID:"filePath",LABEL_ID:"addFileInputResourceLabelID",RESOURCE_ID_ID:"addFileInputResourceID",DESCRIPTION_ID:"addFileInputDescription",FOLDER_URI_ID:"folderUri",SAVE_BUTTON_ID:"save",typeToExtMap:{accessGrantSchema:["xml"],css:["css"],font:["ttf"],img:["jpg","jpeg","gif","bmp","png"],jar:["jar"],jrxml:["jrxml"],olapMondrianSchema:["xml"],prop:["properties"],jrtx:["jrtx"],xml:["xml","agxml"],config:["config"],contentResource:["docx","doc","ppt","pptx","xls","xlsx","ods","odt","odp","pdf","rtf","html","txt","csv"]},_canGenerateId:!0,initialize:function(e){this._form=$(this.PAGE_ID).select("form")[0],this._type=$(this.TYPE_ID),this._path=$(this.PATH_ID),this._label=$(this.LABEL_ID),this._resourceId=$(this.RESOURCE_ID_ID),this._description=$(this.DESCRIPTION_ID),this._folderUri=$(this.FOLDER_URI_ID),this._saveButton=$(this.SAVE_BUTTON_ID),this._isEditMode=e.isEditMode,this._type.validationEntry={validator:this._typeValidator.bind(this),element:this._type,onValid:function(){if(!this._manual){var e=this._getFileNameFromPath(this._path.getValue());this._label.value=e,this._resourceId.readOnly||(this._resourceId.value=resource.generateResourceId(this._label.getValue()))}}.bind(this)},this._path.validator=this._pathValidator.bind(this),this._label.validator=resource.labelValidator.bind(this),this._resourceId.validator=resource.resourceIdValidator.bind(this),this._description.validator=resource.descriptionValidator.bind(this),resourceLocator.initialize({resourceInput:"folderUri",browseButton:"browser_button",treeId:"addFileTreeRepoLocation",providerId:"repositoryExplorerTreeFoldersProvider",dialogTitle:resource.messages["resource.Add.Files.Title"]}),this._initEvents()},_initEvents:function(){this._saveButton.observe("click",function(e){this._isDataValid()||e.stop()}.bindAsEventListener(this)),this._type.observe("change",function(){this._validateTypeAndPath()}.bindAsEventListener(this)),this._path.observe("change",function(){this._validateTypeAndPath()}.bindAsEventListener(this)),this._form&&new Form.Observer(this._form,.3,function(){this._folderUri.getValue().blank()?buttonManager.disable("save"):buttonManager.enable("save")}.bindAsEventListener(this)),this._form.observe("keyup",function(e){var t=e.element(),i=[this._label,this._resourceId,this._description];i.include(t)&&(ValidationModule.validate(resource.getValidationEntries([t])),t==this._resourceId&&this._resourceId.getValue()!=resource.generateResourceId(this._label.getValue())&&(this._canGenerateId=!1),t==this._label&&!this._isEditMode&&this._canGenerateId&&(this._resourceId.setValue(resource.generateResourceId(this._label.getValue())),ValidationModule.validate(resource.getValidationEntries([this._resourceId])))),this._manual=!0}.bindAsEventListener(this)),this._form.observe("keydown",function(e){var t=matchAny(e.element(),["#"+this.DESCRIPTION_ID],!0);t||13==e.keyCode&&this._saveButton.focus()}.bindAsEventListener(this))},_isDataValid:function(){var e=[this._label,this._resourceId,this._description,this._path,this._type];return ValidationModule.validate(resource.getValidationEntries(e))},_validateTypeAndPath:function(){var e=[this._type,this._path];ValidationModule.validate(resource.getValidationEntries(e))},_typeValidator:function(e){var t=!0,i="",s={isValid:t,errorMessage:i},r=this._getExtension();if(r.blank())return s;var a=this._getTypesForExtension(r);return a.include(e)||(s.errorMessage=resource.messages.typeIsNotValid,s.isValid=!1),s},_pathValidator:function(e){var t=!0,i="";return!this._isEditMode&&e.blank()&&(i=resource.messages.pathIsEmpty,t=!1),{isValid:t,errorMessage:i}},_getTypesForExtension:function(e){var t=[];for(var i in this.typeToExtMap)this.typeToExtMap[i].include(e.toLowerCase())&&t.push(i);return t},_getExtension:function(){var e=this._path.getValue();if(e.blank())return"";var t=e.lastIndexOf(".");return-1==t?"":e.substr(t+1)},_getFileNameFromPath:function(e){var t=e.lastIndexOf("\\")>0?e.lastIndexOf("\\")+1:0;return e.substring(t,e.length)}};"undefined"==typeof require&&document.observe("dom:loaded",function(){addFileResource.initialize(localContext.initOptions)});