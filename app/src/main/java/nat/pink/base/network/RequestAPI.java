package nat.pink.base.network;

import nat.pink.base.model.ObjectLocation;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RequestAPI {

    @POST("getNumberAD")
    Call<ObjectLocation> checkLocation(@Body RequestBody requestBody);
}
