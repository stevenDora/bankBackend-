package cn.stylefeng.guns.modular.system.constant;

import cn.stylefeng.guns.modular.system.utils.DateUtil;

public class Constant {

    public static final String BANK_ONLINE_STATUS_PREFIX = "bank_online_status_prefix:";


    public static final Byte BANK_ONLINE_STATUS_ON = 1;
    public static final Byte BANK_ONLINE_STATUS_OFF = 0;

    public static final Byte BANK_ONLINE_STATUS_TIMEOUT_SECONDS = 60;
    public static final Byte BANK_REMARK_TRY_MAX_TIMES = 10;

    public static final Byte BANK_FLOW_PENGDING = 0;
    public static final Byte BANK_FLOW_PROCESSING = 1;
    public static final Byte BANK_FLOW_DONE = 2;

    public static final String BANK_RT_PARM_LAST_CURSOR = "cash_flow_cursor";
    public static final String BANK_RT_PARM_BATCHNUM = "cash_flow_batch_num";


    public interface Lock{
        String CASH_FLOW_LOCK_PREFIX = "cash_flow_lock_prefix";

    }

    public interface Channel{
        Byte CHANNEL_ALIPAY = 1;
        Byte CHANNEL_WXCHAT = 2;
        Byte CHANNEL_BANK = 3;
    }

    public interface OrderPrefix {
        String PLATFORM_SYSTEM_ORDER_NO = "platform_system_order_no";
    }
}
