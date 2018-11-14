package tv.merabihar.app.merabihar.UI.MainTabHostScreens;

import android.accounts.Account;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tv.merabihar.app.merabihar.Adapter.ContentAdapterVertical;
import tv.merabihar.app.merabihar.Adapter.ContentRecyclerHorizontal;
import tv.merabihar.app.merabihar.Adapter.ImagePorifleContentAdapter;
import tv.merabihar.app.merabihar.Adapter.ProfileListAdapter;
import tv.merabihar.app.merabihar.CustomFonts.MyTextView_Roboto_Regular;
import tv.merabihar.app.merabihar.Model.Contents;
import tv.merabihar.app.merabihar.Model.UserProfile;
import tv.merabihar.app.merabihar.R;
import tv.merabihar.app.merabihar.UI.Activity.FollowOptions.FollowOptionsActivity;
import tv.merabihar.app.merabihar.UI.Activity.SettingScreen;
import tv.merabihar.app.merabihar.Util.Constants;
import tv.merabihar.app.merabihar.Util.PreferenceHandler;
import tv.merabihar.app.merabihar.Util.ThreadExecuter;
import tv.merabihar.app.merabihar.Util.Util;
import tv.merabihar.app.merabihar.WebAPI.ContentAPI;
import tv.merabihar.app.merabihar.WebAPI.ProfileAPI;
import tv.merabihar.app.merabihar.WebAPI.ProfileFollowAPI;

public class TabAccountActivity extends AppCompatActivity {

    ImageView mSettings,mFollow;
    CircleImageView mProfilePhoto;
    MyTextView_Roboto_Regular mProfileName,mProfileAbout,mPosts,
            mFollowers,mFollowings,mNoPosts;
    RecyclerView mFollowingPeoples;
    RecyclerView mPostsList;
    //ListView mPostsList;
    ProgressBar progressBar;

    LinearLayout applinear,linearlinear;
    ImageView app,linear;
    ImagePorifleContentAdapter adapters;
    ContentAdapterVertical adapter;

    private static final int PAGE_START = 1;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int TOTAL_PAGES ;
    private int currentPage = PAGE_START;

    private String TAG="BlogList";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try{

            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);

            setContentView(R.layout.activity_tab_account);
            Fresco.initialize(this);
            mSettings = (ImageView)findViewById(R.id.settings);
            mFollow = (ImageView)findViewById(R.id.follow_peopls);
            mProfilePhoto = (CircleImageView)findViewById(R.id.profile_photo);
            mProfileName = (MyTextView_Roboto_Regular)findViewById(R.id.profile_name);
            mProfileAbout = (MyTextView_Roboto_Regular)findViewById(R.id.profile_about);
            mPosts = (MyTextView_Roboto_Regular)findViewById(R.id.tvPosts);
            mFollowers = (MyTextView_Roboto_Regular)findViewById(R.id.tvFollowers);
            mFollowings = (MyTextView_Roboto_Regular)findViewById(R.id.tvFollowing);
            mNoPosts = (MyTextView_Roboto_Regular)findViewById(R.id.no_post);
            mFollowingPeoples = (RecyclerView)findViewById(R.id.follow_profiles);
            mPostsList = (RecyclerView)findViewById(R.id.listviews);
            progressBar = (ProgressBar)findViewById(R.id.progressBar);

            applinear = findViewById(R.id.applinear);
            linearlinear = findViewById(R.id.linearlinear);

            app = findViewById(R.id.apptool);
            linear = findViewById(R.id.lineartool);

           final int profileId = PreferenceHandler.getInstance(TabAccountActivity.this).getUserId();
            applinear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    app.setImageResource(R.drawable.ic_apps_blue);
                    linear.setImageResource(R.drawable.ic_linear_grey);
                    mPostsList.setAdapter(null);
                    mFollowingPeoples.setAdapter(null);
                    mPostsList.setVisibility(View.VISIBLE);
                    mFollowingPeoples.setVisibility(View.GONE);
                    if(profileId!=0){


                        getProfileContent(profileId);



                    }else{

                        Toast.makeText(TabAccountActivity.this, "Something went wrong.Please login again", Toast.LENGTH_SHORT).show();

                    }
                }
            });
            linearlinear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    app.setImageResource(R.drawable.ic_apps_grey);
                    linear.setImageResource(R.drawable.ic_linear_blue);
                    mPostsList.setAdapter(null);
                    mFollowingPeoples.setAdapter(null);
                    mPostsList.setVisibility(View.GONE);
                    mFollowingPeoples.setVisibility(View.VISIBLE);
                    if(profileId!=0){
                        adapter = new ContentAdapterVertical(TabAccountActivity.this);
                        mFollowingPeoples.setAdapter(adapter);

                        loadFirstSetOfBlogs(profileId);



                    }else{

                        Toast.makeText(TabAccountActivity.this, "Something went wrong.Please login again", Toast.LENGTH_SHORT).show();

                    }


                }
            });

            mFollowingPeoples.setLayoutManager(new LinearLayoutManager(TabAccountActivity.this, LinearLayoutManager.VERTICAL, false));
            mFollowingPeoples.setNestedScrollingEnabled(false);

            RecyclerView.LayoutManager layoutManager = new GridLayoutManager(TabAccountActivity.this, 3);
            mPostsList.setLayoutManager(layoutManager);
            mPostsList.setItemAnimator(new DefaultItemAnimator());



            //int profileId = 49;
            if(profileId!=0){

                getProfile(profileId);
                getProfileContent(profileId);



            }else{

                Toast.makeText(this, "Something went wrong.Please login again", Toast.LENGTH_SHORT).show();

            }

            mFollow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent follow = new Intent(TabAccountActivity.this, FollowOptionsActivity.class);
                    startActivity(follow);
                }
            });

            mSettings.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent follow = new Intent(TabAccountActivity.this, SettingScreen.class);
                    startActivity(follow);
                }
            });


            mProfilePhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(profileId!=0){





                    }else{

                        Toast.makeText(TabAccountActivity.this, "Something went wrong.Please login again", Toast.LENGTH_SHORT).show();

                    }

                }
            });


        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void getProfile(final int id){

        new ThreadExecuter().execute(new Runnable() {
            @Override
            public void run() {

                final ProfileAPI subCategoryAPI = Util.getClient().create(ProfileAPI.class);
                Call<UserProfile> getProf = subCategoryAPI.getProfileById(id);
                //Call<ArrayList<Blogs>> getBlog = blogApi.getBlogs();

                getProf.enqueue(new Callback<UserProfile>() {

                    @Override
                    public void onResponse(Call<UserProfile> call, Response<UserProfile> response) {

                        progressBar.setVisibility(View.GONE);

                        if (response.code() == 200)
                        {
                            System.out.println("Inside api");

                            UserProfile profile = response.body();

                            mProfileName.setText(""+profile.getFullName());
                            if(profile.getPrefix()!=null){

                                mProfileAbout.setText(""+profile.getPrefix());
                            }

                            String ref = "MBR"+profile.getProfileId();
                            String referCodeText = Base64.encodeToString(ref.getBytes(), Base64.DEFAULT);
                            PreferenceHandler.getInstance(TabAccountActivity.this).setReferalcode(ref);

                            if(profile.getProfilePhoto()!=null){

                                String base=profile.getProfilePhoto();



                                if(base != null && !base.isEmpty()){
                                    Picasso.with(TabAccountActivity.this).load(base).placeholder(R.drawable.profile_image).
                                            error(R.drawable.profile_image).into(mProfilePhoto);

                                }else{
                                    mProfilePhoto.setImageResource(R.drawable.profile_image);
                                }
                            }




                        }
                    }

                    @Override
                    public void onFailure(Call<UserProfile> call, Throwable t) {

                    }
                });

            }

        });
    }
    private void getFollowerByProfileId(final int id){

        new ThreadExecuter().execute(new Runnable() {
            @Override
            public void run() {


                ProfileFollowAPI apiService =
                        Util.getClient().create(ProfileFollowAPI.class);

                Call<ArrayList<UserProfile>> call = apiService.getFollowersByProfileId(id);

                call.enqueue(new Callback<ArrayList<UserProfile>>() {
                    @Override
                    public void onResponse(Call<ArrayList<UserProfile>> call, Response<ArrayList<UserProfile>> response) {
//                List<RouteDTO.Routes> list = new ArrayList<RouteDTO.Routes>();
                        int statusCode = response.code();

                        progressBar.setVisibility(View.GONE);

                        if(statusCode == 200 || statusCode == 204)
                        {

                            ArrayList<UserProfile> responseProfile = response.body();

                            if(responseProfile != null && responseProfile.size()!=0 )
                            {

                                Collections.shuffle(responseProfile);
                                ProfileListAdapter adapter = new ProfileListAdapter(TabAccountActivity.this,responseProfile);
                                mFollowingPeoples.setAdapter(adapter);


                            }
                            else
                            {



                            }
                        }
                        else
                        {

                            Toast.makeText(TabAccountActivity.this,response.message(),Toast.LENGTH_SHORT).show();
                        }
//                callGetStartEnd();
                    }

                    @Override
                    public void onFailure(Call<ArrayList<UserProfile>> call, Throwable t) {
                        // Log error here since request failed

                        progressBar.setVisibility(View.GONE);

                        Log.e("TAG", t.toString());
                    }
                });
            }
        });
    }

    private void getFollowersByProfileId(final int id){

        new ThreadExecuter().execute(new Runnable() {
            @Override
            public void run() {


                ProfileFollowAPI apiService =
                        Util.getClient().create(ProfileFollowAPI.class);

                Call<ArrayList<UserProfile>> call = apiService.getFollowersByProfileId(id);

                call.enqueue(new Callback<ArrayList<UserProfile>>() {
                    @Override
                    public void onResponse(Call<ArrayList<UserProfile>> call, Response<ArrayList<UserProfile>> response) {
//                List<RouteDTO.Routes> list = new ArrayList<RouteDTO.Routes>();
                        int statusCode = response.code();

                        progressBar.setVisibility(View.GONE);

                        if(statusCode == 200 || statusCode == 204)
                        {

                            ArrayList<UserProfile> responseProfile = response.body();

                            if(responseProfile != null && responseProfile.size()!=0 )
                            {

                                mFollowers.setText(""+responseProfile.size());


                            }
                            else
                            {



                            }
                        }
                        else
                        {

                            Toast.makeText(TabAccountActivity.this,response.message(),Toast.LENGTH_SHORT).show();
                        }
//                callGetStartEnd();
                    }

                    @Override
                    public void onFailure(Call<ArrayList<UserProfile>> call, Throwable t) {
                        // Log error here since request failed

                        progressBar.setVisibility(View.GONE);

                        Log.e("TAG", t.toString());
                    }
                });
            }
        });
    }

    private void getFollowingByProfileId(final int id){

        new ThreadExecuter().execute(new Runnable() {
            @Override
            public void run() {


                ProfileFollowAPI apiService =
                        Util.getClient().create(ProfileFollowAPI.class);

                Call<ArrayList<UserProfile>> call = apiService.getFollowingByProfileId(id);

                call.enqueue(new Callback<ArrayList<UserProfile>>() {
                    @Override
                    public void onResponse(Call<ArrayList<UserProfile>> call, Response<ArrayList<UserProfile>> response) {
//                List<RouteDTO.Routes> list = new ArrayList<RouteDTO.Routes>();
                        int statusCode = response.code();

                        progressBar.setVisibility(View.GONE);

                        if(statusCode == 200 || statusCode == 204)
                        {

                            ArrayList<UserProfile> responseProfile = response.body();

                            if(responseProfile != null && responseProfile.size()!=0 )
                            {
                                Collections.shuffle(responseProfile);
                                mFollowings.setText(""+responseProfile.size());
                                ProfileListAdapter adapter = new ProfileListAdapter(TabAccountActivity.this,responseProfile);
                                mFollowingPeoples.setAdapter(adapter);


                            }
                            else
                            {

                                getFollowerByProfileId(id);
                            }
                        }
                        else
                        {

                            getFollowerByProfileId(id);
                            Toast.makeText(TabAccountActivity.this,response.message(),Toast.LENGTH_SHORT).show();
                        }
//                callGetStartEnd();
                    }

                    @Override
                    public void onFailure(Call<ArrayList<UserProfile>> call, Throwable t) {
                        // Log error here since request failed

                        progressBar.setVisibility(View.GONE);
                        getFollowerByProfileId(id);

                        Log.e("TAG", t.toString());
                    }
                });
            }
        });
    }

    public void getProfileContent(final int profileId)
    {


        new ThreadExecuter().execute(new Runnable() {
            @Override
            public void run() {

                final ContentAPI categoryAPI = Util.getClient().create(ContentAPI.class);
                Call<ArrayList<Contents>> getCat = categoryAPI.getContentByProfileId(profileId);
                //Call<ArrayList<Category>> getCat = categoryAPI.getCategories();

                getCat.enqueue(new Callback<ArrayList<Contents>>() {

                    @Override
                    public void onResponse(Call<ArrayList<Contents>> call, Response<ArrayList<Contents>> response) {



                        if(response.code() == 200 && response.body()!= null)
                        {
                            progressBar.setVisibility(View.GONE);

                            if(response.body().size()!=0){

                                mPosts.setText(""+response.body().size());
                                adapters = new ImagePorifleContentAdapter(TabAccountActivity.this,response.body());
                                mPostsList.setAdapter(adapters);
                            }

                        }else{

                            progressBar.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onFailure(Call<ArrayList<Contents>> call, Throwable t) {

                        progressBar.setVisibility(View.GONE);

                        Toast.makeText(TabAccountActivity.this,t.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });


                //System.out.println(TAG+" thread started");

            }

        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        goBack();
    }

    private void goBack()
    {
        Intent intent = null;

        intent = new Intent(TabAccountActivity.this,TabMainActivity.class);

        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

        TabAccountActivity.this.finish();
    }

    public void loadFirstSetOfBlogs(final int id) {


        new ThreadExecuter().execute(new Runnable() {
            @Override
            public void run() {
                ContentAPI bookingApi = Util.getClient().create(ContentAPI.class);

                Call<ArrayList<Contents>> getAllBookings = bookingApi.
                        getContentByProfileId(id);

                getAllBookings.enqueue(new Callback<ArrayList<Contents>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Contents>> call, Response<ArrayList<Contents>> response) {


                        try{
                            if(response.code() == 200 && response.body()!= null)
                            {
                                if(response.body().size() != 0) {
                                    Log.d(TAG, "loadFirstPage: "+response.message());
                                    ArrayList<Contents> approvedBlogs = response.body();

                                    if(approvedBlogs!=null&&approvedBlogs.size()!=0){
                                        loadFirstPage(approvedBlogs);
                                    }else{
                                        isLoading = true;

                                        currentPage = currentPage+1;

                                    }

                                }
                                else
                                {
                                    adapter.removeLoadingFooter();
                                    isLastPage = true;
                                    isLoading = true;
                                    progressBar.setVisibility(View.GONE);
                                }

                            }
                            else
                            {

                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onFailure(Call<ArrayList<Contents>> call, Throwable t) {
                    }
                });

                //WebService.getAllBookings(PreferenceHandler.getInstance(getActivity()).getHotelID());
            }
        });

    }

    private void loadFirstPage(ArrayList<Contents> list) {

        //Collections.reverse(list);
        progressBar.setVisibility(View.GONE);
        adapter.addAll(list);

        if (list != null && list.size() !=0)
            adapter.addLoadingFooter();
        else
            isLastPage = true;

    }

}
