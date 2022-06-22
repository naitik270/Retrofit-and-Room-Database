package com.demo.practicleapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.demo.practicleapp.ApiClasses.ApiPostViewModel;
import com.demo.practicleapp.ApiClasses.ClsLoginParams;

public class LoginActivity extends AppCompatActivity {


    Button btn_login;
    EditText edt_login, edt_pass;
    ApiPostViewModel mApiPostViewModel;
    private ProgressDialog pd;
    ClsUserInfo obj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mApiPostViewModel = new ViewModelProvider(this).get(ApiPostViewModel.class);
        obj = new ClsUserInfo();
        obj = ClsGlobal.getUserInfo(LoginActivity.this);
        Log.d("--Name--", "Status: " + obj.getLoginStatus());
        initView();

        if (obj.getLoginStatus().equalsIgnoreCase("ACTIVE")) {
            ClsGlobal.setUserInfo(obj, LoginActivity.this);
            startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
        }
    }

    void initView() {
        btn_login = findViewById(R.id.btn_login);
        edt_login = findViewById(R.id.edt_login);
        edt_pass = findViewById(R.id.edt_pass);

        btn_login.setOnClickListener(view -> {
           /* if (validation(view)) {
                try {
                    LoginAPI(view);
                } catch (Exception e) {
                    Log.d("--login--", "Exception: " + e.getMessage());
                }
            }*/

            startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
        });
    }


    private Boolean validation(View view) {

        if (edt_login.getText() == null || edt_login.getText().toString().trim().isEmpty()) {
            ClsGlobal.errorMsg(this, view, "User Name is required...!", 0);
            edt_login.requestFocus();
            return false;
        }
        if (edt_pass.getText() == null || edt_pass.getText().toString().trim().isEmpty()) {
            ClsGlobal.errorMsg(this, view, "Password is required...!", 0);
            edt_pass.requestFocus();
            return false;
        }
        return true;
    }

    void LoginAPI(View view) {
        pd = ClsGlobal._prProgressDialog(this, "Check User Name & Password...!", false);
        pd.show();

        mApiPostViewModel.callLoginAPIModel(edt_login.getText().toString(), edt_pass.getText().toString()).observe(this, mObj -> {
            Log.d("--login--", "LoginAPI: " + mObj.getResult());
            if (!mObj.getResult()) {
                ClsGlobal.errorMsg(this, view, "Please valid credentials...!", 0);
            } else {
                ClsGlobal.errorMsg(this, view, "Login successfully...!", 1);
                obj.setUserName(mObj.getFullname());
                obj.setLoginStatus("ACTIVE");
                ClsGlobal.setUserInfo(obj, LoginActivity.this);
                startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
            }
            pd.dismiss();
        });
    }
}