package com.image;

public class  Projects {

    public String title;
    public String keyword;
    public String description;
    public String smallImageUrl;
    public String bigImageUrl;
    public int cost;

    @Override
    public String toString()
    {
        return "Title: "+title+ " Keyword: "+keyword+ " Image: "+smallImageUrl;

    }


}