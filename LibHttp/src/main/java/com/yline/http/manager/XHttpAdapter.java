package com.yline.http.manager;

import com.yline.http.controller.HttpDefaultResponse;
import com.yline.http.controller.ResponseHandlerCallback;
import com.yline.http.controller.ResponseHandlerConfigCallback;
import com.yline.http.controller.ResponseMethodCallback;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 配置每次的请求参数
 */
public abstract class XHttpAdapter<Result> implements ResponseHandlerConfigCallback, ResponseMethodCallback<Result> {
    // 默认成功的Code
    public static final int REQUEST_SUCCESS_CODE = 0;

    @Override
    public abstract void onSuccess(Call call, Response response, Result result);

    @Override
    public void onFailure(Call call, Exception ex) {
        String content = String.format("onFailure, tag{method=%s, url=%s, tag=%s}, exception", call.request().method(), call.request().url(), call.request().tag());
        LibManager.eRequest(content, ex);
    }

    @Override
    public ResponseHandlerCallback getRequestHandler() {
        return new HttpDefaultResponse(this);
    }

    @Override
    public boolean isResponseHandler() {
        return true;
    }

    @Override
    public boolean isResponseCache() {
        return true;
    }
}
