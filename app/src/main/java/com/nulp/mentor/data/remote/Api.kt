package com.nulp.mentor.data.remote

import com.nulp.mentor.data.remote.dto.*
import com.nulp.mentor.data.remote.request_body.*
import retrofit2.Response
import retrofit2.http.*

interface Api {

    @POST("/signin/email")
    suspend fun login(@Body loginBody: LoginBody): UserDto

    @POST("/signup/email")
    suspend fun signup(@Body signupBody: SignupBody): UserDto

    @POST("/addComment")
    suspend fun addComment(@Body commentBody: CommentBody): Void

    @GET("/getComments/{mentorId}")
    suspend fun getComments(@Path("mentorId") mentorId: Int): List<CommentDto>

    @POST("/rate")
    suspend fun rate(@Body rateBody: RateBody): RateDto

    @GET("/getSubjects")
    suspend fun getSubjects(): List<SubjectDto>

    @POST("/subscribe")
    suspend fun subscribeOnSubject(@Body subscribeOnSubjectBody: SubscribeOnSubjectBody): Response<Void>

    @GET("/applications/{mentorId}")
    suspend fun getMentorsApplications(@Path("mentorId") mentorId: Int): List<ApplicationDto>

    @POST("/applications")
    suspend fun getApplications(@Body ids: List<Int>): List<ApplicationData>

    @POST("/makeAnApplication")
    suspend fun makeAnApplication(@Body makeApplicationBody: MakeApplicationBody): Void

    @POST("/replyOnApplication")
    suspend fun replyOnApplication(@Body replyOnApplicationBody: ReplyOnApplicationBody): Void

    @GET("/mentor/{mentorId}")
    suspend fun getMentor(@Path("mentorId") mentorId: Int): UserDto

    @GET("/bestMentors")
    suspend fun getBestMentors(): List<BestMentorDto>

    @PUT("/becomeMentor/{mentorId}")
    suspend fun becomeMentor(@Path("mentorId") mentorId: Int): UserDto
}