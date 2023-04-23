package com.example.ar14_rv;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<String> lv_data;
    MyRecyclerViewAdapter lv_adapter;
    public ArrayList<String> mZipCodes;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView lv_recyclerView = findViewById(R.id.vv_rvList);
        lv_recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mZipCodes = new ArrayList<>();
        mZipCodes.add("48197");
        mZipCodes.add("85365");
        mZipCodes.add("99703");
        mZipCodes.add("48180");
        mZipCodes.add("28376");


        lv_data = new ArrayList<>();
        for (String zipCode : mZipCodes) {
            lv_data.add(zipCode);
        }
        lv_adapter = new MyRecyclerViewAdapter(lv_data);
        lv_recyclerView.setAdapter(lv_adapter);

        FloatingActionButton fabZipCode = findViewById(R.id.fab_zipcode);
        fabZipCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showZipCodeDialog();
            }
        });
    }
    private void showZipCodeDialog() {
        final EditText editText = new EditText(this);
        editText.setInputType(InputType.TYPE_CLASS_NUMBER);
        editText.setHint("Enter a Zip Code");

        new AlertDialog.Builder(this)
                .setTitle("Enter Zip Code")
                .setMessage("Please enter a zip code")
                .setView(editText)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String zipCode = editText.getText().toString();
                        lv_data.add(zipCode);
                        lv_adapter.notifyDataSetChanged();
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
}