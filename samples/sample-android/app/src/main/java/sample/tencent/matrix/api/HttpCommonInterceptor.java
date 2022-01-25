package sample.tencent.matrix.api;


import android.content.Context;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.Request;
import sample.tencent.matrix.MatrixApplication;

/**
 * Created by zhouds
 * Created time  2018/11/5.
 * Description: 自定义拦截器、添加请求头
 * Version: V 1.0
 */
public class HttpCommonInterceptor implements Interceptor {
    private Map<String, String> mHeaderParamsMap = new HashMap<>();

    public HttpCommonInterceptor() {
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request oldRequest = chain.request();
        // 新的请求
         Request.Builder requestBuilder = oldRequest.newBuilder();
         requestBuilder.method(oldRequest.method(), oldRequest.body());

//        if(CommonModule.getAuthorization() == null || CommonModule.getAuthorization().isEmpty()){
//            LogUtil.e("!!!!!!!!!!!登录令牌丢失，请重新登录!!!!!!!!!!");
//            ARouter.getInstance()
//                    .build("/app/Login")
//                    .navigation();
//
//        }else{
//            requestBuilder.addHeader("Authorization", CommonModule.getAuthorization());//.addHeader("csd",CommonModule.getCsd());
//        }
        //requestBuilder.addHeader("Authorization", "Bearer 914ef804-bce6-4f8b-9d4f-6625c985279f");
        final String token = MatrixApplication.getContext().getSharedPreferences("CACHE_DATA", Context.MODE_PRIVATE)
                .getString("TOKEN","");
        if(token !=null && !token.isEmpty()){
            requestBuilder.addHeader("Authorization", token);
        }else{
            requestBuilder.addHeader("Authorization", "Basic YXBtOmh1bHU=");
        }
        requestBuilder.addHeader("Connection", "close");
        // 添加公共参数,添加到header中
        if (mHeaderParamsMap.size() > 0) {
            for (Map.Entry<String, String> params : mHeaderParamsMap.entrySet()) {
                requestBuilder.header(params.getKey(), params.getValue());
            }
        }
        Request newRequest = requestBuilder.build();
        return chain.proceed(newRequest);
    }

    public static class Builder {
        HttpCommonInterceptor mHttpCommonInterceptor;

        public Builder() {
            mHttpCommonInterceptor = new HttpCommonInterceptor();
        }

        public Builder addHeaderParams(String key, String value) {
            mHttpCommonInterceptor.mHeaderParamsMap.put(key, value);
            return this;
        }

        public Builder addHeaderParams(String key, int value) {
            return addHeaderParams(key, String.valueOf(value));
        }

        public Builder addHeaderParams(String key, float value) {
            return addHeaderParams(key, String.valueOf(value));
        }

        public Builder addHeaderParams(String key, long value) {
            return addHeaderParams(key, String.valueOf(value));
        }

        public Builder addHeaderParams(String key, double value) {
            return addHeaderParams(key, String.valueOf(value));
        }

        public HttpCommonInterceptor build() {
            return mHttpCommonInterceptor;
        }
    }
}