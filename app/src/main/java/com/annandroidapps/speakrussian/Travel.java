package com.annandroidapps.speakrussian;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;

public class Travel extends AppCompatActivity {
    private MediaPlayer mMediaPlayer;

    private AudioManager mAudioManager;

    private int focus;

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
    protected void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.words_list);

        Toolbar myToolBar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolBar);
        ActionBar actionBar= getSupportActionBar();

        assert actionBar != null;
        actionBar.setHomeAsUpIndicator(R.drawable.cathedralblue);
        actionBar.setDisplayHomeAsUpEnabled(true);

        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        focus = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

        if (focus == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
            mMediaPlayer = MediaPlayer.create(Travel.this, R.raw.travel);
            mMediaPlayer.start();
            mMediaPlayer.setOnCompletionListener(mCompletitionListener);
        }

        ArrayList<Word> travelArray = new ArrayList<>();

        travelArray.add(new Word(getString(R.string.car), R.drawable.car, R.raw.car));
        travelArray.add(new Word(getString(R.string.train), R.drawable.train, R.raw.train));
        travelArray.add(new Word(getString(R.string.plane), R.drawable.plane, R.raw.plane));
        travelArray.add(new Word(getString(R.string.bus), R.drawable.bus, R.raw.bus));
        travelArray.add(new Word(getString(R.string.road), R.drawable.road, R.raw.road));
        travelArray.add(new Word(getString(R.string.ticket), R.drawable.ticket, R.raw.ticket));
        travelArray.add(new Word(getString(R.string.luggage), R.drawable.luggage, R.raw.lugage));
        travelArray.add(new Word(getString(R.string.city), R.drawable.city, R.raw.city));
        travelArray.add(new Word(getString(R.string.hotel), R.drawable.hotel, R.raw.hotel));
        travelArray.add(new Word(getString(R.string.museum), R.drawable.museum, R.raw.museum));

        WordAdapter itemsAdapter = new WordAdapter(this, travelArray);
        ListView listView = findViewById(R.id.list);
        listView.setBackgroundColor(getResources().getColor(R.color.inside_background));

        listView.setAdapter(itemsAdapter);

        listView.setOnItemClickListener((adapterView, view, position, l) -> {
            releaseMediaPlayer();

            Word word = travelArray.get(position);

            focus = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

            if (focus == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                mMediaPlayer = MediaPlayer.create(Travel.this, word.getmAudio());
                mMediaPlayer.start();
                mMediaPlayer.setOnCompletionListener(mCompletitionListener);
            }
        });
    }

    private void releaseMediaPlayer() {
        if (mMediaPlayer != null) {
            mMediaPlayer.release();
            mMediaPlayer = null;
            mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
        }
    }
}
