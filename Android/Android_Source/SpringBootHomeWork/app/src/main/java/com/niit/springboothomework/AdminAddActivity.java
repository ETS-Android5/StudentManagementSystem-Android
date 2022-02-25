package com.niit.springboothomework;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.niit.pojo.ResponseCode;
import com.niit.pojo.Student;
import com.niit.pojo.StudentDetail;
import com.niit.pojo.User;
import com.niit.pojo.util.requestUrl;
import com.niit.util.OkHttpUtil;
import com.niit.util.ServerResponse;

import java.util.List;

public class AdminAddActivity extends AppCompatActivity {
    private final static String TAG="AdminAddActivity";
    private ServerResponse<Object> serverResponse;
    private final static int GET = 1;
    private final static int POST = 2;
    private Intent intent;
    private Gson gson = new Gson();
    private OkHttpUtil okHttpUtil=new OkHttpUtil();
    private String url=requestUrl.AdminAddNewStudent.getAll();
    //private String url = "http://192.168.43.134:8086/restful/Admin/add_new_students";


    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg){
            Log.v(TAG,(String)msg.obj);
            serverResponse= gson.fromJson((String)msg.obj, ServerResponse.class);
            Toast.makeText(AdminAddActivity.this,serverResponse.getMsg(),Toast.LENGTH_LONG).show();
            
        }


    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add);
        Button bt = (Button)findViewById(R.id.btAsk);
        EditText edName = findViewById(R.id.edName);
        EditText edBatch = findViewById(R.id.edBatch);
        EditText edAge = findViewById(R.id.edAge);
        EditText edAddress = findViewById(R.id.edAddress);
        EditText edContactNumber = findViewById(R.id.edContactNumber);
        EditText edEmail = findViewById(R.id.edEmail);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //提示框
                AlertDialog.Builder al=new AlertDialog.Builder(AdminAddActivity.this);
                al.setTitle("Attention");
                al.setMessage("Do you confirm the information?");
                al.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        StudentDetail student = new StudentDetail();
                        student.setName(edName.getText().toString());
                        student.setBatch(edBatch.getText().toString());
                        student.setAge(edAge.getText().toString());
                        student.setAddress(edAddress.getText().toString());
                        student.setContactNumber(edContactNumber.getText().toString());
                        student.setEmail(edEmail.getText().toString());
                        okHttpUtil.setObj(student);
                        okHttpUtil.setUrl(url);
                        okHttpUtil.getDataFromPost(handler);
                    }
                });
                al.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                al.show();

            }
        });

        RadioGroup rg=(RadioGroup)findViewById(R.id.radioGroup);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                EditText ed=(EditText)findViewById(R.id.edBatch);
                switch (checkedId){
                    case R.id.radioButton:
                        ed.setText("one");
                        break;
                    case R.id.radioButton2:
                        ed.setText("two");
                        break;
                    case R.id.radioButton3:
                        ed.setText("three");
                        break;
                }
            }
        });
    }

    //点击返回上一页面
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent();
            setResult(RESULT_OK, intent);
            finish();
        }
        return super.onKeyDown(keyCode, event);

    }

}



