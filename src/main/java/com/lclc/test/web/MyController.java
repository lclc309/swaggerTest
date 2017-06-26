package com.lclc.test.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lclc.test.core.BaseController;
import com.lclc.test.core.ResponseResult;
import com.lclc.test.model.UserModel;
import com.lclc.test.service.OrderSeqServiceSeq;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@Controller()
@RequestMapping("/lclc")
@Api(value = "/lclc", description = "操作订单的业务")
public class MyController extends BaseController {

    @Autowired
    private OrderSeqServiceSeq orderSeqService;

    /**
     * swagger 测试,访问http://localhost:8080/swagger-ui.html 查看
     * 
     * @param userid
     * @return
     * @throws InterruptedException
     */
    @ResponseBody
    @RequestMapping(value = "test", method = RequestMethod.POST)
    @ApiOperation(value = "获取订单号", notes = "获得订单号")
    @ApiImplicitParam(name = "userid", value = "userid", paramType = "form", required = true, dataType = "Long")
    public ResponseResult test(Long userid) {

        return this.successResult().data(orderSeqService.getNextSeq(userid));
    }

    @RequestMapping(value = "page1", method = RequestMethod.GET)
    public String page1() {

        return "page1";
    }

    @RequestMapping(value = "newFile", method = RequestMethod.GET)
    public String newFile() {

        return "NewFile";
    }

    @RequestMapping(value = "/load1", method = RequestMethod.POST)
    @ResponseBody
    @ApiImplicitParam(name = "user", value = "用户详细实体user", required = true, dataType = "UserModel")
    public ResponseResult load(@RequestBody UserModel user) {

        return this.successPublicResult();
    }

}
