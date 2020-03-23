package com.shanya.pagergallery

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.google.gson.Gson

class GalleryViewModel(application: Application) : AndroidViewModel(application) {
    private val _photoListLive = MutableLiveData<List<PhotoItem>>()
    val photoListLive : LiveData<List<PhotoItem>>
    get() = _photoListLive

    fun fetchData() {
        val stringRequest = StringRequest(
            Request.Method.GET,
            getUrl(),
            Response.Listener {
                _photoListLive.value = Gson().fromJson(it,Pixabay::class.java).hits.toList()
            },
            Response.ErrorListener { Log.d("VolleyErrorListener",it.toString()) }
        )
        VolleySingleton.getInstance(getApplication()).requestQueue.add(stringRequest)
    }

    private fun getUrl():String {
        return "https://pixabay.com/api/?key=14963951-5701965a9270b6e50415ae561&q=${keyWords.random()}&per_page=100"
    }

    private val keyWords = arrayOf("cat","dog","lion","beauty","flower","car","computer")
}