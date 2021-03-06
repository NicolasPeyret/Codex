package com.project.codex;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;

public class AddBookActivity extends AppCompatActivity {
    FloatingActionButton scan_button;
    EditText title_input, author_input, pages_input, img_input;
    Button add_button;
    //API Implementation
    private static final String TAG = AddBookActivity.class.getSimpleName();
    private EditText mBookInput;
    private TextView mAuthorText, mTitleText, mPageNumberText, mImgText;
    //ENDS HERE

    /**
     * onCreate method is called when the activity is created.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);
        //API Implementation
        mAuthorText = findViewById(R.id.author_input);
        mTitleText = findViewById(R.id.title_input);
        mImgText = findViewById(R.id.img_input);
        mPageNumberText = findViewById(R.id.pages_input);
        //ENDS HERE
        title_input = findViewById(R.id.title_input);
        author_input = findViewById(R.id.author_input);
        img_input = findViewById(R.id.img_input);
        pages_input = findViewById(R.id.pages_input);
        add_button = findViewById(R.id.add_button);
        scan_button = findViewById(R.id.scanButton);
        scan_button.setOnClickListener(new View.OnClickListener() {
            /**
             * onClick method is called when the scan button is clicked.
             * @param view
             */
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddBookActivity.this, ScannerActivity.class);
                int LAUNCH_SECOND_ACTIVITY = 1;
                startActivityForResult(intent, LAUNCH_SECOND_ACTIVITY);
            }
        });

        add_button.setOnClickListener(new View.OnClickListener() {
            /**
             * onClick method is called when the add button is clicked.
             * @param view
             */
            @Override
            public void onClick(View view) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(AddBookActivity.this);
                myDB.addBook(title_input.getText().toString().trim(),
                        author_input.getText().toString().trim(),
                        img_input.getText().toString().trim(),
                        Integer.valueOf(pages_input.getText().toString().trim())
                );
            }
        });
    }


    /**
     * searchBooks method is called when the search button is clicked.
     * @param view
     */
    public void searchBooks(View view) {
        String queryString = mBookInput.getText().toString();
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if(networkInfo != null && networkInfo.isConnected() && queryString.length()!=0) {
            new FetchBook(mTitleText, mAuthorText, mImgText, mPageNumberText).execute(queryString);
            mAuthorText.setText("");
            mPageNumberText.setText("");
            mImgText.setText("");
            mTitleText.setText("Loading..");
        } else {
            if(queryString.length() == 0) {
                mAuthorText.setText("");
                mPageNumberText.setText("");
                mImgText.setText("");
                mTitleText.setText("Please enter a title");
            } else {
                mAuthorText.setText("");
                mPageNumberText.setText("");
                mImgText.setText("");
                mTitleText.setText("Please check your network");
            }
        }
    }

    /**
     * onActivityResult method is called when the scanner activity is finished.
     * @param requestCode
     * @param resultCode
     * @param data
     */
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
                new FetchBook(mTitleText, mAuthorText, mImgText, mPageNumberText).execute(queryString);

            }
        }
    }
}