package com.example.capiter.data.model

import com.google.gson.TypeAdapter
import com.google.gson.annotations.Expose
import com.google.gson.annotations.JsonAdapter
import com.google.gson.annotations.SerializedName
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter

data class ApiError(
    @JsonAdapter(ErrorStatusTypeAdapter::class)
    @SerializedName("code")
    @Expose
    val code: Int,
    @SerializedName("errors")
    @Expose
    val errors: List<ErrorMessage>?
)


enum class Status(private var type: Int) {
    VALIDATION(0), ERROR(1), UNPROCESSED(2);

    fun type(): Int {
        return type
    }
}

class ErrorStatusTypeAdapter : TypeAdapter<Status>() {
    override fun write(out: JsonWriter?, value: Status?) {
        out?.apply {
            value?.apply {
                value(type())
            }
        }
    }

    override fun read(`in`: JsonReader?): Status {
        `in`?.apply {
            while (hasNext()) {
                return when (nextInt()) {
                    Status.VALIDATION.type() -> {
                        Status.VALIDATION
                    }
                    Status.UNPROCESSED.type() -> {
                        Status.UNPROCESSED
                    }
                    else -> {
                        Status.ERROR
                    }
                }
            }
        }
        return Status.ERROR
    }
}