/**
 * 初始化详情对话框
 */
var TradeInfoDlg = {
    tradeInfoData : {}
};

/**
 * 清除数据
 */
TradeInfoDlg.clearData = function() {
    this.tradeInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
TradeInfoDlg.set = function(key, val) {
    this.tradeInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
TradeInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
TradeInfoDlg.close = function() {
    parent.layer.close(window.parent.Trade.layerIndex);
}

/**
 * 收集数据
 */
TradeInfoDlg.collectData = function() {
    this
    .set('id')
    .set('orderNo')
    .set('companyNo')
    .set('companyOrderNo')
    .set('applyAmount')
    .set('actualAmount')
    .set('serviceFee')
    .set('orderStatus')
    .set('arriveTime')
    .set('pushTime')
    .set('pushStatus')
    .set('crtTime')
    .set('uptTime');
}

/**
 * 提交添加
 */
TradeInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/trade/add", function(data){
        Feng.success("添加成功!");
        window.parent.Trade.table.refresh();
        TradeInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.tradeInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
TradeInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/trade/update", function(data){
        Feng.success("修改成功!");
        window.parent.Trade.table.refresh();
        TradeInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.tradeInfoData);
    ajax.start();
}

$(function() {

});
