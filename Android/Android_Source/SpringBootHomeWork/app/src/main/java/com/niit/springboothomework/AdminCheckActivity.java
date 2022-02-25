package com.niit.springboothomework;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.google.gson.Gson;
import com.niit.pojo.Leaveisagreed;
import com.niit.pojo.Student;
import com.niit.pojo.StudentLeaveDTO;
import com.niit.pojo.util.requestUrl;
import com.niit.util.OkHttpUtil;
import com.niit.util.ServerResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AdminCheckActivity extends AppCompatActivity {
    private static final String TAG ="AdminCheckActivity" ;
    private ServerResponse<List<StudentLeaveDTO>> serverResponse;
    private Gson gson = new Gson();
   private OkHttpUtil okHttpUtil=new OkHttpUtil();
    private  String url2=requestUrl.AdminLookStudentLeave.getAll();
    private static final int POST = 2;
    private static final int GET = 1;

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            switch(msg.what){
                case POST:
                    ServerResponse<Object> serverResponse= gson.fromJson((String)msg.obj, ServerResponse.class);
                    Toast.makeText(AdminCheckActivity.this,serverResponse.getMsg(),Toast.LENGTH_LONG).show();
                    okHttpUtil.setUrl(url2);
                    okHttpUtil.getDataFromGet(handler);
                    break;

                case GET:
                    serverResponse= gson.fromJson((String)msg.obj, ServerResponse.class);
                    if (serverResponse.getStatus()==204){
                        Toast.makeText(AdminCheckActivity.this,serverResponse.getMsg(),Toast.LENGTH_LONG).show();
                        okHttpUtil.setUrl(url2);
                        okHttpUtil.getDataFromGet(handler);
                    }else{
                        reload((String)msg.obj);
                       }

                    break;
            }
        }


    };





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_check);
        Intent intent = getIntent();
        String studentLeaveDTOListString = intent.getStringExtra("studentLeaveDTOList");
        reload(studentLeaveDTOListString);

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


    private void reload(String studentLeaveDTOListString){
        if(studentLeaveDTOListString==null) return;
        serverResponse = gson.fromJson(studentLeaveDTOListString, ServerResponse.class);
        List<StudentLeaveDTO> studentLeaveDTOList = serverResponse.getData();
        String studentLeaveDTOList2 = gson.toJson(studentLeaveDTOList);
        StudentLeaveDTO[] studentLeaveDTOs = gson.fromJson(studentLeaveDTOList2, StudentLeaveDTO[].class);
        ListView listView = (ListView) this.findViewById(R.id.studentLeave_item);

        List<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
        for (StudentLeaveDTO studentLeaveDTO : studentLeaveDTOs) {
            HashMap<String, Object> item = new HashMap<String, Object>();
            item.put("name", studentLeaveDTO.getName());
            item.put("others", "Batch: " + studentLeaveDTO.getBatch() + "\nAge:" + studentLeaveDTO.getAge() + "\nLeaveDate:" + studentLeaveDTO.getLeaveDate() + "\nReason:" + studentLeaveDTO.getReason());
            if (studentLeaveDTO.getIsagreed()==0)
            {
                item.put("isagreed","Not approve yet");
            }else if (studentLeaveDTO.getIsagreed()==1){
                item.put("isagreed","Approved");
            }else{
                item.put("isagreed","Rejected");
            }
            item.put("leavestr", gson.toJson(studentLeaveDTO));
            data.add(item);
        }

        SimpleAdapter adapter = new SimpleAdapter(this, data, R.layout.student_leave_item,
                new String[]{"name", "others","isagreed"}, new int[]{R.id.tv_name, R.id.tv_stuLeavebatchageleaveDatereason,R.id.isagreed});

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ListView listView = (ListView) parent;
                HashMap<String, Object> data = (HashMap<String, Object>) listView.getItemAtPosition(position);
                String leavestr=(String)data.get("leavestr");
                StudentLeaveDTO studentLeaveDTO =gson.fromJson(leavestr,StudentLeaveDTO.class);

                //提示框
                AlertDialog.Builder al=new AlertDialog.Builder(AdminCheckActivity.this);
                al.setTitle("Attention");
                al.setMessage("Do you approve the leave application?");
                al.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Leaveisagreed leaveisagreed=new Leaveisagreed();
                        leaveisagreed.setIsagreed(1);
                        leaveisagreed.setLeaveid(studentLeaveDTO.getLeaveid());
                        okHttpUtil.setUrl(requestUrl.AdminAgreeLeave.getAll());
                        okHttpUtil.setObj(leaveisagreed);
                        okHttpUtil.getDataFromPost(handler);
                    }
                });
                al.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Leaveisagreed leaveisagreed=new Leaveisagreed();
                        leaveisagreed.setIsagreed(-1);
                        leaveisagreed.setLeaveid(studentLeaveDTO.getLeaveid());
                        okHttpUtil.setUrl(requestUrl.AdminAgreeLeave.getAll());
                        okHttpUtil.setObj(leaveisagreed);
                        okHttpUtil.getDataFromPost(handler);

                    }
                });
                al.show();

            }







        });







    }


}