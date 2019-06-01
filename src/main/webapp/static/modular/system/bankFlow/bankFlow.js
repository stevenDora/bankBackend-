/**
 * 管理初始化
 */
var BankFlow = {
    id: "BankFlowTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
BankFlow.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: '序号', field: 'id', visible: true, align: 'center', valign: 'middle'},
        {title: '我方卡号', field: 'cardNo', visible: true, align: 'center', valign: 'middle'},
        {title: '交易金额', field: 'amount', visible: true, align: 'center', valign: 'middle'},
        {title: '可用余额', field: 'avaliableBalance', visible: true, align: 'center', valign: 'middle'},
        {title: '对方卡号', field: 'peerCardNo', visible: true, align: 'center', valign: 'middle'},
        {title: '最后交易时间', field: 'transactionTime', visible: true, align: 'center', valign: 'middle'},
        {title: '附言', field: 'remark', visible: true, align: 'center', valign: 'middle'},
        {title: '处理状态', field: 'status', visible: true, align: 'center', valign: 'middle',
            formatter: function(value,row, index){
                var process_state=row.status;
                if(process_state == 0){
                    return '<label for="online"><font color="red">未处理</font></label>';
                } else if(process_state == 1){
                    return '<label for="offline"><font color="blue">处理中</font></label>';
                }else {
                    return '<label for="unknown"><font color="green">已匹配</font></label>';
                }
            }},
        {title: '', field: 'crtTime', visible: false, align: 'center', valign: 'middle'},
        {title: '', field: 'uptTime', visible: false, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
BankFlow.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        BankFlow.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加
 */
BankFlow.openAddBankFlow = function () {
    var index = layer.open({
        type: 2,
        title: '添加',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/bankFlow/bankFlow_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看详情
 */
BankFlow.openBankFlowDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/bankFlow/bankFlow_update/' + BankFlow.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除
 */
BankFlow.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/bankFlow/delete", function (data) {
            Feng.success("删除成功!");
            BankFlow.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("bankFlowId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询列表
 */
BankFlow.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    BankFlow.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = BankFlow.initColumn();
    var table = new BSTable(BankFlow.id, "/bankFlow/list", defaultColunms);
    table.setPaginationType("client");
    BankFlow.table = table.init();
    window.onresize = function(){
        window.location.reload();
    };
});
