define(["require","underscore"],function(e){var r=e("underscore");return{generateUniqueName:function(e){return e+(new Date).getTime()+"_"+String.fromCharCode.apply(null,r.times(4,r.partial(r.random,97,122)))}}});