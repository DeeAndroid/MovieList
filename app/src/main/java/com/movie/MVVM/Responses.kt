package dee.mvvm.koin.MVVM


data class Movies(
    val results: List<Movielist>

)

data class Movielist(
    val backdrop_path: String,
    val first_air_date: String,
    val original_language: String,
    val original_name: String,
    val overview: String,
    val popularity: String,
    val poster_path: String,
    val vote_average: String,
    val vote_count: String
)