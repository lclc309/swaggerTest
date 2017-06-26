package com.lclc.test.core;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.google.common.base.Strings;
import com.lclc.test.conf.AppConfig;
import com.lclc.test.util.ConfigurationPropertys;
import com.lclc.test.util.ErrorCode;
import com.lclc.test.util.QEncodeUtil;

/**
 * 控制器基类.
 * 
 */
public abstract class BaseController {

    public static final String REQ_ATTR_CHANNEL_KEY = "channel";

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ApplicationContext ctx;

    @Autowired
    private AppConfig appConfig;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {

        registerCustomEidtorsForWebDataBinder(binder);
    }

    protected final ResponseResult successResult() {

        return ResponseResult.success();
    }

    protected final ResponseResult successPublicResult() {

        return ResponseResult.success("操作成功！");
    }

    protected final ResponseResult successResult(String message) {

        return ResponseResult.success(message);
    }

    protected final ResponseResult successResult(String message, Object data) {

        return ResponseResult.success(message).data(data);
    }

    protected final ResponseResult failureResult() {

        return ResponseResult.failure();
    }

    protected final ResponseResult failureResult(String message) {

        return ResponseResult.failure(message);
    }

    protected final ResponseResult failureResult(ErrorCode errorCode) {

        if (errorCode == null) {
            return ResponseResult.failure();
        }
        return ResponseResult.failure(errorCode);
    }

    protected final ResponseResult failureParamResult() {

        return this.failureResult(ErrorCode.QQCSCW);
    }

    /**
     * 注册自定义类型转换器.
     * 
     */
    protected void registerCustomEidtorsForWebDataBinder(WebDataBinder binder) {

        // 日期格式化
        SimpleDateFormat dateFormat = new SimpleDateFormat(getBinderDatePattern());
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    /**
     * 是Ajax请求?
     */
    protected boolean isAjaxRequest() {

        return "x-requested-with".equals(this.getRequest().getHeader("XMLHttpRequest"));
    }

    /**
     * 自定义日期绑定格式.
     */
    protected String getBinderDatePattern() {

        return "yyyy-MM-dd HH:mm:ss";
    }

    /**
     * 设置当前channel.
     */
    protected void setChannel(final String channel) {

        this.getRequest().setAttribute(REQ_ATTR_CHANNEL_KEY, channel);
    }

    /**
     * 获取ServletRequestAttributes对象.
     */
    protected final ServletRequestAttributes getServletRequestAttributes() {

        return (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    }

    /**
     * 获取HttpServletRequest对象.
     */
    protected HttpServletRequest getRequest() {

        return getServletRequestAttributes().getRequest();
    }

    protected final String getStringParameter(String name, String defaultValue) {

        return ServletRequestUtils.getStringParameter(getRequest(), name, defaultValue);
    }

    protected final boolean getBooleanParameter(String name, boolean defaultValue) {

        return ServletRequestUtils.getBooleanParameter(getRequest(), name, defaultValue);
    }

    protected final int getIntParameter(String name, int defaultValue) {

        return ServletRequestUtils.getIntParameter(getRequest(), name, defaultValue);
    }

    protected final double getDoubleParameter(String name, double defaultValue) {

        return ServletRequestUtils.getDoubleParameter(getRequest(), name, defaultValue);
    }

    protected final long getLongParameter(String name, long defaultValue) {

        return ServletRequestUtils.getLongParameter(getRequest(), name, defaultValue);
    }

    protected int getPageByDefault() {

        int page = this.getIntParameter("page", 0);// 从0计算
        if (page < 0) {
            page = 0;
        }
        return page;
    }

    protected int getPageSizeByDefault(int defaultValue) {

        return this.getIntParameter("rows", defaultValue);
    }

    /**
     * 根据请求数据获取SearchFilters.
     */
    // protected Collection<SearchFilter> getSearchFilters() {
    // return
    // SearchFilter.parse(Servlets.getParametersStartingWith(this.getRequest(),
    // "f_")).values();
    // }

    protected String viewOrDefault(String name) {

        final Resource resource = ctx.getResource("classpath:/templates/" + name + ".html");
        if (resource != null && resource.exists()) {
            return name;
        }
        return "_" + name;
    }

    protected Long getIdinRequest() {

        String nonce = this.getRequest().getParameter("nonce");
        if (Strings.isNullOrEmpty(nonce)) {
            return null;
        }
        String id = null;
        if (appConfig.getProperty("model.signature", int.class, 0) == 1) {
            id = nonce;
        } else {
            id = QEncodeUtil.aesDecrypt(nonce, ConfigurationPropertys.AESKEY);
        }
        // String id = nonce;
        logger.info("获取到一个id:[{}]", id);
        if (!NumberUtils.isNumber(id)) {
            return null;
        }
        return NumberUtils.toLong(id);
    }
}
