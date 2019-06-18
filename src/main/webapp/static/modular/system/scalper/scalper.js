/**
 * 管理初始化
 */
var Scalper = {
    id: "ScalperTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Scalper.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '', field: 'id', visible: false, align: 'center', valign: 'middle'},
            {title: '金主id', field: 'scalperId', visible: true, align: 'center', valign: 'middle'},
            {title: '根代理id', field: 'rootId', visible: false, align: 'center', valign: 'middle'},
            {title: '直接代理id', field: 'upperId', visible: false, align: 'center', valign: 'middle'},
            {title: '代理层级关系路径', field: 'agentPath', visible: false, align: 'center', valign: 'middle'},
            {title: '可用余额', field: 'avaliableBalance', visible: true, align: 'center', valign: 'middle'},
            {title: '冻结余额', field: 'freezeBalance', visible: true, align: 'center', valign: 'middle'},
            {title: '总额', field: 'totalBalance', visible: true, align: 'center', valign: 'middle'},
            {title: '当日累计收款', field: 'collectBalanceSumDay', visible: true, align: 'center', valign: 'middle'},
            {title: '当日收款上限', field: 'collectBalanceMaxDay', visible: true, align: 'center', valign: 'middle'},
            {title: '最后一次派单时间', field: 'lastAssignTaskTime', visible: false, align: 'center', valign: 'middle'},
            {title: '最后一次到账时间', field: 'lastAccountTime', visible: false, align: 'center', valign: 'middle'},
            {title: '最后一次充值时间', field: 'lastChargeTime', visible: false, align: 'center', valign: 'middle'},
            {title: '最后一次信息流水推送时间', field: 'lastFlowPushTime', visible: false, align: 'center', valign: 'middle'},
            {title: '支付宝开关', field: 'alipaySwitch', visible: true, align: 'center', valign: 'middle'},
            {title: '微信开关', field: 'wxSwitch', visible: true, align: 'center', valign: 'middle'},
            {title: '银行卡开关', field: 'bankSwitch', visible: true, align: 'center', valign: 'middle'},
            {title: '运营总控', field: 'blockStatus', visible: true, align: 'center', valign: 'middle'},
            {title: '支付宝费率', field: 'alipayRate', visible: true, align: 'center', valign: 'middle'},
            {title: '微信费率', field: 'wxRate', visible: true, align: 'center', valign: 'middle'},
            {title: '银行卡费率', field: 'bankRate', visible: true, align: 'center', valign: 'middle'},
            {title: '', field: 'crtTime', visible: false, align: 'center', valign: 'middle'},
            {title: '', field: 'uptTime', visible: false, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
Scalper.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        Scalper.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加
 */
Scalper.openAddScalper = function () {
    var index = layer.open({
        type: 2,
        title: '添加',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/scalper/scalper_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看详情
 */
Scalper.openScalperDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/scalper/scalper_update/' + Scalper.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除
 */
Scalper.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/scalper/delete", function (data) {
            Feng.success("删除成功!");
            Scalper.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("scalperId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询列表
 */
Scalper.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    Scalper.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = Scalper.initColumn();
    var table = new BSTable(Scalper.id, "/scalper/list", defaultColunms);
    table.setPaginationType("client");
    Scalper.table = table.init();
});
