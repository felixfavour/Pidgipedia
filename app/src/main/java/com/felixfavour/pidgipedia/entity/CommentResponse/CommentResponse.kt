package com.felixfavour.pidgipedia.entity.CommentResponse

import com.felixfavour.pidgipedia.entity.Comment
import com.felixfavour.pidgipedia.entity.Eventstamp
import com.felixfavour.pidgipedia.entity.User

data class CommentResponse (
    val commentResponseContent: String,
    val author: User,
    val respondingTo: Comment,
    val dateCreated: Long
): Eventstamp(comment = respondingTo, eventTime = dateCreated, humanEntity = author) {

}
