package com.example.gongmoa;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class LoginRequest extends StringRequest {

    final static private String URL = "http://192.168.0.23/userLogin.php";
    private Map<String, String> map;

    public LoginRequest(String user_id, String user_password, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("user_id", user_id);
        map.put("user_password", user_password);

       /* System.out.println("LoginRequest >>>user_id >>> " + user_id);
        System.out.println("LoginRequest >>>user_password >>> " + user_password);
*/
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
