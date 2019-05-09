package com.example.forsbergboard;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class SoundService {

    private List<Sound> soundList;
    private Context context;

    public SoundService(Context context){
        this.context = context;
        this.soundList = new ArrayList<Sound>();
    }

    public void addSound(Sound s){
        soundList.add(s);
        s.setContext(context);
    }

    public List<Sound> getSounds(){
        return this.soundList;
    }
}
