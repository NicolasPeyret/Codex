// Generated by view binder compiler. Do not edit!
package com.project.codex.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.project.codex.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityMainBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final FloatingActionButton addButton;

  @NonNull
  public final RecyclerView bookRecyclerView;

  @NonNull
  public final ImageView emptyImageview;

  @NonNull
  public final TextView noData;

  private ActivityMainBinding(@NonNull ConstraintLayout rootView,
      @NonNull FloatingActionButton addButton, @NonNull RecyclerView bookRecyclerView,
      @NonNull ImageView emptyImageview, @NonNull TextView noData) {
    this.rootView = rootView;
    this.addButton = addButton;
    this.bookRecyclerView = bookRecyclerView;
    this.emptyImageview = emptyImageview;
    this.noData = noData;
  }

  
  /** 
   * @return ConstraintLayout
   */
  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  
  /** 
   * @param inflater
   * @return ActivityMainBinding
   */
  @NonNull
  public static ActivityMainBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  
  /** 
   * @param inflate(
   * @return ActivityMainBinding
   */
  @NonNull
  public static ActivityMainBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_main, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  
  /** 
   * @param rootView
   * @return ActivityMainBinding
   */
  @NonNull
  public static ActivityMainBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.add_button;
      FloatingActionButton addButton = ViewBindings.findChildViewById(rootView, id);
      if (addButton == null) {
        break missingId;
      }

      id = R.id.bookRecyclerView;
      RecyclerView bookRecyclerView = ViewBindings.findChildViewById(rootView, id);
      if (bookRecyclerView == null) {
        break missingId;
      }

      id = R.id.empty_imageview;
      ImageView emptyImageview = ViewBindings.findChildViewById(rootView, id);
      if (emptyImageview == null) {
        break missingId;
      }

      id = R.id.no_data;
      TextView noData = ViewBindings.findChildViewById(rootView, id);
      if (noData == null) {
        break missingId;
      }

      return new ActivityMainBinding((ConstraintLayout) rootView, addButton, bookRecyclerView,
          emptyImageview, noData);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
