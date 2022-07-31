// Generated by view binder compiler. Do not edit!
package com.example.bus_ticketapplication.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.bus_ticketapplication.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class AddBusBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final Button btnaddBus;

  @NonNull
  public final AutoCompleteTextView busFrom;

  @NonNull
  public final EditText busNumber;

  @NonNull
  public final AutoCompleteTextView busTo;

  @NonNull
  public final TextView journeyDate;

  @NonNull
  public final TextView journeyTime;

  @NonNull
  public final TextView textView;

  @NonNull
  public final EditText travelsName;

  private AddBusBinding(@NonNull ConstraintLayout rootView, @NonNull Button btnaddBus,
      @NonNull AutoCompleteTextView busFrom, @NonNull EditText busNumber,
      @NonNull AutoCompleteTextView busTo, @NonNull TextView journeyDate,
      @NonNull TextView journeyTime, @NonNull TextView textView, @NonNull EditText travelsName) {
    this.rootView = rootView;
    this.btnaddBus = btnaddBus;
    this.busFrom = busFrom;
    this.busNumber = busNumber;
    this.busTo = busTo;
    this.journeyDate = journeyDate;
    this.journeyTime = journeyTime;
    this.textView = textView;
    this.travelsName = travelsName;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static AddBusBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static AddBusBinding inflate(@NonNull LayoutInflater inflater, @Nullable ViewGroup parent,
      boolean attachToParent) {
    View root = inflater.inflate(R.layout.add_bus, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static AddBusBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.btnaddBus;
      Button btnaddBus = ViewBindings.findChildViewById(rootView, id);
      if (btnaddBus == null) {
        break missingId;
      }

      id = R.id.busFrom;
      AutoCompleteTextView busFrom = ViewBindings.findChildViewById(rootView, id);
      if (busFrom == null) {
        break missingId;
      }

      id = R.id.busNumber;
      EditText busNumber = ViewBindings.findChildViewById(rootView, id);
      if (busNumber == null) {
        break missingId;
      }

      id = R.id.busTo;
      AutoCompleteTextView busTo = ViewBindings.findChildViewById(rootView, id);
      if (busTo == null) {
        break missingId;
      }

      id = R.id.journeyDate;
      TextView journeyDate = ViewBindings.findChildViewById(rootView, id);
      if (journeyDate == null) {
        break missingId;
      }

      id = R.id.journeyTime;
      TextView journeyTime = ViewBindings.findChildViewById(rootView, id);
      if (journeyTime == null) {
        break missingId;
      }

      id = R.id.textView;
      TextView textView = ViewBindings.findChildViewById(rootView, id);
      if (textView == null) {
        break missingId;
      }

      id = R.id.travelsName;
      EditText travelsName = ViewBindings.findChildViewById(rootView, id);
      if (travelsName == null) {
        break missingId;
      }

      return new AddBusBinding((ConstraintLayout) rootView, btnaddBus, busFrom, busNumber, busTo,
          journeyDate, journeyTime, textView, travelsName);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
