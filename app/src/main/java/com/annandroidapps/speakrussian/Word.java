package com.annandroidapps.speakrussian;

public class Word {

    private final String mWord;

    private final int mImage;

    private final int mAudio;

    public Word(String word, int image, int audio) {
        mWord = word;
        mImage = image;
        mAudio = audio;
    }

    public Word(String word, int audio) {
        mWord = word;
        mImage = -1;
        mAudio = audio;

    }

    public String getmWord() {
        return mWord;
    }

    public int getmImage() {
        return mImage;
    }

    public int getmAudio() {
        return mAudio;
    }
}
