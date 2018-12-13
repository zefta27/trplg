package palembang.gelumbang.zefta.uwalq.transitpalembang;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;

import palembang.gelumbang.zefta.uwalq.transitpalembang.Activity.HomeActivity;
import palembang.gelumbang.zefta.uwalq.transitpalembang.Activity.WelcomeActivity;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class SplashScreen extends AppCompatActivity {

    private  ProgressBar splashProgress;
    public static final int lama = 8;
    public static final int mililama = lama*1000;
    public static final int delay = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_map_content);


//        splashProgress= (ProgressBar) findViewById(R.id.splashProgress);
//        splashProgress.setMax(max_progress());
//        splashAnimation();

        // Set up the user interaction to manually show or hide the system UI.

        // Upon interacting with UI controls, delay any scheduled hide()
        // operations to prevent the jarring behavior of controls going away
        // while interacting with the UI.
    }

    public  void splashAnimation(){
        new CountDownTimer(mililama, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                splashProgress.setProgress(hitung_progress(millisUntilFinished));
            }

            @Override
            public void onFinish() {
                Intent pindah = new Intent(SplashScreen.this,WelcomeActivity.class);
                startActivity(pindah);
                finish();
            }
        }.start();
    }

    public int hitung_progress(long miliseconds){
        return (int)((mililama-miliseconds)/1000);
    }

    public int max_progress(){
        return lama-delay;
    }

}
