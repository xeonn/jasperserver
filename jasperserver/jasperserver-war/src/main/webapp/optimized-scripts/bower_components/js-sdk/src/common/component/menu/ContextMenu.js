define(["require","./Menu","jquery","json3","underscore"],function(t){"use strict";var e=t("./Menu"),i=t("jquery"),o=t("json3"),n=t("underscore");return e.extend({constructor:function(t,i){e.call(this,t,i),this.topPadding=i&&i.topPadding||5,this.leftPadding=i&&i.leftPadding||5,n.bindAll(this,"_tryHide")},_tryHide:function(){this.hide(),i(document.body).off("click.contextMenu",this._tryHide)},show:function(t,s){if(!t||!n.isNumber(t.top)||!n.isNumber(t.left))throw new Error("Required params (top, left) missing: "+o.stringify(t));i(document.body).on("click.contextMenu",this._tryHide);var r=t.top,d=t.left,h=this.topPadding,f=this.leftPadding,u=i("body"),l=this.$el.height(),c=this.$el.width(),p=s?s.height():u.height(),y=s?s.width():u.width(),a=s?s.offset():u.offset(),g=p-t.top,m=y-t.left;return l>g&&(r=t.top-l-h,r<a.top&&(r+=a.top-r+h),r=0>r?p/2-l/2:r),c>m&&(d=t.left-c-f,d<a.left&&(d+=a.left-d+f),d=0>d?y/2-c/2:d),n.extend(this,{top:r,left:d}),this.$el.css({top:this.top,left:this.left}),e.prototype.show.apply(this,arguments)},remove:function(){i(document.body).off("click.contextMenu",this._tryHide),e.prototype.remove.apply(this,arguments)}})});