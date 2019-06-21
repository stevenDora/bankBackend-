/**
 * 管理初始化
 */
var Merchant = {
    id: "MerchantTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Merchant.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '', field: 'id', visible: false, align: 'center', valign: 'middle'},
            {title: '商户名称', field: 'companyName', visible: true, align: 'center', valign: 'middle'},
            {title: '商户编码', field: 'companyCode', visible: true, align: 'center', valign: 'middle'},
            {title: '地址', field: 'address', visible: true, align: 'center', valign: 'middle'},
            {title: '负责人', field: 'charge', visible: true, align: 'center', valign: 'middle'},
            {title: '固定电话', field: 'fixPhone', visible: true, align: 'center', valign: 'middle'},
            {title: '冻结金额', field: 'freezingAmount', visible: true, align: 'center', valign: 'middle'},
            {title: '可用金额', field: 'availableAmount', visible: true, align: 'center', valign: 'middle'},
            {title: '总金额', field: 'totalAmount', visible: true, align: 'center', valign: 'middle'},
            {title: '邮箱', field: 'email', visible: true, align: 'center', valign: 'middle'},
            {title: '网站', field: 'website', visible: true, align: 'center', valign: 'middle'},
            {title: '是否开启', field: 'status', visible: true, align: 'center', valign: 'middle'},
            {title: '创建时间', field: 'crtTime', visible: false, align: 'center', valign: 'middle'},
            {title: '修改时间', field: 'uptTime', visible: false, align: 'center', valign: 'middle'},
            {title: '备注', field: 'note', visible: true, align: 'center', valign: 'middle'},
            {title: '', field: 'ifRate', visible: false, align: 'center', valign: 'middle'},
            {title: '私钥', field: 'privateKey', visible: true, align: 'center', valign: 'middle'},
            {title: '商户登录密码', field: 'password', visible: false, align: 'center', valign: 'middle'},
            {title: '结算密码', field: 'settlePwd', visible: false, align: 'center', valign: 'middle'},
            {title: '是否开启结算短信验证', field: 'isVerifyPhone', visible: true, align: 'center', valign: 'middle'},
            {title: '结算手机号', field: 'verifyPhone', visible: true, align: 'center', valign: 'middle'},
            {title: '是否开启对充开关，0否，1是', field: 'isOpenDc', visible: false, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
Merchant.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        Merchant.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加
 */
Merchant.openAddMerchant = function () {
    var index = layer.open({
        type: 2,
        title: '添加',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/merchant/merchant_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看详情
 */
Merchant.openMerchantDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/merchant/merchant_update/' + Merchant.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除
 */
Merchant.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/merchant/delete", function (data) {
            Feng.success("删除成功!");
            Merchant.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("merchantId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询列表
 */
Merchant.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    Merchant.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = Merchant.initColumn();
    var table = new BSTable(Merchant.id, "/merchant/list", defaultColunms);
    table.setPaginationType("client");
    Merchant.table = table.init();
});
