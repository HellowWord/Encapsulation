package com.hellow.xuefeng.hwlibrary.appinfo;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.hellow.xuefeng.hwlibrary.app.BaseApplication;
import com.hellow.xuefeng.hwlibrary.debug.L;

import java.util.UUID;

/**
 * Created by daincly on 2014/10/28.
 */
public class AppInfoHelper  {

    private static final String TAG = "AppInfoHelper";

    public static final String META_DATA_CHANNEL = "HaoDF_Channel";

    public static final String META_DATA_IS_DEBUG = "HaoDF_Is_Debug";
    /**
     * 客户端apk文件格式
     */
    public static final String UPDATE_FILE_FORMAT = ".apk";


    public static final String APP_PACKAGE_PATH = "com.haodf.android.doctor";

    /**
     * 客户端文件名
     */
    public static final String APP_NAME = "haodf";

    /**
     * 客户端适用于系统 android
     */
    public static final String APP_OS = "android";

    /**
     * 客户端 代号
     */
    public static final String APP_CODE = "p";

    /**
     * 设备系统版本号
     */
    public static final String DEVICE_VERSION = android.os.Build.VERSION.RELEASE;

    /**
     * 手机型号
     */
    public static final String DEVICE_MODEL = android.os.Build.MODEL;

    /**
     * WIFI网络
     */
    public static final String NET_TYPE_WIFI = "2";

    /**
     * 蜂窝网络
     */
    public static final String NET_TYPE_CELLULAR = "1";

    /**
     * 无sim 卡
     */
    public static final String CELLULAR_TYPE_NO = "0";

    /**
     * 移动蜂窝
     */
    public static final String CELLULAR_TYPE_CM = "1";

    /**
     * 联通蜂窝
     */
    public static final String CELLULAR_TYPE_CN = "2";

    /**
     * 电信蜂窝
     */
    public static final String CELLULAR_TYPE_CT = "3";

    /**
     * 其他蜂窝
     */
    public static final String CELLULAR_TYPE_OT = "4";

    private static AppInfoHelper instance;
    private Context mContext;



    public static AppInfoHelper getInstance() {
        if (instance == null) {
            synchronized (AppInfoHelper.class) {
                instance = new AppInfoHelper();
            }
        }
        return instance;
    }


    private AppInfoHelper() {

        mContext = BaseApplication.getInstance().getApplicationContext();
    }






    /**
     * 查询手机内应用
     *
     * @return
     */
//    public List<PackageInfo> getAllApps() {
//        PackageManager pManager = BaseApplication.getAppContext().getPackageManager();
//        //获取手机内所有应用
//        List<PackageInfo> pakList = pManager.getInstalledPackages(0);
//        return pakList;
//    }

    /**
     * 查询当前程序apk名称
     *
     * @return
     */
    public String getApplicationName() {
        PackageManager pManager = mContext.getPackageManager();
        ApplicationInfo appInfo = null;
        String appName = "";
        try {
            appInfo = pManager.getApplicationInfo(mContext.getPackageName(), PackageManager.GET_UNINSTALLED_PACKAGES);
        } catch (PackageManager.NameNotFoundException e) {
        }
        if (appInfo != null) {
            appName = pManager.getApplicationLabel(appInfo).toString();
        }
        String apkName = appInfo.sourceDir;
//        String apkName1 = appInfo.publicSourceDir;
        return appName;
    }

    /**
     * 查询当前程序包名称
     *
     * @return
     */
    public String getApplicationPackageName() {
        PackageManager pManager = mContext.getPackageManager();
        ApplicationInfo appInfo = null;
        String appName = "";
        try {
            appInfo = pManager.getApplicationInfo(mContext.getPackageName(), PackageManager.GET_UNINSTALLED_PACKAGES);
        } catch (PackageManager.NameNotFoundException e) {
        }
        if (appInfo != null) {
            appName = pManager.getApplicationLabel(appInfo).toString();
        }
        String apkName = appInfo.packageName;
        return appName;
    }

    /**
     * 查询当前程序安装路径
     *
     * @return
     */
    public String getApplicationSourceDir() {
        PackageManager pManager = mContext.getPackageManager();
        ApplicationInfo appInfo = null;
        String sourceDir = "";
        try {
            appInfo = pManager.getApplicationInfo(mContext.getPackageName(), PackageManager.GET_UNINSTALLED_PACKAGES);
        } catch (PackageManager.NameNotFoundException e) {
            L.e(e + "");
        }
        if (appInfo != null) {
            sourceDir = appInfo.sourceDir;
        }
        return sourceDir;
    }


    /**
     * Manifest中meta_data的字符串信息
     *
     * @param metaKey
     * @return
     */
    public String getMetaInfo(String metaKey, String defaultValue) {
        PackageManager pManager = mContext.getPackageManager();
        ApplicationInfo appInfo = null;
        String msg = defaultValue;
        try {
            appInfo = pManager.getApplicationInfo(mContext.getPackageName(), PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            return msg;
        }
        if (appInfo != null && appInfo.metaData != null) {
            if (appInfo.metaData.get(metaKey) != null) {
                if (!TextUtils.isEmpty(appInfo.metaData.get(metaKey).toString())) {
                    msg = appInfo.metaData.get(metaKey).toString();
                }
            }
        }
        return msg;
    }

    /**
     * Manifest中meta_data的布尔值信息
     *
     * @param metaKey
     * @return
     */
    public boolean getMetaInfo(String metaKey, boolean defaultValue) {
        PackageManager pManager = mContext.getPackageManager();
        ApplicationInfo appInfo = null;
        boolean result = defaultValue;
        try {
            appInfo = pManager.getApplicationInfo(mContext.getPackageName(), PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            return result;
        }
        if (appInfo != null && appInfo.metaData != null) {
            result = appInfo.metaData.getBoolean(metaKey);
        }
        return result;
    }

    /**
     * 获取客户端versionName
     */
    public String getAppVersionName() {
        String versionName = "";
        try {
            PackageManager pManager = mContext.getPackageManager();
            PackageInfo pi;
            if (pManager != null) {
                pi = pManager.getPackageInfo(mContext.getPackageName(), 0);
                versionName = pi.versionName;
            }
            if (TextUtils.isEmpty(versionName)) {
                return "";
            }
        } catch (Exception e) {
        }
        return versionName;
    }

    /**
     * 获取客户端versionCode
     */
    public int getAppVersionCode() {
        int versionCode = 0;
        try {
            PackageManager pManager = mContext.getPackageManager();
            PackageInfo pi;
            if (pManager != null) {
                pi = pManager.getPackageInfo(mContext.getPackageName(), 0);
                versionCode = pi.versionCode;
            }
        } catch (Exception e) {
        }
        return versionCode;
    }

    /**
     * 获取手机串号IMEI
     *
     * @return
     */
    public String getDeviceIMEI() {
        TelephonyManager tm = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
        String deviceId=null;
        try {
            deviceId = tm.getDeviceId();
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return deviceId == null || deviceId.length() == 0 ? UUID.randomUUID().toString() : tm.getDeviceId();
    }

    /**
     * 获取网络状态  蜂窝网 or WIFI
     * 1=蜂窝网，2=WIFI
     * @return
     */
    public String getNetType() {
        if (isMobileConnected()) {
            return NET_TYPE_CELLULAR;
        } else if (isWifiConnected()) {
            return NET_TYPE_WIFI;
        }
        return null;
    }


    /**
     * 判断蜂窝网络是否连接
     * @return
     */
    public boolean isMobileConnected() {
        ConnectivityManager mConnectivityManager = (ConnectivityManager) mContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mMobileNetworkInfo = mConnectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (mMobileNetworkInfo != null) {
            return mMobileNetworkInfo.isConnected();
        }
        return false;
    }

    /**
     * 判断WIFI网络是否链接
     * @return
     */
    public boolean isWifiConnected() {
        ConnectivityManager mConnectivityManager = (ConnectivityManager) mContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mWiFiNetworkInfo = mConnectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (mWiFiNetworkInfo != null) {
            return mWiFiNetworkInfo.isConnected();
        }
        return false;
    }

    /**
     * 获取蜂窝网络提供商
     *
     * @return
     */
    public String getCellularType() {
        TelephonyManager telManager = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
        String operator = telManager.getSimOperator();
        if (operator != null) {

            if (operator.equals("46000") || operator.equals("46002") || operator.equals("46007")) {
                //中国移动
                return CELLULAR_TYPE_CM;
            } else if (operator.equals("46001")) {
                //中国联通
                return CELLULAR_TYPE_CN;
            } else if (operator.equals("46003")) {
                //中国电信
                return CELLULAR_TYPE_CT;
            } else {
                return CELLULAR_TYPE_OT;
            }
        }
        return null;
    }


}
