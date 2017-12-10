package com.tyler_buchheim.nowplaying;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class QueryUtils {

    private static final String URL_STRING = "http://content.guardianapis.com/search?section=film|tv-and-radio&order-by=newest&api-key=test&page-size=25&show-tags=contributor";
    private static final String LOG_TAG = QueryUtils.class.getName();

    private QueryUtils() {}

    public static ArrayList<Article> fetchArticleData() {
        URL url = createUrl();

        String jsonResponse = "";
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "The HTTP connection failed: " + e);
        }

        return extractArticles(jsonResponse);
    }

    private static URL createUrl() {
        URL url = null;

        try {
            url = new URL(URL_STRING);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "There was an error formatting url: " + e);
        }

        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.connect();
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Connection did not return a 200 ok response, URL: " + url.toString());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "There was an error connecting to the Guardian API: " + e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }

        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();

        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream,
                    Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }

        return output.toString();
    }

    private static ArrayList<Article> extractArticles(String root) {
        if (root.isEmpty()) {
            return null;
        }

        ArrayList<Article> articles = new ArrayList<>();

        try {
            JSONObject rootJSON = new JSONObject(root);
            JSONObject responseJSON = rootJSON.getJSONObject("response");
            JSONArray articlesJSON = responseJSON.getJSONArray("results");
            for (int i = 0; i < articlesJSON.length(); i++) {
                JSONObject articleJSON = articlesJSON.getJSONObject(i);

                String section = articleJSON.getString("sectionName");
                String title = articleJSON.getString("webTitle");

                articles.add(new Article(section, title, null, null, null));
            }
        } catch (JSONException e) {
            Log.e(LOG_TAG, "There was an error during JSON parsing: " + e);
        }

        return articles;
    }

}
