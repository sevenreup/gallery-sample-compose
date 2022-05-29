package com.sevenreup.albumsample.utils

public sealed class Response<out T>(
    val data: T? = null,
    val message: String? = null
) {
    public class Success<T>(data: T) : Response<T>(data = data)
    public class Failure<T>(message: String) : Response<T>(message = message)
    public class Loading<T> : Response<T>()
    public class Idle<T> : Response<T>()
}