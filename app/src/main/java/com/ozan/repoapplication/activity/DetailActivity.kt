package com.ozan.repoapplication.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.ozan.repoapplication.GenericFiles
import com.ozan.repoapplication.R
import com.squareup.picasso.Picasso

class DetailActivity : AppCompatActivity() {

    private lateinit var img : ImageView
    private lateinit var tvOwner : TextView
    private lateinit var tvStars : TextView
    private lateinit var tvIssues : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        img = findViewById(R.id.img)
        tvOwner = findViewById(R.id.tv_owner)
        tvStars = findViewById(R.id.tv_starts)
        tvIssues = findViewById(R.id.tv_issues)


        Picasso.get().load(GenericFiles.selectedRepositoryList[GenericFiles.selectedRepositoryIndex].owner?.avatar_url).into(img)

        tvOwner.text = GenericFiles.selectedRepositoryList[GenericFiles.selectedRepositoryIndex].owner?.login

        tvStars.text = "Stars: " + GenericFiles.selectedRepositoryList[GenericFiles.selectedRepositoryIndex].stargazers_count.toString()
        tvIssues.text = "Open Issues: " + GenericFiles.selectedRepositoryList[GenericFiles.selectedRepositoryIndex].open_issues_count.toString()


        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.setDisplayShowHomeEnabled(true)
            supportActionBar!!.setDisplayShowTitleEnabled(true)
            supportActionBar!!.title = GenericFiles.selectedRepositoryList[GenericFiles.selectedRepositoryIndex].name
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
         super.onCreateOptionsMenu(menu)
        val inflaTer : MenuInflater = menuInflater
        inflaTer.inflate(R.menu.menu_detail, menu)

        val menuItem : MenuItem? = menu?.findItem(R.id.item_star)

        menuItem?.setIcon(android.R.drawable.star_big_off)

        if (GenericFiles.selectedRepositoryList[GenericFiles.selectedRepositoryIndex].isStarred){
            menuItem?.setIcon(android.R.drawable.star_big_on)

        }

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onOptionsItemSelected(item)

        if (item.itemId == android.R.id.home) {
            finish()
        }
        else if (item.itemId == R.id.item_star){

            GenericFiles.selectedRepositoryList[GenericFiles.selectedRepositoryIndex].isStarred = !GenericFiles.selectedRepositoryList[GenericFiles.selectedRepositoryIndex].isStarred
            invalidateOptionsMenu()

        }

        return true
    }
}