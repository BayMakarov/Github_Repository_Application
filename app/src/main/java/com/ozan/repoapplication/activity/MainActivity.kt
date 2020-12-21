package com.ozan.repoapplication.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ozan.repoapplication.GenericFiles
import com.ozan.repoapplication.R
import com.ozan.repoapplication.adapter.RepositoryAdapter
import com.ozan.repoapplication.classes.RepoResponse
import dagger.Component
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

class MainActivity : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RepositoryAdapter

    var repoList: List<RepoResponse> = ArrayList()


    lateinit var editText: EditText
    lateinit var button: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(false)
            supportActionBar!!.setDisplayShowHomeEnabled(true)
            supportActionBar!!.setDisplayShowTitleEnabled(true)
            supportActionBar!!.title = "Repositories"

        }


        editText = findViewById(R.id.editText)
        button = findViewById(R.id.button2)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.itemAnimator = DefaultItemAnimator()

        adapter = RepositoryAdapter(repoList)

        val linearLayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = adapter

        adapter.onItemClick = { pos ->

            GenericFiles.selectedRepositoryIndex = pos
            GenericFiles.selectedRepositoryList = repoList

            passToDetailPage()

        }

        adapter.onItemImageClick = { pos ->

            repoList[pos].isStarred = !repoList[pos].isStarred
            adapter.notifyDataSetChanged()

        }


        button.setOnClickListener { it ->

            hideKeyboard()

            GenericFiles.selectedRepositoryList = ArrayList()
            GenericFiles.selectedRepositoryIndex = 0

            val writtenRepo = editText.text.toString().trim()

            val destinationService = ServiceBuilder.buildService(Service::class.java)
            val requestCall = destinationService.getRepos(writtenRepo)

            val compositeDisposable = CompositeDisposable()
            compositeDisposable.add(
                requestCall
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribeWith(object : DisposableObserver<List<RepoResponse>>() {

                        override fun onComplete() {
                            setupUi()
                        }


                        override fun onNext(t: List<RepoResponse>?) {

                            if (t != null) {
                                repoList = t
                            }
                        }


                        override fun onError(e: Throwable?) {

                            if (e != null) {
                                Toast.makeText(
                                    this@MainActivity,
                                    e.localizedMessage,
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                        }

                    })
            )


        }

    }

    override fun onResume() {
        super.onResume()

        if (GenericFiles.selectedRepositoryList.isNotEmpty()){
            adapter.setItems(GenericFiles.selectedRepositoryList)
            adapter.notifyDataSetChanged()

        }

    }

    private fun setupUi() {

        adapter.setItems(repoList)
        adapter.notifyDataSetChanged()
    }

    fun passToDetailPage() {

        val intent = Intent(this, DetailActivity::class.java)
        startActivity(intent)

    }

    override fun onBackPressed() {

        super.onBackPressed()
        finish()

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onOptionsItemSelected(item)

        if (item.itemId == android.R.id.home) {
            finish()
        }

        return true
    }

    fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (imm != null && currentFocus != null) {
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }

        editText.clearFocus()
    }
}

//Interface to call service
interface Service {


    @GET("users/{user}/repos")
    fun getRepos(@Path("user") id: String): Observable<List<RepoResponse>>

}

//REtrofit Builder
object ServiceBuilder {
    private const val URL = "https://api.github.com/"

    //CREATE HTTP CLIENT
    private val okHttp = OkHttpClient.Builder()

    //retrofit builder
    private val builder = Retrofit.Builder().baseUrl(URL)
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttp.build())

    //create retrofit Instance
    private val retrofit = builder.build()

    fun <T> buildService(serviceType: Class<T>): T {
        return retrofit.create(serviceType)
    }

}

@Component
interface AppComponent {
    fun bind(app: MainActivity)
}



