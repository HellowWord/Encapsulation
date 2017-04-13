package com.hellow.xuefeng.hwlibrary.storage;



import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


/**
 * @FileName: com.hiersun.dmbase.storage.SimpleStorage.java
 * @author: daincly
 * @date: 2015-12-12 16:56
 * @description: <替代系统SharedPreferences>
 */
public class SimpleStorage {

    private static final String TAG = "SimpleStorage";
    private String mPath, mStorageName;
    private Properties mProperties;

    public SimpleStorage(String path, String storageName) {
        mPath = path;
        mStorageName = storageName;
        mProperties = getProperty();
    }

    private Properties getProperty() {
        if (mProperties != null) {
            return mProperties;
        }
        Properties mProperties = new Properties();
        InputStream in = null;
        try {
            in = StorageUtils.getFileIN(mPath, mStorageName);
            mProperties.load(in);
        } catch (IOException e) {
            mProperties = null;
            e.printStackTrace();
        }
        if (in != null) {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            in = null;
        }
        return mProperties;
    }

    /**
     * 获取key下保存的defValue
     *
     * @param key
     * @param defValue
     * @return
     */
    public String getString(String key, String defValue) {
        if (key == null || key.equals("")) {
            return defValue;
        }
        Properties p = getProperty();

        String str = p.getProperty(key, defValue);
        return str;
    }


    public boolean getBoolean(String key, boolean defValue) {
        return Boolean.parseBoolean(getString(key, String.valueOf(defValue)));
    }


    public int getInt(String key, Integer defValue) {
        return Integer.parseInt(getString(key, String.valueOf(defValue)));
    }


    public float getFloat(String key, float defValue) {
        String tmpStr = getString(key, String.valueOf(defValue));
        return Float.parseFloat(tmpStr);
    }

    public long getLong(String key, long defValue) {
        String tmpStr = getString(key, String.valueOf(defValue));
        return Long.parseLong(tmpStr);
    }


    public Properties putString(String key, String value) {
        Properties p = getProperty();
        if (key == null || key.equals("") || value == null || value.equals("")) {
            return p;
        }
        p.put(key, value);
        return p;
    }

    public Properties putBoolean(String key, boolean value) {
        return putString(key, String.valueOf(value));
    }

    public Properties putInt(String key, Integer value) {
        return putString(key, String.valueOf(value));
    }

    public Properties putFloat(String key, Float value) {
        return putString(key, String.valueOf(value));
    }

    public Properties putLong(String key, long value) {
        return putString(key, String.valueOf(value));
    }

    /**
     * 是否包含该key
     *
     * @param key
     * @return
     */
    public boolean hasKey(String key) {
        if (key == null || key.equals("")) {
            return false;
        }
        return getProperty().containsKey(key);
    }

    /**
     * 移除键值
     *
     * @param key
     */
    public void remove(String key) {
        if (key == null || key.equals("")) {
            return;
        }
        getProperty().remove(key);
    }

    /**
     * 提交，在put value过后必须调用 getConfigName()，由于可能之前没有uid，所以这里不能一开始就初始化configname,
     * 需要后续过程中再次获取configname
     */
    public void commit() {
        FileOutputStream fos = null;
        Properties p = getProperty();
        try {
            synchronized (mProperties) {
                fos = StorageUtils.getFileOS(mPath, mStorageName);
                p.store(fos, "");
            }
        } catch (FileNotFoundException ex) {
        } catch (IOException ex) {
        } finally {
            if (null != fos) {
                try {
                    fos.close();
                } catch (IOException ex) {
                }
            }
            fos = null;
        }
    }

}
