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
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class Outside extends AppCompatActivity {

    //we need private members for mediaplayer(to play audio files and audiomanager to gain focus of device to play audiofiles

    private MediaPlayer mMediaPlayer;
    private AudioManager mAudioManager;

    //we need on completion listener to release our media player

    private final MediaPlayer.OnCompletionListener mOncompletitionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            releaseMediaPlayer();
        }
    };
    // we need listener for change of audiofocus
    private final AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT || focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                mMediaPlayer.pause();
                mMediaPlayer.seekTo(0);
            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                mMediaPlayer.start();
            }else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
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
        actionBar.setHomeAsUpIndicator(R.drawable.cathedral);
        actionBar.setDisplayHomeAsUpEnabled(true);

        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        ArrayList<Word> outsideArray = new ArrayList<>();

        outsideArray.add(new Word(getString(R.string.yard), R.drawable.yard, R.raw.yard));
        outsideArray.add(new Word(getString(R.string.grass), R.drawable.grass, R.raw.grass));
        outsideArray.add(new Word(getString(R.string.tree), R.drawable.tree, R.raw.tree));
        outsideArray.add(new Word(getString(R.string.sky), R.drawable.sky, R.raw.sky));
        outsideArray.add(new Word(getString(R.string.clouds), R.drawable.clouds, R.raw.clouds));
        outsideArray.add(new Word(getString(R.string.warm), R.drawable.warm, R.raw.warm));
        outsideArray.add(new Word(getString(R.string.cold), R.drawable.cold, R.raw.cold));
        outsideArray.add(new Word(getString(R.string.hot), R.drawable.hot, R.raw.hot));
        outsideArray.add(new Word(getString(R.string.rain), R.drawable.rain, R.raw.rain));
        outsideArray.add(new Word(getString(R.string.snow), R.drawable.snow, R.raw.snow));

        WordAdapter itemsAdapter = new WordAdapter(this, outsideArray);
        ListView listView = findViewById(R.id.list);
        listView.setBackgroundColor(getResources().getColor(R.color.outside_background));

        listView.setAdapter(itemsAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                releaseMediaPlayer();
                Word word = outsideArray.get(position);
                int focus = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if (focus == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    mMediaPlayer = MediaPlayer.create(Outside.this, word.getmAudio() );
                    mMediaPlayer.start();

                    mMediaPlayer.setOnCompletionListener(mOncompletitionListener);
                }

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
        if(item.getItemId() == android.R.id.home) {
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