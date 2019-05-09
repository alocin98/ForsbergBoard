package com.example.forsbergboard;

import android.content.Context;
import android.media.MediaPlayer;

import java.net.URI;

public class Sound {

    private Context context;

    private String label;
    private String filename;

    public Sound(String label){
        this.label = label;
    }

    public void play(){
        MediaPlayer mp = MediaPlayer.create(context, R.raw.sample);
        mp.start();
    }

    public void setContext(Context context){
        this.context = context;
    }

    public String toString(){
        return label;
    }
}
