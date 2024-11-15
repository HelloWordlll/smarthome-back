package com.hmall.smarthome.entry.vo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hmall.smarthome.entry.vo.ActionVO;
import com.hmall.smarthome.entry.vo.CondVO;
import com.hmall.smarthome.entry.vo.RuleVO;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
public class RuleVO3 {

    private RuleVO rule;
    private List<CondVO> cond;
    private List<ActionVO> action;

    @JsonCreator
    public RuleVO3(
            @JsonProperty("rule") RuleVO rule,
            @JsonProperty("cond") List<CondVO> cond,
            @JsonProperty("action") List<ActionVO> action) {
        this.rule = rule;
        this.cond = cond;
        this.action = action;
    }
}
