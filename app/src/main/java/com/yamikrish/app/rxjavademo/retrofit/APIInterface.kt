package com.yamikrish.app.rxjavademo.retrofit

import com.yamikrish.app.rxjavademo.model.UserModel
import io.reactivex.Observable
import retrofit2.http.GET

interface APIInterface {
    @GET("users")
    fun getUsers():  Observable<ArrayList<UserModel>>
}