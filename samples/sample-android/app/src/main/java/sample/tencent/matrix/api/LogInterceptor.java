package sample.tencent.matrix.api;

import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;

public class LogInterceptor implements Interceptor {

    public static String TAG = LogInterceptor.class.getSimpleName();

    private static HashMap<String,String> headerIgnoreMap = new HashMap<>();

    static {
        headerIgnoreMap.put("Host", "");
        headerIgnoreMap.put("Connection", "");
        headerIgnoreMap.put("Accept-Encoding", "");
    }
    private Level level;
    public LogInterceptor(Level level){
        this.level = level;
    }

    protected void log(String message) {
        Log.d(TAG, message);
    }

    private boolean isPlainText(MediaType mediaType) {
        if (null != mediaType) {
            String mediaTypeString = (null != mediaType ? mediaType.toString() : null);
            if (!TextUtils.isEmpty(mediaTypeString)) {
                mediaTypeString = mediaTypeString.toLowerCase();
                if (mediaTypeString.contains("text") || mediaTypeString.contains("application/json")) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public okhttp3.Response intercept(Chain chain) throws IOException {
        if(Level.NONE.equals(level)){
            return chain.proceed(chain.request());
        }
        Request request = chain.request();

        long startTime = SystemClock.elapsedRealtime();
        okhttp3.Response response = chain.proceed(chain.request());
        long endTime = SystemClock.elapsedRealtime();
        long duration = endTime - startTime;


        //url
        String url = request.url().toString();
        log("----------Request Start----------");
        log(String.format("%s %s", request.method(), url));

        //headers
        Headers headers = request.headers();
        if (null != headers) {
            for (int i = 0, count = headers.size(); i < count; i++) {
                if (!headerIgnoreMap.containsKey(headers.name(i))) {
                    log(headers.name(i) + ": " + headers.value(i));
                }
            }
        }

        //param
        RequestBody requestBody = request.body();
        String paramString = readRequestParamString(requestBody);
        if (!TextUtils.isEmpty(paramString)) {
            log(String.format("Params: %s", paramString));
        }

        //response
        if(Level.BODY.equals(level)){
            String responseString = "";
            if(url.contains("/minio/file/") || url.contains("/erp/file/")){
                responseString="----------file body ignore----------";
            }else{
                ResponseBody responseBody = response.body();
                if (null != responseBody) {
                    if (isPlainText(responseBody.contentType())) {
                        responseString = readContent(response);
                    } else {
                        responseString = "other-type="+ responseBody.contentType();
                    }
                }
            }
            log(String.format("Response Body: %s", responseString));
        }

        log(String.format("Time: %s ms",duration));
        log("----------Request End----------");
        return response;
    }

    private String readRequestParamString(RequestBody requestBody) {
        String paramString;
        if (requestBody instanceof MultipartBody) {//判断是否有文件
            StringBuilder sb = new StringBuilder();
            MultipartBody body = (MultipartBody) requestBody;
            List<MultipartBody.Part> parts = body.parts();
            RequestBody partBody;
            for (int i = 0, size = parts.size(); i < size; i++) {
                partBody = parts.get(i).body();
                if (null != partBody) {
                    if (sb.length() > 0) {
                        sb.append(",");
                    }
                    if (isPlainText(partBody.contentType())) {
                        sb.append(readContent(partBody));
                    } else {
                        sb.append("other-param-type=").append(partBody.contentType());
                    }
                }
            }
            paramString = sb.toString();
        } else {
            paramString = readContent(requestBody);
        }
        return paramString;
    }

    private String readContent(Response response){
        if (response == null ) {
            return "";
        }

        try {
            return response.peekBody(Long.MAX_VALUE).string();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    private String readContent(RequestBody body){
        if (body == null ) {
            return "";
        }

        Buffer buffer = new Buffer();

        try {
            //小于500K
            if (body.contentLength() <= 0.6* 1024*1024) {
                body.writeTo(buffer);
            } else {
                return "content is more than 600KB";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return buffer.readUtf8();
    }
}
