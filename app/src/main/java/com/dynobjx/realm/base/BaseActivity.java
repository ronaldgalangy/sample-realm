package com.dynobjx.realm.base;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.dynobjx.realm.R;
import com.dynobjx.realm.interfaces.OnConfirmDialogListener;
import com.dynobjx.realm.singletons.BusSingleton;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by root on 11/02/16.
 */
public abstract class BaseActivity extends AppCompatActivity {
    private static ProgressDialog progressDialog;

    public void animateToLeft(Activity activity) {
        activity.overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
    }

    public static void animateToRight(Activity activity) {
        activity.overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
    }

    public Drawable getImageById(final int id) {
        return ContextCompat.getDrawable(this, id);
    }

    public void setToolbarTitle(final String title) {
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(ContextCompat
                .getColor(this, R.color.colorPrimary)));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void updateToolbarTitle(final String title) {
        getSupportActionBar().setTitle(title);
    }

    @Override
    protected void onPause() {
        BusSingleton.getInstance().unregister(this);
        super.onPause();
    }

    @Override
    protected void onResume() {
        BusSingleton.getInstance().register(this);
        super.onResume();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        animateToRight(this);
    }

    public boolean isFacebookInstalled() {
        try {
            getPackageManager().getApplicationInfo("com.facebook.katana", 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    public void moveToOtherActivity(Class clz) {
        startActivity(new Intent(this, clz));
        animateToLeft(this);
    }

    public boolean isServiceRunning(Class<?> serviceClass) {
        final ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * check network connection availability
     */
    public boolean isNetworkAvailable() {
        final ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null) {
            // connected to the internet
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI ||
                    activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public void showToast(final String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    public void showConfirmDialog(final String action, final String title, final String message,

                                  final String positiveText, final String negativeText,
                                  final OnConfirmDialogListener onConfirmDialogListener) {
        final View view = getLayoutInflater().inflate(R.layout.dialog_confirm, null);
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(view);
        final TextView tvHeader = (TextView) view.findViewById(R.id.tv_header);
        tvHeader.setText(title);
        final TextView tvMessage = (TextView) view.findViewById(R.id.tv_message);

        tvMessage.setText(message);

        if (!positiveText.isEmpty()) {
            builder.setPositiveButton(positiveText, (dialog, which) -> {
                dialog.dismiss();
                if (onConfirmDialogListener != null) {
                    onConfirmDialogListener.onConfirmed(action);
                }
            });
        }
        builder.setNegativeButton(negativeText, (dialog, which) -> {
            dialog.cancel();
            if (onConfirmDialogListener != null) {
                onConfirmDialogListener.onCancelled(action);
            }
        });
        builder.show();
    }

    /*public void showConfirmDialog(final String action, final String header, final String message,
                                  final String positiveText, final String negativeText,
                                  final OnConfirmDialogListener onConfirmDialogListener) {
        final View view = getLayoutInflater().inflate(R.layout.dialog_confirm, null);
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(view);
        final TextView tvHeader =  ButterKnife.findById(view, R.id.tv_header);
        tvHeader.setText(title);
        final TextView tvMessage =  ButterKnife.findById(view, R.id.tv_message);
        tvMessage.setText(message);

        if (!positiveText.isEmpty()) {
            builder.setPositiveButton(positiveText, (dialog, which) -> {
                dialog.dismiss();
                if (onConfirmDialogListener != null) {
                    onConfirmDialogListener.onConfirmed(action);
                }
            });
        }
        builder.setNegativeButton(negativeText, (dialog, which) -> {
            dialog.cancel();
            if (onConfirmDialogListener != null) {
                onConfirmDialogListener.onCancelled(action);
            }
        });
        builder.show();
    }*/

    public void setError(final TextView textView, final String errorMessage) {
        textView.setError(errorMessage);
        textView.requestFocus();
    }

    public void showProgressDialog(final String title, final String message) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            if (title != null) {
                progressDialog.setTitle(title);
            }
            progressDialog.setMessage(message);
            progressDialog.setCancelable(false);
            progressDialog.show();
        }
    }

    public void updateProgressDialog(final String message) {
        if (progressDialog != null) {
            progressDialog.setMessage(message);
        }
    }

    public void dismissProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    public String getText(final TextView textView) {
        return textView.getText().toString().trim();
    }
    public SimpleDateFormat getSDF() {
        return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"); //2016-10-20T06:13:53.166Z
    }

    public SimpleDateFormat getSDFDate() {
        return new SimpleDateFormat("yyyy-MM-dd"); //2016-10-20T06
    }

    public String getDateFormatter (Date date) {
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd");
        return simpleDateFormat.format(date);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        //super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    public boolean isEmailValid(final String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
