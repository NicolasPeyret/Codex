package com.project.codex;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.raodevs.touchdraw.TouchDrawView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class AddNoteActivity extends AppCompatActivity {
    EditText title_input, page_input, book_id_input, content_input;
    String id;
    Button add_button;

    /**
     * onCreate method is called when the activity is created.
     * 
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        title_input = findViewById(R.id.title_input);
        page_input = findViewById(R.id.page_input);
        book_id_input = findViewById(R.id.book_id_input);
        content_input = findViewById(R.id.content_input);
        getAndSetIntentData();
        add_button = findViewById(R.id.add_button);
        add_button.setOnClickListener(new View.OnClickListener() {
            /**
             * onClick method is called when the add button is clicked.
             * 
             * @param view
             */
            @Override
            public void onClick(View view) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(AddNoteActivity.this);
                myDB.addNote(
                        Integer.parseInt(id),
                        title_input.getText().toString().trim(),
                        content_input.getText().toString().trim(),
                        Integer.valueOf(page_input.getText().toString().trim()));
            }
        });
    }

    /**
     * getAndSetIntentData method is called when the activity is created.
     */
    void getAndSetIntentData() {
        if (getIntent().hasExtra("book_id")) {
            id = getIntent().getStringExtra("book_id");
            book_id_input.setText(id);
        }
    }
}