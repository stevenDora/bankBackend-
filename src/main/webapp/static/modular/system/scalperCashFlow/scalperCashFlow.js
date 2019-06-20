/**
 * 管理初始化
 */
var ScalperCashFlow = {
    id: "ScalperCashFlowTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
ScalperCashFlow.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '', field: 'id', visible: true, align: 'center', valign: 'middle'},
            {title: '', field: 'scalperId', visible: true, align: 'center', valign: 'middle'},
            {title: '代收金额', field: 'collectionAmount', visible: true, align: 'center', valign: 'middle'},
            {title: '代付金额', field: 'paymentAmount', visible: true, align: 'center', valign: 'middle'},
            {title: '代收收益', field: 'collectionEarning', visible: true, align: 'center', valign: 'middle'},
            {title: '代付收益', field: 'paymentEarning', visible: true, align: 'center', valign: 'middle'},
            {title: '新可用余额', field: 'updatedAb', visible: true, align: 'center', valign: 'middle'},
            {title: '旧可用余额', field: 'oldAb', visible: true, align: 'center', valign: 'middle'},
            {title: '新冻结金额', field: 'updatedFa', visible: true, align: 'center', valign: 'middle'},
            {title: '旧冻结金额', field: 'oldFa', visible: true, align: 'center', valign: 'middle'},
            {title: '', field: 'transactionTime', visible: true, align: 'center', valign: 'middle'},
            {title: '', field: 'orderNo', visible: true, align: 'center', valign: 'middle'},
            {title: '', field: 'orderStatus', visible: true, align: 'center', valign: 'middle'},
            {title: '', field: 'crtTime', visible: true, align: 'center', valign: 'middle'},
            {title: '', field: 'uptTime', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
ScalperCashFlow.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        ScalperCashFlow.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加
 */
ScalperCashFlow.openAddScalperCashFlow = function () {
    var index = layer.open({
        type: 2,
        title: '添加',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/scalperCashFlow/scalperCashFlow_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看详情
 */
ScalperCashFlow.openScalperCashFlowDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/scalperCashFlow/scalperCashFlow_update/' + ScalperCashFlow.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除
 */
ScalperCashFlow.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/scalperCashFlow/delete", function (data) {
            Feng.success("删除成功!");
            ScalperCashFlow.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("scalperCashFlowId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询列表
 */
ScalperCashFlow.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    ScalperCashFlow.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = ScalperCashFlow.initColumn();
    var table = new BSTable(ScalperCashFlow.id, "/scalperCashFlow/list", defaultColunms);
    table.setPaginationType("client");
    ScalperCashFlow.table = table.init();
});
