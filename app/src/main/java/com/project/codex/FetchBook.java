package com.project.codex;

import android.os.AsyncTask;
import android.widget.EditText;
import android.widget.TextView;

public class FetchBook extends AsyncTask<String, Void, String> {

    private TextView mAuthorText, mTitleText;

    public FetchBook(TextView mTitleText, TextView mAuthorText) {
    this.mTitleText = mTitleText;
    this.mAuthorText = mAuthorText;
    }

    @Override
    protected String doInBackground(String... strings) {
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }
}
