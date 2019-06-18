/**
 * 初始化新流水显示详情对话框
 */
var FlowDataInfoDlg = {
    flowDataInfoData : {}
};

/**
 * 清除数据
 */
FlowDataInfoDlg.clearData = function() {
    this.flowDataInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
FlowDataInfoDlg.set = function(key, val) {
    this.flowDataInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
FlowDataInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
FlowDataInfoDlg.close = function() {
    parent.layer.close(window.parent.FlowData.layerIndex);
}

/**
 * 收集数据
 */
FlowDataInfoDlg.collectData = function() {
    this
    .set('id')
    .set('scalperId')
    .set('channel')
    .set('amount')
    .set('remark')
    .set('fromIp')
    .set('receiveTime')
    .set('data')
    .set('orderNo')
    .set('isMatched')
    .set('crtTime')
    .set('uptTime');
}

/**
 * 提交添加
 */
FlowDataInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/flowData/add", function(data){
        Feng.success("添加成功!");
        window.parent.FlowData.table.refresh();
        FlowDataInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.flowDataInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
FlowDataInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/flowData/update", function(data){
        Feng.success("修改成功!");
        window.parent.FlowData.table.refresh();
        FlowDataInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.flowDataInfoData);
    ajax.start();
}

$(function() {

});
