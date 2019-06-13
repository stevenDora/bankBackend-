/**
 * 管理初始化
 */
var Trade = {
    id: "TradeTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Trade.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '平台订单号', field: 'orderNo', visible: true, align: 'center', valign: 'middle'},
            {title: '商户编号', field: 'companyNo', visible: true, align: 'center', valign: 'middle'},
            {title: '商户订单号', field: 'companyOrderNo', visible: true, align: 'center', valign: 'middle'},
            {title: '渠道号', field: 'channel', visible: true, align: 'center', valign: 'middle',
                formatter: function(value,row, index){
                    var channel=row.channel;
                    if(channel == 1){
                        return '<label for="online"><font color="blue">支付宝</font></label>';
                    } else if(channel == 2){
                        return '<label for="offline"><font color="blue">微信</font></label>';
                    }else if(channel == 3){
                        return '<label for="unknown"><font color="blue">银行卡</font></label>';
                    }
                }},
            {title: '申请金额', field: 'applyAmount', visible: true, align: 'center', valign: 'middle'},
            {title: '到账金额', field: 'actualAmount', visible: true, align: 'center', valign: 'middle'},
            {title: '商户手续费', field: 'serviceFee', visible: true, align: 'center', valign: 'middle'},
            {title: '订单创建时间', field: 'crtTime', visible: true, align: 'center', valign: 'middle'},
            {title: '到账时间', field: 'arriveTime', visible: true, align: 'center', valign: 'middle'},
            {title: '回调时间', field: 'pushTime', visible: true, align: 'center', valign: 'middle'},
            {title: '订单状态', field: 'orderStatus', visible: true, align: 'center', valign: 'middle',
                formatter: function(value,row, index){
                    var orderStatus=row.orderStatus;
                    if(orderStatus == 2){
                        return '<label for="online"><font color="green">已到账</font></label>';
                    } else if(orderStatus == 1){
                        return '<label for="offline"><font color="red">处理中</font></label>';
                    }else {
                        return '<label for="unknown"><font color="#ff1493">订单超时</font></label>';
                    }
                }},
            {title: '回调状态', field: 'pushStatus', visible: true, align: 'center', valign: 'middle',
                formatter: function(value,row, index){
                    var pushStatus=row.pushStatus;
                    if(pushStatus == 2){
                        return '<label for="online"><font color="green">回调成功</font></label>';
                    } else if(pushStatus == 1){
                        return '<label for="offline"><font color="red">未回调</font></label>';
                    }else {
                        return '<label for="unknown"><font color="#ff1493">未知</font></label>';
                    }
                }

            }
    ];
};

/**
 * 检查是否选中
 */
Trade.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        Trade.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加
 */
Trade.openAddTrade = function () {
    var index = layer.open({
        type: 2,
        title: '添加',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/trade/trade_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看详情
 */
Trade.openTradeDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/trade/trade_update/' + Trade.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除
 */
Trade.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/trade/delete", function (data) {
            Feng.success("删除成功!");
            Trade.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("tradeId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询列表
 */
Trade.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    Trade.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = Trade.initColumn();
    var table = new BSTable(Trade.id, "/trade/list", defaultColunms);
    table.setPaginationType("client");
    Trade.table = table.init();
});
