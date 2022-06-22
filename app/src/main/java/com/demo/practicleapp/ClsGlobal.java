package com.demo.practicleapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import com.google.android.material.snackbar.Snackbar;

import static android.content.Context.MODE_PRIVATE;

public class ClsGlobal {

    public static ProgressDialog _prProgressDialog(Context c, String msg, Boolean setCancelable) {
        ProgressDialog progressDialog = new ProgressDialog(c, R.style.AppCompatAlertDialogStyle);
        progressDialog.setMessage(msg); // Setting Message
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
        progressDialog.setCancelable(setCancelable);
        return progressDialog;
    }


    public static void errorMsg(Activity context, View v, String err_msg, int done) {

        final Snackbar snackbar = Snackbar.make(v, "", Snackbar.LENGTH_LONG);

        View customSnackView = context.getLayoutInflater().inflate(R.layout.cust_error_msg, null);
        snackbar.getView().setBackgroundColor(Color.TRANSPARENT);
        Snackbar.SnackbarLayout snackbarLayout = (Snackbar.SnackbarLayout) snackbar.getView();
        snackbarLayout.setPadding(0, 0, 0, 0);
        TextView txt_err_msg = customSnackView.findViewById(R.id.txt_err_msg);
        CardView cc_header = customSnackView.findViewById(R.id.cc_header);
        txt_err_msg.setText(err_msg);
        if (done != 1) {
            cc_header.setCardBackgroundColor(ContextCompat.getColor(context, R.color.red));
        } else {
            cc_header.setCardBackgroundColor(ContextCompat.getColor(context, R.color.green));
        }

        Button bGotoWebsite = customSnackView.findViewById(R.id.gotoWebsiteButton);
        bGotoWebsite.setOnClickListener(v1 -> {
            snackbar.dismiss();
        });
        snackbarLayout.addView(customSnackView, 0);
        snackbar.show();
    }

    public static void setUserInfo(ClsUserInfo objClsUserInfo, Context c) {
        String MyPREFERENCES = "LoginDetails";
        SharedPreferences mPreferences = c.getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor editor = mPreferences.edit();
        String UserName = "UserName";
        String LoginStatus = "LoginStatus";
        editor.putString(UserName, objClsUserInfo.getUserName());
        editor.putString(LoginStatus, objClsUserInfo.getLoginStatus());
        editor.apply();
        editor.commit();
    }

    public static ClsUserInfo getUserInfo(Context c) {
        ClsUserInfo objClsUserInfo = new ClsUserInfo();
        String MyPREFERENCES = "LoginDetails";
        SharedPreferences mPreferences = c.getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        String UserName = "UserName";
        String LoginStatus = "LoginStatus";
        objClsUserInfo.setUserName(mPreferences.getString(UserName, objClsUserInfo.getUserName()));
        objClsUserInfo.setLoginStatus(mPreferences.getString(LoginStatus, objClsUserInfo.getLoginStatus()));
        return objClsUserInfo;
    }

    public static String getRealPathFromURIPath(Uri contentURI, Activity activity) {
        Cursor cursor = activity.getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) {
            return contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(idx);
        }
    }

    public static void autoLogout(Context c) {
        String MyPREFERENCES = "LoginDetails";
        SharedPreferences mPreferences = c.getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor editor = mPreferences.edit();

        String LoginStatus = "LoginStatus";

        editor.putString(LoginStatus, "DEACTIVE");

        editor.apply();
        editor.commit();
    }
}
