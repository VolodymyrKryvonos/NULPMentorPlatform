package com.nulp.mentor.domain.model

import java.io.Serializable

data class BestMentor(
    val mentor: Mentor,
    val subject: Subject
): Serializable
