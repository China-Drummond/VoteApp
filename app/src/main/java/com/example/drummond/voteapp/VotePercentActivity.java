package com.example.drummond.voteapp;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class VotePercentActivity extends AppCompatActivity {

    private int sum;
    private int m_amount;
    private String m_voteNum;
    private String m_title;
    private ListView lv_list_percent;
    private TextView tv_title_percent;
    private ArrayList<String> list_percent;
    private Map map;
    private JSONArray jsonArray;
    private JSONObject jsonObject;
    private StringRequest stringRequest;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote_percent);
        lv_list_percent = (ListView) findViewById(R.id.lv_list_percent);
        tv_title_percent = (TextView) findViewById(R.id.tv_title_percent);
        m_voteNum = getIntent().getStringExtra("voteNum");
        list_percent = new ArrayList<String>();
        sum = 0;

        String address = HttpAddress.getAddress() + "percent.php";
        map = new HashMap();
        map.put("voteNum",m_voteNum);
        stringRequest = VolleyHttp.postStringRequest(address, map, listener, errorListener);
        requestQueue = Volley.newRequestQueue(VotePercentActivity.this);
        requestQueue.add(stringRequest);
    }

//    public void initListPercent(){
//        Cursor cursor = db.query("Vote",null,null,null,null,null,null);
//
//        if (cursor.moveToFirst()){
//            do {
//                if (m_voteNum == cursor.getInt(cursor.getColumnIndex("voteNum"))){
//                    tv_title_percent.setText(cursor.getString(cursor.getColumnIndex("title")));
//
//                    for (int i = 0;i < cursor.getInt(cursor.getColumnIndex("amount"));i++){
//                        String item_current = "item" + String.valueOf(i + 1) + "_amount";
//                        Log.d("item_current",item_current);
//                        sum += cursor.getInt(cursor.getColumnIndex(item_current));
//                        Log.d("sum_current",String.valueOf(sum));
//                    }
//
//                    for (int i = 0;i < cursor.getInt(cursor.getColumnIndex("amount"));i++){
//                        NumberFormat nt = NumberFormat.getPercentInstance();
//                        nt.setMaximumFractionDigits(2);
//                        String item_current = "item" + String.valueOf(i + 1) + "_amount";
//                        double percent = cursor.getInt(cursor.getColumnIndex(item_current)) / (double)sum;
//                        Log.d("percent_double",String.valueOf(percent));
//                        String item = cursor.getString(cursor.getColumnIndex("item" + String.valueOf(i + 1)))
//                                + " ："
//                                + String.valueOf(nt.format(percent));
//                        list_percent.add(item);
//                    }
//
//                    break;
//                }
//            } while (cursor.moveToNext());
//
//            cursor.close();
//        }
//    }

    Response.Listener listener = new Response.Listener() {
        @Override
        public void onResponse(Object o) {
            Log.d("test",o.toString());
            try {
                jsonArray = new JSONArray(o.toString());
                jsonObject = jsonArray.getJSONObject(0);

                m_amount = Integer.parseInt(jsonObject.getString("amount"));
                Log.d("m_amount",String.valueOf(m_amount));
                m_title = jsonObject.getString("title");
                Log.d("m_title",m_title);
                tv_title_percent.setText(m_title);
//                for (int i = 0;i < jsonArray.length();i++){
//                    jsonObject = jsonArray.getJSONObject(i);
//                    String title = jsonObject.getString("title");
//                    Log.d("title_current",title);
//                    voteList.add(title);
//                }

                for (int i = 0;i < m_amount;i++){
                    String item_amount_current = jsonObject.getString("item" + String.valueOf(i + 1) + "_amount");
                    Log.d("item_amount_current",item_amount_current);
                    sum = sum + Integer.parseInt(item_amount_current);
                    Log.d("Sum",String.valueOf(sum));
                }

                for (int i = 0;i < m_amount;i++){
                    NumberFormat nt = NumberFormat.getPercentInstance();
                    nt.setMaximumFractionDigits(2);

                    String item_amount_current = "item" + String.valueOf(i + 1) + "_amount";
                    Log.d("item_amount_current",item_amount_current);
                    String amount = jsonObject.getString(item_amount_current);

                    String item_current = "item" + String.valueOf(i + 1);
                    Log.d("item_current",item_current);
                    double percent = Integer.parseInt(amount) / (double)sum;

                    String item = jsonObject.getString(item_current)
                            + " ：" + amount + "票    "
                            + String.valueOf(nt.format(percent));
                    list_percent.add(item);
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(VotePercentActivity.this, android.R.layout.simple_list_item_1, list_percent);
                lv_list_percent.setAdapter(adapter);
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
