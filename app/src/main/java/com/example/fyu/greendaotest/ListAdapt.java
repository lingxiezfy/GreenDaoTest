package com.example.fyu.greendaotest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.fyu.bean.Staff;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fyu on 2017-12-13.
 */

public class ListAdapt extends BaseAdapter implements Filterable {

    private LayoutInflater inflater;
    private Context context;
    private List<Staff> staffList = null;

    public ListAdapt(Context context, List<Staff> list) {
        this.context = context;
        this.staffList = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {

        return staffList == null ? 0 : staffList.size();
    }

    @Override
    public Object getItem(int position) {
        return staffList == null ? null : staffList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setStaffList(List<Staff> staffList) {
        this.staffList = staffList;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.staff_list_item, null);
            holder.tvId = (TextView) convertView.findViewById(R.id.tv_id);
            holder.tvName = (TextView) convertView.findViewById(R.id.tv_name);
            holder.tvSex = (TextView) convertView.findViewById(R.id.tv_sex);
            holder.tvDepartment = (TextView) convertView.findViewById(R.id.tv_department);
            holder.tvSalary = (TextView) convertView.findViewById(R.id.tv_salary);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (staffList != null) {
            final Staff item = staffList.get(position);
            holder.tvId.setText(item.getId() + "");
            holder.tvName.setText(item.getName() + "");
            holder.tvSex.setText(item.getSex() + "");
            holder.tvDepartment.setText(item.getDepartment() + "");
            holder.tvSalary.setText(item.getSalary() + "");
        }

        return convertView;
    }

    public void remove(Staff staff) {
        staffList.remove(staff);
        notifyDataSetChanged();
    }

    public void clean() {
        staffList.clear();
        notifyDataSetChanged();
    }

    private class ViewHolder {
        TextView tvId, tvName, tvSex, tvDepartment, tvSalary;
    }


    private ArrayList<Staff> mOriginalValues = null;
    private ArrayFilter mFilter = null;


    @Override
    public Filter getFilter() {
        if (mFilter == null) {
            mFilter = new ArrayFilter();
        }
        return mFilter;
    }

    private class ArrayFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence prefix) {
            final FilterResults results = new FilterResults();

            if (mOriginalValues == null) {
                mOriginalValues = new ArrayList<>(staffList);
            }

            if (prefix == null || prefix.length() == 0) {
                final ArrayList<Staff> list;

                list = new ArrayList<>(mOriginalValues);

                results.values = list;
                results.count = list.size();
            } else {
                final String prefixString = prefix.toString().toLowerCase();

                final ArrayList<Staff> values;

                values = new ArrayList<>(mOriginalValues);


                final int count = values.size();
                final ArrayList<Staff> newValues = new ArrayList<>();

                for (int i = 0; i < count; i++) {
                    final Staff value = values.get(i);
                    final String valueText = value.toString().toLowerCase();

                    // First match against the whole, non-splitted value
                    if (valueText.startsWith(prefixString)) {
                        newValues.add(value);
                    } else {
                        final String[] words = valueText.split(" ");
                        for (String word : words) {
                            if (word.startsWith(prefixString)) {
                                newValues.add(value);
                                break;
                            }
                        }
                    }
                }

                results.values = newValues;
                results.count = newValues.size();
            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            //noinspection unchecked
            staffList = (List<Staff>) results.values;
            if (results.count > 0) {
                notifyDataSetChanged();
            } else {
                notifyDataSetInvalidated();
            }
        }
    }

}
