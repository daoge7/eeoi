(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-7e38bb28"],{"09f4":function(e,t,n){"use strict";n.d(t,"a",(function(){return l})),Math.easeInOutQuad=function(e,t,n,a){return e/=a/2,e<1?n/2*e*e+t:(e--,-n/2*(e*(e-2)-1)+t)};var a=function(){return window.requestAnimationFrame||window.webkitRequestAnimationFrame||window.mozRequestAnimationFrame||function(e){window.setTimeout(e,1e3/60)}}();function i(e){document.documentElement.scrollTop=e,document.body.parentNode.scrollTop=e,document.body.scrollTop=e}function s(){return document.documentElement.scrollTop||document.body.parentNode.scrollTop||document.body.scrollTop}function l(e,t,n){var l=s(),o=e-l,r=20,c=0;t="undefined"===typeof t?500:t;var u=function e(){c+=r;var s=Math.easeInOutQuad(c,l,o,t);i(s),c<t?a(e):n&&"function"===typeof n&&n()};u()}},f9bc:function(e,t,n){"use strict";n.r(t);var a=function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("div",{staticClass:"app-container"},[n("div",{staticClass:"filter-container"},[n("div",{staticStyle:{display:"inline-block"}},[n("label",{staticClass:"radio-label",staticStyle:{"padding-left":"0"}},[e._v(e._s(e.$t("sysPcappVersion.newVersion")))]),e._v(" "),n("el-input",{staticClass:"filter-item",staticStyle:{width:"200px"},attrs:{placeholder:e.$t("common.selectRemind")},nativeOn:{keyup:function(t){return!t.type.indexOf("key")&&e._k(t.keyCode,"enter",13,t.key,"Enter")?null:e.handleFilter(t)}},model:{value:e.listQuery.newVersion,callback:function(t){e.$set(e.listQuery,"newVersion",t)},expression:"listQuery.newVersion"}}),e._v(" "),n("label",{staticClass:"radio-label",staticStyle:{"padding-left":"0"}},[e._v(e._s(e.$t("sysPcappVersion.upType")))]),e._v(" "),n("el-input",{staticClass:"filter-item",staticStyle:{width:"200px"},attrs:{placeholder:e.$t("common.selectRemind")},nativeOn:{keyup:function(t){return!t.type.indexOf("key")&&e._k(t.keyCode,"enter",13,t.key,"Enter")?null:e.handleFilter(t)}},model:{value:e.listQuery.upType,callback:function(t){e.$set(e.listQuery,"upType",t)},expression:"listQuery.upType"}}),e._v(" "),n("el-button",{directives:[{name:"waves",rawName:"v-waves"}],staticClass:"filter-item",staticStyle:{"margin-left":"20px"},attrs:{icon:"el-icon-search"},on:{click:e.handleFilter}},[e._v("\n       "+e._s(e.$t("common.query"))+"\n      ")]),e._v(" "),n("el-button",{directives:[{name:"waves",rawName:"v-waves"}],staticClass:"filter-item",attrs:{type:"info",icon:"el-icon-setting"},on:{click:e.reset}},[e._v("\n        "+e._s(e.$t("common.reset"))+"\n      ")])],1),e._v(" "),n("div",{staticStyle:{"margin-top":"20px"}},[n("el-button",{staticClass:"filter-item",staticStyle:{"margin-left":"10px"},attrs:{type:"primary",icon:"el-icon-plus"},on:{click:e.handleCreate}},[e._v("\n        "+e._s(e.$t("common.add"))+"\n      ")])],1)]),e._v(" "),n("el-table",{directives:[{name:"loading",rawName:"v-loading",value:e.listLoading,expression:"listLoading"},{name:"adaptive",rawName:"v-adaptive",value:{bottomOffset:38},expression:"{bottomOffset: 38}"}],key:e.tableKey,staticStyle:{width:"100%",overflow:"auto"},attrs:{data:e.list,border:"",fit:"",height:"100px","highlight-current-row":"","row-style":{height:"35px"},"cell-style":{padding:"0"}},on:{"sort-change":e.sortChange}},[n("el-table-column",{attrs:{label:e.$t("common.no"),type:"index","show-overflow-tooltip":"",width:"50",align:"center"}}),e._v(" "),n("el-table-column",{attrs:{label:e.$t("sysPcappVersion.sysCode"),align:"center"},scopedSlots:e._u([{key:"default",fn:function(t){var a=t.row;return[n("span",[e._v(e._s(e._f("docsysCodeFilter")(a.sysCode)))])]}}])}),e._v(" "),n("el-table-column",{attrs:{label:e.$t("sysPcappVersion.preVersion"),width:"110px",align:"center"},scopedSlots:e._u([{key:"default",fn:function(t){var a=t.row;return[n("span",[e._v(e._s(a.preVersion))])]}}])}),e._v(" "),n("el-table-column",{attrs:{label:e.$t("sysPcappVersion.newVersion"),align:"center"},scopedSlots:e._u([{key:"default",fn:function(t){var a=t.row;return[n("span",[e._v(e._s(a.newVersion))])]}}])}),e._v(" "),n("el-table-column",{attrs:{label:e.$t("sysPcappVersion.upType"),align:"center"},scopedSlots:e._u([{key:"default",fn:function(t){var a=t.row;return[n("span",[e._v(e._s(e._f("upTypeFilter")(a.upType)))])]}}])}),e._v(" "),n("el-table-column",{attrs:{label:e.$t("sysPcappVersion.recStatus"),align:"center"},scopedSlots:e._u([{key:"default",fn:function(t){var a=t.row;return[n("el-tag",[e._v(e._s(e._f("docStatusFilter")(a.recStatus)))])]}}])}),e._v(" "),n("el-table-column",{attrs:{label:e.$t("common.creator"),align:"center"},scopedSlots:e._u([{key:"default",fn:function(t){var a=t.row;return[n("span",[e._v(e._s(a.creator))])]}}])}),e._v(" "),n("el-table-column",{attrs:{label:e.$t("common.createdTime"),align:"center"},scopedSlots:e._u([{key:"default",fn:function(t){var a=t.row;return[n("span",[e._v(e._s(e._f("datesformat")(a.createTm,"YYYY-MM-DD HH:mm")))])]}}])}),e._v(" "),n("el-table-column",{attrs:{label:e.$t("common.operator"),align:"center"},scopedSlots:e._u([{key:"default",fn:function(t){var a=t.row;return[n("span",[e._v(e._s(a.opuser))])]}}])}),e._v(" "),n("el-table-column",{attrs:{label:e.$t("common.operatedTime"),width:"150px",align:"center"},scopedSlots:e._u([{key:"default",fn:function(t){var a=t.row;return[n("span",[e._v(e._s(e._f("datesformat")(a.opdate,"YYYY-MM-DD HH:mm")))])]}}])}),e._v(" "),n("el-table-column",{attrs:{label:e.$t("common.operate"),align:"center",width:"230","class-name":"small-padding fixed-width"},scopedSlots:e._u([{key:"default",fn:function(t){var a=t.row;return[n("el-button",{attrs:{type:"primary",size:"mini"},on:{click:function(t){return e.userDetail2(a,"edit")}}},[e._v("\n          "+e._s(e.$t("common.edit"))+"\n        ")]),e._v(" "),n("el-button",{attrs:{size:"mini",type:"success"},on:{click:function(t){return e.userDetail2(a,"detail")}}},[e._v("\n          "+e._s(e.$t("common.detail"))+"\n        ")]),e._v(" "),n("el-button",{attrs:{size:"mini",type:"danger"},on:{click:function(t){return e.handleDelete(a)}}},[e._v("\n          "+e._s(e.$t("common.delete"))+"\n        ")])]}}])})],1),e._v(" "),n("pagination",{directives:[{name:"show",rawName:"v-show",value:e.total>0,expression:"total>0"}],attrs:{total:e.total,page:e.listQuery.pageNum,limit:e.listQuery.pageSize},on:{"update:page":function(t){return e.$set(e.listQuery,"pageNum",t)},"update:limit":function(t){return e.$set(e.listQuery,"pageSize",t)},pagination:e.getList}}),e._v(" "),n("div",[n("sysPcappVersion",{ref:"child",on:{inidData:e.inidData}})],1)],1)},i=[],s=n("bff4"),l=n("6724"),o=n("333d"),r=n("1593"),c=n("a888"),u=n("c316"),d=[{key:0,display_name:"拟稿"},{key:1,display_name:"发布"},{key:2,display_name:"失效"}],p=[{key:"01",display_name:"PC"},{key:"02",display_name:"App"}],m=[{key:"0",display_name:"全量"},{key:"1",display_name:"增量"}],f=d.reduce((function(e,t){return e[t.key]=t.display_name,e}),{}),y=p.reduce((function(e,t){return e[t.key]=t.display_name,e}),{}),v=m.reduce((function(e,t){return e[t.key]=t.display_name,e}),{}),_={name:"PcAppMng",components:{Pagination:o["a"],sysPcappVersion:u["default"]},directives:{waves:l["a"],adaptive:r["a"],elDragDialog:c["a"]},filters:{docStatusFilter:function(e){return f[e]},docsysCodeFilter:function(e){return y[e]},upTypeFilter:function(e){return v[e]}},props:{datascndoc:{type:Boolean}},data:function(){return{tableKey:0,list:null,total:0,total2:0,listLoading:!0,recStatusOptions:d,upTypeOptions:m,listQuery:{pageNum:1,pageSize:20,newVersion:"",upType:""},downloadFile:{path:"",fileName:""},userRoleOptions:[],currentFile:{fileId:""},dialogFormVisible:!1,dialogStatus:"",textMap:{update:"编辑",create:"新增",detail:"详情"},tableChecked:[],tableChecked2:[],fileList:[],readonly:!1,btnShow:!0}},created:function(){this.getList()},methods:{inidData:function(){this.getList()},getList:function(){var e=this;this.listLoading=!0;var t="/sysPcappVersion/findSysPcappVersionList";Object(s["k"])(t,this.listQuery).then((function(t){e.list=t.data.data.items,e.total=t.data.data.total,setTimeout((function(){e.listLoading=!1}),1500)}))},sortChange:function(e){var t=e.prop,n=e.order;"id"===t&&this.sortByID(n)},handleFilter:function(){this.listQuery.pageNum=1,this.getList()},reset:function(){this.listQuery={pageNum:1,pageSize:20,newVersion:"",upType:""},this.getList()},handleCreate:function(){this.$refs.child.handleCreate()},userDetail2:function(e,t){this.$refs.child.userDetail2(e,t)},handleDelete:function(e){this.$refs.child.handleDelete(e)}}},h=_,g=n("2877"),w=Object(g["a"])(h,a,i,!1,null,null,null);t["default"]=w.exports}}]);