package com.youssef.fixit.Models.Data;


import com.youssef.fixit.Models.Ads.Package.Package;
import com.youssef.fixit.Models.Ads.ShowAds;
import com.youssef.fixit.Models.Bids.Bids;
import com.youssef.fixit.Models.Bids.CreateBid;
import com.youssef.fixit.Models.Category.Category;
import com.youssef.fixit.Models.Cities.Cites;
import com.youssef.fixit.Models.Contract.Contract;
import com.youssef.fixit.Models.Contract.MyContract;
import com.youssef.fixit.Models.InVoice.MyInvoice;
import com.youssef.fixit.Models.Jobs.Jobs;
import com.youssef.fixit.Models.MyBids.MyBids;
import com.youssef.fixit.Models.MyProjects.MyProject;
import com.youssef.fixit.Models.Project.Project;
import com.youssef.fixit.Models.Register.Register;
import com.youssef.fixit.Models.SearchProff.SearchProff;
import com.youssef.fixit.Models.ShowProfile.ShowProfile;
import com.youssef.fixit.Models.chat.Massage.Chat;
import com.youssef.fixit.Models.chat.Rooms;
import com.youssef.fixit.Models.chat.SendMassage.SendMassage;

import java.util.HashMap;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface RetrofitHelper {
    @GET("regions/countries/get-all")
    Call<Cites> getCountery();

    @GET("regions/cities/get-all")
    Call<Cites> getCity(@Query("id") int country_id);

    @GET("regions/areas/get-all")
    Call<Cites> getArea(@Query("id") int city_id);

    @GET("categories/index")
    Call<Category> GetGategory();

    @Multipart
    @Headers({"Accept: application/json; charset=utf-8"})
    @POST("auth/register")
    Call<Register> Register(@Part MultipartBody.Part image_file//,@QueryMap ArrayMap<String,String> hashMap);
            , @Query("name") String name
            , @Query("email") String email
            , @Query("password") String password
            , @Query("password_confirmation") String password_confirmation
            , @Query("area_id") int area_id
            , @Query("phone") String phone
            , @Query("type") String type
            , @Query("catgories[]") int catgories_id);

    @POST("auth/login")
    Call<Register> Login(@Query("email") String email, @Query("password") String password);

    @POST("project/store")
    Call<CreateBid> CreateProject(@Header("Authorization") String token, @Body HashMap hashMap);

    @GET("project/index")
    Call<Jobs> GetJobs(@Query("status") String status, @Query("search_title") String search_title);

    @GET("project/show")
    Call<Project> GetProject(@Query("project_id") int project_id);

    @GET("project/bid/index")
    Call<Bids> GetBids(@Query("project_id") int project_id);

    @POST("project/bid/store")
    Call<CreateBid> CreateBid(@Header("Authorization") String token, @Body HashMap hashMap);

    @GET("project/my-projects")
    Call<MyProject> MyOpenProject(@Header("Authorization") String token, @Query("status") String status);

    @POST("project/contracts/store")
    Call<CreateBid> Createcontract(@Header("Authorization") String token, @Body HashMap hashMap);

    @GET("profession/bids")
    Call<MyBids> MyBids(@Header("Authorization") String token, @Query("status") String status);

    @GET("profession/bids")
    Call<MyBids> MyRequests(@Header("Authorization") String token, @Query("contract_status") String status);

    @GET("project/contracts/get")
    Call<Contract> GetContract(@Header("Authorization") String token, @Query("contract_id") int contract_id);

    @POST("project/contracts/change-status")
    Call<CreateBid> AcceptOrReject(@Header("Authorization") String token, @Body HashMap hashMap);

    @POST("project/milestones/release-request")
    Call<CreateBid> ReleaseMilestone(@Header("Authorization") String token, @Body HashMap hashMap);

    @POST("project/milestones/accept-or-reject")
    Call<CreateBid> AcceptReleaseMilestone(@Header("Authorization") String token, @Body HashMap hashMap);

    //add milestone
    @POST("project/milestones/create")
    Call<CreateBid> CreateMilestones(@Header("Authorization") String token, @Body HashMap hashMap);

    //My Contract
    @GET("project/contracts")
    Call<MyContract> MyContract(@Header("Authorization") String token);

    //show profile
    @GET("user/profile")
    Call<ShowProfile> ShowProfile(@Query("user_id") int user_id);

    //me profile
    @GET("user/me")
    Call<ShowProfile> MyProfile(@Header("Authorization") String token);

    //create invoice
    @POST("profession/send-invoice")
    Call<CreateBid> SendInvoice(@Header("Authorization") String token, @Body HashMap hashMap);

    //get my invoice
    @GET("user/invoices")
    Call<MyInvoice> MyInvoice(@Header("Authorization") String token);

    // get Package
    @GET("ads/packages/get-all")
    Call<Package> GetPackage(@Header("Authorization") String token);

    //subscribe in ads
    @POST("profession/send-invoice")
    Call<CreateBid> SubscribeAds(@Header("Authorization") String token, @Body HashMap hashMap);

    // home ads
    @GET("ads/home")
    Call<ShowAds> HomeAds(@Header("Authorization") String token);

    //rateing
    @POST("feedback/rate/create")
    Call<CreateBid> SetRate(@Header("Authorization") String token, @Body HashMap hashMap);

    //feedback
    @POST("feedback/comment/create")
    Call<CreateBid> Setfeedback(@Header("Authorization") String token, @Body HashMap hashMap);

    // chat
    //chat rooms
    @GET("chat/users")
    Call<Rooms> Rooms(@Header("Authorization") String token);

    //Massage
    @GET("chat/get-all")
    Call<Chat> Massages(@Header("Authorization") String token, @Query("user_id") int user_id);

    //Send Massage
    @POST("chat/send-message")
    Call<SendMassage> SendMassages(@Header("Authorization") String token, @Body HashMap hashMap);

    //Make room
    @POST("chat/make-room")
    Call<SendMassage> MakeRoom(@Header("Authorization") String token, @Body HashMap hashMap);

    //Search Proff
    @GET("profession/search")
    Call<SearchProff> searchproff(@Header("Authorization") String token, @Query("search") String search, @Query("category_id") int category_id);

    //Active project
    @POST("project/activate")
    Call<CreateBid> AciveProject(@Header("Authorization") String token, @Body HashMap hashMap);

    //Edite Profile
    @POST("user/edit-profile")
    Call<CreateBid> EditeProfile(@Header("Authorization") String token, @Body HashMap hashMap);

    //Forget password
    @POST("auth/forget-password")
    Call<CreateBid> ForgetPassword(@Body HashMap hashMap);
}
