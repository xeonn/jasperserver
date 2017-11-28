define(["require","jquery","underscore","bundle!all","resource.locate","dataSource/view/CustomDataSourceView","dataSource/fileDataSource/TextDataSourceModel","dataSource/fileDataSource/enum/delimitersTextDataSource","dataSource/fileDataSource/enum/characterEncodings","dataSource/fileDataSource/enum/fileSourceTypes","text!dataSource/fileDataSource/template/backboneTemplate.htm","text!dataSource/fileDataSource/template/fileLocationTemplate.htm","text!dataSource/fileDataSource/template/filePropertiesTemplate.htm","text!common/templates/components.pickers.htm","text!dataSource/fileDataSource/template/previewTableTemplate.htm","css!dataSource/textDataSource.css"],function(e){{var t=(e("jquery"),e("underscore")),i=e("bundle!all"),o=e("resource.locate"),r=e("dataSource/view/CustomDataSourceView"),a=e("dataSource/fileDataSource/TextDataSourceModel"),s=e("dataSource/fileDataSource/enum/delimitersTextDataSource"),n=e("dataSource/fileDataSource/enum/characterEncodings"),l=e("dataSource/fileDataSource/enum/fileSourceTypes"),u=t.reduce(l,function(e,t){return"repository"===t.name&&e.push(t),e},[]),c=e("text!dataSource/fileDataSource/template/backboneTemplate.htm"),d=e("text!dataSource/fileDataSource/template/fileLocationTemplate.htm"),m=e("text!dataSource/fileDataSource/template/filePropertiesTemplate.htm"),h=e("text!common/templates/components.pickers.htm");e("text!dataSource/fileDataSource/template/previewTableTemplate.htm")}return e("css!dataSource/textDataSource.css"),r.extend({PAGE_TITLE_NEW_MESSAGE_CODE:"resource.datasource.text.page.title.new",PAGE_TITLE_EDIT_MESSAGE_CODE:"resource.datasource.text.page.title.edit",modelConstructor:a,browseButton:!1,events:function(){var e=t.extend({},r.prototype.events);return e["change [name=fileSourceType]"]="changeFileSourceType",e},initialize:function(){r.prototype.initialize.apply(this,arguments),this.listenTo(this.model,"change:serverFileName",this.adjustFileSystemConnectButton),this.listenTo(this.model,"change:serverAddress",this.adjustFtpServerConnectButton),this.listenTo(this.model,"change:serverPath",this.adjustFtpServerConnectButton),this.listenTo(this.model,"change:ftpsPort",this.adjustFtpServerConnectButton),this.listenTo(this.model,"change:fieldDelimiter",this.adjustFieldDelimiterSection),this.listenTo(this.model,"change:rowDelimiter",this.adjustRowDelimiterSection),this.listenTo(this.model,"change",this.adjustPreviewButton),this.listenTo(this.model,"sourceFileIsOK",this.sourceFileIsOK),this.listenTo(this.model,"sourceFileCantBeParsed",this.sourceFileCantBeParsed)},changeFileSourceType:function(){t.defer(t.bind(function(){this.renderFileLocationSection()},this))},render:function(){return this.$el.empty(),this.renderTextDataSourceSection(),this},templateData:function(){return t.extend(r.prototype.templateData.apply(this,arguments),{fileSourceTypeOptions:u,fieldDelimiterOptions:s,rowDelimiterOptions:s,encodingOptions:n})},renderTextDataSourceSection:function(){this.$el.append(t.template(c,this.templateData())),this.renderFileLocationSection(),this.renderFilePropertiesSection()},renderFileLocationSection:function(){this.renderOrAddAnyBlock(this.$el.find("[name=textDataSourceFieldsContainer]"),t.template(d,this.templateData())),this.browseButton&&(this.browseButton.remove(),this.browseButton=!1),"repository"===this.model.get("fileSourceType")&&(this.browseButton=o.initialize({i18n:i,template:h,resourceInput:this.$el.find("[name=repositoryFileName]")[0],browseButton:this.$el.find("[name=repositoryBrowserButton]")[0],providerId:"contentResourceTreeDataProvider",dialogTitle:i["resource.Add.Files.Title"],selectLeavesOnly:!0,onChange:t.bind(function(e){this.model.set("repositoryFileName",e),this.model.validate({repositoryFileName:e})},this)})),this.adjustFileSystemConnectButton(),this.adjustFtpServerConnectButton()},adjustFileSystemConnectButton:function(){var e=this.model.isValid("serverFileName");this._adjustButton("serverFileSystemConnectToServer",e)},adjustFtpServerConnectButton:function(){var e=this.model.isValid(["serverAddress","serverPath","ftpsPort"]);this._adjustButton("ftpConnectToServer",e)},renderFilePropertiesSection:function(){this.renderOrAddAnyBlock(this.$el.find("[name=textDataSourceFieldsContainer]"),t.template(m,this.templateData())),this.adjustFieldDelimiterSection(),this.adjustRowDelimiterSection(),this.adjustPreviewButton()},adjustFieldDelimiterSection:function(){this._adjustSection("fieldDelimiter",{regex:"fieldDelimiterRegexInput",plugin:"fieldDelimiterPluginInput",other:"fieldDelimiterOtherInput"})},adjustRowDelimiterSection:function(){this._adjustSection("rowDelimiter",{regex:"rowDelimiterRegexInput",plugin:"rowDelimiterPluginInput",other:"rowDelimiterOtherInput"})},adjustPreviewButton:function(){var e="repository"===this.model.get("fileSourceType")&&this.model.isValid("repositoryFileName"),t="serverFileSystem"===this.model.get("fileSourceType")&&this.model.isValid("serverFileName"),i="ftpServer"===this.model.get("fileSourceType")&&this.model.isValid(["serverAddress","serverPath","ftpsPort"]),o=e||t||i;this._adjustButton("previewDataSource",o)},_adjustButton:function(e,t){var i=this.$el.find("[name="+e+"]");t?i.removeAttr("disabled"):i.attr("disabled","disabled")},_adjustSection:function(e,i){var o=this;t.each(i,function(t,i){var r=o.$el.find("[name="+t+"]");r.toggleClass("hidden",!(o.model.get(e)===i))})},sourceFileIsOK:function(){this.fieldIsValid(this,"repositoryFileName","name")},sourceFileCantBeParsed:function(){this.fieldIsInvalid(this,"repositoryFileName",i["resource.file.cantBeProcessed"],"name")}})});