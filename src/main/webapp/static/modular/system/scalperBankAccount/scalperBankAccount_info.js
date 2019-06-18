/**
 * 初始化详情对话框
 */
var ScalperBankAccountInfoDlg = {
    scalperBankAccountInfoData : {}
};

/**
 * 清除数据
 */
ScalperBankAccountInfoDlg.clearData = function() {
    this.scalperBankAccountInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ScalperBankAccountInfoDlg.set = function(key, val) {
    this.scalperBankAccountInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ScalperBankAccountInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
ScalperBankAccountInfoDlg.close = function() {
    parent.layer.close(window.parent.ScalperBankAccount.layerIndex);
}

/**
 * 收集数据
 */
ScalperBankAccountInfoDlg.collectData = function() {
    this
    .set('id')
    .set('scalperId')
    .set('name')
    .set('cardNo')
    .set('bank')
    .set('bankCode')
    .set('cardAddr')
    .set('status')
    .set('crtTime')
    .set('uptTime');
}

/**
 * 提交添加
 */
ScalperBankAccountInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/scalperBankAccount/add", function(data){
        Feng.success("添加成功!");
        window.parent.ScalperBankAccount.table.refresh();
        ScalperBankAccountInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.scalperBankAccountInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
ScalperBankAccountInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/scalperBankAccount/update", function(data){
        Feng.success("修改成功!");
        window.parent.ScalperBankAccount.table.refresh();
        ScalperBankAccountInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.scalperBankAccountInfoData);
    ajax.start();
}

$(function() {

});
