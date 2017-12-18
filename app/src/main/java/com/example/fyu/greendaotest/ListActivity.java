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
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.fyu.application.MyApplication;
import com.example.fyu.bean.Staff;
import com.example.fyu.dao.StaffDao;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {
    private ListView lvStaffList;
    private Button btnAdd;
    private ImageButton navLeft;
    private ImageButton navRight;

    private SearchView svSearch;
    private LinearLayout llHeader;

    private ListAdapt adapt = null;
    ArrayList<Staff> list = null;

    StaffDao staffDao = null;
    int curPage = 0;
    int totalPage = 0;
    int totalCount = 0;
    int counts = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Log.i("hh", "onCreate");
        staffDao = MyApplication.getInstances().getDaoSession().getStaffDao();
        lvStaffList = (ListView) findViewById(R.id.lv_staff_list);
        lvStaffList.setTextFilterEnabled(true);

        navLeft = (ImageButton) findViewById(R.id.nav_left);
        navRight = (ImageButton) findViewById(R.id.nav_right);
        btnAdd = (Button) findViewById(R.id.btn_add);
        svSearch = (SearchView) findViewById(R.id.sv_search);
        llHeader = (LinearLayout) findViewById(R.id.ll_header);

        navRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchData(svSearch.getQuery().toString(), getAfterPage());
                Log.i("hh", "Right Click " + curPage);
            }
        });

        navLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchData(svSearch.getQuery().toString(), getBeforePage());
                Log.i("hh", "Left Click  " + curPage);
            }
        });

        svSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                curPage = 0;
                searchData(query, curPage);
                clearFocuse();
                Log.i("hh", " onQueryTextSubmit  " + query + " "+ curPage);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                curPage = 0;
                searchData(newText, curPage);
                Log.i("hh", " onQueryTextChange  " + newText + " "+ curPage);
                return false;
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListActivity.this, MainActivity.class);
                intent.putExtra("type", "add");
                startActivity(intent);
            }
        });


        lvStaffList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                new AlertDialog.Builder(ListActivity.this).setTitle("选项").setItems(
                        new CharSequence[]{"修改", "删除"}, new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which) {
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

    @Override
    protected void onStart() {
        Log.i("hh", "onStart");
        super.onStart();
    }

    @Override
    protected void onRestart() {
        Log.i("hh", "onReStart");
        super.onRestart();
    }

    @Override
    protected void onResume() {
        Log.i("hh", "onResume");

        //   queryData(curPage);
        searchData(svSearch.getQuery().toString(), curPage);
        //   filterData();
        clearFocuse();
        super.onResume();
    }

    private void filterData() {
        if (TextUtils.isEmpty(svSearch.getQuery())) {
            Log.i("hh", "filterData--noFilter");
            lvStaffList.clearTextFilter();
        } else {
            Log.i("hh", "filterData--addFilter");
            adapt.getFilter().filter(svSearch.getQuery());
        }
    }

    private void clearFocuse() {
        Log.i("hh", "clearFocuse");
        llHeader.setFocusable(true);
        llHeader.setFocusableInTouchMode(true);
        svSearch.clearFocus();
    }

    private void queryData(int offset) {
        Log.i("hh", "queryData" + offset);
        list = (ArrayList<Staff>) staffDao.queryBuilder().offset(offset * counts).limit(10).list();
        viewList();
    }

    private void searchData(String text, int offset) {
        Log.i("hh", "searchData" + text + " " + offset);

        QueryBuilder qb = staffDao.queryBuilder();
        qb.where(qb.or(StaffDao.Properties.Id.like("%" + text + "%"),
                StaffDao.Properties.Name.like("%" + text + "%"),
                StaffDao.Properties.Salary.like("%" + text + "%"),
                StaffDao.Properties.Sex.like("%" + text + "%"),
                StaffDao.Properties.Department.like("%" + text + "%"),
                StaffDao.Properties.Salary.like("%" + text + "%")));
        totalCount = qb.list().size();
        list = (ArrayList<Staff>) qb.offset(offset * counts).limit(counts).list();
        calculateTotalPage();
        viewList();
    }

    private void viewList() {
        Log.i("hh", "viewList");
        adapt = new ListAdapt(ListActivity.this, list);
        lvStaffList.setAdapter(adapt);
    }

    private void deleteStaff(int position) {
        Staff staff = (Staff) adapt.getItem(position);
        staffDao.delete(staff);
        adapt.remove(staff);
        list.remove(staff);
        if(list.size() == 0) curPage--;
        searchData(svSearch.getQuery().toString(), curPage);
        clearFocuse();
    }

    private void updataStaff(int position) {
        Staff staff = (Staff) adapt.getItem(position);
        Intent intent = new Intent(ListActivity.this, MainActivity.class);
        intent.putExtra("type", "edit");
        intent.putExtra("id", staff.getId());
        startActivity(intent);
    }

    private int getBeforePage() {
        if (curPage > 0) {
            return --curPage;
        } else{
            Toast.makeText(getBaseContext(),"没有啦",Toast.LENGTH_SHORT).show();
            return curPage;
        }

    }

    private int getAfterPage() {

        if (curPage < totalPage - 1) {
            return ++curPage;
        } else{
            Toast.makeText(getBaseContext(),"没有啦",Toast.LENGTH_SHORT).show();
            return curPage;
        }


    }

    private void calculateTotalPage() {
        if (totalCount % counts == 0) {
            totalPage = (totalCount / counts);
        } else {
            totalPage = (totalCount / counts) + 1;
        }
        Log.i("hh", totalCount + " " + totalPage);
    }
}
