package ch.philosoph.forsbergboard;

import android.content.Context;
import android.media.AudioFocusRequest;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.forsbergboard.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayout ll = findViewById(R.id.linearlayout);



        SoundService soundService = new SoundService(getApplicationContext());
        soundService.fetchSounds();
        MediaPlayer mp = new MediaPlayer();


        for(Sound s: soundService.getSounds()){
            Button btn = new Button(this);
            btn.setText(s.toString().replace("_", " "));
            btn.setOnClickListener((click) -> play(s, mp));
            ll.addView(btn);
        }
    }

    private void play(Sound s, MediaPlayer mp){
            s.play(mp);
    }
}
