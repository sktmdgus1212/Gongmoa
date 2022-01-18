package com.example.gongmoa;

import android.graphics.Bitmap;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class WriteContestRequest extends StringRequest {

    // 서버 URL 설정 ( PHP 파일 연동 )
    final static private String URL = "http://192.168.0.23/contestRegister.php";
    private Map<String, String> map;


    public WriteContestRequest(String contest_name, int contest_field, int contest_startdate, int contest_enddate, String contest_writedate, String contest_introduce, String user_id, Bitmap contest_image, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);


        map = new HashMap<>();
        map.put("contest_name", contest_name);
        map.put("contest_field", contest_field+"");
        map.put("contest_startdate", contest_startdate+"");
        map.put("contest_enddate", contest_enddate+"");
        //map.put("contest_writer", user_seq);
        map.put("contest_writedate", contest_writedate);
        map.put("contest_introduce", contest_introduce);
        map.put("user_id", user_id);
        map.put("contest_image", contest_image+"");
       // System.out.println("RegisterRequest >>>map >>> " + map);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}