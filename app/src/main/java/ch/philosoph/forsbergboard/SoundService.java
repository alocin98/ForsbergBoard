package ch.philosoph.forsbergboard;

import android.content.Context;

import com.example.forsbergboard.R;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class SoundService {

    private List<Sound> soundList;
    private Context context;

    //To be implemented
    private RemoruchService service;

    public SoundService(Context context){
        this.context = context;
        this.soundList = new ArrayList<Sound>();

        //To be implemented
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.remoruch.ch/jugendlager/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        service = retrofit.create(RemoruchService.class);
    }

    public void fetchSounds(){

        Field[] fields = R.raw.class.getFields();
        for(Field field: fields){
            Sound s = null;
            try {
                s = new Sound(field.getName(), field.getInt(field));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            this.addSound(s);
        }


    }

    public void addSound(Sound s){
        soundList.add(s);
        s.setContext(context);
    }

    public List<Sound> getSounds(){
        return this.soundList;
    }
}
