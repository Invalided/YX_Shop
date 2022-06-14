(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-093dfcc2"],{"18ef":function(e,t,a){"use strict";a("77cb")},"24e5":function(e,t,a){"use strict";a.r(t);var o=function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("div",{staticClass:"container"},[a("div",{staticClass:"title"},[e._v("商铺内容列表")]),e._v(" "),a("div",{staticClass:"add-button"}),e._v(" "),a("div",{staticClass:"table-container"},[a("el-table",{attrs:{data:e.shopList,border:""}},[a("el-table-column",{attrs:{label:"序号",width:"60",type:"index"}}),e._v(" "),e._e(),e._v(" "),a("el-table-column",{attrs:{label:"商铺名称",prop:"shopName"}}),e._v(" "),a("el-table-column",{attrs:{label:"商铺描述",prop:"shopDesc"}}),e._v(" "),a("el-table-column",{attrs:{label:"商铺地址",prop:"shopAddr"}}),e._v(" "),a("el-table-column",{attrs:{label:"类别Id",prop:"categoryId"},scopedSlots:e._u([{key:"default",fn:function(t){return[e._v("\n          "+e._s(t.row.shopCategory.shopCategoryId)+"\n        ")]}}])}),e._v(" "),a("el-table-column",{attrs:{label:"电话",prop:"phone"}}),e._v(" "),a("el-table-column",{attrs:{label:"优先级",prop:"priority",width:"80"}}),e._v(" "),a("el-table-column",{attrs:{label:"建议",prop:"advice"}}),e._v(" "),a("el-table-column",{attrs:{label:"创建时间",prop:"createTime",width:"120"},scopedSlots:e._u([{key:"default",fn:function(t){return[e._v("\n          "+e._s(e.getDate(t.row.createTime))+"\n        ")]}}])}),e._v(" "),a("el-table-column",{attrs:{label:"修改时间",prop:"lastEditTime",width:"120"},scopedSlots:e._u([{key:"default",fn:function(t){return[e._v("\n          "+e._s(e.getDate(t.row.lastEditTime))+"\n        ")]}}])}),e._v(" "),a("el-table-column",{attrs:{label:"商铺状态",prop:"enableStatus",width:"100"},scopedSlots:e._u([{key:"default",fn:function(t){return[1===t.row.enableStatus?a("el-tag",{attrs:{type:"success"}},[e._v("启用")]):a("el-tag",{attrs:{type:"info"}},[e._v("禁用")])]}}])}),e._v(" "),a("el-table-column",{attrs:{label:"操作",fixed:"right",width:"180"},scopedSlots:e._u([{key:"default",fn:function(t){return[a("el-button",{on:{click:function(a){return e.handleEdit(t.row)}}},[e._v("编辑")]),e._v(" "),a("el-button",{attrs:{type:"danger",disabled:""},on:{click:function(a){return e.handleDelete(t.row)}}},[e._v("\n            删除\n          ")])]}}])})],1)],1),e._v(" "),a("el-dialog",{attrs:{title:e.dialogTitle,width:"800px",visible:e.showDialog,"close-on-click-modal":!1,"close-on-press-escape":!1},on:{"update:visible":function(t){e.showDialog=t},close:e.resetForm}},[a("el-form",{ref:"form",attrs:{model:e.temp,"label-width":"90px",rules:e.rules,"hide-required-asterisk":""}},[a("el-form-item",{attrs:{label:"商铺名称",prop:"shopName"}},[a("el-col",{attrs:{span:11}},[a("el-input",{model:{value:e.temp.shopName,callback:function(t){e.$set(e.temp,"shopName",t)},expression:"temp.shopName"}})],1)],1),e._v(" "),a("el-form-item",{attrs:{label:"优先级",prop:"priority"}},[a("el-col",{attrs:{span:11}},[a("el-input",{attrs:{oninput:"value=value.replace(/[^\\d]/g,'')",placeholder:"请输入数字，最多5位",maxlength:"5"},model:{value:e.temp.priority,callback:function(t){e.$set(e.temp,"priority",t)},expression:"temp.priority"}})],1)],1),e._v(" "),a("el-form-item",{attrs:{label:"建议",prop:"advice"}},[a("el-col",{attrs:{span:11}},[a("el-input",{attrs:{type:"textarea",rows:2,placeholder:"请输入内容"},model:{value:e.temp.advice,callback:function(t){e.$set(e.temp,"advice",t)},expression:"temp.advice"}})],1)],1),e._v(" "),a("el-form-item",{attrs:{label:"商铺状态",prop:"enableStauts"}},[a("el-switch",{attrs:{"active-value":1,"inactive-value":0},model:{value:e.temp.enableStatus,callback:function(t){e.$set(e.temp,"enableStatus",t)},expression:"temp.enableStatus"}})],1)],1),e._v(" "),a("span",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[a("el-button",{on:{click:function(t){e.showDialog=!1}}},[e._v("取 消")]),e._v(" "),a("el-button",{attrs:{type:"primary"},on:{click:function(t){"添加内容"===e.dialogTitle?e.confirmAdd():e.confirmEdit()}}},[e._v("保 存")])],1)],1),e._v(" "),a("el-dialog",{attrs:{title:"提示",visible:e.showDeleteDialog,width:"400px"},on:{"update:visible":function(t){e.showDeleteDialog=t}}},[a("span",[e._v("确认删除内容?此操作不可撤销,请谨慎操作")]),e._v(" "),a("span",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[a("el-button",{on:{click:function(t){e.showDeleteDialog=!1}}},[e._v("取 消")]),e._v(" "),a("el-button",{attrs:{type:"danger"},on:{click:e.confirmDelete}},[e._v("删 除")])],1)])],1)},s=[],r=(a("96cf"),a("1da1")),n={data:function(){return{shopList:[],dialogTitle:"",showDialog:!1,temp:{shopCategory:{shopCategoryId:""},shopId:null,shopName:null,priority:null,enableStatus:null,advice:null},fileList:[],isType:0,isExit:!1,param:"",options:[{value:"1",label:"二手市场"},{value:"2",label:"美容美发"},{value:"3",label:"美食饮品"},{value:"4",label:"休闲娱乐"},{value:"5",label:"培训教育"},{value:"6",label:"租赁市场"},{value:null,label:"一级列表"}],value:"一级列表",rules:{shopName:[{required:!0,message:"商铺名称不能为空",trigger:"blur"}],shopDesc:[{required:!0,message:"商铺描述不能为空",trigger:"blur"}],shopImage:[{required:!0,message:"商铺图片不能为空",trigger:"change"}],priority:[{required:!0,message:"优先级不能为空",trigger:"blur"}],advice:[{required:!0,message:"建议不能为空",trigger:"blur"}]},showDeleteDialog:!1}},created:function(){this.getShopList()},methods:{getShopList:function(){var e=Object(r["a"])(regeneratorRuntime.mark((function e(){var t=this;return regeneratorRuntime.wrap((function(e){while(1)switch(e.prev=e.next){case 0:this.shopData=this.req({url:"superadmin/listshops?enableStatus=ALL&page=1&rows=100",method:"POST"}).then((function(e){t.shopList=e.rows,console.log(t.shopList)}),(function(e){console.log("err: ",e),t.$message.success(e)}));case 1:case"end":return e.stop()}}),e,this)})));function t(){return e.apply(this,arguments)}return t}(),getDate:function(e){var t=new Date(e);return t.getFullYear()+"-"+(t.getMonth()+1)+"-"+t.getDate()+" "+t.getHours()+":"+t.getMinutes()+":"+t.getSeconds()},selectChange:function(e){this.temp.parent.shopId=e,console.log(this.temp.parent),console.log("e",e)},handleAdd:function(){this.dialogTitle="添加内容",this.showDialog=!0},handleEdit:function(e){var t=this;this.dialogTitle="编辑内容",this.showDialog=!0,this.$nextTick((function(){t.temp.shopCategory.shopCategoryId=e.shopCategory.shopCategoryId,t.temp.shopId=e.shopId,t.temp.shopName=e.shopName,t.temp.priority=e.priority,t.temp.advice=e.advice,t.temp.enableStatus=e.enableStatus}))},handleDelete:function(e){this.showDeleteDialog=!0,this.temp.shopId=e.shopId},handleChange:function(e){e&&(this.isExit=!0,console.log("change"),this.$refs.uploadElement.clearValidate())},beforeUpload:function(e){return this.param=new FormData,0==this.isType?(this.param.append("shopManagementAdd_shopImg",e),console.log("0")):(this.param.append("shopManagementEdit_shopImg",e),console.log("1")),!1},confirmAdd:function(){var e=Object(r["a"])(regeneratorRuntime.mark((function e(){var t=this;return regeneratorRuntime.wrap((function(e){while(1)switch(e.prev=e.next){case 0:this.isExit?(console.log("image",this.isExit),this.rules.shopImage[0].validator=function(e,a,o){o(),t.$refs.upload.submit()}):this.rules.shopImage[0].validator=function(e,t,a){a(e,t)},console.log("temp",JSON.stringify(this.temp)),this.$refs.form.validate(function(){var e=Object(r["a"])(regeneratorRuntime.mark((function e(a){return regeneratorRuntime.wrap((function(e){while(1)switch(e.prev=e.next){case 0:a&&(t.param.append("shopStr",JSON.stringify(t.temp)),t.req({url:"superadmin/addshop",data:t.param,method:"POST"}).then((function(e){t.getShopList(),t.$message.success("添加成功")}),(function(e){console.log("err: ",e)})),t.showDialog=!1,t.$refs.form.resetFields());case 1:case"end":return e.stop()}}),e)})));return function(t){return e.apply(this,arguments)}}());case 3:case"end":return e.stop()}}),e,this)})));function t(){return e.apply(this,arguments)}return t}(),confirmEdit:function(){var e=Object(r["a"])(regeneratorRuntime.mark((function e(){var t=this;return regeneratorRuntime.wrap((function(e){while(1)switch(e.prev=e.next){case 0:this.rules.shopImage[0].validator=function(e,t,a){a()},console.log(this.isExit),this.$refs.form.validate(function(){var e=Object(r["a"])(regeneratorRuntime.mark((function e(a){var o;return regeneratorRuntime.wrap((function(e){while(1)switch(e.prev=e.next){case 0:o=new FormData,a&&(t.isExit?(t.$refs.upload.submit(),t.param.append("shopStr",JSON.stringify(t.temp))):(o.append("shopManagementEdit_shopImg",""),o.append("shopStr",JSON.stringify(t.temp)),t.param=o),t.req({url:"superadmin/modifyshop",data:t.param,method:"POST"}).then((function(e){t.getShopList(),t.$message.success("修改成功")}),(function(e){console.log("err: ",e)})),t.showDialog=!1,t.$refs.form.resetFields());case 2:case"end":return e.stop()}}),e)})));return function(t){return e.apply(this,arguments)}}());case 3:case"end":return e.stop()}}),e,this)})));function t(){return e.apply(this,arguments)}return t}(),confirmDelete:function(){var e=Object(r["a"])(regeneratorRuntime.mark((function e(){var t=this;return regeneratorRuntime.wrap((function(e){while(1)switch(e.prev=e.next){case 0:this.req({url:"superadmin/removeshop?shopId="+this.temp.shopId,data:this.param,method:"POST"}).then((function(e){t.showDeleteDialog=!1,t.getShopList(),t.$message.success("删除成功")}),(function(e){console.log("err: ",e)}));case 1:case"end":return e.stop()}}),e,this)})));function t(){return e.apply(this,arguments)}return t}(),handleRemove:function(e,t){console.log(e,t),this.isExit=!1,console.log("remove",this.isExit)},resetForm:function(){this.$refs.form.resetFields(),this.isType=0}}},i=n,l=(a("18ef"),a("7fd9"),a("2877")),p=Object(l["a"])(i,o,s,!1,null,"10239ec7",null);t["default"]=p.exports},"77cb":function(e,t,a){},"7fd9":function(e,t,a){"use strict";a("e2e2")},e2e2:function(e,t,a){}}]);