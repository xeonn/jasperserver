define(["require","exports","module","inputControl/collection/InputControlCollection","inputControl/view/InputControlCollectionView","logger"],function(o,t,n){var e=o("inputControl/collection/InputControlCollection"),i=o("inputControl/view/InputControlCollectionView"),l=o("logger").register(n),r=new e([],{contextPath:"http://localhost:8080/jasperserver-pro",resourceUri:"/organizations/organization_1/adhoc/topics/Cascading_multi_select_topic",container:"#inputControlsContainer"}),c=new i({collection:r});r.fetch(),l.debug("init returned: ",r,c)});