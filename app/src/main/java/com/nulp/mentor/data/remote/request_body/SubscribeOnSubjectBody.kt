package com.nulp.mentor.data.remote.request_body

import java.io.Serializable

data class SubscribeOnSubjectBody(
    val subjectId:Int,
    val mentorId:Int
): Serializable
