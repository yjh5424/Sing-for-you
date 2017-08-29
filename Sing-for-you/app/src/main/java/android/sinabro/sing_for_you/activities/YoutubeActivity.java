package android.sinabro.sing_for_you.activities;

import android.content.Intent;
import android.sinabro.sing_for_you.R;
import android.sinabro.sing_for_you.adpater.PlayerCycleAdapter;
import android.sinabro.sing_for_you.model.Music;
import android.sinabro.sing_for_you.model.PlayerItem;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.gigamole.infinitecycleviewpager.HorizontalInfiniteCycleViewPager;
import com.gigamole.infinitecycleviewpager.VerticalInfiniteCycleViewPager;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.google.android.youtube.player.YouTubePlayerView;

import java.util.ArrayList;
import java.util.List;


public class YoutubeActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {

    public static final String DEVELOPER_KEY = "AIzaSyDdhbfmdB-WWTGtZDZuOinA5EL0R66VH6g";
    public static final String VIDEO_ID = "WyiIGEHQP8o";
    public static final int RECOVERY_DIALOG_REQUEST = 1;

    YouTubePlayerFragment youTubePlayerFragment;

    ArrayList<Music> playerItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube);

        youTubePlayerFragment = (YouTubePlayerFragment)getFragmentManager().findFragmentById(R.id.youtubeplayerfragment);
        youTubePlayerFragment.initialize(DEVELOPER_KEY,this);

        TextView textView=(TextView)findViewById(R.id.tv_toolbar_title);
        textView.setText("YOUTUBE");



        HorizontalInfiniteCycleViewPager pager = (HorizontalInfiniteCycleViewPager) findViewById(R.id.VerticalCycleViewPager);
        playerItems=(ArrayList<Music>) getIntent().getSerializableExtra("list");
        pager.setAdapter(new PlayerCycleAdapter(playerItems,getApplicationContext()));

        for(String str:pager)

    }


   /* private ArrayList<PlayerItem> initData(){
        playerItems=new ArrayList<PlayerItem>();
        playerItems.add(new PlayerItem(R.drawable.album_image1,"벚꽃엔딩","버스커버스커"));
        playerItems.add(new PlayerItem(R.drawable.album_image2,"좋은날","아이유"));
        playerItems.add(new PlayerItem(R.drawable.album_image3,"가나다라마바사","세종대왕"));

        return playerItems;
    }*/

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
