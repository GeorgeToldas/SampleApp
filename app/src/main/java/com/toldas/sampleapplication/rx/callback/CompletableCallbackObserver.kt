package com.toldas.sampleapplication.rx.callback

import io.reactivex.observers.DisposableCompletableObserver
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.HttpException

abstract class CompletableCallbackObserver : DisposableCompletableObserver() {

    protected abstract fun onResponse()

    protected abstract fun onFailure(message: String)

    override fun onComplete() {
        onResponse()
    }

    override fun onError(e: Throwable) {
        if (e is HttpException) {
            val responseBody = e.response().errorBody()
            onFailure(getErrorMessage(responseBody))
        }
    }

    private fun getErrorMessage(responseBody: ResponseBody?): String {
        return try {
            val jsonObject = JSONObject(responseBody.toString())
            jsonObject.getString("message")
        } catch (e: Exception) {
            e.toString()
        }
    }
}