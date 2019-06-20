package ch.philosoph.forsbergboard;

import android.content.Context;
import android.media.AudioFocusRequest;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.forsbergboard.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayout ll = findViewById(R.id.linearlayout);

        TextView twitterlink = (TextView) findViewById(R.id.twitterlink);
        twitterlink.setText( Html.fromHtml("<a href=\"https://twitter.com/PhilosophenWG?lang=de\">(Follow us on Twitter)</a>"));
        twitterlink.setMovementMethod(LinkMovementMethod.getInstance());

        SoundService soundService = new SoundService(getApplicationContext());
        soundService.fetchSounds();
        MediaPlayer mp = new MediaPlayer();


        for(Sound s: soundService.getSounds()){
            Button btn = new Button(this);
            btn.setBackgroundColor(getResources().getColor(R.color.red));
            btn.setTextColor(getResources().getColor(R.color.white));
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            layoutParams.setMargins(5, 10, 5, 0);

            btn.setText(s.toString().replace("_", " "));
            btn.setOnClickListener((click) -> play(s, mp));
            ll.addView(btn, layoutParams);
        }
    }

    private void play(Sound s, MediaPlayer mp){
            s.play(mp);
    }
}
