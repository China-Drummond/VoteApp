package com.example.drummond.voteapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class VoteNumActivity extends AppCompatActivity {

    private String voteNum;
    private TextView tv_num_vote;
    Intent intent_create;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote_num);

        tv_num_vote = (TextView) findViewById(R.id.tv_num_vote);
        intent_create = getIntent();
        voteNum = intent_create.getStringExtra("voteNum");
        tv_num_vote.setText(voteNum);
    }

}
