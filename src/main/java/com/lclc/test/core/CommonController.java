package com.lclc.test.core;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lclc.test.util.ErrorCode;

import springfox.documentation.annotations.ApiIgnore;

@Controller
@RequestMapping("")
@ApiIgnore
public class CommonController extends JsonViewController {

    private static Logger log = LoggerFactory.getLogger(CommonController.class);

    // @Autowired
    // private AppConfig appConfig;
    @RequestMapping("/404")
    @ResponseBody
    public ResponseResult _404(HttpServletRequest request, HttpServletResponse response) {

        // if (Tools.isAjax(request)) {
        // try {
        // PrintWriter writer = response.getWriter();
        // ResponseResult result = new ResponseResult(false, 404, "请求不存在",
        // null);
        // writer.write(JSON.toJSONString(result));
        // writer.flush();
        //
        // } catch (IOException e) {
        // e.printStackTrace();
        // }
        // return null;
        // } else {
        // return "_common/404";
        // }
        return new ResponseResult(false, 404, "请求不存在", null);

    }

    @RequestMapping("/500")
    @ResponseBody
    public ResponseResult _500(HttpServletRequest request, HttpServletResponse response, ModelMap model) {

        log.warn("there is something wrong:" + request.getAttribute(javax.servlet.RequestDispatcher.ERROR_EXCEPTION));
        // if (Tools.isAjax(request)) {
        // return this.render(ResponseResult.failure(ErrorCode.SYSERR),
        // response);
        // } else {
        // model.put("exception",
        // request.getAttribute(javax.servlet.RequestDispatcher.ERROR_EXCEPTION));
        // return new ModelAndView("_common/500", model);
        // }
        return ResponseResult.failure(ErrorCode.SYSERR);
    }

    @RequestMapping("/public/test_500")
    @ResponseBody
    public String test_500(HttpServletRequest request) {

        throw new RuntimeException("错误了");
    }

}
