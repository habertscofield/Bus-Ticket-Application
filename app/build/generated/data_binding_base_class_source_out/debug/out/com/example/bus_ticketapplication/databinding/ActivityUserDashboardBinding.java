// Generated by view binder compiler. Do not edit!
package com.example.bus_ticketapplication.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.bus_ticketapplication.R;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityUserDashboardBinding implements ViewBinding {
  @NonNull
  private final DrawerLayout rootView;

  @NonNull
  public final NavigationView NavigationView;

  @NonNull
  public final Button angryBtn;

  @NonNull
  public final AutoCompleteTextView busFrom;

  @NonNull
  public final AutoCompleteTextView busTo;

  @NonNull
  public final DrawerLayout drawer;

  @NonNull
  public final FrameLayout frameLayout;

  @NonNull
  public final TextInputEditText journeydate;

  @NonNull
  public final TextInputLayout textInputLayoutId;

  private ActivityUserDashboardBinding(@NonNull DrawerLayout rootView,
      @NonNull NavigationView NavigationView, @NonNull Button angryBtn,
      @NonNull AutoCompleteTextView busFrom, @NonNull AutoCompleteTextView busTo,
      @NonNull DrawerLayout drawer, @NonNull FrameLayout frameLayout,
      @NonNull TextInputEditText journeydate, @NonNull TextInputLayout textInputLayoutId) {
    this.rootView = rootView;
    this.NavigationView = NavigationView;
    this.angryBtn = angryBtn;
    this.busFrom = busFrom;
    this.busTo = busTo;
    this.drawer = drawer;
    this.frameLayout = frameLayout;
    this.journeydate = journeydate;
    this.textInputLayoutId = textInputLayoutId;
  }

  @Override
  @NonNull
  public DrawerLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityUserDashboardBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityUserDashboardBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_user_dashboard, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityUserDashboardBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.NavigationView;
      NavigationView NavigationView = ViewBindings.findChildViewById(rootView, id);
      if (NavigationView == null) {
        break missingId;
      }

      id = R.id.angry_btn;
      Button angryBtn = ViewBindings.findChildViewById(rootView, id);
      if (angryBtn == null) {
        break missingId;
      }

      id = R.id.busFrom;
      AutoCompleteTextView busFrom = ViewBindings.findChildViewById(rootView, id);
      if (busFrom == null) {
        break missingId;
      }

      id = R.id.busTo;
      AutoCompleteTextView busTo = ViewBindings.findChildViewById(rootView, id);
      if (busTo == null) {
        break missingId;
      }

      DrawerLayout drawer = (DrawerLayout) rootView;

      id = R.id.frameLayout;
      FrameLayout frameLayout = ViewBindings.findChildViewById(rootView, id);
      if (frameLayout == null) {
        break missingId;
      }

      id = R.id.journeydate;
      TextInputEditText journeydate = ViewBindings.findChildViewById(rootView, id);
      if (journeydate == null) {
        break missingId;
      }

      id = R.id.textInputLayoutId;
      TextInputLayout textInputLayoutId = ViewBindings.findChildViewById(rootView, id);
      if (textInputLayoutId == null) {
        break missingId;
      }

      return new ActivityUserDashboardBinding((DrawerLayout) rootView, NavigationView, angryBtn,
          busFrom, busTo, drawer, frameLayout, journeydate, textInputLayoutId);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
