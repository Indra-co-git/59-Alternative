package com.indra.alter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ArrayList<Item> allapps;
    ListView listView;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        allapps = new ArrayList<>();
        listView = findViewById(R.id.appslist);

        button = findViewById(R.id.click);

        installedapps();

        Collections.sort(allapps, new Comparator<Item>() {
            @Override
            public int compare(Item i1, Item i2) {
                return i1.getAppname().compareTo(i2.getAppname());
            }
        });

        ItemAdapter adapter = new ItemAdapter(getBaseContext(),R.layout.list_item,allapps);


        listView.setAdapter(adapter);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(MainActivity.this,AddingPage.class);
                //intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.microsoft.office.officelens&hl=en_IN"));
                startActivity(intent);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                Toast.makeText(getBaseContext() ,"opening" + position,Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.microsoft.office.officelens&hl=en_IN"));
                startActivity(intent);

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