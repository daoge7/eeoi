(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-14733a8a","chunk-1740ccb6"],{"09f4":function(t,e,i){"use strict";i.d(e,"a",(function(){return s})),Math.easeInOutQuad=function(t,e,i,n){return t/=n/2,t<1?i/2*t*t+e:(t--,-i/2*(t*(t-2)-1)+e)};var n=function(){return window.requestAnimationFrame||window.webkitRequestAnimationFrame||window.mozRequestAnimationFrame||function(t){window.setTimeout(t,1e3/60)}}();function a(t){document.documentElement.scrollTop=t,document.body.parentNode.scrollTop=t,document.body.scrollTop=t}function o(){return document.documentElement.scrollTop||document.body.parentNode.scrollTop||document.body.scrollTop}function s(t,e,i){var s=o(),r=t-s,l=20,c=0;e="undefined"===typeof e?500:e;var u=function t(){c+=l;var o=Math.easeInOutQuad(c,s,r,e);a(o),c<e?n(t):i&&"function"===typeof i&&i()};u()}},1593:function(t,e,i){"use strict";var n=i("2f7b"),a=function(t,e,i){var n=i.componentInstance,a=e.value;if(!n.height)throw new Error("el-$table must set the height. Such as height='100px'");var o=a&&a.bottomOffset||30;if(n){var s=window.innerHeight-t.getBoundingClientRect().top-o;setTimeout((function(){n.layout.setHeight(s),n.doLayout()}),1)}},o={bind:function(t,e,i){t.resizeListener=function(){a(t,e,i)},Object(n["a"])(window.document.body,t.resizeListener)},inserted:function(t,e,i){a(t,e,i)},unbind:function(t){Object(n["b"])(window.document.body,t.resizeListener)}},s=function(t){t.directive("adaptive",o)};window.Vue&&(window["adaptive"]=o,Vue.use(s)),o.install=s;e["a"]=o},"219a":function(t,e,i){"use strict";i.r(e);var n=function(){var t=this,e=t.$createElement,i=t._self._c||e;return i("div",{staticClass:"app-container"},[i("div",{staticClass:"filter-container"},[i("div",{staticStyle:{padding:"10px 10px 0"}},[i("label",{staticClass:"radio-label"},[t._v(t._s(t.$t("MRV.shipCompany")))]),t._v(" "),i("el-input",{staticClass:"filter-item",staticStyle:{width:"240px","margin-right":"10px"},attrs:{placeholder:t.$t("common.inputRemind")},nativeOn:{keyup:function(e){return!e.type.indexOf("key")&&t._k(e.keyCode,"enter",13,e.key,"Enter")?null:t.handleFilter(e)}},model:{value:t.listQuery.keyword,callback:function(e){t.$set(t.listQuery,"keyword",e)},expression:"listQuery.keyword"}}),t._v(" "),i("label",{staticClass:"radio-label"},[t._v(t._s(t.$t("MRV.FlagState")))]),t._v(" "),i("el-input",{staticClass:"filter-item",staticStyle:{width:"240px","margin-right":"10px"},attrs:{placeholder:t.$t("common.inputRemind")},model:{value:t.listQuery.flag,callback:function(e){t.$set(t.listQuery,"flag",e)},expression:"listQuery.flag"}})],1),t._v(" "),i("div",{staticStyle:{padding:"5px 10px 0"}},[i("label",{staticClass:"radio-label"},[t._v(t._s(t.$t("MRV.Period")))]),t._v(" "),i("el-date-picker",{staticStyle:{width:"150px"},attrs:{editable:!0,type:"date",align:"left",placeholder:t.$t("common.datePickerRemind")},model:{value:t.listQuery.startTime,callback:function(e){t.$set(t.listQuery,"startTime",e)},expression:"listQuery.startTime"}}),t._v("~\n      "),i("el-date-picker",{staticStyle:{width:"150px"},attrs:{editable:!0,type:"date",align:"left",placeholder:t.$t("common.datePickerRemind")},model:{value:t.listQuery.endTime,callback:function(e){t.$set(t.listQuery,"endTime",e)},expression:"listQuery.endTime"}}),t._v(" "),i("el-button",{staticClass:"filter-item",staticStyle:{"margin-left":"20px"},attrs:{icon:"el-icon-search"},on:{click:t.handleFilter}},[t._v(t._s(t.$t("common.search")))]),t._v(" "),i("el-button",{staticClass:"filter-item",attrs:{type:"info",icon:"el-icon-setting"},on:{click:t.handleRest}},[t._v(t._s(t.$t("common.reset")))])],1),t._v(" "),i("div",{staticStyle:{"margin-top":"10px"}},[i("el-button",{attrs:{type:"primary",icon:"el-icon-plus"},on:{click:t.addclick}},[t._v(t._s(t.$t("common.add")))])],1),t._v(" "),i("el-table",{directives:[{name:"loading",rawName:"v-loading",value:t.listLoading,expression:"listLoading"},{name:"adaptive",rawName:"v-adaptive",value:{bottomOffset:38},expression:"{bottomOffset: 38}"}],staticStyle:{width:"100%",overflow:"auto","margin-top":"10px"},attrs:{data:t.dataTreeGrid,border:"",fit:"",height:t.tableHeight,"highlight-current-row":"","row-style":{height:"35px"},"cell-style":{padding:"0"},"row-key":"id"}},[i("el-table-column",{attrs:{type:"selection",width:"55",align:"center"}}),t._v(" "),i("el-table-column",{attrs:{label:"IMO NO.",align:"center",width:"auto","show-overflow-tooltip":""},scopedSlots:t._u([{key:"default",fn:function(e){var n=e.row;return[i("span",[t._v(t._s(n.imoNu))])]}}])}),t._v(" "),i("el-table-column",{attrs:{label:t.$t("shipBattery.shipName"),align:"center",width:"auto","show-overflow-tooltip":""},scopedSlots:t._u([{key:"default",fn:function(e){var n=e.row;return[i("span",{on:{click:function(e){return t.handlEnit(n)}}},[t._v(t._s(n.shipName))])]}}])}),t._v(" "),i("el-table-column",{attrs:{label:t.$t("ship.manager"),align:"center",width:"auto","show-overflow-tooltip":""},scopedSlots:t._u([{key:"default",fn:function(e){var n=e.row;return[i("span",[t._v(t._s(n.shipName))])]}}])}),t._v(" "),i("el-table-column",{attrs:{label:t.$t("ship.shipFlag"),width:"auto",align:"center","show-overflow-tooltip":""},scopedSlots:t._u([{key:"default",fn:function(e){var n=e.row;return[i("span",[t._v(t._s(n.flag))])]}}])}),t._v(" "),i("el-table-column",{attrs:{label:t.$t("ship.shipType"),width:"auto",align:"center","show-overflow-tooltip":""},scopedSlots:t._u([{key:"default",fn:function(e){var n=e.row;return[i("span",[t._v(t._s(n.shipType))])]}}])}),t._v(" "),i("el-table-column",{attrs:{label:t.$t("MRV.Reportingyear"),width:"auto",align:"center","show-overflow-tooltip":""},scopedSlots:t._u([{key:"default",fn:function(e){var n=e.row;return[i("span",[t._v(t._s(n.euMrvYear))])]}}])}),t._v(" "),i("el-table-column",{attrs:{label:t.$t("MRV.Reportstatus"),width:"auto",align:"center","show-overflow-tooltip":""},scopedSlots:t._u([{key:"default",fn:function(e){var n=e.row;return["1"==n.rptStatus?i("span",[t._v(t._s(t.$t("MRV.Registration")))]):t._e(),t._v(" "),"2"==n.rptStatus?i("span",[t._v(t._s(t.$t("MRV.Lockstate")))]):t._e(),t._v(" "),i("span",[t._v(t._s(n.endDate))])]}}])}),t._v(" "),i("el-table-column",{attrs:{label:t.$t("common.operate"),align:"center","class-name":"small-padding fixed-width","show-overflow-tooltip":"",width:"250"},scopedSlots:t._u([{key:"default",fn:function(e){var n=e.row;return["1"==n.rptStatus?i("el-button",{attrs:{size:"mini",type:"warning"},on:{click:function(e){return t.handleModifyStatus(n)}}},[t._v(t._s(t.$t("voyage.btnLock")))]):t._e(),t._v(" "),"2"==n.rptStatus?i("el-button",{attrs:{size:"mini"},on:{click:function(e){return t.handleModifyStatus(n)}}},[t._v(t._s(t.$t("voyage.btnUnLock")))]):t._e(),t._v(" "),i("el-button",{attrs:{type:"primary",size:"mini"},on:{click:function(e){return t.handlEnit(n)}}},[t._v(t._s(t.$t("common.edit")))]),t._v(" "),"1"==n.rptStatus?i("el-button",{attrs:{size:"mini",type:"danger"},on:{click:function(e){return t.handlDelete(n)}}},[t._v(t._s(t.$t("common.delete")))]):t._e()]}}])})],1),t._v(" "),i("pagination",{directives:[{name:"show",rawName:"v-show",value:t.total>0,expression:"total>0"}],staticStyle:{margin:"0px",padding:"0px"},attrs:{total:t.total,page:t.listQuery.currentPage,limit:t.listQuery.pageSize},on:{"update:page":function(e){return t.$set(t.listQuery,"currentPage",e)},"update:limit":function(e){return t.$set(t.listQuery,"pageSize",e)},pagination:t.init}}),t._v(" "),t.shipComPanyNamevicode?i("reportManagementDalog",{attrs:{shipComPanyNamevicode:t.shipComPanyNamevicode},on:{shipComPanyNameData:t.shipComPanyNameData}}):t._e(),t._v(" "),t.shipComPanyNamevicodes?i("reportManagementDetail",{attrs:{list:t.list,shipComPanyNamevicodes:t.shipComPanyNamevicodes,flag:t.flag},on:{shipComPanyNameDetail:t.shipComPanyNameDetail}}):t._e()],1)])},a=[],o=i("eed0"),s=i("1089"),r=i("d3e3"),l=i("333d"),c=i("1593"),u={name:"reportManagement",components:{Pagination:l["a"],reportManagementDalog:o["default"],reportManagementDetail:s["default"]},directives:{adaptive:c["a"]},data:function(){return{value:[],reportForm:{shipId:"",startTime:"",endTime:"",imoRptId:""},list:{},shipComPanyNamevicodes:!1,flag:!1,shipComPanyNamevicode:!1,tableHeight:window.innerHeight-240-200,total:0,listLoading:!1,listQuery:{keyword:"",flag:"",startTime:"",endTime:"",currentPage:1,pageSize:20},dataTreeGrid:[]}},created:function(){this.init()},methods:{handTime:function(){this.listQuery.startTime=this.value[0],this.listQuery.endTime=this.value[1]},handleRest:function(){this.value=[],this.listQuery.keyword="",this.listQuery.flag="",this.listQuery.startTime="",this.listQuery.endTime="",this.listQuery.currentPage=1,this.init()},handlEnit:function(t){var e=this;"2"==t.rptStatus?this.flag=!0:this.flag=!1,this.reportForm.shipId=t.shipId,this.reportForm.startTime=t.rptStartTime,this.reportForm.endTime=t.rptEndTime,this.reportForm.imoRptId=t.id,this.reportForm.year=t.euMrvYear,Object(r["t"])(this.reportForm).then((function(t){t.data.result?(e.list=t.data.data,e.shipComPanyNamevicodes=!0):e.$notify({title:"error",message:t.data.data,type:"error",duration:2e3})}))},shipComPanyNameDetail:function(){this.init(),this.flag=!1,this.shipComPanyNamevicodes=!1},init:function(){var t=this;this.listLoading=!0,Object(r["C"])(this.listQuery).then((function(e){t.listLoading=!1,t.dataTreeGrid=e.data.data.items}))},addclick:function(){this.shipComPanyNamevicode=!0},shipComPanyNameData:function(){this.init(),this.shipComPanyNamevicode=!1},handleFilter:function(){this.listQuery.page=1,this.init()},handlDelete:function(t){var e=this;Object(r["d"])({id:t.id}).then((function(t){t.data.result?(e.init(),e.$message({message:"删除Success",type:"success"})):e.$message({showClose:!0,message:"删除error",type:"error"})}))},handleModifyStatus:function(t){var e=this;Object(r["L"])({id:t.id}).then((function(t){t.data.result?(e.init(),e.$message({message:"操作Success",type:"success"})):e.$message({showClose:!0,message:"操作error",type:"error"})}))}}},d=u,p=(i("5b4f"),i("2877")),m=Object(p["a"])(d,n,a,!1,null,"69710057",null);e["default"]=m.exports},"5b4f":function(t,e,i){"use strict";var n=i("ec85"),a=i.n(n);a.a},6724:function(t,e,i){"use strict";i("8d41");var n="@@wavesContext";function a(t,e){function i(i){var n=Object.assign({},e.value),a=Object.assign({ele:t,type:"hit",color:"rgba(0, 0, 0, 0.15)"},n),o=a.ele;if(o){o.style.position="relative",o.style.overflow="hidden";var s=o.getBoundingClientRect(),r=o.querySelector(".waves-ripple");switch(r?r.className="waves-ripple":(r=document.createElement("span"),r.className="waves-ripple",r.style.height=r.style.width=Math.max(s.width,s.height)+"px",o.appendChild(r)),a.type){case"center":r.style.top=s.height/2-r.offsetHeight/2+"px",r.style.left=s.width/2-r.offsetWidth/2+"px";break;default:r.style.top=(i.pageY-s.top-r.offsetHeight/2-document.documentElement.scrollTop||document.body.scrollTop)+"px",r.style.left=(i.pageX-s.left-r.offsetWidth/2-document.documentElement.scrollLeft||document.body.scrollLeft)+"px"}return r.style.backgroundColor=a.color,r.className="waves-ripple z-active",!1}}return t[n]?t[n].removeHandle=i:t[n]={removeHandle:i},i}var o={bind:function(t,e){t.addEventListener("click",a(t,e),!1)},update:function(t,e){t.removeEventListener("click",t[n].removeHandle,!1),t.addEventListener("click",a(t,e),!1)},unbind:function(t){t.removeEventListener("click",t[n].removeHandle,!1),t[n]=null,delete t[n]}},s=function(t){t.directive("waves",o)};window.Vue&&(window.waves=o,Vue.use(s)),o.install=s;e["a"]=o},"8d41":function(t,e,i){},a888:function(t,e,i){"use strict";var n={bind:function(t,e,i){var n=t.querySelector(".el-dialog__header"),a=t.querySelector(".el-dialog");n.style.cssText+=";cursor:move;",a.style.cssText+=";top:0px;";var o=function(){return window.document.currentStyle?function(t,e){return t.currentStyle[e]}:function(t,e){return getComputedStyle(t,!1)[e]}}();n.onmousedown=function(t){var e=t.clientX-n.offsetLeft,s=t.clientY-n.offsetTop,r=a.offsetWidth,l=a.offsetHeight,c=document.body.clientWidth,u=document.body.clientHeight,d=a.offsetLeft,p=c-a.offsetLeft-r,m=a.offsetTop,h=u-a.offsetTop-l,f=o(a,"left"),y=o(a,"top");f.includes("%")?(f=+document.body.clientWidth*(+f.replace(/\%/g,"")/100),y=+document.body.clientHeight*(+y.replace(/\%/g,"")/100)):(f=+f.replace(/\px/g,""),y=+y.replace(/\px/g,"")),document.onmousemove=function(t){var n=t.clientX-e,o=t.clientY-s;-n>d?n=-d:n>p&&(n=p),-o>m?o=-m:o>h&&(o=h),a.style.cssText+=";left:".concat(n+f,"px;top:").concat(o+y,"px;"),i.child.$emit("dragDialog")},document.onmouseup=function(t){document.onmousemove=null,document.onmouseup=null}}}},a=function(t){t.directive("el-drag-dialog",n)};window.Vue&&(window["el-drag-dialog"]=n,Vue.use(a)),n.install=a;e["a"]=n},bb9a:function(t,e,i){},ccd8:function(t,e,i){"use strict";var n=i("bb9a"),a=i.n(n);a.a},ec85:function(t,e,i){},eed0:function(t,e,i){"use strict";i.r(e);var n=function(){var t=this,e=t.$createElement,i=t._self._c||e;return i("el-dialog",{attrs:{title:t.$t("MRV.NewMRVReport"),"append-to-body":!0,visible:t.shipComPanyNamevicode,"before-close":t.addDetales,width:"80%"},on:{"update:visible":function(e){t.shipComPanyNamevicode=e}}},[i("el-form",{ref:"generateData",staticStyle:{width:"400px","margin-left":"20px"},attrs:{model:t.generateData,"label-position":"left","label-width":"70px"}},[i("el-form-item",{attrs:{"label-width":"150px",label:t.$t("shipBattery.shipName"),prop:"imono"}},[i("div",{staticClass:"addBox"},[i("el-input",{attrs:{disabled:"",placeholder:t.$t("MRV.boxselect")},model:{value:t.shipNam,callback:function(e){t.shipNam=e},expression:"shipNam"}}),t._v(" "),i("el-button",{staticStyle:{"margin-left":"20px"},attrs:{type:"primary"},on:{click:t.addImon}},[t._v(t._s(t.$t("common.select")))])],1)]),t._v(" "),i("el-form-item",{attrs:{"label-width":"150px",label:t.$t("MRV.ShipIMONo"),prop:"imono"}},[i("div",{staticClass:"addBox"},[i("el-input",{attrs:{disabled:"",placeholder:t.$t("MRV.boxselect")},model:{value:t.shipMOM,callback:function(e){t.shipMOM=e},expression:"shipMOM"}}),t._v(" "),i("el-button",{staticStyle:{"margin-left":"20px"},attrs:{type:"primary"},on:{click:t.addImon}},[t._v(t._s(t.$t("common.select")))])],1)]),t._v(" "),i("el-form-item",{attrs:{"label-width":"150px",label:t.$t("MRV.particularyear"),prop:"imono"}},[i("div",{staticClass:"addBox"},[i("el-select",{staticStyle:{width:"174px"},attrs:{placeholder:t.$t("common.selectRemind")},model:{value:t.generateData.year,callback:function(e){t.$set(t.generateData,"year",e)},expression:"generateData.year"}},t._l(t.ReportingyearArray,(function(t){return i("el-option",{key:t,attrs:{label:t,value:t}})})),1),t._v(" "),i("el-button",{staticStyle:{"margin-left":"20px"},on:{click:function(e){return t.addIMOReportInfo()}}},[t._v(t._s(t.$t("MRV.generate")))])],1)])],1),t._v(" "),t.dialogShipCompanyFormVisible?i("shipSwlectsDelog",{attrs:{add:t.dialogShipCompanyFormVisible},on:{adds:t.adds}}):t._e(),t._v(" "),t.shipComPanyNamevicodes?i("reportManagementDetail",{attrs:{list:t.list,shipComPanyNamevicodes:t.shipComPanyNamevicodes},on:{shipComPanyNameDetail:t.shipComPanyNameDetail}}):t._e()],1)},a=[],o=i("8e72"),s=i("1089"),r=i("bff4"),l=i("d3e3"),c={name:"reportManagementDalog",components:{shipSwlectsDelog:o["a"],reportManagementDetail:s["default"]},props:{shipComPanyNamevicode:{type:Boolean}},data:function(){return{shipComPanyNamevicodes:!1,IMOReportInfo:!1,tableHeight:window.innerHeight-240-200,tableData:[],shipNam:"",shipMOM:"",list:[],generateData:{shipId:"",year:"2020"},ReportingyearArray:[],reportForm:{id:"",shipId:"",startTime:"",endTime:"",imoRptId:""},dialogShipCompanyFormVisible:!1,listq:{shipId:"",year:"2020"}}},created:function(){this.generateData=this.listq,this.shipNam="",this.shipMOM="",this.initYear()},methods:{initYear:function(){var t=this;Object(r["q"])().then((function(e){t.ReportingyearArray=e.data.data}))},queryPostClick:function(){this.getListAdd()},getListAdd:function(){var t=this;Object(r["g"])(this.generateData.shipId,this.generateData.year,"1").then((function(e){t.tableData=e.data.data}))},addIMOReportInfo:function(){var t=this;this.reportForm.shipId=this.generateData.shipId,this.reportForm.year=this.generateData.year,this.shipComPanyNamevicodes=!0,Object(l["t"])(this.reportForm).then((function(e){t.list=e.data.data}))},deleteArr:function(t){var e=this;Object(l["e"])({imoStdId:t.imoRptId}).then((function(t){e.getListAdd(),e.$message({message:"删除成功",type:"success"})}))},addDetales:function(){this.$emit("shipComPanyNameData",!1)},adds:function(t){this.shipNam=t.name,this.shipMOM=t.imo,this.generateData.shipId=t.id,this.dialogShipCompanyFormVisible=!1},addImon:function(){this.dialogShipCompanyFormVisible=!0},shipComPanyNameDetail:function(){this.IMOReportInfo=!1,this.$emit("shipComPanyNameData",!1)}}},u=c,d=(i("ccd8"),i("2877")),p=Object(d["a"])(u,n,a,!1,null,"22b35dcb",null);e["default"]=p.exports},f9ac:function(t,e,i){"use strict";i.d(e,"q",(function(){return a})),i.d(e,"r",(function(){return o})),i.d(e,"p",(function(){return s})),i.d(e,"f",(function(){return r})),i.d(e,"o",(function(){return l})),i.d(e,"k",(function(){return c})),i.d(e,"j",(function(){return u})),i.d(e,"e",(function(){return d})),i.d(e,"n",(function(){return p})),i.d(e,"g",(function(){return m})),i.d(e,"c",(function(){return h})),i.d(e,"l",(function(){return f})),i.d(e,"h",(function(){return y})),i.d(e,"d",(function(){return v})),i.d(e,"m",(function(){return g})),i.d(e,"i",(function(){return b})),i.d(e,"b",(function(){return w})),i.d(e,"a",(function(){return _}));var n=i("b775");function a(t){return Object(n["a"])({url:"/sysUser/userList",method:"get",params:t})}function o(t){return Object(n["a"])({url:"/gcClient/findGcClientlist",method:"get",params:t})}function s(t){return Object(n["a"])({url:"/sysUser/userDetail",method:"get",params:{id:t}})}function r(t){return Object(n["a"])({url:"/sysUser/createUser",method:"post",data:t})}function l(t){return Object(n["a"])({url:"/sysUser/createUser",method:"post",data:t})}function c(t){return Object(n["a"])({url:"/sysRole/roleList",method:"get",params:t})}function u(t){return Object(n["a"])({url:"/sysRole/roleDetail",method:"get",params:{roleId:t}})}function d(t){return Object(n["a"])({url:"/sysRole/saveRole",method:"post",data:t})}function p(t){return Object(n["a"])({url:"/sysRole/updateRole",method:"post",data:t})}function m(t){return Object(n["a"])({url:"/sysDic/dicList",method:"get",params:t})}function h(t){return Object(n["a"])({url:"/sysDic/createDic",method:"post",data:t})}function f(t){return Object(n["a"])({url:"/sysDic/updateDic",method:"post",data:t})}function y(t){return Object(n["a"])({url:"/sysFunc/funcList",method:"get",params:t})}function v(t){return Object(n["a"])({url:"/sysFunc/createFunc",method:"post",data:t})}function g(t){return Object(n["a"])({url:"/sysFunc/updateFunc",method:"post",data:t})}function b(t){return Object(n["a"])({url:"/sysLog/logList",method:"get",params:t})}function w(t){return Object(n["a"])({url:"/sysUser/userList",method:"get",params:t})}function _(t){return Object(n["a"])({url:"/sysShipAuth/shipList",method:"get",params:t})}}}]);