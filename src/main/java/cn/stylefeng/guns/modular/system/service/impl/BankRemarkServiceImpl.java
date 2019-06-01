package cn.stylefeng.guns.modular.system.service.impl;

import cn.stylefeng.guns.modular.system.dao.BankRemarkMapper;
import cn.stylefeng.guns.modular.system.model.BankRemark;
import cn.stylefeng.guns.modular.system.service.IBankRemarkService;
import cn.stylefeng.guns.modular.system.utils.StringUtils;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

import static cn.stylefeng.guns.modular.system.constant.Constant.BANK_REMARK_TRY_MAX_TIMES;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author stylefeng123
 * @since 2019-05-29
 */
@Service
public class BankRemarkServiceImpl extends ServiceImpl<BankRemarkMapper, BankRemark> implements IBankRemarkService {

    @Autowired
    private BankRemarkMapper bankRemarkMapper;

    @Override
    public String genRemark() {
        Boolean over = false;
        Integer times = 0;
        BankRemark bankRemark = BankRemark.builder().build();
        while(!over&&times <= BANK_REMARK_TRY_MAX_TIMES){
            over = true;
            try {
                //生成remark
                String remark = generateRemark();
                if(StringUtils.isEmpty(remark)){
                    over = false;
                    continue;
                }
                bankRemark.setRemark(remark);
                bankRemarkMapper.insert(bankRemark);
            }catch (Exception e){
                //duplicate check
                over = false;
                ++ times;
            }

        }
        if(!over){
            return null;
        }
        return bankRemark.getRemark();
    }


    private static char[] ops = new char[] {'+', '-', '*'};
    /**
     * + - *
     * */
    private String generateRemark() {
        Random rdm = new Random();
        int num1 = rdm.nextInt(10);
        int num2 = rdm.nextInt(10);
        int num3 = rdm.nextInt(10);
        char op1 = ops[rdm.nextInt(3)];
        char op2 = ops[rdm.nextInt(3)];
        String exp = ""+ num1 + op1 + num2 + op2 + num3;
        return exp;
    }

}
