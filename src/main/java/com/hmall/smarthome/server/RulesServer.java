package com.hmall.smarthome.server;

import com.hmall.smarthome.entry.pojo.Rules;
import com.hmall.smarthome.entry.vo.RuleVO2;
import com.hmall.smarthome.entry.vo.RuleVO3;
import org.apache.tomcat.util.digester.Rule;

import java.util.List;

public interface RulesServer {

    List<Rules> getRules();

    RuleVO2 getRules2(Integer id);

    boolean doRules(String msg);

    boolean updata(RuleVO3 rule);

    boolean deleteRules(Long id);
}
