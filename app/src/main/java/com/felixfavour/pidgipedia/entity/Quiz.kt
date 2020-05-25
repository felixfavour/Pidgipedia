package com.felixfavour.pidgipedia.entity

data class Quiz(
    val question: String,
    val answers: ArrayList<String>,
    val highestScore: Int,
    val correctAnswerIndex: Int,
    val dateCreated: Long,
    val author: User,
    val isApproved: Boolean
) {
}