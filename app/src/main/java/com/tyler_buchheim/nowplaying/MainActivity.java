package com.tyler_buchheim.nowplaying;

import android.app.LoaderManager;
import android.content.Loader;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Article>> {

    private ArticleAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView articleListView = (ListView) findViewById(R.id.list);
        final TextView emptyView = (TextView) findViewById(R.id.empty);
        mAdapter = new ArticleAdapter(this, new ArrayList<Article>());

        // Save state of scroll to preserve position on rotation
        Parcelable state = articleListView.onSaveInstanceState();

        articleListView.setAdapter(mAdapter);
        articleListView.setEmptyView(findViewById(R.id.empty));

        // Initialize loader if there is a connection or set empty view text
        final ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo initialNetworkInfo = connMgr.getActiveNetworkInfo();
        if (initialNetworkInfo != null && initialNetworkInfo.isConnected()) {
            getLoaderManager().initLoader(0, null, this);
        } else {
            mAdapter.clear();
            emptyView.setText(R.string.no_connection);
        }

        // Restore scroll state on rotation
        articleListView.onRestoreInstanceState(state);

        // Set up timer for refreshing and reloading articles

    }

    @Override
    public Loader<List<Article>> onCreateLoader(int i, Bundle bundle) {
        return new ArticleLoader(this);
    }

    @Override
    public void onLoadFinished(Loader<List<Article>> loader, List<Article> articles) {
        mAdapter.clear();
        TextView emptyView = (TextView) findViewById(R.id.empty);

        if (articles != null && !articles.isEmpty()) {
            mAdapter.addAll(articles);
        } else {
            emptyView.setText(R.string.no_results);
        }

        findViewById(R.id.progress_bar).setVisibility(View.GONE);
    }

    @Override
    public void onLoaderReset(Loader<List<Article>> loader) {
        mAdapter.clear();
    }

}
