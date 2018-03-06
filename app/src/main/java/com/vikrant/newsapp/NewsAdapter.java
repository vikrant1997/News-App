package com.vikrant.newsapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Vikrant on 30-01-2018.
 */

public class NewsAdapter extends ArrayAdapter<News> {


    public NewsAdapter(Context context, List<News> books) {
        super(context, 0, books);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if there is an existing list item view (called convertView) that we can reuse,
        // otherwise, if convertView is null, then inflate a new list item layout.


        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.news_list_item, parent, false);
        }


        // Find the earthquake at the given position in the list of earthquakes
        News currentNews = getItem(position);

        // Find the TextView with view ID magnitude
        TextView ArticleType = listItemView.findViewById(R.id.Type_show);
        ArticleType.setText(currentNews.getArticleType());
        TextView Section = listItemView.findViewById(R.id.Section_Show);
        Section.setText(currentNews.getSectionName());
        TextView paragraph = listItemView.findViewById(R.id.Paragraph);
        paragraph.setText(currentNews.getWeb_Paragraph());


        TextView Date_show = listItemView.findViewById(R.id.Date_Show);
        Date_show.setText(currentNews.getPublishTime());

        ImageView BookImage = listItemView.findViewById(R.id.Image_show);
        //View loadingIndicator = listItemView.findViewById(R.id.loading_indicator2);

            //loadingIndicator.setVisibility(View.GONE);
            BookImage.setImageBitmap(currentNews.getThumbnail());
            //} else {

            // new LoadImageFromURL(BookImage,loadingIndicator).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,currentBook.getImage());}


            return listItemView;
        }
    }


       /* public class LoadImageFromURL extends AsyncTask<String, Void, Bitmap> {
             private ImageView bmImage;
            private View mloadingIndicator;




            public LoadImageFromURL(ImageView Image, View LoadingIndicator) {

                this.bmImage = Image;
                this.mloadingIndicator = LoadingIndicator;
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
                bmImage.setImageBitmap(result);


            }

        }*/


