package com.vikrant.newsapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by Vikrant on 30-01-2018.
 */

public class Show_News extends AppCompatActivity {
    private WebView wv1;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_news);
        // Hide loading indicator because the data has been loaded
        View loadingIndicator = findViewById(R.id.loading_indicator2);
        loadingIndicator.setVisibility(View.VISIBLE);

        Intent intent=getIntent();
        Uri NewsUri = Uri.parse(intent.getStringExtra("NewsUri"));
        WebView webView = (WebView) findViewById(R.id.WebView);

//Specify the URL you want to display//
        webView.loadUrl(String.valueOf(NewsUri));

        webView.setWebViewClient(new WebViewClient() {

            public void onPageFinished(WebView view, String url) {
                View loadingIndicator = findViewById(R.id.loading_indicator2);
                loadingIndicator.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.show_news_menu, menu);

        //Intent newsIntent=new Intent(Intent.ACTION_VIEW,NewsUri);
        //startActivity(newsIntent);

        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);

        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {

            case android.R.id.home:

                   // NavUtils.navigateUpFromSameTask(Show_News.this);
                finish();
                    return true;

        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
    super.onBackPressed();
    finish();

    }
   /* private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Intent intent=getIntent();
            Uri NewsUri = Uri.parse(intent.getStringExtra("NewsUri"));
            view.loadUrl(String.valueOf(NewsUri));
            return true;
        }
    }*/

}
