/**
 * 管理初始化
 */
var ScalperBankAccount = {
    id: "ScalperBankAccountTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
ScalperBankAccount.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '主键', field: 'id', visible: false, align: 'center', valign: 'middle'},
            {title: '金主id', field: 'scalperId', visible: true, align: 'center', valign: 'middle'},
            {title: '姓名', field: 'name', visible: true, align: 'center', valign: 'middle'},
            {title: '卡号', field: 'cardNo', visible: true, align: 'center', valign: 'middle'},
            {title: '银行名称', field: 'bank', visible: true, align: 'center', valign: 'middle'},
            {title: '银行编号', field: 'bankCode', visible: true, align: 'center', valign: 'middle'},
            {title: '发卡行地址', field: 'cardAddr', visible: true, align: 'center', valign: 'middle'},
            {title: '激活状态', field: 'status', visible: true, align: 'center', valign: 'middle'},
            {title: '', field: 'crtTime', visible: false, align: 'center', valign: 'middle'},
            {title: '', field: 'uptTime', visible: false, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
ScalperBankAccount.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        ScalperBankAccount.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加
 */
ScalperBankAccount.openAddScalperBankAccount = function () {
    var index = layer.open({
        type: 2,
        title: '添加',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/scalperBankAccount/scalperBankAccount_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看详情
 */
ScalperBankAccount.openScalperBankAccountDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/scalperBankAccount/scalperBankAccount_update/' + ScalperBankAccount.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除
 */
ScalperBankAccount.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/scalperBankAccount/delete", function (data) {
            Feng.success("删除成功!");
            ScalperBankAccount.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("scalperBankAccountId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询列表
 */
ScalperBankAccount.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    ScalperBankAccount.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = ScalperBankAccount.initColumn();
    var table = new BSTable(ScalperBankAccount.id, "/scalperBankAccount/list", defaultColunms);
    table.setPaginationType("client");
    ScalperBankAccount.table = table.init();
});
