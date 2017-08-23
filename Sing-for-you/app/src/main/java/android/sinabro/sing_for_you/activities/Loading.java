package android.sinabro.sing_for_you.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.sinabro.sing_for_you.R;
import android.support.v7.app.AppCompatActivity;


public class Loading extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        Handler hd = new Handler();
        hd.postDelayed(new splashHandler(), 3000); // 3초 후에 hd Handler 실행
    }

    private class splashHandler implements Runnable {
        public void run() {
            startActivity(new Intent(getApplicationContext(), LoginActivity.class)); // 로딩이 끝난후 이동할 Activity
            finish();
        }
    }
}
