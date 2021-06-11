package dee.mvvm.koin.MVVM

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch


open class Viewmodels constructor(
     val repository: Repository
): ViewModel()  {


    //for movie list
    val movie_list: MutableLiveData<Resource<Movies>> = MutableLiveData()
    val movie_list_response: LiveData<Resource<Movies>> get() = movie_list
    fun movie_list_viewmodel(
        api_key:String,
        page: Int
    ) = viewModelScope.launch {
        movie_list.value = Resource.Loading
        movie_list.value = repository.movie_list_repository(api_key, page)
    }


}