(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-27257066"],{3123:function(t,a,e){"use strict";e.d(a,"a",(function(){return o}));var n=e("b775");function o(){return Object(n["a"])({url:"/qiniu/upload/token",method:"get"})}},"5cf3":function(t,a,e){"use strict";e.r(a);var n=function(){var t=this,a=t.$createElement,e=t._self._c||a;return e("el-upload",{attrs:{data:t.dataObj,multiple:!0,"before-upload":t.beforeUpload,action:"https://upload.qbox.me",drag:""}},[e("i",{staticClass:"el-icon-upload"}),t._v(" "),e("div",{staticClass:"el-upload__text"},[t._v("\n    将文件拖到此处，或"),e("em",[t._v("点击上传")])])])},o=[],u=e("3123"),i={data:function(){return{dataObj:{token:"",key:""},image_uri:[],fileList:[]}},methods:{beforeUpload:function(){var t=this;return new Promise((function(a,e){Object(u["a"])().then((function(e){var n=e.data.qiniu_key,o=e.data.qiniu_token;t._data.dataObj.token=o,t._data.dataObj.key=n,a(!0)})).catch((function(t){e(!1)}))}))}}},r=i,c=e("2877"),d=Object(c["a"])(r,n,o,!1,null,null,null);a["default"]=d.exports}}]);