package ch.philosoph.forsbergboard;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.forsbergboard.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MyRecyclerViewAdapter.ItemClickListener {

    MyRecyclerViewAdapter adapter;
    static List<PerSound> perSounds = new ArrayList<>();
    MediaPlayer mp = new MediaPlayer();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        LinearLayout ll = findViewById(R.id.linearlayout);

        TextView twitterlink = (TextView) findViewById(R.id.twitterlink);
        twitterlink.setText(Html.fromHtml("<a href=\"https://twitter.com/PhilosophenWG?lang=de\">(Follow us on Twitter)</a>"));
        twitterlink.setMovementMethod(LinkMovementMethod.getInstance());

        SoundService soundService = new SoundService(getApplicationContext());
        soundService.fetchSounds();


        for (Sound s : soundService.getSounds()) {
            PerSound perSound = new PerSound(s, s.toString().replace("_", " "));
            perSounds.add(perSound);
        }

        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.perSoundItem);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyRecyclerViewAdapter(this, perSounds);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);

    }

    private void play(Sound s, MediaPlayer mp) {
        s.play(mp);
    }


    @Override
    public void onItemClick(View view, int position) {
        mp.stop();
        play(perSounds.get(position).getSound(), mp);
    }

    /**
     * Adds {@link MenuItem}s to the {@link Menu}.
     * The MenuItem is a SearchView which is used for filter the different sounds
     *
     * @param menu Menu of the activity
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapter.getFilter().filter(s);
                return false;
            }
        });
        return true;
    }


    public void openBreakForSpeaker(MenuItem item) {
        Intent intent = new Intent(this, BreakForSpeaker.class);
        startActivity(intent);
    }

    public static List<PerSound> getPerSounds() {
        return perSounds;
    }
}
