(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-1ca158a9","chunk-2d0d0ba5","chunk-2d237873"],{"105d5":function(e,t,n){"use strict";n.r(t);var o=function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("div",{staticClass:"icons-container"},[e._m(0),e._v(" "),n("el-tabs",{attrs:{type:"border-card"}},[n("el-tab-pane",{attrs:{label:"Icons"}},[n("div",{staticClass:"grid"},e._l(e.svgIcons,(function(t){return n("div",{key:t,on:{click:function(n){e.handleClipboard(e.generateIconCode(t),n)}}},[n("el-tooltip",{attrs:{placement:"top"}},[n("div",{attrs:{slot:"content"},slot:"content"},[e._v("\n              "+e._s(e.generateIconCode(t))+"\n            ")]),e._v(" "),n("div",{staticClass:"icon-item"},[n("svg-icon",{attrs:{"icon-class":t,"class-name":"disabled"}}),e._v(" "),n("span",[e._v(e._s(t))])],1)])],1)})),0)]),e._v(" "),n("el-tab-pane",{attrs:{label:"Element-UI Icons"}},[n("div",{staticClass:"grid"},e._l(e.elementIcons,(function(t){return n("div",{key:t,on:{click:function(n){e.handleClipboard(e.generateElementIconCode(t),n)}}},[n("el-tooltip",{attrs:{placement:"top"}},[n("div",{attrs:{slot:"content"},slot:"content"},[e._v("\n              "+e._s(e.generateElementIconCode(t))+"\n            ")]),e._v(" "),n("div",{staticClass:"icon-item"},[n("i",{class:"el-icon-"+t}),e._v(" "),n("span",[e._v(e._s(t))])])])],1)})),0)])],1)],1)},c=[function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("aside",[n("a",{attrs:{href:"https://panjiachen.github.io/vue-element-admin-site/guide/advanced/icon.html",target:"_blank"}},[e._v("Add and use\n    ")])])}],a=n("f71e"),s=n("fc00"),r=n("68e2"),i={name:"Icons",data:function(){return{svgIcons:s["default"],elementIcons:r["default"]}},methods:{generateIconCode:function(e){return'<svg-icon icon-class="'.concat(e,'" />')},generateElementIconCode:function(e){return'<i class="el-icon-'.concat(e,'" />')},handleClipboard:function(e,t){Object(a["a"])(e,t)}}},l=i,u=(n("a25fa"),n("2877")),d=Object(u["a"])(l,o,c,!1,null,"0454c005",null);t["default"]=d.exports},"47a6":function(e,t,n){},"68e2":function(e,t,n){"use strict";n.r(t);var o=["info","error","success","warning","question","back","arrow-left","arrow-down","arrow-right","arrow-up","caret-left","caret-bottom","caret-top","caret-right","d-arrow-left","d-arrow-right","minus","plus","remove","circle-plus","remove-outline","circle-plus-outline","close","check","circle-close","circle-check","circle-close-outline","circle-check-outline","zoom-out","zoom-in","d-caret","sort","sort-down","sort-up","tickets","document","goods","sold-out","news","message","date","printer","time","bell","mobile-phone","service","view","menu","more","more-outline","star-on","star-off","location","location-outline","phone","phone-outline","picture","picture-outline","delete","search","edit","edit-outline","rank","refresh","share","setting","upload","upload2","download","loading"];t["default"]=o},a25fa:function(e,t,n){"use strict";var o=n("47a6"),c=n.n(o);c.a},f71e:function(e,t,n){"use strict";n.d(t,"a",(function(){return i}));var o=n("2b0e"),c=n("b311"),a=n.n(c);function s(){o["default"].prototype.$message({message:"Copy successfully",type:"success",duration:1500})}function r(){o["default"].prototype.$message({message:"Copy failed",type:"error"})}function i(e,t){var n=new a.a(t.target,{text:function(){return e}});n.on("success",(function(){s(),n.destroy()})),n.on("error",(function(){r(),n.destroy()})),n.onClick(t)}},fc00:function(e,t,n){"use strict";n.r(t);var o=n("51ff"),c=function(e){return e.keys()},a=/\.\/(.*)\.svg/,s=c(o).map((function(e){return e.match(a)[1]}));t["default"]=s}}]);