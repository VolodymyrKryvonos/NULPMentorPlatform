package com.nulp.mentor.data.remote

import com.nulp.mentor.data.remote.dto.*
import com.nulp.mentor.data.remote.request_body.*
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

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
    suspend fun subscribeOnSubject(@Body subscribeOnSubjectBody: SubscribeOnSubjectBody):Void

    @GET("/applications/{mentorId}")
    suspend fun getMentorsApplications(@Path("mentorId") mentorId: Int):List<ApplicationDto>

    @POST("/makeAnApplication")
    suspend fun makeAnApplication(@Body makeApplicationBody: MakeApplicationBody): Void

    @POST("/replyOnApplication")
    suspend fun replyOnApplication(@Body replyOnApplicationBody: ReplyOnApplicationBody): Void

    @POST("/makeMentorRequest")
    suspend fun makeMentorRequest(@Body mentorRequestBody: MentorRequestBody): Void

    @POST("/cancelMentorRequest")
    suspend fun cancelMentorRequest(@Body cancelRequest: CancelRequest): Void

    @POST("/mentorReplyOnRequest")
    suspend fun mentorReplyOnRequest(@Body mentorReplyBody: MentorReplyBody): Void

    @GET("/bestMentors")
    suspend fun getBestMentors(): List<BestMentorDto>
}