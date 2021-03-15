package com.youssef.fixit.Data;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
import com.youssef.fixit.UI.MainActivity.MainActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private final String BASE_URL = "https://backend.helpmefixit.ca/api/";
    public static RetrofitClient retrofitClient;
    public RetrofitHelper retrofitHelper;
    public static String token;

    public RetrofitClient() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        retrofitHelper = retrofit.create(RetrofitHelper.class);
    }

    public static RetrofitClient getInstance() {
        if (retrofitClient == null) {
            retrofitClient = new RetrofitClient();
        }
        token = "Bearer " + MainActivity.MyToken;
        return retrofitClient;
    }

    public Call<Cites> GetCountry() {
        return retrofitHelper.getCountery();
    }

    ;

    public Call<Cites> GetCity(int countery_id) {
        return retrofitHelper.getCity(countery_id);
    }

    ;

    public Call<Cites> GetArea(int city_id) {
        return retrofitHelper.getArea(city_id);
    }

    ;

    public Call<Category> GetCategory() {
        return retrofitHelper.GetGategory();
    }

    ;

    public Call<Register> Register(MultipartBody.Part image_file, String name, String email, String password, String password_confirmation, int area_id, String phone, String type, int catgories_id) {
//        ArrayMap<String, String> hashMap = new ArrayMap<>();
//        hashMap.put("name", name);
//        hashMap.put("email", email);
//        hashMap.put("password", password);
//        hashMap.put("password_confirmation", password_confirmation);
//        hashMap.put("area_id", String.valueOf(area_id));
//        hashMap.put("phone", phone);
//        hashMap.put("type", type);
//        hashMap.put("catgories[0]", String.valueOf(catgories_id));
        return retrofitHelper.Register(
                image_file, name, email, password, password_confirmation, area_id, phone, type, catgories_id);
    }

    public Call<Register> Login(String email, String password) {
        return retrofitHelper.Login(email, password);
    }

    public Call<Jobs> GetJobs(String search_title) {
        return retrofitHelper.GetJobs("pending",search_title);
    }

    public Call<CreateBid> CreateProject(String title, String description, String pay_type, int price_from, int price_to, String address, String how_long, int area_id, int category_id, String all_price, String currency) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("title", title);
        hashMap.put("description", description);
        hashMap.put("pay_type", pay_type);
        hashMap.put("price_from", price_from);
        hashMap.put("price_to", price_to);
        hashMap.put("address", address);
        hashMap.put("how_long", how_long);
        hashMap.put("area_id", area_id);
        hashMap.put("category_id", category_id);
        hashMap.put("price", all_price);
        hashMap.put("currency", currency);
        return retrofitHelper.CreateProject(token, hashMap);
    }

    public Call<Project> GetProject(int project_id) {
        return retrofitHelper.GetProject(project_id);
    }

    public Call<Bids> GetBids(int project_id) {
        return retrofitHelper.GetBids(project_id);
    }

    public Call<CreateBid> CreateBid(int project_id, String description, String time, String price) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("project_id", project_id);
        hashMap.put("description", description);
        hashMap.put("time", time);
        hashMap.put("price", price);
        return retrofitHelper.CreateBid(token, hashMap);
    }

    ;

    public Call<MyProject> MyOpenProjects(String Status) {
        return retrofitHelper.MyOpenProject(token, Status);
    }

    ;

    public Call<CreateBid> Createcontract(int project_id, String description, String total_price, String address, String phone, int profession_id, List<String> titlelist, List<String> pricelist) {
        HashMap<String, Object> hashMap = new HashMap<>();
        HashMap<String, Object> Milestones = new HashMap<>();
        List<HashMap> objectList = new ArrayList<>();
        hashMap.put("project_id", project_id);
        hashMap.put("details", description);
        hashMap.put("total_price", total_price);
        hashMap.put("address", address);
        hashMap.put("phone", phone);
        hashMap.put("profession_id", profession_id);
        for (int i = 0; i < titlelist.size(); i++) {
//            Milestones.put("milestones[" + i + "][details]", titlelist.get(i));
//            Milestones.put("milestones[" + i + "][price]", pricelist.get(i));
            Milestones.put("details", titlelist.get(i));
            Milestones.put("price", pricelist.get(i));
            objectList.add(Milestones);
        }
        hashMap.put("milestones", objectList);
        return retrofitHelper.Createcontract(token, hashMap);
    }


    public Call<MyBids> GetMyBids(String status) {
        return retrofitHelper.MyBids(token, status);
    }

    public Call<MyContract> MyContract() {
        return retrofitHelper.MyContract(token);
    }

    public Call<MyBids> GetMyRequests(String status) {
        return retrofitHelper.MyRequests(token, status);
    }

    public Call<Contract> ShowContract(int contract_id) {
        Log.e("hesham2",token+"");
        return retrofitHelper.GetContract(token, contract_id);
    }

    public Call<CreateBid> Acceptorreject(int contract_id, String status) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("contract_id", contract_id);
        hashMap.put("status", status);
        return retrofitHelper.AcceptOrReject(token, hashMap);
    }

    public Call<CreateBid> ReleaseMilestone(int milestone_id) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("milestone_id", milestone_id);
        hashMap.put("status", "pending");
        return retrofitHelper.ReleaseMilestone(token, hashMap);
    }

    public Call<CreateBid> AcceptReleaseMilestone(int milestone_id) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("milestone_id", milestone_id);
        hashMap.put("status", "released");
        return retrofitHelper.AcceptReleaseMilestone(token, hashMap);
    }

    //add milestones
    public Call<CreateBid> CreateMilestone(int contract_id, String details, String price) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("contract_id", contract_id);
        hashMap.put("details", details);
        hashMap.put("price", price);
        return retrofitHelper.CreateMilestones(token, hashMap);
    }


    public Call<ShowProfile> Showprofile(int user_id) {
        return retrofitHelper.ShowProfile(user_id);
    }

    public Call<ShowProfile> MyProfile() {
        return retrofitHelper.MyProfile(token);
    }

    public Call<CreateBid> SendInvoice(String title, String description, String price, int discount, int to, String balance, String currency) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("title", title);
        hashMap.put("description", description);
        hashMap.put("price", price);
        hashMap.put("discount", discount);
        hashMap.put("to", to);
        hashMap.put("balance", balance);
        hashMap.put("currency", currency);
        return retrofitHelper.SendInvoice(token, hashMap);
    }

    public Call<MyInvoice> MyInvoice() {
        return retrofitHelper.MyInvoice(token);
    }

    public Call<Package> GetPackage() {
        return retrofitHelper.GetPackage(token);
    }

    public Call<CreateBid> SubscribeAds(int package_id, int months) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("package_id", package_id);
        hashMap.put("months", months);
        return retrofitHelper.SubscribeAds(token, hashMap);
    }

    public Call<ShowAds> HomeAds() {
        return retrofitHelper.HomeAds(token);
    }

    public Call<CreateBid> SetRate(String type, float rate, int project_id, int user_id) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("type", type);
        hashMap.put("rate", rate);
        hashMap.put("project_id", project_id);
        hashMap.put("user_id", user_id);
        return retrofitHelper.SetRate(token, hashMap);
    }

    public Call<CreateBid> Setfeedback(String comment, int project_id, int user_id) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("comment", comment);
        hashMap.put("project_id", project_id);
        hashMap.put("user_id", user_id);
        return retrofitHelper.Setfeedback(token, hashMap);
    }

    public Call<Rooms> Rooms() {
        return retrofitHelper.Rooms(token);
    }

    public Call<Chat> Massage(int user_id) {
        return retrofitHelper.Massages(token, user_id);
    }

    public Call<SendMassage> sendmassage(int room_id, String message, int to) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("room_id", room_id);
        hashMap.put("message", message);
        hashMap.put("to", to);
        return retrofitHelper.SendMassages(token, hashMap);
    }
    public Call<SendMassage> MakeRoom(int user_id) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("user_id", user_id);
        return retrofitHelper.MakeRoom(token, hashMap);
    }
    public Call<SearchProff> SearchProff(String search,int category_id) {
        return retrofitHelper.searchproff(token,search, category_id);
    }
    public Call<CreateBid> activeproject(int contract_id) {
        HashMap<String,Object> hashMap=new HashMap<>();
        hashMap.put("contract_id",contract_id);
        return retrofitHelper.AciveProject(token,hashMap);
    }
    public Call<CreateBid> EditeProfile(String  name,String email,String phone,String details) {
        HashMap<String,Object> hashMap=new HashMap<>();
        hashMap.put("name",name);
        hashMap.put("email",email);
        hashMap.put("phone",phone);
        hashMap.put("details",details);
        return retrofitHelper.EditeProfile(token,hashMap);
    }
    public Call<CreateBid> ForgetPassword(String email) {
        HashMap<String,Object> hashMap=new HashMap<>();
        hashMap.put("email",email);
        return retrofitHelper.ForgetPassword(hashMap);
    }
}
