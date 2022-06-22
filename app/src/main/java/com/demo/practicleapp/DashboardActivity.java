package com.demo.practicleapp;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.demo.practicleapp.Adapter.TabViewPagerAdapter;
import com.demo.practicleapp.Fragment.CategoryFragment;
import com.demo.practicleapp.Fragment.ItemFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

public class DashboardActivity extends AppCompatActivity {


    ViewPager2 viewPager;
    TabLayout tablayout;
    TextView txt_user_name;
    TextView txt_logout;
    TextView txt_add_item;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        ClsUserInfo userInfo = ClsGlobal.getUserInfo(DashboardActivity.this);

        txt_logout = findViewById(R.id.txt_logout);
        txt_user_name = findViewById(R.id.txt_user_name);
        viewPager = findViewById(R.id.viewPager);
        tablayout = findViewById(R.id.tablayout);
        txt_add_item = findViewById(R.id.txt_add_item);

        Log.d("--Name--", "getUserName: " + userInfo.getUserName());
        Log.d("--Name--", "getLoginStatus: " + userInfo.getLoginStatus());

        txt_user_name.setText(userInfo.getUserName());

        List<String> lst = new ArrayList<>();
        lst.add("CATEGORIES");
        lst.add("ITEMS");

        if (viewPager != null) {
            viewPager.setCurrentItem(1);
            viewPager.setOffscreenPageLimit(2);
            setupViewPager(viewPager);
        }

        new TabLayoutMediator(tablayout, viewPager, (tab, position) ->
                tab.setText(lst.get(position))).attach();

        txt_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LogoutAlert();
            }
        });

        txt_add_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AddItemActivity.class));
            }
        });
    }


    private void setupViewPager(ViewPager2 viewPager) {

        TabViewPagerAdapter adapter = new TabViewPagerAdapter(getSupportFragmentManager(), getLifecycle());

        adapter.addFrag(new CategoryFragment());
        adapter.addFrag(new ItemFragment());

        viewPager.setAdapter(adapter);
    }


    void LogoutAlert() {
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.message_logout_prompt, null);

        TextView tvMessage = alertLayout.findViewById(R.id.tvPromptMessage);

        AlertDialog alertDialog = new AlertDialog.Builder(DashboardActivity.this,
                R.style.AppCompatAlertDialogStyle).create(); //Read Update.
        alertDialog.setView(alertLayout);
        alertDialog.setTitle("Confirmation");
        tvMessage.setText(getResources().getString(R.string.logout_message));

        alertDialog.setButton(Dialog.BUTTON_POSITIVE, "Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ClsGlobal.autoLogout(getApplicationContext());
                Intent i = new Intent(DashboardActivity.this, LoginActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });
        alertDialog.setButton(Dialog.BUTTON_NEGATIVE, "No", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        alertDialog.setCancelable(false);
        alertDialog.show();
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "Click on logout...!", Toast.LENGTH_SHORT).show();
    }
}
