(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-c84e9436"],{4949:function(t,a,n){},6768:function(t,a,n){"use strict";n.r(a);var e=function(){var t=this,a=t.$createElement,n=t._self._c||a;return n("el-row",{staticClass:"panel-group",attrs:{gutter:40}},[n("el-col",{staticClass:"card-panel-col",attrs:{xs:12,sm:12,lg:6}},[n("div",{staticClass:"card-panel",on:{click:function(a){return t.handleSetLineChartData("newVisitis")}}},[n("div",{staticClass:"card-panel-icon-wrapper icon-people"},[n("svg-icon",{attrs:{"icon-class":"peoples","class-name":"card-panel-icon"}})],1),t._v(" "),n("div",{staticClass:"card-panel-description"},[n("div",{staticClass:"card-panel-text"},[t._v("\n          New Visits\n        ")]),t._v(" "),n("count-to",{staticClass:"card-panel-num",attrs:{"start-val":0,"end-val":102400,duration:2600}})],1)])]),t._v(" "),n("el-col",{staticClass:"card-panel-col",attrs:{xs:12,sm:12,lg:6}},[n("div",{staticClass:"card-panel",on:{click:function(a){return t.handleSetLineChartData("messages")}}},[n("div",{staticClass:"card-panel-icon-wrapper icon-message"},[n("svg-icon",{attrs:{"icon-class":"message","class-name":"card-panel-icon"}})],1),t._v(" "),n("div",{staticClass:"card-panel-description"},[n("div",{staticClass:"card-panel-text"},[t._v("\n          Messages\n        ")]),t._v(" "),n("count-to",{staticClass:"card-panel-num",attrs:{"start-val":0,"end-val":81212,duration:3e3}})],1)])]),t._v(" "),n("el-col",{staticClass:"card-panel-col",attrs:{xs:12,sm:12,lg:6}},[n("div",{staticClass:"card-panel",on:{click:function(a){return t.handleSetLineChartData("purchases")}}},[n("div",{staticClass:"card-panel-icon-wrapper icon-money"},[n("svg-icon",{attrs:{"icon-class":"money","class-name":"card-panel-icon"}})],1),t._v(" "),n("div",{staticClass:"card-panel-description"},[n("div",{staticClass:"card-panel-text"},[t._v("\n          Purchases\n        ")]),t._v(" "),n("count-to",{staticClass:"card-panel-num",attrs:{"start-val":0,"end-val":9280,duration:3200}})],1)])]),t._v(" "),n("el-col",{staticClass:"card-panel-col",attrs:{xs:12,sm:12,lg:6}},[n("div",{staticClass:"card-panel",on:{click:function(a){return t.handleSetLineChartData("shoppings")}}},[n("div",{staticClass:"card-panel-icon-wrapper icon-shopping"},[n("svg-icon",{attrs:{"icon-class":"shopping","class-name":"card-panel-icon"}})],1),t._v(" "),n("div",{staticClass:"card-panel-description"},[n("div",{staticClass:"card-panel-text"},[t._v("\n          Shoppings\n        ")]),t._v(" "),n("count-to",{staticClass:"card-panel-num",attrs:{"start-val":0,"end-val":13600,duration:3600}})],1)])])],1)},i=[],s=n("ec1b"),r=n.n(s),o={components:{CountTo:r.a},methods:{handleSetLineChartData:function(t){this.$emit("handleSetLineChartData",t)}}},l=o,c=(n("afce"),n("2877")),u=Object(c["a"])(l,e,i,!1,null,"6723c96e",null);a["default"]=u.exports},afce:function(t,a,n){"use strict";var e=n("4949"),i=n.n(e);i.a},ec1b:function(t,a,n){(function(t){var n,e,i;function s(t){return s="function"===typeof Symbol&&"symbol"===typeof Symbol.iterator?function(t){return typeof t}:function(t){return t&&"function"===typeof Symbol&&t.constructor===Symbol&&t!==Symbol.prototype?"symbol":typeof t},s(t)}!function(r,o){"object"==s(a)&&"object"==s(t)?t.exports=o():(e=[],n=o,i="function"===typeof n?n.apply(a,e):n,void 0===i||(t.exports=i))}(0,(function(){return function(t){function a(e){if(n[e])return n[e].exports;var i=n[e]={i:e,l:!1,exports:{}};return t[e].call(i.exports,i,i.exports,a),i.l=!0,i.exports}var n={};return a.m=t,a.c=n,a.i=function(t){return t},a.d=function(t,n,e){a.o(t,n)||Object.defineProperty(t,n,{configurable:!1,enumerable:!0,get:e})},a.n=function(t){var n=t&&t.__esModule?function(){return t.default}:function(){return t};return a.d(n,"a",n),n},a.o=function(t,a){return Object.prototype.hasOwnProperty.call(t,a)},a.p="/dist/",a(a.s=2)}([function(t,a,n){var e=n(4)(n(1),n(5),null,null);t.exports=e.exports},function(t,a,n){"use strict";Object.defineProperty(a,"__esModule",{value:!0});var e=n(3);a.default={props:{startVal:{type:Number,required:!1,default:0},endVal:{type:Number,required:!1,default:2017},duration:{type:Number,required:!1,default:3e3},autoplay:{type:Boolean,required:!1,default:!0},decimals:{type:Number,required:!1,default:0,validator:function(t){return t>=0}},decimal:{type:String,required:!1,default:"."},separator:{type:String,required:!1,default:","},prefix:{type:String,required:!1,default:""},suffix:{type:String,required:!1,default:""},useEasing:{type:Boolean,required:!1,default:!0},easingFn:{type:Function,default:function(t,a,n,e){return n*(1-Math.pow(2,-10*t/e))*1024/1023+a}}},data:function(){return{localStartVal:this.startVal,displayValue:this.formatNumber(this.startVal),printVal:null,paused:!1,localDuration:this.duration,startTime:null,timestamp:null,remaining:null,rAF:null}},computed:{countDown:function(){return this.startVal>this.endVal}},watch:{startVal:function(){this.autoplay&&this.start()},endVal:function(){this.autoplay&&this.start()}},mounted:function(){this.autoplay&&this.start(),this.$emit("mountedCallback")},methods:{start:function(){this.localStartVal=this.startVal,this.startTime=null,this.localDuration=this.duration,this.paused=!1,this.rAF=(0,e.requestAnimationFrame)(this.count)},pauseResume:function(){this.paused?(this.resume(),this.paused=!1):(this.pause(),this.paused=!0)},pause:function(){(0,e.cancelAnimationFrame)(this.rAF)},resume:function(){this.startTime=null,this.localDuration=+this.remaining,this.localStartVal=+this.printVal,(0,e.requestAnimationFrame)(this.count)},reset:function(){this.startTime=null,(0,e.cancelAnimationFrame)(this.rAF),this.displayValue=this.formatNumber(this.startVal)},count:function(t){this.startTime||(this.startTime=t),this.timestamp=t;var a=t-this.startTime;this.remaining=this.localDuration-a,this.useEasing?this.countDown?this.printVal=this.localStartVal-this.easingFn(a,0,this.localStartVal-this.endVal,this.localDuration):this.printVal=this.easingFn(a,this.localStartVal,this.endVal-this.localStartVal,this.localDuration):this.countDown?this.printVal=this.localStartVal-(this.localStartVal-this.endVal)*(a/this.localDuration):this.printVal=this.localStartVal+(this.localStartVal-this.startVal)*(a/this.localDuration),this.countDown?this.printVal=this.printVal<this.endVal?this.endVal:this.printVal:this.printVal=this.printVal>this.endVal?this.endVal:this.printVal,this.displayValue=this.formatNumber(this.printVal),a<this.localDuration?this.rAF=(0,e.requestAnimationFrame)(this.count):this.$emit("callback")},isNumber:function(t){return!isNaN(parseFloat(t))},formatNumber:function(t){t=t.toFixed(this.decimals),t+="";var a=t.split("."),n=a[0],e=a.length>1?this.decimal+a[1]:"",i=/(\d+)(\d{3})/;if(this.separator&&!this.isNumber(this.separator))for(;i.test(n);)n=n.replace(i,"$1"+this.separator+"$2");return this.prefix+n+e+this.suffix}},destroyed:function(){(0,e.cancelAnimationFrame)(this.rAF)}}},function(t,a,n){"use strict";Object.defineProperty(a,"__esModule",{value:!0});var e=n(0),i=function(t){return t&&t.__esModule?t:{default:t}}(e);a.default=i.default,"undefined"!=typeof window&&window.Vue&&window.Vue.component("count-to",i.default)},function(t,a,n){"use strict";Object.defineProperty(a,"__esModule",{value:!0});var e=0,i="webkit moz ms o".split(" "),s=void 0,r=void 0;if("undefined"==typeof window)a.requestAnimationFrame=s=function(){},a.cancelAnimationFrame=r=function(){};else{a.requestAnimationFrame=s=window.requestAnimationFrame,a.cancelAnimationFrame=r=window.cancelAnimationFrame;for(var o=void 0,l=0;l<i.length&&(!s||!r);l++)o=i[l],a.requestAnimationFrame=s=s||window[o+"RequestAnimationFrame"],a.cancelAnimationFrame=r=r||window[o+"CancelAnimationFrame"]||window[o+"CancelRequestAnimationFrame"];s&&r||(a.requestAnimationFrame=s=function(t){var a=(new Date).getTime(),n=Math.max(0,16-(a-e)),i=window.setTimeout((function(){t(a+n)}),n);return e=a+n,i},a.cancelAnimationFrame=r=function(t){window.clearTimeout(t)})}a.requestAnimationFrame=s,a.cancelAnimationFrame=r},function(t,a){t.exports=function(t,a,n,e){var i,r=t=t||{},o=s(t.default);"object"!==o&&"function"!==o||(i=t,r=t.default);var l="function"==typeof r?r.options:r;if(a&&(l.render=a.render,l.staticRenderFns=a.staticRenderFns),n&&(l._scopeId=n),e){var c=Object.create(l.computed||null);Object.keys(e).forEach((function(t){var a=e[t];c[t]=function(){return a}})),l.computed=c}return{esModule:i,exports:r,options:l}}},function(t,a){t.exports={render:function(){var t=this,a=t.$createElement;return(t._self._c||a)("span",[t._v("\n  "+t._s(t.displayValue)+"\n")])},staticRenderFns:[]}}])}))}).call(this,n("62e4")(t))}}]);