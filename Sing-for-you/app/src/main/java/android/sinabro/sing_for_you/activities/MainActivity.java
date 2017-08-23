package android.sinabro.sing_for_you.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.sinabro.sing_for_you.R;
import android.sinabro.sing_for_you.adpater.RecyclerviewAdapter;
import android.sinabro.sing_for_you.model.Music;
import android.sinabro.sing_for_you.network.HTTPConnection;
import android.sinabro.sing_for_you.network.Service;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.util.ArrayList;
import java.util.jar.Manifest;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.HTTP;

public class MainActivity extends AppCompatActivity {

    private Context context;
    private RecyclerviewAdapter adapter;
    private ArrayList<Music> musics;
    private RecyclerView recyclerView;
    private BroadcastReceiver broadcastReceiver;
    private RecyclerView.LayoutManager layoutManager;
    private Button button1;
    private TextView temperature;
    private Service service;
    private ArrayList<Music> arrayList;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            PackageInfo info = getPackageManager().getPackageInfo("android.sinabro.sing_for_you", PackageManager.GET_SIGNATURES);
            for (android.content.pm.Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        service = HTTPConnection.getInstance().create(Service.class);





        //setBackground(2);
        service.getWeather(32.22, 21.32).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Log.d("hello", response.body().get("weather")+"");

                JsonElement weather=response.body().get("weather");
                String weather_name=weather.getAsString();
                temperature=(TextView)findViewById(R.id.temperature);
                temperature.setText(weather_name);
                getMusicList();
                Log.d("----------aaa---------",weather_name);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });



    }//oncreate

    public void getMusicList(){
        service.getMusicList(2).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonArray jsonObject=response.body().getAsJsonArray("music");
                JsonArray jsonElements=jsonObject.getAsJsonArray();
                arrayList=getArrayList(jsonElements);
                adapter=new RecyclerviewAdapter(getApplicationContext(),arrayList);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
    }


    public ArrayList<Music> getArrayList(JsonArray jsonElements){
        ArrayList<Music> arrayList=new ArrayList<>();
        for(int i=0;i<jsonElements.size();i++){
            JsonObject jsonObject=(JsonObject)jsonElements.get(i);
            String title=jsonObject.getAsJsonPrimitive("title").getAsString();
            String singer=jsonObject.getAsJsonPrimitive("singer").getAsString();
            String url=jsonObject.getAsJsonPrimitive("musicURL").getAsString();
            String imaurl=jsonObject.getAsJsonPrimitive("imgURL").getAsString();

            arrayList.add(new Music(title,singer,url,imaurl));
        }
        return  arrayList;
    }


    public Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    } // Author: sile
            private boolean runtime_permissions() {
                if (Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                        ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) !=
                                PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{
                            android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION
                    }, 100);
                    return true;
                }

                return false;

            }
}