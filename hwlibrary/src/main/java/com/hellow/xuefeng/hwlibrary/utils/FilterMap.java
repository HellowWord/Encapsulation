package com.hellow.xuefeng.hwlibrary.utils;

import com.hellow.xuefeng.hwlibrary.debug.L;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @param <K>
 * @param <V>
 */
public class FilterMap<K, V> extends HashMap<K,V>{

    /**
     * filterPut function
     * @param key
     * @param value
     * @return
     */
    public Map<K, V> filterPut(K key, V value) {
        if (key == null || value == null || key.equals("") || value.equals("")){
            L.e("key or value is null");
            return this;
        } else {
            super.put(key,value);
        }
        return this;
    }
}
