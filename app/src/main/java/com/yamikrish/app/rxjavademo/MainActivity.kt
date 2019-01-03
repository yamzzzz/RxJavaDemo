package com.yamikrish.app.rxjavademo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.Toast
import com.yamikrish.app.rxjavademo.adapter.RecyclerAdapter
import com.yamikrish.app.rxjavademo.model.UserModel
import com.yamikrish.app.rxjavademo.retrofit.APIInterface
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity() {

    lateinit var recyclerAdapter: RecyclerAdapter
    var list: ArrayList<UserModel> = ArrayList()
    val BASE_URL = "https://jsonplaceholder.typicode.com/"

    val disposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)


        setAdapter()

        // Build Retrofit Request
        val requestInterface = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(APIInterface::class.java)


        // Call API using RxJava & RxAndroid
        val observable = requestInterface.getUsers()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(this::handleResponse, this::handleError)


        // Add API Call/Observable to CompositeDisposable
        disposable.add(observable)

    }

    /**
     * Setting Adapter to RecyclerView
     */
    private fun setAdapter() {
        recyclerAdapter = RecyclerAdapter(this@MainActivity, list)
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = recyclerAdapter
        }
    }

    /**
     * To handle Response received by RxJava's Observable
     */
    private fun handleResponse(userList: ArrayList<UserModel>) {
        Log.i("userList", "userList==" + userList)
        list.addAll(userList)
        recyclerAdapter.notifyDataSetChanged()
    }

    /**
     * To handle Error (if any), received by RxJava's Observable
     */
    private fun handleError(error: Throwable) {
        Toast.makeText(this, "Error " + error.localizedMessage, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.dispose()
    }
}
