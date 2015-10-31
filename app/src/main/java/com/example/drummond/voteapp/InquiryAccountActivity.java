package com.example.drummond.voteapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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

public class InquiryAccountActivity extends AppCompatActivity {

    private TextView tv_title_inquiry;
    private ListView lv_list_inquiry;
    private List<String> voteList;
    private String m_account;
    private Map map;
    private JSONArray jsonArray;
    private JSONObject jsonObject;
    private StringRequest stringRequest;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inquiry_account);

        voteList = new ArrayList<String>();
        tv_title_inquiry = (TextView) findViewById(R.id.tv_title_inquiry);
        lv_list_inquiry = (ListView) findViewById(R.id.lv_list_inquiry);
        m_account = getIntent().getStringExtra("account");

        String address = HttpAddress.getAddress() + "title.php";
        map = new HashMap();
        map.put("account",m_account);
        stringRequest = VolleyHttp.postStringRequest(address, map, listener, errorListener);
        requestQueue = Volley.newRequestQueue(InquiryAccountActivity.this);
        requestQueue.add(stringRequest);

        lv_list_inquiry.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    jsonObject = jsonArray.getJSONObject(position);
                    String voteNum = jsonObject.getString("voteNum");
                    Log.d("voteNum",voteNum);

                    Intent intent_percent = new Intent(InquiryAccountActivity.this,VotePercentActivity.class);
                    intent_percent.putExtra("voteNum",voteNum);
                    startActivity(intent_percent);

                }catch (JSONException e){
                    System.out.println("Something wrong...");
                    e.printStackTrace();
                }
            }
        });
    }

//    public void InitVoteItem(String account){
//        Cursor cursor = db.query("Vote",null,null,null,null,null,null);
//
//        if (cursor.moveToFirst()){
//            do {
//                if (account.equals(cursor.getString(cursor.getColumnIndex("account")))){
//                    String vote_title = cursor.getString(cursor.getColumnIndex("title"));
//                    //Log.d("title",vote_title);
//                    voteList.add(vote_title);
//                }
//            } while (cursor.moveToNext());
//        }
//
//        cursor.close();
//    }

    Response.Listener listener = new Response.Listener() {
        @Override
        public void onResponse(Object o) {
            Log.d("test",o.toString());
            try {
                jsonArray = new JSONArray(o.toString());

                for (int i = 0;i < jsonArray.length();i++){
                    jsonObject = jsonArray.getJSONObject(i);
                    String title = jsonObject.getString("title");
                    Log.d("title_current",title);
                    voteList.add(title);
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(InquiryAccountActivity.this, android.R.layout.simple_list_item_1, voteList);
                lv_list_inquiry.setAdapter(adapter);
            }catch (JSONException e){
                System.out.println("Something wrong...");
                e.printStackTrace();
            }
        }
    };

    Response.ErrorListener errorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError volleyError) {
            Log.e("error", volleyError.getMessage(), volleyError);
        }
    };

}
