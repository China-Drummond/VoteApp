package com.example.drummond.voteapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.view.View.*;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements OnClickListener{

    private Button btn_create_main,btn_inquiry_main,btn_examine_main,btn_exit_main;
    private String account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        account = getIntent().getStringExtra("account");
        Log.d("account_main", account);

        btn_create_main = (Button) findViewById(R.id.btn_create_main);
        btn_inquiry_main = (Button) findViewById(R.id.btn_inquiry_main);
        btn_examine_main = (Button) findViewById(R.id.btn_examine_main);
        btn_exit_main = (Button) findViewById(R.id.btn_exit_main);

        btn_create_main.setOnClickListener(this);
        btn_examine_main.setOnClickListener(this);
        btn_inquiry_main.setOnClickListener(this);
        btn_exit_main.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){

        switch (v.getId()){
            case R.id.btn_create_main:
                Intent intent_create = new Intent(MainActivity.this,CreateVoteActivity.class);
                intent_create.putExtra("account",account);
                startActivity(intent_create);
                break;
            case R.id.btn_examine_main:
                Intent intent_examine = new Intent(MainActivity.this,ExamineActivity.class);
                startActivity(intent_examine);
                break;
            case R.id.btn_inquiry_main:
                Intent intent_inquiry = new Intent(MainActivity.this,InquiryAccountActivity.class);
                intent_inquiry.putExtra("account",account);
                startActivity(intent_inquiry);
                break;
            case R.id.btn_exit_main:
                finish();
                break;
        }
    }

}
