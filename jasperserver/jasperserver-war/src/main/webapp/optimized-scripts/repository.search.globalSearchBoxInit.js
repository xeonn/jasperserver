var globalSearchBox={_searchBox:null,_containerId:"globalSearch",_searchInputId:"searchInput",initialize:function(){this._searchBox=new SearchBox({id:this._containerId}),this._searchBox.onSearch=function(a){$(this._searchInputId).setValue(a),primaryNavModule.navigationPaths.search.params+="&searchText="+encodeUriParameter(a),primaryNavModule.navigationOption("search")}.bind(this)},setText:function(a){this._searchBox.setText(a)}};