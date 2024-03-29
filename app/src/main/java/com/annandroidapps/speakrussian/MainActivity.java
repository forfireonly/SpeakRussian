package com.annandroidapps.speakrussian;


import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import com.google.android.gms.tasks.Task;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.model.UpdateAvailability;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private MediaPlayer mMediaPlayer;
    private AudioManager mAudioManager;
    private final MediaPlayer.OnCompletionListener mCompletitionListener = mediaPlayer -> releaseMediaPlayer();
    private final AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT || focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                mMediaPlayer.pause();
                mMediaPlayer.seekTo(0);
            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                mMediaPlayer.start();
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                releaseMediaPlayer();
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppUpdateManager appUpdateManager = AppUpdateManagerFactory.create(this);
// Returns an intent object that you use to check for an update.
        Task<AppUpdateInfo> appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();
// Checks that the platform will allow the specified type of update.
        appUpdateInfoTask.addOnSuccessListener(appUpdateInfo -> {
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE) {
                // Request the update.
                new UpgradeAvailableDialog().show(getSupportFragmentManager(),UpgradeAvailableDialog.TAG );
            }
        });

        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        ArrayList<Word> categoriesArray = new ArrayList<>();

        categoriesArray.add(new Word(getString(R.string.category_numbers), R.drawable.numbers, R.raw.numbers));
        categoriesArray.add(new Word(getString(R.string.category_colors), R.drawable.colors, R.raw.colors));
        categoriesArray.add(new Word(getString(R.string.category_inside), R.drawable.inside, R.raw.inside));
        categoriesArray.add(new Word(getString(R.string.category_outside), R.drawable.outside, R.raw.outside));
        categoriesArray.add(new Word(getString(R.string.category_phrases), R.drawable.phrases, R.raw.phrases));
        categoriesArray.add(new Word(getString(R.string.category_animals), R.drawable.animals, R.raw.animals));
        categoriesArray.add(new Word(getString(R.string.category_food), R.drawable.food, R.raw.food));
        categoriesArray.add(new Word(getString(R.string.category_travel), R.drawable.travel, R.raw.travel));

        MainWordAdapter itemsAdapter = new MainWordAdapter(this, categoriesArray);
        ListView listView = findViewById(R.id.list_categories);

        //listView.setBackgroundColor(getResources().getColor(R.color.numbers_background));

        listView.setAdapter(itemsAdapter);

        listView.setOnItemClickListener((AdapterView<?> adapterView, View view, int position, long l) -> {
            releaseMediaPlayer();
            Word word = categoriesArray.get(position);


            int focus = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

            if (focus == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                mMediaPlayer = MediaPlayer.create(MainActivity.this, word.getmAudio());
                mMediaPlayer.start();
                mMediaPlayer.setOnCompletionListener(mCompletitionListener);
            }

            if (position == 0) {
                Intent i = new Intent(MainActivity.this, Numbers.class);
                startActivity(i);
            } else if (position == 1) {
                Intent i = new Intent(MainActivity.this, Colors.class);
                startActivity(i);
            } else if (position == 2) {
                Intent i = new Intent(MainActivity.this, Inside.class);
                startActivity(i);
            } else if (position == 3) {
                Intent i = new Intent(MainActivity.this, Outside.class);
                startActivity(i);
            } else if (position == 4) {
                Intent i = new Intent(MainActivity.this, Communication.class);
                startActivity(i);
            } else if (position == 5) {
                Intent i = new Intent(MainActivity.this, Animals.class);
                startActivity(i);
            } else if (position == 6) {
                Intent i = new Intent(MainActivity.this, Food.class);
                startActivity(i);
            } else if (position == 7) {
                Intent i = new Intent(MainActivity.this, Travel.class);
                startActivity(i);
            }

        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }
    private void releaseMediaPlayer() {
        if (mMediaPlayer != null) {
            mMediaPlayer.release();

            mMediaPlayer = null;
            mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
        }
    }
}