(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-3e9f23d3","chunk-92598964"],{1593:function(e,t,i){"use strict";var o=i("2f7b"),n=function(e,t,i){var o=i.componentInstance,n=t.value;if(!o.height)throw new Error("el-$table must set the height. Such as height='100px'");var a=n&&n.bottomOffset||30;if(o){var s=window.innerHeight-e.getBoundingClientRect().top-a;setTimeout((function(){o.layout.setHeight(s),o.doLayout()}),1)}},a={bind:function(e,t,i){e.resizeListener=function(){n(e,t,i)},Object(o["a"])(window.document.body,e.resizeListener)},inserted:function(e,t,i){n(e,t,i)},unbind:function(e){Object(o["b"])(window.document.body,e.resizeListener)}},s=function(e){e.directive("adaptive",a)};window.Vue&&(window["adaptive"]=a,Vue.use(s)),a.install=s;t["a"]=a},4624:function(e,t,i){"use strict";var o=i("2f62");function n(e,t){var i=Object.keys(e);if(Object.getOwnPropertySymbols){var o=Object.getOwnPropertySymbols(e);t&&(o=o.filter((function(t){return Object.getOwnPropertyDescriptor(e,t).enumerable}))),i.push.apply(i,o)}return i}function a(e){for(var t=1;t<arguments.length;t++){var i=null!=arguments[t]?arguments[t]:{};t%2?n(i,!0).forEach((function(t){s(e,t,i[t])})):Object.getOwnPropertyDescriptors?Object.defineProperties(e,Object.getOwnPropertyDescriptors(i)):n(i).forEach((function(t){Object.defineProperty(e,t,Object.getOwnPropertyDescriptor(i,t))}))}return e}function s(e,t,i){return t in e?Object.defineProperty(e,t,{value:i,enumerable:!0,configurable:!0,writable:!0}):e[t]=i,e}t["a"]={computed:a({},Object(o["d"])("zidainma",["list","situations"]))}},"4a5a":function(e,t,i){"use strict";i.r(t);var o=function(){var e=this,t=e.$createElement,i=e._self._c||t;return i("div",{staticClass:"app-container"},[i("el-dialog",{attrs:{title:e.$t("ship.fuelId"),width:"80%","append-to-body":!0,visible:e.shipComPanyNamevicode,"before-close":e.addDetale},on:{"update:visible":function(t){e.shipComPanyNamevicode=t}}},[i("el-table",{directives:[{name:"loading",rawName:"v-loading",value:e.listLoading,expression:"listLoading"},{name:"adaptive",rawName:"v-adaptive",value:{bottomOffset:38},expression:"{bottomOffset: 38}"}],ref:"multipleTable",staticStyle:{width:"100%",overflow:"auto"},attrs:{data:e.shipCompanylist,border:"","append-to-body":"",fit:"",height:"100px","highlight-current-row":"","row-style":{height:"35px"},"cell-style":{padding:"0"}},on:{"selection-change":e.handleSelectionChange}},[i("el-table-column",{attrs:{type:"selection",width:"55",align:"center"}}),e._v(" "),i("el-table-column",{attrs:{label:e.$t("common.no"),type:"index","show-overflow-tooltip":"",width:"50",align:"center"}}),e._v(" "),i("el-table-column",{attrs:{label:e.$t("ship.Oiltype"),align:"center","show-overflow-tooltip":""},scopedSlots:e._u([{key:"default",fn:function(t){var o=t.row;return[i("span",[e._v(e._s(o.fuelName))])]}}])}),e._v(" "),i("el-table-column",{attrs:{label:e.$t("MRV.Datacollectionmethod"),align:"center","show-overflow-tooltip":""},scopedSlots:e._u([{key:"default",fn:function(t){var o=t.row;return[i("el-select",{attrs:{placeholder:e.$t("common.selectRemind")},model:{value:o.consMethod,callback:function(t){e.$set(o,"consMethod",t)},expression:"row.consMethod"}},e._l(e.consMethodArray,(function(e){return i("el-option",{key:e.value,attrs:{label:e.label,value:e.value}})})),1)]}}])})],1),e._v(" "),i("div",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[i("el-button",{on:{click:e.addDetale}},[e._v("\n        "+e._s(e.$t("common.close"))+"\n      ")]),e._v(" "),i("el-button",{attrs:{type:"primary"},on:{click:function(t){return e.saveRelation(e.tableChecked)}}},[e._v("\n        "+e._s(e.$t("common.save"))+"\n      ")])],1)],1)],1)},n=[],a=i("1593"),s=i("bff4"),l={name:"ShipComPanySelect",directives:{adaptive:a["a"]},props:{shipComPanyNamevicode:{type:Boolean},listData:{type:Array}},data:function(){return{changValue:null,shipCompanylist:[],listLoading:!1,shipComPanyNmevicode:!1,consMethodArray:[{value:"1",label:"Method of using fuel delivery note"},{value:"2",label:"Method of using flowmeter"},{value:"3",label:"Monitoring method of fuel tank"}]}},created:function(){this.initData()},mounted:function(){},methods:{initData:function(){var e=this;Object(s["r"])().then((function(t){e.shipCompanylist=t.data.data;var i=[];t.data.data.forEach((function(t){e.listData.forEach((function(e){t.fuelId===e.fuelId&&(t.consMethod=e.consMethod,i.push(t))}))})),e.$nextTick((function(){i.forEach((function(t){e.$refs.multipleTable.toggleRowSelection(t,!0)}))}))}))},addDetale:function(){this.$emit("shipComPanyNameData",!1)},saveRelation:function(){this.$emit("shipComPanyNameDatas",this.tableChecked)},handleSelectionChange:function(e){this.tableChecked=e},getShipCompanyList:function(){this.listLoading=!0}}},r=l,p=i("2877"),c=Object(p["a"])(r,o,n,!1,null,"f35764e8",null);t["default"]=c.exports},aea2:function(e,t,i){"use strict";i.r(t);var o=function(){var e=this,t=e.$createElement,i=e._self._c||t;return i("div",{staticClass:"app-container"},[i("div",{staticClass:"filter-container"},[i("el-select",{staticClass:"filter-item",staticStyle:{width:"100px"},attrs:{placeholder:e.$t("common.selectRemind"),value:"1"},on:{change:e.xhSelectChange},model:{value:e.oiMainEngineXh,callback:function(t){e.oiMainEngineXh=t},expression:"oiMainEngineXh"}},e._l(e.oiShipGeIds,(function(e){return i("el-option",{key:e.id,attrs:{label:e.xh,value:e.id}})})),1)],1),e._v(" "),i("el-form",{ref:"oiShipGeForm",attrs:{rules:e.oiShipGeRules,model:e.oiShipGe,"label-width":"100px"}},[i("el-row",[i("el-col",{attrs:{span:12}},[i("el-form-item",{attrs:{label:e.$t("ship.type"),prop:"engineType"}},[i("el-input",{attrs:{placeholder:e.$t("common.inputRemind"),readonly:this.formIsDisabled},model:{value:e.oiShipGe.engineType,callback:function(t){e.$set(e.oiShipGe,"engineType",t)},expression:"oiShipGe.engineType"}})],1)],1),e._v(" "),i("el-col",{attrs:{span:12}},[i("el-form-item",{attrs:{label:e.$t("ship.ratedPower"),prop:"ratePower"}},[i("el-input",{attrs:{type:"number",placeholder:e.$t("common.inputRemind"),readonly:this.formIsDisabled},model:{value:e.oiShipGe.ratePower,callback:function(t){e.$set(e.oiShipGe,"ratePower",t)},expression:"oiShipGe.ratePower"}},[i("i",{staticStyle:{color:"red"},attrs:{slot:"suffix"},slot:"suffix"},[e._v("Kw")])])],1)],1)],1),e._v(" "),i("el-row",[i("el-col",{attrs:{span:12}},[i("el-form-item",{attrs:{label:e.$t("ship.ratedSpeed")}},[i("el-input",{attrs:{type:"number",placeholder:e.$t("common.inputRemind"),readonly:this.formIsDisabled},model:{value:e.oiShipGe.ratedSpeed,callback:function(t){e.$set(e.oiShipGe,"ratedSpeed",t)},expression:"oiShipGe.ratedSpeed"}},[i("i",{staticStyle:{color:"red"},attrs:{slot:"suffix"},slot:"suffix"},[e._v("Rpm")])])],1)],1),e._v(" "),i("el-col",{attrs:{span:12}},[i("el-form-item",{attrs:{label:e.$t("ship.consumpRate"),prop:"consumpRate"}},[i("el-input",{attrs:{type:"number",placeholder:e.$t("common.inputRemind"),readonly:this.formIsDisabled},model:{value:e.oiShipGe.consumpRate,callback:function(t){e.$set(e.oiShipGe,"consumpRate",t)},expression:"oiShipGe.consumpRate"}},[i("i",{staticStyle:{color:"red"},attrs:{slot:"suffix"},slot:"suffix"},[e._v("g/(kW·h)")])])],1)],1)],1),e._v(" "),i("el-row",[i("el-col",{attrs:{span:12}},[i("el-form-item",{attrs:{label:e.$t("ship.energyId"),prop:"energyid"}},[i("el-checkbox-group",{on:{change:e.checkboxChange},model:{value:e.engineIds,callback:function(t){e.engineIds=t},expression:"engineIds"}},e._l(e.situations,(function(t,o){return i("el-checkbox",{key:o,attrs:{label:t.formCode}},[e._v(e._s(t.formName))])})),1)],1)],1),e._v(" "),i("el-col",{attrs:{span:6}},[i("el-form-item",{attrs:{label:e.$t("ship.equipmentXh"),prop:"xh"}},[i("el-input",{attrs:{placeholder:e.$t("common.inputRemind"),readonly:this.formIsDisabled},model:{value:e.oiShipGe.xh,callback:function(t){e.$set(e.oiShipGe,"xh",t)},expression:"oiShipGe.xh"}})],1)],1),e._v(" "),i("el-col",{attrs:{span:6}},[i("el-form-item",{attrs:{label:e.$t("ship.deviceSerial"),prop:"serialNo"}},[i("el-input",{attrs:{placeholder:e.$t("common.inputRemind"),readonly:this.formIsDisabled},model:{value:e.oiShipGe.serialNo,callback:function(t){e.$set(e.oiShipGe,"serialNo",t)},expression:"oiShipGe.serialNo"}})],1)],1)],1),e._v(" "),i("el-row",[i("el-col",{attrs:{span:12}},[i("el-form-item",{attrs:{label:e.$t("ship.source")}},[i("el-input",{attrs:{placeholder:e.$t("common.inputRemind"),readonly:this.formIsDisabled},model:{value:e.oiShipGe.mfrsName,callback:function(t){e.$set(e.oiShipGe,"mfrsName",t)},expression:"oiShipGe.mfrsName"}})],1)],1),e._v(" "),i("el-col",{attrs:{span:12}},[i("el-form-item",{attrs:{label:e.$t("ship.produceTime")}},[i("el-date-picker",{staticStyle:{width:"100%"},attrs:{editable:!0,type:"year",size:"small",placeholder:e.$t("common.dataYearRemind")},model:{value:e.oiShipGe.produceTime,callback:function(t){e.$set(e.oiShipGe,"produceTime",t)},expression:"oiShipGe.produceTime"}})],1)],1)],1),e._v(" "),i("el-form-item",{attrs:{label:e.$t("ship.fuelId"),prop:"fuelIds"}},[i("el-button",{attrs:{type:"primary",disabled:this.formIsDisabled},on:{click:e.shipTypes}},[e._v(e._s(e.$t("common.select")))]),e._v(" "),e.oiShipGe.shipEquipmentFuelMaps.length?i("div",{staticStyle:{width:"100%",height:"auto",border:"1px #ccc solid","margin-top":"10px",padding:"0 15px"}},[i("el-row",e._l(e.oiShipGe.shipEquipmentFuelMaps,(function(t,o){return i("el-col",{key:o,attrs:{span:12}},[i("span",[e._v(e._s(e.$t("ship.fuel"))+":"+e._s(t.fuelName))]),e._v(" "),"1"===t.consMethod?i("span",[e._v(e._s(e.$t("ship.method1")))]):e._e(),e._v(" "),"2"===t.consMethod?i("span",[e._v(e._s(e.$t("ship.method2")))]):e._e(),e._v(" "),"3"===t.consMethod?i("span",[e._v(e._s(e.$t("ship.method3")))]):e._e()])})),1)],1):e._e()],1)],1),e._v(" "),e.shipComPanyNamevicode?i("shipType",{attrs:{shipComPanyNamevicode:e.shipComPanyNamevicode,listData:e.oiShipGe.shipEquipmentFuelMaps},on:{shipComPanyNameDatas:e.shipComPanyNameDatas,shipComPanyNameData:e.shipComPanyNameData}}):e._e()],1)},n=[],a=i("bff4"),s=i("4a5a"),l=i("4624"),r={name:"ShipPM",components:{shipType:s["default"]},mixins:[l["a"]],props:{editStatus:{type:String,default:""},editRowId:{type:String,default:""},rowModel:{type:Object,default:function(){}}},data:function(){return{shipComPanyNamevicode:!1,consMethodArray:[{value:"1",label:"使用燃油交付单的方法"},{value:"2",label:"使用流量计的方法"},{value:"3",label:"使用燃油舱柜监测的方法"}],engineIds:[],oiShipGeId:"",oiMainEngineXh:"",formIsDisabled:!1,oiShipGeIds:[],oiShipGe:{id:"",shipId:"",engineType:"",ratePower:"",ratedSpeed:"",consumpRate:"",energyid:"",mfrsName:"",produceTime:"",xh:"",fuelIds:[],shipEquipmentFuelMaps:[],serialNo:""},oiShipGeRules:{engineType:[{required:!0,message:this.$t("common.inputRequired"),trigger:"blur"}],ratePower:[{required:!0,message:this.$t("common.inputRequired"),trigger:"blur"}],consumpRate:[{required:!0,message:this.$t("common.inputRequired"),trigger:"blur"}],energyid:[{required:!0,message:this.$t("common.inputRequired"),trigger:"blur"}],xh:[{required:!0,message:this.$t("common.inputRequired"),trigger:"blur"}],serialNo:[{required:!0,message:this.$t("common.inputRequired"),trigger:"blur"}]}}},watch:{oiShipGeIds:function(e){e.length?(this.oiShipGeId=e[0].id,this.oiMainEngineXh=e[0].xh||e[0].id,this.initData()):this.oiMainEngineXh=""}},methods:{shipTypes:function(){this.shipComPanyNamevicode=!0},shipComPanyNameData:function(){this.shipComPanyNamevicode=!1},shipComPanyNameDatas:function(e){this.oiShipGe.shipEquipmentFuelMaps=e,this.shipComPanyNamevicode=!1},initData:function(){var e=this;Object(a["k"])("/shipManager/getShipAuEngine",{id:this.oiShipGeId}).then((function(t){e.oiShipGe=t.data.data,e.engineIds=e.getEngineIds(t.data.data.energyid),e.oiShipGe.fuelIds.length=0;var i=!0,o=!1,n=void 0;try{for(var a,s=e.oiShipGe.shipEquipmentFuelMaps[Symbol.iterator]();!(i=(a=s.next()).done);i=!0){var l=a.value;e.oiShipGe.fuelIds.push(l.fuelId)}}catch(r){o=!0,n=r}finally{try{i||null==s.return||s.return()}finally{if(o)throw n}}}))},getEngineIds:function(e){var t=[];return""!==e&&(t=e.split(",")),t},resetTemp:function(){this.engineIds=[],this.oiMainEngineXh="",this.oiShipGe={id:"",shipId:"",engineType:"",ratePower:"",ratedSpeed:"",consumpRate:"",energyid:"",mfrsName:"",produceTime:"",xh:"",fuelIds:[],shipEquipmentFuelMaps:[],serialNo:""}},handleModifyStatus:function(e,t){this.$message({message:"操作Success",type:"success"}),e.status=t},CreateModel:function(){var e=this;this.resetTemp(),this.$nextTick((function(){e.$refs["oiShipGeForm"].clearValidate()}))},EditRowData:function(e){var t=this,i=Object.assign({},e);this.oiShipGe=i.shipPM[1],this.$nextTick((function(){t.$refs["oiShipGeForm"].clearValidate()}))},xhSelectChange:function(){this.oiShipGeId=this.oiMainEngineXh,this.initData()},checkboxChange:function(){this.engineIds.length>0?this.oiShipGe.energyId=this.engineIds[0]:this.oiShipGe.energyId=""}}},p=r,c=i("2877"),h=Object(c["a"])(p,o,n,!1,null,null,null);t["default"]=h.exports}}]);