package cn.stylefeng.guns.modular.system.constant;

import cn.stylefeng.roses.kernel.model.exception.AbstractBaseExceptionEnum;

import cn.stylefeng.guns.modular.system.utils.StringUtils;

public enum PayApiEnum implements AbstractBaseExceptionEnum {

    BANK_CARD_SELECT_SUC(200,"選卡成功!!"),
    BANK_CARD_QUERY_SUC(200,"查詢最後一次交易時間成功!"),

    BANK_CARD_SELECT_FAILED(401,"選卡失敗!!"),
    BANK_CARD_SELECT_FAILED_NOT_ENOUGH(402,"選卡失敗,卡資源不足"),

    BANK_CARD_RECV_FAILED_CARD_NO_INVALID(431,"交易流水接收失敗,卡號在系統不存在！！"),
    BANK_CARD_RECV_FAILED_AMOUNT_INVALID(432,"交易流水接收失敗,金額不存在！！"),
    BANK_CARD_RECV_FAILED_AB_INVALID(433,"交易流水接收失敗,卡餘額不存在！！"),
    BANK_CARD_RECV_FAILED_PEER_CARD_NO_INVALID(434,"交易流水接收失敗,對方卡號未填！！"),
    BANK_CARD_RECV_FAILED_TRANSACTION_TIME_INVALID(435,"交易流水接收失敗,交易時間格式不對！！"),
    BANK_CARD_RECV_FAILED_NULL(436,"交易流水接收失敗,信息為空!!"),
    BANK_CARD_RECV_FAILED_OLD_DATA(437,"交易流水接收失敗,流水数据是老数据！！ ！！"),
    BANK_CARD_RECV_SAVE_FAILED(438,"交易流水接收入库失败!!"),


    BANK_CARD_HEART_BEAT_FAILED(451,"心跳信息上報失敗,卡號在系統不存在！！"),
    BANK_CARD_QUERY_FAILED_CARDNO_INVALID(452,"查詢最後一次交易時間失敗,卡號不存在!"),


    BANK_CARD_CASH_FLOW_HANDLE_FLOW_DONE_FAILED(461,"处理银行流水,更改为发送状态失败！！"),
    BANK_CARD_CASH_FLOW_FAILED(462,"流水处理失败!!"),
    BANK_CARD_CASH_FLOW_FORWARD_FAILED(463,"流水转发失败!!"),


    BANK_CARD_LOCK_FAILED(471,"分布式锁失败！！"),
    GENERATION_ORDER_NO_FAILED(472,"生成平台订单号失败！！"),
    DEPOSIT_FAILED_SIGN(473,"充值申请失败,验签失败！！"),
    DEPOSIT_FAILED_INPUT_PARAMETERS(474,"生成平台订单号失败请求参数有误！！"),
    ACCOUNT_SELECT_FAILED(475,"账号选择失败!!"),
    DEPOSIT_FAILED_MERCHANT_REPEAT_SUBMIT(478,"相同商户订单号重复提交！！"),
    DEPOSIT_FAILED_ORDER_NOT_EXIST(479,"充值失败，订单不存在！！"),
    DEPOSIT_FAILED_UNKNOW(485,"充值失败，未知错误！！"),
    DEPOSIT_FAILED_CREATE_ORDER_MSG_SUBMIT(486,"发送下单失败！！"),
    MSG_COMMUNICATE_ERROR(487,"消息通讯异常！！"),
    FLOW_FAILED_SIGN(488,"流水推送验签失败！！"),
    UN_KNOW_ERROR(499,"未知错误！！"),
    DEPOSIT_APPLY_SUCCESS(200,"申请充值成功！！");


    private String desc;
    private Integer code;
    private String message;

    private PayApiEnum(int code, String desc){
        this.code = code;
        this.desc = desc;
        this.message = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public final static PayApiEnum newInstance(int code) {
        PayApiEnum[] s_ = PayApiEnum.values();
        if (StringUtils.isNotEmpty(s_)) {
            for (PayApiEnum s_s : s_) {
                if (s_s.getCode() == code) {
                    return s_s;
                }
            }
        }
        return null;
    }
}
