package com.project.codex;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class UpdateBookActivity extends AppCompatActivity {

    EditText title_input, author_input, pages_input, img_input;
    ImageView book_img_view;

    Button update_button;
    FloatingActionButton add_button;
    Button delete_button;

    String id, title, author, pages, img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_book);

        book_img_view = findViewById(R.id.book_img_2);
        title_input = findViewById(R.id.title_input2);
        add_button = findViewById(R.id.add_button_note);
        author_input = findViewById(R.id.author_input2);
        img_input = findViewById(R.id.img_input2);
        pages_input = findViewById(R.id.pages_input2);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UpdateBookActivity.this, AddNoteActivity.class);
                startActivity(intent);
            }
        });
        update_button = findViewById(R.id.update_button);
        delete_button = findViewById(R.id.delete_button);

        getAndSetIntentData();

        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle(title);
        }
        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(UpdateBookActivity.this);
                title=title_input.getText().toString().trim();
                author=author_input.getText().toString().trim();
                img=img_input.getText().toString().trim();
                pages=pages_input.getText().toString().trim();
                myDB.updateBook(id, title, author, img, pages);
                finish();
            }
        });
        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDialog();
            }
        });
    }

    void getAndSetIntentData() {
        if (getIntent().hasExtra("id")
                && getIntent().hasExtra("title")
                && getIntent().hasExtra("author")
                && getIntent().hasExtra("image")
                && getIntent().hasExtra("pages")
        ) {
            //GET
            id = getIntent().getStringExtra("id");
            title = getIntent().getStringExtra("title");
            author = getIntent().getStringExtra("author");
            img = getIntent().getStringExtra("image");
            pages = getIntent().getStringExtra("pages");

            //SET
            title_input.setText(title);
            author_input.setText(author);
            img_input.setText(img);
            pages_input.setText(pages);
            Picasso.with(this)
                    .load(String.valueOf(img))
                    .into(book_img_view, new Callback(){
                        @Override
                        public void onSuccess() {}

                        @Override
                        public void onError() {}
                    });

        } else {
            Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show();
        }
    }

    void confirmDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete " + title + " ?");
        builder.setMessage("Are you sure you want to delete " + title + " ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(UpdateBookActivity.this);
                myDB.deleteOneRow("books", id);
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }
}