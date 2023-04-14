package com.example.testbreadjava;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    ImageView spaceImageView;
    TextView description;

    public final String ADDRESS = "https://api.nasa.gov/planetary/apod";
    public final String KEY = "DEMO_KEY";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        spaceImageView = findViewById(R.id.spaceImageView);
        description = findViewById(R.id.description);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ADDRESS)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        SpaceService spaceService = retrofit.create(SpaceService.class);
        Call<SpaceInfo> call = spaceService.getImage(KEY);
        call.enqueue(new SpaceResponse());
    }
    class SpaceResponse implements Callback<SpaceInfo>{
        @Override
        public void onResponse(Response<SpaceInfo> response, Retrofit retrofit) {
            if (response.isSuccess()){
                SpaceInfo spaceInfo = response.body();
                String res = response.body().title+"\n";
                if (spaceInfo.media_type.equals("image")){
                    Picasso.get()
                            .load(spaceInfo.url)
                            .placeholder(R.drawable.img)
                            .into(spaceImageView);
                }
            }
        }

        @Override
        public void onFailure(Throwable t) {

        }
    }
}