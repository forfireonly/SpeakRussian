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

public class MainWordAdapter extends ArrayAdapter<Word> {
    public MainWordAdapter(@NonNull Activity context, ArrayList<Word> resource) {
        super(context, 0, resource);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItemView = convertView;

        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }
        Word currentWord = getItem(position);
        if (position == 0 || position == 5)
            listItemView.setBackgroundResource(R.color.numbers_background);
        if (position == 1 || position == 6)
            listItemView.setBackgroundResource(R.color.colors_background);
        if (position == 2 || position == 7)
            listItemView.setBackgroundResource(R.color.inside_background);
        if (position == 3) listItemView.setBackgroundResource(R.color.outside_background);
        if (position == 4) listItemView.setBackgroundResource(R.color.communication_background);


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
