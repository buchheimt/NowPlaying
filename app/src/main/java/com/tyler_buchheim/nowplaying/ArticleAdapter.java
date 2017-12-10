package com.tyler_buchheim.nowplaying;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ArticleAdapter extends ArrayAdapter<Article> {

    public ArticleAdapter(Activity context, ArrayList<Article> articles) {
        super(context, 0, articles);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item,
                    parent, false);
        }

        Article currentArticle = getItem(position);

        TextView sectionView = listItemView.findViewById(R.id.section);
        sectionView.setText(currentArticle.getSection());

        TextView titleView = listItemView.findViewById(R.id.title);
        titleView.setText(currentArticle.getTitle());

        // add authors and date fields as well

        return listItemView;
    }
}
