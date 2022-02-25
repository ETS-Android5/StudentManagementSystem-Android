package com.niit.springboothomework;

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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.niit.pojo.Student;
import com.niit.pojo.util.requestUrl;
import com.niit.util.OkHttpUtil;
import com.niit.util.ServerResponse;

public class AdminEditActivity extends AppCompatActivity {
    private Gson gson = new Gson();
    private OkHttpUtil okHttpUtil=new OkHttpUtil();
    private  String url= requestUrl.AdminModifyStuInfo.getAll();
    private  String url2=requestUrl.AdminDeleteStudent.getAll();
    private static final int POST = 2;
    private static final int GET = 1;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            switch(msg.what){
                case POST:
                    ServerResponse<Object> serverResponse= gson.fromJson((String)msg.obj, ServerResponse.class);
                    Toast.makeText(AdminEditActivity.this,serverResponse.getMsg(),Toast.LENGTH_LONG).show();
                case GET:
                     serverResponse= gson.fromJson((String)msg.obj, ServerResponse.class);
                    Toast.makeText(AdminEditActivity.this,serverResponse.getMsg(),Toast.LENGTH_LONG).show();
                    break;
            }
        }


    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_edit);
        Button bt = (Button)findViewById(R.id.btAsk);
        EditText name=(EditText)findViewById(R.id.edName);
        TextView stuid=(TextView)findViewById(R.id.txStudentsID);
        EditText batch=(EditText)findViewById(R.id.edBatch);
        EditText age=(EditText)findViewById(R.id.edAge);
        EditText address=(EditText)findViewById(R.id.edAddress);
        EditText contactnumber=(EditText)findViewById(R.id.edContactNumber);
        EditText email=(EditText)findViewById(R.id.edEmail);
        Button btDelete = findViewById(R.id.btDelete);



        Intent intent=getIntent();
        String studentstr=intent.getStringExtra("studentstr");
        Student student=gson.fromJson(studentstr, Student.class);


        name.setText(student.getName());
        stuid.setText(student.getStuid()+"");


        batch.setText(student.getBatch());


        switch (student.getBatch()){
            case "one":
                RadioButton radioButton=(RadioButton)findViewById(R.id.radioButton);
                radioButton.toggle();
                break;
            case "two":
                RadioButton radioButton2=(RadioButton)findViewById(R.id.radioButton2);
                radioButton2.toggle();
                break;
            case "three":
                RadioButton radioButton3=(RadioButton)findViewById(R.id.radioButton3);
                radioButton3.toggle();
                break;
        }


        age.setText(student.getAge());
        address.setText(student.getAddress());
        contactnumber.setText(student.getContactnumber());
        email.setText(student.getEmail());


        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //提示框
                AlertDialog.Builder al=new AlertDialog.Builder(AdminEditActivity.this);
                al.setTitle("Attention");
                al.setMessage("Do you confirm the modification?");
                al.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        student.setName(name.getText()+"");
                        student.setAddress(address.getText()+"");
                        student.setAge(age.getText()+"");
                        student.setBatch(batch.getText()+"");
                        student.setContactnumber(batch.getText()+"");
                        student.setEmail(email.getText()+"");
                        String str= gson.toJson(student);
                        okHttpUtil.setUrl(url);
                        okHttpUtil.setObj(student);
                        okHttpUtil.getDataFromPost(handler);
                    }
                });
                al.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                al.show();

            }
        });
        btDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //提示框
                AlertDialog.Builder al=new AlertDialog.Builder(AdminEditActivity.this);
                al.setTitle("Attention");
                al.setMessage("Do you delete the student?");
                al.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Integer id = Integer.parseInt(stuid.getText()+"");
                        okHttpUtil.setUrl(url2+id);
                        okHttpUtil.getDataFromGet(handler);
                        Intent intent = new Intent();
                        setResult(RESULT_OK, intent);
                        finish();

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