package android.sinabro.sing_for_you.activities;

import android.content.Intent;
import android.graphics.BlurMaskFilter;
import android.renderscript.ScriptIntrinsicBlur;
import android.sinabro.sing_for_you.R;
import android.sinabro.sing_for_you.adpater.PlayerCycleAdapter;
import android.sinabro.sing_for_you.adpater.RecyclerviewAdapter;
import android.sinabro.sing_for_you.model.Music;
import android.sinabro.sing_for_you.model.PlayerItem;
import android.sinabro.sing_for_you.network.HTTPConnection;
import android.sinabro.sing_for_you.network.Service;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.gigamole.infinitecycleviewpager.HorizontalInfiniteCycleViewPager;
import com.gigamole.infinitecycleviewpager.VerticalInfiniteCycleViewPager;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class YoutubeActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {

    public static final String DEVELOPER_KEY = "AIzaSyDdhbfmdB-WWTGtZDZuOinA5EL0R66VH6g";
    public static final String VIDEO_ID = "WyiIGEHQP8o";
    public static final int RECOVERY_DIALOG_REQUEST = 1;

    YouTubePlayerFragment youTubePlayerFragment;

    ArrayList<Music> playerItems;
    private HorizontalInfiniteCycleViewPager infiniteCycleViewPager;
    PlayerCycleAdapter playerCycleAdapter;
    ArrayList<Music> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube);

        youTubePlayerFragment = (YouTubePlayerFragment)getFragmentManager().findFragmentById(R.id.youtubeplayerfragment);
        youTubePlayerFragment.initialize(DEVELOPER_KEY,this);

        Service service = HTTPConnection.getInstance().create(Service.class);

        service.getMusicList(2).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonArray jsonObject = response.body().getAsJsonArray("music");
                JsonArray jsonElements = jsonObject.getAsJsonArray();
                arrayList = initData(jsonElements);
                infiniteCycleViewPager = (HorizontalInfiniteCycleViewPager) findViewById(R.id.VerticalCycleViewPager);
                playerCycleAdapter = new PlayerCycleAdapter(arrayList,getApplicationContext());
                infiniteCycleViewPager.setAdapter(playerCycleAdapter);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });

//        HorizontalInfiniteCycleViewPager pager = (HorizontalInfiniteCycleViewPager) findViewById(R.id.VerticalCycleViewPager);
//        pager.setAdapter(new PlayerCycleAdapter(initData(),getApplicationContext()));

    }


    private ArrayList<Music> initData(JsonArray jsonElements){
        playerItems=new ArrayList<Music>();

        for(int i=0 ;i<jsonElements.size();i++) {
            JsonObject jsonObject = (JsonObject) jsonElements.get(i);
            String title = jsonObject.getAsJsonPrimitive("title").getAsString();
            String singer = jsonObject.getAsJsonPrimitive("singer").getAsString();
            String url = jsonObject.getAsJsonPrimitive("musicURL").getAsString();
            String imaurl = jsonObject.getAsJsonPrimitive("imgURL").getAsString();

            playerItems.add(new Music(title, singer, url, imaurl));
        }

//        playerItems.add(new PlayerItem());
//        playerItems.add(new PlayerItem("","좋은날","아이유",""));
//        playerItems.add(new PlayerItem("","가나다라마바사","세종대왕",""));

        return playerItems;
    }

    //플레이어가 초기화되지 못할 때 호출
    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult errorReason) {
        if(errorReason.isUserRecoverableError()){
            errorReason.getErrorDialog(this,RECOVERY_DIALOG_REQUEST).show();
        }else{
            String errorMessage = String.format("There was an error initializing the YouTubePlayer",errorReason.toString());
            Toast.makeText(this,errorMessage,Toast.LENGTH_LONG).show();
        }

    }

    //플레이어가 초기화될 때 호출
    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        if(!b){
            youTubePlayer.cueVideo(VIDEO_ID);
            youTubePlayer.
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == RECOVERY_DIALOG_REQUEST){
            getYouTubePlayerProvider().initialize(DEVELOPER_KEY,this);
        }
    }

    protected YouTubePlayer.Provider getYouTubePlayerProvider(){
        return (YouTubePlayerView) findViewById(R.id.youtubeplayerfragment);
    }
}
