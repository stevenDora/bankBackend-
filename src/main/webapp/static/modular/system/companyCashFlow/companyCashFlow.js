/**
 * 管理初始化
 */
var CompanyCashFlow = {
    id: "CompanyCashFlowTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
CompanyCashFlow.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '', field: 'id', visible: true, align: 'center', valign: 'middle'},
            {title: '', field: 'companyId', visible: true, align: 'center', valign: 'middle'},
            {title: '玩家充值金额', field: 'collectionAmount', visible: true, align: 'center', valign: 'middle'},
            {title: '玩家提现金额', field: 'paymentAmount', visible: true, align: 'center', valign: 'middle'},
            {title: '玩家充值商户手续费', field: 'collectionBfs', visible: true, align: 'center', valign: 'middle'},
            {title: '玩家提现商户手续费', field: 'paymentBfs', visible: true, align: 'center', valign: 'middle'},
            {title: '新可以用余额', field: 'updatedAb', visible: true, align: 'center', valign: 'middle'},
            {title: '旧可用余额', field: 'oldAb', visible: true, align: 'center', valign: 'middle'},
            {title: '新冻结金额', field: 'updatedFa', visible: true, align: 'center', valign: 'middle'},
            {title: '旧冻结金额', field: 'oldFa', visible: true, align: 'center', valign: 'middle'},
            {title: '', field: 'transactionDate', visible: true, align: 'center', valign: 'middle'},
            {title: '', field: 'orderNo', visible: true, align: 'center', valign: 'middle'},
            {title: '', field: 'crtTime', visible: true, align: 'center', valign: 'middle'},
            {title: '', field: 'uptTime', visible: true, align: 'center', valign: 'middle'},
            {title: '订单状态', field: 'orderStatus', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
CompanyCashFlow.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        CompanyCashFlow.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加
 */
CompanyCashFlow.openAddCompanyCashFlow = function () {
    var index = layer.open({
        type: 2,
        title: '添加',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/companyCashFlow/companyCashFlow_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看详情
 */
CompanyCashFlow.openCompanyCashFlowDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/companyCashFlow/companyCashFlow_update/' + CompanyCashFlow.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除
 */
CompanyCashFlow.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/companyCashFlow/delete", function (data) {
            Feng.success("删除成功!");
            CompanyCashFlow.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("companyCashFlowId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询列表
 */
CompanyCashFlow.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    CompanyCashFlow.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = CompanyCashFlow.initColumn();
    var table = new BSTable(CompanyCashFlow.id, "/companyCashFlow/list", defaultColunms);
    table.setPaginationType("client");
    CompanyCashFlow.table = table.init();
});
