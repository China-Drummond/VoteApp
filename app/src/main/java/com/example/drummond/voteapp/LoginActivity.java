package com.example.drummond.voteapp;

import android.content.Intent;
import android.util.Log;
import android.view.*;
import android.view.View.*;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.android.volley.*;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity implements OnClickListener {

    private Button btn_login,btn_register,btn_examine,btn_set_address;
    private EditText edit_account,edit_password;
    private RequestQueue requestQueue;
    private StringRequest stringRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        requestQueue = Volley.newRequestQueue(LoginActivity.this);

        btn_login = (Button)findViewById(R.id.login);
        btn_register = (Button)findViewById(R.id.register);
        btn_examine = (Button)findViewById(R.id.examine);
        btn_set_address = (Button)findViewById(R.id.set_address);
        edit_account = (EditText)findViewById(R.id.account);
        edit_password = (EditText)findViewById(R.id.password);

        btn_login.setOnClickListener(this);
        btn_register.setOnClickListener(this);
        btn_examine.setOnClickListener(this);
        btn_set_address.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        String address = HttpAddress.getAddress() + "check.php";

        switch (v.getId()){
            case R.id.login:
                stringRequest = new StringRequest(Request.Method.POST,address,listener,errorListener) {
                    @Override
                    protected Map getParams() throws AuthFailureError {
                        Map map = new HashMap();
                        map.put("account",edit_account.getText().toString());
                        map.put("password",edit_password.getText().toString());
                        return map;
                    }
                };
                requestQueue.add(stringRequest);

                break;

            case R.id.register:
                Intent intent_register = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent_register);
                break;

            case R.id.examine:
                Intent intent_examine = new Intent(LoginActivity.this,ExamineActivity.class);
                startActivity(intent_examine);
                break;

            case R.id.set_address:
                Intent intent_address = new Intent(LoginActivity.this,HttpAddress.class);
                startActivity(intent_address);
                break;
        }
    }

    Response.Listener listener = new Response.Listener() {
        @Override
        public void onResponse(Object o) {
            Log.d("response",o.toString());

            if (o.toString().equals("success")) {
                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                intent.putExtra("account",edit_account.getText().toString());
                startActivity(intent);
            } else {
                Toast.makeText(LoginActivity.this,"用户名或密码错误!",Toast.LENGTH_SHORT).show();
        }
    }
    };

    Response.ErrorListener errorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError volleyError) {
            Log.e("error", volleyError.getMessage(), volleyError);
        }
    };

//    public boolean checkOutAccount(String account,String password){
//
//        String data = "account=" + account + "&password=" + password;
//        String address = "http://192.168.2.104/myweb/check.php";
//        final boolean state;
//
//        HttpUtil.sendHttpRequest(address,data,new HttpUtil.HttpCallbackListener() {
//            @Override
//            public void onFinish(String response) {
//                Log.d("test", response);
//                if (response.equals("success")){
//                }
//            }
//
//            @Override
//            public void onError(Exception e) {
//                Log.d("test", "fail");
//            }
//        });
//
//        return false;
//    }

}
