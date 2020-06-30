package com.example.alter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class ItemAdapter extends ArrayAdapter<Item> {


    public ItemAdapter(@NonNull Context context, int resource, ArrayList<Item> objects) {
        super(context, resource,objects);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        TextView name = convertView.findViewById(R.id.appname);
        TextView detial = convertView.findViewById(R.id.appdetial);
        ImageView logo = convertView.findViewById(R.id.appicon);
        Button button = convertView.findViewById(R.id.delete);


        final Item item = getItem(position);

        name.setText(item.getAppname());
        detial.setText(item.getAppdetails());
        logo.setImageDrawable(item.getAppicon());
       // logo.setImageResource(item.getAppicon());

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DELETE);
                intent.setData(Uri.parse("package:"+item.getAppdetails()));
                getContext().startActivity(intent);

            }
        });

        return convertView;

    }
}
