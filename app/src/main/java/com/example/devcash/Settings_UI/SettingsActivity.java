package com.example.devcash.Settings_UI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.devcash.CustomAdapters.SettingsAdapter;
import com.example.devcash.R;
import com.example.devcash.Model.SettingsList;
import com.example.devcash.SubscriptionActivity;

import java.util.ArrayList;

public class SettingsActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ListView lv;
    //data container
    ArrayList<SettingsList> list = new ArrayList<SettingsList>();
    //adapter
    SettingsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // show Back button in the app bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //////////


        //instantiate
        lv = (ListView) this.findViewById(R.id.listview_settings);
        adapter = new SettingsAdapter(this, list);

        //populate the list
        list.add(new SettingsList(R.drawable.employee, "Account"));
        list.add(new SettingsList(R.drawable.store, "Enterprise"));
        list.add(new SettingsList(R.drawable.password, "Password"));
        list.add(new SettingsList(R.drawable.cash_billing, "Subscription"));
        list.add(new SettingsList(R.drawable.notification, "Notifications Settings"));
        list.add(new SettingsList(R.drawable.order, "Terms and Conditions"));
        list.add(new SettingsList(R.drawable.info, "About"));

        //delegate the adapter
        lv.setAdapter(adapter);

        //add listener to the listview
        lv.setOnItemClickListener(this);
    }

    // handles back button
    @Override
    public void onBackPressed() {
        finish();
    }

    ///////
    //handles back button
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            onBackPressed();
            return true;
        }else
            return super.onOptionsItemSelected(item);
    }

    /// listeners to settings listview
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        SettingsList selectedSettingsList = this.list.get(position);

        int icon = selectedSettingsList.getSettingsicon();
        String title = selectedSettingsList.getSettingstitle();

        //switch
        switch (position){
            case 0:
                Intent profile = new Intent(SettingsActivity.this, OwnerProfileActivity.class);
                startActivity(profile);
                break;
            case 1:
                Intent enterpise = new Intent(SettingsActivity.this, EnterpriseActivity.class);
                startActivity(enterpise);
                break;
            case 2:
                Intent passw = new Intent(SettingsActivity.this, ChangePasswordActivity.class);
                startActivity(passw);
                break;
            case 3:
                Intent subs = new Intent(SettingsActivity.this, SubscriptionActivity.class);
                startActivity(subs);
                break;
            case 4:
                Intent notifications = new Intent(SettingsActivity.this, NotificationsSettingsActivity.class);
                startActivity(notifications);
                break;
            case 5:
                Intent terms = new Intent(SettingsActivity.this, TermsActivity.class);
                startActivity(terms);
                break;
            case 6:
                Intent about = new Intent(SettingsActivity.this, AboutActivity.class);
                startActivity(about);
                break;
        }




//        Intent toprofile = new Intent(SettingsActivity.this, NotificationsSettingsActivity.class);
//        startActivity(toprofile);
    }
}