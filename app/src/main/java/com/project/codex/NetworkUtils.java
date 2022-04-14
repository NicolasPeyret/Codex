package com.project.codex;

import java.io.BufferedReader;
import java.net.HttpURLConnection;

public class NetworkUtils {
    private static final String LOG_TAG = NetworkUtils.class.getSimpleName();
    private static final String BOOK_BASE_URL = "https://googleapis.com/books/v1/volume?";


    static String getBookInfo(String queryString) {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String bookJSONString = null;
        return bookJSONString;
    }
}
