/**
 * 初始化详情对话框
 */
var CompanyCashFlowInfoDlg = {
    companyCashFlowInfoData : {}
};

/**
 * 清除数据
 */
CompanyCashFlowInfoDlg.clearData = function() {
    this.companyCashFlowInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
CompanyCashFlowInfoDlg.set = function(key, val) {
    this.companyCashFlowInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
CompanyCashFlowInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
CompanyCashFlowInfoDlg.close = function() {
    parent.layer.close(window.parent.CompanyCashFlow.layerIndex);
}

/**
 * 收集数据
 */
CompanyCashFlowInfoDlg.collectData = function() {
    this
    .set('id')
    .set('companyId')
    .set('collectionAmount')
    .set('paymentAmount')
    .set('collectionBfs')
    .set('paymentBfs')
    .set('updatedAb')
    .set('oldAb')
    .set('updatedFa')
    .set('oldFa')
    .set('transactionDate')
    .set('orderNo')
    .set('crtTime')
    .set('uptTime')
    .set('orderStatus');
}

/**
 * 提交添加
 */
CompanyCashFlowInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/companyCashFlow/add", function(data){
        Feng.success("添加成功!");
        window.parent.CompanyCashFlow.table.refresh();
        CompanyCashFlowInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.companyCashFlowInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
CompanyCashFlowInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/companyCashFlow/update", function(data){
        Feng.success("修改成功!");
        window.parent.CompanyCashFlow.table.refresh();
        CompanyCashFlowInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.companyCashFlowInfoData);
    ajax.start();
}

$(function() {

});
