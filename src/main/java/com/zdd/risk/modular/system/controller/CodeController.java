package com.zdd.risk.modular.system.controller;

import com.zdd.risk.common.annotion.Permission;
import com.zdd.risk.common.constant.Const;
import com.zdd.risk.common.controller.BaseController;
import com.zdd.risk.common.exception.BizExceptionEnum;
import com.zdd.risk.common.exception.BussinessException;
import com.zdd.risk.core.template.config.ContextConfig;
import com.zdd.risk.core.template.config.ControllerConfig;
import com.zdd.risk.core.template.engine.SimpleTemplateEngine;
import com.zdd.risk.core.template.engine.base.RiskManageTemplateEngine;
import com.zdd.risk.core.util.ToolUtil;
import com.zdd.risk.modular.system.service.IRiskManageTemplate;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 代码生成控制器
 *
 * @author fengshuonan
 * @Date 2017-05-23 18:52:34
 */
@Controller
@RequestMapping("/code")
public class CodeController extends BaseController {

    private String PREFIX = "/system/code/";

    @Value("${controllerPathTemplate}")
    private String controllerPathTemplate;

    @Autowired
    private IRiskManageTemplate iRiskManageTemplate;
    /**
     * 跳转到代码生成首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "code.html";
    }

    /**
     * 代码生成
     */
    @ApiOperation("生成代码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bizChName", value = "业务名称", required = true, dataType = "String"),
            @ApiImplicitParam(name = "bizEnName", value = "业务英文名称", required = true, dataType = "String"),
            @ApiImplicitParam(name = "path", value = "项目生成类路径", required = true, dataType = "String")
    })
    @RequestMapping(value = "/generate",method = RequestMethod.POST)
    @ResponseBody
    @Permission(Const.ADMIN_NAME)
    public Object add(String bizChName, String bizEnName, String path) {
        if (ToolUtil.isOneEmpty(bizChName, bizEnName)) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        ContextConfig contextConfig = new ContextConfig();
        contextConfig.setBizChName(bizChName);
        contextConfig.setBizEnName(bizEnName);
        if (ToolUtil.isNotEmpty(path)) {
            contextConfig.setProjectPath(path);
        }

//        RiskManageTemplateEngine templateEngine = new SimpleTemplateEngine();
//        templateEngine.setContextConfig(contextConfig);
//        templateEngine.start();
        iRiskManageTemplate.setContextConfig(contextConfig);

        ControllerConfig controllerConfig=new ControllerConfig();
        controllerConfig.setControllerPathTemplate(controllerPathTemplate);
        iRiskManageTemplate.setControllerConfig(controllerConfig);

        iRiskManageTemplate.initBeetlEngine();
        iRiskManageTemplate.start();

        return super.SUCCESS_TIP;
    }
}
