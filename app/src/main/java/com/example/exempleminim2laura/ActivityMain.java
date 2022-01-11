package com.example.exempleminim2laura;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.exempleminim2laura.Models.User;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ActivityMain extends AppCompatActivity {

    private EditText user;
    private Button exploreButton;
    private GithubAPI APIgit;
    private static Retrofit retrofit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        user = findViewById(R.id.UserEditText);
        exploreButton = findViewById(R.id.buttonExploreFollowers);

        startRetrofit();
        cargarUser();
    }

    private static void startRetrofit(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        //Attaching Intercepor to a client
        OkHttpClient client = new OkHttpClient().newBuilder().addInterceptor(interceptor).build();

        retrofit = new Retrofit.Builder()
                .baseUrl("http://api.github.com/") //Local host on windows 10.0.2.2 and ip our machine 147.83.7.203
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
    }

    private void cargarUser(){
        APIgit = retrofit.create(GithubAPI.class);
        exploreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("click","ok");

                Call<User> call = APIgit.infoUser(user.getText().toString());
                Log.d("User", user.getText().toString());
                call.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        Log.d("Info","Tenemos Respuesta");
                        if(response.isSuccessful()){
                            Log.d("onResponse", "Tenemos User");
                            Intent intent = new Intent(getApplicationContext(),ActivityInicial.class);
                            intent.putExtra("user", user.getText().toString());
                            startActivity(intent);
                        }
                        else{
                            Log.d("Info", "User Not Found");
                            Toast.makeText(getApplicationContext(), "User Not Found" + response.code(), Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Toast.makeText(getApplicationContext(),"Error: " + t.getMessage(), Toast.LENGTH_LONG);
                    }
                });
            }
        });
    }
}
