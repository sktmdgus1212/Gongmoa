package com.example.gongmoa;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegisterRequest extends StringRequest {

    // 서버 URL 설정 ( PHP 파일 연동 )
    final static private String URL = "http://192.168.0.23/userRegister.php";
    private Map<String, String> map;


    public RegisterRequest(String user_id, String user_password, String user_name, int user_age, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        System.out.println("RegisterRequest >>>user_id >>> " + user_id);
        System.out.println("RegisterRequest >>>user_password >>> " + user_password);
        System.out.println("RegisterRequest >>>user_name >>> " + user_name);
        System.out.println("RegisterRequest >>>user_age >>> " + user_age);


        map = new HashMap<>();

        map.put("user_id",user_id);
        map.put("user_password", user_password);
        map.put("user_name", user_name);
        map.put("user_age", user_age + "");
        //System.out.println("RegisterRequest >>>map >>> " + map);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}