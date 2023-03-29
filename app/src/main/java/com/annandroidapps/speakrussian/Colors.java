package com.annandroidapps.speakrussian;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class Colors extends AppCompatActivity {

    private MediaPlayer mMediaPlayer;

    private AudioManager mAudioManager;

    private final MediaPlayer.OnCompletionListener mCompletitionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            releaseMediaPlayer();
        }
    };

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
        switch (item.getItemId()) {
            case android.R.id.home:
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
        actionBar.setHomeAsUpIndicator(R.drawable.cathedral);
        actionBar.setDisplayHomeAsUpEnabled(true);


        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        ArrayList<Word> colorsArray = new ArrayList<>();

        colorsArray.add(new Word(getString(R.string.red), R.drawable.circle_red, R.raw.red));
        colorsArray.add(new Word(getString(R.string.blue), R.drawable.circle_blue, R.raw.blue));
        colorsArray.add(new Word(getString(R.string.yellow), R.drawable.circle_yellow, R.raw.yellow));
        colorsArray.add(new Word(getString(R.string.green), R.drawable.circle_green, R.raw.green));
        colorsArray.add(new Word(getString(R.string.orange), R.drawable.circle_orange, R.raw.orange));
        colorsArray.add(new Word(getString(R.string.purple), R.drawable.circle_purple, R.raw.purple));
        colorsArray.add(new Word(getString(R.string.pink), R.drawable.circle_pink, R.raw.pink));
        colorsArray.add(new Word(getString(R.string.brown), R.drawable.circle_brown, R.raw.brown));
        colorsArray.add(new Word(getString(R.string.white), R.drawable.circle_white, R.raw.white));
        colorsArray.add(new Word(getString(R.string.black), R.drawable.circle_black, R.raw.black));

        WordAdapter itemsAdapter = new WordAdapter(this, colorsArray);
        ListView listView = findViewById(R.id.list);
        listView.setBackgroundColor(getResources().getColor(R.color.colors_background));

        listView.setAdapter(itemsAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                releaseMediaPlayer();

                Word word = colorsArray.get(position);

                int focus = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if (focus == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    mMediaPlayer = MediaPlayer.create(Colors.this, word.getmAudio());
                    mMediaPlayer.start();
                    mMediaPlayer.setOnCompletionListener(mCompletitionListener);
                }
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