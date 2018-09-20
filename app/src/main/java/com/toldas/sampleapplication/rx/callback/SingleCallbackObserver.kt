package com.toldas.sampleapplication.rx.callback

import io.reactivex.observers.DisposableSingleObserver
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.HttpException

abstract class SingleCallbackObserver<T> : DisposableSingleObserver<T>() {

    protected abstract fun onResponse(response: T)

    protected abstract fun onFailure(message: String)

    override fun onSuccess(t: T) {
        onResponse(t)
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