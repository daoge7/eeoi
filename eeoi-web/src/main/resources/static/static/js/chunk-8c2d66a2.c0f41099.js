(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-8c2d66a2"],{1593:function(e,t,n){"use strict";var o=n("2f7b"),r=function(e,t,n){var o=n.componentInstance,r=t.value;if(!o.height)throw new Error("el-$table must set the height. Such as height='100px'");var a=r&&r.bottomOffset||30;if(o){var i=window.innerHeight-e.getBoundingClientRect().top-a;setTimeout((function(){o.layout.setHeight(i),o.doLayout()}),1)}},a={bind:function(e,t,n){e.resizeListener=function(){r(e,t,n)},Object(o["a"])(window.document.body,e.resizeListener)},inserted:function(e,t,n){r(e,t,n)},unbind:function(e){Object(o["b"])(window.document.body,e.resizeListener)}},i=function(e){e.directive("adaptive",a)};window.Vue&&(window["adaptive"]=a,Vue.use(i)),a.install=i;t["a"]=a},6724:function(e,t,n){"use strict";n("8d41");var o="@@wavesContext";function r(e,t){function n(n){var o=Object.assign({},t.value),r=Object.assign({ele:e,type:"hit",color:"rgba(0, 0, 0, 0.15)"},o),a=r.ele;if(a){a.style.position="relative",a.style.overflow="hidden";var i=a.getBoundingClientRect(),s=a.querySelector(".waves-ripple");switch(s?s.className="waves-ripple":(s=document.createElement("span"),s.className="waves-ripple",s.style.height=s.style.width=Math.max(i.width,i.height)+"px",a.appendChild(s)),r.type){case"center":s.style.top=i.height/2-s.offsetHeight/2+"px",s.style.left=i.width/2-s.offsetWidth/2+"px";break;default:s.style.top=(n.pageY-i.top-s.offsetHeight/2-document.documentElement.scrollTop||document.body.scrollTop)+"px",s.style.left=(n.pageX-i.left-s.offsetWidth/2-document.documentElement.scrollLeft||document.body.scrollLeft)+"px"}return s.style.backgroundColor=r.color,s.className="waves-ripple z-active",!1}}return e[o]?e[o].removeHandle=n:e[o]={removeHandle:n},n}var a={bind:function(e,t){e.addEventListener("click",r(e,t),!1)},update:function(e,t){e.removeEventListener("click",e[o].removeHandle,!1),e.addEventListener("click",r(e,t),!1)},unbind:function(e){e.removeEventListener("click",e[o].removeHandle,!1),e[o]=null,delete e[o]}},i=function(e){e.directive("waves",a)};window.Vue&&(window.waves=a,Vue.use(i)),a.install=i;t["a"]=a},"8d41":function(e,t,n){},a888:function(e,t,n){"use strict";var o={bind:function(e,t,n){var o=e.querySelector(".el-dialog__header"),r=e.querySelector(".el-dialog");o.style.cssText+=";cursor:move;",r.style.cssText+=";top:0px;";var a=function(){return window.document.currentStyle?function(e,t){return e.currentStyle[t]}:function(e,t){return getComputedStyle(e,!1)[t]}}();o.onmousedown=function(e){var t=e.clientX-o.offsetLeft,i=e.clientY-o.offsetTop,s=r.offsetWidth,u=r.offsetHeight,c=document.body.clientWidth,l=document.body.clientHeight,d=r.offsetLeft,p=c-r.offsetLeft-s,f=r.offsetTop,m=l-r.offsetTop-u,h=a(r,"left"),y=a(r,"top");h.includes("%")?(h=+document.body.clientWidth*(+h.replace(/\%/g,"")/100),y=+document.body.clientHeight*(+y.replace(/\%/g,"")/100)):(h=+h.replace(/\px/g,""),y=+y.replace(/\px/g,"")),document.onmousemove=function(e){var o=e.clientX-t,a=e.clientY-i;-o>d?o=-d:o>p&&(o=p),-a>f?a=-f:a>m&&(a=m),r.style.cssText+=";left:".concat(o+h,"px;top:").concat(a+y,"px;"),n.child.$emit("dragDialog")},document.onmouseup=function(e){document.onmousemove=null,document.onmouseup=null}}}},r=function(e){e.directive("el-drag-dialog",o)};window.Vue&&(window["el-drag-dialog"]=o,Vue.use(r)),o.install=r;t["a"]=o},c316:function(e,t,n){"use strict";n.r(t);var o=function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("el-dialog",{directives:[{name:"el-drag-dialog",rawName:"v-el-drag-dialog"}],attrs:{title:e.textMap[e.dialogStatus],visible:e.dialogFormVisible,"close-on-click-modal":!1,width:"80%","append-to-body":!0},on:{"update:visible":function(t){e.dialogFormVisible=t},dragDialog:function(e){return this.$refs.select.blur()}}},[n("el-form",{ref:"dataForm",staticStyle:{width:"90%","margin-left":"20px"},attrs:{rules:e.rules,model:e.temp,"label-position":"left","label-width":"70px"}},[n("el-row",[n("el-col",{attrs:{span:12}},[n("el-form-item",{attrs:{label:e.$t("sysPcappVersion.sysCode"),prop:"sysCode","label-width":"110px"}},[n("el-select",{staticClass:"filter-item",staticStyle:{width:"100%"},attrs:{placeholder:e.$t("common.selectRemind"),disabled:e.readonly},model:{value:e.temp.sysCode,callback:function(t){e.$set(e.temp,"sysCode",t)},expression:"temp.sysCode"}},e._l(e.sysCodeOptions,(function(e){return n("el-option",{key:e.key,attrs:{label:e.display_name,value:e.key}})})),1)],1)],1),e._v(" "),n("el-col",{attrs:{span:12}},[n("el-form-item",{attrs:{label:e.$t("sysPcappVersion.upType"),prop:"sysCode","label-width":"110px"}},[n("el-select",{staticClass:"filter-item",staticStyle:{width:"100%"},attrs:{placeholder:e.$t("common.selectRemind"),disabled:e.readonly},model:{value:e.temp.upType,callback:function(t){e.$set(e.temp,"upType",t)},expression:"temp.upType"}},e._l(e.upTypeOptions,(function(e){return n("el-option",{key:e.key,attrs:{label:e.display_name,value:e.key}})})),1)],1)],1)],1),e._v(" "),n("el-row",[n("el-col",{attrs:{span:12}},[n("el-form-item",{attrs:{label:e.$t("sysPcappVersion.preVersion"),prop:"preVersion","label-width":"110px"}},[n("el-input",{attrs:{readonly:e.readonly},model:{value:e.temp.preVersion,callback:function(t){e.$set(e.temp,"preVersion",t)},expression:"temp.preVersion"}})],1)],1),e._v(" "),n("el-col",{attrs:{span:12}},[n("el-form-item",{attrs:{label:e.$t("sysPcappVersion.newVersion"),prop:"newVersion","label-width":"110px"}},[n("el-input",{attrs:{readonly:e.readonly},model:{value:e.temp.newVersion,callback:function(t){e.$set(e.temp,"newVersion",t)},expression:"temp.newVersion"}})],1)],1)],1),e._v(" "),n("el-row",[n("el-col",{attrs:{span:12}},[n("el-form-item",{attrs:{label:e.$t("sysPcappVersion.recStatus"),prop:"recStatus","label-width":"110px"}},[n("el-select",{staticClass:"filter-item",staticStyle:{width:"100%"},attrs:{placeholder:e.$t("common.selectRemind"),disabled:e.readonly},model:{value:e.temp.recStatus,callback:function(t){e.$set(e.temp,"recStatus",t)},expression:"temp.recStatus"}},e._l(e.recStatusOptions,(function(e){return n("el-option",{key:e.key,attrs:{label:e.display_name,value:e.key}})})),1)],1)],1)],1),e._v(" "),n("el-row",[n("el-col",{attrs:{span:24}},[n("el-form-item",{attrs:{label:e.$t("sysPcappVersion.upContent"),"label-width":"110px",prop:"upContent"}},[n("el-input",{attrs:{type:"textarea",autosize:{minRows:10,maxRows:20},readonly:e.readonly},model:{value:e.temp.upContent,callback:function(t){e.$set(e.temp,"upContent",t)},expression:"temp.upContent"}})],1)],1)],1),e._v(" "),n("el-row",[n("el-col",{attrs:{span:24}},[n("el-form-item",{attrs:{label:e.$t("sysDocNotice.fileUpload"),prop:"fileIds","label-width":"110px"}},[n("el-upload",{staticClass:"upload-demo",attrs:{action:"/sysUpload/upload",data:e.currentFile,"on-preview":e.handlePreview,"on-remove":e.handleRemove,"before-remove":e.beforeRemove,"on-change":e.handleChange,"before-upload":e.handleBeforeUpload,"on-success":e.handleSuccess,multiple:"",limit:1,"on-exceed":e.handleExceed,"file-list":e.fileList}},[n("el-button",{attrs:{slot:"trigger",size:"small",type:"primary"},slot:"trigger"},[e._v(e._s(e.$t("sysDocNotice.clickToUpload")))]),e._v(" "),n("div",{staticClass:"el-upload__tip",attrs:{slot:"tip"},slot:"tip"}),e._v("\n            "+e._s(e.$t("sysDocNotice.fileSizeLimitation"))+"\n          ")],1)],1)],1)],1)],1),e._v(" "),n("div",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[n("el-button",{on:{click:function(t){return e.closeDio()}}},[e._v("\n      "+e._s(e.$t("common.close"))+"\n    ")]),e._v(" "),n("el-button",{directives:[{name:"show",rawName:"v-show",value:e.btnShow,expression:"btnShow"}],attrs:{type:"primary"},on:{click:function(t){"create"===e.dialogStatus?e.createData():e.updateData()}}},[e._v("\n      "+e._s(e.$t("common.save"))+"\n    ")])],1)],1)},r=[],a=n("f9ac"),i=n("bff4"),s=n("6724"),u=n("ed08"),c=n("1593"),l=n("a888"),d=n("d3e3"),p=[{key:"0",display_name:"拟稿"},{key:"1",display_name:"发布"},{key:"2",display_name:"失效"}],f=[{key:"01",display_name:"PC"},{key:"02",display_name:"APP"}],m=[{key:"0",display_name:"全量"},{key:"1",display_name:"增量"}],h={name:"PcAppMng",components:{},directives:{waves:s["a"],adaptive:c["a"],elDragDialog:l["a"]},data:function(){return{downloadFile:{path:"",fileName:""},recStatusOptions:p,sysCodeOptions:f,upTypeOptions:m,currentFile:{fileId:""},temp:{id:"",sysCode:"",upType:"",preVersion:"",newVersion:"",recStatus:"",upContent:"",fileIds:""},dialogFormVisible:!1,dialogStatus:"",textMap:{update:"编辑",create:"新增",detail:"详情"},rules:{sysCode:[{required:!0,message:"必填项",trigger:"blur"}],upType:[{required:!0,message:"必填项",trigger:"blur"}],preVersion:[{required:!0,message:"必填项",trigger:"blur"}],newVersion:[{required:!0,message:"必填项",trigger:"blur"}],recStatus:[{required:!0,message:"必填项",trigger:"blur"}],upContent:[{required:!0,message:"必填项",trigger:"blur"}]},tableChecked:[],tableChecked2:[],fileList:[],readonly:!1,btnShow:!0}},created:function(){},methods:{getFileList:function(e){var t=this;this.listLoading=!0;var n="/sysPcappVersion/findSysFilsListByNoticeId/"+e;Object(i["k"])(n).then((function(e){t.fileList=e.data.data,setTimeout((function(){t.listLoading=!1}),1500)}))},userDetail2:function(e,t){this.fileList=[],this.listLoading=!0,this.temp=Object.assign({},e),this.getFileList(e.id),this.dialogFormVisible=!0,"edit"===t?(this.dialogStatus="update",this.readonly=!1,this.btnShow=!0):(this.readonly=!0,this.dialogStatus="detail",this.btnShow=!1),this.listLoading=!1},userDetail:function(e,t){var n=this;this.listLoading=!0,Object(a["p"])(e).then((function(e){if(n.temp=Object.assign({},e.data.data),n.dialogFormVisible=!0,"edit"===t?(n.dialogStatus="update",n.readonly=!1,n.btnShow=!0):(n.readonly=!0,n.dialogStatus="detail",n.btnShow=!1),void 0!==n.temp.userRole&&n.temp.userRole.length>0){for(var o=[],r=0;r<n.temp.userRole.length;r++)o.push(n.temp.userRole[r]);n.temp.userRole=o}setTimeout((function(){n.listLoading=!1}),1500)}))},handleDelete:function(e){var t=this;!0===confirm("确定要删除？")&&Object(i["j"])("/sysPcappVersion/deleteSysPcappVersionById/"+e.id).then((function(e){e.data.result?(t.$notify({title:"Success",message:"删除成功",type:"success",duration:2e3}),t.$emit("inidData",!0)):t.$notify({title:"Fail",message:"删除失败",type:"fail",duration:2e3})}))},handleSelectionChange:function(e){this.tableChecked=e},handleRelationCompanySelectionChange:function(e){this.tableChecked2=e},resetTemp:function(){this.temp={id:void 0,num:1,docTitle:"",docType:"",docNumber:"",fileIds:""}},handleCreate:function(){var e=this;this.resetTemp(),this.fileList=[],this.dialogStatus="create",this.dialogFormVisible=!0,this.btnShow=!0,this.$nextTick((function(){e.$refs["dataForm"].clearValidate()}))},handleCreateRelation:function(){},createData:function(){var e=this;this.$refs["dataForm"].validate((function(t){if(t){var n="/sysPcappVersion/saveOrUpdateSysPcappVersion";Object(d["M"])(n,e.temp).then((function(t){e.$emit("inidData",!0),e.dialogFormVisible=!1,e.$notify({title:"Success",message:"Created Successfully",type:"success",duration:2e3})}))}}))},updateData:function(){var e=this;this.$refs["dataForm"].validate((function(t){if(t){var n=Object.assign({},e.temp),o="/sysPcappVersion/saveOrUpdateSysPcappVersion";Object(d["M"])(o,n).then((function(t){e.$emit("inidData",!0),e.fileList=[],e.dialogFormVisible=!1,e.$notify({title:"Success",message:"Update Successfully",type:"success",duration:2e3})}))}}))},formatJson:function(e,t){return t.map((function(t){return e.map((function(e){return"timestamp"===e?Object(u["e"])(t[e]):t[e]}))}))},getSortClass:function(e){var t=this.listQuery.sort;return t==="+".concat(e)?"ascending":t==="-".concat(e)?"descending":""},selectTrigger:function(e){},handleRemove:function(e,t){},closeDio:function(){this.dialogFormVisible=!1,this.fileList=[]},handlePreview:function(e){window.open("/sysPcappVersion/downloadFile?path="+e.url+"&fileName="+e.name)},handleExceed:function(e,t){this.$message.warning("当前限制选择 1 个文件，本次选择了 ".concat(e.length," 个文件，共选择了 ").concat(e.length+t.length," 个文件"))},beforeRemove:function(e,t){var n=this;this.readonly?this.$notify({title:"Cannot delete",message:"Cannot delete",type:"fail",duration:2e3}):this.$confirm("确定移除 ".concat(e.name,"？")).then((function(){var t=new RegExp(e.uid+",","g"),o=new RegExp(e.uid+"_newUploadFile,","g");n.temp.fileIds=n.temp.fileIds.replace(t,""),n.temp.fileIds=n.temp.fileIds.replace(o,"");var r=new RegExp(e.name+",","g");n.temp.fileNames=n.temp.fileNames.replace(r,"")}))},submitUpload:function(){this.$refs.upload.submit()},handleChange:function(e,t){this.fileList=t.slice(-3)},handleSuccess:function(e,t,n){"undefined"!==typeof this.temp.fileIds&&(this.temp.fileIds=this.temp.fileIds+t.uid+"_newUploadFile,"),"undefined"!==typeof this.temp.fileNames&&null!==this.temp.fileNames?this.temp.fileNames=this.temp.fileNames+t.name+",":this.temp.fileNames=t.name+","},handleBeforeUpload:function(e){this.currentFile.fileId=e.uid}}},y=h,b=n("2877"),g=Object(b["a"])(y,o,r,!1,null,null,null);t["default"]=g.exports},d3e3:function(e,t,n){"use strict";n.d(t,"M",(function(){return r})),n.d(t,"b",(function(){return a})),n.d(t,"r",(function(){return i})),n.d(t,"U",(function(){return s})),n.d(t,"s",(function(){return u})),n.d(t,"u",(function(){return c})),n.d(t,"e",(function(){return l})),n.d(t,"G",(function(){return d})),n.d(t,"I",(function(){return p})),n.d(t,"D",(function(){return f})),n.d(t,"S",(function(){return m})),n.d(t,"T",(function(){return h})),n.d(t,"E",(function(){return y})),n.d(t,"o",(function(){return b})),n.d(t,"l",(function(){return g})),n.d(t,"j",(function(){return v})),n.d(t,"P",(function(){return w})),n.d(t,"N",(function(){return O})),n.d(t,"F",(function(){return j})),n.d(t,"g",(function(){return C})),n.d(t,"K",(function(){return S})),n.d(t,"O",(function(){return T})),n.d(t,"h",(function(){return k})),n.d(t,"C",(function(){return L})),n.d(t,"t",(function(){return D})),n.d(t,"L",(function(){return x})),n.d(t,"d",(function(){return V})),n.d(t,"i",(function(){return R})),n.d(t,"Q",(function(){return $})),n.d(t,"W",(function(){return _})),n.d(t,"q",(function(){return E})),n.d(t,"m",(function(){return F})),n.d(t,"V",(function(){return U})),n.d(t,"H",(function(){return P})),n.d(t,"R",(function(){return I})),n.d(t,"z",(function(){return N})),n.d(t,"x",(function(){return M})),n.d(t,"A",(function(){return H})),n.d(t,"y",(function(){return q})),n.d(t,"p",(function(){return A})),n.d(t,"n",(function(){return B})),n.d(t,"f",(function(){return z})),n.d(t,"w",(function(){return W})),n.d(t,"v",(function(){return Y})),n.d(t,"c",(function(){return J})),n.d(t,"J",(function(){return X})),n.d(t,"k",(function(){return G})),n.d(t,"a",(function(){return Q})),n.d(t,"B",(function(){return K}));var o=n("b775");function r(e,t){return Object(o["a"])({url:e,method:"post",data:t})}function a(e,t){return Object(o["a"])({url:e,method:"post",data:t})}function i(e,t){return Object(o["a"])({url:e,method:"post",responseType:"blob",headers:{"Content-Type":"application/json"},data:t})}function s(e,t){return Object(o["a"])({url:e,method:"post",data:t})}function u(e,t){return Object(o["a"])({url:e,method:"post",responseType:"blob",headers:{"Content-Type":"application/json"},data:t})}function c(e){return Object(o["a"])({url:"/mrv/generatorImoRpt",method:"post",data:e,timeout:6e5})}function l(e){return Object(o["a"])({url:"/mrv/deleteImoStdRpt",method:"post",data:e})}function d(e){return Object(o["a"])({url:"/mrv/imoLockOrUnlock",method:"post",data:e})}function p(e){return Object(o["a"])({url:"/mrv/imoReport",method:"post",data:e})}function f(e){return Object(o["a"])({url:"/mrv/getImoStdRpts",method:"post",data:e})}function m(e){return Object(o["a"])({url:"/mrv/saveImoRpt",method:"post",data:e})}function h(e){return Object(o["a"])({url:"/mrv/saveManualDcs",method:"post",data:e})}function y(e){return Object(o["a"])({url:"/mrv/getManuleDcs",method:"post",data:e})}function b(e){return Object(o["a"])({url:"/mrv/downLoadDcs",method:"post",responseType:"blob",headers:{"Content-Type":"application/json"},data:e})}function g(e){return Object(o["a"])({url:"/mrv/downLoadCollectionData",method:"post",responseType:"blob",headers:{"Content-Type":"application/json"},data:e})}function v(e){return Object(o["a"])({url:"/mrv/downLoadBdnData",method:"post",responseType:"blob",headers:{"Content-Type":"application/json"},data:e})}function w(e){return Object(o["a"])({url:"/sysUser/saveAppUser",method:"post",data:e})}function O(e){return Object(o["a"])({url:"/mrv/saveAndUpdatePeriodOil",method:"post",data:e})}function j(e){return Object(o["a"])({url:"/mrv/getPeriodOil",method:"post",data:e})}function C(e){return Object(o["a"])({url:"/mrv/deletePeriodOil",method:"post",data:e})}function S(e){return Object(o["a"])({url:"/mrv/lockOrUnlock",method:"post",data:e})}function T(e){return Object(o["a"])({url:"/shipManager/saveAndUpdateTfc",method:"post",data:e})}function k(e){return Object(o["a"])({url:"/shipManager/deleteTfc",method:"post",data:e})}function L(e){return Object(o["a"])({url:"/mrv/getEudcs",method:"post",data:e})}function D(e){return Object(o["a"])({url:"/mrv/generatorEuDcs",method:"post",data:e})}function x(e){return Object(o["a"])({url:"/mrv/lockOrUnlockEuDcs",method:"post",data:e})}function V(e){return Object(o["a"])({url:"/mrv/deleteEuDcs",method:"post",data:e})}function R(e){return Object(o["a"])({url:"/mrv/downEuDcs",method:"post",responseType:"blob",headers:{"Content-Type":"application/json"},data:e})}function $(e){return Object(o["a"])({url:"/mrv/saveEuDcs",method:"post",data:e})}function _(e){return Object(o["a"])({url:"/mrv/singalShipYear",method:"post",data:e})}function E(e){return Object(o["a"])({url:"/mrv/downLoadSigalShip",method:"post",responseType:"blob",headers:{"Content-Type":"application/json"},data:e})}function F(e){return Object(o["a"])({url:"/mrv/downLoadComPanyShip",method:"post",responseType:"blob",headers:{"Content-Type":"application/json"},data:e})}function U(e){return Object(o["a"])({url:"/mrv/shipComPanyYear",method:"post",data:e})}function P(e){return Object(o["a"])({url:"/mrv/imoReportLibya",method:"post",data:e})}function I(e){return Object(o["a"])({url:"/flagDocChange/saveFlagDocChange",method:"post",data:e})}function N(e){return Object(o["a"])({url:"/mrv/getEnergyEfficencyTrend",method:"post",data:e})}function M(e){return Object(o["a"])({url:"/mrv/getCompareAnalysisData",method:"post",data:e})}function H(e){return Object(o["a"])({url:"/mrv/getEnergyEfficencyTrendData",method:"post",data:e})}function q(e){return Object(o["a"])({url:"/mrv/getCompareAnalysisDatas",method:"post",data:e})}function A(e){return Object(o["a"])({url:"/mrv/downLoadEnergyEfficencyTrendData",method:"post",responseType:"blob",headers:{"Content-Type":"application/json"},data:e})}function B(e){return Object(o["a"])({url:"/mrv/downLoadCompareAnalysisData",method:"post",responseType:"blob",headers:{"Content-Type":"application/json"},data:e})}function z(e){return Object(o["a"])({url:"/flagDocChange/deleteMultiFlagDocChangeById",method:"post",data:e})}function W(e){return Object(o["a"])({url:"/energyEfficiency/getCmsaStdRpt",method:"post",data:e})}function Y(e){return Object(o["a"])({url:"/energyEfficiency/getCmsaRpt",method:"post",data:e})}function J(e){return Object(o["a"])({url:"/energyEfficiency/deleteCmsaRpt",method:"post",data:e})}function X(e){return Object(o["a"])({url:"/energyEfficiency/lockOrUnLockCmsaRpt",method:"post",data:e})}function G(e){return Object(o["a"])({url:"/energyEfficiency/downLoadCmsaRpt",method:"post",responseType:"blob",headers:{"Content-Type":"application/json"},data:e})}function Q(e){return Object(o["a"])({url:"/energyEfficiency/cmsaRptStatusTag",method:"post",data:e})}function K(e){return Object(o["a"])({url:"/mrv/getEnergyEfficencyTrendMessage",method:"post",data:e})}},f9ac:function(e,t,n){"use strict";n.d(t,"q",(function(){return r})),n.d(t,"r",(function(){return a})),n.d(t,"p",(function(){return i})),n.d(t,"f",(function(){return s})),n.d(t,"o",(function(){return u})),n.d(t,"k",(function(){return c})),n.d(t,"j",(function(){return l})),n.d(t,"e",(function(){return d})),n.d(t,"n",(function(){return p})),n.d(t,"g",(function(){return f})),n.d(t,"c",(function(){return m})),n.d(t,"l",(function(){return h})),n.d(t,"h",(function(){return y})),n.d(t,"d",(function(){return b})),n.d(t,"m",(function(){return g})),n.d(t,"i",(function(){return v})),n.d(t,"b",(function(){return w})),n.d(t,"a",(function(){return O}));var o=n("b775");function r(e){return Object(o["a"])({url:"/sysUser/userList",method:"get",params:e})}function a(e){return Object(o["a"])({url:"/gcClient/findGcClientlist",method:"get",params:e})}function i(e){return Object(o["a"])({url:"/sysUser/userDetail",method:"get",params:{id:e}})}function s(e){return Object(o["a"])({url:"/sysUser/createUser",method:"post",data:e})}function u(e){return Object(o["a"])({url:"/sysUser/createUser",method:"post",data:e})}function c(e){return Object(o["a"])({url:"/sysRole/roleList",method:"get",params:e})}function l(e){return Object(o["a"])({url:"/sysRole/roleDetail",method:"get",params:{roleId:e}})}function d(e){return Object(o["a"])({url:"/sysRole/saveRole",method:"post",data:e})}function p(e){return Object(o["a"])({url:"/sysRole/updateRole",method:"post",data:e})}function f(e){return Object(o["a"])({url:"/sysDic/dicList",method:"get",params:e})}function m(e){return Object(o["a"])({url:"/sysDic/createDic",method:"post",data:e})}function h(e){return Object(o["a"])({url:"/sysDic/updateDic",method:"post",data:e})}function y(e){return Object(o["a"])({url:"/sysFunc/funcList",method:"get",params:e})}function b(e){return Object(o["a"])({url:"/sysFunc/createFunc",method:"post",data:e})}function g(e){return Object(o["a"])({url:"/sysFunc/updateFunc",method:"post",data:e})}function v(e){return Object(o["a"])({url:"/sysLog/logList",method:"get",params:e})}function w(e){return Object(o["a"])({url:"/sysUser/userList",method:"get",params:e})}function O(e){return Object(o["a"])({url:"/sysShipAuth/shipList",method:"get",params:e})}}}]);