package com.movie.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.movie.DrawableAlwaysCrossFadeFactory
import com.movie.R
import com.movie.databinding.ItemMovieListBinding
import dee.mvvm.koin.MVVM.Movielist


class MovieListAdapter(private var listener: OnItemClickListener) :
    RecyclerView.Adapter<MovieListAdapter.ViewHolder>() {
    var modelArrayList: ArrayList<Movielist> = ArrayList<Movielist>()
    private lateinit var binding: ItemMovieListBinding

    interface OnItemClickListener {
        fun onItemClick(movielist: ArrayList<Movielist>, position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val inflater: LayoutInflater = LayoutInflater.from(parent.context).context.getSystemService(
            Context.LAYOUT_INFLATER_SERVICE
        ) as LayoutInflater

        binding =
            DataBindingUtil.inflate(
                inflater, R.layout.item_movie_list, null, false
            )

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindUI(position, modelArrayList)
    }

    override fun getItemCount(): Int {
        return modelArrayList.size
    }

    fun setModelArrayList(_modelArrayList: List<Movielist>?) {
        modelArrayList.addAll(_modelArrayList!!)

         }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
    inner class ViewHolder(binding: ItemMovieListBinding) : RecyclerView.ViewHolder(
        binding.root
    ) {

        fun bindUI(position: Int, movielist: ArrayList<Movielist>) {


            //setting values
            binding.movietitle.text= movielist[position].original_name
            binding.rating.text= movielist[position].vote_average
            binding.date.text= " ${movielist[position].first_air_date} "
            binding.votes.text= movielist[position].vote_count+" Votes"
            binding.countrylanguage.text= movielist[position].original_language


            //setting images
            var img_url="https://image.tmdb.org/t/p/w500/${movielist[position].poster_path}"
            val options: RequestOptions = RequestOptions()
                .format(DecodeFormat.PREFER_RGB_565)
            Glide
                .with(binding.movieposter.context)
                .load(img_url)
                .apply(options)
                .transition(DrawableTransitionOptions.with(DrawableAlwaysCrossFadeFactory()))
                .into(binding.movieposter)

            binding.moviecard.setOnClickListener {
                listener.onItemClick(
                    movielist, position
                )
            }

        }
    }
}
