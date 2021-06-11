package dee.mvvm.koin.MVVM


class Repository constructor(
    private val api: Api
) : SafeApiCall {

    //for Geting Movie list
    suspend fun movie_list_repository(
        api_key:String,
        page: Int
    ) = safeApiCall {
        api.movie_list_api(api_key,page)
    }

}
