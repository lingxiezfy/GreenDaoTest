package com.example.fyu.greendaotest;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;

import com.example.fyu.application.MyApplication;
import com.example.fyu.bean.Staff;
import com.example.fyu.dao.StaffDao;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {
    private ListView lvStaffList;
    private Button btnAdd;
    private SearchView svSearch;
    private String searchValue = null;
    private LinearLayout llHeader;

    private ListAdapt adapt = null;
    ArrayList<Staff> list  = null;

    StaffDao staffDao = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Log.i("hh","onCreate");
        staffDao = MyApplication.getInstances().getDaoSession().getStaffDao();
        lvStaffList = (ListView) findViewById(R.id.lv_staff_list);
        lvStaffList.setTextFilterEnabled(true);

        btnAdd = (Button)findViewById(R.id.btn_add);
        svSearch = (SearchView)findViewById(R.id.sv_search);
        llHeader = (LinearLayout)findViewById(R.id.ll_header);


        svSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (TextUtils.isEmpty(newText)){
                    lvStaffList.clearTextFilter();
                    queryData();
                    viewList();
                }else{
                    adapt.getFilter().filter(newText);
                }
                return false;
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListActivity.this , MainActivity.class);
                intent.putExtra("type","add");
                startActivity(intent);
            }
        });



        lvStaffList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                new AlertDialog.Builder(ListActivity.this).setTitle("选项").setItems(
                        new CharSequence[]{"修改","删除"} , new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch(which){
                                    case 0:
                                        updataStaff(position);
                                        break;
                                    case 1:
                                        deleteStaff(position);
                                        break;
                                    default:
                                        break;
                                }
                            }
                        }).setNegativeButton("取消", null).show();
                return true;
            }
        });
    }

//    private static final int MSG_WHAT_LLHEADER_RESET = 0;
//
//    private Handler hander = new Handler(){
//        public void handleMessage(android.os.Message msg) {
//            switch (msg.what) {
//                case MSG_WHAT_LLHEADER_RESET:
//                    Log.i("ggg","刷新了没有？？？");
//                    llHeader.invalidate();
//                    break;
//
//                default:
//                    break;
//            }
//
//        };
//    };


    @Override
    protected void onStart() {
        Log.i("hh","onStart");
        super.onStart();
    }

    @Override
    protected void onRestart() {
        Log.i("hh","onReStart");
        super.onRestart();
    }

    @Override
    protected void onResume() {
        Log.i("hh","onResume");

        queryData();
        viewList();
        filterData();
        clearFocuse();
        super.onResume();
    }

    private void filterData(){
        if (TextUtils.isEmpty(svSearch.getQuery())){
            Log.i("hh","filterData--noFilter");
            lvStaffList.clearTextFilter();
        }else{
            Log.i("hh","filterData--addFilter");
            adapt.getFilter().filter(svSearch.getQuery());
        }
    }

    private void clearFocuse(){
        Log.i("hh","clearFocuse");
        llHeader.setFocusable(true);
        llHeader.setFocusableInTouchMode(true);
        svSearch.clearFocus();
    }

    private void queryData(){
        Log.i("hh","queryData");
        list = (ArrayList<Staff>) staffDao.queryBuilder().list();
    }

    private void searchData(String text){

    }
    private void viewList(){
        Log.i("hh","viewList");
        adapt = new ListAdapt(ListActivity.this,list);
        lvStaffList.setAdapter(adapt);
    }
    private void deleteStaff(int position){
        Staff staff =(Staff) adapt.getItem(position);
        adapt.remove(staff);
        staffDao.delete(staff);
    }
    private void updataStaff(int position){
        Staff staff =(Staff) adapt.getItem(position);
        Intent intent = new Intent(ListActivity.this,MainActivity.class);
        intent.putExtra("type","edit");
        intent.putExtra("id",staff.getId());
        startActivity(intent);
    }
}
