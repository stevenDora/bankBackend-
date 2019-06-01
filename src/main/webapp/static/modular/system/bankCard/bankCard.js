/**
 * 管理初始化
 */
var BankCard = {
    id: "BankCardTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
BankCard.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: '', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '卡号', field: 'cardNo', visible: true, align: 'center', valign: 'middle'},
        {title: '卡主姓名', field: 'name', visible: true, align: 'center', valign: 'middle'},
        {title: '发卡行地址', field: 'address', visible: true, align: 'center', valign: 'middle'},
        {title: '银行编号', field: 'bankCode', visible: true, align: 'center', valign: 'middle'},
        {title: '余额', field: 'avaliableAmount', visible: true, align: 'center', valign: 'middle'},
        {title: '已收款项', field: 'recvAmount', visible: true, align: 'center', valign: 'middle'},
        {title: '进行中款项', field: 'pendingAmount', visible: true, align: 'center', valign: 'middle'},
        {title: '收款上限', field: 'limitAmount', visible: true, align: 'center', valign: 'middle'},
        {title: '上次收款时间', field: 'lastTransactionTime', visible: true, align: 'center', valign: 'middle'},
        {title: '', field: 'crtTime', visible: false, align: 'center', valign: 'middle'},
        {title: '', field: 'uptTime', visible: false, align: 'center', valign: 'middle'},
        {title: '在线状态', field: 'onlineStatus', visible: true, align: 'center', valign: 'middle',
            formatter: function(value,row, index){
                var onlineStatus=row.onlineStatus;
                if(onlineStatus == 1){
                    return '<label for="online"><font color="green">在线</font></label>';
                } else if(onlineStatus == 0){
                    return '<label for="offline"><font color="red">离线</font></label>';
                }else {
                    return '<label for="unknown"><font color="#ff1493">未知</font></label>';
                }
            }
        },
        {title: '开关状态', field: 'status', visible: true, align: 'center', valign: 'middle',
            formatter: function(value,row, index){
                var card_id = row.id;
                var status=row.status;
                if(status == 1){
                    return '<input  value="'+card_id+'" type="checkbox" name="my-checkbox" checked/>';
                }else{
                    return '<input  value="'+card_id+'" type="checkbox" name="my-checkbox"/>';
                }
            }
        }
    ];
};

/**
 * 检查是否选中
 */
BankCard.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        BankCard.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加
 */
BankCard.openAddBankCard = function () {
    var index = layer.open({
        type: 2,
        title: '添加',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/bankCard/bankCard_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看详情
 */
BankCard.openBankCardDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/bankCard/bankCard_update/' + BankCard.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除
 */
BankCard.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/bankCard/delete", function (data) {
            Feng.success("删除成功!");
            BankCard.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("bankCardId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询列表
 */
BankCard.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    BankCard.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = BankCard.initColumn();
    var table = new BSTable(BankCard.id, "/bankCard/list", defaultColunms);
    table.setPaginationType("client");
    BankCard.table = table.init();

    window.onresize = function(){
        window.location.reload();
    };
});
