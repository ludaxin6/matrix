package sample.tencent.matrix.api;

import android.util.Log;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okio.Buffer;
import okio.BufferedSource;
import sample.tencent.matrix.util.CodeUtil;


public class CustomHttpLoggingInterceptor implements Interceptor {

    private Level level;

    public CustomHttpLoggingInterceptor(Level level) {
        this.level = level;
    }

    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        String traceId = getUUID();
        if (Level.NONE.equals(level)) {
            return logNone(chain);
        }
        else if (Level.INFO.equals(level)) {
            return logInfo(chain, traceId);
        }
        else if (Level.DEBUG.equals(level)) {
            return logDebug(chain, traceId);
        }
        else{
            return logInfo(chain, traceId);
        }
    }
    protected void log(String message) {
        Log.d(CustomHttpLoggingInterceptor.class.getSimpleName(), message);
    }
    
    private Response logNone(Chain chain) throws IOException {
        return chain.proceed(chain.request());
    }
    
    private Response logInfo(Chain chain, String traceId) throws IOException {
        Request request = chain.request();
        String method = request.method();
        String uri = request.url().uri().toString();

        print(String.format("%s -->  %s %s ", traceId, method, uri));

        long startNs = System.nanoTime();
        Response response = chain.proceed(request);
        long timeMillis = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs);
        int code = response.code();
        boolean successful = response.isSuccessful();

        logWithoutContext(successful, response, traceId);

        return response;
    }
    
     private Response logDebug(Chain chain, String traceId) throws IOException {
        Request request = chain.request();
        String method = request.method();
        String uri = request.url().uri().toString();
        RequestBody body = request.body();

        print(String.format("%s --> %s %s ", traceId, method, uri));
        logRequestHeaders(request, traceId);

        String bodyStr = null;
        long bodyLength = 0;
        String mediaType = null;
        if (body != null) {
        bodyLength = body.contentLength();
            if (bodyLength > 0) {
                mediaType = body.contentType().toString();
                Buffer buffer = new Buffer();
                body.writeTo(buffer);
                bodyStr = buffer.readString(StandardCharsets.UTF_8);
            }
        }

        if (body != null) {
            print(String.format("%s --> Content-Type %s Content-Length %s", traceId, mediaType, bodyLength));
            print(String.format("%s --> request body%n%s", traceId, bodyStr));
        }

        return chain.proceed(request);
    }
    
    private void logWithoutContext(boolean successful, Response response, String traceId) throws IOException {
        if (successful) {
            print(String.format("%s <-- %s", traceId, response.code()));
        }
        if (!successful) {
            print(String.format("%s <-- %s %n%s", traceId, response.code(), getBodyCopy(response)));
        }
    }
    
    private void logRequestHeaders(Request request, String traceId) {
        Headers headers = request.headers();
        for (int i = 0; i < headers.size(); i++) {
            print(String.format("%s --> header %s %s", traceId, headers.name(i), headers.value(i)));
        }
    }
    
    private void logResponseHeaders(Response response, String traceId) {
        Headers headers = response.headers();
        for (int i = 0; i < headers.size(); i++) {
            print(String.format("%s <-- header %s %s", traceId, headers.name(i), headers.value(i)));
        }
    }
    
    private String getBodyCopy(Response response) throws IOException {
        BufferedSource source = Objects.requireNonNull(response.body()).source();
        source.request(Long.MAX_VALUE);
        return source.getBuffer().clone().readString(StandardCharsets.UTF_8);
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    private static String getUUID() {
        return CodeUtil.getUUID().toString().replaceAll("-", "");
    }

    private void print(String str) {
        log(str);
        //log.info(str);
    }
}