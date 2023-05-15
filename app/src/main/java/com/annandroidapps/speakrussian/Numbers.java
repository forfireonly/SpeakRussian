package com.annandroidapps.speakrussian;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;
import java.util.ArrayList;

public class Numbers extends AppCompatActivity {

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
        setContentView(R.layout.words_list);

        Toolbar myToolBar = findViewById(R.id.toolbar);

        setSupportActionBar(myToolBar);
        ActionBar actionBar= getSupportActionBar();

        assert actionBar != null;
        actionBar.setHomeAsUpIndicator(R.drawable.cathedralblue);
        actionBar.setDisplayHomeAsUpEnabled(true);

        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        ArrayList<Word> numbersArray = new ArrayList<>();

        numbersArray.add(new Word(getString(R.string.one), R.drawable.one, R.raw.one));
        numbersArray.add(new Word(getString(R.string.two), R.drawable.two, R.raw.two));
        numbersArray.add(new Word(getString(R.string.three), R.drawable.three, R.raw.three));
        numbersArray.add(new Word(getString(R.string.four), R.drawable.four, R.raw.four));
        numbersArray.add(new Word(getString(R.string.five), R.drawable.five, R.raw.five));
        numbersArray.add(new Word(getString(R.string.six), R.drawable.six, R.raw.six));
        numbersArray.add(new Word(getString(R.string.seven), R.drawable.seven, R.raw.seven));
        numbersArray.add(new Word(getString(R.string.eight), R.drawable.eight, R.raw.eight));
        numbersArray.add(new Word(getString(R.string.nine), R.drawable.nine, R.raw.nine));
        numbersArray.add(new Word(getString(R.string.ten), R.drawable.ten, R.raw.ten));


        WordAdapter itemsAdapter = new WordAdapter(this, numbersArray);
        ListView listView = findViewById(R.id.list);
        listView.setBackgroundColor(getResources().getColor(R.color.numbers_background));

        listView.setAdapter(itemsAdapter);

        listView.setOnItemClickListener((adapterView, view, position, l) -> {
            releaseMediaPlayer();
            Word word = numbersArray.get(position);

            int focus = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

            if (focus == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                mMediaPlayer = MediaPlayer.create(Numbers.this, word.getmAudio());
                mMediaPlayer.start();
                mMediaPlayer.setOnCompletionListener(mCompletitionListener);
            }
        });

    }

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

    private void releaseMediaPlayer() {
        if (mMediaPlayer != null) {
            mMediaPlayer.release();


            mMediaPlayer = null;
            mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);

        }
    }
}

