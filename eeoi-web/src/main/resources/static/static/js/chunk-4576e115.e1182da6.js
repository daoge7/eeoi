(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-4576e115"],{"09f4":function(t,e,a){"use strict";a.d(e,"a",(function(){return s})),Math.easeInOutQuad=function(t,e,a,n){return t/=n/2,t<1?a/2*t*t+e:(t--,-a/2*(t*(t-2)-1)+e)};var n=function(){return window.requestAnimationFrame||window.webkitRequestAnimationFrame||window.mozRequestAnimationFrame||function(t){window.setTimeout(t,1e3/60)}}();function l(t){document.documentElement.scrollTop=t,document.body.parentNode.scrollTop=t,document.body.scrollTop=t}function i(){return document.documentElement.scrollTop||document.body.parentNode.scrollTop||document.body.scrollTop}function s(t,e,a){var s=i(),o=t-s,r=20,u=0;e="undefined"===typeof e?500:e;var c=function t(){u+=r;var i=Math.easeInOutQuad(u,s,o,e);l(i),u<e?n(t):a&&"function"===typeof a&&a()};c()}},"31d4":function(t,e,a){},"49ac":function(t,e,a){"use strict";var n=a("31d4"),l=a.n(n);l.a},f115:function(t,e,a){"use strict";a.r(e);var n=function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",{staticClass:"app-container"},[a("div",{staticClass:"filter-container"},[a("label",{staticClass:"radio-label",staticStyle:{"padding-left":"0px"}},[t._v(t._s(t.$t("Statistics.shipCompany")))]),t._v(" "),a("el-select",{staticClass:"filter-item",staticStyle:{width:"130px"},attrs:{placeholder:t.$t("common.selectRemind"),clearable:"",size:"mini"},model:{value:t.listQuery.shipCompany,callback:function(e){t.$set(t.listQuery,"shipCompany",e)},expression:"listQuery.shipCompany"}},t._l(t.shipCompanyOptions,(function(t){return a("el-option",{key:t.key,attrs:{label:t.display_name,value:t.key}})})),1),t._v(" "),a("label",{staticClass:"radio-label",staticStyle:{"padding-left":"20px"}},[t._v(t._s(t.$t("Statistics.startEndDate")))]),t._v(" "),a("el-select",{staticClass:"filter-item",staticStyle:{width:"130px"},attrs:{placeholder:t.$t("common.selectRemind"),size:"mini"},model:{value:t.listQuery.startYear,callback:function(e){t.$set(t.listQuery,"startYear",e)},expression:"listQuery.startYear"}},t._l(t.yearOptions,(function(t){return a("el-option",{key:t.key,attrs:{label:t.display_name,value:t.key}})})),1),t._v("~\n    "),a("el-select",{staticClass:"filter-item",staticStyle:{width:"130px"},attrs:{placeholder:t.$t("common.selectRemind"),size:"mini"},model:{value:t.listQuery.endYear,callback:function(e){t.$set(t.listQuery,"endYear",e)},expression:"listQuery.endYear"}},t._l(t.yearOptions,(function(t){return a("el-option",{key:t.key,attrs:{label:t.display_name,value:t.key}})})),1),t._v(" "),a("el-button",{staticClass:"filter-item",staticStyle:{"margin-left":"20px"},attrs:{type:"primary",icon:"el-icon-search"},on:{click:t.handleFilter}},[t._v("\n      "+t._s(t.$t("common.Statistics"))+"\n    ")]),t._v(" "),a("el-button",{staticClass:"filter-item",attrs:{type:"info",icon:"el-icon-setting"},on:{click:t.handleReset}},[t._v("\n      "+t._s(t.$t("common.reset"))+"\n    ")]),t._v(" "),a("el-button",{staticClass:"filter-item",attrs:{type:"primary",plain:"",icon:"el-icon-download"},on:{click:t.handleExport}},[t._v("\n      "+t._s(t.$t("common.exportExcel"))+"\n    ")])],1),t._v(" "),a("el-table",{directives:[{name:"loading",rawName:"v-loading",value:t.listLoading,expression:"listLoading"}],key:t.tableKey,staticStyle:{width:"100%",overflow:"auto"},attrs:{data:t.list,border:"",fit:"",height:t.tableHeight,"highlight-current-row":"","row-style":{height:"35px"},"cell-style":{padding:"0"},"empty-text":t.$t("Statistics.noDataAlert")}},[a("el-table-column",{attrs:{label:t.$t("Statistics.CompanyAnnualData"),align:"center"}},[a("el-table-column",{attrs:{label:t.$t("Statistics.num"),width:"50px",align:"center"},scopedSlots:t._u([{key:"default",fn:function(e){var n=e.row;return[a("span",[t._v(t._s(n.num))])]}}])}),t._v(" "),a("el-table-column",{attrs:{label:t.$t("Statistics.shipCompany"),width:"105px",align:"center","show-overflow-tooltip":""},scopedSlots:t._u([{key:"default",fn:function(e){var n=e.row;return[a("span",[t._v(t._s(t._f("shipCompanyFilter")(n.shipCompany)))])]}}])}),t._v(" "),a("el-table-column",{attrs:{label:t.$t("Statistics.year"),width:"70px",align:"center","show-overflow-tooltip":""},scopedSlots:t._u([{key:"default",fn:function(e){var n=e.row;return[a("span",[t._v(t._s(n.year))])]}}])}),t._v(" "),a("el-table-column",{attrs:{label:t.$t("Statistics.FreightVolume"),width:"65px",align:"center","show-overflow-tooltip":"","render-header":t.renderheader},scopedSlots:t._u([{key:"default",fn:function(e){var n=e.row;return[a("span",[t._v(t._s(n.FreightVolume))])]}}])}),t._v(" "),a("el-table-column",{attrs:{label:t.$t("Statistics.FreightVolume"),align:"center"}},[a("el-table-column",{attrs:{label:t.$t("Statistics.heavyOil"),width:"50px",align:"center","show-overflow-tooltip":""},scopedSlots:t._u([{key:"default",fn:function(e){var n=e.row;return[a("span",[t._v(t._s(n.heavyOil))])]}}])}),t._v(" "),a("el-table-column",{attrs:{label:t.$t("Statistics.lightOil"),width:"50px",align:"center"},scopedSlots:t._u([{key:"default",fn:function(e){var n=e.row;return[a("span",[t._v(t._s(n.lightOil))])]}}])}),t._v(" "),a("el-table-column",{attrs:{label:t.$t("Statistics.dieselOil"),width:"50px",align:"center"},scopedSlots:t._u([{key:"default",fn:function(e){var n=e.row;return[a("span",[t._v(t._s(n.dieselOil))])]}}])}),t._v(" "),a("el-table-column",{attrs:{label:t.$t("Statistics.propane"),width:"50px",align:"center"},scopedSlots:t._u([{key:"default",fn:function(e){var n=e.row;return[a("span",[t._v(t._s(n.propane))])]}}])}),t._v(" "),a("el-table-column",{attrs:{label:t.$t("Statistics.butane"),width:"50px",align:"center"},scopedSlots:t._u([{key:"default",fn:function(e){var n=e.row;return[a("span",[t._v(t._s(n.butane))])]}}])}),t._v(" "),a("el-table-column",{attrs:{label:t.$t("Statistics.NaturalGas"),width:"60px",align:"center"},scopedSlots:t._u([{key:"default",fn:function(e){var n=e.row;return[a("span",[t._v(t._s(n.NaturalGas))])]}}])})],1),t._v(" "),a("el-table-column",{attrs:{label:t.$t("Statistics.inportFuelConsumption"),align:"center"}},[a("el-table-column",{attrs:{label:t.$t("Statistics.heavyOil"),width:"50px",align:"center","show-overflow-tooltip":""},scopedSlots:t._u([{key:"default",fn:function(e){var n=e.row;return[a("span",[t._v(t._s(n.inPortHeavyOil))])]}}])}),t._v(" "),a("el-table-column",{attrs:{label:t.$t("Statistics.lightOil"),width:"50px",align:"center"},scopedSlots:t._u([{key:"default",fn:function(e){var n=e.row;return[a("span",[t._v(t._s(n.inPortLightOil))])]}}])}),t._v(" "),a("el-table-column",{attrs:{label:t.$t("Statistics.dieselOil"),width:"50px",align:"center"},scopedSlots:t._u([{key:"default",fn:function(e){var n=e.row;return[a("span",[t._v(t._s(n.inPortDieselOil))])]}}])}),t._v(" "),a("el-table-column",{attrs:{label:t.$t("Statistics.propane"),width:"50px",align:"center"},scopedSlots:t._u([{key:"default",fn:function(e){var n=e.row;return[a("span",[t._v(t._s(n.inPortPropane))])]}}])}),t._v(" "),a("el-table-column",{attrs:{label:t.$t("Statistics.butane"),width:"50px",align:"center"},scopedSlots:t._u([{key:"default",fn:function(e){var n=e.row;return[a("span",[t._v(t._s(n.inPortButane))])]}}])}),t._v(" "),a("el-table-column",{attrs:{label:t.$t("Statistics.NaturalGas"),width:"60px",align:"center"},scopedSlots:t._u([{key:"default",fn:function(e){var n=e.row;return[a("span",[t._v(t._s(n.inPortNaturalGas))])]}}])})],1),t._v(" "),a("el-table-column",{attrs:{label:t.$t("Statistics.voyageMileage"),width:"70px",align:"center","show-overflow-tooltip":"","render-header":t.renderheader},scopedSlots:t._u([{key:"default",fn:function(e){var n=e.row;return[a("span",[t._v(t._s(n.voyageMileage))])]}}])}),t._v(" "),a("el-table-column",{attrs:{label:t.$t("Statistics.NoLoadMileage"),width:"70px",align:"center","show-overflow-tooltip":"","render-header":t.renderheader},scopedSlots:t._u([{key:"default",fn:function(e){var n=e.row;return[a("span",[t._v(t._s(n.NoLoadMileage))])]}}])}),t._v(" "),a("el-table-column",{attrs:{label:t.$t("Statistics.TurnoverVolume"),width:"65px",align:"center","show-overflow-tooltip":"","render-header":t.renderheader},scopedSlots:t._u([{key:"default",fn:function(e){var n=e.row;return[a("span",[t._v(t._s(n.TurnoverVolume))])]}}])}),t._v(" "),a("el-table-column",{attrs:{label:t.$t("Statistics.BoxTurnover"),width:"70px",align:"center","show-overflow-tooltip":"","render-header":t.renderheader},scopedSlots:t._u([{key:"default",fn:function(e){var n=e.row;return[a("span",[t._v(t._s(n.BoxTurnover))])]}}])})],1)],1),t._v(" "),a("pagination",{directives:[{name:"show",rawName:"v-show",value:t.total>0,expression:"total>0"}],attrs:{total:t.total,page:t.listQuery.page,limit:t.listQuery.limit},on:{"update:page":function(e){return t.$set(t.listQuery,"page",e)},"update:limit":function(e){return t.$set(t.listQuery,"limit",e)},pagination:t.getList}})],1)},l=[],i=a("bff4"),s=a("333d"),o=[{key:"2017",display_name:"2017"},{key:"2018",display_name:"2018"}],r=[{key:"1",display_name:"公司1"},{key:"2",display_name:"公司2"}],u=r.reduce((function(t,e){return t[e.key]=e.display_name,t}),{}),c={name:"CompanyAnnualData",components:{Pagination:s["a"]},filters:{shipCompanyFilter:function(t){return u[t]}},data:function(){return{tableKey:0,tableHeight:window.innerHeight-240,list:null,total:0,listLoading:!0,listQuery:{page:1,limit:20,shipCompany:void 0,startYear:void 0,endYear:void 0},yearOptions:o,shipCompanyOptions:r}},created:function(){this.list=null,this.total=0,this.listLoading=!1},methods:{getList:function(){var t=this;this.listLoading=!0;var e="/energyStatistics/CompanyAnnualData";Object(i["k"])(e,this.listQuery).then((function(e){t.list=e.data.items,t.total=e.data.total,setTimeout((function(){t.listLoading=!1}),1500)}))},handleReset:function(){this.listQuery.page=1,this.listQuery.shipCompany="",this.listQuery.startYear="",this.listQuery.endYear=""},handleFilter:function(){this.listQuery.page=1,this.getList()},handleExport:function(){this.$notify({title:"Success",message:"export Successfully",type:"success",duration:2e3})},renderheader:function(t,e){var a=e.column;e.$index;return t("span",{},[t("span",{},a.label.split("|")[0]),t("br"),t("span",{},a.label.split("|")[1])])}}},d=c,p=(a("49ac"),a("2877")),f=Object(p["a"])(d,n,l,!1,null,"c1b403f4",null);e["default"]=f.exports}}]);