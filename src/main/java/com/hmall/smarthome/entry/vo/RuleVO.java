package com.hmall.smarthome.entry.vo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RuleVO {
    private Long id;
    private String name;
    private boolean open;

    @JsonCreator
    public RuleVO(
            @JsonProperty("id") Long id,
            @JsonProperty("name") String name,
            @JsonProperty("open") boolean open) {
        this.id = id;
        this.name = name;
        this.open = open;
    }
}
