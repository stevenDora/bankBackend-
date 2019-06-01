package cn.stylefeng.guns.modular.system.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BankFlowsModel {
    private List<BankCashflowVo> bankCashflowVos;

    public void sort(){
        Collections.sort(bankCashflowVos, new Comparator<BankCashflowVo>() {
            public int compare(BankCashflowVo arg0, BankCashflowVo arg1) {
                Date time1 = arg0.getTransaction_time();
                Date time2 = arg1.getTransaction_time();
                return time1.compareTo(time2);
            }
        });
    }

}
