package cn.stylefeng.guns.modular.system.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import cn.stylefeng.guns.modular.system.model.BankCard;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author steven123
 * @since 2019-05-27
 */
public interface BankCardMapper extends BaseMapper<BankCard> {

    @Update("update bank_card set status = #{status} where id = #{id}")
    void changeSwitch(@Param("id") Integer id,
                      @Param("status") Byte status);

    @Update("update bank_card set online_status = #{status} where id = #{card_id}")
    void updateOnlineStats(@Param("card_id") Integer card_id,
                           @Param("status") Byte status);

    @Select("select id from bank_card")
    List<Integer> getAllCardIds();

    @Select("select id from bank_card where card_no = #{cardNo}")
    Integer getCardIdByCardNo(String cardNo);


    @Select("select id,card_no,name,address,bank_code,status,online_status,avaliable_amount" +
            ",recv_amount,pending_amount,limit_amount,last_transaction_time,crt_time,upt_time from bank_card " +
            "where " +
            "online_status = 1 " +
            "and status =1 " +
            "and limit_amount >= recv_amount + pending_amount + #{amount} order by upt_time asc ")
    @ResultType(cn.stylefeng.guns.modular.system.model.BankCard.class)
    List<BankCard> matchCards(BigDecimal amount);

    @Update("update bank_card set upt_time = Now(),pending_amount = pending_amount + #{amount} " +
            "where id = #{card_id} and (limit_amount >= recv_amount + pending_amount + #{amount})")
    int updateCardData(@Param("card_id") Long card_id,
                       @Param("amount") BigDecimal amount);


    @Update("update bank_card set last_transaction_time = #{lastTransactionTime} where card_no = #{cardNo}")
    void updateLastTransactionTime(@Param("cardNo") String cardNo,
                                   @Param("lastTransactionTime") Date lastTransactionTime);


    @Select("select card_no,last_transaction_time from bank_card where card_no = #{cardNo}")
    Map<String,Object> getTransLastTimeByCardNo(String cardNo);
}
