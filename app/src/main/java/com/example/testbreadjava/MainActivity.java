package com.example.testbreadjava;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
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
    Button transleteButton;

    public final String ADDRESS = "https://api.nasa.gov/planetary/apod";
    public final String IAMADRESS = "https://iam.api.cloud.yandex.net";
    public final String KEY = "DEMO_KEY";
    public final String oAuthToken = "y0_AgAAAABjnfpTAATuwQAAAADg5WbZ6gn_Bus2QwGEAZXnxlqTIs3fevE";
    public final String IDAdress = "b1grqraost1n38tpuatt";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        spaceImageView = findViewById(R.id.spaceImageView);
        description = findViewById(R.id.description);
        transleteButton = findViewById(R.id.translete);

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