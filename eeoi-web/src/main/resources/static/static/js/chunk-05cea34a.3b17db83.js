(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-05cea34a"],{"09f4":function(e,t,n){"use strict";n.d(t,"a",(function(){return r})),Math.easeInOutQuad=function(e,t,n,a){return e/=a/2,e<1?n/2*e*e+t:(e--,-n/2*(e*(e-2)-1)+t)};var a=function(){return window.requestAnimationFrame||window.webkitRequestAnimationFrame||window.mozRequestAnimationFrame||function(e){window.setTimeout(e,1e3/60)}}();function s(e){document.documentElement.scrollTop=e,document.body.parentNode.scrollTop=e,document.body.scrollTop=e}function i(){return document.documentElement.scrollTop||document.body.parentNode.scrollTop||document.body.scrollTop}function r(e,t,n){var r=i(),l=e-r,o=20,u=0;t="undefined"===typeof t?500:t;var c=function e(){u+=o;var i=Math.easeInOutQuad(u,r,l,t);s(i),u<t?a(e):n&&"function"===typeof n&&n()};c()}},"30f7":function(e,t,n){"use strict";n.r(t);var a=function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("div",{staticClass:"app-container"},[n("div",{staticClass:"filter-container"},[n("div",{staticStyle:{display:"inline-block"}},[n("label",{staticClass:"radio-label",staticStyle:{"padding-left":"0"}},[e._v(e._s(e.$t("sysManage.unName")))]),e._v(" "),n("el-input",{staticClass:"filter-item",staticStyle:{width:"200px"},attrs:{placeholder:e.$t("sysManage.keywords")},nativeOn:{keyup:function(t){return!t.type.indexOf("key")&&e._k(t.keyCode,"enter",13,t.key,"Enter")?null:e.handleFilter(t)}},model:{value:e.listQuery.account,callback:function(t){e.$set(e.listQuery,"account",t)},expression:"listQuery.account"}}),e._v(" "),n("label",{staticClass:"radio-label",staticStyle:{"padding-left":"0"}},[e._v(e._s(e.$t("sysManage.searchOrg")))]),e._v(" "),n("el-input",{staticClass:"filter-item",staticStyle:{width:"200px"},attrs:{placeholder:e.$t("sysManage.keywords")},nativeOn:{keyup:function(t){return!t.type.indexOf("key")&&e._k(t.keyCode,"enter",13,t.key,"Enter")?null:e.handleFilter(t)}},model:{value:e.listQuery.userdept,callback:function(t){e.$set(e.listQuery,"userdept",t)},expression:"listQuery.userdept"}}),e._v(" "),n("label",{staticClass:"radio-label",staticStyle:{"padding-left":"0"}},[e._v(e._s(e.$t("sysManage.searchStatus"))+" ")]),e._v(" "),n("el-select",{staticClass:"filter-item",staticStyle:{width:"90px"},attrs:{placeholder:e.$t("common.selectRemind"),clearable:""},model:{value:e.listQuery.userstatus,callback:function(t){e.$set(e.listQuery,"userstatus",t)},expression:"listQuery.userstatus"}},e._l(e.userStatusOptions,(function(e){return n("el-option",{key:e.key,attrs:{label:e.display_name,value:e.key}})})),1),e._v(" "),n("el-button",{directives:[{name:"waves",rawName:"v-waves"}],staticClass:"filter-item",staticStyle:{"margin-left":"20px"},attrs:{icon:"el-icon-search"},on:{click:e.handleFilter}},[e._v("\n          "+e._s(e.$t("common.query"))+"\n      ")]),e._v(" "),n("el-button",{directives:[{name:"waves",rawName:"v-waves"}],staticClass:"filter-item",attrs:{type:"info",icon:"el-icon-setting"},on:{click:e.handleFilter}},[e._v("\n          "+e._s(e.$t("common.reset"))+"\n      ")])],1),e._v(" "),n("div",{staticStyle:{"margin-top":"20px"}},[n("el-button",{staticClass:"filter-item",staticStyle:{"margin-left":"10px"},attrs:{type:"primary",icon:"el-icon-plus"},on:{click:e.handleCreate}},[e._v("\n          "+e._s(e.$t("common.add"))+"\n     ")])],1)]),e._v(" "),n("el-table",{directives:[{name:"loading",rawName:"v-loading",value:e.listLoading,expression:"listLoading"}],key:e.tableKey,staticStyle:{width:"100%"},attrs:{data:e.list,border:"",fit:"","highlight-current-row":""},on:{"sort-change":e.sortChange}},[n("el-table-column",{attrs:{label:e.$t("common.no"),prop:"id",sortable:"custom",align:"center",width:"80","class-name":e.getSortClass("id")},scopedSlots:e._u([{key:"default",fn:function(t){var a=t.row;return[n("span",[e._v(e._s(a.num))])]}}])}),e._v(" "),n("el-table-column",{attrs:{label:e.$t("sysManage.usernames")},scopedSlots:e._u([{key:"default",fn:function(t){var a=t.row;return[n("span",{staticClass:"link-type",on:{click:function(t){return e.handleUpdate(a)}}},[e._v(e._s(a.account))])]}}])}),e._v(" "),n("el-table-column",{attrs:{label:e.$t("sysManage.preronName")},scopedSlots:e._u([{key:"default",fn:function(t){var a=t.row;return[n("span",{staticClass:"link-type",on:{click:function(t){return e.handleUpdate(a)}}},[e._v(e._s(a.nameCn)+"/"+e._s(a.name))])]}}])}),e._v(" "),n("el-table-column",{attrs:{label:e.$t("sysManage.manaCompany"),width:"350px",align:"left"},scopedSlots:e._u([{key:"default",fn:function(t){var a=t.row;return[n("span",[e._v(e._s(a.userDept))])]}}])}),e._v(" "),n("el-table-column",{attrs:{label:e.$t("sysManage.updateTiem"),width:"150px",align:"center"},scopedSlots:e._u([{key:"default",fn:function(t){var a=t.row;return[n("span",[e._v(e._s(e._f("datesformat")(a.timestamp,"YYYY-MM-DD HH:mm")))])]}}])}),e._v(" "),n("el-table-column",{attrs:{label:e.$t("sysManage.updateTiem"),width:"110px",align:"center"},scopedSlots:e._u([{key:"default",fn:function(t){var a=t.row;return[n("el-tag",[e._v(e._s(e._f("userStatusFilter")(a.userStatus)))])]}}])}),e._v(" "),n("el-table-column",{attrs:{label:e.$t("common.operate"),align:"center",width:"230","class-name":"small-padding fixed-width"},scopedSlots:e._u([{key:"default",fn:function(t){var a=t.row;return[n("el-button",{attrs:{type:"primary",size:"mini"},on:{click:function(t){return e.handleUpdate(a)}}},[e._v("\n          "+e._s(e.$t("common.edit"))+"\n        ")]),e._v(" "),n("el-button",{attrs:{size:"mini",type:"success"},on:{click:function(t){return e.handleModifyStatus(a,"detail")}}},[e._v("\n          "+e._s(e.$t("common.detail"))+"\n        ")]),e._v(" "),n("el-button",{attrs:{size:"mini",type:"danger"},on:{click:function(t){return e.handleModifyStatus(a,"deleted")}}},[e._v("\n          "+e._s(e.$t("common.delete"))+"\n        ")])]}}])})],1),e._v(" "),n("pagination",{directives:[{name:"show",rawName:"v-show",value:e.total>0,expression:"total>0"}],attrs:{total:e.total,page:e.listQuery.page,limit:e.listQuery.limit},on:{"update:page":function(t){return e.$set(e.listQuery,"page",t)},"update:limit":function(t){return e.$set(e.listQuery,"limit",t)},pagination:e.getList}}),e._v(" "),n("el-dialog",{attrs:{width:"80%","append-to-body":!0,title:e.textMap[e.dialogStatus],visible:e.dialogFormVisible,"close-on-click-modal":!1},on:{"update:visible":function(t){e.dialogFormVisible=t}}},[n("el-form",{ref:"dataForm",staticStyle:{width:"400px","margin-left":"20px"},attrs:{rules:e.rules,model:e.temp,"label-position":"left","label-width":"70px"}},[n("el-form-item",{attrs:{label:e.$t("sysManage.usernames"),prop:"account","label-width":"150px"}},[n("el-input",{model:{value:e.temp.account,callback:function(t){e.$set(e.temp,"account",t)},expression:"temp.account"}})],1),e._v(" "),n("el-form-item",{attrs:{label:e.$t("sysManage.preronName"),prop:"nameCn","label-width":"150px"}},[n("el-input",{model:{value:e.temp.nameCn,callback:function(t){e.$set(e.temp,"nameCn",t)},expression:"temp.nameCn"}})],1),e._v(" "),n("el-form-item",{attrs:{label:e.$t("sysManage.Englishname"),prop:"name","label-width":"150px"}},[n("el-input",{model:{value:e.temp.name,callback:function(t){e.$set(e.temp,"name",t)},expression:"temp.name"}})],1),e._v(" "),n("el-form-item",{attrs:{label:e.$t("sysManage.searchOrg"),prop:"userDept","label-width":"150px"}},[n("el-select",{staticClass:"filter-item",attrs:{placeholder:e.$t("common.selectRemind")},model:{value:e.temp.userDept,callback:function(t){e.$set(e.temp,"userDept",t)},expression:"temp.userDept"}},e._l(e.userDeptOptions,(function(e){return n("el-option",{key:e.key,attrs:{label:e.display_name,value:e.key}})})),1)],1),e._v(" "),n("el-form-item",{attrs:{label:e.$t("sysManage.phone"),"label-width":"150px"}},[n("el-input",{model:{value:e.temp.phone,callback:function(t){e.$set(e.temp,"phone",t)},expression:"temp.phone"}})],1),e._v(" "),n("el-form-item",{attrs:{label:e.$t("sysManage.email"),"label-width":"150px"}},[n("el-input",{model:{value:e.temp.email,callback:function(t){e.$set(e.temp,"email",t)},expression:"temp.email"}})],1),e._v(" "),n("el-form-item",{attrs:{label:e.$t("sysManage.searchStatus"),prop:"userStatus","label-width":"150px"}},[n("el-select",{staticClass:"filter-item",attrs:{placeholder:e.$t("common.selectRemind")},model:{value:e.temp.userStatus,callback:function(t){e.$set(e.temp,"userStatus",t)},expression:"temp.userStatus"}},e._l(e.userStatusOptions,(function(e){return n("el-option",{key:e.key,attrs:{label:e.display_name,value:e.key}})})),1)],1),e._v(" "),n("el-form-item",{attrs:{label:e.$t("common.memo"),"label-width":"150px"}},[n("el-input",{attrs:{autosize:{minRows:2,maxRows:4},type:"textarea",placeholder:e.$t("common.inputRemind")},model:{value:e.temp.remark,callback:function(t){e.$set(e.temp,"remark",t)},expression:"temp.remark"}})],1)],1),e._v(" "),n("div",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[n("el-button",{on:{click:function(t){e.dialogFormVisible=!1}}},[e._v("\n        "+e._s(e.$t("common.close"))+"\n      ")]),e._v(" "),n("el-button",{attrs:{type:"primary"},on:{click:function(t){"create"===e.dialogStatus?e.createData():e.updateData()}}},[e._v("\n        "+e._s(e.$t("common.save"))+"\n      ")])],1)],1)],1)},s=[],i=n("f9ac"),r=n("6724"),l=n("ed08"),o=n("333d"),u=[{key:"1",display_name:"启用"},{key:"0",display_name:"冻结"}],c=[{key:"1",display_name:"机构1"},{key:"0",display_name:"机构2"}],d=u.reduce((function(e,t){return e[t.key]=t.display_name,e}),{}),m=c.reduce((function(e,t){return e[t.key]=t.display_name,e}),{}),p={name:"ComplexTable",components:{Pagination:o["a"]},directives:{waves:r["a"]},filters:{userStatusFilter:function(e){return d[e]},userDeptFilter:function(e){return m[e]}},data:function(){return{tableKey:0,list:null,total:0,listLoading:!0,listQuery:{page:1,limit:20,account:"",userstatus:"",userdept:"",sort:"+id"},userStatusOptions:u,userDeptOptions:c,temp:{id:void 0,num:1,timestamp:new Date,account:"",name:"",nameCn:"",userDept:"",userStatus:"",email:"",phone:"",remark:""},dialogFormVisible:!1,dialogStatus:"",textMap:{update:"编辑",create:"新增"},rules:{account:[{required:!0,message:"必填项",trigger:"blur"}],name:[{required:!0,message:"必填项",trigger:"blur"}],nameCn:[{required:!0,message:"必填项",trigger:"blur"}],userDept:[{required:!0,message:"必填项",trigger:"change"}],userStatus:[{required:!0,message:"必填项",trigger:"change"}]}}},created:function(){this.getList()},methods:{getList:function(){var e=this;this.listLoading=!0,Object(i["q"])(this.listQuery).then((function(t){e.list=t.data.items,e.total=t.data.total,setTimeout((function(){e.listLoading=!1}),1500)}))},handleFilter:function(){this.listQuery.page=1,this.getList()},handleModifyStatus:function(e,t){this.$message({message:"操作Success",type:"success"}),e.status=t},sortChange:function(e){var t=e.prop,n=e.order;"id"===t&&this.sortByID(n)},sortByID:function(e){this.listQuery.sort="ascending"===e?"+id":"-id",this.handleFilter()},resetTemp:function(){this.temp={id:void 0,num:1,timestamp:new Date,account:"",name:"",nameCn:"",userDept:"",userStatus:"",email:"",phone:"",remark:""}},handleCreate:function(){var e=this;this.resetTemp(),this.dialogStatus="create",this.dialogFormVisible=!0,this.$nextTick((function(){e.$refs["dataForm"].clearValidate()}))},createData:function(){var e=this;this.$refs["dataForm"].validate((function(t){t&&(e.temp.id=parseInt(100*Math.random())+1024,e.temp.author="vue-element-admin",Object(i["f"])(e.temp).then((function(){e.list.unshift(e.temp),e.dialogFormVisible=!1,e.$notify({title:"Success",message:"Created Successfully",type:"success",duration:2e3})})))}))},handleUpdate:function(e){var t=this;this.temp=Object.assign({},e),this.temp.timestamp=new Date(this.temp.timestamp),this.dialogStatus="update",this.dialogFormVisible=!0,this.$nextTick((function(){t.$refs["dataForm"].clearValidate()}))},updateData:function(){var e=this;this.$refs["dataForm"].validate((function(t){if(t){var n=Object.assign({},e.temp);n.timestamp=+new Date(n.timestamp),Object(i["o"])(n).then((function(){var t=!0,n=!1,a=void 0;try{for(var s,i=e.list[Symbol.iterator]();!(t=(s=i.next()).done);t=!0){var r=s.value;if(r.id===e.temp.id){var l=e.list.indexOf(r);e.list.splice(l,1,e.temp);break}}}catch(o){n=!0,a=o}finally{try{t||null==i.return||i.return()}finally{if(n)throw a}}e.dialogFormVisible=!1,e.$notify({title:"Success",message:"Update Successfully",type:"success",duration:2e3})}))}}))},handleDelete:function(e){this.$notify({title:"Success",message:"Delete Successfully",type:"success",duration:2e3});var t=this.list.indexOf(e);this.list.splice(t,1)},formatJson:function(e,t){return t.map((function(t){return e.map((function(e){return"timestamp"===e?Object(l["e"])(t[e]):t[e]}))}))},getSortClass:function(e){var t=this.listQuery.sort;return t==="+".concat(e)?"ascending":t==="-".concat(e)?"descending":""},paramTest:function(){this.$store.commit("setRemark","stringtest");var e={name:"zhangsan",age:22};this.$store.commit("setJsontest",e)}}},f=p,y=n("2877"),h=Object(y["a"])(f,a,s,!1,null,null,null);t["default"]=h.exports},6724:function(e,t,n){"use strict";n("8d41");var a="@@wavesContext";function s(e,t){function n(n){var a=Object.assign({},t.value),s=Object.assign({ele:e,type:"hit",color:"rgba(0, 0, 0, 0.15)"},a),i=s.ele;if(i){i.style.position="relative",i.style.overflow="hidden";var r=i.getBoundingClientRect(),l=i.querySelector(".waves-ripple");switch(l?l.className="waves-ripple":(l=document.createElement("span"),l.className="waves-ripple",l.style.height=l.style.width=Math.max(r.width,r.height)+"px",i.appendChild(l)),s.type){case"center":l.style.top=r.height/2-l.offsetHeight/2+"px",l.style.left=r.width/2-l.offsetWidth/2+"px";break;default:l.style.top=(n.pageY-r.top-l.offsetHeight/2-document.documentElement.scrollTop||document.body.scrollTop)+"px",l.style.left=(n.pageX-r.left-l.offsetWidth/2-document.documentElement.scrollLeft||document.body.scrollLeft)+"px"}return l.style.backgroundColor=s.color,l.className="waves-ripple z-active",!1}}return e[a]?e[a].removeHandle=n:e[a]={removeHandle:n},n}var i={bind:function(e,t){e.addEventListener("click",s(e,t),!1)},update:function(e,t){e.removeEventListener("click",e[a].removeHandle,!1),e.addEventListener("click",s(e,t),!1)},unbind:function(e){e.removeEventListener("click",e[a].removeHandle,!1),e[a]=null,delete e[a]}},r=function(e){e.directive("waves",i)};window.Vue&&(window.waves=i,Vue.use(r)),i.install=r;t["a"]=i},"8d41":function(e,t,n){},f9ac:function(e,t,n){"use strict";n.d(t,"q",(function(){return s})),n.d(t,"r",(function(){return i})),n.d(t,"p",(function(){return r})),n.d(t,"f",(function(){return l})),n.d(t,"o",(function(){return o})),n.d(t,"k",(function(){return u})),n.d(t,"j",(function(){return c})),n.d(t,"e",(function(){return d})),n.d(t,"n",(function(){return m})),n.d(t,"g",(function(){return p})),n.d(t,"c",(function(){return f})),n.d(t,"l",(function(){return y})),n.d(t,"h",(function(){return h})),n.d(t,"d",(function(){return v})),n.d(t,"m",(function(){return g})),n.d(t,"i",(function(){return b})),n.d(t,"b",(function(){return _})),n.d(t,"a",(function(){return k}));var a=n("b775");function s(e){return Object(a["a"])({url:"/sysUser/userList",method:"get",params:e})}function i(e){return Object(a["a"])({url:"/gcClient/findGcClientlist",method:"get",params:e})}function r(e){return Object(a["a"])({url:"/sysUser/userDetail",method:"get",params:{id:e}})}function l(e){return Object(a["a"])({url:"/sysUser/createUser",method:"post",data:e})}function o(e){return Object(a["a"])({url:"/sysUser/createUser",method:"post",data:e})}function u(e){return Object(a["a"])({url:"/sysRole/roleList",method:"get",params:e})}function c(e){return Object(a["a"])({url:"/sysRole/roleDetail",method:"get",params:{roleId:e}})}function d(e){return Object(a["a"])({url:"/sysRole/saveRole",method:"post",data:e})}function m(e){return Object(a["a"])({url:"/sysRole/updateRole",method:"post",data:e})}function p(e){return Object(a["a"])({url:"/sysDic/dicList",method:"get",params:e})}function f(e){return Object(a["a"])({url:"/sysDic/createDic",method:"post",data:e})}function y(e){return Object(a["a"])({url:"/sysDic/updateDic",method:"post",data:e})}function h(e){return Object(a["a"])({url:"/sysFunc/funcList",method:"get",params:e})}function v(e){return Object(a["a"])({url:"/sysFunc/createFunc",method:"post",data:e})}function g(e){return Object(a["a"])({url:"/sysFunc/updateFunc",method:"post",data:e})}function b(e){return Object(a["a"])({url:"/sysLog/logList",method:"get",params:e})}function _(e){return Object(a["a"])({url:"/sysUser/userList",method:"get",params:e})}function k(e){return Object(a["a"])({url:"/sysShipAuth/shipList",method:"get",params:e})}}}]);