package sample.tencent.matrix.api;

import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
import sample.tencent.matrix.api.bean.TokenInfo;

public interface CommonNetWorkService {
    String BASE_URL="http://192.168.111.66:9999 ";//http://4170064zk3.51vip.biz";

    @Headers("Content_Type:application/json;charset=utf-8")
    @POST("/apm/data")
    Observable<BaseResponse<String>> pushInfo(@Body HashMap<String, String> dto);

    @Headers("Content_Type:application/json;charset=utf-8")
    @GET("/auth/oauth/token")
    Observable<TokenInfo> getToken(@Query("grant_type") String grant_type, @Query("clientId") String clientId);

}
