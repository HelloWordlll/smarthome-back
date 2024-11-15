package com.hmall.smarthome.entry.vo;


import lombok.Data;

import java.util.List;

@Data
public class RuleVO2 {

    List<CondVO> cond;

    List<ActionVO> action;

    public RuleVO2(List<CondVO> conds, List<ActionVO> act) {
        cond = conds;
        action = act;
    }
}
