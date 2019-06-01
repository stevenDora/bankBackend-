/**
 * 初始化详情对话框
 */
var BankFlowInfoDlg = {
    bankFlowInfoData : {}
};

/**
 * 清除数据
 */
BankFlowInfoDlg.clearData = function() {
    this.bankFlowInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
BankFlowInfoDlg.set = function(key, val) {
    this.bankFlowInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
BankFlowInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
BankFlowInfoDlg.close = function() {
    parent.layer.close(window.parent.BankFlow.layerIndex);
}

/**
 * 收集数据
 */
BankFlowInfoDlg.collectData = function() {
    this
    .set('id')
    .set('cardNo')
    .set('amount')
    .set('avaliableBalance')
    .set('peerCardNo')
    .set('transactionTime')
    .set('remark')
    .set('status')
    .set('crtTime')
    .set('uptTime');
}

/**
 * 提交添加
 */
BankFlowInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/bankFlow/add", function(data){
        Feng.success("添加成功!");
        window.parent.BankFlow.table.refresh();
        BankFlowInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.bankFlowInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
BankFlowInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/bankFlow/update", function(data){
        Feng.success("修改成功!");
        window.parent.BankFlow.table.refresh();
        BankFlowInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.bankFlowInfoData);
    ajax.start();
}

$(function() {

});
