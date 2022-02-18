package dev.illwiz.tada.data.utils

import java.net.SocketException
import java.net.UnknownHostException

object NetworkUtils {

    fun isConnectionError(throwable: Throwable):Boolean {
        return throwable is UnknownHostException || throwable is SocketException
    }
}