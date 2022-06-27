package com.project.codex;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

public class UpdateNoteActivity extends AppCompatActivity {

    EditText title_input_2, content_input_2, book_id_input_2, page_input_2;
    MyDatabaseHelper myDB;
    FloatingActionButton delete_button, update_button;
    String id, title, content, book_id, page;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_note);
        myDB = new MyDatabaseHelper(UpdateNoteActivity.this);
        title_input_2 = findViewById(R.id.title_input_2);
        content_input_2 = findViewById(R.id.content_input_2);
        book_id_input_2 = findViewById(R.id.book_id_input_2);
        page_input_2 = findViewById(R.id.page_input_2);
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
                MyDatabaseHelper myDB = new MyDatabaseHelper(UpdateNoteActivity.this);
                title=title_input_2.getText().toString().trim();
                content=content_input_2.getText().toString().trim();
                book_id=book_id_input_2.getText().toString().trim();
                page=page_input_2.getText().toString().trim();
                myDB.updateNote(id, book_id, title, content, page);
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
                && getIntent().hasExtra("content")
                && getIntent().hasExtra("book_id")
                && getIntent().hasExtra("page")
        ) {
            //GET
            id = getIntent().getStringExtra("id");
            title = getIntent().getStringExtra("title");
            content = getIntent().getStringExtra("content");
            book_id = getIntent().getStringExtra("book_id");
            page = getIntent().getStringExtra("page");

            //SET
            title_input_2.setText(title);
            content_input_2.setText(content);
            book_id_input_2.setText(book_id);
            page_input_2.setText(page);
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
                MyDatabaseHelper myDB = new MyDatabaseHelper(UpdateNoteActivity.this);
                myDB.deleteOneRow("notes", id);
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