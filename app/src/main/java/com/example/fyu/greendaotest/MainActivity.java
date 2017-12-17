package com.example.fyu.greendaotest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.fyu.application.MyApplication;
import com.example.fyu.bean.Staff;
import com.example.fyu.dao.StaffDao;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;


public class MainActivity extends AppCompatActivity {

    private StaffDao staffDao ;

    private EditText etName;
    private EditText etSex;
    private EditText etDepartment;
    private EditText etSalary;
    private Button btnSave;
    private Intent intent;

    Staff staff = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        staffDao = MyApplication.getInstances().getDaoSession().getStaffDao();

        etName = (EditText)findViewById(R.id.et_name);
        etSex = (EditText)findViewById(R.id.et_sex);
        etDepartment = (EditText)findViewById(R.id.et_department);
        etSalary = (EditText)findViewById(R.id.et_salary);

        intent = getIntent();

        btnSave = (Button)findViewById(R.id.btn_save);


        btnSave.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(etName.getText())){
                    Toast.makeText(getBaseContext(),"请输入姓名",Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(etSex.getText())){
                    Toast.makeText(getBaseContext(),"请输入性别",Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(etDepartment.getText())){
                    Toast.makeText(getBaseContext(),"请输入部门",Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(etSalary.getText()) || etSalary.getText().toString().equals("0.0")){
                    Toast.makeText(getBaseContext(),"请输入工资",Toast.LENGTH_SHORT).show();
                }else {
                    saveStaff();
                }
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        actionType();
    }

    public void actionType(){
        if(intent.getStringExtra("type").equals("edit")){
            staff = queryById(intent.getLongExtra("id",(long)0));
        }else {
            staff = new Staff(null,"","","",0);
        }
        setData();
    }

    public void getData(){
        staff.setName(etName.getText().toString());
        staff.setSex(etSex.getText().toString());
        staff.setDepartment(etDepartment.getText().toString());
        staff.setSalary(Double.parseDouble(etSalary.getText().toString()));
    }

    public void setData(){
        etName.setText(staff.getName().toString());
        etSex.setText(staff.getSex().toString());
        etDepartment.setText(staff.getDepartment().toString());
        etSalary.setText(staff.getSalary()+"");
    }

    public void saveStaff(){
        getData();
        updata(staff);
        Intent intent = new Intent(this,ListActivity.class);
        intent.setFlags(FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    //查询
    public Staff queryById(Long id){
        Staff oldStaff = staffDao.loadByRowId(id);
        return oldStaff;
    }
    //删除

    public void delete(Staff staff){
        staffDao.delete(staff);

    }
    //更新

    public void updata(Staff staff){
        staffDao.insertOrReplace(staff);
    }


}
