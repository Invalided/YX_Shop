let api = [];
api.push({
    alias: 'api',
    order: '1',
    desc: 'AdminAuthController 管理员登录',
    link: 'adminauthcontroller_管理员登录',
    list: []
})
api[0].list.push({
    order: '1',
    desc: '管理员登录接口',
});
api[0].list.push({
    order: '2',
    desc: '管理员修改密码',
});
api[0].list.push({
    order: '3',
    desc: '管理员退出登录',
});
api.push({
    alias: 'LocalAuthController',
    order: '2',
    desc: 'LocalAuthController 用户/商家登录',
    link: 'localauthcontroller_用户/商家登录',
    list: []
})
api[1].list.push({
    order: '1',
    desc: '用户注册信息接口',
});
api[1].list.push({
    order: '2',
    desc: '用户登录接口',
});
api[1].list.push({
    order: '3',
    desc: '用户修改密码',
});
api[1].list.push({
    order: '4',
    desc: '用户退出登录',
});
api.push({
    alias: 'HelloController',
    order: '3',
    desc: 'HelloController 项目使用基本示例',
    link: 'hellocontroller_项目使用基本示例',
    list: []
})
api[2].list.push({
    order: '1',
    desc: 'GetMapping 请求',
});
api[2].list.push({
    order: '2',
    desc: '测试统一异常处理',
});
api[2].list.push({
    order: '3',
    desc: 'PostMapping 请求',
});
api[2].list.push({
    order: '4',
    desc: 'PostMapping 请求',
});
api[2].list.push({
    order: '5',
    desc: 'DeleteMapping请求',
});
api.push({
    alias: 'MainPageController',
    order: '4',
    desc: 'MainPageController 商铺前台主页面',
    link: 'mainpagecontroller_商铺前台主页面',
    list: []
})
api[3].list.push({
    order: '1',
    desc: '获取首页信息',
});
api.push({
    alias: 'ProductDetailController',
    order: '5',
    desc: 'ProductDetailController 商品详情页面',
    link: 'productdetailcontroller_商品详情页面',
    list: []
})
api[4].list.push({
    order: '1',
    desc: '根据Id获取商品的详细信息',
});
api.push({
    alias: 'ShopDetailController',
    order: '6',
    desc: 'ShopDetailController 商铺详情',
    link: 'shopdetailcontroller_商铺详情',
    list: []
})
api[5].list.push({
    order: '1',
    desc: '根据商铺Id获取商铺信息',
});
api[5].list.push({
    order: '2',
    desc: '根据商铺信息条件查询',
});
api.push({
    alias: 'ShopListController',
    order: '7',
    desc: 'ShopListController 商铺列表页面',
    link: 'shoplistcontroller_商铺列表页面',
    list: []
})
api[6].list.push({
    order: '1',
    desc: '获取列表页的相关信息',
});
api[6].list.push({
    order: '2',
    desc: '获取指定条件下的商铺',
});
api.push({
    alias: 'UserConsumptionController',
    order: '8',
    desc: 'UserConsumptionController 用户消费记录',
    link: 'userconsumptioncontroller_用户消费记录',
    list: []
})
api[7].list.push({
    order: '1',
    desc: '用户购物订单创建',
});
api[7].list.push({
    order: '2',
    desc: '用户订单列表接口',
});
api.push({
    alias: 'RedisController',
    order: '9',
    desc: 'RedisController Redis简单使用示例',
    link: 'rediscontroller_redis简单使用示例',
    list: []
})
api[8].list.push({
    order: '1',
    desc: '根据key获取value',
});
api[8].list.push({
    order: '2',
    desc: '仅作演示使用，传入key、value值存入缓存',
});
api[8].list.push({
    order: '3',
    desc: '根据key删除缓存',
});
api.push({
    alias: 'AwardManageController',
    order: '10',
    desc: 'AwardManageController 奖品管理(Manager)',
    link: 'awardmanagecontroller_奖品管理(manager)',
    list: []
})
api[9].list.push({
    order: '1',
    desc: '获取当前的奖品列表',
});
api[9].list.push({
    order: '2',
    desc: '创建奖品信息',
});
api[9].list.push({
    order: '3',
    desc: '更新奖品信息',
});
api.push({
    alias: 'ConsumptionRecordController',
    order: '11',
    desc: 'ConsumptionRecordController 消费记录管理(Manager)',
    link: 'consumptionrecordcontroller_消费记录管理(manager)',
    list: []
})
api[10].list.push({
    order: '1',
    desc: '获取当前商铺下的消费信息',
});
api.push({
    alias: 'ProductCategoryManageController',
    order: '12',
    desc: 'ProductCategoryManageController 商家商品类别管理(Manager)',
    link: 'productcategorymanagecontroller_商家商品类别管理(manager)',
    list: []
})
api[11].list.push({
    order: '1',
    desc: '批量新增商品类别',
});
api[11].list.push({
    order: '2',
    desc: '商品类别删除',
});
api.push({
    alias: 'ProductManageController',
    order: '13',
    desc: 'ProductManageController 商品管理(Manager)',
    link: 'productmanagecontroller_商品管理(manager)',
    list: []
})
api[12].list.push({
    order: '1',
    desc: '根据商铺id获取当前已经上架的商品列表',
});
api[12].list.push({
    order: '2',
    desc: '获取指定商铺的商品类别信息',
});
api[12].list.push({
    order: '3',
    desc: '创建商品',
});
api[12].list.push({
    order: '4',
    desc: '修改商品信息',
});
api.push({
    alias: 'ShopInfoManageController',
    order: '14',
    desc: 'ShopInfoManageController 商铺信息管理(Manager)',
    link: 'shopinfomanagecontroller_商铺信息管理(manager)',
    list: []
})
api[13].list.push({
    order: '1',
    desc: '根据商铺id获取当前商铺信息',
});
api[13].list.push({
    order: '2',
    desc: '商铺信息修改',
});
api.push({
    alias: 'ShopListManageController',
    order: '15',
    desc: 'ShopListManageController 后台管理商铺列表(Manager)',
    link: 'shoplistmanagecontroller_后台管理商铺列表(manager)',
    list: []
})
api[14].list.push({
    order: '1',
    desc: '获取商家创建的商铺列表',
});
api[14].list.push({
    order: '2',
    desc: '创建商铺时先获取初始信息  获取二级分类,以及当前的区域信息',
});
api[14].list.push({
    order: '3',
    desc: '商铺创建',
});
api.push({
    alias: 'UserPointManageController',
    order: '16',
    desc: 'UserPointManageController 商家积分管理(Manager)',
    link: 'userpointmanagecontroller_商家积分管理(manager)',
    list: []
})
api[15].list.push({
    order: '1',
    desc: '',
});
api.push({
    alias: 'AreaController',
    order: '17',
    desc: 'AreaController  区域信息管理(Admin)',
    link: 'areacontroller__区域信息管理(admin)',
    list: []
})
api[16].list.push({
    order: '1',
    desc: '根据Id获取区域信息',
});
api[16].list.push({
    order: '2',
    desc: '获取所有的区域信息',
});
api[16].list.push({
    order: '3',
    desc: '新增区域信息',
});
api[16].list.push({
    order: '4',
    desc: '删除区域信息',
});
api[16].list.push({
    order: '5',
    desc: '更新区域信息',
});
api[16].list.push({
    order: '6',
    desc: '批量删除区域信息',
});
api.push({
    alias: 'HeadLineController',
    order: '18',
    desc: 'HeadLineController 头条信息管理(Admin)',
    link: 'headlinecontroller_头条信息管理(admin)',
    list: []
})
api[17].list.push({
    order: '1',
    desc: '获取所有的头条信息',
});
api[17].list.push({
    order: '2',
    desc: '获取指定的头条信息',
});
api[17].list.push({
    order: '3',
    desc: '新增头条信息,此处不能使用RequestBody注解，因为需要上传图片，contentType = multipart/form-data,',
});
api[17].list.push({
    order: '4',
    desc: '根据id删除头条信息',
});
api[17].list.push({
    order: '5',
    desc: '根据Id更新头条信息',
});
api[17].list.push({
    order: '6',
    desc: '批量删除头条信息',
});
api.push({
    alias: 'PersonInfoController',
    order: '19',
    desc: 'PersonInfoController 用户信息管理(Admin)',
    link: 'personinfocontroller_用户信息管理(admin)',
    list: []
})
api[18].list.push({
    order: '1',
    desc: '根据用户Id获取用户信息',
});
api[18].list.push({
    order: '2',
    desc: '分页查询返回用户数据',
});
api[18].list.push({
    order: '3',
    desc: '修改用户信息',
});
api.push({
    alias: 'ShopCategoryController',
    order: '20',
    desc: 'ShopCategoryController 类别信息管理(Admin)',
    link: 'shopcategorycontroller_类别信息管理(admin)',
    list: []
})
api[19].list.push({
    order: '1',
    desc: '获取shopCategory所有信息列表',
});
api[19].list.push({
    order: '2',
    desc: '不分页返回所有的一级列表',
});
api[19].list.push({
    order: '3',
    desc: '不分页返回所有的二级列表',
});
api[19].list.push({
    order: '4',
    desc: '',
});
api[19].list.push({
    order: '5',
    desc: '更新类别信息',
});
api[19].list.push({
    order: '6',
    desc: '根据id删除对应类别信息',
});
api.push({
    alias: 'ShopController',
    order: '21',
    desc: 'ShopController 商铺信息管理(Admin)',
    link: 'shopcontroller_商铺信息管理(admin)',
    list: []
})
api[20].list.push({
    order: '1',
    desc: '获取所有商铺信息',
});
api[20].list.push({
    order: '2',
    desc: '根据id获取指定店铺信息',
});
api[20].list.push({
    order: '3',
    desc: '更新商铺信息',
});
api.push({
    alias: 'UserController',
    order: '22',
    desc: 'UserController 用户模块简单示例',
    link: 'usercontroller_用户模块简单示例',
    list: []
})
api[21].list.push({
    order: '1',
    desc: '根据Id获取用户信息',
});
api[21].list.push({
    order: '2',
    desc: '新增用户信息',
});
api[21].list.push({
    order: '3',
    desc: '修改用户信息',
});
api[21].list.push({
    order: '4',
    desc: '根据id删除用户',
});
api[21].list.push({
    order: '5',
    desc: '分页查询用户',
});
api.push({
    alias: 'error',
    order: '23',
    desc: '错误码列表',
    link: 'error_code_list',
    list: []
})
document.onkeydown = keyDownSearch;
function keyDownSearch(e) {
    const theEvent = e;
    const code = theEvent.keyCode || theEvent.which || theEvent.charCode;
    if (code === 13) {
        const search = document.getElementById('search');
        const searchValue = search.value;
        let searchArr = [];
        for (let i = 0; i < api.length; i++) {
            let apiData = api[i];
            const desc = apiData.desc;
            if (desc.toLocaleLowerCase().indexOf(searchValue) > -1) {
                searchArr.push({
                    order: apiData.order,
                    desc: apiData.desc,
                    link: apiData.link,
                    alias: apiData.alias,
                    list: apiData.list
                });
            } else {
                let methodList = apiData.list || [];
                let methodListTemp = [];
                for (let j = 0; j < methodList.length; j++) {
                    const methodData = methodList[j];
                    const methodDesc = methodData.desc;
                    if (methodDesc.toLocaleLowerCase().indexOf(searchValue) > -1) {
                        methodListTemp.push(methodData);
                        break;
                    }
                }
                if (methodListTemp.length > 0) {
                    const data = {
                        order: apiData.order,
                        desc: apiData.desc,
                        alias: apiData.alias,
                        link: apiData.link,
                        list: methodListTemp
                    };
                    searchArr.push(data);
                }
            }
        }
        let html;
        if (searchValue === '') {
            const liClass = "";
            const display = "display: none";
            html = buildAccordion(api,liClass,display);
            document.getElementById('accordion').innerHTML = html;
        } else {
            const liClass = "open";
            const display = "display: block";
            html = buildAccordion(searchArr,liClass,display);
            document.getElementById('accordion').innerHTML = html;
        }
        const Accordion = function (el, multiple) {
            this.el = el || {};
            this.multiple = multiple || false;
            const links = this.el.find('.dd');
            links.on('click', {el: this.el, multiple: this.multiple}, this.dropdown);
        };
        Accordion.prototype.dropdown = function (e) {
            const $el = e.data.el;
            let $this = $(this), $next = $this.next();
            $next.slideToggle();
            $this.parent().toggleClass('open');
            if (!e.data.multiple) {
                $el.find('.submenu').not($next).slideUp("20").parent().removeClass('open');
            }
        };
        new Accordion($('#accordion'), false);
    }
}

function buildAccordion(apiData, liClass, display) {
    let html = "";
    if (apiData.length > 0) {
         for (let j = 0; j < apiData.length; j++) {
            html += '<li class="'+liClass+'">';
            html += '<a class="dd" href="' + apiData[j].alias + '.html#header">' + apiData[j].order + '.&nbsp;' + apiData[j].desc + '</a>';
            html += '<ul class="sectlevel2" style="'+display+'">';
            let doc = apiData[j].list;
            for (let m = 0; m < doc.length; m++) {
                html += '<li><a href="' + apiData[j].alias + '.html#_' + apiData[j].order + '_' + doc[m].order + '_' + doc[m].desc + '">' + apiData[j].order + '.' + doc[m].order + '.&nbsp;' + doc[m].desc + '</a> </li>';
            }
            html += '</ul>';
            html += '</li>';
        }
    }
    return html;
}