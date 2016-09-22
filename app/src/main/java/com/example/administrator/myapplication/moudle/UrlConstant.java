package com.example.administrator.myapplication.moudle;


/**
 * Created by Administrator on 2016/9/22.
 */
public class UrlConstant {
    //    public static final String BASE_IMAGE_URL = BuildConfig.BASE_IMAGE_URL;
    public static final String BASE_HTTPS_URL = BuildConfig.BASE_HTTPS_URL;
    /**
     * 版本更新地址
     */
    public static final String UPDATE_APK_URL = BASE_HTTPS_URL + "appClient/checkForUpgrade";


    /**
     * 首页列表
     */
    public static final String URL_HOMEPAGE_LIST = BASE_HTTPS_URL + "show/list";
    /**
     * 获取约票信息
     */
    public static final String URL_ORDER_TICKET_INFO = BASE_HTTPS_URL + "order/preRequestTicket";
    /**
     * 获取登录短信验证码
     */
    public static final String URL_GET_LOGIN_CODE = BASE_HTTPS_URL + "user/getLoginVerificationCode";
    /**
     * 登录
     */
    public static final String URL_LOGIN = BASE_HTTPS_URL + "user/login";
    /**
     * 获取城市列表
     */
    public static final String URL_GET_CITIES = BASE_HTTPS_URL + "city/list";
    /**
     * 立即约票
     */
    public static final String URL_APPOINTMENT = BASE_HTTPS_URL + "order/requestTicket";
    /**
     * 上传头像
     */
    public static final String URL_UP_AVATAR = BASE_HTTPS_URL + "user/uploadAvatar";

    /**
     * 获取热门关键词
     */
    public static final String URL_GET_HOT_KEYS = BASE_HTTPS_URL + "keyword/findHot";
    /**
     * 根据关键词搜索演出列表
     */
    public static final String URL_GET_SHOWS_BY_KEYWORD = BASE_HTTPS_URL + "show/listByKeyword";
    /**
     * 约票/订单 列表
     */
    public static final String URL_APPOINTMENT_AND_ORDER_LIST = BASE_HTTPS_URL + "order/listAll";
    /**
     * 取消订单
     */
    public static final String URL_CANCEL_ORDER = BASE_HTTPS_URL + "order/cancelOrder";
    /**
     * 取消订单
     */
    public static final String URL_CLOSE_ORDER = BASE_HTTPS_URL + "order/closeOrder";
    /**
     * 获取订单详情
     */
    public static final String URL_GET_ORDER_DETAIL = BASE_HTTPS_URL + "order/viewInfo";//order/view
    /**
     * 获取订单详情 支付界面用
     */
    public static final String URL_GET_ORDER_DETAIL_FOR_PAY = BASE_HTTPS_URL + "order/viewInfoForPay";//order/view
    /**
     * 新增收货人信息
     */
    public static final String URL_CREATE_RECEIVER = BASE_HTTPS_URL + "deliveryAddress/save";
    /**
     * 修改收货人信息
     */
    public static final String URL_MODIFY_RECEIVER = BASE_HTTPS_URL + "deliveryAddress/update";
    /**
     * 获取收货人列表
     */
    public static final String URL_GET_RECEIVERS = BASE_HTTPS_URL + "deliveryAddress/list";
    /**
     * 删除收货人信息
     */
    public static final String URL_DELETE_RECEIVER = BASE_HTTPS_URL + "deliveryAddress/delete";
    /**
     * 预支付处理
     */
    public static final String URL_PREPAY = BASE_HTTPS_URL + "order/pay";
    /**
     * 获取演出详情
     */
    public static final String URL_SHOW_DETAIL = BASE_HTTPS_URL + "show/view";
    /**
     * 订单申诉
     */
    public static final String URL_APPEAL = BASE_HTTPS_URL + "order/appeal1";
    /**
     * 修改买家留言
     */
    public static final String URL_MODIFY_BUYER_REMARK = BASE_HTTPS_URL + "order/updateLeaveMessage";

    /**
     *
     */
    public static final String ADVIC_BACK = BASE_HTTPS_URL + "appFeedback/save";

    /**
     * 验证旧手机号码
     */
    public static final String CHECK_OLD_PHONE = BASE_HTTPS_URL + "user/verifyOldCellphone";
    /**
     * 绑定新手机号码手机号码
     */
    public static final String CHECK_NEW_PHONE = BASE_HTTPS_URL + "user/updateCellphone";
    /**
     * 修改手机号时获取短信验证码
     */
    public static final String MODIFY_PHONE_CODE = BASE_HTTPS_URL + "user/getModifyPhoneVerificationCode";

    /**
     * 修改手机号时获取短信验证码
     */
    public static final String CHANGE_USER_NAME = BASE_HTTPS_URL + "user/updateNickname";

    /**
     * 退出接口
     */
    public static final String OUT_LOGIN = BASE_HTTPS_URL + "user/logout";
    /**
     * 绑定推送服务
     */
    public static final String PUSH_CLIENT = BASE_HTTPS_URL + "pushClient/bind2";

    /**
     * 判断是否已绑定推送
     */
    public static final String GET_IF_HAS_BIND_PUSH = BASE_HTTPS_URL + "pushClient/isBound";

    /**
     * 获取绑定推送的别名
     */
    public static final String GET_BIND_PUSH_ALIAS = BASE_HTTPS_URL + "pushClient/getAlias";

    /**
     * 申请退款
     * order/applyDrawback
     */
    public static final String ORDER_APPLY_DRAWBACK = BASE_HTTPS_URL + "order/applyDrawback";

    /**
     * 确认收货
     * order/applyDrawback
     */
    public static final String ORDER_CONFIRM_RECEIVING = BASE_HTTPS_URL + "order/confirmReceived";
    /**
     * 进入评价界面
     * order/applyDrawback
     */
    public static final String ORDER_PRE_EVALUATE = BASE_HTTPS_URL + "order/preEvaluate";

    /**
     * 评价
     * order/evaluate
     */
    public static final String ORDER_EVALUATE = BASE_HTTPS_URL + "order/evaluate";
    /**
     * 关闭申诉
     */
    public static final String CLOSE_APPEALING = BASE_HTTPS_URL + "order/closeAppealing";

    /**
     * 获取评论标签
     */
    public static final String URL_GET_COMMENT_TAGS = BASE_HTTPS_URL + "order/listCommentsTag";

    /**
     * 获取淘票列表
     */
    public static final String URL_GET_DISCOVERY_LIST = BASE_HTTPS_URL + "show/listByKeywordForPostTicket";

    /**
     * 淘票 进入购买页面 获取详情
     */
    public static final String URL_GET_DISCOVERY_DATA = BASE_HTTPS_URL + "postTicket/prePurchase";

    /**
     * 淘票 进入购买页面 获取详情
     */
    public static final String URL_GET_DISCOVERY_DATA1 = BASE_HTTPS_URL + "postTicket/prePurchase1";

    /**
     * 立即确认
     */
    public static final String URL_DISCOVERY_PURCHASE = BASE_HTTPS_URL + "postTicket/purchase";

    /**
     * 立即确认
     */
    public static final String URL_DISCOVERY_PURCHASE1 = BASE_HTTPS_URL + "postTicket/purchase1";

    /**
     * 挂单演出详情
     */
    public static final String URL_DISCOVERY_SHOW_DETAIL = BASE_HTTPS_URL + "show/viewForPostTicket";

    /**
     * 淘票 提交订单
     */
    public static final String URL_DISCOVERY_SUBMIT_ORDER = BASE_HTTPS_URL + "postTicket/createOrder";

    /**
     * 团购 提交订单
     */
    public static final String URL_GROUP_BUY_SUBMIT_ORDER = BASE_HTTPS_URL + "activity/groupPurchase";
    /**
     * 淘票 banner
     */
    public static final String URL_DISCOVERY_BANNER = BASE_HTTPS_URL + "activity/load";

    /**
     * 支付成功后调用
     */
    public static final String URL_PAY_SUCCESS = BASE_HTTPS_URL + "order/notifyPayResultForApp";

    /**
     * 现场列表
     */
    public static final String URL_LIVE_LIST = BASE_HTTPS_URL + "show/listForLive";

    /**
     * 现场详情
     */
    public static final String URL_LIVE_DETAIL = BASE_HTTPS_URL + "show/viewForLive";

    /**
     * 创建询价接口，改版
     */
    public static final String URL_ORDER_ENQUIRY1 = BASE_HTTPS_URL + "order/enquiry1";

    /**
     * 创建询价接口
     */
    public static final String URL_ORDER_ENQUIRY = BASE_HTTPS_URL + "order/enquiry";

    /**
     * 轮询扫描
     */
    public static final String URL_SWEEP_ENQUIRY = BASE_HTTPS_URL + "order/getTicketStatus";

    /**
     * 现场轮询扫描
     */
    public static final String URL_LIVE_SWEEP_ENQUIRY = BASE_HTTPS_URL + "order/getLiveTicketStatus";

    /**
     * 选择卖家并生成订单
     */
    public static final String URL_CREATE_ENQUIRY_ORDER = BASE_HTTPS_URL + "order/createEnquiryOrder";

    /**
     * 搜索卖家的时候取消现场订单
     */
    public static final String URL_LIVE_CANCEL_ORDER_WHILE_SEARCHING = BASE_HTTPS_URL + "order/cancelLiveOrder";

    /**
     * 获取抽奖详情
     */
    public static final String URL_LOTTERY_INFO = BASE_HTTPS_URL + "activity/viewLottery";

    /**
     * 获取团购详情
     */
    public static final String URL_GROUP_BUYING = BASE_HTTPS_URL + "activity/viewGroupBuying";

    /**
     * 立即团购
     */
    public static final String URL_GROUP_BUY = BASE_HTTPS_URL + "activity/preGroupPurchase";
    /**
     * 立即抽奖
     */
    public static final String URL_LOTTERY = BASE_HTTPS_URL + "activity/lottery";

    /**
     * 获取拍卖View的数据
     */
    public static final String VIEW_AUCTION = BASE_HTTPS_URL + "activity/viewAuction";

    /**
     * 点击底部，立即竞拍接口
     */
    public static final String PRE_AUCTION = BASE_HTTPS_URL + "activity/preAuction";


    /**
     * 点击Dialog中，立即竞拍接口
     * auction(String activitySid, int payPrice, int totalPrice)
     */
    public static final String AUCTION = BASE_HTTPS_URL + "activity/auction";

    /**
     * 获取首页广告banner
     */
    public static final String URL_GET_ADVERTISEMENT = BASE_HTTPS_URL + "activity/loadAdvertisement";

    /**
     * 关注列表
     */
    public static final String URL_GET_FAVORITES = BASE_HTTPS_URL + "user/showFavorite";

    /**
     * 添加、取消关注
     */
    public static final String URL_MAKE_OR_CANCEL_FAVORITE = BASE_HTTPS_URL + "show/favorite";

    /**
     * 删除订单
     */
    public static final String URL_DELETE_ORDER_BY_USER = BASE_HTTPS_URL + "order/delete";

    /**
     * 获取票价成功率信息
     */
    public static final String URL_GET_QUOTE_AVG = BASE_HTTPS_URL + "order/getQuoteAVG";

    /**
     * 获取用户是否能够约票
     */
    public static final String URL_GET_USER_OPRATION_ENABLE = BASE_HTTPS_URL + "order/invalidForFrequentOperation";

    /**
     * 权证首页
     */
    public static final String URL_GET_WARRANT_LIST = BASE_HTTPS_URL + "warrant/list";

    /**
     * 权证详情
     */
    public static final String URL_GET_WARRANT_DETAIL = BASE_HTTPS_URL + "warrant/load";

    /**
     * 点赞
     */
    public static final String URL_WARRANT_FAVOR = BASE_HTTPS_URL + "warrant/favorite";

    /**
     * 取消点赞
     */
    public static final String URL_WARRANT_CANCEL_FAVOR = BASE_HTTPS_URL + "warrant/cancelFavorite";

    /**
     * 设置交易密码
     */
    public static final String URL_SET_TRADE_CODE = BASE_HTTPS_URL + "user/resetTradingPassword";

    /**
     * 设置交易密码获取验证码
     */
    public static final String URL_SET_TRADE_CODE_GET_VERIFYCODE = BASE_HTTPS_URL + "user/getResetTradingPasswordVerificationCode";















    /**
     * 立即申购
     */
    public static final String URL_WARRANT_APPLY = BASE_HTTPS_URL + "userWarrant/preSubscribe";

    /**
     * 权证，提交订单
     */
    public static final String URL_WARRANT_SUBMIT_ORDER = BASE_HTTPS_URL + "userWarrant/subscribe";

    /**
     * 我的权证
     */
    public static final String URL_MY_WARRANT_LIST = BASE_HTTPS_URL + "userWarrant/list";

    /**
     * 权证明细
     */
    public static final String URL_WARRANT_DETAIL = BASE_HTTPS_URL + "userWarrant/listWarrantInfo";

    /**
     * 出售权证
     */
    public static final String URL_WARRANT_SOLD = BASE_HTTPS_URL + "userWarrant/preSell";

    /**
     * 出售权证
     */
    public static final String URL_WARRANT_SOLD_COMMIT = BASE_HTTPS_URL + "userWarrant/sell";

    /**
     * 撤销按钮
     */
    public static final String URL_WARRANT_DETAIL_REVOKE = BASE_HTTPS_URL + "userWarrant/revoke";

    /**
     * 兑换按钮
     */
    public static final String URL_WARRANT_EXCHANGE = BASE_HTTPS_URL + "userWarrant/preExchange";

    /**
     * 确认兑换
     */
    public static final String URL_WARRANT_EXCHANGE_SUBMIT = BASE_HTTPS_URL + "userWarrant/exchange";

    /**
     * 明细支付
     */
    public static final String URL_WARRANT_DETAIL_PAY= BASE_HTTPS_URL + "userWarrant/prePay";

    /**
     * 兑换详情
     */
    public static final String URL_WARRANT_ORDER_DETAIL = BASE_HTTPS_URL + "userWarrant/viewInfo";

    /**
     * 确认收货
     */
    public static final String URL_WARRANT_ORDER_DETAIL_ENSURE = BASE_HTTPS_URL + "userWarrant/confirmReceived";

    /**
     * 余额支付
     */
    public static final String URL_WARRANT_PAY_BALANCE = BASE_HTTPS_URL + "userWarrant/pay";

    /**
     * 权证买入
     */
    public static final String URL_WARRANT_PURCHASE = BASE_HTTPS_URL + "userWarrant/listTrading";

    /**
     * 购买权证按钮
     */
    public static final String URL_WARRANT_PURCHASE_TO_ORDER = BASE_HTTPS_URL + "userWarrant/prePurchase";

    /**
     * 购买权证按钮
     */
    public static final String URL_WARRANT_PURCHASE_TO_ORDER_SUBMIT = BASE_HTTPS_URL + "userWarrant/purchase";

    /**
     * 权证支付取消
     */
    public static final String URL_WARRANT_CANCEL_PAY = BASE_HTTPS_URL + "userWarrant/cancel";

    /**
     * 我的钱包
     */
    public static final String URL_WALLET = BASE_HTTPS_URL + "balance/viewWallet";

    /**
     * 余额明细
     */
    public static final String URL_BALANCE_DETAIL = BASE_HTTPS_URL + "balance/listBalanceRecords";

    /**
     * 退款明细
     */
    public static final String URL_WITHDRAW_DETAIL = BASE_HTTPS_URL + "balance/listWithdrawRecords";

    /**
     * 提现按钮
     */
    public static final String PRE_WITHDRAW = BASE_HTTPS_URL + "balance/preWithdraw";

    /**
     * 申请提现
     */
    public static final String CASH_WITHDRAW = BASE_HTTPS_URL + "balance/withdraw";

    /**
     * 绑定银行卡
     */
    public static final String SAVE_BACK_CARD = BASE_HTTPS_URL + "balance/saveBankCard";

    /**
     * 明星介绍
     */
    public static final String STAR_INTRODUCE = BASE_HTTPS_URL + "warrant/viewStarInfo";

    /**
     * 主办方详情
     */
    public static final String HOST_DETAIL = BASE_HTTPS_URL + "warrant/viewSponsorInfo";


}
