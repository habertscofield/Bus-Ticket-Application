// Generated by view binder compiler. Do not edit!
package com.example.bus_ticketapplication.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.bus_ticketapplication.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityConfirmTicketBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final TextView busJourneyDateId;

  @NonNull
  public final TextView busName;

  @NonNull
  public final Button homeButtonId;

  @NonNull
  public final TextView ticketID;

  private ActivityConfirmTicketBinding(@NonNull LinearLayout rootView,
      @NonNull TextView busJourneyDateId, @NonNull TextView busName, @NonNull Button homeButtonId,
      @NonNull TextView ticketID) {
    this.rootView = rootView;
    this.busJourneyDateId = busJourneyDateId;
    this.busName = busName;
    this.homeButtonId = homeButtonId;
    this.ticketID = ticketID;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityConfirmTicketBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityConfirmTicketBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_confirm_ticket, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityConfirmTicketBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.busJourneyDateId;
      TextView busJourneyDateId = ViewBindings.findChildViewById(rootView, id);
      if (busJourneyDateId == null) {
        break missingId;
      }

      id = R.id.busName;
      TextView busName = ViewBindings.findChildViewById(rootView, id);
      if (busName == null) {
        break missingId;
      }

      id = R.id.homeButtonId;
      Button homeButtonId = ViewBindings.findChildViewById(rootView, id);
      if (homeButtonId == null) {
        break missingId;
      }

      id = R.id.ticketID;
      TextView ticketID = ViewBindings.findChildViewById(rootView, id);
      if (ticketID == null) {
        break missingId;
      }

      return new ActivityConfirmTicketBinding((LinearLayout) rootView, busJourneyDateId, busName,
          homeButtonId, ticketID);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
