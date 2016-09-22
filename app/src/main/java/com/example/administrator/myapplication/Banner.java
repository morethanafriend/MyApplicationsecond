package com.example.administrator.myapplication;


import java.io.Serializable;
import java.util.List;

/**
 * Created by zhangll on 15/12/4.
 */
public class Banner implements Serializable {

    public String beginDatetime;
    public int bidding;
    public String brokerName;
    public String brokerSid;
    public int currentMaxPrice;
    public String endDatetime;
    public String updatedDatetime;
    public int maxPrice;
    public String postTicketSid;
    public int reservePrice;


    public String showSid;
    public String showName;
    public String activityBucketName;
    public String activityKey;
    public String createdDatetime;
    public String sid;

    public String coverImageUrl;
    public String advertisementImageUrl;
    public String captionImageUrl;

    public String activityType;
    public String activityRule;
    public String content;
    public String name;
    public long money;
    public long ticketPrice;
    public int playerQuantity;
    public long durationToBegin;//>0 ,活动没有开始；==0 活动开始，
    public long durationToEnd; //> 0 活动还没结束， == 0 活动结束
    public boolean enableLottery;
    public boolean enableGroupBuying;
    public List<Player> winPlayers;
    public List<Picture> activityPictures;

    //yang
    public long soldQuantity;

    public boolean isRequestTicketType() {
        return OrderType.REQUEST_TICKET.equals(activityType);
    }

    public boolean isPostTicketType() {
        return OrderType.POST_TICKET.equals(activityType);
    }

    public boolean isLiveType() {
        return OrderType.LIVE.equals(activityType);
    }

    public boolean isLotteryType() {
        return OrderType.LOTTERY.equals(activityType);
    }

    public boolean isAuctionType() {
        return OrderType.AUCTION.equals(activityType);
    }

    public boolean isGroupBuyType() {
        return OrderType.GROUP_BUYING.equals(activityType);
    }

    public boolean isLotteryFinished() {
        return winPlayers != null && !winPlayers.isEmpty();
    }

    public String getAdvertisement() {
        return advertisementImageUrl;
    }

    public String getCaption() {
        return captionImageUrl;
    }

    public String getCover() {
        return coverImageUrl;
    }

    public class Picture implements Serializable {
        public String activitySid;
        public String picBucketName;
        public String picKey;
        public String picImageUrl;
        public String sid;

        public String getPicUrl() {
            return picImageUrl;
        }
    }
}
