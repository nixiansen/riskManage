package com.zdd.risk;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 * ZddRiskManageApplication Web程序启动类
 *
 * @author fengshuonan
 * @date 2017-05-21 9:43
 */
public class ZddRiskManageServletInitializer extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(ZddRiskManageApplication.class);
    }

}
