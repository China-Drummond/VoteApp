package com.example.drummond.voteapp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.renderscript.Sampler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VoteActivity extends AppCompatActivity {

    private int m_voteNum;
    private TextView tv_title_vote;
    private ListView lv_list_vote;
    private List<String> itemList;
    private int m_amount;
    private String m_title;
    private Map map;
    private JSONArray jsonArray;
    private JSONObject jsonObject;
    private StringRequest stringRequest;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote);
        m_voteNum = Integer.parseInt(getIntent().getStringExtra("voteNum"));
        itemList = new ArrayList<String>();
        tv_title_vote = (TextView) findViewById(R.id.tv_title_vote);
        lv_list_vote = (ListView) findViewById(R.id.lv_list_vote);

        map = new HashMap();
        map.put("voteNum",String.valueOf(m_voteNum));
        String address = HttpAddress.getAddress() + "obtain.php";
        stringRequest = VolleyHttp.postStringRequest(address, map, listener, errorListener);
        requestQueue = Volley.newRequestQueue(VoteActivity.this);
        requestQueue.add(stringRequest);
        lv_list_vote.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item_current ="item" + String.valueOf(position + 1);
                Log.d("item_current", item_current);

                try{
                    String item_current_amount = jsonObject.getString(item_current + "_amount");
                    Log.d("item_current_amount", item_current_amount);
                    int amount_click = Integer.parseInt(item_current_amount) + 1;
                    Log.d("amount_click", item_current_amount);

                    Map map_add = new HashMap();
                    map_add.put("voteNum",String.valueOf(m_voteNum));
                    map_add.put("item",item_current + "_amount");
                    map_add.put("item_amount",String.valueOf(amount_click));
                    Log.d("item_amount",String.valueOf(amount_click));

                    String address_add = HttpAddress.getAddress() + "add.php";
                    stringRequest = VolleyHttp.postStringRequest(address_add, map_add, listener_add, errorListener);
                    requestQueue.add(stringRequest);
                }catch (JSONException e){
                    System.out.println("Something wrong...");
                    e.printStackTrace();
                }

                Intent intent_success = new Intent(VoteActivity.this,VoteSuccessActivity.class);
                startActivity(intent_success);
            }
        });
    }

//    public void initItems(){
//        Cursor cursor = db.query("Vote",null,null,null,null,null,null);
//        if (cursor.moveToFirst()){
//            do {
//                int voteNum = cursor.getInt(cursor.getColumnIndex("voteNum"));
//
//                if (m_voteNum == voteNum){
//                    tv_title_vote.setText(cursor.getString(cursor.getColumnIndex("title")));
//
//                    m_amount = cursor.getInt(cursor.getColumnIndex("amount"));
//                    for (int i = 0;i < m_amount;i++){
//                        String current_item = "item" + String.valueOf(i + 1);
//                        //Log.d("current_item",current_item);
//                        itemList.add(cursor.getString(cursor.getColumnIndex(current_item)));
//                    }
//
//                    break;
//                }
//            } while (cursor.moveToNext());
//
//            tv_title_vote.setText(cursor.getString(cursor.getColumnIndex("title")));
//            itemList.add(cursor.getString(cursor.getColumnIndex(current_item)));
//        }
//    }

//    public void itemIncrease(String item){
//        String item_amount = item + "_amount";
//        Log.d("item_amount",item_amount);
//        Cursor cursor = db.query("Vote",null,null,null,null,null,null);
//
//        if (cursor.moveToFirst()){
//            do {
//                int voteNum = cursor.getInt(cursor.getColumnIndex("voteNum"));
//
//                if (m_voteNum == voteNum){
//                    int amount = cursor.getInt(cursor.getColumnIndex(item_amount));
//                    ContentValues values = new ContentValues();
//                    values.put(item_amount, amount + 1);
//                    db.update("Vote",values,"voteNum=?",new String[]{String.valueOf(m_voteNum)});
//
//                    break;
//                }
//            }while (cursor.moveToNext());
//
//            cursor.close();
//        }
//    }

    Response.Listener listener = new Response.Listener() {
        @Override
        public void onResponse(Object o) {
            Log.d("test",o.toString());
            if (o.toString().equals("false"))
                tv_title_vote.setText("投票码错误，找不到对应投票");
            try {
                jsonArray = new JSONArray("["+o.toString()+"]");
                jsonObject = jsonArray.getJSONObject(0);
//                String account = jsonObject.getString("account");
//                Log.d("account",account);

                m_title = jsonObject.getString("title");
                Log.d("title",m_title);
                tv_title_vote.setText(m_title);

                m_amount = Integer.parseInt(jsonObject.getString("amount"));
                for (int i = 0;i < m_amount;i++){
                    String item_current = "item" + String.valueOf(i+1);
                    String item_data_current = jsonObject.getString(item_current);
                    itemList.add(item_data_current);
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(VoteActivity.this, android.R.layout.simple_list_item_1, itemList);
                lv_list_vote.setAdapter(adapter);

            }catch (JSONException e){
                System.out.println("Something wrong...");
                e.printStackTrace();
            }
        }
    };

    Response.Listener listener_add = new Response.Listener(){
        @Override
        public void onResponse(Object o){
//            Intent intent_success = new Intent(VoteActivity.this,VoteSuccessActivity.class);
//            startActivity(intent_success);
            Log.d("add",o.toString());
        }
    };

    Response.ErrorListener errorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError volleyError) {
            Log.e("error", volleyError.getMessage(), volleyError);
        }
    };
}
