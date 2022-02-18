package dev.illwiz.tada.data.utils

import org.json.JSONObject
import retrofit2.HttpException
import timber.log.Timber

object AppUtils {

    fun getErrorMessage(throwable: Throwable):String {
        var errMessage = ""
        if (NetworkUtils.isConnectionError(throwable)) {
            errMessage = "No Internet Connection !"
        } else if(throwable is HttpException) {
            val httpCode = throwable.code()
            try {
                val responseBody = throwable.response()?.errorBody()
                responseBody?.let {
                    val jsonObject = JSONObject(it.string())
                    errMessage = jsonObject.getString("message")
                }
            } catch (e: Exception) {
                e.printStackTrace()
                errMessage = throwable.message()
            }
            if(errMessage.isNullOrEmpty()) errMessage = "HTTP Error"
            Timber.d("HTTP Error : $httpCode - $errMessage")
        } else {
            errMessage = throwable.message ?: "unknown"
        }
        return errMessage
    }
}