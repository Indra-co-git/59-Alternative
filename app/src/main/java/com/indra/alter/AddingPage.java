package com.indra.alter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddingPage extends AppCompatActivity {

    EditText ed1,ed2;
    Button sub;
    CheckBox c1,c2;


    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference,databaseReferencec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding_page);

        ed1=findViewById(R.id.apname);
        ed2 = findViewById(R.id.altlink);

        c1= findViewById(R.id.banapp);
        c2= findViewById(R.id.chinaapp);

        sub = findViewById(R.id.submit);


        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("apps");
        databaseReferencec = firebaseDatabase.getReference().child("chinaapps");

        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(ed1.getText().toString().trim().length()==0)
                {
                    Toast.makeText(AddingPage.this,"an't be left blank",Toast.LENGTH_SHORT).show();
                }
                else if(ed2.getText().toString().trim().length()==0)
                {
                    Toast.makeText(AddingPage.this,"Can't be left blank",Toast.LENGTH_SHORT).show();
                }
                else {

                    App app = new App(ed1.getText().toString().trim(),ed2.getText().toString().trim());

                    if(c1.isChecked())
                        databaseReference.child(ed1.getText().toString().trim()).setValue(app);

                    if(c2.isChecked())
                        databaseReferencec.child(ed1.getText().toString().trim()).setValue(app);

                    ed1.setText("");
                    ed2.setText("");
                    Toast.makeText(AddingPage.this, "Data Recorded..!", Toast.LENGTH_SHORT).show();
                }


            }
        });


    }
}