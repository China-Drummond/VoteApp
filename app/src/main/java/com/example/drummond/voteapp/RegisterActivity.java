package com.example.drummond.voteapp;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.view.*;
import android.view.View.*;
import android.widget.Toast;

/**
 * Created by Drummond on 2015/10/13.
 */
public class RegisterActivity extends AppCompatActivity implements OnClickListener{

    private Button btn_register;
    private EditText edit_account,edit_password,edit_safetycode,edit_email;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btn_register = (Button) findViewById(R.id.r_register);
        edit_account = (EditText) findViewById(R.id.r_account);
        edit_password = (EditText) findViewById(R.id.r_password);
        edit_safetycode = (EditText) findViewById(R.id.r_safetycode);
        edit_email = (EditText) findViewById(R.id.r_email);

        btn_register.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.r_register:
                String data = "account=" +  edit_account.getText().toString()
                        + "&password=" + edit_password.getText().toString()
                        + "&Email=" + edit_email.getText().toString()
                        + "&safetycode=" + edit_safetycode.getText().toString();
                Log.d("data", data);
                String address = HttpAddress.getAddress() + "testphp.php";
                HttpUtil.sendHttpRequest(address,data,new HttpUtil.HttpCallbackListener() {
                    @Override
                    public void onFinish(String response) {
                        Log.d("test", response);
                    }

                    @Override
                    public void onError(Exception e) {
                        Log.d("test", e.toString());
                    }
                });
                Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show();
                break;
        }
    }

}
