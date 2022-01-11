package com.example.exempleminim2laura;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.exempleminim2laura.Models.Repos;
import com.example.exempleminim2laura.Models.User;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.logging.Logger;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ActivityInicial extends AppCompatActivity {
    protected TextView followers;
    protected TextView following;
    protected android.widget.ImageView imageprofile;
    RecyclerView recyclerView;
    ProgressBar barProgreso;
    GithubAPI apiRest;
    public static final String MyPREFERENCES = "MyPrefs";
    static final String BASEURL = "https://api.github.com/";
    final Logger log = Logger.getLogger(String.valueOf(ActivityInicial.class));

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial);

        recyclerView = findViewById(R.id.ReposList);

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        //Attaching Interceptor to a client
        OkHttpClient client = new OkHttpClient().newBuilder().addInterceptor(interceptor).build();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASEURL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        apiRest = retrofit.create(GithubAPI.class);

        //SharedPreferences sharedPref = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        //String username = sharedPref.getString("Username","");
        //No em funciona
        String username = getIntent().getStringExtra("username");

        barProgreso = findViewById(R.id.progressBar);
        barProgreso.setVisibility(View.VISIBLE);

        Call<User> call = apiRest.infoUser(username);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                final User user = response.body();
                log.info("Correcto");
                followers = findViewById(R.id.Followers);
                following = findViewById(R.id.Following);
                imageprofile = findViewById(R.id.imageProfile);
                followers.setText(String.valueOf(user.getFollowers()));
                following.setText(String.valueOf(user.getFollowing()));
                Picasso.with(getApplicationContext()).load(user.getAvatar_url()).into(imageprofile);

                Call<List<Repos>> call2 = apiRest.repositories(username);
                call2.enqueue(new Callback<List<Repos>>() {
                    @Override
                    public void onResponse(Call<List<Repos>> call2, Response<List<Repos>> resp2) {
                        if(!response.isSuccessful()) {
                            log.info("Error" + resp2.code());
                            return;
                        }

                        List<Repos> listarepos = resp2.body();
                        inicializarRecyclerView(listarepos);
                        barProgreso.setVisibility(View.GONE);
                    }

                    @Override
                    public void onFailure(Call<List<Repos>> call2, Throwable t) {

                    }
                });
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error Code" + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void inicializarRecyclerView(List<Repos> repos){ //Inicializar RecyclerView
        RecyclerViewAdapt adapter= new RecyclerViewAdapt(this,repos);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
