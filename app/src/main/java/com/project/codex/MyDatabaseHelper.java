package com.project.codex;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MyDatabaseHelper extends SQLiteOpenHelper {
    private Context context;
    private static final String DATABASE_NAME = "BookLibrary.db";
    private static final int DATABASE_VERSION = 3;

    private static final String TABLE_NAME_1 = "my_library";
    private static final String COLUMN_ID_1 = "_id";
    private static final String COLUMN_TITLE_1 = "book_title";
    private static final String COLUMN_AUTHOR_1 = "book_author";
    private static final String COLUMN_IMG_1 = "book_img";
    private static final String COLUMN_PAGES_1 = "book_pages";

    private static final String TABLE_NAME_2 = "my_notes";
    private static final String COLUMN_ID_2 = "_id";
    private static final String BOOK_ID_2 = "book_id";
    private static final String COLUMN_TITLE_2 = "note_title";
    private static final String COLUMN_CONTENT_2 = "note_content";
    private static final String COLUMN_PAGE_2 = "book_page";
    private static final String FOREIGN_KEY_2 = "_id";


    MyDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query =
                "CREATE TABLE "
                        + TABLE_NAME_1
                        + " ("
                        + COLUMN_ID_1
                        + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + COLUMN_TITLE_1
                        + " TEXT, "
                        + COLUMN_AUTHOR_1
                        + " TEXT, "
                        + COLUMN_IMG_1
                        + " TEXT, "
                        + COLUMN_PAGES_1
                        + " INTEGER);";
        String query_2 =
                "CREATE TABLE "
                        + TABLE_NAME_2
                        + " ("
                        + COLUMN_ID_2
                        + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + BOOK_ID_2
                        + " INTEGER, "
                        + COLUMN_TITLE_2
                        + " TEXT, "
                        + COLUMN_CONTENT_2
                        + " TEXT, "
                        + COLUMN_PAGE_2
                        + " INTEGER, "
                        + "FOREIGN KEY("
                        + BOOK_ID_2
                        + ") references "
                        + TABLE_NAME_1
                        +"("
                        + COLUMN_ID_1
                        + "))";
        db.execSQL(query);
        db.execSQL(query_2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_1);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_2);
        onCreate(db);
    }

    void addBook(String title, String author, String img, int pages) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_TITLE_1,title);
        cv.put(COLUMN_AUTHOR_1,author);
        cv.put(COLUMN_IMG_1,img);
        cv.put(COLUMN_PAGES_1,pages);

        long result = db.insert(TABLE_NAME_1, null, cv);
        if(result == -1) {
            Toast.makeText(context, "Failed", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context, "Added Successfully", Toast.LENGTH_LONG).show();
        }
    }

    void addNote(int bookId, String title, String content, int page) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(BOOK_ID_2,bookId);
        cv.put(COLUMN_TITLE_2,title);
        cv.put(COLUMN_CONTENT_2, content);
        cv.put(COLUMN_PAGE_2,page);

        long result = db.insert(TABLE_NAME_2, null, cv);
        if(result == -1) {
            Toast.makeText(context, "Failed", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context, "Added Successfully", Toast.LENGTH_LONG).show();
        }
    }

    Cursor readAllData(String database) {
        String query = "";
        if (database == "books") {
            query = "SELECT * FROM " + TABLE_NAME_1;

        } else if (database == "notes") {
            query = "SELECT * FROM " + TABLE_NAME_2;
        }

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if(db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    void updateBook(String row_id, String title, String author, String img, String pages) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TITLE_1, title);
        cv.put(COLUMN_AUTHOR_1, author);
        cv.put(COLUMN_IMG_1,img);
        cv.put(COLUMN_PAGES_1, pages);
        long result = db.update(TABLE_NAME_1, cv, "_id=?", new String[]{row_id});
        if(result == -1) {
            Toast.makeText(context, "Failed to update.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Successfully Updated!", Toast.LENGTH_SHORT).show();
        }
    }

    void updateNote(String row_id, String bookId, String title, String content, int page) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_ID_2,bookId);
        cv.put(BOOK_ID_2,bookId);
        cv.put(COLUMN_TITLE_2,title);
        cv.put(COLUMN_CONTENT_2, content);
        cv.put(COLUMN_PAGE_2,page);
        long result = db.update(TABLE_NAME_2, cv, "_id=?", new String[]{row_id});
        if(result == -1) {
            Toast.makeText(context, "Failed to update.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Successfully Updated!", Toast.LENGTH_SHORT).show();
        }
    }

    void deleteOneRow(String database, String row_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = 0;

        if (database == "books") {
            result = db.delete(TABLE_NAME_1, "_id=?", new String[]{row_id});

        } else if (database == "notes") {
            result = db.delete(TABLE_NAME_2, "_id=?", new String[]{row_id});
        }

        if(result == -1) {
            Toast.makeText(context, "Failed to delete.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Successfully Deleted!", Toast.LENGTH_SHORT).show();
        }
    }

    void deleteAllData(String database) {
        SQLiteDatabase db = this.getWritableDatabase();
        if (database == "books") {
            db.execSQL("DELETE FROM " + TABLE_NAME_1);

        } else if (database == "notes") {
            db.execSQL("DELETE FROM " + TABLE_NAME_2);
        }
    }
}
