package com.timmitof.cityreport.core.utils

data class Resource<out T>(val status: Status, val data: T?, val message: String?) {
    companion object {

        fun <T> success(data: T?): Resource<T> {
            return Resource(Status.SUCCESSFUL, data, null)
        }

        fun <T> failure(msg: String): Resource<T> {
            return Resource(Status.FAILURE, null, msg)
        }

        fun <T> loading(data: T?): Resource<T> {
            return Resource(Status.LOADING, data, null)
        }
    }
}

enum class Status {
    SUCCESSFUL,
    FAILURE,
    LOADING
}