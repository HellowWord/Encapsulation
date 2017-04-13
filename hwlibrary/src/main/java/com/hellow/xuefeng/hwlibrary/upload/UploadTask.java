package com.hellow.xuefeng.hwlibrary.upload;

import android.os.Bundle;
import android.os.Message;

import com.hellow.xuefeng.hwlibrary.app.BaseHandler;
import com.hellow.xuefeng.hwlibrary.debug.L;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;

import java.net.URL;
import java.util.Map;
import java.util.UUID;



/**
 * @FileName: com.hiersun.dmbase.upload.UploadTask.java
 * @author: daincly
 * @date: 2015-12-17 19:04
 * @description: <功能>
 */
public class UploadTask implements Runnable {
    private final static String TAG = "UploadTask";
    private final static int CONNECT_TIME_OUT = 10 * 1000;
    private final static int READ_TIME_OUT = 30 * 1000;
    private final static String CHARSET = "utf-8";// 编码
    private final static String PREFIX = "--";
    private final static String LINE_END = "\r\n";
    private final static String CONTENT_TYPE = "multipart/form-data"; // 内容类型
    private final static String BOUNDARY = UUID.randomUUID().toString(); // 边界标识 随机生成
    private final static int FILE_READ_SIZE = 1024;
    private UploadRequest mRequest;
    private String mUrl;
    private UploadHandler mHandler;

    public UploadTask(String url, UploadRequest request) {
        mUrl = url;
        mRequest = request;
        mHandler = new UploadHandler(this);
    }


    @Override
    public void run() {
        if (mRequest == null || mRequest.getFile() == null) {
            L.e("UploadRequest is null !");
            sendResponseMessage(UploadRequest.RESPONSE_CODE_APP_EXCEPTION, "", "");
            return;
        }

        URL url = null;
        try {
            url = new URL(mUrl);
        } catch (MalformedURLException e) {
            sendResponseMessage(UploadRequest.RESPONSE_CODE_APP_EXCEPTION, "", "");
            e.printStackTrace();
            return;
        }
        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
        } catch (IOException e) {
            e.printStackTrace();
            sendResponseMessage(UploadRequest.RESPONSE_CODE_APP_EXCEPTION, "", "");
            return;
        }


        conn.setConnectTimeout(CONNECT_TIME_OUT);
        conn.setReadTimeout(READ_TIME_OUT);
        conn.setDoInput(true); // 允许输入流
        conn.setDoOutput(true); // 允许输出流
        conn.setUseCaches(false); // 不允许使用缓存
        conn.setRequestProperty("Charset", CHARSET); // 设置编码
        conn.setRequestProperty("connection", "keep-alive");
        conn.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary="
                + BOUNDARY);
        for (Map.Entry<String, String> entry : mRequest.getHeader().entrySet()) {
            conn.setRequestProperty(entry.getKey(), entry.getValue());
        }

        if (mRequest.isCancel) {
            return;
        }
        OutputStream outputSteam = null;
        try {
            outputSteam = conn.getOutputStream();
        } catch (IOException e) {
            sendResponseMessage(UploadRequest.RESPONSE_CODE_APP_EXCEPTION, "", "");
            e.printStackTrace();
            return;
        }

        // -------- 包装 multipart
        StringBuffer sb = new StringBuffer();
        sb.append(PREFIX);
        sb.append(BOUNDARY);
        sb.append(LINE_END);
        sb.append("Content-Disposition: form-data; name=\"img\"; filename=\""
                + mRequest.getFile().getName() + "\"" + LINE_END);
        sb.append("Content-Type: application/octet-stream; charset="
                + CHARSET + LINE_END);
        sb.append(LINE_END);
        byte[] start_data = sb.toString().getBytes();
        // ---------
        DataOutputStream dos = new DataOutputStream(outputSteam);
        InputStream is = null;
        int totalSize = 0;
        if (mRequest.isCancel) {
            return;
        }
        try {
            dos.write(start_data);
            is = new FileInputStream(mRequest.getFile());
            totalSize = is.available();
            byte[] bytes = new byte[FILE_READ_SIZE];
            int len = 0;
            while ((len = is.read(bytes)) != -1) {
                int uploadSize = totalSize - is.available();
                sendProgressMessage(uploadSize, totalSize);
                if (mRequest.isCancel) {
                    return;
                }
                dos.write(bytes, 0, len);
            }
            dos.write(LINE_END.getBytes());
            byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINE_END)
                    .getBytes();
            dos.write(end_data);
            dos.flush();
        } catch (IOException e) {
            sendResponseMessage(UploadRequest.RESPONSE_CODE_APP_EXCEPTION, "", "");
            e.printStackTrace();
            return;
        } finally {
            if (dos != null) {
                try {
                    if (dos != null) {
                        dos.close();
                    }
                    if (is != null) {
                        is.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }

            }
        }
        sendProgressMessage(totalSize, totalSize);
        if (mRequest.isCancel) {
            return;
        }
        // ---------接收
        int res = 0;
        String msg = null;
        try {
            res = conn.getResponseCode();
            msg = conn.getResponseMessage();
        } catch (IOException e) {
            sendResponseMessage(UploadRequest.RESPONSE_CODE_APP_EXCEPTION, "", "");
            e.printStackTrace();
        }
        if (res != 200) {
            sendResponseMessage(res, msg, "");
            return;
        }
        InputStream pis = null;
        BufferedReader reader = null;
        StringBuffer psb = new StringBuffer();
        String curLine = null;
        try {
            pis = conn.getInputStream();
            reader = new BufferedReader(new InputStreamReader(pis, "UTF8"));
            while ((curLine = reader.readLine()) != null) {
                if (mRequest.isCancel) {
                    return;
                }
                psb.append(curLine);
            }
        } catch (IOException e) {
            sendResponseMessage(UploadRequest.RESPONSE_CODE_APP_EXCEPTION, "", "");
            e.printStackTrace();
        } finally {
            try {
                if (pis != null) {
                    pis.close();
                }
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String str = psb.toString();
        sendResponseMessage(0, "", str);
    }

    private void sendResponseMessage(int errorCode, String errorMsg, String response) {
        Message msg = UploadHandler.getMessage();
        msg.what = 0xFF;
        Bundle bundle = new Bundle();
        bundle.putInt("errorCode", errorCode);
        bundle.putString("errorMsg", errorMsg);
        bundle.putString("response", response);
        msg.setData(bundle);
        mHandler.sendMessage(msg);
    }

    private void sendProgressMessage(int uploadSize,int totalSize){
        Message msg = UploadHandler.getMessage();
        msg.what = 0xF0;
        msg.arg1 = uploadSize;
        msg.arg2 = totalSize;
        mHandler.sendMessage(msg);
    }

    private class UploadHandler extends BaseHandler<UploadTask> {

        public UploadHandler(UploadTask reference) {
            super(reference);
        }

        @Override
        protected void handleMessage(UploadTask reference, Message msg) {
            switch (msg.what){
                case 0xF0:
                    reference. mRequest.onProgress(msg.arg1, msg.arg2);
                    break;
                case 0xFF:
                    int errorCode = msg.getData().getInt("errorCode");
                    String errorMsg = msg.getData().getString("errorMsg");
                    String response = msg.getData().getString("response");
                    if (errorCode != 0) {
                        reference.mRequest.onBaseErrorResponse(errorCode, errorMsg);
                    } else {
                        reference.mRequest.onBaseResponse(response);
                    }
                    break;
            }

        }
    }

}
