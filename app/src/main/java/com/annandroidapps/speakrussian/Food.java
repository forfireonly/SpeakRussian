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

public class Food extends AppCompatActivity {
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
            mMediaPlayer = MediaPlayer.create(Food.this, R.raw.food);
            mMediaPlayer.start();
            mMediaPlayer.setOnCompletionListener(mCompletitionListener);
        }

        ArrayList<Word> foodArray = new ArrayList<>();

        foodArray.add(new Word(getString(R.string.breakfast), R.drawable.breakfast, R.raw.breakfast));
        foodArray.add(new Word(getString(R.string.lunch), R.drawable.lunch, R.raw.lunch));
        foodArray.add(new Word(getString(R.string.dinner), R.drawable.dinner, R.raw.dinner));
        foodArray.add(new Word(getString(R.string.cheese), R.drawable.cheese,R.raw.cheese));
        foodArray.add(new Word(getString(R.string.sausage), R.drawable.sausage, R.raw.sausage));
        foodArray.add(new Word(getString(R.string.egg), R.drawable.egg, R.raw.egg));
        foodArray.add(new Word(getString(R.string.tomato), R.drawable.tomato, R.raw.tomato));
        foodArray.add(new Word(getString(R.string.avocado), R.drawable.avocado, R.raw.avocado));
        foodArray.add(new Word(getString(R.string.chips), R.drawable.chips, R.raw.chips));
        foodArray.add(new Word(getString(R.string.grapes), R.drawable.grapes, R.raw.grapes));


        WordAdapter itemsAdapter = new WordAdapter(this, foodArray);
        ListView listView = findViewById(R.id.list);
        listView.setBackgroundColor(getResources().getColor(R.color.colors_background));

        listView.setAdapter(itemsAdapter);

        listView.setOnItemClickListener((adapterView, view, position, l) -> {
            releaseMediaPlayer();

            Word word = foodArray.get(position);

            focus = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

            if (focus == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                mMediaPlayer = MediaPlayer.create(Food.this, word.getmAudio());
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
