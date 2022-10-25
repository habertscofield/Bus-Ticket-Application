// Generated by view binder compiler. Do not edit!
package com.example.bus_ticketapplication.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.bus_ticketapplication.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class CustomRowBinding implements ViewBinding {
  @NonNull
  private final CardView rootView;

  @NonNull
  public final TextView textViewBusFare;

  @NonNull
  public final TextView textViewBusName;

  @NonNull
  public final TextView textViewBusNumber;

  @NonNull
  public final TextView textViewDate;

  @NonNull
  public final TextView textViewFrom;

  @NonNull
  public final TextView textViewTime;

  @NonNull
  public final TextView textViewTo;

  private CustomRowBinding(@NonNull CardView rootView, @NonNull TextView textViewBusFare,
      @NonNull TextView textViewBusName, @NonNull TextView textViewBusNumber,
      @NonNull TextView textViewDate, @NonNull TextView textViewFrom,
      @NonNull TextView textViewTime, @NonNull TextView textViewTo) {
    this.rootView = rootView;
    this.textViewBusFare = textViewBusFare;
    this.textViewBusName = textViewBusName;
    this.textViewBusNumber = textViewBusNumber;
    this.textViewDate = textViewDate;
    this.textViewFrom = textViewFrom;
    this.textViewTime = textViewTime;
    this.textViewTo = textViewTo;
  }

  @Override
  @NonNull
  public CardView getRoot() {
    return rootView;
  }

  @NonNull
  public static CustomRowBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static CustomRowBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.custom_row, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static CustomRowBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.text_view_busFare;
      TextView textViewBusFare = ViewBindings.findChildViewById(rootView, id);
      if (textViewBusFare == null) {
        break missingId;
      }

      id = R.id.text_view_busName;
      TextView textViewBusName = ViewBindings.findChildViewById(rootView, id);
      if (textViewBusName == null) {
        break missingId;
      }

      id = R.id.text_view_busNumber;
      TextView textViewBusNumber = ViewBindings.findChildViewById(rootView, id);
      if (textViewBusNumber == null) {
        break missingId;
      }

      id = R.id.text_view_date;
      TextView textViewDate = ViewBindings.findChildViewById(rootView, id);
      if (textViewDate == null) {
        break missingId;
      }

      id = R.id.text_view_from;
      TextView textViewFrom = ViewBindings.findChildViewById(rootView, id);
      if (textViewFrom == null) {
        break missingId;
      }

      id = R.id.text_view_time;
      TextView textViewTime = ViewBindings.findChildViewById(rootView, id);
      if (textViewTime == null) {
        break missingId;
      }

      id = R.id.text_view_to;
      TextView textViewTo = ViewBindings.findChildViewById(rootView, id);
      if (textViewTo == null) {
        break missingId;
      }

      return new CustomRowBinding((CardView) rootView, textViewBusFare, textViewBusName,
          textViewBusNumber, textViewDate, textViewFrom, textViewTime, textViewTo);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
