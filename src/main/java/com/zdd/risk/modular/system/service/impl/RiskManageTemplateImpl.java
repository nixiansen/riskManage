package com.zdd.risk.modular.system.service.impl;

import com.zdd.risk.core.template.config.ContextConfig;
import com.zdd.risk.core.template.config.ControllerConfig;
import com.zdd.risk.core.template.config.PageConfig;
import com.zdd.risk.core.template.engine.base.AbstractTemplateEngine;
import com.zdd.risk.core.util.ToolUtil;
import com.zdd.risk.modular.system.service.IRiskManageTemplate;
import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.beetl.core.resource.ClasspathResourceLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * RiskManageTemplate
 *
 * @author 租无忧科技有限公司
 * @date 2018-11-27.
 */
@org.springframework.context.annotation.Configuration
@Service
public class RiskManageTemplateImpl implements IRiskManageTemplate {

    @Value("${pagePathTemplate}")
    private String pagePathTemplate;
    @Value("${pageAddPathTemplate}")
    private String pageAddPathTemplate;
    @Value("${pageEditPathTemplate}")
    private String pageEditPathTemplate;
    @Value("${pageJsPathTemplate}")
    private String pageJsPathTemplate;
    @Value("${pageInfoJsPathTemplate}")
    private String pageInfoJsPathTemplate;

    protected GroupTemplate groupTemplate;
    private ContextConfig contextConfig = new ContextConfig();              //全局配置
    private ControllerConfig controllerConfig = new ControllerConfig();     //控制器的配置
    private PageConfig pageConfig = new PageConfig();                       //页面的控制器

    private void generatePageEditHtml() {
        String path = ToolUtil.format(getContextConfig().getProjectPath() + pageEditPathTemplate,
                getContextConfig().getBizEnName(),getContextConfig().getBizEnName());
        generateFile("template/page_edit.html.btl", path);
        System.out.println("生成编辑页面成功!");
    }

    private void generatePageAddHtml() {
        String path = ToolUtil.format(getContextConfig().getProjectPath() + pageAddPathTemplate,
                getContextConfig().getBizEnName(),getContextConfig().getBizEnName());
        generateFile("template/page_add.html.btl", path);
        System.out.println("生成添加页面成功!");
    }

    private void generatePageInfoJs() {
        String path = ToolUtil.format(getContextConfig().getProjectPath() + pageInfoJsPathTemplate,
                getContextConfig().getBizEnName(),getContextConfig().getBizEnName());
        generateFile("template/page_info.js.btl", path);
        System.out.println("生成页面详情js成功!");
    }

    private void generatePageJs() {
        String path = ToolUtil.format(getContextConfig().getProjectPath() + pageJsPathTemplate,
                getContextConfig().getBizEnName(),getContextConfig().getBizEnName());
        generateFile("template/page.js.btl", path);
        System.out.println("生成页面js成功!");
    }

    private void generatePageHtml() {
        String path = ToolUtil.format(getContextConfig().getProjectPath() + pagePathTemplate,
                getContextConfig().getBizEnName(),getContextConfig().getBizEnName());
        generateFile("template/page.html.btl", path);
        System.out.println("生成页面成功!");
    }

    private void generateController() {
        String controllerPath = ToolUtil.format(getContextConfig().getProjectPath() + getControllerConfig().getControllerPathTemplate(),
                ToolUtil.firstLetterToUpper(getContextConfig().getBizEnName()));
        generateFile("template/Controller.java.btl", controllerPath);
        System.out.println("生成控制器成功!");
    }

    private void configTemplate(Template template){
        template.binding("controller", getControllerConfig());
        template.binding("context", getContextConfig());
    }

    @Override
    public void initBeetlEngine() {
        Properties properties = new Properties();
        properties.put("RESOURCE.root", "");
        properties.put("DELIMITER_STATEMENT_START", "<%");
        properties.put("DELIMITER_STATEMENT_END", "%>");
        properties.put("HTML_TAG_FLAG", "##");
        Configuration cfg = null;
        try {
            cfg = new Configuration(properties);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ClasspathResourceLoader resourceLoader = new ClasspathResourceLoader();
        groupTemplate = new GroupTemplate(resourceLoader, cfg);
        groupTemplate.registerFunctionPackage("tool", new ToolUtil());
    }


    private void generateFile(String template,String filePath){
        Template pageTemplate = groupTemplate.getTemplate(template);
        configTemplate(pageTemplate);
        File file = new File(filePath);
        File parentFile = file.getParentFile();
        if(!parentFile.exists()){
            parentFile.mkdirs();
        }
        try {
            pageTemplate.renderTo(new FileOutputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void start() {
        generateController();
        generatePageHtml();
        generatePageAddHtml();
        generatePageEditHtml();
        generatePageJs();
        generatePageInfoJs();
    }

    @Override
    public PageConfig getPageConfig() {
        return pageConfig;
    }
    @Override
    public void setPageConfig(PageConfig pageConfig) {
        this.pageConfig = pageConfig;
    }
    @Override
    public ContextConfig getContextConfig() {
        return contextConfig;
    }
    @Override
    public void setContextConfig(ContextConfig contextConfig) {
        this.contextConfig = contextConfig;
    }
    @Override
    public ControllerConfig getControllerConfig() {
        return controllerConfig;
    }
    @Override
    public void setControllerConfig(ControllerConfig controllerConfig) {
        this.controllerConfig = controllerConfig;
    }
}
