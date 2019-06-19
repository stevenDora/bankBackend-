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
        String TRADE_INVALID_OVERTIME_LOCK_PREFIX = "trade_invalid_overtime_lock_prefix";
        String TRADE_ORDER_STATUS_LOCK_PREFIX = "trade_order_status_lock_prefix";
        String SCALPER_LOCK_PREFIX = "scalper_lock_prefix";
    }

    public interface Channel{
        Integer CHANNEL_ALIPAY = 1;
        Integer CHANNEL_WXCHAT = 2;
        Integer CHANNEL_BANK = 3;
    }

    public interface RedisOrderPrefix {
        String PLATFORM_SYSTEM_ORDER_NO = "platform_system_order_no:";
        String ORDER_DUPLICATE_SUBMIT_CHECK = "order_dup_submit_check:";
        String ORDER_DETAIL = "order_detail:";
    }

    public interface OrderStatus{
        Integer ORDER_DUPLICATE_SUBMIT_MAX = 1;
        Integer ORDER_STATUS_PENDING = 0;
        Integer ORDER_STATUS_PROCESS = 1;
        Integer ORDER_STATUS_SUCCESS = 2;
        Integer ORDER_STATUS_OVERDUE = 3;
    }

    public interface PushStatus{
        Integer PUSH_STATUS_UNHANDLE = 1;
        Integer PUSH_STATUS_SUCCESS = 2;
    }


    public interface MessageRoute{
        String MSG_ROUTE_ORDER = "MSG_ROUTE_ORDER";
        String MSG_ROUTE_FLOW = "MSG_ROUTE_FLOW";
    }


    public interface orderRoute{
        Integer CREATE_ORDER = 1;
    }

}
