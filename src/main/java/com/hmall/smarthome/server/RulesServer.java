package com.hmall.smarthome.server;

import com.hmall.smarthome.entry.pojo.Rules;
import org.apache.tomcat.util.digester.Rule;

import java.util.List;

public interface RulesServer {

    List<Rules> getRules();

    boolean doRules(String msg);
}
