package com.project.codex;

import android.os.AsyncTask;
import android.widget.TextView;
import org.json.JSONArray;
import org.json.JSONObject;

public class FetchBook extends AsyncTask<String, Void, String> {

    private TextView mAuthorText, mTitleText, mPageNumberText, mImgText;

    /**
     * onPreExecute method is called before the background task is executed.
     */
    public FetchBook(TextView mTitleText, TextView mAuthorText, TextView mImgText, TextView mPageNumberText) {
        this.mTitleText = mTitleText;
        this.mAuthorText = mAuthorText;
        this.mImgText = mImgText;
        this.mPageNumberText = mPageNumberText;
    }

    /**
     * doInBackground method is called when the background task is executed.
     * @param strings
     * @return
     */
    @Override
    protected String doInBackground(String... strings) {
        return NetworkUtils.getBookInfo(strings[0]);
    }

    /**
     * onPostExecute method is called after the background task is executed.
     * @param s
     */
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        try {
            JSONObject jsonObject = new JSONObject(s);
            JSONArray itemsArray = jsonObject.getJSONArray("items");

            for (int i = 0; i < itemsArray.length(); i++) {
                JSONObject book = itemsArray.getJSONObject(i);
                String title = null;
                String authors = null;
                String pageNumber = null;
                String image = null;
                JSONObject volumeInfo = book.getJSONObject("volumeInfo");
                try {
                    title = volumeInfo.getString("title");
                    authors = volumeInfo.getString("authors");
                    authors = authors.substring(1, authors.length()-1);
                    //get substring of image named smallThumbnail
                    image = volumeInfo.getString("imageLinks");
                    JSONObject jObject = new JSONObject(image);
                    //smallThumbnail
                    image = jObject.getString("smallThumbnail");
                    //http to https
                    image = image.replace("http", "https");

                    String[] authorsArray = authors.split(",");
                    //foreach loop to transform the array into a string
                    StringBuilder sb = new StringBuilder();
                    for (String author : authorsArray) {
                        author = author.replace("\"","");
                        sb.append(author);
                        sb.append("\n");
                    }
                    authors = sb.toString();
                    pageNumber = volumeInfo.getString("pageCount");
                } catch (Exception e) {
                    e.printStackTrace() ;
                }

                if (title != null && authors != null && pageNumber != null) {
                    mTitleText.setText(title);
                    mAuthorText.setText(authors);
                    mImgText.setText(image);
                    mPageNumberText.setText(pageNumber);
                    return;
                }
            }

            mTitleText.setText("No results found");
            mAuthorText.setText("");
            mImgText.setText("");
            mPageNumberText.setText("");

        } catch (Exception e) {
            mTitleText.setText("No results found");
            mAuthorText.setText("");
            mImgText.setText("");
            mPageNumberText.setText("");
            e.printStackTrace();
        }
    }
}
