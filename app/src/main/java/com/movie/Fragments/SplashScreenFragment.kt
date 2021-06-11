package com.movie.Fragments

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.movie.R
import com.movie.setCurrentFragment

class SplashScreenFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        Handler(Looper.getMainLooper()).postDelayed({
            val activity = context as AppCompatActivity
            val myFragment: Fragment = MoviesFragment()
                     activity.supportFragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                .replace(R.id.viewview, myFragment).commit() }, 2000)

        return inflater.inflate(R.layout.fragment_splash_screen, container, false)
    }


}