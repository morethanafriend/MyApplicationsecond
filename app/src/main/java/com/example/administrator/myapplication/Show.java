package com.example.administrator.myapplication;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/22.
 */
public class Show implements Serializable {
    public boolean favorable;
    public String cityCode;
    public int soldQuantity;
    public int ticketQuantity;
    /**
     * city : 上海市
     * cover : show/getPoster?key=fbd5cdb6ca8a401791e1365014800b8f/fbd5cdb6ca8a401791e1365014800b8f
     * createdDatetime : 2015-10-11 21:33:13
     * name : 2015中国足球协会超级联赛-上海上港集团队 主场
     * minPrice : 50.00起
     * putawayDatetime : 2015-10-12 10:39:54
     * remark :
     * showScheduleDesp : 2015-10-17 19:45:00
     * sid : fbd5cdb6ca8a401791e1365014800b8f
     * sortNumber : 1
     * content : 1
     * type : sports
     * venueAddress : 地铁4号线
     * venueName : 上海体育场
     * venueSid : aa2810a2b31d4c3ba85e3f16a27bda81
     */

    public String type;
    public String venueAddress;
    public String venueName;

    public String cityName;
    public String coverImageUrl;
    public String name;
    public long minPrice = -100;
    public long minPostingPrice = -1;
    public String remark;
    public String showScheduleDesp;
    public String showScheduleRange;
    public String sid;
//    public Venue venue;
//    public List<Schedule> showSchedules = new ArrayList<>();
//    public List<Ticket> tickets = new ArrayList<>();
    public List<Poster> showPictures = new ArrayList<>();
    public String seatLocationImageUrl;

    //yang hot/live/discount
    public boolean isHot;
    public boolean live;
    public boolean isOnSales;
    public String discountText;

    public boolean hasSeatLocationPic() {
        return !StringUtil.isNull(seatLocationImageUrl);
    }

    public boolean isSoldOut() {
        return ticketQuantity <= 0;
    }

    public String getRangeTime() {
        return StringUtil.isNull(showScheduleRange) ? showScheduleDesp : showScheduleRange;
    }

    public long getMinPostingPrice() {
        return minPostingPrice < 0 ? minPrice : minPostingPrice;
    }

    public int getSellingQuantity() {
        int sellingQuantity = ticketQuantity;//ticketQuantity - preSellQuantity - soldQuantity
        return sellingQuantity < 0 ? 0 : sellingQuantity;
    }

    public static class Poster implements Serializable {
        public String posterImageUrl;
        public String showSid;
        public String sid;
    }
}
