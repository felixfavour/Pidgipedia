package com.felixfavour.pidgipedia.entity

data class WordApproval(
    val wordApproved: Word,
    val timeApproved: Long,
    val approvedBy: User
) : Eventstamp(word = wordApproved, eventTime = timeApproved, humanEntity = approvedBy, isApproved = true) {
}