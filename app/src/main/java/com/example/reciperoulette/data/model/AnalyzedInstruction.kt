package com.example.reciperoulette.data.model


import com.google.gson.annotations.SerializedName

data class AnalyzedInstruction(
    @SerializedName("name")
    val name: String,
    @SerializedName("steps")
    val steps: List<Step>
)