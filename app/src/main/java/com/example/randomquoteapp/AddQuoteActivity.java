package com.example.randomquoteapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AddQuoteActivity extends AppCompatActivity {

    EditText editQuote, editAuthor;
    Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_quote);

        editQuote = findViewById(R.id.editQuote);
        editAuthor = findViewById(R.id.editAuthor);
        btnSave = findViewById(R.id.btnSaveQuote);

        btnSave.setOnClickListener(v -> {

            Intent intent = new Intent();
            intent.putExtra("quote", editQuote.getText().toString());
            intent.putExtra("author", editAuthor.getText().toString());

            setResult(RESULT_OK, intent);
            finish();
        });
    }
}