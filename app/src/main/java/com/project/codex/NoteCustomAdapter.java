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

public class NoteCustomAdapter extends RecyclerView.Adapter<NoteCustomAdapter.MyViewHolder> {

    Context context;
    Activity activity;

    private ArrayList note_id, note_title, note_content, book_id, note_page;

    Animation translate_anim;

    NoteCustomAdapter(Activity activity,
                      Context context,
                      ArrayList note_id,
                      ArrayList note_title,
                      ArrayList note_content,
                      ArrayList book_id,
                      ArrayList note_page) {
        this.activity = activity;
        this.context = context;
        this.note_id = note_id;
        this.note_title = note_title;
        this.note_content = note_content;
        this.book_id = book_id;
        this.note_page = note_page;
    }

    /**
     * This method is used to create the view holder for the recycler view.
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_notes_row, parent, false);
        return new MyViewHolder(view);
    }

    /**
     * This method is used to bind the data to the view holder.
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        holder.note_id_txt.setText(String.valueOf(note_id.get(position)));
        holder.note_title_txt.setText(String.valueOf(note_title.get(position)));
        holder.note_content_txt.setText(String.valueOf(note_content.get(position)));
        holder.book_id_txt.setText(String.valueOf(book_id.get(position)));
        holder.note_page_txt.setText(String.valueOf(note_page.get(position)));
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UpdateNoteActivity.class);
                intent.putExtra("id", String.valueOf(note_id.get(position)));
                intent.putExtra("title", String.valueOf(note_title.get(position)));
                intent.putExtra("content", String.valueOf(note_content.get(position)));
                intent.putExtra("book_id", String.valueOf(book_id.get(position)));
                intent.putExtra("page", String.valueOf(note_page.get(position)));
                activity.startActivityForResult(intent, 1);
            }
        });
    }

    /**
     * This method is used to return the size of the list.
     * @return
     */
    @Override
    public int getItemCount() {
        return note_id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView note_id_txt, note_title_txt, note_content_txt, book_id_txt, note_page_txt;
        LinearLayout mainLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            note_id_txt = itemView.findViewById(R.id.note_id_txt);
            note_title_txt = itemView.findViewById(R.id.note_title_txt);
            note_content_txt = itemView.findViewById(R.id.note_content_txt);
            book_id_txt = itemView.findViewById(R.id.book_id_txt);
            note_page_txt = itemView.findViewById(R.id.note_page_txt);
            mainLayout = itemView.findViewById(R.id.mainLayout);
            translate_anim = AnimationUtils.loadAnimation(context, R.anim.translate_anim);
            mainLayout.setAnimation(translate_anim);
        }
    }
}