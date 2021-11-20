package com.dranoer.giphyapp.data.remote

import android.util.Log
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response
import java.io.IOException

abstract class SafeApiRequest {
    suspend fun <T : Any> apiRequest(call: suspend () -> Response<T>): T {

        val response = call.invoke()
        val message = StringBuilder()

        if (response.isSuccessful) {
            Log.d("safeapi_request", "> ${response.body()} ")

            return response.body()!!

        } else {
            val error = response.errorBody()?.string()
            error?.let {
                try {
                    message.append(JSONObject(it).getString("message"))

                } catch (e: JSONException) {
                    message.append("\n")
                }

            }
            message.append("\nError code ${response.code()}")
            Log.d("safeapi_request", "> ${response.code()} ")
            throw ApiException(message.toString())
        }
    }
}


class ApiException(message: String) : IOException(message)