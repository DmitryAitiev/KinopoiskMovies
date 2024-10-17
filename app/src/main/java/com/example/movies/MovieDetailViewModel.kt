package com.example.movies

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class MovieDetailViewModel(application: Application): AndroidViewModel(application) {
    private val compositeDisposable = CompositeDisposable()
    private val TAG = "MovieDetailActivityTEST"
    private val trailers: MutableLiveData<List<Trailer>> = MutableLiveData()
    private val reviews: MutableLiveData<List<Review>> = MutableLiveData(emptyList())
    val isLoading = MutableLiveData<Boolean>(false)
    private val movieDao = MovieDatabase.getInstanse(application).movieDao()
    private var page = 1

    fun getFavouriteMovie(movieId: Int): LiveData<Movie> {
        return movieDao.getFavouriteMovie(movieId)
    }
    fun insertMovie(movie: Movie) {
        val disposable = movieDao.insertMovie(movie)
            .subscribeOn(Schedulers.io())
            .subscribe()
    compositeDisposable.add(disposable)
    }
    fun removeMovie(movieId: Int) {
        val disposable = movieDao.removeMovie(movieId)
            .subscribeOn(Schedulers.io())
            .subscribe()
        compositeDisposable.add(disposable)
    }
    fun getTrailers(): LiveData<List<Trailer>>{
        return trailers
    }
    fun getReviews(): LiveData<List<Review>>{
        return reviews
    }
    fun loadTrailers(id: Int) {
        val disposable = ApiFactory.apiService.loadTrailers(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map{Response -> Response.trailersList.trailers}
            .subscribe({
                trailers.value = it
            }, {
                Log.d(TAG, it.toString())
            })
        compositeDisposable.add(disposable)
    }
    fun loadReview(movieId: Int) {
        val loading = isLoading.value
        if (loading != null && loading)
            return
        val disposable = ApiFactory.apiService.loadReview(movieId, page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                isLoading.value = true
            }
            .doAfterTerminate {
                isLoading.value = false
            }
            .map { resp -> resp.reviewList }
            .subscribe({resp ->
                //val currentReviews = reviews.value?.toMutableList() ?: mutableListOf()
                //currentReviews.addAll(resp)
                //reviews.value = currentReviews
                //page++
                reviews.value = resp
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