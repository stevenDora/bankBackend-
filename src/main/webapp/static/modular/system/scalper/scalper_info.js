/**
 * 初始化详情对话框
 */
var ScalperInfoDlg = {
    scalperInfoData : {}
};

/**
 * 清除数据
 */
ScalperInfoDlg.clearData = function() {
    this.scalperInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ScalperInfoDlg.set = function(key, val) {
    this.scalperInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ScalperInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
ScalperInfoDlg.close = function() {
    parent.layer.close(window.parent.Scalper.layerIndex);
}

/**
 * 收集数据
 */
ScalperInfoDlg.collectData = function() {
    this
    .set('id')
    .set('scalperId')
    .set('rootId')
    .set('upperId')
    .set('agentPath')
    .set('avaliableBalance')
    .set('freezeBalance')
    .set('totalBalance')
    .set('collectBalanceSumDay')
    .set('collectBalanceMaxDay')
    .set('lastAssignTaskTime')
    .set('lastAccountTime')
    .set('lastChargeTime')
    .set('lastFlowPushTime')
    .set('alipaySwitch')
    .set('wxSwitch')
    .set('bankSwitch')
    .set('blockStatus')
    .set('alipayRate')
    .set('wxRate')
    .set('bankRate')
    .set('crtTime')
    .set('uptTime');
}

/**
 * 提交添加
 */
ScalperInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/scalper/add", function(data){
        Feng.success("添加成功!");
        window.parent.Scalper.table.refresh();
        ScalperInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.scalperInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
ScalperInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/scalper/update", function(data){
        Feng.success("修改成功!");
        window.parent.Scalper.table.refresh();
        ScalperInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.scalperInfoData);
    ajax.start();
}

$(function() {

});
