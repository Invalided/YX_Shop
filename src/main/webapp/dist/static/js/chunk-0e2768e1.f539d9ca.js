(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-0e2768e1"],{"4c45":function(e,t,n){"use strict";n("a6bb")},7060:function(e,t,n){},a6bb:function(e,t,n){},bf28:function(e,t,n){"use strict";n.r(t);var i=function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("div",{staticClass:"container"},[n("div",{staticClass:"title"},[e._v("头条内容列表")]),e._v(" "),n("div",{staticClass:"add-button"},[n("el-button",{attrs:{type:"primary"},on:{click:function(t){return e.handleAdd()}}},[e._v("添加内容")])],1),e._v(" "),n("div",{staticClass:"table-container"},[n("el-table",{attrs:{data:e.headlineList,border:""}},[n("el-table-column",{attrs:{label:"序号",width:"60",type:"index"}}),e._v(" "),e._e(),e._v(" "),n("el-table-column",{attrs:{label:"头条名称",prop:"lineName"}}),e._v(" "),n("el-table-column",{attrs:{label:"头条链接",prop:"lineLink"}}),e._v(" "),n("el-table-column",{attrs:{label:"头条图片",width:"120"},scopedSlots:e._u([{key:"default",fn:function(e){return[n("img",{attrs:{src:e.row.lineImg}})]}}])}),e._v(" "),n("el-table-column",{attrs:{label:"优先级",prop:"priority",width:"80"}}),e._v(" "),n("el-table-column",{attrs:{label:"发布时间",prop:"createTime",width:"120"},scopedSlots:e._u([{key:"default",fn:function(t){return[e._v("\n          "+e._s(e.getDate(t.row.createTime))+"\n        ")]}}])}),e._v(" "),n("el-table-column",{attrs:{label:"修改时间",prop:"lastEditTime",width:"120"},scopedSlots:e._u([{key:"default",fn:function(t){return[e._v("\n          "+e._s(e.getDate(t.row.lastEditTime))+"\n        ")]}}])}),e._v(" "),n("el-table-column",{attrs:{label:"头条状态",prop:"enableStatus",width:"100"},scopedSlots:e._u([{key:"default",fn:function(t){return[1===t.row.enableStatus?n("el-tag",{attrs:{type:"success"}},[e._v("有效")]):n("el-tag",{attrs:{type:"info"}},[e._v("无效")])]}}])}),e._v(" "),n("el-table-column",{attrs:{label:"操作",fixed:"right",width:"180"},scopedSlots:e._u([{key:"default",fn:function(t){return[n("el-button",{on:{click:function(n){return e.handleEdit(t.row)}}},[e._v("编辑")]),e._v(" "),n("el-button",{attrs:{type:"danger"},on:{click:function(n){return e.handleDelete(t.row)}}},[e._v("\n            删除\n          ")])]}}])})],1)],1),e._v(" "),n("el-dialog",{attrs:{title:e.dialogTitle,width:"800px",visible:e.showDialog,"close-on-click-modal":!1,"close-on-press-escape":!1},on:{"update:visible":function(t){e.showDialog=t},close:e.resetForm}},[n("el-form",{ref:"form",attrs:{model:e.temp,"label-width":"90px",rules:e.rules,"hide-required-asterisk":""}},[n("el-form-item",{attrs:{label:"头条名称",prop:"lineName"}},[n("el-col",{attrs:{span:11}},[n("el-input",{model:{value:e.temp.lineName,callback:function(t){e.$set(e.temp,"lineName",t)},expression:"temp.lineName"}})],1)],1),e._v(" "),n("el-form-item",{attrs:{label:"头条链接",prop:"lineLink"}},[n("el-col",{attrs:{span:11}},[n("el-input",{model:{value:e.temp.lineLink,callback:function(t){e.$set(e.temp,"lineLink",t)},expression:"temp.lineLink"}})],1)],1),e._v(" "),n("el-form-item",{ref:"uploadElement",attrs:{label:"头条图片",prop:"lineImage"}},[n("el-upload",{ref:"upload",attrs:{action:"#","list-type":"picture-card","before-upload":e.beforeUpload,"on-remove":e.handleRemove,"auto-upload":!1,multiple:!1,"on-change":e.handleChange}},[n("i",{staticClass:"el-icon-plus"}),e._v(" "),n("div",{staticClass:"el-upload__tip",attrs:{slot:"tip"},slot:"tip"},[e._v("\n            只能上传jpg/png文件，且不超过500kb\n          ")])])],1),e._v(" "),n("el-form-item",{attrs:{label:"优先级",prop:"priority"}},[n("el-col",{attrs:{span:11}},[n("el-input",{attrs:{oninput:"value=value.replace(/[^\\d]/g,'')",placeholder:"请输入数字，最多5位",maxlength:"5"},model:{value:e.temp.priority,callback:function(t){e.$set(e.temp,"priority",t)},expression:"temp.priority"}})],1)],1),e._v(" "),n("el-form-item",{attrs:{label:"头条状态",prop:"enableStauts"}},[n("el-switch",{attrs:{"active-value":1,"inactive-value":0},model:{value:e.temp.enableStatus,callback:function(t){e.$set(e.temp,"enableStatus",t)},expression:"temp.enableStatus"}})],1)],1),e._v(" "),n("span",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[n("el-button",{on:{click:function(t){e.showDialog=!1}}},[e._v("取 消")]),e._v(" "),n("el-button",{attrs:{type:"primary"},on:{click:function(t){"添加内容"===e.dialogTitle?e.confirmAdd():e.confirmEdit()}}},[e._v("保 存")])],1)],1),e._v(" "),n("el-dialog",{attrs:{title:"提示",visible:e.showDeleteDialog,width:"400px"},on:{"update:visible":function(t){e.showDeleteDialog=t}}},[n("span",[e._v("确认删除内容?此操作不可撤销,请谨慎操作")]),e._v(" "),n("span",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[n("el-button",{on:{click:function(t){e.showDeleteDialog=!1}}},[e._v("取 消")]),e._v(" "),n("el-button",{attrs:{type:"danger"},on:{click:e.confirmDelete}},[e._v("删 除")])],1)])],1)},a=[],r=(n("96cf"),n("1da1")),l={data:function(){return{headlineList:[],dialogTitle:"",showDialog:!1,temp:{lineId:null,lineName:null,lineLink:null,priority:null,enableStatus:""},fileList:[],isType:0,isExit:!1,param:"",rules:{lineName:[{required:!0,message:"头条名称不能为空",trigger:"blur"}],lineLink:[{required:!0,message:"头条链接不能为空",trigger:"blur"}],lineImage:[{required:!0,message:"头条图片不能为空",trigger:"change"}],priority:[{required:!0,message:"优先级不能为空",trigger:"blur"}],createTime:[{required:!0,message:"创建时间不能为空",trigger:"blur"}],lastEditTime:[{required:!0,message:"修改时间不能为空",trigger:"blur"}],enableStatus:[{required:!0,message:"头条状态不能为空",trigger:"blur"}]},showDeleteDialog:!1}},created:function(){this.getheadlineList()},methods:{getheadlineList:function(){var e=Object(r["a"])(regeneratorRuntime.mark((function e(){var t,n=this;return regeneratorRuntime.wrap((function(e){while(1)switch(e.prev=e.next){case 0:t=new URLSearchParams,t.append("enableStatus","ALL"),this.headlineData=this.req({url:"superadmin/listheadlines",data:t,method:"POST"}).then((function(e){n.headlineList=e.rows,console.log(n.headlineList)}),(function(e){console.log("err: ",e),n.$message.success(e)}));case 3:case"end":return e.stop()}}),e,this)})));function t(){return e.apply(this,arguments)}return t}(),getDate:function(e){var t=new Date(e);return t.getFullYear()+"-"+(t.getMonth()+1)+"-"+t.getDate()+" "+t.getHours()+":"+t.getMinutes()+":"+t.getSeconds()},handleAdd:function(){this.dialogTitle="添加内容",this.showDialog=!0},handleEdit:function(e){var t=this;this.showDialog=!0,this.dialogTitle="编辑内容",this.$nextTick((function(){t.temp.lineId=e.lineId,t.temp.lineName=e.lineName,t.temp.lineLink=e.lineLink,t.temp.enableStatus=e.enableStatus,t.temp.priority=e.priority}))},handleDelete:function(e){this.showDeleteDialog=!0,this.temp.lineId=e.lineId},handleChange:function(e){e&&(this.isExit=!0,console.log("change"),this.$refs.uploadElement.clearValidate())},beforeUpload:function(e){return this.param=new FormData,0==this.isType?(this.param.append("headTitleManagementAdd_lineImg",e),console.log("0")):(this.param.append("headTitleManagementEdit_lineImg",e),console.log("1")),!1},confirmAdd:function(){var e=Object(r["a"])(regeneratorRuntime.mark((function e(){var t=this;return regeneratorRuntime.wrap((function(e){while(1)switch(e.prev=e.next){case 0:this.isExit?(console.log("image",this.isExit),this.rules.lineImage[0].validator=function(e,n,i){i(),t.$refs.upload.submit()}):this.rules.lineImage[0].validator=function(e,t,n){n(e,t)},console.log("temp is ",this.temp),this.$refs.form.validate(function(){var e=Object(r["a"])(regeneratorRuntime.mark((function e(n){return regeneratorRuntime.wrap((function(e){while(1)switch(e.prev=e.next){case 0:n&&(t.param.append("headLineStr",JSON.stringify(t.temp)),t.req({url:"superadmin/addheadline",data:t.param,method:"POST"}).then((function(e){t.getheadlineList(),t.$message.success("添加成功")}),(function(e){console.log("err: ",e)})),t.showDialog=!1,t.$refs.form.resetFields());case 1:case"end":return e.stop()}}),e)})));return function(t){return e.apply(this,arguments)}}());case 3:case"end":return e.stop()}}),e,this)})));function t(){return e.apply(this,arguments)}return t}(),confirmEdit:function(){var e=Object(r["a"])(regeneratorRuntime.mark((function e(){var t=this;return regeneratorRuntime.wrap((function(e){while(1)switch(e.prev=e.next){case 0:this.rules.lineImage[0].validator=function(e,t,n){n()},console.log(this.isExit),this.$refs.form.validate(function(){var e=Object(r["a"])(regeneratorRuntime.mark((function e(n){var i;return regeneratorRuntime.wrap((function(e){while(1)switch(e.prev=e.next){case 0:i=new FormData,n&&(t.isExit?(t.$refs.upload.submit(),t.param.append("headLineStr",JSON.stringify(t.temp))):(i.append("headTitleManagementEdit_lineImg",null),i.append("headLineStr",JSON.stringify(t.temp)),t.param=i),t.req({url:"superadmin/modifyheadline",data:t.param,method:"POST"}).then((function(e){t.getheadlineList(),t.$message.success("修改成功")}),(function(e){console.log("err: ",e)})),t.showDialog=!1,t.$refs.form.resetFields());case 2:case"end":return e.stop()}}),e)})));return function(t){return e.apply(this,arguments)}}());case 3:case"end":return e.stop()}}),e,this)})));function t(){return e.apply(this,arguments)}return t}(),confirmDelete:function(){var e=Object(r["a"])(regeneratorRuntime.mark((function e(){var t=this;return regeneratorRuntime.wrap((function(e){while(1)switch(e.prev=e.next){case 0:this.req({url:"superadmin/removeheadline?headLineId="+this.temp.lineId,data:this.param,method:"POST"}).then((function(e){t.showDeleteDialog=!1,t.getheadlineList(),t.$message.success("删除成功")}),(function(e){console.log("err: ",e)}));case 1:case"end":return e.stop()}}),e,this)})));function t(){return e.apply(this,arguments)}return t}(),handleRemove:function(e,t){console.log(e,t),this.isExit=!1,console.log("remove",this.isExit)},resetForm:function(){this.$refs.form.resetFields(),this.isType=0}}},s=l,o=(n("edc5"),n("4c45"),n("2877")),u=Object(o["a"])(s,i,a,!1,null,"1e1de99a",null);t["default"]=u.exports},edc5:function(e,t,n){"use strict";n("7060")}}]);