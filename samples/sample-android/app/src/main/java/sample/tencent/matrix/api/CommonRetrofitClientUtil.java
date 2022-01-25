package sample.tencent.matrix.api;

import android.annotation.SuppressLint;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import sample.tencent.matrix.BuildConfig;
import sample.tencent.matrix.util.GesonUtil;

/**
 * Retofit网络请求工具类
 * Created by HDL on 2016/7/29.
 */
public class CommonRetrofitClientUtil {
    public static final int READ_TIMEOUT = 5*60;//读取超时时间,单位  秒
    public static final int WRITE_TIMEOUT = 5*60;//读取超时时间,单位  秒
    public static final int CONN_TIMEOUT = 5*60;//连接超时时间,单位  秒
    private static HttpLoggingInterceptor loggingInterceptor;
    private static OkHttpClient okHttpClient;
    private static Retrofit mRetrofit;
    //客户端链接池
    private static Map<String, Retrofit> mRetrofitPool = new HashMap<>();

    //静态块,类加载时初始化OkHttpClient对象
    static {
        initOkHttpClient();
    }

    private CommonRetrofitClientUtil() {

    }
    public static OkHttpClient getOkHttpClient(){
        return okHttpClient;
    }
    public static Retrofit newInstence(String url) {
        if (mRetrofit == null) {
            synchronized (Retrofit.class) {
                if (mRetrofit == null) {
                    mRetrofit = new Retrofit.Builder()
                            .client(okHttpClient)//添加一个client,不然retrofit会自己默认添加一个
                            .baseUrl(url)
                            .addConverterFactory(GsonConverterFactory.create(GesonUtil.getGson()))
                            // 改成RxJava2CallAdapterFactory
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .build();
                    if(mRetrofitPool != null){
                        mRetrofitPool.put(url, mRetrofit);
                    }
                }
            }
        }else if(mRetrofitPool !=null && mRetrofitPool.get(url) !=null){
            mRetrofit = mRetrofitPool.get(url);
        }else if(mRetrofitPool !=null && mRetrofitPool.get(url) ==null){
            mRetrofit = mRetrofit.newBuilder().baseUrl(url).build();
            mRetrofitPool.put(url, mRetrofit);
        }else {
            mRetrofit = mRetrofit.newBuilder().baseUrl(url).build();
        }
        return mRetrofit;
    }
    /**
     *  获取单例OkHttpClient
     *
     */
    private static void initOkHttpClient() {
        //日志
        loggingInterceptor = new HttpLoggingInterceptor();
        //日志等级
        if (BuildConfig.DEBUG) {
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        } else {
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
        }
//        LogInterceptor loggingInterceptor = new LogInterceptor(BuildConfig.DEBUG?Level.BODY:Level.BASIC);
        if (okHttpClient == null) {
            synchronized (OkHttpClient.class) {
                if (okHttpClient == null) {
                    HttpCommonInterceptor commonInterceptor = new HttpCommonInterceptor.Builder().build();
                    OkHttpClient.Builder builder = new OkHttpClient.Builder()
                    .hostnameVerifier(new HostnameVerifier() {
                        @SuppressLint("BadHostnameVerifier")
                        @Override
                        public boolean verify(String hostname, SSLSession session) {
                            return true;
                        }
                    })
                    //打印拦截器日志
                    .addNetworkInterceptor(loggingInterceptor)
                    //为所有请求添加请求头
                    //添加公共参数拦截器
                    .addInterceptor(commonInterceptor)
                    .cookieJar(new SimpleCookieJar())
                    //设置连接超时时间
                    .connectTimeout(CONN_TIMEOUT, TimeUnit.SECONDS)
                    //设置读取超时时间
                    .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                    // 设置写入超时时间
                    .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                    .followRedirects(true)
                    /**
                     * trust all the https point
                     */
                    .sslSocketFactory(HttpsUtils.initSSLSocketFactory(),
                            HttpsUtils.initTrustManager());
                    okHttpClient = builder.build();
                }
            }
        }
    }

}
