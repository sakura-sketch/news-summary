/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.datafrominternet;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.datafrominternet.model.ArrayData;
import com.example.android.datafrominternet.utilities.JsonUtils;
import com.example.android.datafrominternet.utilities.NetworkUtils;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    //41f63ec9981b4fe09852e87be7cd97d9 newsapi
    //http://newsapi.org/v2/top-headlines -G \
    //    -d country=us \
    //    -d apiKey=
    private EditText mSearchBoxEditText;

    //private TextView mUrlDisplayTextView;

    private TextView mSearchResultsTextView;


    TextView mErrorMessageTextView;

    ProgressBar mProgressBarLoad;

    Handler handler = new Handler();
    Runnable refresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //mSearchBoxEditText = (EditText) findViewById(R.id.et_search_box);

        //mUrlDisplayTextView = (TextView) findViewById(R.id.tv_url_display);
        //mSearchResultsTextView = (TextView) findViewById(R.id.tv_news_search_results_json);


        //mErrorMessageTextView = (TextView) findViewById(R.id.tv_error_message_display);

        //mProgressBarLoad = (ProgressBar) findViewById(R.id.pb_loading_indicator);

        refresh = new Runnable() {
            @Override
            public void run() {
                makeNewsSearchQuery();
                handler.postDelayed(refresh, 5000);
            }
        };
        handler.post(refresh);
    }


    private void makeNewsSearchQuery() {
        //String newsQuery = mSearchBoxEditText.getText().toString();
        URL newsSearchUrl = NetworkUtils.buildUrl("");
        //mUrlDisplayTextView.setText(newsSearchUrl.toString());
        new NewsQueryTask().execute(newsSearchUrl);
    }


    private void showJsonDataView(){
        //mErrorMessageTextView.setVisibility(View.INVISIBLE);
        //mSearchResultsTextView.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage(){
        //mErrorMessageTextView.setVisibility(View.VISIBLE);
        //mSearchResultsTextView.setVisibility(View.INVISIBLE);
    }
    public class NewsQueryTask extends AsyncTask<URL, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //mProgressBarLoad.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(URL... params) {
            URL searchUrl = params[0];
            String newsSearchResults = null;
            try {
                newsSearchResults = NetworkUtils.getResponseFromHttpUrl(searchUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return newsSearchResults;
        }

        @Override
        protected void onPostExecute(String newsSearchResults) {

            //mProgressBarLoad.setVisibility(View.INVISIBLE);
            if (newsSearchResults != null && !newsSearchResults.equals("")) {


                showJsonDataView();
                //mSearchResultsTextView.setText(newsSearchResults);
                try {
                    JSONObject jsonObject = new JSONObject(newsSearchResults);
                    JSONArray jsonArray = jsonObject.getJSONArray("articles");
                    ArrayData.getInstance().setData(jsonArray);
                    String[] stringJsonArray = new String[jsonArray.length()];
                    for(int i=0;i<stringJsonArray.length;i++){
                        stringJsonArray[i] = jsonArray.getJSONObject(i).getString("title");
                    }
                    Context context = MainActivity.this;
                    /*Picasso.with(this)
                            .load(news.getImageLink())
                            .into(ingredientsIv);*/
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(context,
                            android.R.layout.simple_expandable_list_item_1, stringJsonArray);
                    ListView listView = findViewById(R.id.news_listview);
                    listView.setAdapter(adapter);

                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                            launchDetailActivity(position);
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            else{
                showErrorMessage();
            }


        }
    }
    private void launchDetailActivity(int position) {
        Intent intent = new Intent(this, DetailedActivity.class);
        intent.putExtra(DetailedActivity.EXTRA_POSITION, position);

        startActivity(intent);
    }


    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemThatWasClickedId = item.getItemId();
        if (itemThatWasClickedId == R.id.action_search) {
            makeNewsSearchQuery();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }*/
}
