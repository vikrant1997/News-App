package com.vikrant.newsapp;


import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<News>> {

    private static final String LOG_TAG = MainActivity.class.getName();


    public static final String SEARCH_URL= "http://content.guardianapis.com/search?q=";
    public static final String ATTACH_URL="&show-fields=thumbnail&headline&publication&starRating&api-key=test";

    public static final String BOOKS_REQUEST_URL="http://content.guardianapis.com/search?q=bill%20gates&show-fields=thumbnail&headline&publication&starRating&api-key=test";
    public static  String FINAL_URL;

    private static final int NEWS_LOADER_ID = 1;
    private NewsAdapter mAdapter;
    /** TextView that is displayed when the list is empty */
    private TextView mEmptyStateTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText toSearch=findViewById(R.id.Search_show);
        String search=toSearch.getText().toString();

        if(search.isEmpty()||search==null){
            FINAL_URL=BOOKS_REQUEST_URL;

        }else{
            FINAL_URL=SEARCH_URL+search+ATTACH_URL;
        }
        Log.i(LOG_TAG,FINAL_URL);

        final ListView booksView=findViewById(R.id.list);


        mEmptyStateTextView = findViewById(R.id.empty_view);
        booksView.setEmptyView(mEmptyStateTextView);

        mAdapter=new NewsAdapter(this,new ArrayList<News>());
        booksView.setAdapter(mAdapter);
        Log.i(LOG_TAG,"selector called");
        booksView.setSelector(R.drawable.listselector);

        setConnectivity();

        Button SearchButton=findViewById(R.id.Search_Button);
        SearchButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                InputMethodManager inputManager = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);

                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
                EditText searchEditText=findViewById(R.id.Search_show);
                View loadingIndicator = findViewById(R.id.loading_indicator);
                mAdapter.clear();
                mEmptyStateTextView.setVisibility(View.GONE);
                loadingIndicator.setVisibility(View.VISIBLE);
                if(!(searchEditText.getText().toString()).isEmpty()){
                    //FINAL_URL=SEARCH_URL+searchEditText.getText().toString();
                    //Log.i(LOG_TAG,FINAL_URL);
                    getLoaderManager().restartLoader(2,null,MainActivity.this);
                    //setConnectivity();

                }else{
                    // FINAL_URL=BOOKS_REQUEST_URL;
                    //Log.i(LOG_TAG,FINAL_URL);
                    getLoaderManager().restartLoader(1,null,MainActivity.this);
                }

            }
        });

        booksView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                News currentNews = mAdapter.getItem(position);

                // Convert the String URL into a URI object (to pass into the Intent constructor)
               // Uri NewsUri = Uri.parse(currentNews.getWebUrl());

                // Create a new intent to view the earthquake URI
                Intent websiteIntent = new Intent(MainActivity.this,Show_News.class);

                websiteIntent.putExtra("NewsUri",currentNews.getWebUrl());

                // Send the intent to launch a new activity
                startActivity(websiteIntent);
            }
        });
    }


    @Override
    public Loader<List<News>> onCreateLoader(int i, Bundle bundle) {
        // Create a new loader for the given URL

        if(i==1){
            Log.i("FINAL_URI_PROCESSED",FINAL_URL);
            return new NewsLoader(this,FINAL_URL);
        }
        else{
            EditText search=findViewById(R.id.Search_show);

            FINAL_URL=SEARCH_URL+search.getText().toString()+ATTACH_URL;
            Log.i("FINAL_URI_PROCESSED",FINAL_URL);
            return new NewsLoader(this,FINAL_URL);


        }


    }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> books) {
        // Hide loading indicator because the data has been loaded
        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);

        // Set empty state text to display "No books found."

        mEmptyStateTextView.setText("NO NEWS");
        mEmptyStateTextView.setVisibility(View.VISIBLE);

        // Clear the adapter of previous earthquake data
        mAdapter.clear();

        // If there is a valid list of {@link Earthquake}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (books != null && !books.isEmpty()) {
            mAdapter.addAll(books);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<News>> loader) {
        // Loader reset, so we can clear out our existing data.
        mAdapter.clear();
    }

    public void setConnectivity(){

        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()) {
            // Get a reference to the LoaderManager, in order to interact with loaders.
            LoaderManager loaderManager = getLoaderManager();

            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            loaderManager.initLoader(NEWS_LOADER_ID, null, this);
        } else {
            // Otherwise, display error
            // First, hide loading indicator so error message will be visible
            View loadingIndicator = findViewById(R.id.loading_indicator);
            loadingIndicator.setVisibility(View.GONE);

            // Update empty state with no connection error message
            mEmptyStateTextView.setText("No Internet Connection");
        }
    }

}