(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-3b52cfd4","chunk-b4c0a026"],{"0e56":function(e,t,i){"use strict";var a=i("cfd6"),n=i.n(a);n.a},3461:function(e,t,i){"use strict";var a=i("c91c"),n=i.n(a);n.a},"54d1":function(e,t,i){"use strict";i.r(t);var a=function(){var e=this,t=e.$createElement,i=e._self._c||t;return i("div",{staticClass:"app-container"},[i("div",{staticClass:"filter-container"},[i("div",{staticStyle:{display:"flex","align-items":"center"}},[i("label",{staticClass:"radio-labels"},[e._v(e._s(e.$t("common.keyWord")))]),e._v(" "),i("el-input",{staticStyle:{width:"160px"},attrs:{placeholder:e.$t("common.inputRemind")},nativeOn:{keyup:function(t){return!t.type.indexOf("key")&&e._k(t.keyCode,"enter",13,t.key,"Enter")?null:e.handleFilter(t)}},model:{value:e.listQuery.keyWord,callback:function(t){e.$set(e.listQuery,"keyWord",t)},expression:"listQuery.keyWord"}}),e._v(" "),i("label",{staticClass:"radio-label"},[e._v(e._s(e.$t("MRV.Year")))]),e._v(" "),i("el-select",{staticClass:"filter-item",staticStyle:{width:"160px"},attrs:{placeholder:e.$t("common.selectRemind"),clearable:"",size:"mini"},model:{value:e.listQuery.Year,callback:function(t){e.$set(e.listQuery,"Year",t)},expression:"listQuery.Year"}},e._l(e.ReportingyearArray,(function(e){return i("el-option",{key:e,attrs:{label:e,value:e}})})),1),e._v(" "),i("el-button",{staticClass:"filter-item",staticStyle:{"margin-left":"20px"},attrs:{icon:"el-icon-search"},on:{click:e.handleFilter}},[e._v("\n        "+e._s(e.$t("common.search"))+"\n      ")]),e._v(" "),i("el-button",{staticClass:"filter-item",staticStyle:{"margin-left":"20px"},attrs:{type:"info"},on:{click:e.handleReset}},[e._v("\n        "+e._s(e.$t("common.reset"))+"\n      ")])],1)]),e._v(" "),i("div",[i("el-button",{attrs:{type:"primary",size:"mini",icon:"el-icon-plus"},on:{click:e.addClick}},[e._v(e._s(e.$t("common.add"))+"\n  ")])],1),e._v(" "),i("el-table",{directives:[{name:"loading",rawName:"v-loading",value:e.listLoading,expression:"listLoading"}],staticStyle:{width:"100%",overflow:"auto","margin-top":"10px"},attrs:{data:e.list,border:"",fit:"",height:e.tableHeight,"highlight-current-row":"","row-style":{height:"35px"},"cell-style":{padding:"0"},"empty-text":e.$t("MRV.noDataAlert")}},[i("el-table-column",{attrs:{label:e.$t("common.no"),type:"index",width:"50"}}),e._v(" "),i("el-table-column",{attrs:{label:e.$t("MRV.VesselName"),align:"center","show-overflow-tooltip":""},scopedSlots:e._u([{key:"default",fn:function(t){var a=t.row;return[i("el-tooltip",{attrs:{placement:"top"}},[0!==a.beginPeriodOilDtos.length?i("div",{attrs:{slot:"content"},slot:"content"},e._l(a.beginPeriodOilDtos,(function(t,a){return i("li",{key:a},[i("span",[e._v(e._s(t.fuelName)+":")]),e._v(" "),i("span",[e._v(e._s(t.fuelTons)+"T")])])})),0):e._e(),e._v(" "),i("span",[e._v(e._s(a.shipName))])])]}}])}),e._v(" "),i("el-table-column",{attrs:{label:e.$t("MRV.Date"),align:"center","show-overflow-tooltip":""},scopedSlots:e._u([{key:"default",fn:function(t){var a=t.row;return[i("el-tooltip",{attrs:{placement:"top"}},[0!==a.beginPeriodOilDtos.length?i("div",{attrs:{slot:"content"},slot:"content"},e._l(a.beginPeriodOilDtos,(function(t,a){return i("li",{key:a},[i("span",[e._v(e._s(t.fuelName)+":")]),e._v(" "),i("span",[e._v(e._s(t.fuelTons)+"T")])])})),0):e._e(),e._v(" "),i("span",[e._v(e._s(e._f("datesformat")(a.periodTime,"YYYY-MM-DD HH:mm:ss")))])])]}}])}),e._v(" "),i("el-table-column",{attrs:{label:e.$t("common.operate"),align:"center","show-overflow-tooltip":""},scopedSlots:e._u([{key:"default",fn:function(t){return["0"===t.row.recStatus?i("el-button",{attrs:{type:"warning",size:"mini"},on:{click:function(i){return e.Unclock(t.row)}}},[e._v(e._s(e.$t("voyage.btnLock"))+"\n              ")]):e._e(),e._v(" "),"1"===t.row.recStatus?i("el-button",{attrs:{size:"mini"},on:{click:function(i){return e.Unclock(t.row)}}},[e._v(e._s(e.$t("voyage.btnUnLock"))+"\n              ")]):e._e(),e._v(" "),i("el-button",{attrs:{disabled:"1"===t.row.recStatus,type:"primary",size:"mini"},on:{click:function(i){return e.handleUpdatePort(t.row)}}},[e._v(e._s(e.$t("common.edit"))+"\n              ")]),e._v(" "),i("el-button",{attrs:{size:"mini",type:"success"},on:{click:function(i){return e.handleDetailPort(t.row)}}},[e._v(e._s(e.$t("common.detail"))+"\n              ")]),e._v(" "),i("el-button",{attrs:{disabled:"1"===t.row.recStatus,size:"mini",type:"danger"},on:{click:function(i){return e.handleDeletePort(t)}}},[e._v(e._s(e.$t("common.delete"))+"\n              ")])]}}])})],1),e._v(" "),e.total>0?i("pagination",{attrs:{total:e.total,page:e.listQuery.currentPage,limit:e.listQuery.pageSize},on:{"update:page":function(t){return e.$set(e.listQuery,"currentPage",t)},"update:limit":function(t){return e.$set(e.listQuery,"pageSize",t)},pagination:e.getList}}):e._e(),e._v(" "),e.dialogShipCompanyFormVisibles.dalog?i("openingVolumeDalog",{attrs:{shipComPanyNamevicode:e.dialogShipCompanyFormVisibles},on:{shipComPanyNameData:e.shipComPanyNameData}}):e._e(),e._v(" "),e.shipComPanyNamevicodeDetail.dalog?i("openingVolumeDeatil",{attrs:{shipComPanyNamevicodeDetail:e.shipComPanyNamevicodeDetail},on:{shipComPanyNameDataDetali:e.shipComPanyNameDataDetali}}):e._e()],1)},n=[],o=i("d3e3"),l=i("bff4"),s=i("333d"),r=i("79eb"),c=i("823a"),d=[{key:"2017",display_name:"2017"},{key:"2018",display_name:"2018"},{key:"2019",display_name:"2019"},{key:"2020",display_name:"2020"}],m={name:"openingVolume",components:{Pagination:s["a"],openingVolumeDalog:r["default"],openingVolumeDeatil:c["default"]},data:function(){return{shipComPanyNamevicodeDetail:{dalog:!1,opening:{}},ReportingyearArray:[],shipName:"",shipComPanyNamevicode:{},tableKey:0,tableHeight:window.innerHeight-240,list:[],total:0,dialogShipCompanyFormVisibles:{dalog:!1,opening:{}},listLoading:!0,listQuery:{currentPage:1,pageSize:20,keyWord:"",year:""},yearOptions:d}},created:function(){this.listLoading=!1,this.getList(),this.initYear()},methods:{initYear:function(){var e=this;Object(l["q"])().then((function(t){e.ReportingyearArray=t.data.data}))},shipComPanyNameDataDetali:function(){this.shipComPanyNamevicodeDetail.dalog=!1},shipComPanyNameData:function(){this.getList(),this.dialogShipCompanyFormVisibles.dalog=!1},handleUpdatePort:function(e){this.dialogShipCompanyFormVisibles.dalog=!0,this.dialogShipCompanyFormVisibles.opening=e},handleDetailPort:function(e){this.shipComPanyNamevicodeDetail.opening=e,this.shipComPanyNamevicodeDetail.dalog=!0},handleDeletePort:function(e){var t=this;"1"===e.row.recStatus?this.$message({message:"锁定状态不可进行删除操作",type:"warning"}):Object(o["g"])({periodId:e.row.periodId}).then((function(i){t.list.splice(e.$index,1),t.getList()}))},Unclock:function(e){var t=this;Object(o["K"])({periodId:e.periodId}).then((function(e){t.getList()}))},addClick:function(){this.dialogShipCompanyFormVisibles.dalog=!0,this.dialogShipCompanyFormVisibles.opening={}},getList:function(){var e=this;this.listLoading=!0,Object(o["F"])(this.listQuery).then((function(t){e.list=t.data.data.items,e.total=t.data.data.total,setTimeout((function(){e.listLoading=!1}),1500)}))},handleReset:function(){this.listQuery.currentPage=1,this.listQuery.pageSize=20,this.listQuery.keyWord="",this.listQuery.year=""},handleFilter:function(){this.listQuery.currentPage=1,this.getList()}}},p=m,u=(i("0e56"),i("2877")),h=Object(u["a"])(p,a,n,!1,null,"37da4178",null);t["default"]=h.exports},"823a":function(e,t,i){"use strict";i.r(t);var a=function(){var e=this,t=e.$createElement,i=e._self._c||t;return i("el-dialog",{attrs:{title:e.$t("MRV.openingVolume"),"append-to-body":!0,visible:e.shipComPanyNamevicodeDetail.dalog,"before-close":e.addDetale,width:"80%"},on:{"update:visible":function(t){return e.$set(e.shipComPanyNamevicodeDetail,"dalog",t)}}},[i("div",{staticClass:"filter-container"},[i("el-form",{ref:"listQuery",attrs:{model:e.listQuery,"label-width":"175px"}},[i("el-row",[i("el-col",{attrs:{span:12}},[i("el-form-item",{attrs:{label:e.$t("MRV.Nameofvessel")}},[i("div",{staticClass:"addBox"},[e._v("\n          "+e._s(e.shipName)+"\n        ")])])],1),e._v(" "),i("el-col",{attrs:{span:12}},[i("el-form-item",{attrs:{label:e.$t("table.date")}},[e._v("\n          "+e._s(e._f("datesformat")(e.listQuery.periodTime,"YYYY-MM-DD HH:mm:ss"))+"\n        ")])],1)],1),e._v(" "),i("el-row",e._l(e.listQuery.beginPeriodOils,(function(t,a){return i("el-col",{key:a,attrs:{span:12}},[i("el-form-item",{attrs:{label:t.fuelName}},[e._v("\n          "+e._s(t.fuelTons)),i("span",{staticStyle:{color:"red","margin-left":"10px"}},[e._v("T")])])],1)})),1)],1)],1),e._v(" "),i("div",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[i("el-button",{on:{click:e.addDetale}},[e._v(e._s(e.$t("common.close")))])],1)])},n=[],o={name:"openingVolumeDetaildalog",props:{shipComPanyNamevicodeDetail:{type:Object}},data:function(){return{dialogShipCompanyFormVisibleDetail:!1,shipName:"",listQuery:{shipId:"",periodTime:"",beginPeriodOils:[]},listq:{shipId:"",periodTime:"",beginPeriodOils:[]}}},created:function(){this.listQuery=this.listq,0!==Object.keys(this.shipComPanyNamevicodeDetail.opening).length&&(this.listQuery.shipId=this.shipComPanyNamevicodeDetail.opening.shipId,this.shipName=this.shipComPanyNamevicodeDetail.opening.shipName,this.listQuery.periodTime=this.shipComPanyNamevicodeDetail.opening.periodTime,this.listQuery.beginPeriodOils=this.shipComPanyNamevicodeDetail.opening.beginPeriodOilDtos)},methods:{addDetale:function(){this.$emit("shipComPanyNameDataDetali",!1)}}},l=o,s=(i("3461"),i("2877")),r=Object(s["a"])(l,a,n,!1,null,"777f050d",null);t["default"]=r.exports},c91c:function(e,t,i){},cfd6:function(e,t,i){}}]);