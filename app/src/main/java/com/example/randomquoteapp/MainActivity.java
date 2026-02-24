package com.example.randomquoteapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Random;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.InputStream;
import java.util.ArrayList;
import android.graphics.Color;

public class MainActivity extends AppCompatActivity {

    TextView textQuote, textAuthor;
    Button btnNewQuote, btnAddQuote;
    DBHelper db;

    ArrayList<String> quotes = new ArrayList<>();
    ArrayList<String> authors = new ArrayList<>();
    Random random = new Random();
    LinearLayout mainLayout;

    String[] colors = {
            "#FFCDD2",
            "#C8E6C9",
            "#BBDEFB",
            "#FFE0B2",
            "#E1BEE7",
            "#B2DFDB",
            "#F8BBD0"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textQuote = findViewById(R.id.textQuote);
        textAuthor = findViewById(R.id.textAuthor);
        btnNewQuote = findViewById(R.id.btnNewQuote);
        btnAddQuote = findViewById(R.id.btnAddQuote);
        mainLayout = findViewById(R.id.mainLayout);

        db = new DBHelper(this);

        loadQuotesFromJson();   // JSON load
        showRandomQuote();      // First quote

        btnNewQuote.setOnClickListener(v -> showRandomQuote());

        btnAddQuote.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddQuoteActivity.class);
            startActivityForResult(intent, 1);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {

            String q = data.getStringExtra("quote");
            String a = data.getStringExtra("author");

            db.insertQuote(q, a);

            quotes.add(q);
            authors.add(a);
        }
    }

    private void loadQuotesFromJson() {
        try {
            InputStream is = getAssets().open("quotes.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            String json = new String(buffer, "UTF-8");
            JSONArray jsonArray = new JSONArray(json);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                quotes.add(obj.getString("quote"));
                authors.add(obj.getString("author"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showRandomQuote() {

        if (quotes.size() == 0) return;

        int index = random.nextInt(quotes.size());
        textQuote.setText(quotes.get(index));
        textAuthor.setText("- " + authors.get(index));

        int colorIndex = random.nextInt(colors.length);
        mainLayout.setBackgroundColor(Color.parseColor(colors[colorIndex]));
    }
}