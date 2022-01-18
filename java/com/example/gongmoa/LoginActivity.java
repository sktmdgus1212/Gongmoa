package com.example.gongmoa;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText et_id, et_pass;
    private Button btn_login, btn_register;
    String contest_user_id;
    public static Context context_main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        context_main=this;
        et_id = (EditText) findViewById(R.id.InputId);
        et_pass = (EditText) findViewById(R.id.InputPassword);
        btn_login = (Button) findViewById(R.id.loginbutton);
        btn_register = (Button) findViewById(R.id.memberjoin);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user_id = et_id.getText().toString();
                String user_password = et_pass.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            // TODO : 인코딩 문제때문에 한글 DB인 경우 로그인 불가
                            System.out.println("로그인");
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if (success) { // 로그인에 성공한 경우
                                String user_id = jsonObject.getString("user_id");
                                String user_password = jsonObject.getString("user_password");
                                contest_user_id=user_id;
                                Toast.makeText(getApplicationContext(), "로그인에 성공하였습니다.", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                intent.putExtra("user_id", user_id);
                                intent.putExtra("user_password", user_password);
                                startActivity(intent);
                                finish();
                            } else { // 로그인에 실패한 경우
                                Toast.makeText(getApplicationContext(), "로그인에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                LoginRequest loginRequest = new LoginRequest(user_id, user_password, responseListener);
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                queue.add(loginRequest);
            }
        });


    }
}