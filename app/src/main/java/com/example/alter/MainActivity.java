package com.example.alter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ArrayList<Item> allapps;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        allapps = new ArrayList<>();
        listView = findViewById(R.id.appslist);

        installedapps();

        Collections.sort(allapps, new Comparator<Item>() {
            @Override
            public int compare(Item i1, Item i2) {
                return i1.getAppname().compareTo(i2.getAppname());
            }
        });

        ItemAdapter adapter = new ItemAdapter(getBaseContext(),R.layout.list_item,allapps);


        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


            }
        });

    }


    public void installedapps(){
        List<PackageInfo> packagelist=getPackageManager().getInstalledPackages(0);
        for(int i=0;i<packagelist.size();i++)
        {
            PackageInfo packageInfo= packagelist.get(i);
            if( (packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) ==0 )
            {
                String appName = packageInfo.applicationInfo.loadLabel(getPackageManager()).toString();

                String apppackage = packageInfo.applicationInfo.packageName.toString();

//                try
//                {
//                    Drawable icon = this.getPackageManager().getApplicationIcon("com.example.testnotification");
//                    imageView.setImageDrawable(icon);
//                }
//                catch (PackageManager.NameNotFoundException e)
//                {
//                    e.printStackTrace();
//                }
                Drawable applogo =  packageInfo.applicationInfo.loadIcon(getPackageManager());

                allapps.add(new Item(appName,apppackage,applogo));
                Log.e("App Name", i + appName);
            }


        }
    }
}