package com.vikrant.newsapp;

import android.graphics.Bitmap;

/**
 * Created by Vikrant on 28-01-2018.
 */

public class News {
    private String mArticleType;
    private String mSectionName;
    private String mWeb_Paragraph;
    private String mWebUrl;
    private String mPublishTime;
    private Bitmap mThumbnail;

    public News(String ArticleType,String SectionName,String Web_Paragraph,String Web_Url,String PublishTime,Bitmap Thumbnail){
        mArticleType=ArticleType;
        mSectionName=SectionName;
        mWeb_Paragraph=Web_Paragraph;
        mWebUrl=Web_Url;
        mPublishTime=PublishTime;
        mThumbnail=Thumbnail;

    }
    public String getArticleType(){return mArticleType;}
    public String getSectionName(){return mSectionName;}
    public String getWeb_Paragraph(){return mWeb_Paragraph;}
    public String getWebUrl(){return mWebUrl;}
    public String getPublishTime(){return mPublishTime;}
    public Bitmap getThumbnail(){return mThumbnail;}


}
