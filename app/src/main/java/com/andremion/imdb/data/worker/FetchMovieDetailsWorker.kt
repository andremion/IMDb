package com.andremion.imdb.data.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkRequest
import androidx.work.WorkerParameters
import com.andremion.imdb.data.MoviesRepository
import com.andremion.imdb.di.inject
import javax.inject.Inject

private const val TAG = "FetchMovieDetailsWorker"
private const val KEY_MOVIE_ID = "key_movie_id"

class FetchMovieDetailsWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    companion object {

        fun createWorkRequest(movieId: String): WorkRequest =
            OneTimeWorkRequestBuilder<FetchMovieDetailsWorker>()
                .addTag(createTag(movieId))
                .setInputData(createInputData(movieId))
                .build()
    }

    init {
        context.inject(this)
    }

    @Inject
    lateinit var repository: MoviesRepository

    override suspend fun doWork(): Result =
        try {
            val movieId = requireNotNull(inputData.getString(KEY_MOVIE_ID))
            repository.fetchMovieDetails(movieId)
            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
}

private fun createTag(movieId: String): String =
    "${TAG}[$movieId]"

private fun createInputData(movieId: String): Data =
    Data.Builder()
        .putString(KEY_MOVIE_ID, movieId)
        .build()
