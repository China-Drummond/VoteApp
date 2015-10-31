package com.example.drummond.voteapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Drummond on 2015/10/29.
 */
public class HttpAddress extends AppCompatActivity {

    private static String address = "http://192.168.1.108/myweb/";
    private EditText et_ip_address;
    private Button btn_ok_address;

    public static String getAddress(){
        return address;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.http_address);

        et_ip_address = (EditText) findViewById(R.id.et_ip_address);
        btn_ok_address = (Button) findViewById(R.id.btn_ok_address);

        btn_ok_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                address = "http://" + et_ip_address.getText().toString() + "/myweb/";
                Toast.makeText(HttpAddress.this,"设置成功",Toast.LENGTH_SHORT).show();
                Log.d("address",address);
            }
        });
    }
}
