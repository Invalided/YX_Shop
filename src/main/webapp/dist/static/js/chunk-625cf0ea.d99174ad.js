(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-625cf0ea"],{"1bb0":function(e,t,r){},5034:function(e,t,r){"use strict";r("1bb0")},c90f:function(e,t,r){"use strict";r("d1d1")},d1d1:function(e,t,r){},dae2:function(e,t,r){"use strict";r.r(t);var n=function(){var e=this,t=e.$createElement,r=e._self._c||t;return r("div",{staticClass:"container"},[r("div",{staticClass:"title"},[e._v("帐号内容列表")]),e._v(" "),r("div",{staticClass:"add-button"}),e._v(" "),r("div",{staticClass:"table-container"},[r("el-table",{attrs:{data:e.userList,border:""}},[r("el-table-column",{attrs:{label:"序号",width:"60",type:"index"}}),e._v(" "),e._e(),e._v(" "),r("el-table-column",{attrs:{label:"用户名称",prop:"name"}}),e._v(" "),r("el-table-column",{attrs:{label:"性别",prop:"gender",width:"100"},scopedSlots:e._u([{key:"default",fn:function(t){return[1==t.row.gender?r("el-tag",{attrs:{type:"info"}},[e._v("男")]):r("el-tag",{attrs:{type:"info"}},[e._v("女")])]}}])}),e._v(" "),r("el-table-column",{attrs:{label:"电子邮箱",prop:"email",width:"350"}}),e._v(" "),r("el-table-column",{attrs:{label:"用户类型",width:"120"},scopedSlots:e._u([{key:"default",fn:function(t){return[1===t.row.userType?r("el-tag",{attrs:{type:"info"}},[e._v(" 顾客 ")]):e._e(),e._v(" "),2===t.row.userType?r("el-tag",{attrs:{type:"info"}},[e._v(" 商家 ")]):e._e(),e._v(" "),3===t.row.userType?r("el-tag",{attrs:{type:"info"}},[e._v(" 管理员 ")]):e._e()]}}])}),e._v(" "),r("el-table-column",{attrs:{label:"用户类型",prop:"userType",width:"80"}}),e._v(" "),r("el-table-column",{attrs:{label:"创建时间",prop:"createTime",width:"120"},scopedSlots:e._u([{key:"default",fn:function(t){return[e._v("\n          "+e._s(e.getDate(t.row.createTime))+"\n        ")]}}])}),e._v(" "),r("el-table-column",{attrs:{label:"修改时间",prop:"lastEditTime",width:"120"},scopedSlots:e._u([{key:"default",fn:function(t){return[e._v("\n          "+e._s(e.getDate(t.row.lastEditTime))+"\n        ")]}}])}),e._v(" "),r("el-table-column",{attrs:{label:"帐号状态",prop:"enableStatus",width:"100"},scopedSlots:e._u([{key:"default",fn:function(t){return[1===t.row.enableStatus?r("el-tag",{attrs:{type:"success"}},[e._v("启用")]):r("el-tag",{attrs:{type:"info"}},[e._v("禁用")])]}}])}),e._v(" "),r("el-table-column",{attrs:{label:"操作",fixed:"right",width:"180"},scopedSlots:e._u([{key:"default",fn:function(t){return[r("el-button",{on:{click:function(r){return e.handleEdit(t.row)}}},[e._v("编辑")]),e._v(" "),r("el-button",{attrs:{type:"danger",disabled:""},on:{click:function(r){return e.handleDelete(t.row)}}},[e._v("\n            删除\n          ")])]}}])})],1)],1),e._v(" "),r("el-dialog",{attrs:{title:e.dialogTitle,width:"800px",visible:e.showAdd,"close-on-click-modal":!1,"close-on-press-escape":!1},on:{"update:visible":function(t){e.showAdd=t},close:e.resetForm}},[r("el-form",{ref:"form",attrs:{model:e.temp,"label-width":"90px",rules:e.rules,"hide-required-asterisk":""}},[r("el-form-item",{attrs:{label:"用户名称",prop:"name"}},[r("el-col",{attrs:{span:11}},[r("el-input",{model:{value:e.temp.name,callback:function(t){e.$set(e.temp,"name",t)},expression:"temp.name"}})],1)],1),e._v(" "),r("el-form-item",{attrs:{label:"性别",prop:"gender"}},[r("el-col",{attrs:{span:11}},[r("el-input",{model:{value:e.temp.gender,callback:function(t){e.$set(e.temp,"gender",t)},expression:"temp.gender"}})],1)],1),e._v(" "),r("el-form-item",{attrs:{label:"电子邮箱",prop:"email"}},[r("el-col",{attrs:{span:11}},[r("el-input",{model:{value:e.temp.email,callback:function(t){e.$set(e.temp,"email",t)},expression:"temp.email"}})],1)],1),e._v(" "),r("el-form-item",{ref:"uploadElement",attrs:{label:"用户头像",prop:"profileImg"}},[r("el-upload",{ref:"upload",attrs:{action:"#","list-type":"picture-card","before-upload":e.beforeUpload,"on-remove":e.handleRemove,"auto-upload":!1,multiple:!1,"on-change":e.handleChange}},[r("i",{staticClass:"el-icon-plus"}),e._v(" "),r("div",{staticClass:"el-upload__tip",attrs:{slot:"tip"},slot:"tip"},[e._v("\n            只能上传jpg/png文件，且不超过500kb\n          ")])])],1),e._v(" "),r("el-form-item",{attrs:{label:"帐号状态",prop:"enableStauts"}},[r("el-switch",{attrs:{"active-value":1,"inactive-value":0},model:{value:e.temp.enableStatus,callback:function(t){e.$set(e.temp,"enableStatus",t)},expression:"temp.enableStatus"}})],1)],1),e._v(" "),r("span",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[r("el-button",{on:{click:function(t){e.showAdd=!1}}},[e._v("取 消")]),e._v(" "),r("el-button",{attrs:{type:"primary"},on:{click:function(t){return e.confirmAdd()}}},[e._v("保 存")])],1)],1),e._v(" "),r("el-dialog",{attrs:{title:e.dialogTitle,width:"800px",visible:e.showEdit,"close-on-click-modal":!1,"close-on-press-escape":!1},on:{"update:visible":function(t){e.showEdit=t},close:e.resetForm}},[r("el-form",{ref:"form",attrs:{model:e.temp,"label-width":"90px",rules:e.rules,"hide-required-asterisk":""}},[r("el-form-item",{attrs:{label:"用户名称",prop:"name"}},[r("el-col",{attrs:{span:11}},[r("el-input",{attrs:{disabled:""},model:{value:e.temp.name,callback:function(t){e.$set(e.temp,"name",t)},expression:"temp.name"}})],1)],1),e._v(" "),r("el-form-item",{attrs:{label:"帐号状态",prop:"enableStauts"}},[r("el-switch",{attrs:{"active-value":1,"inactive-value":0},model:{value:e.temp.enableStatus,callback:function(t){e.$set(e.temp,"enableStatus",t)},expression:"temp.enableStatus"}})],1)],1),e._v(" "),r("span",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[r("el-button",{on:{click:function(t){e.showEdit=!1}}},[e._v("取 消")]),e._v(" "),r("el-button",{attrs:{type:"primary"},on:{click:function(t){return e.confirmEdit()}}},[e._v("保 存")])],1)],1),e._v(" "),r("el-dialog",{attrs:{title:"提示",visible:e.showDeleteDialog,width:"400px"},on:{"update:visible":function(t){e.showDeleteDialog=t}}},[r("span",[e._v("确认删除内容?此操作不可撤销,请谨慎操作")]),e._v(" "),r("span",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[r("el-button",{on:{click:function(t){e.showDeleteDialog=!1}}},[e._v("取 消")]),e._v(" "),r("el-button",{attrs:{type:"danger"},on:{click:e.confirmDelete}},[e._v("删 除")])],1)])],1)},a=[],s=(r("7f7f"),r("96cf"),r("1da1")),i={data:function(){return{userList:[],dialogTitle:"",showAdd:!1,showEdit:!1,temp:{userId:null,name:null,gender:null,priority:null},fileList:[],isType:0,isExit:!1,param:"",value:"一级列表",rules:{name:[{required:!0,message:"帐号名称不能为空",trigger:"blur"}],gender:[{required:!0,message:"性别不能为空",trigger:"blur"}],profileImg:[{required:!0,message:"用户头像不能为空",trigger:"change"}],email:[{required:!0,type:"email",message:"邮箱不能为空",trigger:"change"}],priority:[{required:!0,message:"用户类型不能为空",trigger:"blur"}],createTime:[{required:!0,message:"创建时间不能为空",trigger:"blur"}],lastEditTime:[{required:!0,message:"修改时间不能为空",trigger:"blur"}]},showDeleteDialog:!1}},created:function(){this.getUserList()},methods:{getUserList:function(){var e=Object(s["a"])(regeneratorRuntime.mark((function e(){var t=this;return regeneratorRuntime.wrap((function(e){while(1)switch(e.prev=e.next){case 0:this.userData=this.req({url:"superadmin/listpersonInfos?enableStatus=ALL&page=1&rows=100",method:"POST"}).then((function(e){t.userList=e.rows,console.log(t.userList)}),(function(e){console.log("err: ",e),t.$message.success(e)}));case 1:case"end":return e.stop()}}),e,this)})));function t(){return e.apply(this,arguments)}return t}(),getDate:function(e){var t=new Date(e);return t.getFullYear()+"-"+(t.getMonth()+1)+"-"+t.getDate()+" "+t.getHours()+":"+t.getMinutes()+":"+t.getSeconds()},selectChange:function(e){this.temp.parent.userId=e,console.log(this.temp.parent),console.log("e",e)},handleAdd:function(){this.dialogTitle="添加内容",this.showAdd=!0},handleEdit:function(e){var t=this;this.dialogTitle="编辑内容",this.showEdit=!0,this.$nextTick((function(){t.temp.userId=e.userId,t.temp.name=e.name,t.temp.enableStatus=e.enableStatus}))},handleDelete:function(e){this.showDeleteDialog=!0,this.temp.userId=e.userId},handleChange:function(e){e&&(this.isExit=!0,console.log("change"),this.$refs.uploadElement.clearValidate())},beforeUpload:function(e){return this.param=new FormData,0==this.isType?(this.param.append("userManagementAdd_userImg",e),console.log("0")):(this.param.append("userManagementEdit_userImg",e),console.log("1")),!1},confirmAdd:function(){var e=Object(s["a"])(regeneratorRuntime.mark((function e(){var t=this;return regeneratorRuntime.wrap((function(e){while(1)switch(e.prev=e.next){case 0:this.isExit?(console.log("image",this.isExit),this.rules.profileImg[0].validator=function(e,r,n){n(),t.$refs.upload.submit()}):this.rules.profileImg[0].validator=function(e,t,r){r(e,t)},console.log("temp",JSON.stringify(this.temp)),this.$refs.form.validate(function(){var e=Object(s["a"])(regeneratorRuntime.mark((function e(r){return regeneratorRuntime.wrap((function(e){while(1)switch(e.prev=e.next){case 0:r&&(t.param.append("userStr",JSON.stringify(t.temp)),t.req({url:"superadmin/adduser",data:t.param,method:"POST"}).then((function(e){t.getUserList(),t.$message.success("添加成功")}),(function(e){console.log("err: ",e)})),t.showAdd=!1,t.$refs.form.resetFields());case 1:case"end":return e.stop()}}),e)})));return function(t){return e.apply(this,arguments)}}());case 3:case"end":return e.stop()}}),e,this)})));function t(){return e.apply(this,arguments)}return t}(),confirmEdit:function(){var e=Object(s["a"])(regeneratorRuntime.mark((function e(){var t=this;return regeneratorRuntime.wrap((function(e){while(1)switch(e.prev=e.next){case 0:this.$refs.form.validate(function(){var e=Object(s["a"])(regeneratorRuntime.mark((function e(r){return regeneratorRuntime.wrap((function(e){while(1)switch(e.prev=e.next){case 0:r&&(t.param=new FormData,t.param.append("userId",t.temp.userId),t.param.append("enableStatus",t.temp.enableStatus),t.req({url:"superadmin/modifypersonInfo",data:t.param,method:"POST"}).then((function(e){t.getUserList(),t.$message.success("修改成功")}),(function(e){console.log("err: ",e)})),t.showEdit=!1,t.$refs.form.resetFields());case 1:case"end":return e.stop()}}),e)})));return function(t){return e.apply(this,arguments)}}());case 1:case"end":return e.stop()}}),e,this)})));function t(){return e.apply(this,arguments)}return t}(),confirmDelete:function(){var e=Object(s["a"])(regeneratorRuntime.mark((function e(){var t=this;return regeneratorRuntime.wrap((function(e){while(1)switch(e.prev=e.next){case 0:this.req({url:"superadmin/removeuser?userId="+this.temp.userId,data:this.param,method:"POST"}).then((function(e){t.showDeleteDialog=!1,t.getUserList(),t.$message.success("删除成功")}),(function(e){console.log("err: ",e)}));case 1:case"end":return e.stop()}}),e,this)})));function t(){return e.apply(this,arguments)}return t}(),handleRemove:function(e,t){console.log(e,t),this.isExit=!1,console.log("remove",this.isExit)},resetForm:function(){this.$refs.form.resetFields(),this.isType=0}}},o=i,l=(r("5034"),r("c90f"),r("2877")),u=Object(l["a"])(o,n,a,!1,null,"0d9df248",null);t["default"]=u.exports}}]);