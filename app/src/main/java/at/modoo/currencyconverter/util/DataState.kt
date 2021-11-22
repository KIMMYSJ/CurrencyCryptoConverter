package at.modoo.currencyconverter.util

import java.lang.Exception

sealed class DataState<out T>{
    data class Success<out T>(val data: T): DataState<T>()
    data class Error(val exception: Exception): DataState<Nothing>()
    data class ErrorUnknown(val exception: String):DataState<Nothing>()
    object Loading : DataState<Nothing>()
    object Empty:DataState<Nothing>()
}

