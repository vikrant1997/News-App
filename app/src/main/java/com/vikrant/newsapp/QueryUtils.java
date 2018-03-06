package com.vikrant.newsapp;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
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
import java.util.List;

/**
 * Created by Vikrant on 28-01-2018.
 */

public class QueryUtils{

        private QueryUtils(){}
private static final String LOG_TAG = QueryUtils.class.getSimpleName();


public static List<News> fetchNewsData(String requestUrl) {
        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
        jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
        Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        // Extract relevant fields from the JSON response and create a list of {@link Earthquake}s
        List<News> allNews = extractNews(jsonResponse);

        // Return the list of {@link Earthquake}s
        return allNews;
        }

private static URL createUrl(String stringurl){
        URL url=null;
        try {
        url=new URL(stringurl);
        }catch (MalformedURLException e){
        Log.e(LOG_TAG,"Problem Building the Url ",e);
        }
        return url;
        }

private static String makeHttpRequest(URL url)throws IOException{
        String jsonResponse="";
        if(url==null){
        return jsonResponse;
        }
        HttpURLConnection urlConnection=null;
        InputStream inputStream=null;
        try {
        urlConnection=(HttpURLConnection)url.openConnection();
        urlConnection.setReadTimeout(10000);
        urlConnection.setConnectTimeout(15000);
        urlConnection.setRequestMethod("GET");
        if(urlConnection.getResponseCode()==200){
        inputStream=urlConnection.getInputStream();
        jsonResponse=readFromStream(inputStream);
        }else{Log.e(LOG_TAG,"Error response code: "+urlConnection.getResponseCode());}

        }catch (IOException e) {
        Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
        } finally {
        if (urlConnection != null) {
        urlConnection.disconnect();
        }
        if (inputStream != null) {
        // Closing the input stream could throw an IOException, which is why
        // the makeHttpRequest(URL url) method signature specifies than an IOException
        // could be thrown.
        inputStream.close();
        }
        }
        return jsonResponse;
        }

private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
        BufferedReader reader = new BufferedReader(inputStreamReader);
        String line = reader.readLine();
        while (line != null) {
        output.append(line);
        line = reader.readLine();
        }
        }
        return output.toString();
        }


public static List<News> extractNews(String bookJSON){
        if(TextUtils.isEmpty(bookJSON)){
        return null;
        }
        List<News> news=new ArrayList<>();
        try {
        JSONObject baseJsonResponse=new JSONObject(bookJSON);
        JSONObject newsresponse=baseJsonResponse.getJSONObject("response");
        JSONArray newsResults=newsresponse.getJSONArray("results");

        for(int i=0;i<newsResults.length();i++){
        JSONObject currentNews=newsResults.getJSONObject(i);
       // JSONObject properties=currentBook.getJSONObject("volumeInfo");

        String ArticleType="-----";
        try {
        ArticleType=currentNews.getString("type");
        }catch (JSONException e){}

        String SectionName="-----";

        try {
        SectionName=currentNews.getString("sectionName");
        }catch (JSONException e){}

        String Web_Paragraph="-------";
        try{
            Web_Paragraph=currentNews.getString("webTitle");
        }
        catch (JSONException e){}
            String Web_Url="-------";
            try{
                Web_Url=currentNews.getString("webUrl");
            }
            catch (JSONException e){}
            String Web_Publication_Date="-------";
            try{
                Web_Publication_Date=currentNews.getString("webPublicationDate");
            }
            catch (JSONException e){}



        String imageString="";
        try{
                JSONObject fieldsString=currentNews.getJSONObject("fields");

                imageString=fieldsString.getString("thumbnail");
        }
        catch (JSONException e){

        }


        //new LoadImageFromURL().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,imageString);
        Bitmap FullImageBitMap= BitmapFactory.decodeResource(Resources.getSystem(),
        R.drawable.image);
        try {

        Log.i(LOG_TAG,imageString);
        URL url = new URL(imageString);
        InputStream imageStream = url.openConnection().getInputStream();
        FullImageBitMap = BitmapFactory.decodeStream(imageStream);


        } catch (MalformedURLException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
        } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
        }
        //Bitmap FullImageBitMap = new BitmapDrawable(BitmapFactory.decodeStream(new URL(imageString).openStream()));

        news.add(new News(ArticleType,SectionName,Web_Paragraph,Web_Url,Web_Publication_Date,FullImageBitMap));



        }
        }catch (JSONException e){
        Log.e("QueryUtils", "Problem parsing the books JSON results", e);
        }
        return news;
        }



   /* public static class LoadImageFromURL extends AsyncTask<String, Void, Bitmap> {
        private ImageView bmImage;
        private View mloadingIndicator;
        private List<Book> books=new ArrayList<>();
        private String mTitle,mAuthor,mPublisher;



        public LoadImageFromURL(String title, String author,String publisher) {

            this.mTitle
            //this.bmImage = Image;
           // this.mloadingIndicator = LoadingIndicator;
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            // TODO Auto-generated method stub

            try {
                String mystring = params[0];

                URL url = new URL(mystring);
                InputStream is = url.openConnection().getInputStream();
                Bitmap bitMap = BitmapFactory.decodeStream(is);
                return bitMap;

            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;

        }


        @Override
        protected void onPostExecute(Bitmap result) {
            // TODO Auto-generated method stub


            // View loadingIndicator = findViewById(R.id.loading_indicator2);
            mloadingIndicator.setVisibility(View.GONE);
            // String bookImage=context.getApplicationContext().getResources().getResourceName(R.id.BookImage);
            // BookImage.setImageResource(Integer.parseInt(bookImage));

           // bmImage.setImageBitmap(result);
            books.add(new Book(title,))


        }

    }*/
        }
