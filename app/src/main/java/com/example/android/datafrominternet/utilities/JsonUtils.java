package com.example.android.datafrominternet.utilities;

import com.example.android.datafrominternet.model.News;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static News parseNewsJson(String json) {

        try {
            JSONObject newsDetails = new JSONObject(json);
            News newsData = new News();

            //String titleData = newsDetails.getString("title");
            newsData.setTitle(newsDetails.getString("title"));
            newsData.setAuthor(newsDetails.getString("author"));
            newsData.setDate(newsDetails.getString("publishedAt"));
            newsData.setImageLink(newsDetails.getString("urlToImage"));
            JSONObject source = new JSONObject(newsDetails.getString("source"));
            newsData.setSource(source.getString("name"));
            newsData.setUrl(newsDetails.getString("url"));
            newsData.setDescription(newsDetails.getString("description"));
            return newsData;
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }


    }

}
