package com.zdd.risk.modular.system.service;

import com.zdd.risk.core.template.config.ContextConfig;
import com.zdd.risk.core.template.config.ControllerConfig;
import com.zdd.risk.core.template.config.PageConfig;

/**
 * Created by hyg on 2018-11-27.
 * Copyright by mofanghr
 */
public interface IRiskManageTemplate {


    void initBeetlEngine();

    void start();

    PageConfig getPageConfig();

    void setPageConfig(PageConfig pageConfig);

    ContextConfig getContextConfig();

    void setContextConfig(ContextConfig contextConfig);

    ControllerConfig getControllerConfig();

    void setControllerConfig(ControllerConfig controllerConfig);
}
