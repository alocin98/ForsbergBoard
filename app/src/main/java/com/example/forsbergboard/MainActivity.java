package com.example.forsbergboard;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        LinearLayout ll = findViewById(R.id.listLayout);

        SoundService soundService = new SoundService(getApplicationContext());

        soundService.addSound(new Sound("test1"));
        soundService.addSound(new Sound("test2"));
        soundService.addSound(new Sound("test3"));
        soundService.addSound(new Sound("test4"));
        soundService.addSound(new Sound("test5"));
        soundService.addSound(new Sound("test6"));


        for(Sound s: soundService.getSounds()){
            Button btn = new Button(this);
            btn.setText(s.toString());
            btn.setOnClickListener((click) -> s.play());
            ll.addView(btn);
        }
    }
}
