(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-5fcdf756"],{"09f4":function(t,e,n){"use strict";n.d(e,"a",(function(){return i})),Math.easeInOutQuad=function(t,e,n,a){return t/=a/2,t<1?n/2*t*t+e:(t--,-n/2*(t*(t-2)-1)+e)};var a=function(){return window.requestAnimationFrame||window.webkitRequestAnimationFrame||window.mozRequestAnimationFrame||function(t){window.setTimeout(t,1e3/60)}}();function o(t){document.documentElement.scrollTop=t,document.body.parentNode.scrollTop=t,document.body.scrollTop=t}function r(){return document.documentElement.scrollTop||document.body.parentNode.scrollTop||document.body.scrollTop}function i(t,e,n){var i=r(),l=t-i,c=20,s=0;e="undefined"===typeof e?500:e;var u=function t(){s+=c;var r=Math.easeInOutQuad(s,i,l,e);o(r),s<e?a(t):n&&"function"===typeof n&&n()};u()}},1593:function(t,e,n){"use strict";var a=n("2f7b"),o=function(t,e,n){var a=n.componentInstance,o=e.value;if(!a.height)throw new Error("el-$table must set the height. Such as height='100px'");var r=o&&o.bottomOffset||30;if(a){var i=window.innerHeight-t.getBoundingClientRect().top-r;setTimeout((function(){a.layout.setHeight(i),a.doLayout()}),1)}},r={bind:function(t,e,n){t.resizeListener=function(){o(t,e,n)},Object(a["a"])(window.document.body,t.resizeListener)},inserted:function(t,e,n){o(t,e,n)},unbind:function(t){Object(a["b"])(window.document.body,t.resizeListener)}},i=function(t){t.directive("adaptive",r)};window.Vue&&(window["adaptive"]=r,Vue.use(i)),r.install=i;e["a"]=r},5888:function(t,e,n){"use strict";n.r(e);var a=function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("div",{staticClass:"app-container"},[n("div",{staticClass:"filter-container"},[n("div",{staticStyle:{display:"inline-block"}},[n("label",{staticClass:"radio-label",staticStyle:{"padding-left":"0"}},[t._v(t._s(t.$t("dictionary.gcState.cnName")))]),t._v(" "),n("el-input",{staticClass:"filter-item",staticStyle:{width:"200px"},attrs:{placeholder:t.$t("dictionary.gcState.cnName")},nativeOn:{keyup:function(e){return!e.type.indexOf("key")&&t._k(e.keyCode,"enter",13,e.key,"Enter")?null:t.handleFilter(e)}},model:{value:t.listQuery.cnName,callback:function(e){t.$set(t.listQuery,"cnName",e)},expression:"listQuery.cnName"}}),t._v(" "),n("label",{staticClass:"radio-label",staticStyle:{"padding-left":"0"}},[t._v(t._s(t.$t("dictionary.gcState.enName")))]),t._v(" "),n("el-input",{staticClass:"filter-item",staticStyle:{width:"200px"},attrs:{placeholder:t.$t("dictionary.gcState.enName")},nativeOn:{keyup:function(e){return!e.type.indexOf("key")&&t._k(e.keyCode,"enter",13,e.key,"Enter")?null:t.handleFilter(e)}},model:{value:t.listQuery.enName,callback:function(e){t.$set(t.listQuery,"enName",e)},expression:"listQuery.enName"}}),t._v(" "),n("el-button",{directives:[{name:"waves",rawName:"v-waves"}],staticClass:"filter-item",attrs:{icon:"el-icon-search"},on:{click:t.handleFilter}},[t._v("\n        "+t._s(t.$t("common.search"))+"\n      ")]),t._v(" "),n("el-button",{directives:[{name:"waves",rawName:"v-waves"}],staticClass:"filter-item",attrs:{type:"info",icon:"el-icon-setting"},on:{click:t.reset}},[t._v("\n        "+t._s(t.$t("common.reset"))+"\n      ")]),t._v(" "),n("div",{staticStyle:{"margin-top":"5px"}},[n("el-button",{staticClass:"filter-item",attrs:{type:"primary",icon:"el-icon-plus"},on:{click:t.handleCreate}},[t._v("\n          "+t._s(t.$t("common.add"))+"\n        ")])],1)],1),t._v(" "),n("el-table",{directives:[{name:"loading",rawName:"v-loading",value:t.listLoading,expression:"listLoading"},{name:"adaptive",rawName:"v-adaptive",value:{bottomOffset:40},expression:"{bottomOffset: 40}"}],key:t.tableKey,staticStyle:{width:"100%",overflow:"auto"},attrs:{data:t.list,border:"",fit:"",height:"100px","highlight-current-row":"","row-style":{height:"35px"},"cell-style":{padding:"0"}}},[n("el-table-column",{attrs:{label:"序号",type:"index","show-overflow-tooltip":"",width:"auto",align:"center"}}),t._v(" "),n("el-table-column",{attrs:{label:t.$t("dictionary.gcState.enBrief"),width:"auto",align:"center","show-overflow-tooltip":""},scopedSlots:t._u([{key:"default",fn:function(e){var a=e.row;return[n("span",{staticClass:"link-type"},[n("el-link",{attrs:{type:"info"},on:{click:function(e){return t.handleModifyStatus(a,"detail")}}},[t._v(t._s(a.enBrief)),n("i",{staticClass:"el-icon-view el-icon--right"})])],1)]}}])}),t._v(" "),n("el-table-column",{attrs:{label:t.$t("dictionary.gcState.cnBrief"),width:"auto",align:"center","show-overflow-tooltip":""},scopedSlots:t._u([{key:"default",fn:function(e){var a=e.row;return[n("span",[t._v(t._s(a.cnBrief))])]}}])}),t._v(" "),n("el-table-column",{attrs:{label:t.$t("dictionary.gcState.cnName"),width:"auto",align:"center","show-overflow-tooltip":""},scopedSlots:t._u([{key:"default",fn:function(e){var a=e.row;return[n("span",[t._v(t._s(a.cnName))])]}}])}),t._v(" "),n("el-table-column",{attrs:{label:t.$t("dictionary.gcState.enName"),width:"auto",align:"center","show-overflow-tooltip":""},scopedSlots:t._u([{key:"default",fn:function(e){var a=e.row;return[n("span",[t._v(t._s(a.enName))])]}}])}),t._v(" "),n("el-table-column",{attrs:{label:t.$t("dictionary.gcState.twoCode"),width:"auto",align:"center","show-overflow-tooltip":""},scopedSlots:t._u([{key:"default",fn:function(e){var a=e.row;return[n("span",[t._v(t._s(a.twoCode))])]}}])}),t._v(" "),n("el-table-column",{attrs:{label:t.$t("dictionary.gcState.threeCode"),width:"auto",align:"center","show-overflow-tooltip":""},scopedSlots:t._u([{key:"default",fn:function(e){var a=e.row;return[n("span",[t._v(t._s(a.threeCode))])]}}])}),t._v(" "),n("el-table-column",{attrs:{label:t.$t("dictionary.gcState.numberCode"),width:"auto",align:"center","show-overflow-tooltip":""},scopedSlots:t._u([{key:"default",fn:function(e){var a=e.row;return[n("span",[t._v(t._s(a.numberCode))])]}}])}),t._v(" "),n("el-table-column",{attrs:{label:t.$t("common.operate"),align:"center",width:"150px","class-name":"small-padding fixed-width","show-overflow-tooltip":""},scopedSlots:t._u([{key:"default",fn:function(e){var a=e.row;return[n("el-button",{attrs:{type:"primary"},on:{click:function(e){return t.handleUpdate(a)}}},[t._v("\n            "+t._s(t.$t("common.edit"))+"\n          ")]),t._v(" "),n("el-button",{attrs:{type:"danger","show-overflow-tooltip":""},on:{click:function(e){return t.handleDelete(a,"delete")}}},[t._v("\n            "+t._s(t.$t("common.delete"))+"\n          ")])]}}])})],1),t._v(" "),n("pagination",{directives:[{name:"show",rawName:"v-show",value:t.total>0,expression:"total>0"}],staticStyle:{margin:"0px","padding-left":"0px"},attrs:{total:t.total,page:t.listQuery.pageNum,limit:t.listQuery.pageSize},on:{"update:page":function(e){return t.$set(t.listQuery,"pageNum",e)},"update:limit":function(e){return t.$set(t.listQuery,"pageSize",e)},pagination:t.getList}}),t._v(" "),n("el-dialog",{directives:[{name:"dialogDrag",rawName:"v-dialogDrag"},{name:"el-drag-dialog",rawName:"v-el-drag-dialog"}],attrs:{title:t.textMap[t.dialogStatus],visible:t.dialogFormVisible,"custom-class":"customwh;",width:"80%","append-to-body":!0},on:{"update:visible":function(e){t.dialogFormVisible=e},dragDialog:function(t){return this.$refs.select.blur()}}},[n("el-form",{ref:"dataForm",staticStyle:{width:"100%"},attrs:{rules:t.rules,model:t.temp,"label-position":"left","label-width":"130px"}},[n("el-row",[n("el-col",{attrs:{span:12}},[n("el-form-item",{attrs:{label:t.$t("dictionary.gcState.enName")}},[n("el-input",{attrs:{readonly:"detail"==t.dialogStatus},model:{value:t.temp.enName,callback:function(e){t.$set(t.temp,"enName",e)},expression:"temp.enName"}})],1)],1),t._v(" "),n("el-col",{attrs:{span:12}},[n("el-form-item",{attrs:{label:t.$t("dictionary.gcState.cnName")}},[n("el-input",{attrs:{readonly:"detail"==t.dialogStatus},model:{value:t.temp.cnName,callback:function(e){t.$set(t.temp,"cnName",e)},expression:"temp.cnName"}})],1)],1)],1),t._v(" "),n("el-row",[n("el-col",{attrs:{span:12}},[n("el-form-item",{attrs:{label:t.$t("dictionary.gcState.enBrief")}},[n("el-input",{attrs:{readonly:"detail"==t.dialogStatus},model:{value:t.temp.enBrief,callback:function(e){t.$set(t.temp,"enBrief",e)},expression:"temp.enBrief"}})],1)],1),t._v(" "),n("el-col",{attrs:{span:12}},[n("el-form-item",{attrs:{label:t.$t("dictionary.gcState.cnBrief")}},[n("el-input",{attrs:{readonly:"detail"==t.dialogStatus},model:{value:t.temp.cnBrief,callback:function(e){t.$set(t.temp,"cnBrief",e)},expression:"temp.cnBrief"}})],1)],1)],1),t._v(" "),n("el-row",[n("el-col",{attrs:{span:12}},[n("el-form-item",{attrs:{label:t.$t("dictionary.gcState.twoCode")}},[n("el-input",{attrs:{readonly:"detail"==t.dialogStatus},model:{value:t.temp.twoCode,callback:function(e){t.$set(t.temp,"twoCode",e)},expression:"temp.twoCode"}})],1)],1),t._v(" "),n("el-col",{attrs:{span:12}},[n("el-form-item",{attrs:{label:t.$t("dictionary.gcState.threeCode")}},[n("el-input",{attrs:{readonly:"detail"==t.dialogStatus},model:{value:t.temp.threeCode,callback:function(e){t.$set(t.temp,"threeCode",e)},expression:"temp.threeCode"}})],1)],1)],1),t._v(" "),n("el-row",[n("el-col",{attrs:{span:12}},[n("el-form-item",{attrs:{label:t.$t("dictionary.gcState.numberCode")}},[n("el-input",{attrs:{readonly:"detail"==t.dialogStatus},model:{value:t.temp.numberCode,callback:function(e){t.$set(t.temp,"numberCode",e)},expression:"temp.numberCode"}})],1)],1),t._v(" "),n("el-col",{attrs:{span:12}},[n("el-form-item",{attrs:{label:t.$t("dictionary.gcState.version")}},[n("el-date-picker",{staticStyle:{width:"100%"},attrs:{editable:!0,type:"date",align:"left",placeholder:t.$t("common.datePickerRemind"),readonly:"detail"==t.dialogStatus},model:{value:t.temp.version,callback:function(e){t.$set(t.temp,"version",e)},expression:"temp.version"}})],1)],1)],1),t._v(" "),n("el-row",[n("el-col",{attrs:{span:24}},[n("el-form-item",{attrs:{label:t.$t("dictionary.gcState.remarkInfo")}},[n("el-input",{attrs:{type:"textarea",readonly:"detail"==t.dialogStatus},model:{value:t.temp.remarkInfo,callback:function(e){t.$set(t.temp,"remarkInfo",e)},expression:"temp.remarkInfo"}})],1)],1)],1)],1),t._v(" "),n("div",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[n("el-button",{on:{click:function(e){t.dialogFormVisible=!1}}},[t._v("\n          "+t._s(t.$t("common.close"))+"\n        ")]),t._v(" "),"detail"!=t.dialogStatus?n("el-button",{attrs:{type:"primary"},on:{click:function(e){"create"===t.dialogStatus?t.createData():t.updateData()}}},[t._v("\n          "+t._s(t.$t("common.save"))+"\n        ")]):t._e()],1)],1)],1)])},o=[],r=n("d3e3"),i=n("6724"),l=n("333d"),c=n("1593"),s=n("a888"),u=n("bff4"),d={name:"gcState",components:{Pagination:l["a"]},directives:{waves:i["a"],adaptive:c["a"],elDragDialog:s["a"]},data:function(){return{total:3,listLoading:!1,listQuery:{pageNum:1,pageSize:20,enName:"",cnName:""},dialogFormVisible:!1,dialogStatus:"",textMap:{update:this.$t("common.edit"),create:this.$t("common.add"),detail:this.$t("common.detail")},list:[{code:"ESMLN",enName:"Melilla",cnName:"梅利利亚",nationCn:"西班牙",nationEn:"Spain",threeCode:"ESP",branch:"AT"},{code:"ESMLN",enName:"Melilla",cnName:"梅利利亚",nationCn:"西班牙",nationEn:"Spain",threeCode:"ESP",branch:"AT"},{code:"ESMLN",enName:"Melilla",cnName:"梅利利亚11",nationCn:"西班牙",nationEn:"Spain",threeCode:"ESP",branch:"AT"}],temp:{id:"",version:"",cnBrief:"",enBrief:"",enName:"",cnName:"",numberCode:"",twoCode:"",threeCode:"",remarkInfo:""},rules:{enName:[{required:!0,message:this.$t("common.inputRequired"),trigger:"blur"}],cnName:[{required:!0,message:this.$t("common.inputRequired"),trigger:"blur"}],version:[{required:!0,message:this.$t("common.inputRequired"),trigger:"blur"}],cnBrief:[{required:!0,message:this.$t("common.inputRequired"),trigger:"blur"}],enBrief:[{required:!0,message:this.$t("common.inputRequired"),trigger:"blur"}],threeCode:[{required:!0,message:this.$t("common.inputRequired"),trigger:"blur"}],twoCode:[{required:!0,message:this.$t("common.inputRequired"),trigger:"blur"}],numberCode:[{required:!0,message:this.$t("common.inputRequired"),trigger:"blur"}]}}},created:function(){this.getList()},methods:{getList:function(){var t=this;this.listLoading=!0;var e="/gcState/findGcStateList";Object(u["k"])(e,this.listQuery).then((function(e){t.list=e.data.data.items,t.total=e.data.data.total,setTimeout((function(){t.listLoading=!1}),1500)}))},resetTemp:function(){this.temp={id:"",version:"",state:"",valid:"",updateTime:"",updateUser:"",code:"",enName:"",cnName:"",nationCn:"",nationEn:"",threeCode:"",remarkInfo:"",branch:"",province:""},this.getList()},handleFilter:function(){this.getList()},reset:function(){this.listQuery.enName="",this.listQuery.cnName=""},handleCreate:function(){var t=this;this.resetTemp(),this.dialogStatus="create",this.dialogFormVisible=!0,this.$nextTick((function(){t.$refs["dataForm"].clearValidate()}))},handleUpdate:function(t){var e=this,n="/gcState/findGcStateById/"+t.id;Object(u["k"])(n).then((function(t){e.temp=t.data.data,setTimeout((function(){e.listLoading=!1}),1500)})),this.dialogStatus="update",this.dialogFormVisible=!0,this.$nextTick((function(){e.$refs["dataForm"].clearValidate()}))},handleDelete:function(t,e){var n=this;"delete"===e&&!0===confirm("确定要删除？")&&Object(u["j"])("/gcState/deleteGcStateById/"+t.id).then((function(e){if(e.data.result){n.$notify({title:"Success",message:"删除成功",type:"success",duration:2e3});var a=n.list.indexOf(t);n.list.splice(a,1),n.total=n.total-1}else n.$notify({title:"Fail",message:"删除失败",type:"fail",duration:2e3})}))},createData:function(){var t=this;this.$refs["dataForm"].validate((function(e){if(e){var n="/gcState/saveOrUpdateGcState";Object(r["M"])(n,t.temp).then((function(e){t.total=t.total+1,t.temp.id=e.data.data.id,t.list.unshift(t.temp),t.dialogFormVisible=!1,t.$notify({title:"Success",message:"Created Successfully",type:"success",duration:2e3})}))}}))},handleModifyStatus:function(t,e){var n=this,a="/gcState/findGcStateById/"+t.id;Object(u["k"])(a).then((function(t){n.temp=t.data.data,setTimeout((function(){n.listLoading=!1}),1500)})),this.dialogStatus="detail",this.dialogFormVisible=!0,this.$nextTick((function(){n.$refs["dataForm"].clearValidate()}))},updateData:function(){var t=this;this.$refs["dataForm"].validate((function(e){if(e){var n=Object.assign({},t.temp);n.version=+new Date(n.version);var a="/gcState/saveOrUpdateGcState";Object(r["M"])(a,n).then((function(e){var n=!0,a=!1,o=void 0;try{for(var r,i=t.list[Symbol.iterator]();!(n=(r=i.next()).done);n=!0){var l=r.value;if(l.id===t.temp.id){var c=t.list.indexOf(l);t.list.splice(c,1,e.data.data);break}}}catch(s){a=!0,o=s}finally{try{n||null==i.return||i.return()}finally{if(a)throw o}}t.dialogFormVisible=!1,t.$notify({title:"Success",message:"Update Successfully",type:"success",duration:2e3})}))}}))}}},m=d,p=n("2877"),f=Object(p["a"])(m,a,o,!1,null,null,null);e["default"]=f.exports},6724:function(t,e,n){"use strict";n("8d41");var a="@@wavesContext";function o(t,e){function n(n){var a=Object.assign({},e.value),o=Object.assign({ele:t,type:"hit",color:"rgba(0, 0, 0, 0.15)"},a),r=o.ele;if(r){r.style.position="relative",r.style.overflow="hidden";var i=r.getBoundingClientRect(),l=r.querySelector(".waves-ripple");switch(l?l.className="waves-ripple":(l=document.createElement("span"),l.className="waves-ripple",l.style.height=l.style.width=Math.max(i.width,i.height)+"px",r.appendChild(l)),o.type){case"center":l.style.top=i.height/2-l.offsetHeight/2+"px",l.style.left=i.width/2-l.offsetWidth/2+"px";break;default:l.style.top=(n.pageY-i.top-l.offsetHeight/2-document.documentElement.scrollTop||document.body.scrollTop)+"px",l.style.left=(n.pageX-i.left-l.offsetWidth/2-document.documentElement.scrollLeft||document.body.scrollLeft)+"px"}return l.style.backgroundColor=o.color,l.className="waves-ripple z-active",!1}}return t[a]?t[a].removeHandle=n:t[a]={removeHandle:n},n}var r={bind:function(t,e){t.addEventListener("click",o(t,e),!1)},update:function(t,e){t.removeEventListener("click",t[a].removeHandle,!1),t.addEventListener("click",o(t,e),!1)},unbind:function(t){t.removeEventListener("click",t[a].removeHandle,!1),t[a]=null,delete t[a]}},i=function(t){t.directive("waves",r)};window.Vue&&(window.waves=r,Vue.use(i)),r.install=i;e["a"]=r},"8d41":function(t,e,n){},a888:function(t,e,n){"use strict";var a={bind:function(t,e,n){var a=t.querySelector(".el-dialog__header"),o=t.querySelector(".el-dialog");a.style.cssText+=";cursor:move;",o.style.cssText+=";top:0px;";var r=function(){return window.document.currentStyle?function(t,e){return t.currentStyle[e]}:function(t,e){return getComputedStyle(t,!1)[e]}}();a.onmousedown=function(t){var e=t.clientX-a.offsetLeft,i=t.clientY-a.offsetTop,l=o.offsetWidth,c=o.offsetHeight,s=document.body.clientWidth,u=document.body.clientHeight,d=o.offsetLeft,m=s-o.offsetLeft-l,p=o.offsetTop,f=u-o.offsetTop-c,h=r(o,"left"),v=r(o,"top");h.includes("%")?(h=+document.body.clientWidth*(+h.replace(/\%/g,"")/100),v=+document.body.clientHeight*(+v.replace(/\%/g,"")/100)):(h=+h.replace(/\px/g,""),v=+v.replace(/\px/g,"")),document.onmousemove=function(t){var a=t.clientX-e,r=t.clientY-i;-a>d?a=-d:a>m&&(a=m),-r>p?r=-p:r>f&&(r=f),o.style.cssText+=";left:".concat(a+h,"px;top:").concat(r+v,"px;"),n.child.$emit("dragDialog")},document.onmouseup=function(t){document.onmousemove=null,document.onmouseup=null}}}},o=function(t){t.directive("el-drag-dialog",a)};window.Vue&&(window["el-drag-dialog"]=a,Vue.use(o)),a.install=o;e["a"]=a},d3e3:function(t,e,n){"use strict";n.d(e,"M",(function(){return o})),n.d(e,"b",(function(){return r})),n.d(e,"r",(function(){return i})),n.d(e,"U",(function(){return l})),n.d(e,"s",(function(){return c})),n.d(e,"u",(function(){return s})),n.d(e,"e",(function(){return u})),n.d(e,"G",(function(){return d})),n.d(e,"I",(function(){return m})),n.d(e,"D",(function(){return p})),n.d(e,"S",(function(){return f})),n.d(e,"T",(function(){return h})),n.d(e,"E",(function(){return v})),n.d(e,"o",(function(){return g})),n.d(e,"l",(function(){return b})),n.d(e,"j",(function(){return y})),n.d(e,"P",(function(){return w})),n.d(e,"N",(function(){return S})),n.d(e,"F",(function(){return C})),n.d(e,"g",(function(){return O})),n.d(e,"K",(function(){return k})),n.d(e,"O",(function(){return _})),n.d(e,"h",(function(){return j})),n.d(e,"C",(function(){return $})),n.d(e,"t",(function(){return T})),n.d(e,"L",(function(){return N})),n.d(e,"d",(function(){return x})),n.d(e,"i",(function(){return E})),n.d(e,"Q",(function(){return L})),n.d(e,"W",(function(){return D})),n.d(e,"q",(function(){return F})),n.d(e,"m",(function(){return R})),n.d(e,"V",(function(){return B})),n.d(e,"H",(function(){return q})),n.d(e,"R",(function(){return M})),n.d(e,"z",(function(){return V})),n.d(e,"x",(function(){return I})),n.d(e,"A",(function(){return Q})),n.d(e,"y",(function(){return U})),n.d(e,"p",(function(){return A})),n.d(e,"n",(function(){return H})),n.d(e,"f",(function(){return P})),n.d(e,"w",(function(){return z})),n.d(e,"v",(function(){return G})),n.d(e,"c",(function(){return W})),n.d(e,"J",(function(){return Y})),n.d(e,"k",(function(){return J})),n.d(e,"a",(function(){return X})),n.d(e,"B",(function(){return K}));var a=n("b775");function o(t,e){return Object(a["a"])({url:t,method:"post",data:e})}function r(t,e){return Object(a["a"])({url:t,method:"post",data:e})}function i(t,e){return Object(a["a"])({url:t,method:"post",responseType:"blob",headers:{"Content-Type":"application/json"},data:e})}function l(t,e){return Object(a["a"])({url:t,method:"post",data:e})}function c(t,e){return Object(a["a"])({url:t,method:"post",responseType:"blob",headers:{"Content-Type":"application/json"},data:e})}function s(t){return Object(a["a"])({url:"/mrv/generatorImoRpt",method:"post",data:t,timeout:6e5})}function u(t){return Object(a["a"])({url:"/mrv/deleteImoStdRpt",method:"post",data:t})}function d(t){return Object(a["a"])({url:"/mrv/imoLockOrUnlock",method:"post",data:t})}function m(t){return Object(a["a"])({url:"/mrv/imoReport",method:"post",data:t})}function p(t){return Object(a["a"])({url:"/mrv/getImoStdRpts",method:"post",data:t})}function f(t){return Object(a["a"])({url:"/mrv/saveImoRpt",method:"post",data:t})}function h(t){return Object(a["a"])({url:"/mrv/saveManualDcs",method:"post",data:t})}function v(t){return Object(a["a"])({url:"/mrv/getManuleDcs",method:"post",data:t})}function g(t){return Object(a["a"])({url:"/mrv/downLoadDcs",method:"post",responseType:"blob",headers:{"Content-Type":"application/json"},data:t})}function b(t){return Object(a["a"])({url:"/mrv/downLoadCollectionData",method:"post",responseType:"blob",headers:{"Content-Type":"application/json"},data:t})}function y(t){return Object(a["a"])({url:"/mrv/downLoadBdnData",method:"post",responseType:"blob",headers:{"Content-Type":"application/json"},data:t})}function w(t){return Object(a["a"])({url:"/sysUser/saveAppUser",method:"post",data:t})}function S(t){return Object(a["a"])({url:"/mrv/saveAndUpdatePeriodOil",method:"post",data:t})}function C(t){return Object(a["a"])({url:"/mrv/getPeriodOil",method:"post",data:t})}function O(t){return Object(a["a"])({url:"/mrv/deletePeriodOil",method:"post",data:t})}function k(t){return Object(a["a"])({url:"/mrv/lockOrUnlock",method:"post",data:t})}function _(t){return Object(a["a"])({url:"/shipManager/saveAndUpdateTfc",method:"post",data:t})}function j(t){return Object(a["a"])({url:"/shipManager/deleteTfc",method:"post",data:t})}function $(t){return Object(a["a"])({url:"/mrv/getEudcs",method:"post",data:t})}function T(t){return Object(a["a"])({url:"/mrv/generatorEuDcs",method:"post",data:t})}function N(t){return Object(a["a"])({url:"/mrv/lockOrUnlockEuDcs",method:"post",data:t})}function x(t){return Object(a["a"])({url:"/mrv/deleteEuDcs",method:"post",data:t})}function E(t){return Object(a["a"])({url:"/mrv/downEuDcs",method:"post",responseType:"blob",headers:{"Content-Type":"application/json"},data:t})}function L(t){return Object(a["a"])({url:"/mrv/saveEuDcs",method:"post",data:t})}function D(t){return Object(a["a"])({url:"/mrv/singalShipYear",method:"post",data:t})}function F(t){return Object(a["a"])({url:"/mrv/downLoadSigalShip",method:"post",responseType:"blob",headers:{"Content-Type":"application/json"},data:t})}function R(t){return Object(a["a"])({url:"/mrv/downLoadComPanyShip",method:"post",responseType:"blob",headers:{"Content-Type":"application/json"},data:t})}function B(t){return Object(a["a"])({url:"/mrv/shipComPanyYear",method:"post",data:t})}function q(t){return Object(a["a"])({url:"/mrv/imoReportLibya",method:"post",data:t})}function M(t){return Object(a["a"])({url:"/flagDocChange/saveFlagDocChange",method:"post",data:t})}function V(t){return Object(a["a"])({url:"/mrv/getEnergyEfficencyTrend",method:"post",data:t})}function I(t){return Object(a["a"])({url:"/mrv/getCompareAnalysisData",method:"post",data:t})}function Q(t){return Object(a["a"])({url:"/mrv/getEnergyEfficencyTrendData",method:"post",data:t})}function U(t){return Object(a["a"])({url:"/mrv/getCompareAnalysisDatas",method:"post",data:t})}function A(t){return Object(a["a"])({url:"/mrv/downLoadEnergyEfficencyTrendData",method:"post",responseType:"blob",headers:{"Content-Type":"application/json"},data:t})}function H(t){return Object(a["a"])({url:"/mrv/downLoadCompareAnalysisData",method:"post",responseType:"blob",headers:{"Content-Type":"application/json"},data:t})}function P(t){return Object(a["a"])({url:"/flagDocChange/deleteMultiFlagDocChangeById",method:"post",data:t})}function z(t){return Object(a["a"])({url:"/energyEfficiency/getCmsaStdRpt",method:"post",data:t})}function G(t){return Object(a["a"])({url:"/energyEfficiency/getCmsaRpt",method:"post",data:t})}function W(t){return Object(a["a"])({url:"/energyEfficiency/deleteCmsaRpt",method:"post",data:t})}function Y(t){return Object(a["a"])({url:"/energyEfficiency/lockOrUnLockCmsaRpt",method:"post",data:t})}function J(t){return Object(a["a"])({url:"/energyEfficiency/downLoadCmsaRpt",method:"post",responseType:"blob",headers:{"Content-Type":"application/json"},data:t})}function X(t){return Object(a["a"])({url:"/energyEfficiency/cmsaRptStatusTag",method:"post",data:t})}function K(t){return Object(a["a"])({url:"/mrv/getEnergyEfficencyTrendMessage",method:"post",data:t})}}}]);