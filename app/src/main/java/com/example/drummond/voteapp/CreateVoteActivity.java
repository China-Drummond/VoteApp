package com.example.drummond.voteapp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.view.View.*;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class CreateVoteActivity extends AppCompatActivity implements OnClickListener{

    private RequestQueue requestQueue;
    private StringRequest stringRequest;
    private Map map;
    private String account;
    private ListView myList;
    private AddItemAdpater addItemAdpater;
    private Button btn_create_create;
    private int voteNum;
    private int amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_vote);

        requestQueue = Volley.newRequestQueue(CreateVoteActivity.this);
        map = new HashMap();

        btn_create_create = (Button) findViewById(R.id.btn_create_create);
        btn_create_create.setOnClickListener(this);
        addItemAdpater = new AddItemAdpater(getApplicationContext());
        myList = (ListView) findViewById(R.id.lv_list_create);
        myList.setAdapter(addItemAdpater);
        account = getIntent().getStringExtra("account");
    }

    @Override
    public void onClick(View v){

        switch (v.getId()){
            case R.id.btn_create_create:
                addItemAdpater.notifyDataSetChanged();
                amount = myList.getChildCount();

                map.put("account",account);
                EditText et_title_create = (EditText) findViewById(R.id.et_title_create);
                map.put("title",et_title_create.getText().toString());

                for (int i = 0;i < myList.getChildCount();i++){
                    LinearLayout layout = (LinearLayout) myList.getChildAt(i);// 获得子item的layout
                    EditText et_item_item = (EditText) layout.findViewById(R.id.et_item_item);// 从layout中获得控件,根据其id
                    //Log.v(String.valueOf(i),et_item_item.getText().toString());
                    String item_current = "item" + String.valueOf(i + 1);
                    //Log.d("item_current",item_current);
                    String item_amount = item_current + "_amount";

                    map.put(item_current,et_item_item.getText().toString());
                    map.put(item_amount,String.valueOf(0));
                }


                map.put("amount",String.valueOf(amount));
                //Log.d("amount",String.valueOf(amount));
                Random random = new Random();
                voteNum = random.nextInt(899999) + 100000;
                map.put("voteNum",String.valueOf(voteNum));
                String url = HttpAddress.getAddress() + "creat_vote.php";
                stringRequest = VolleyHttp.postStringRequest(url,map,listener,errorListener);
                requestQueue.add(stringRequest);

                Intent intent_create = new Intent(CreateVoteActivity.this,VoteNumActivity.class);
                intent_create.putExtra("voteNum",String.valueOf(voteNum));
                startActivity(intent_create);

                break;
        }
    }

    Response.Listener listener = new Response.Listener() {
        @Override
        public void onResponse(Object o) {
            Log.d("Create","create success");
        }
    };

    Response.ErrorListener errorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError volleyError) {
            Log.e("error", volleyError.getMessage(), volleyError);
        }
    };
}
