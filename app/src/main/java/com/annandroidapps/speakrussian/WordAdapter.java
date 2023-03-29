package com.annandroidapps.speakrussian;

import android.app.Activity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class WordAdapter extends ArrayAdapter<Word> {

    public WordAdapter(@NonNull Activity context, ArrayList<Word> resource) {
        super(context,0, resource);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }
        Word currentWord = getItem(position);

        ImageView image = listItemView.findViewById(R.id.image);
        int imageResource = currentWord.getmImage();
        if (imageResource != -1)
            image.setImageResource(currentWord.getmImage());

        TextView word = listItemView.findViewById(R.id.word);

        if (imageResource == -1) {
            image.setVisibility(View.GONE);
            word.setGravity(Gravity.CENTER_VERTICAL);
        }
        word.setText(currentWord.getmWord());

        return listItemView;
    }
}
