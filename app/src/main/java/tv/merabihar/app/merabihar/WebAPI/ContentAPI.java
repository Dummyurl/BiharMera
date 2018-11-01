package tv.merabihar.app.merabihar.WebAPI;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import tv.merabihar.app.merabihar.Model.Contents;

/**
 * Created by ZingoHotels Tech on 31-10-2018.
 */

public interface ContentAPI {

    @GET("Pagination/GetContentByCityId/{CityId}")
    Call<ArrayList<Contents>> getContentPageByCityId(@Path("CityId") int id, @Query("pageNumber") int pageNumber, @Query("pageSize") int pageSize);

    @GET("Pagination/GetContentByCategoryId/{CategoryId}")
    Call<ArrayList<Contents>> getContentPageByCategoryId(@Path("CategoryId") int id, @Query("pageNumber") int pageNumber, @Query("pageSize") int pageSize);

    @GET("Contents/GetTopFiveContent")
    Call<ArrayList<Contents>> getTrendingContent();

    @GET("Contents/GetContentByProfileId/{ProfileId}")
    Call<ArrayList<Contents>> getContentByProfileId(@Path("ProfileId") int id);

    @GET("Pagination/GetContentByCityIdAndContentType/{CityId}/{ContentType}")
    Call<ArrayList<Contents>> getContentByVideoAndCity(@Path("CityId") int id,@Path("ContentType") String type, @Query("pageNumber") int pageNumber, @Query("pageSize") int pageSize);


}