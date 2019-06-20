package ch.philosoph.forsbergboard;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.net.Uri;

import java.io.IOException;
import java.net.URI;

public class Sound {

    private Context context;

    private String label;
    private int ressourceID;
    private Uri uri;

    public Sound(String label, int ressourceID){
        this.ressourceID = ressourceID;
        this.label = label;
    }

    public void play(MediaPlayer mp){
        mp.reset();
        try {
            mp.setDataSource(context, uri);
            mp.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mp.start();
    }

    public void setContext(Context context){
        this.context = context;
        Resources res = context.getResources();
        this.uri = new Uri.Builder()
                .scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
                .authority(res.getResourcePackageName(this.ressourceID))
                .appendPath(res.getResourceTypeName(this.ressourceID))
                .appendPath(res.getResourceEntryName(this.ressourceID))
                .build();
    }

    public String toString(){
        return label;
    }
}
