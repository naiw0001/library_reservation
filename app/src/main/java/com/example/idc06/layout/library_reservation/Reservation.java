package com.example.idc06.layout.library_reservation;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

/**
 * Created by idc06 on 2016-12-28.
 */

public class Reservation extends AppCompatActivity{

    private Button li_1,li_2,li_3;
    private String id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reservation);
        li_1 = (Button)findViewById(R.id.library_1);
        li_2 = (Button)findViewById(R.id.library_2);
        li_3 = (Button)findViewById(R.id.library_3);
        Intent intent = getIntent();
        id = intent.getStringExtra("id");

    }

    public void choose_library(View v){
        switch (v.getId()){
            case R.id.library_1:
                Intent intent1 = new Intent(Reservation.this,Library1_Activity.class);
                intent1.putExtra("id",id);
                startActivity(intent1);
                break;
            case R.id.library_2:
                Intent intent2 = new Intent(Reservation.this,Library2_Activity.class);
                intent2.putExtra("id",id);
                startActivity(intent2);
                break;
            case R.id.library_3:
                Intent intent3 = new Intent(Reservation.this,Library3_Activity.class);
                intent3.putExtra("id",id);
                startActivity(intent3);
                break;



        }


    }

}
