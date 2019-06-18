package cn.stylefeng.guns.modular.system.dao;

import cn.stylefeng.guns.modular.system.dto.SelectCardReq;
import cn.stylefeng.guns.modular.system.model.ScalperBankAccount;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author stylefeng
 * @since 2019-06-18
 */
public interface ScalperBankAccountMapper extends BaseMapper<ScalperBankAccount> {

    Map getBankCard(SelectCardReq selectCardReq);
}
