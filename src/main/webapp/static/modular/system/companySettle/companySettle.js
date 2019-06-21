/**
 * 商户结算管理初始化
 */
var CompanySettle = {
    id: "CompanySettleTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
CompanySettle.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '', field: 'id', visible: true, align: 'center', valign: 'middle'},
            {title: '', field: 'orderNo', visible: true, align: 'center', valign: 'middle'},
            {title: '', field: 'mowOrderNo', visible: true, align: 'center', valign: 'middle'},
            {title: '', field: 'applyAmount', visible: true, align: 'center', valign: 'middle'},
            {title: '', field: 'trueAmount', visible: true, align: 'center', valign: 'middle'},
            {title: '', field: 'serviceFee', visible: true, align: 'center', valign: 'middle'},
            {title: '', field: 'bankCode', visible: true, align: 'center', valign: 'middle'},
            {title: '', field: 'bankName', visible: true, align: 'center', valign: 'middle'},
            {title: '', field: 'subBank', visible: true, align: 'center', valign: 'middle'},
            {title: '', field: 'bankCardNo', visible: true, align: 'center', valign: 'middle'},
            {title: '', field: 'accountName', visible: true, align: 'center', valign: 'middle'},
            {title: '1 processing 2 success 3 failed 4 verifying', field: 'status', visible: true, align: 'center', valign: 'middle'},
            {title: '0 自行 1 三方', field: 'processWay', visible: true, align: 'center', valign: 'middle'},
            {title: '', field: 'companyId', visible: true, align: 'center', valign: 'middle'},
            {title: '', field: 'remark', visible: true, align: 'center', valign: 'middle'},
            {title: '', field: 'crtTime', visible: true, align: 'center', valign: 'middle'},
            {title: '', field: 'uptTime', visible: true, align: 'center', valign: 'middle'},
            {title: '', field: 'transTime', visible: true, align: 'center', valign: 'middle'},
            {title: 'MA编号', field: 'maNo', visible: true, align: 'center', valign: 'middle'},
            {title: '商户结算附言', field: 'note', visible: true, align: 'center', valign: 'middle'},
            {title: '商户结算运营操作员', field: 'operator', visible: true, align: 'center', valign: 'middle'},
            {title: '商户结算订单超时时间', field: 'overTime', visible: true, align: 'center', valign: 'middle'},
            {title: '抢单金主ID', field: 'scalperId', visible: true, align: 'center', valign: 'middle'},
            {title: '对充金主交易流水单截图', field: 'scalperImageUrl', visible: true, align: 'center', valign: 'middle'},
            {title: '订单金主手机号', field: 'phone', visible: true, align: 'center', valign: 'middle'},
            {title: '订单结算币种', field: 'currency', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
CompanySettle.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        CompanySettle.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加商户结算
 */
CompanySettle.openAddCompanySettle = function () {
    var index = layer.open({
        type: 2,
        title: '添加商户结算',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/companySettle/companySettle_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看商户结算详情
 */
CompanySettle.openCompanySettleDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '商户结算详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/companySettle/companySettle_update/' + CompanySettle.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除商户结算
 */
CompanySettle.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/companySettle/delete", function (data) {
            Feng.success("删除成功!");
            CompanySettle.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("companySettleId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询商户结算列表
 */
CompanySettle.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    CompanySettle.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = CompanySettle.initColumn();
    var table = new BSTable(CompanySettle.id, "/companySettle/list", defaultColunms);
    table.setPaginationType("client");
    CompanySettle.table = table.init();
});
