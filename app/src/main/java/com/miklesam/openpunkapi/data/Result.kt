package com.miklesam.openpunkapi.data

open class Result<T>( val data: T? = null, val error: Throwable? = null) {
    companion object {
        fun <T> fromData( data: T ) : Result<T> {
            return Result(data, null)
        }

        fun <T> fromError( error : Throwable ) : Result<T> {
            return Result(null, error)
        }
    }

    fun isError() : Boolean{
        return error != null
    }

    fun isSuccess() : Boolean {
        return data != null
    }
}