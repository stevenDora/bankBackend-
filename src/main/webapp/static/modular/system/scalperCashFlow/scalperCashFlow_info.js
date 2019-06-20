/**
 * 初始化详情对话框
 */
var ScalperCashFlowInfoDlg = {
    scalperCashFlowInfoData : {}
};

/**
 * 清除数据
 */
ScalperCashFlowInfoDlg.clearData = function() {
    this.scalperCashFlowInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ScalperCashFlowInfoDlg.set = function(key, val) {
    this.scalperCashFlowInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ScalperCashFlowInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
ScalperCashFlowInfoDlg.close = function() {
    parent.layer.close(window.parent.ScalperCashFlow.layerIndex);
}

/**
 * 收集数据
 */
ScalperCashFlowInfoDlg.collectData = function() {
    this
    .set('id')
    .set('scalperId')
    .set('collectionAmount')
    .set('paymentAmount')
    .set('collectionEarning')
    .set('paymentEarning')
    .set('updatedAb')
    .set('oldAb')
    .set('updatedFa')
    .set('oldFa')
    .set('transactionTime')
    .set('orderNo')
    .set('orderStatus')
    .set('crtTime')
    .set('uptTime');
}

/**
 * 提交添加
 */
ScalperCashFlowInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/scalperCashFlow/add", function(data){
        Feng.success("添加成功!");
        window.parent.ScalperCashFlow.table.refresh();
        ScalperCashFlowInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.scalperCashFlowInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
ScalperCashFlowInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/scalperCashFlow/update", function(data){
        Feng.success("修改成功!");
        window.parent.ScalperCashFlow.table.refresh();
        ScalperCashFlowInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.scalperCashFlowInfoData);
    ajax.start();
}

$(function() {

});
