/**
 * 初始化详情对话框
 */
var BankCardInfoDlg = {
    bankCardInfoData : {}
};

/**
 * 清除数据
 */
BankCardInfoDlg.clearData = function() {
    this.bankCardInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
BankCardInfoDlg.set = function(key, val) {
    this.bankCardInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
BankCardInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
BankCardInfoDlg.close = function() {
    parent.layer.close(window.parent.BankCard.layerIndex);
}

/**
 * 收集数据
 */
BankCardInfoDlg.collectData = function() {
    this
    .set('id')
    .set('cardNo')
    .set('name')
    .set('address')
    .set('bankCode')
    .set('status')
    .set('onlineStatus')
    .set('avaliableAmount')
    .set('recvAmount')
    .set('pendingAmount')
    .set('limitAmount')
    .set('lastTransactionTime')
    .set('crtTime')
    .set('uptTime');
}

/**
 * 提交添加
 */
BankCardInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/bankCard/add", function(data){
        Feng.success("添加成功!");
        window.parent.BankCard.table.refresh();
        BankCardInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.bankCardInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
BankCardInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/bankCard/update", function(data){
        Feng.success("修改成功!");
        window.parent.BankCard.table.refresh();
        BankCardInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.bankCardInfoData);
    ajax.start();
}

$(function() {

});
