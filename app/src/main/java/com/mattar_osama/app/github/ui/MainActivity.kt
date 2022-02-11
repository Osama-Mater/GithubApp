package com.mattar_osama.app.github.ui

import android.os.Bundle
import android.view.Menu
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import com.mattar_osama.app.github.R
import com.mattar_osama.app.github.ui.repository.search.SearchGithubReposFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    // OVERRIDE ---
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        configureAndShowFragment()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return configureToolbar(menu)
    }

    // CONFIGURATION ---

    private fun configureAndShowFragment() {
        var fragment =
            supportFragmentManager.findFragmentById(R.id.activity_main_container) as SearchGithubReposFragment?
        if (fragment == null) {
            fragment = SearchGithubReposFragment()
            supportFragmentManager.beginTransaction()
                .add(R.id.activity_main_container, fragment)
                .commit()
        }
    }

    private fun configureToolbar(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)
        val searchItem = menu?.findItem(R.id.action_search)
        val searchView = searchItem?.actionView as? SearchView
        searchView?.queryHint = getString(R.string.search)
        return true
    }
}
