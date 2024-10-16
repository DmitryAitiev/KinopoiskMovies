package com.example.movies

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class MainViewModel(application: Application):AndroidViewModel(application) {
    private val TAG = "MainActivity"
    private var page = 1
    private val compositeDisposable = CompositeDisposable()

    val movies = MutableLiveData<List<Movie>>(emptyList())
    val isLoading = MutableLiveData<Boolean>(false)

    init {
        loadMovies()
    }

    fun getMovies(): LiveData<List<Movie>> {
        return movies
    }
    fun getisLoading(): LiveData<Boolean> {
        return isLoading
    }
    fun loadMovies() {
        val loading = isLoading.value
        if (loading != null && loading)
            return
        val disposable = ApiFactory.apiService.loadMovies(page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                isLoading.value = true
            }
            .doAfterTerminate {
                isLoading.value = false
            }
            .subscribe({response ->
                val currentMovies = movies.value?.toMutableList() ?: mutableListOf()
                currentMovies.addAll(response.movies)
                movies.value = currentMovies
                Log.d(TAG, "Loaded" + page)
                page++
            },{
                Log.d(TAG, it.toString())
            })
        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}
