package com.example.fp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.hdodenhof.circleimageview.CircleImageView;


public class MainFragment extends Fragment {
    CircleImageView buttonImage1, buttonImage2, buttonImage3, buttonImage4;
    Context context;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview =  inflater.inflate(R.layout.fragment_main, container, false);
        context = rootview.getContext();

        buttonImage1  = (CircleImageView) rootview.findViewById(R.id.button1);
        buttonImage2  = (CircleImageView) rootview.findViewById(R.id.button2);
        buttonImage3  = (CircleImageView) rootview.findViewById(R.id.button3);
        buttonImage4  = (CircleImageView) rootview.findViewById(R.id.button4);

        buttonImage1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context,InsertActivity.class);
                //Bundle bundle = new Bundle();
                //bundle.putString("Key","1");
                //intent.putExtras(bundle);
                //intent.putExtra("Key","1");
                startActivity(intent);
            }
        });
        buttonImage2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,InsertActivity.class);
                //intent.putExtra("Key","2");
                startActivity(intent);
            }
        });
        buttonImage3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,InsertActivity.class);
                //intent.putExtra("Key","3");
                startActivity(intent);
            }
        });
        buttonImage4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,InsertActivity.class);
                //intent.putExtra("Key","4");
                startActivity(intent);
            }
        });

        return rootview;
    }
}