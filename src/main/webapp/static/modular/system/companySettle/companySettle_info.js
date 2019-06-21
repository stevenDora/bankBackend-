/**
 * 初始化商户结算详情对话框
 */
var CompanySettleInfoDlg = {
    companySettleInfoData : {}
};

/**
 * 清除数据
 */
CompanySettleInfoDlg.clearData = function() {
    this.companySettleInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
CompanySettleInfoDlg.set = function(key, val) {
    this.companySettleInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
CompanySettleInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
CompanySettleInfoDlg.close = function() {
    parent.layer.close(window.parent.CompanySettle.layerIndex);
}

/**
 * 收集数据
 */
CompanySettleInfoDlg.collectData = function() {
    this
    .set('id')
    .set('orderNo')
    .set('mowOrderNo')
    .set('applyAmount')
    .set('trueAmount')
    .set('serviceFee')
    .set('bankCode')
    .set('bankName')
    .set('subBank')
    .set('bankCardNo')
    .set('accountName')
    .set('status')
    .set('processWay')
    .set('companyId')
    .set('remark')
    .set('crtTime')
    .set('uptTime')
    .set('transTime')
    .set('maNo')
    .set('note')
    .set('operator')
    .set('overTime')
    .set('scalperId')
    .set('scalperImageUrl')
    .set('phone')
    .set('currency');
}

/**
 * 提交添加
 */
CompanySettleInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/companySettle/add", function(data){
        Feng.success("添加成功!");
        window.parent.CompanySettle.table.refresh();
        CompanySettleInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.companySettleInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
CompanySettleInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/companySettle/update", function(data){
        Feng.success("修改成功!");
        window.parent.CompanySettle.table.refresh();
        CompanySettleInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.companySettleInfoData);
    ajax.start();
}

$(function() {

});
