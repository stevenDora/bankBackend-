package cn.stylefeng.guns.modular.system.service.impl;

import cn.stylefeng.guns.modular.system.dao.BankMgrParmMapper;
import cn.stylefeng.guns.modular.system.model.BankMgrParm;
import cn.stylefeng.guns.modular.system.service.IBankMgrParmService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static cn.stylefeng.guns.modular.system.constant.Constant.BANK_RT_PARM_BATCHNUM;
import static cn.stylefeng.guns.modular.system.constant.Constant.BANK_RT_PARM_LAST_CURSOR;


/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author stylefeng123
 * @since 2019-05-30
 */
@Service
public class BankMgrParmServiceImpl extends ServiceImpl<BankMgrParmMapper, BankMgrParm> implements IBankMgrParmService {

    @Autowired
    private BankMgrParmMapper bankMgrParmMapper;

    @Override
    public Integer getLastCursor() {
        return bankMgrParmMapper.getParmValueByName(BANK_RT_PARM_LAST_CURSOR);
    }

    @Override
    public Integer getBatchNum() {
        return bankMgrParmMapper.getParmValueByName(BANK_RT_PARM_BATCHNUM);
    }

    @Override
    public void updateLastCursor(Integer value) {
        bankMgrParmMapper.updateParmValueByName(BANK_RT_PARM_LAST_CURSOR,value);
    }
}
