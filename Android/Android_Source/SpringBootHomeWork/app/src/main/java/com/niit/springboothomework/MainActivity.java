package com.niit.springboothomework;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.renderscript.RenderScript;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.niit.pojo.Student;
import com.niit.pojo.User;
import com.niit.pojo.util.requestUrl;
import com.niit.util.OkHttpUtil;
import com.niit.util.ServerResponse;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class MainActivity extends AppCompatActivity {
    //public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    private final static String TAG = "MainActivity";
//    private String url = "http://192.168.43.134:8086/restful/User/login";
    private String url = requestUrl.UserLogin.getAll();
    private static final int GET = 1;
    private static final int POST = 2;
    private static final int FAILURE = -1;
    private Intent intent;
    private Gson gson = new Gson();
    private OkHttpUtil okHttpUtil = new OkHttpUtil();
    private User user = new User();
    private EditText login_id;
    private Button login_button;
    private EditText password;
    //  private final OkHttpClient client = new OkHttpClient();
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case GET:
                    //获取数据
                    break;
                case POST:
                    //获取数据
                    Log.v(TAG, (String) msg.obj);
                    ServerResponse<Object> serverResponse = gson.fromJson((String) msg.obj, ServerResponse.class);
                    if (serverResponse.getStatus() == 0) {
                        Toast.makeText(MainActivity.this, "wrong password/id", Toast.LENGTH_LONG).show();
                    } else {
                        Object objclass = serverResponse.getData();
                        Log.v(TAG, serverResponse.getStatus() + "" + objclass);
                        if (objclass instanceof java.util.ArrayList) {
                            intent = new Intent(MainActivity.this, AdministratorActivity.class);
                            intent.putExtra("studentList", (String) msg.obj);
                            intent.putExtra("user", gson.toJson(user));
                        } else if (objclass instanceof com.google.gson.internal.LinkedTreeMap) {
                            intent = new Intent(MainActivity.this, StudentActivity.class);
                            intent.putExtra("student", (String) msg.obj);
                            intent.putExtra("user", gson.toJson(user));
                        }

                        startActivity(intent);

                    }
                    break;
                case FAILURE:
                   Toast.makeText(MainActivity.this, (String) msg.obj + "", Toast.LENGTH_LONG).show();
            }
        }


    };


    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btLogin:
                    //Toast.makeText(MainActivity.this,"hello,click",Toast.LENGTH_LONG).show();
                    try {
                        String userId = login_id.getText().toString().trim();
                        String userPasswd = password.getText().toString().trim();
                        if(userId.length()==0) {
                           Toast.makeText(MainActivity.this,"your id is null!",Toast.LENGTH_LONG).show();
                         }
                        else if(userPasswd.length()==0) {
                            Toast.makeText(MainActivity.this,"your password is null!",Toast.LENGTH_LONG).show();
                        }else { int userId2 = Integer.parseInt(userId);
                            user.setLoginid(userId2);
                            user.setPassword(userPasswd);
                            okHttpUtil.setObj(user);
                            okHttpUtil.setUrl(url);
                            okHttpUtil.getDataFromPost(handler);
                            break;}

                        } catch (Exception e) {
                        Toast.makeText(MainActivity.this, "your format is wrong,id must be number", Toast.LENGTH_LONG).show();
                    }


            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        login_id = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        login_button = (Button) findViewById(R.id.btLogin);
        login_button.setOnClickListener(listener);//setOnClickListener(
        login_id.addTextChangedListener(textWatcher);
        password.addTextChangedListener(textWatcher);
    }//onCreate(Bundle savedInstanceState) {

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            if (s.length() > 0) {
                login_button.setEnabled(true);
            } else {
                login_button.setEnabled(false);
            }
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s.length() > 0) {
                login_button.setEnabled(true);
            } else {
                login_button.setEnabled(false);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }


    };

















}
