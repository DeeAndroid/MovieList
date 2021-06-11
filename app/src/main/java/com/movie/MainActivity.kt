package com.movie

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.movie.Fragments.MoviesFragment
import com.movie.Fragments.SplashScreenFragment

class MainActivity : AppCompatActivity() {
    val activity = this@MainActivity as AppCompatActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //setdefault fragment
            setCurrentFragment(activity,SplashScreenFragment())
    }
}