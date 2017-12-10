package com.tyler_buchheim.nowplaying;

import java.net.URL;
import java.util.Date;

public class Article {

    private String mSection;
    private String mTitle;
    private String mAuthors;
    private Date mPublicationDate;
    private URL mUrl;

    public Article(String section, String title, String authors, Date publicationDate, URL url) {
        mSection = section;
        mTitle = title;
        mAuthors = authors;
        mPublicationDate = publicationDate;
        mUrl = url;
    }

    public String getSection() {
        return mSection;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getAuthors() {
        return mAuthors;
    }

    public Date getPublicationDate() {
        return mPublicationDate;
    }

    public URL getUrl() {
        return mUrl;
    }
}
