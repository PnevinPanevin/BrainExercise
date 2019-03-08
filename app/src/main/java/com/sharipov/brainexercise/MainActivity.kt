package com.sharipov.brainexercise

import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.arellomobile.mvp.MvpAppCompatActivity
import com.sharipov.brainexercise.mvp.OnBackPressedListener
import kotlinx.android.synthetic.main.activity_main.*



class MainActivity : MvpAppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        navController = Navigation.findNavController(this, R.id.navHostFragment)

        bottomNavigation.setupWithNavController(navController)
        NavigationUI.setupWithNavController(toolbar, navController, null)

        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            when (destination.id) {
                R.id.helpFragment, R.id.exercisesFragment, R.id.statisticsFragment -> onMainDestinations()
                R.id.shapesFragment, R.id.expressionsFragment, R.id.comparisonsFragment -> onTestStarted()
            }
        }
    }

    private fun onMainDestinations(){
        bottomNavigation.show()
        toolbar.show()
    }

    private fun onTestStarted() {
        toolbar.hide()
        bottomNavigation.hide()
    }

    override fun onBackPressed() {
        val currentFragment = navHostFragment.childFragmentManager.fragments[0]
        if (currentFragment is OnBackPressedListener)
            currentFragment.onBackPressed()
        else if (!navController.popBackStack())
            super.onBackPressed()
    }

    override fun onNavigateUp(): Boolean {
        return navController.navigateUp()
    }
}

fun View.hide() = with(this) {
    if (visibility == android.view.View.VISIBLE && alpha == 1F)
        animate().alpha(0F)
            .withEndAction { visibility = android.view.View.GONE }
            .duration = 0L
}

fun View.show() = with(this) {
    if (visibility == View.GONE && alpha == 0F)
        animate().alpha(1F)
            .withEndAction { visibility = View.VISIBLE }
            .duration = 0L
}
