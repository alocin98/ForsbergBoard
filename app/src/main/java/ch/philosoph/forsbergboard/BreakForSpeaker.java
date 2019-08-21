package ch.philosoph.forsbergboard;

import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.speech.tts.Voice;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.example.forsbergboard.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class BreakForSpeaker extends AppCompatActivity {

    private TextToSpeech textToSpeech;
    private static final String TAG = "BreakForSpeakerActivity";
    private volatile boolean stopThread = false;
    private boolean isPlaying = false;

    // View components
    private EditText t2sText;
    private Spinner perSoundsSpinner;
    private ImageButton randomPlayBtn;
    private Switch t2sAfterPerSwitch;

    List<PerSound> perSoundList = MainActivity.getPerSounds();
    MediaPlayer mp = new MediaPlayer();
    int numberOfSounds;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_break_for_speaker);
        numberOfSounds = perSoundList.size();

        t2sText = findViewById(R.id.text_to_speech);
        perSoundsSpinner = findViewById(R.id.spinner_per_sound);
        randomPlayBtn = findViewById(R.id.random_play_btn);
        t2sAfterPerSwitch = findViewById(R.id.t2s_after_per);


        textToSpeech = new TextToSpeech(getApplicationContext(), status -> {
            if (status == TextToSpeech.SUCCESS) {
                int ttsLang = textToSpeech.setLanguage(Locale.UK);
                Set<String> a = new HashSet<>();
                a.add("male");
                Voice v = new Voice("en-gb-x-fis#male_3-local", Locale.UK, Voice.QUALITY_HIGH, Voice.LATENCY_HIGH, true, a);
                textToSpeech.setVoice(v);

                if (ttsLang == TextToSpeech.LANG_MISSING_DATA || ttsLang == TextToSpeech.LANG_NOT_SUPPORTED) {
                    Log.e("TextToSpeak", "The Language is not supported!");
                } else {
                    Log.i("TTS", "Language Supported.");
                }

                Log.i("TTS", "Initialization success.");
            } else {
                Toast.makeText(getApplicationContext(), "TTS Initialization failed!", Toast.LENGTH_SHORT).show();
            }
        });

        ArrayList<String> temp = new ArrayList<>();

        for (PerSound sound : perSoundList) {
            temp.add(sound.getDisplayName());
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, temp);

        perSoundsSpinner.setAdapter(arrayAdapter);

    }


    @Override
    protected void onPause() {
        super.onPause();
        stopThread = true;
        mp.stop();
    }

    public void playRandomPerSounds(View view) {
        if (!isPlaying) {
            stopThread = false;
            RandomPerRunnable runnable = new RandomPerRunnable(100);
            new Thread(runnable).start();
            isPlaying = true;
            randomPlayBtn.setImageResource(R.drawable.ic_pause_circle_outline_black_24dp);
        } else {
            stopThread = true;
            isPlaying = false;
            randomPlayBtn.setImageResource(R.drawable.ic_play_circle_outline_black_24dp);
        }
    }

    public void replaceSpeaker(View view) {

        int selectedSoundIndex = perSoundsSpinner.getSelectedItemPosition();

        if (t2sAfterPerSwitch.isChecked()) {
            textToSpeech.setOnUtteranceProgressListener(null);
            letPerSpeak(selectedSoundIndex, 0);
            sayText();
        } else {
            sayText();
            textToSpeech.setOnUtteranceProgressListener(new MyUtteranceProgressListener(selectedSoundIndex, mp));

        }
    }


    private void letPerSpeak(int index, int timeout) {
        perSoundList.get(index).getSound().play(mp);
        int time = mp.getDuration();
        try {
            Thread.sleep(time + timeout);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void sayText() {
        String text = t2sText.getText().toString();

        HashMap<String, String> map = new HashMap<>();
        map.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "MessageId");
        int speechStatus = textToSpeech.speak(text, TextToSpeech.QUEUE_ADD, map);

        if (speechStatus == TextToSpeech.ERROR) {
            Log.e("TTS", "Error in converting Text to Speech!");
        }
    }


    class RandomPerRunnable implements Runnable {

        int iterations;

        RandomPerRunnable(int iterations) {
            this.iterations = iterations;
        }

        @Override
        public void run() {
            for (int i = 0; i < iterations; i++) {
                if (stopThread) {
                    mp.stop();
                    return;
                }
                Log.d(TAG, "" + i);
                letPerSpeak((int) (Math.random() * numberOfSounds), 550);
            }
        }
    }

    class MyUtteranceProgressListener extends UtteranceProgressListener {

        int soundIndex;
        MediaPlayer mp;

        MyUtteranceProgressListener(int soundIndex, MediaPlayer mp){
            this.soundIndex = soundIndex;
            this.mp = mp;
        }

        @Override
        public void onStart(String utteranceId) {}

        @Override
        public void onDone(String utteranceId) {

            new Thread() {
                public void run() {
                    perSoundList.get(soundIndex).getSound().play(mp);
                }
            }.start();
        }

        @Override
        public void onError(String utteranceId) {}
    }
}
