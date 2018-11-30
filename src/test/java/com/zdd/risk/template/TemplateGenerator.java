package com.zdd.risk.template;

import com.zdd.risk.core.template.config.ContextConfig;
import com.zdd.risk.core.template.engine.SimpleTemplateEngine;
import com.zdd.risk.core.template.engine.base.RiskManageTemplateEngine;

import java.io.IOException;

/**
 * 测试riskManage模板引擎
 *
 * @author fengshuonan
 * @date 2017-05-09 20:27
 */
public class TemplateGenerator {

    public static void main(String[] args) throws IOException {
        ContextConfig contextConfig = new ContextConfig();
        contextConfig.setBizChName("代码生成");
        contextConfig.setBizEnName("code");

        RiskManageTemplateEngine riskManageTemplateEngine = new SimpleTemplateEngine();
        riskManageTemplateEngine.setContextConfig(contextConfig);
        riskManageTemplateEngine.start();
    }

}
