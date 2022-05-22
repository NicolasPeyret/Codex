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

public class AddActivity extends AppCompatActivity {

    FloatingActionButton scan_button;
    EditText title_input, author_input, pages_input;
    Button add_button;

    //API Implementation
    private static final String TAG = AddActivity.class.getSimpleName();
    private EditText mBookInput;
    private TextView mAuthorText, mTitleText, mPageNumberText;
    //ENDS HERE

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        //API Implementation
        mBookInput = (EditText) findViewById(R.id.bookInput);
        mAuthorText = findViewById(R.id.author_input);
        mTitleText = findViewById(R.id.title_input);
        mPageNumberText = findViewById(R.id.pages_input);
        //ENDS HERE

        title_input = findViewById(R.id.title_input);
        author_input = findViewById(R.id.author_input);
        pages_input = findViewById(R.id.pages_input);
        add_button = findViewById(R.id.add_button);
        scan_button = findViewById(R.id.scanButton);
        scan_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddActivity.this, ScannerActivity.class);
                int LAUNCH_SECOND_ACTIVITY = 1;
                startActivityForResult(intent, LAUNCH_SECOND_ACTIVITY);
            }
        });
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(AddActivity.this);
                myDB.addBook(title_input.getText().toString().trim(),
                        author_input.getText().toString().trim(),
                        Integer.valueOf(pages_input.getText().toString().trim())
                );
                //finish();
            }
        });
    }

    //API Implementation
    public void searchBooks(View view) {

        String queryString = mBookInput.getText().toString();
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if(networkInfo != null && networkInfo.isConnected() && queryString.length()!=0) {
            new FetchBook(mTitleText, mAuthorText, mPageNumberText).execute(queryString);
            mAuthorText.setText("");
            mPageNumberText.setText("");
            mTitleText.setText("Loading..");
        } else {
            if(queryString.length() == 0) {
                mAuthorText.setText("");
                mPageNumberText.setText("");
                mTitleText.setText("Please enter a title");
            } else {
                mAuthorText.setText("");
                mPageNumberText.setText("");
                mTitleText.setText("Please check your network");
            }
        }
    }
    //ENDS HERE

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (data.hasExtra("decodedBarcode")) {
                Toast.makeText(
                        this,
                        "book imported",
                        Toast.LENGTH_SHORT).show();
                String queryString = data.getExtras().getString("decodedBarcode");
                new FetchBook(mTitleText, mAuthorText, mPageNumberText).execute(queryString);

            }
        }
    }
}