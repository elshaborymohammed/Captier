package com.example.capiter.data.model

import com.google.gson.annotations.Expose

data class ErrorMessage(
    @Expose
    val path: String,
    @Expose
    val message: String,
    @Expose
    val type: String
)