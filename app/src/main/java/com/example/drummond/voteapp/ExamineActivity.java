package com.example.drummond.voteapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.view.*;
import android.view.View.*;

public class ExamineActivity extends AppCompatActivity implements OnClickListener{

    private EditText et_votenum_examine;
    private Button btn_ok_examine;
    private String voteNum;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_examine);

        et_votenum_examine = (EditText) findViewById(R.id.et_votenum_examine);
        btn_ok_examine = (Button) findViewById(R.id.btn_ok_examine);
        btn_ok_examine.setOnClickListener(this);
    }

    @Override
    public void onClick(View view){
        voteNum = et_votenum_examine.getText().toString();
        Intent intent_vote = new Intent(ExamineActivity.this,VoteActivity.class);
        intent_vote.putExtra("voteNum",voteNum);
        startActivity(intent_vote);
    }

}
