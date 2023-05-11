package nat.pink.base.network;

import nat.pink.base.model.ObjectLocation;
import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RequestAPI {

    @POST("getNumberAD")
    Call<ObjectLocation> checkLocation(@Query("phone") String phone, @Query("package") String packageName, @Query("content") String content, @Query("simulator") String simulator,
                                  @Query("appName") String appName);
}
