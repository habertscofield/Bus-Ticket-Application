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

public final class TicketshownBinding implements ViewBinding {
  @NonNull
  private final CardView rootView;

  @NonNull
  public final TextView busname;

  @NonNull
  public final TextView fromLocation;

  @NonNull
  public final TextView issueDate;

  @NonNull
  public final TextView issueTime;

  @NonNull
  public final TextView toLocation;

  private TicketshownBinding(@NonNull CardView rootView, @NonNull TextView busname,
      @NonNull TextView fromLocation, @NonNull TextView issueDate, @NonNull TextView issueTime,
      @NonNull TextView toLocation) {
    this.rootView = rootView;
    this.busname = busname;
    this.fromLocation = fromLocation;
    this.issueDate = issueDate;
    this.issueTime = issueTime;
    this.toLocation = toLocation;
  }

  @Override
  @NonNull
  public CardView getRoot() {
    return rootView;
  }

  @NonNull
  public static TicketshownBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static TicketshownBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.ticketshown, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static TicketshownBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.busname;
      TextView busname = ViewBindings.findChildViewById(rootView, id);
      if (busname == null) {
        break missingId;
      }

      id = R.id.fromLocation;
      TextView fromLocation = ViewBindings.findChildViewById(rootView, id);
      if (fromLocation == null) {
        break missingId;
      }

      id = R.id.issueDate;
      TextView issueDate = ViewBindings.findChildViewById(rootView, id);
      if (issueDate == null) {
        break missingId;
      }

      id = R.id.issueTime;
      TextView issueTime = ViewBindings.findChildViewById(rootView, id);
      if (issueTime == null) {
        break missingId;
      }

      id = R.id.toLocation;
      TextView toLocation = ViewBindings.findChildViewById(rootView, id);
      if (toLocation == null) {
        break missingId;
      }

      return new TicketshownBinding((CardView) rootView, busname, fromLocation, issueDate,
          issueTime, toLocation);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
