package com.lib.http.demo.download;

import android.text.TextUtils;

import com.yline.http.controller.HttpDefaultHandler;
import com.yline.http.controller.ResponseHandlerCallback;
import com.yline.http.controller.ResponseHandlerConfigCallback;
import com.yline.http.controller.ResponseMethodCallback;
import com.yline.http.manager.LibManager;
import com.yline.http.manager.XHttpAdapter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 用于文件下载
 *
 * @author yline 2017/11/24 -- 19:46
 * @version 1.0.0
 */
public class HttpDownloadResponse implements ResponseHandlerCallback {
    protected ResponseHandlerConfigCallback mResponseConfig;
    protected ResponseMethodCallback mResponseCallback;
    private HttpDefaultHandler mHttpHandler;

    public HttpDownloadResponse(XHttpAdapter adapter) {
        this.mResponseCallback = adapter;
        this.mResponseConfig = adapter;

        if (mResponseConfig.isResponseHandler()) {
            mHttpHandler = HttpDefaultHandler.build();
        }
    }

    @Override
    public <T> void handleSuccess(Call call, Response response, Class<T> clazz) throws IOException {
        Object tagModel = call.request().tag();
        LibManager.vRequest("download response = " + tagModel);
        if (tagModel instanceof DownloadModel) {
            File storeFile = judgeStoreFile(((DownloadModel) tagModel).getStoreDirPath(), ((DownloadModel) tagModel).getStoreFileName(), true);
            if (null != storeFile) {
                InputStream inputStream = response.body().byteStream();
                FileOutputStream fileOutputStream = new FileOutputStream(storeFile);

                int len;
                byte[] buffer = new byte[2048];
                while ((len = inputStream.read(buffer)) != -1) {
                    fileOutputStream.write(buffer, 0, len);
                }

                fileOutputStream.close();

                sendSuccess(call, response, null);
            } else {
                sendFailure(call, new IOException("传入的数据错误，储存文件为空"));
            }
        } else {
            sendFailure(call, new IOException("传入的数据类型错误，暂不支持该数据类型的文件下载"));
        }
    }

    @Override
    public <T> void handleFailure(Call call, final IOException ex) {
        sendFailure(call, ex);
    }


    private <T> void sendSuccess(final Call call, final Response response, final T result) {
        if (mResponseConfig.isResponseHandler()) {
            mHttpHandler.post(new Runnable() {
                @Override
                public void run() {
                    mResponseCallback.onSuccess(call, response, result);
                }
            });
        } else {
            mResponseCallback.onSuccess(call, response, result);
        }
    }

    private <T> void sendFailure(final Call call, final Exception ex) {
        if (mResponseConfig.isResponseHandler()) {
            mHttpHandler.post(new Runnable() {
                @Override
                public void run() {
                    mResponseCallback.onFailure(call, ex);
                }
            });
        } else {
            mResponseCallback.onFailure(call, ex);
        }
    }

    private File judgeStoreFile(String dirPath, String fileName, boolean isResetIfExist) {
        if (TextUtils.isEmpty(dirPath) || TextUtils.isEmpty(fileName)) {
            LibManager.eRequest("dir:" + dirPath + ",fileName:" + fileName, new Exception("saveStoreFile dirPath or fileName is null"));
            return null;
        }

        // 父目录
        File storeDir = new File(dirPath);
        if (!storeDir.exists()) {
            if (!storeDir.mkdir()) {
                LibManager.eRequest("", new Exception("saveStoreFile storeDir create failed"));
                return null;
            }
        }

        // 文件
        File storeFile = new File(storeDir, fileName);
        if (!storeFile.exists()) {
            try {
                if (storeFile.createNewFile()) {
                    return storeFile;
                }
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        if (isResetIfExist) {
            try {
                FileWriter fileWriter = new FileWriter(storeFile);
                fileWriter.write("");
                fileWriter.flush();
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        return storeFile;
    }
}
