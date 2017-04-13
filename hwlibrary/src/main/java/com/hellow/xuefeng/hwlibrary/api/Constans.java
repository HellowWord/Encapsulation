package com.hellow.xuefeng.hwlibrary.api;


//import com.android.comm.utils.Eutils;
//import com.haodf.android.entity.User;
//import com.haodf.android.utils.Md5;

import com.hellow.xuefeng.hwlibrary.appinfo.AppInfoHelper;
import com.hellow.xuefeng.hwlibrary.utils.FilterMap;

import java.util.HashMap;
import java.util.Map;

/**
 * @FileName: com.hiersun.dmbase.api.APIHelper.java
 * @author: daincly
 * @date: 2014-11-21
 * @description:
 */
@Deprecated
public class Constans {
    public static  String BASE_URL = "http://mobile-api.haodf.com/patientapi/";
    public static  String Old_BASE_URL = "http://mobile-api.haodf.com/mobileapi/";


    //http 请求头
    private static HashMap<String, String> header = new HashMap<String, String>();
    public static HashMap<String, String> getHeader() {
        header.put("User-Agent","haodf_app/1.0");
        header.put("Accept-Encoding","gzip, deflate");
        return header;
    }

    //http 请求公共参数
    public static Map<String, String> getParams() {

        FilterMap<String,String> map = new FilterMap<String,String>();
        AppInfoHelper appInfoHelper = AppInfoHelper.getInstance();
//        map.filterPut("app", AppInfoHelper.APP_NAME);
        map.filterPut("api","1.2");
//        map.filterPut("deviceToken", Eutils.getDeviceId());
//        map.filterPut("userId", User.newInstance().getUserId() + "");
//        map.filterPut("currentUserId", User.newInstance().getUserId() + "");
//        map.filterPut("certificateToken",  User.getInstance().getCertificateToken());
//        //新公共参数
//        map.filterPut("cid", User.getInstance().getUserId() + "");
//        map.filterPut("_t", Md5.getMD5Str(User.newInstance().getUserId() + "c0m.haodf.mobile-api"));
        map.filterPut("m", AppInfoHelper.DEVICE_MODEL); //设备型号
        map.filterPut("os", AppInfoHelper.APP_OS);
        map.filterPut("sv", AppInfoHelper.DEVICE_VERSION);//设备操作系统
        map.filterPut("app", AppInfoHelper.APP_CODE);
        map.filterPut("v", appInfoHelper.getAppVersionName());
        map.filterPut("s", appInfoHelper.getMetaInfo("UMENG_CHANNEL", "hd"));
        map.filterPut("n", appInfoHelper.getNetType());//网络状态，1=蜂窝网，2=WIFI
        map.filterPut("p", appInfoHelper.getCellularType());//蜂窝网络提供商；0=无sim卡，1=移动、2=联通、3=电信 4=其他
        map.filterPut("di", appInfoHelper.getDeviceIMEI());//设备唯一标示符
        return map;
    }

}
