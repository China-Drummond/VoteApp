package com.example.drummond.voteapp;

/**
 * Created by Drummond on 2015/10/28.
 */
import com.android.volley.*;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class VolleyHttp {
    public static  StringRequest postStringRequest(String url, Map map,Response.Listener listener,Response.ErrorListener errorListener){

        final Map m_map = map;

        StringRequest stringRequest = new StringRequest(Request.Method.POST,url,listener,errorListener) {
            @Override
            protected Map getParams() throws AuthFailureError {
                return m_map;
            }
        };

        return stringRequest;
    }

//    public static JsonObjectRequest postJsonObjectRequest(String url,Response.Listener listener,Response.ErrorListener errorListener){
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url,)
//    }
}
