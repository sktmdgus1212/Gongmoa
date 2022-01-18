package com.example.gongmoa;

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

public class RegisterActivity extends AppCompatActivity {

    private EditText et_id, et_pass, et_name, et_age;
    private Button btn_register;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        et_id = (EditText) findViewById(R.id.registerid);
        et_pass = (EditText) findViewById(R.id.registerpassword);
        et_name = (EditText) findViewById(R.id.registername);
        et_age = (EditText) findViewById(R.id.registerage);

        btn_register = (Button) findViewById(R.id.complete);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user_id = et_id.getText().toString();
                String user_pass = et_pass.getText().toString();
                String user_name = et_name.getText().toString();
                String str_user_age = et_age.getText().toString();

                if (user_id.equals("") || user_pass.equals("") || user_name.equals("") || str_user_age.equals("")){
                    Toast.makeText(getApplicationContext(), "모든 값을 입력하세요.", Toast.LENGTH_SHORT).show();
                }
                else {
                    int user_age = Integer.parseInt(str_user_age);
                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                boolean success = jsonObject.getBoolean("success");
                                if (success) { // 회원등록에 성공한 경우
                                    Toast.makeText(getApplicationContext(), "회원 등록에 성공하였습니다.", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else { // 회원등록에 실패한 경우
                                    Toast.makeText(getApplicationContext(), "회원 등록에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    };
                    // 서버로 Volley를 이용해서 요청을 함.
                    RegisterRequest registerRequest = new RegisterRequest(user_id, user_pass, user_name, user_age, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                    queue.add(registerRequest);
                }
            }
        });

    }
}
