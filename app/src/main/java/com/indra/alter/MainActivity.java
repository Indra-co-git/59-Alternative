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
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ArrayList<Item> allapps,bannedaps;
    ListView listView;
    Button button;
    Switch aSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        allapps = new ArrayList<>();
        bannedaps = new ArrayList<>();
        aSwitch = findViewById(R.id.choice);
        listView = findViewById(R.id.appslist);

        button = findViewById(R.id.click);

        installedapps();

        Collections.sort(allapps, new Comparator<Item>() {
            @Override
            public int compare(Item i1, Item i2) {
                return i1.getAppname().compareTo(i2.getAppname());
            }
        });




        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();

        DatabaseReference appsdRef = rootRef.child("apps");

        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    String appname = ds.child("appname").getValue(String.class);
                    String alturl = ds.child("alternativeurl").getValue(String.class);

                    bannedaps.add(new Item(appname,alturl));

                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        appsdRef.addListenerForSingleValueEvent(eventListener);


        final ItemAdapter adapter = new ItemAdapter(this,R.layout.list_item,allapps);

        final ItemAdapter adapter1 = new ItemAdapter(this,R.layout.list_item,bannedaps);


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


            }
        });



        aSwitch.setText("Apps In your device");
        listView.setAdapter(adapter);

        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b== true)
                {
                    listView.setAdapter(adapter1);
                    aSwitch.setText("Banned Appslist");
                }
                else
                {
                    listView.setAdapter(adapter);
                    aSwitch.setText("Apps In your device");
                }
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