/**
 * 新流水显示管理初始化
 */
var FlowData = {
    id: "FlowDataTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
FlowData.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '', field: 'id', visible: true, align: 'center', valign: 'middle'},
            {title: '金主ID', field: 'scalperId', visible: true, align: 'center', valign: 'middle'},
            {title: '渠道', field: 'channel', visible: true, align: 'center', valign: 'middle'},
            {title: '金额', field: 'amount', visible: true, align: 'center', valign: 'middle'},
            {title: '附言', field: 'remark', visible: true, align: 'center', valign: 'middle'},
            {title: '源ip', field: 'fromIp', visible: true, align: 'center', valign: 'middle'},
            {title: '到账时间', field: 'receiveTime', visible: true, align: 'center', valign: 'middle'},
            {title: '详细信息', field: 'data', visible: true, align: 'center', valign: 'middle'},
            {title: '匹配上的订单号', field: 'orderNo', visible: true, align: 'center', valign: 'middle'},
            {title: '是否匹配', field: 'isMatched', visible: true, align: 'center', valign: 'middle'},
            {title: '接收时间', field: 'crtTime', visible: true, align: 'center', valign: 'middle'},
            {title: '更新时间', field: 'uptTime', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
FlowData.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        FlowData.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加新流水显示
 */
FlowData.openAddFlowData = function () {
    var index = layer.open({
        type: 2,
        title: '添加新流水显示',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/flowData/flowData_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看新流水显示详情
 */
FlowData.openFlowDataDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '新流水显示详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/flowData/flowData_update/' + FlowData.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除新流水显示
 */
FlowData.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/flowData/delete", function (data) {
            Feng.success("删除成功!");
            FlowData.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("flowDataId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询新流水显示列表
 */
FlowData.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    FlowData.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = FlowData.initColumn();
    var table = new BSTable(FlowData.id, "/flowData/list", defaultColunms);
    table.setPaginationType("client");
    FlowData.table = table.init();
});
