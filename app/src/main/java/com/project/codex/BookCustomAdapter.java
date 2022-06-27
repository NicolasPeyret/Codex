package com.project.codex;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

public class BookCustomAdapter extends RecyclerView.Adapter<BookCustomAdapter.MyViewHolder> {

    Context context;
    Activity activity;

    private ArrayList book_id, book_title, book_author, book_img, book_pages;

    Animation translate_anim;

    BookCustomAdapter(Activity activity,
                      Context context,
                      ArrayList book_id,
                      ArrayList book_title,
                      ArrayList book_author,
                      ArrayList book_img,
                      ArrayList book_pages) {
        this.activity = activity;
        this.context = context;
        this.book_id = book_id;
        this.book_title = book_title;
        this.book_author = book_author;
        this.book_img = book_img;
        this.book_pages = book_pages;
    }

    /**
     *  This method is used to create the view holder for the recycler view.
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_books_row, parent, false);
        return new MyViewHolder(view);
    }

    /**
     * This method is called by the RecyclerView to display the data at the specified position.
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        holder.book_id_txt.setText(String.valueOf(book_id.get(position)));
        holder.book_title_txt.setText(String.valueOf(book_title.get(position)));
        holder.book_author_txt.setText(String.valueOf(book_author.get(position)));
        Picasso.with(context)
                .load(String.valueOf(book_img.get(position)))
                .into(holder.book_img_txt, new Callback(){
                    @Override
                    public void onSuccess() {}

                    @Override
                    public void onError() {}
                });

        holder.book_pages_txt.setText(String.valueOf(book_pages.get(position)));
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            /**
             * Called when a view has been clicked.
             * @param view
             */
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UpdateBookActivity.class);
                intent.putExtra("id", String.valueOf(book_id.get(position)));
                intent.putExtra("title", String.valueOf(book_title.get(position)));
                intent.putExtra("author", String.valueOf(book_author.get(position)));
                intent.putExtra("image", String.valueOf(book_img.get(position)));
                intent.putExtra("pages", String.valueOf(book_pages.get(position)));
                activity.startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return book_id.size();
    }

    /**
     * ViewHolder class for the RecyclerView
     */
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView book_id_txt, book_title_txt, book_author_txt, book_pages_txt;
        ImageView book_img_txt;
        LinearLayout mainLayout;
        
        /**
         * Constructor for the ViewHolder class
         * @param itemView
         */
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            book_id_txt = itemView.findViewById(R.id.book_id_txt);
            book_title_txt = itemView.findViewById(R.id.book_title_txt);
            book_author_txt = itemView.findViewById(R.id.book_author_txt);
            book_img_txt = itemView.findViewById(R.id.book_img_txt);
            book_pages_txt = itemView.findViewById(R.id.book_pages_txt);
            mainLayout = itemView.findViewById(R.id.mainLayout);
            translate_anim = AnimationUtils.loadAnimation(context, R.anim.translate_anim);
            mainLayout.setAnimation(translate_anim);
        }
    }
}