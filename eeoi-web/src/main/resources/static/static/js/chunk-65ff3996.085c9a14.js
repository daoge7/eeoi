(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-65ff3996"],{"09f4":function(t,e,n){"use strict";n.d(e,"a",(function(){return i})),Math.easeInOutQuad=function(t,e,n,o){return t/=o/2,t<1?n/2*t*t+e:(t--,-n/2*(t*(t-2)-1)+e)};var o=function(){return window.requestAnimationFrame||window.webkitRequestAnimationFrame||window.mozRequestAnimationFrame||function(t){window.setTimeout(t,1e3/60)}}();function a(t){document.documentElement.scrollTop=t,document.body.parentNode.scrollTop=t,document.body.scrollTop=t}function r(){return document.documentElement.scrollTop||document.body.parentNode.scrollTop||document.body.scrollTop}function i(t,e,n){var i=r(),u=t-i,l=20,c=0;e="undefined"===typeof e?500:e;var s=function t(){c+=l;var r=Math.easeInOutQuad(c,i,u,e);a(r),c<e?o(t):n&&"function"===typeof n&&n()};s()}},1593:function(t,e,n){"use strict";var o=n("2f7b"),a=function(t,e,n){var o=n.componentInstance,a=e.value;if(!o.height)throw new Error("el-$table must set the height. Such as height='100px'");var r=a&&a.bottomOffset||30;if(o){var i=window.innerHeight-t.getBoundingClientRect().top-r;setTimeout((function(){o.layout.setHeight(i),o.doLayout()}),1)}},r={bind:function(t,e,n){t.resizeListener=function(){a(t,e,n)},Object(o["a"])(window.document.body,t.resizeListener)},inserted:function(t,e,n){a(t,e,n)},unbind:function(t){Object(o["b"])(window.document.body,t.resizeListener)}},i=function(t){t.directive("adaptive",r)};window.Vue&&(window["adaptive"]=r,Vue.use(i)),r.install=i;e["a"]=r},a94e:function(t,e,n){"use strict";n.r(e);var o=function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("div",{staticClass:"app-container"},[n("div",{staticClass:"filter-container"},[n("div",{staticStyle:{display:"inline-block"}},[n("el-button",{staticClass:"filter-item",staticStyle:{"margin-top":"10px"},attrs:{type:"primary",icon:"el-icon-plus"},on:{click:t.handleCreate}},[t._v("\n        "+t._s(t.$t("common.add"))+"\n      ")])],1)]),t._v(" "),n("el-table",{directives:[{name:"loading",rawName:"v-loading",value:t.listLoading,expression:"listLoading"},{name:"adaptive",rawName:"v-adaptive",value:{bottomOffset:38},expression:"{bottomOffset: 38}"}],key:t.tableKey,staticStyle:{width:"100%",overflow:"auto"},attrs:{data:t.list,border:"",fit:"",height:"100px","highlight-current-row":"","row-style":{height:"35px"},"cell-style":{padding:"0"}}},[n("el-table-column",{attrs:{label:t.$t("dicManage.num"),width:"60",align:"center"},scopedSlots:t._u([{key:"default",fn:function(e){var o=e.row;return[n("span",[t._v(t._s(o.num))])]}}])}),t._v(" "),n("el-table-column",{attrs:{label:t.$t("dicManage.fuelOilName"),align:"center"},scopedSlots:t._u([{key:"default",fn:function(e){var o=e.row;return[n("span",[t._v(t._s(o.fuelOilName))])]}}])}),t._v(" "),n("el-table-column",{attrs:{label:t.$t("dicManage.fuelOilCode"),align:"center"},scopedSlots:t._u([{key:"default",fn:function(e){var o=e.row;return[n("span",[t._v(t._s(o.fuelOilCode))])]}}])}),t._v(" "),n("el-table-column",{attrs:{label:t.$t("dicManage.fuelCarbonContent"),align:"center"},scopedSlots:t._u([{key:"default",fn:function(e){var o=e.row;return[n("span",[t._v(t._s(o.fuelCarbonContent))])]}}])}),t._v(" "),n("el-table-column",{attrs:{label:t.$t("dicManage.convertOfoilAndCO2"),align:"center"},scopedSlots:t._u([{key:"default",fn:function(e){var o=e.row;return[n("span",[t._v(t._s(o.convertOfoilAndCO2))])]}}])}),t._v(" "),n("el-table-column",{attrs:{label:t.$t("dicManage.upateDate"),width:"100",align:"center"},scopedSlots:t._u([{key:"default",fn:function(e){var o=e.row;return[n("span",[t._v(t._s(o.upateDate))])]}}])}),t._v(" "),n("el-table-column",{attrs:{label:t.$t("dicManage.remark"),align:"center"},scopedSlots:t._u([{key:"default",fn:function(e){var o=e.row;return[n("span",[t._v(t._s(o.remark))])]}}])}),t._v(" "),n("el-table-column",{attrs:{label:t.$t("common.operate"),align:"center",width:"180","class-name":"small-padding fixed-width"},scopedSlots:t._u([{key:"default",fn:function(e){var o=e.row;return[n("el-button",{attrs:{type:"primary",size:"mini"},on:{click:function(e){return t.handleUpdate(o)}}},[t._v("\n          "+t._s(t.$t("common.update"))+"\n        ")]),t._v(" "),n("el-button",{attrs:{size:"mini",type:"danger"},on:{click:function(e){return t.handleDelete(o)}}},[t._v("\n          "+t._s(t.$t("common.delete"))+"\n        ")])]}}])})],1),t._v(" "),n("pagination",{directives:[{name:"show",rawName:"v-show",value:t.total>0,expression:"total>0"}],staticStyle:{margin:"0px",padding:"0px"},attrs:{total:t.total,page:t.listQuery.page,limit:t.listQuery.limit},on:{"update:page":function(e){return t.$set(t.listQuery,"page",e)},"update:limit":function(e){return t.$set(t.listQuery,"limit",e)},pagination:t.getList}}),t._v(" "),n("el-dialog",{attrs:{width:"80%",title:t.textMap[t.dialogStatus],visible:t.dialogFormVisible,"append-to-body":!0,"close-on-click-modal":!1},on:{"update:visible":function(e){t.dialogFormVisible=e}}},[n("el-form",{ref:"dataForm",attrs:{rules:t.rules,model:t.temp,"label-position":"left","label-width":"100px"}},[n("el-row",[n("el-col",{attrs:{span:12}},[n("el-form-item",{attrs:{label:t.$t("dicManage.fuelOilName"),prop:"fuelOilName"}},[n("el-input",{attrs:{placeholder:t.$t("common.inputRemind")},model:{value:t.temp.fuelOilName,callback:function(e){t.$set(t.temp,"fuelOilName",e)},expression:"temp.fuelOilName"}})],1)],1),t._v(" "),n("el-col",{attrs:{span:12}},[n("el-form-item",{attrs:{label:t.$t("dicManage.fuelOilCode"),prop:"fuelOilCode","label-width":"150px"}},[n("el-input",{attrs:{placeholder:t.$t("common.inputRemind")},model:{value:t.temp.fuelOilCode,callback:function(e){t.$set(t.temp,"fuelOilCode",e)},expression:"temp.fuelOilCode"}})],1)],1)],1),t._v(" "),n("el-row",[n("el-col",{attrs:{span:12}},[n("el-form-item",{attrs:{label:t.$t("dicManage.fuelCarbonContent"),prop:"fuelCarbonContent"}},[n("el-input",{attrs:{placeholder:t.$t("common.inputRemind")},model:{value:t.temp.fuelCarbonContent,callback:function(e){t.$set(t.temp,"fuelCarbonContent",e)},expression:"temp.fuelCarbonContent"}})],1)],1),t._v(" "),n("el-col",{attrs:{span:12}},[n("el-form-item",{attrs:{label:t.$t("dicManage.convertOfoilAndCO2"),prop:"convertOfoilAndCO2","label-width":"150px"}},[n("el-input",{attrs:{placeholder:t.$t("common.inputRemind")},model:{value:t.temp.convertOfoilAndCO2,callback:function(e){t.$set(t.temp,"convertOfoilAndCO2",e)},expression:"temp.convertOfoilAndCO2"}})],1)],1)],1),t._v(" "),n("el-form-item",{attrs:{label:t.$t("dicManage.remark")}},[n("el-input",{attrs:{autosize:{minRows:2,maxRows:4},type:"textarea",placeholder:t.$t("common.inputRemind")},model:{value:t.temp.remark,callback:function(e){t.$set(t.temp,"remark",e)},expression:"temp.remark"}})],1)],1),t._v(" "),n("div",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[n("el-button",{on:{click:function(e){t.dialogFormVisible=!1}}},[t._v("\n        "+t._s(t.$t("common.close"))+"\n      ")]),t._v(" "),n("el-button",{attrs:{type:"primary"},on:{click:function(e){"create"===t.dialogStatus?t.createData():t.updateData()}}},[t._v("\n        "+t._s(t.$t("common.save"))+"\n      ")])],1)],1)],1)},a=[],r=n("d3e3"),i=n("bff4"),u=n("333d"),l=n("1593"),c=[{key:"1",display_name:"启用"},{key:"0",display_name:"冻结"}],s=c.reduce((function(t,e){return t[e.key]=e.display_name,t}),{}),d={name:"FuelOilType",components:{Pagination:u["a"]},directives:{adaptive:l["a"]},filters:{dicStatusFilter:function(t){return s[t]}},data:function(){return{tableKey:0,list:null,total:0,listLoading:!0,listQuery:{page:1,limit:10},dicStatusOptions:c,temp:{fuelOilName:void 0,fuelOilCode:void 0,fuelCarbonContent:void 0,convertOfoilAndCO2:void 0,remark:void 0},dialogFormVisible:!1,dialogStatus:"",textMap:{update:this.$t("common.edit"),create:this.$t("common.add")},rules:{fuelOilName:[{required:!0,message:this.$t("common.inputRequired"),trigger:"blur"}],fuelOilCode:[{required:!0,message:this.$t("common.inputRequired"),trigger:"blur"}],fuelCarbonContent:[{required:!0,message:this.$t("common.inputRequired"),trigger:"blur"}],convertOfoilAndCO2:[{required:!0,message:this.$t("common.inputRequired"),trigger:"blur"}]}}},created:function(){this.getList()},methods:{getList:function(){var t=this;this.listLoading=!0;var e="/dicManage/fuelOilList";Object(i["k"])(e,this.listQuery).then((function(e){t.list=e.data.items,t.total=e.data.total,setTimeout((function(){t.listLoading=!1}),150)}))},resetTemp:function(){this.temp={fuelOilName:"",fuelOilCode:"",fuelCarbonContent:"",convertOfoilAndCO2:"",remark:""}},handleCreate:function(){var t=this;this.resetTemp(),this.dialogStatus="create",this.dialogFormVisible=!0,this.$nextTick((function(){t.$refs["dataForm"].clearValidate()}))},createData:function(){var t=this;this.$refs["dataForm"].validate((function(e){if(e){var n="/dicManage/create";Object(r["M"])(n,t.temp).then((function(){t.list.unshift(t.temp),t.dialogFormVisible=!1,t.$notify({title:"Success",message:"Created Successfully",type:"success",duration:2e3})}))}}))},handleUpdate:function(t){var e=this;this.temp=Object.assign({},t),this.dialogStatus="update",this.dialogFormVisible=!0,this.$nextTick((function(){e.$refs["dataForm"].clearValidate()}))},updateData:function(){var t=this;this.$refs["dataForm"].validate((function(e){if(e){var n=Object.assign({},t.temp),o="/dicManage/update";Object(r["M"])(o,n)(n).then((function(){var e=!0,n=!1,o=void 0;try{for(var a,r=t.list[Symbol.iterator]();!(e=(a=r.next()).done);e=!0){var i=a.value;if(i.id===t.temp.id){var u=t.list.indexOf(i);t.list.splice(u,1,t.temp);break}}}catch(l){n=!0,o=l}finally{try{e||null==r.return||r.return()}finally{if(n)throw o}}t.dialogFormVisible=!1,t.$notify({title:"Success",message:"Update Successfully",type:"success",duration:2e3})}))}}))},handleDelete:function(t){this.$notify({title:"Success",message:"Delete Successfully",type:"success",duration:2e3});var e=this.list.indexOf(t);this.list.splice(e,1)}}},f=d,p=n("2877"),m=Object(p["a"])(f,o,a,!1,null,null,null);e["default"]=m.exports},d3e3:function(t,e,n){"use strict";n.d(e,"M",(function(){return a})),n.d(e,"b",(function(){return r})),n.d(e,"r",(function(){return i})),n.d(e,"U",(function(){return u})),n.d(e,"s",(function(){return l})),n.d(e,"u",(function(){return c})),n.d(e,"e",(function(){return s})),n.d(e,"G",(function(){return d})),n.d(e,"I",(function(){return f})),n.d(e,"D",(function(){return p})),n.d(e,"S",(function(){return m})),n.d(e,"T",(function(){return b})),n.d(e,"E",(function(){return h})),n.d(e,"o",(function(){return v})),n.d(e,"l",(function(){return g})),n.d(e,"j",(function(){return y})),n.d(e,"P",(function(){return O})),n.d(e,"N",(function(){return C})),n.d(e,"F",(function(){return j})),n.d(e,"g",(function(){return w})),n.d(e,"K",(function(){return _})),n.d(e,"O",(function(){return k})),n.d(e,"h",(function(){return $})),n.d(e,"C",(function(){return T})),n.d(e,"t",(function(){return S})),n.d(e,"L",(function(){return x})),n.d(e,"d",(function(){return D})),n.d(e,"i",(function(){return M})),n.d(e,"Q",(function(){return R})),n.d(e,"W",(function(){return E})),n.d(e,"q",(function(){return L})),n.d(e,"m",(function(){return F})),n.d(e,"V",(function(){return A})),n.d(e,"H",(function(){return N})),n.d(e,"R",(function(){return V})),n.d(e,"z",(function(){return q})),n.d(e,"x",(function(){return U})),n.d(e,"A",(function(){return I})),n.d(e,"y",(function(){return Q})),n.d(e,"p",(function(){return z})),n.d(e,"n",(function(){return P})),n.d(e,"f",(function(){return B})),n.d(e,"w",(function(){return H})),n.d(e,"v",(function(){return J})),n.d(e,"c",(function(){return K})),n.d(e,"J",(function(){return Y})),n.d(e,"k",(function(){return G})),n.d(e,"a",(function(){return W})),n.d(e,"B",(function(){return X}));var o=n("b775");function a(t,e){return Object(o["a"])({url:t,method:"post",data:e})}function r(t,e){return Object(o["a"])({url:t,method:"post",data:e})}function i(t,e){return Object(o["a"])({url:t,method:"post",responseType:"blob",headers:{"Content-Type":"application/json"},data:e})}function u(t,e){return Object(o["a"])({url:t,method:"post",data:e})}function l(t,e){return Object(o["a"])({url:t,method:"post",responseType:"blob",headers:{"Content-Type":"application/json"},data:e})}function c(t){return Object(o["a"])({url:"/mrv/generatorImoRpt",method:"post",data:t,timeout:6e5})}function s(t){return Object(o["a"])({url:"/mrv/deleteImoStdRpt",method:"post",data:t})}function d(t){return Object(o["a"])({url:"/mrv/imoLockOrUnlock",method:"post",data:t})}function f(t){return Object(o["a"])({url:"/mrv/imoReport",method:"post",data:t})}function p(t){return Object(o["a"])({url:"/mrv/getImoStdRpts",method:"post",data:t})}function m(t){return Object(o["a"])({url:"/mrv/saveImoRpt",method:"post",data:t})}function b(t){return Object(o["a"])({url:"/mrv/saveManualDcs",method:"post",data:t})}function h(t){return Object(o["a"])({url:"/mrv/getManuleDcs",method:"post",data:t})}function v(t){return Object(o["a"])({url:"/mrv/downLoadDcs",method:"post",responseType:"blob",headers:{"Content-Type":"application/json"},data:t})}function g(t){return Object(o["a"])({url:"/mrv/downLoadCollectionData",method:"post",responseType:"blob",headers:{"Content-Type":"application/json"},data:t})}function y(t){return Object(o["a"])({url:"/mrv/downLoadBdnData",method:"post",responseType:"blob",headers:{"Content-Type":"application/json"},data:t})}function O(t){return Object(o["a"])({url:"/sysUser/saveAppUser",method:"post",data:t})}function C(t){return Object(o["a"])({url:"/mrv/saveAndUpdatePeriodOil",method:"post",data:t})}function j(t){return Object(o["a"])({url:"/mrv/getPeriodOil",method:"post",data:t})}function w(t){return Object(o["a"])({url:"/mrv/deletePeriodOil",method:"post",data:t})}function _(t){return Object(o["a"])({url:"/mrv/lockOrUnlock",method:"post",data:t})}function k(t){return Object(o["a"])({url:"/shipManager/saveAndUpdateTfc",method:"post",data:t})}function $(t){return Object(o["a"])({url:"/shipManager/deleteTfc",method:"post",data:t})}function T(t){return Object(o["a"])({url:"/mrv/getEudcs",method:"post",data:t})}function S(t){return Object(o["a"])({url:"/mrv/generatorEuDcs",method:"post",data:t})}function x(t){return Object(o["a"])({url:"/mrv/lockOrUnlockEuDcs",method:"post",data:t})}function D(t){return Object(o["a"])({url:"/mrv/deleteEuDcs",method:"post",data:t})}function M(t){return Object(o["a"])({url:"/mrv/downEuDcs",method:"post",responseType:"blob",headers:{"Content-Type":"application/json"},data:t})}function R(t){return Object(o["a"])({url:"/mrv/saveEuDcs",method:"post",data:t})}function E(t){return Object(o["a"])({url:"/mrv/singalShipYear",method:"post",data:t})}function L(t){return Object(o["a"])({url:"/mrv/downLoadSigalShip",method:"post",responseType:"blob",headers:{"Content-Type":"application/json"},data:t})}function F(t){return Object(o["a"])({url:"/mrv/downLoadComPanyShip",method:"post",responseType:"blob",headers:{"Content-Type":"application/json"},data:t})}function A(t){return Object(o["a"])({url:"/mrv/shipComPanyYear",method:"post",data:t})}function N(t){return Object(o["a"])({url:"/mrv/imoReportLibya",method:"post",data:t})}function V(t){return Object(o["a"])({url:"/flagDocChange/saveFlagDocChange",method:"post",data:t})}function q(t){return Object(o["a"])({url:"/mrv/getEnergyEfficencyTrend",method:"post",data:t})}function U(t){return Object(o["a"])({url:"/mrv/getCompareAnalysisData",method:"post",data:t})}function I(t){return Object(o["a"])({url:"/mrv/getEnergyEfficencyTrendData",method:"post",data:t})}function Q(t){return Object(o["a"])({url:"/mrv/getCompareAnalysisDatas",method:"post",data:t})}function z(t){return Object(o["a"])({url:"/mrv/downLoadEnergyEfficencyTrendData",method:"post",responseType:"blob",headers:{"Content-Type":"application/json"},data:t})}function P(t){return Object(o["a"])({url:"/mrv/downLoadCompareAnalysisData",method:"post",responseType:"blob",headers:{"Content-Type":"application/json"},data:t})}function B(t){return Object(o["a"])({url:"/flagDocChange/deleteMultiFlagDocChangeById",method:"post",data:t})}function H(t){return Object(o["a"])({url:"/energyEfficiency/getCmsaStdRpt",method:"post",data:t})}function J(t){return Object(o["a"])({url:"/energyEfficiency/getCmsaRpt",method:"post",data:t})}function K(t){return Object(o["a"])({url:"/energyEfficiency/deleteCmsaRpt",method:"post",data:t})}function Y(t){return Object(o["a"])({url:"/energyEfficiency/lockOrUnLockCmsaRpt",method:"post",data:t})}function G(t){return Object(o["a"])({url:"/energyEfficiency/downLoadCmsaRpt",method:"post",responseType:"blob",headers:{"Content-Type":"application/json"},data:t})}function W(t){return Object(o["a"])({url:"/energyEfficiency/cmsaRptStatusTag",method:"post",data:t})}function X(t){return Object(o["a"])({url:"/mrv/getEnergyEfficencyTrendMessage",method:"post",data:t})}}}]);