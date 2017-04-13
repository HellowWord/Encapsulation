/*
 * Copyright (C) 2011 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hellow.xuefeng.hwlibrary.volley.toolbox;


import com.hellow.xuefeng.hwlibrary.debug.L;
import com.hellow.xuefeng.hwlibrary.utils.StreamTool;
import com.hellow.xuefeng.hwlibrary.volley.AuthFailureError;
import com.hellow.xuefeng.hwlibrary.volley.NetworkResponse;
import com.hellow.xuefeng.hwlibrary.volley.Request;
import com.hellow.xuefeng.hwlibrary.volley.Response;
import com.hellow.xuefeng.hwlibrary.volley.Response.ErrorListener;
import com.hellow.xuefeng.hwlibrary.volley.Response.Listener;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.Map;
import java.util.zip.GZIPInputStream;

/**
 * A canned request for retrieving the response body at a given URL as a String.
 */
public class StringRequest extends Request<String> {
    private final Listener<String> mListener;
    private Map<String, String> mParams;
    private Map<String, String> mHeaders;
    //TODO::   add final


    /**
     * Creates a new request with the given method.
     *
     * @param method        the request {@link Method} to use
     * @param url           URL to fetch the string at
     * @param listener      Listener to receive the String response
     * @param errorListener Error listener, or null to ignore errors
     */
    public StringRequest(int method, String url, Listener<String> listener,
                         ErrorListener errorListener) {
        super(method, url, errorListener);
        mListener = listener;
    }

    public StringRequest(int method,String url, Map<String, String> header, Map<String, String> params, Listener<String> listener,
                         ErrorListener errorListener) {
        super(method, url, errorListener);
        mListener = listener;
        mParams = params;
        mHeaders = header;
    }

    private String parseParams(Map<String, String> params) {
        StringBuffer sb = new StringBuffer();
        Iterator<String> keys= params.keySet().iterator();
        while(keys.hasNext()) {
            String key = keys.next().toString();
            String value = params.get(key);
            if (value == null) {
                L.e("API the value of the key:".concat(key).concat(" is null"));
                keys.remove();
                continue;
            }
            sb.append(key).append("=").append(value).append("&");
        }

        return sb.toString();
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return mParams;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return mHeaders;
    }

    @Override
    protected void deliverResponse(String response) {
        mListener.onResponse(response);
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        String responseData = "";
        try {

            //gzip 解压缩
            InputStream inputStream = new ByteArrayInputStream(response.data);
            String charsetName = HttpHeaderParser.parseCharset(response.headers);
            if (charsetName == null) {
                charsetName = "gbk";
            }
            if ("gzip".equals(response.headers.get("Content-Encoding"))) {

                GZIPInputStream gzin = new GZIPInputStream(inputStream);
                responseData = new String(StreamTool.readInputStream(gzin), charsetName);
            } else {
                responseData = new String(StreamTool.readInputStream(inputStream),charsetName);
            }
        } catch (UnsupportedEncodingException e) {
            responseData = new String(response.data);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Response.success(responseData, HttpHeaderParser.parseCacheHeaders(response));
    }
}
