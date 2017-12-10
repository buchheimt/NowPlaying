package com.tyler_buchheim.nowplaying;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.Date;
import java.text.*;

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

        TextView authorsView = listItemView.findViewById(R.id.authors);
        authorsView.setText(currentArticle.getAuthors());

        Date publicationDate = currentArticle.getPublicationDate();
        if (publicationDate != null) {
            TextView publicationDateView = listItemView.findViewById(R.id.publication_date);
            publicationDateView.setText(formatDate(publicationDate));
        }

        return listItemView;
    }

    private String formatDate(Date dateObject) {
        SimpleDateFormat format = new SimpleDateFormat("h:mm a - MMM dd, yyyy");
        return format.format(dateObject);
    }
}
