(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-3e4892e5"],{9606:function(e,t,o){"use strict";o.r(t);var n=function(){var e=this,t=e.$createElement,o=e._self._c||t;return o("el-form",{ref:"oilForm",attrs:{rules:e.oilRules,model:e.oilForm}},[o("el-table",{staticStyle:{width:"100%"},attrs:{data:e.oilFormData,stripe:"",border:"",size:"mini"}},[o("el-table-column",{attrs:{label:e.$t("voyageOil.oilType"),align:"center"},scopedSlots:e._u([{key:"default",fn:function(t){var n=t.row;return[o("span",[e._v(e._s(n.oilName))])]}}])}),e._v(" "),o("el-table-column",{attrs:{label:e.$t("voyageOil.arrOilTons"),align:"center"},scopedSlots:e._u([{key:"default",fn:function(t){var n=t.row;return[o("el-input",{staticClass:"el_input",attrs:{maxlength:10,type:"number",oninput:"if(isNaN(value)) { value = parseFloat(value) } if(value.indexOf('.')>0){value=value.slice(0,value.indexOf('.')+4)}",readonly:e.readOnly},model:{value:n.arrTons,callback:function(t){e.$set(n,"arrTons",t)},expression:"row.arrTons"}})]}}])}),e._v(" "),o("el-table-column",{attrs:{label:e.$t("voyageOil.deptOilTons"),align:"center"},scopedSlots:e._u([{key:"default",fn:function(t){var n=t.row;return[o("el-input",{staticClass:"el_input",attrs:{readonly:e.readOnly,type:"number",oninput:"if(isNaN(value)) { value = parseFloat(value) } if(value.indexOf('.')>0){value=value.slice(0,value.indexOf('.')+4)}"},model:{value:n.deptTons,callback:function(t){e.$set(n,"deptTons",t)},expression:"row.deptTons"}})]}}])}),e._v(" "),o("el-table-column",{attrs:{label:e.$t("voyageOil.addOil"),align:"center"},scopedSlots:e._u([{key:"default",fn:function(t){var n=t.row;return[o("el-input",{staticClass:"el_input",attrs:{disabled:""},model:{value:n.addTons,callback:function(t){e.$set(n,"addTons",t)},expression:"row.addTons"}})]}}])}),e._v(" "),o("el-table-column",{attrs:{label:e.$t("voyageOil.outOil"),align:"center"},scopedSlots:e._u([{key:"default",fn:function(t){var n=t.row;return[o("el-input",{staticClass:"el_input",attrs:{disabled:""},model:{value:n.outTons,callback:function(t){e.$set(n,"outTons",t)},expression:"row.outTons"}})]}}])}),e._v(" "),o("el-table-column",{attrs:{label:e.$t("voyagePort.Correctionquantity"),type:"number",oninput:"if(isNaN(value)) { value = parseFloat(value) } if(value.indexOf('.')>0){value=value.slice(0,value.indexOf('.')+4)}",align:"center"},scopedSlots:e._u([{key:"default",fn:function(t){var n=t.row;return[o("el-input",{staticClass:"el_input",attrs:{type:"number",readonly:e.readOnly},model:{value:n.correctTons,callback:function(t){e.$set(n,"correctTons",t)},expression:"row.correctTons"}})]}}])}),e._v(" "),o("el-table-column",{attrs:{label:e.$t("MRV.Fuel"),align:"center"},scopedSlots:e._u([{key:"default",fn:function(t){var n=t.row;return[o("el-input",{staticClass:"el_input",attrs:{value:(Number(n.arrTons)-Number(n.deptTons)+Number(n.addTons)-Number(n.outTons)+Number(n.correctTons)).toFixed(1),readonly:e.readOnly}})]}}])})],1)],1)},l=[],a=o("bff4"),i={name:"OilTons",props:{times:{},timeEnd:{},editStatus:{type:String,default:""},portEditRowId:{type:String,default:""},stabel:{type:String,default:""}},data:function(){var e=this;return{pickerOptionsEndportTemp:{disabledDate:function(t){var o=e.times,n=e.timeEnd;if(o&&n)return t.getTime()<new Date(o).getTime()-864e5||t.getTime()>new Date(n).getTime()}},shipId:"",oilTons:[],readOnly:!1,oilFormData:[],tempOilFormdata:[],oilRules:{},oilForm:{}}},watch:{shipId:function(e){e?this.getFuelList(e):this.initsave()}},created:function(){this.oilFormData=[],this.tempOilFormdata=[]},methods:{initsave:function(){var e=this,t=[],o=[];Object(a["x"])().then((function(n){var l=!0,a=!1,i=void 0;try{for(var r,s=n.data.data.items[Symbol.iterator]();!(l=(r=s.next()).done);l=!0){var u=r.value,d={id:"",oilId:"",oilName:"",arrTons:0,deptTons:0,addTons:0,addBillNo:"",addTm:null,outTons:0,outTm:null,correctTons:0,consTons:0},c={id:"",oilId:"",oilName:"",arrTons:0,deptTons:0,addTons:0,addBillNo:"",addTm:null,outTons:0,outTm:null,correctTons:0,consTons:0};d.id=u.id,d.oilId=u.fuelCode,d.oilName=u.fuelName,c.id=u.id,c.oilId=u.fuelCode,c.oilName=u.fuelName,t.push(d),o.push(c)}}catch(p){a=!0,i=p}finally{try{l||null==s.return||s.return()}finally{if(a)throw i}}if(e.oilFormData=t,e.tempOilFormdata=o,e.oilTons.length>0){var m=e.oilTons.map((function(e){return e.oilId})),f=e.oilFormData.filter((function(e){return!m.some((function(t){return t===e.oilId}))}));e.oilFormData=f.concat(e.oilTons)}}))},initDat:function(){this.oilFormData.length=0,this.tempOilFormdata.length=0},getFuelList:function(e){var t=this;Object(a["h"])(e).then((function(e){if(0==e.data.data.length)t.initsave();else{var o=[],n=[];if(e.data.data.forEach((function(e){var t={id:"",oilId:"",oilName:"",arrTons:0,deptTons:0,addTons:0,addBillNo:"",addTm:null,outTons:0,outTm:null,consTons:0},l={id:"",oilId:"",oilName:"",arrTons:0,deptTons:0,addTons:0,addBillNo:"",addTm:null,outTons:0,outTm:null,consTons:0};t.consTons=e.consTons,t.id=e.id,t.oilId=e.fuelCode,t.oilName=e.fuelName,l.id=e.id,l.oilId=e.fuelCode,l.oilName=e.fuelName,l.consTons=e.consTons,o.push(t),n.push(l)})),t.oilFormData=o,t.tempOilFormdata=n,t.oilTons.length>0){var l=t.oilTons.map((function(e){return e.oilId})),a=t.oilFormData.filter((function(e){return!l.some((function(t){return t===e.oilId}))}));t.oilFormData=a.concat(t.oilTons)}}}))}}},r=i,s=(o("9623"),o("2877")),u=Object(s["a"])(r,n,l,!1,null,null,null);t["default"]=u.exports},9623:function(e,t,o){"use strict";var n=o("d88a"),l=o.n(n);l.a},d88a:function(e,t,o){}}]);