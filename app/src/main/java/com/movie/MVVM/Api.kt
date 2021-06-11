package dee.mvvm.koin.MVVM

import retrofit2.http.GET
import retrofit2.http.Query


interface Api {
      //for Movie list
    @GET("popular")
    suspend fun movie_list_api(
        @Query("api_key") api_key: String,
        @Query("page") page: Int
    ): Movies
}