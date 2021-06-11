package com.movie.Fragments

import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.movie.Adapters.MovieListAdapter
import com.movie.R
import com.movie.databinding.FragmentMoviesBinding
import dee.mvvm.koin.MVVM.Movielist
import dee.mvvm.koin.MVVM.Movies
import dee.mvvm.koin.MVVM.Resource
import dee.mvvm.koin.MVVM.Viewmodels
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.FieldPosition


class MoviesFragment : Fragment() {
    val viewModel: Viewmodels by viewModel()
    private lateinit var binding: FragmentMoviesBinding
    private var recyclerViewState: Parcelable? = null
    var page = 1
    private var isScroll = false
    private var list: List<Movielist> = mutableListOf<Movielist>()

    //listner for adapter
    private var listener = object : MovieListAdapter.OnItemClickListener {
        override fun onItemClick(
            newsfeeds: ArrayList<Movielist>,
            position: Int

        ) {
            val activity = context as AppCompatActivity
            val myFragment: Fragment = MovieDetailsFragment()
            val bundle = Bundle()
            bundle.putSerializable("movie_details", newsfeeds)
            bundle.putInt("position", position)
            myFragment.arguments = bundle

            activity.supportFragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                .replace(R.id.viewview, myFragment).addToBackStack(null).commit()

        }

    }
    val adapter = MovieListAdapter(listener!!)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_movies, container, false
        )

        // recycler layout manager
        binding.moviesrecycler.layoutManager = LinearLayoutManager(context)
        binding.moviesrecycler.adapter = adapter
        //getting movies
        getmovies(page)

        // recyclerview scrololl listner for pagination
        recyclerViewState = binding.moviesrecycler.layoutManager!!.onSaveInstanceState()
        binding.moviesrecycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                if (isLastVisible() && isScroll) {

                  //  viewModel.movie_list_response.removeObservers(viewLifecycleOwner)
                    getmovies(page)
                }

            }
        })

        return binding.root
    }

    private fun getmovies(i: Int) {
        page += 1
        Log.d("TAG", "getmovies: $page")
        viewModel.movie_list_response.removeObservers(viewLifecycleOwner)
        viewModel.movie_list_viewmodel("5d967c7c335764f39b1efbe9c5de9760", i)
        viewModel.movie_list_response.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Failure -> {
                    isScroll = false
                    binding.progressMovies.visibility=View.GONE
                }
                is Resource.Loading -> {
                    isScroll = false
                    binding.progressMovies.visibility=View.VISIBLE
                }
                is Resource.Success -> {
                    binding.progressMovies.visibility=View.GONE
                    lifecycleScope.launch {
                        list = it.value.results
                        adapter.setModelArrayList(list)
                        recyclerViewState =
                            binding.moviesrecycler.layoutManager!!.onSaveInstanceState()
                        adapter.notifyItemRangeChanged(0, it.value.results.size)
                        binding.moviesrecycler.layoutManager!!.onRestoreInstanceState(
                            recyclerViewState
                        )
                        isScroll = true

                    }
                }
            }
        })
    }
    fun isLastVisible(): Boolean {
        val layoutManager = binding.moviesrecycler.layoutManager as LinearLayoutManager
        val pos = layoutManager.findLastCompletelyVisibleItemPosition()
        val numItems: Int = binding.moviesrecycler.adapter!!.itemCount
        return pos >= numItems -1
    }

}