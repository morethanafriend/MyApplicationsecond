package com.example.administrator.myapplication;

/**
 * Created by Administrator on 2016/9/22.
 */
public class UpdateModel {


    /**
     * code : 2000
     * data : {"appClient":{"channelName":"91","clientDescription":"新版本上线","clientName":"USER","clientStatus":2,"clientType":"ANDROID","clientVersion":120,"createdDate":"2015-11-24 16:40:21","fileName":"11","sid":"123","type":"1","updateType":"optional","updateUrl":"http://112.65.222.35/dd.myapp.com/16891/2053DF3FD0C4989E38B1B8FDE966EC5D.apk?mkey=562f481f8b86405a&f=b088&fsname=cn.piaofun.broker_1.0_1.apk&asr=02f1&p=.apk"},"updateCode":"1"}
     * extraData : {}
     * message : 已有新版本，请前往更新
     * success : true
     */

    public String code;
    /**
     * appClient : {"channelName":"91","clientDescription":"新版本上线","clientName":"USER","clientStatus":2,"clientType":"ANDROID","clientVersion":120,"createdDate":"2015-11-24 16:40:21","fileName":"11","sid":"123","type":"1","updateType":"optional","updateUrl":"http://112.65.222.35/dd.myapp.com/16891/2053DF3FD0C4989E38B1B8FDE966EC5D.apk?mkey=562f481f8b86405a&f=b088&fsname=cn.piaofun.broker_1.0_1.apk&asr=02f1&p=.apk"}
     * updateCode : 1
     */

    public DataEntity data;
    public String message;
    public boolean success;

    public static class DataEntity {
        /**
         * channelName : 91
         * clientDescription : 新版本上线
         * clientName : USER
         * clientStatus : 2
         * clientType : ANDROID
         * clientVersion : 120
         * createdDate : 2015-11-24 16:40:21
         * fileName : 11
         * sid : 123
         * type : 1
         * updateType : optional
         * updateUrl : http://112.65.222.35/dd.myapp.com/16891/2053DF3FD0C4989E38B1B8FDE966EC5D.apk?mkey=562f481f8b86405a&f=b088&fsname=cn.piaofun.broker_1.0_1.apk&asr=02f1&p=.apk
         */

        public AppClientEntity appClient;
        public String updateCode;

        public static class AppClientEntity {
            public String channelName;
            public String clientDescription;
            public String clientName;
            public int clientStatus;
            public String clientType;
            public int clientVersion;
            public String createdDate;
            public String fileName;
            public String sid;
            public String type;
            public String updateType;
            public String updateUrl;
        }
    }
}