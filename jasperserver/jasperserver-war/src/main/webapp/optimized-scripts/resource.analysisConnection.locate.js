var resourceOLAPLocate={initialize:function(){resourceOLAPLocate.foldersPanel.initialize()}};resourceOLAPLocate.foldersPanel={_treeId:"OLAPTreeRepoLocation",_uri:"/",initialize:function(){return this.tree=new dynamicTree.createRepositoryTree(this.getTreeId(),{providerId:"OLAPTreeDataProvider"}),this.tree.observe("server:error",function(){window.console&&console.log("Server load error.")}),this.tree.observe("tree:loaded",function(){}.bind(this)),this.tree.observe("leaf:selected",function(e){this._uri=e.memo.node.param.uri,$("resourceUri").setValue(this._uri)}.bindAsEventListener(this)),this.tree.showTree(1),this},getTreeId:function(){return this._treeId},selectFolder:function(e){this.tree.openAndSelectNode(e)},getSelectedFolderUri:function(){return this._selectedFolderUri}},document.observe("dom:loaded",function(){resourceOLAPLocate.initialize()});