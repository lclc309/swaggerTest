package com.lclc.test.util;

/**
 * 
 * @name ErrorCode
 * @discription 错误代码
 * @author lichao
 * @date 2015年9月29日
 */
public enum ErrorCode {

    ERRORCW(900, "不是合法请求"),
    EQUIPMENTCW(901, "该设备非您常用登录设备,需要验证您的手机号码"), // 不是常用设备登录
    QTSBDLCW(902, "已在其他设备登录,需要重新登录"),
    DOERROR(903, "操作失败"),
    QQCSCW(1001, "请求参数不正确"),
    YQMCW(1002, "此邀请码不存在或已经被使用"),
    SJHCW(1004, "此手机号已经被注册"),
    SJHNULL(1005, "此用户不存在"),
    PWDCW(1006, "您输入的密码有误,请重新输入"),
    PICNULL(1007, "此文章不存在"),
    PRODUCTNULL(1008, "此商品不存在或未上架"),
    BRANDNULL(1009, "此品牌不存在"),
    HASMODIFY(1010, "用户名只能修改一次"),
    MAILNOTALLOW(1011, "解除绑定邮件不合法"),
    MOBILEISNULL(1012, "由于您还未绑定手机号码,所以无法解除绑定邮箱"),
    MOBILENOTALLOW(1013, "解除绑定手机号码不合法"),
    MAILISNULL(1014, "由于您还未绑定邮箱,所以无法解除绑定手机号码"),
    MAILISEXIT(1015, "此邮箱已经被注册"),
    USERNAMEISEXIT(1016, "此用户名已经存在"),
    OAUTHHASBIND(1017, "此渠道已经被绑定过"),
    OAUTHBINDBYOTHER(1018, "此渠道已经被其他用户绑定"),
    OAUTHCANOTUNBIND(1019, "不能解除绑定,您将会失去登录渠道"),
    STORENULL(1020, "此店铺不存在或已下架"),
    REWARDNOTDEL(1021, "打赏过的图片无法被删除"),
    AUDITNOTDEL(1021, "审核中的投稿图片无法被删除"),

    YZMNECW(1101, "您输入的验证码有误,请重新输入"), // 验证码不存在
    YZMIUCW(1102, "您输入的验证码有误,请重新输入"), // 验证码已经被使用
    YZMGQCW(1103, "您输入的验证码已失效，请重新输入"), // 验证码已过期

    ACTIVITYNULL(1104, "此活动不存在"),
    AWARDNULL(1105, "此领奖信息不存在"),
    AWARDOVERDUE(1106, "已过领奖期限"),
    YHMCF(1107, "不能关注自己"),

    // 支付相关
    NOTENOUGH(1201, "余额不足"),
    ORDERPAID(1202, "订单/部分订单已支付或支付结果处理中"),
    ORDERCLOSED(1203, "订单已关闭"),
    SYSTEMERROR(1204, "支付超时，请重试"), // 超时
    ORDERERROR(1205, "支付超时，请重试"), // 其他异常 如签名不正确
    QUERYERROR(1206, "查询支付结果超时，请重试"),
    QUERYEREFUNDRROR(1207, "查询退款结果超时，请重试"),
    REFUNDERROR(1208, "退款超时，请重试"),
    OUTAMOUNT(1209, "退款额度大于订单总额"),

    // 店铺相关
    STORESUSPENDBUSINESS(1301, "店铺已暂停营业,过段时间再来吧"),
    STORESTOPBUSINESS(1302, "店铺已停止营业,过段时间再来吧"),
    // 货品相关
    PRODUCTSOLDOUT(1401, "商品已下架"),
    PRODUCTNOTFOUND(1402, "商品不存在"),
    PRODUCTONPROMOTION(1403, "当前商品正在参加商城活动，请等活动结束在进行下架（or删除）处理，若有问题请联系猫趣，电话xxxxxx"),
    // sku相关
    SKULOWSTOCKS(1501, "超过库存上限"),
    OUTRESTRICTION(1502, "超过限购数量"),

    // 快递相关
    POSTIDNOTFOUND(1601, "该单号暂无物流进展，请稍后再试"),

    // 资金账户相关
    CAPITALUNUSUAL(1701, "您操作的账户状态异常"),
    CAPITALUNUSUALME(1702, "您的账户状态异常"),
    CAPITALUREWARED(1703, "已经对该买家秀进行过打赏/不打赏"),
    OPERERROR(1704, "操作异常，请重试！"),

    // 售后
    OUTPOSTSALES(1801, "此商品错过售后期或已发送买家秀，无法申请售后"),
    POSTSALESTATUSERR(1802, "此售后当前状态不支持此操作"),
    EIXSTORDER(1802, "已存在售后订单"),
    REFUNDAMOUNTERR(1803, "退款金额错误"),
    POSTSALESNULL(1804, "售后订单不存在"),
    EIXSTREFUNDORDER(1805, "已存在退款订单"),
    NOTREFUND(1806, "当前售后状态不能操作退款"),
    REFUDFAIL(1807, "退款操作失败"),
    ORDERSHIP(1808, "已发货的订单不可申请此类售后"),

    // ===============之后是admin的错误========================
    HASPROMOTION(2001, "此时间段有设置的活动"),
    PROMOTIONNOW(2002, "当前进行中的活动,不能进行此操作"),
    PROMOTIONNULL(2003, "此活动不存在或已经删除"),
    PROMOTIONSTART(2004, "此活动已开始,不能进行修改"),
    PROMOTIONPRODUCTNULL(2005, "此关系不存在"),
    PRODUCTREPEAT(2006, "单品有重复"),
    PRODUCTREPEAT2(2008, "此单品已经参加了此活动"),
    BINDBRANDREPEAT(2009, "此品牌已经关联到其他店铺"),
    ACCOUNTBRANDREPEAT(2010, "此营销号已经关联到其他店铺"),
    STORENAMEREPEAT(2011, "此店铺名称已经被使用"),
    STOREBANKNOTEXIT(2022, "此转账账号不存在"),
    STORENOTEXIT(2023, "此店铺不存在"),
    HASPRODUCTEXIT(2024, "此店铺有存在正在售卖的产品,无法进行此操作,请先确认"),
    PRODUCTPRODUCTEXIT(2025, "商品正在参加活动,请先确认"),
    STORENOTCANBUY(2026, "店铺为不可销售状态"),
    STORONSALE(2027, "店铺营业中,不能修改,请先关闭品牌店铺"),

    // =============订单相关================================
    ORDERSTATUSERR(3000, "此订单当前状态不支持此操作"),
    STSTUSERROR(3001, "异常状态"),
    PRISEERROR(3002, "订单异常,价格不一致"),
    TRADENULL(3003, "该订单不存在"),
    CONSIGNOUTOFDATE(3004, "您已经延长过一次了,不能继续延长了"),
    EXTENDEDRECEIPTNOT(3005, "时间还未到,请在剩余3天时进行延长"),
    EXTENDEDRECEIPTREPEAT(3006, "您今天已经催过一次单了"),
    EXTENDEDONE(3007, "付款24小时后才能提醒卖家发货哦"),

    SYSERR(9999, "系统服务器异常");

    private int code;

    private String message;

    private ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {

        return code;
    }

    public void setCode(int code) {

        this.code = code;
    }

    public String getMessage() {

        return message;
    }

    public void setMessage(String message) {

        this.message = message;
    }

}
