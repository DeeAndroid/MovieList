package com.movie.Fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.SurfaceControl.Transaction
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.movie.DrawableAlwaysCrossFadeFactory
import com.movie.R
import com.movie.databinding.FragmentMovieDetailsBinding
import com.movie.databinding.FragmentMoviesBinding
import dee.mvvm.koin.MVVM.Movielist


@SuppressLint("UseRequireInsteadOfGet")
class MovieDetailsFragment : Fragment() {
    private lateinit var binding: FragmentMovieDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_movie_details, container, false
        )

       // for on go back
        binding.back.setOnClickListener {
            activity!!.onBackPressed()
        }

        //getting values from bundle
        val movielist = requireArguments().getSerializable("movie_details") as ArrayList<Movielist>?
        val position=requireArguments().getInt("position")

        //setting values
        binding.movietitle.text=movielist!![position].original_name
        binding.description.text= movielist[position].overview
        binding.rating.text= movielist[position].vote_average
        binding.releasedate.text="Initial Release -${movielist!![position].first_air_date}"
        binding.votes.text="${movielist!![position].vote_count} Peoples Loved This Movie"


        // setting image to image view
        var img_url="https://image.tmdb.org/t/p/w500/${movielist[position].backdrop_path}"
        val options: RequestOptions = RequestOptions()
            .format(DecodeFormat.PREFER_RGB_565)
        Glide
            .with(context!!)
            .load(img_url)
            .apply(options)
            .transition(DrawableTransitionOptions.with(DrawableAlwaysCrossFadeFactory()))
            .into(binding.moviecover)



        return binding.root
    }


}