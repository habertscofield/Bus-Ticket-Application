// Generated by view binder compiler. Do not edit!
package com.example.bus_ticketapplication.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.bus_ticketapplication.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ResetPassBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final EditText resetPasswordEditTxt;

  private ResetPassBinding(@NonNull LinearLayout rootView, @NonNull EditText resetPasswordEditTxt) {
    this.rootView = rootView;
    this.resetPasswordEditTxt = resetPasswordEditTxt;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ResetPassBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ResetPassBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.reset_pass, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ResetPassBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.reset_passwordEditTxt;
      EditText resetPasswordEditTxt = ViewBindings.findChildViewById(rootView, id);
      if (resetPasswordEditTxt == null) {
        break missingId;
      }

      return new ResetPassBinding((LinearLayout) rootView, resetPasswordEditTxt);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
