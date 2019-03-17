package com.sharipov.brainexercise.view

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.arellomobile.mvp.MvpAppCompatActivity
import com.sharipov.brainexercise.R
import com.sharipov.brainexercise.mvp.TestView
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : MvpAppCompatActivity() {
    private val USER_ID = "USER_ID"
    private lateinit var userId: String
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navController = Navigation.findNavController(this, R.id.navHostFragment)

        val prefs = this.getSharedPreferences(packageName, Context.MODE_PRIVATE)
        if (prefs.contains(USER_ID)) {
            userId = prefs.getString(USER_ID, "") as String
        } else {
            userId = "${Build.MANUFACTURER}${Build.MODEL}${System.currentTimeMillis()}"
            prefs.edit()
                .putString(USER_ID, userId)
                .apply()
        }
    }

    override fun onBackPressed() {
        val currentFragment = navHostFragment.childFragmentManager.fragments[0]
        if (currentFragment is TestView)
            currentFragment.onBackPressed()
        else if (!navController.popBackStack())
            super.onBackPressed()
    }

    override fun onNavigateUp(): Boolean {
        return navController.navigateUp()
    }
}

fun View.hide() = with(this) {
    visibility = View.GONE
}

fun View.show() = with(this) {
    visibility = View.VISIBLE
}
