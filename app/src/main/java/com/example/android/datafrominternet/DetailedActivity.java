package com.example.android.datafrominternet;

import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.datafrominternet.model.ArrayData;
import com.squareup.picasso.Picasso;
import com.example.android.datafrominternet.model.News;
import com.example.android.datafrominternet.utilities.JsonUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.w3c.dom.Text;

import java.util.Locale;

public class DetailedActivity extends AppCompatActivity {
    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    TextView titleTextView;
    TextView authorTextView;
    TextView dateTextView;
    TextView descriptionTextView;
    TextView sourceTextView;
    TextView urlTextView;

    TextToSpeech t1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);

        ImageView ingredientsIv = (ImageView) findViewById(R.id.image_iv);


        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        JSONArray newsArray = ArrayData.getInstance().getData();
        if (newsArray == null) {
            closeOnError();
            return;
        }
        String[] stringNewsArray = new String[newsArray.length()];
        for (int i = 0; i < newsArray.length(); i++) {
            try {
                stringNewsArray[i] = newsArray.getJSONObject(i).toString();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        String json = stringNewsArray[position];
        News news = JsonUtils.parseNewsJson(json);
        if (news == null) {
            // News data unavailable
            closeOnError();
            return;
        }
        t1 = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if(i != TextToSpeech.ERROR) {
                    t1.setLanguage(Locale.UK);
                }
            }
        });
        populateUI(news);
        Picasso.with(this)
                .load(news.getImageLink())
                .into(ingredientsIv);

        setTitle(news.getTitle());

    }




    private void closeOnError() {
        finish();
        Toast.makeText(this, "News Data not found", Toast.LENGTH_SHORT).show();
    }


    private void populateUI(News news) {
        titleTextView = (TextView) findViewById(R.id.title_tv);
        descriptionTextView = (TextView) findViewById(R.id.description_tv);
        authorTextView = (TextView) findViewById(R.id.author_tv);
        dateTextView = (TextView) findViewById(R.id.publishedAt_tv);
        urlTextView = (TextView) findViewById(R.id.url_tv);
        sourceTextView = (TextView) findViewById(R.id.source_tv);

        sourceTextView.setText(news.getSource()+ "\n");
        descriptionTextView.setText(news.getDescription()+"\n");
        urlTextView.setText(news.getUrl());
        titleTextView.setText(news.getTitle() + "\n");
        dateTextView.setText(news.getDate() + "\n");
        authorTextView.setText(news.getAuthor() + "\n");

        t1.speak(descriptionTextView.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);
    }


}