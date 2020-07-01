package com.indra.alter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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

    ArrayList<Item> allapps,bannedaps,common;
  //  ArrayList<String> bannedAppsname,allappsname;
    ListView listView;
    Button button;
    Switch aSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        allapps = new ArrayList<>();
        bannedaps = new ArrayList<>();
        common = new ArrayList<>();
       // bannedAppsname = new ArrayList<>();
       // allappsname = new ArrayList<>();

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
                    //bannedAppsname.add(appname.toLowerCase().trim());

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



        listView.setAdapter(adapter);
        aSwitch.setText("All Apps In your device");


        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b== true)
                {
                    listView.setAdapter(adapter1);
                    aSwitch.setText("Banned Appslist");
                    Log.e("TAG",common.toString());
                   // Log.e("TAG",bannedAppsname.toString());
                }
                else
                {
                    Log.e("T",allapps.size()+" A");
                   // Log.e("T",bannedAppsname.size()+" B");

                    common.clear();

                    for(int i=0;i<allapps.size();i++)
                    {
                        for(int j =0 ;j<bannedaps.size();j++)
                        {
                            if(allapps.get(i).getAppname().toLowerCase().equals(bannedaps.get(j).getAppname().toLowerCase()))
                            {
                                Item app= new Item();
                                app=allapps.get(i);
                                app.setAppdetails(bannedaps.get(j).getAppdetails());
                                common.add(app);
                            }
                        }
                    }

                    ItemAdapter commonadapter = new ItemAdapter(MainActivity.this,R.layout.list_item,common);

                    listView.setAdapter(commonadapter);
                    aSwitch.setText("Banned Apps In your device");

                }
            }
        });


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Save" menu option
            case R.id.game:
                Intent intent =new Intent(MainActivity.this,GameActivity.class);
                startActivity(intent);
                return true;

        }
        return super.onOptionsItemSelected(item);
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
               // allappsname.add(appName.toLowerCase().trim());
               // Log.e("App Name", i + appName);
            }


        }
    }
}