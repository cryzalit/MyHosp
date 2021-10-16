package com.opylypiv.myhosp;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DoctorListAdapter extends BaseExpandableListAdapter {

    private final ArrayList<ArrayList<Doctor>> mGroups;
    private final Context mContext;

    public DoctorListAdapter(Context context, ArrayList<ArrayList<Doctor>> groups) {
        mContext = context;
        mGroups = groups;
    }

    @Override
    public int getGroupCount() {
        return mGroups.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mGroups.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mGroups.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mGroups.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView,
                             ViewGroup parent) {

        Map<String, Integer> dayMap;

        dayMap = new HashMap<String, Integer>();
        dayMap.put("day1", R.drawable.day1);
        dayMap.put("day2", R.drawable.day2);
        dayMap.put("day3", R.drawable.day3);
        dayMap.put("day4", R.drawable.day4);
        dayMap.put("day5", R.drawable.day5);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.group_view, null);
        }

        if (isExpanded) {
            //Изменяем что-нибудь, если текущая Group раскрыта
        } else {

        }
        TextView textGroup = (TextView) convertView.findViewById(R.id.textGroup);
        if (mGroups.get(groupPosition).isEmpty()) {
        } else {
            textGroup.setText(mGroups.get(groupPosition).get(0).getSpec());

        }


        return convertView;

    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild,
                             View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.child_view, null);
        }
        LinearLayout child_item = (LinearLayout) convertView.findViewById(R.id.child_item);
        child_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(mContext, DoctorProfile.class);
                intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("id",mGroups.get(groupPosition).get(childPosition).getId());
                intent.putExtra("name",mGroups.get(groupPosition).get(childPosition).getFullname());
                intent.putExtra("spec",mGroups.get(groupPosition).get(childPosition).getSpec());
                intent.putExtra("point",mGroups.get(groupPosition).get(childPosition).getPoint()+"");
                intent.putExtra("photo",mGroups.get(groupPosition).get(childPosition).getPhoto());

                mContext.startActivity(intent);
            }
        });

        TextView doctor_fullname = (TextView) convertView.findViewById(R.id.name_doctor);
        TextView doctor_pro = (TextView) convertView.findViewById(R.id.profession_doctor);
        RatingBar point = (RatingBar) convertView.findViewById(R.id.ratingbar_doctor);

        doctor_fullname.setText(mGroups.get(groupPosition).get(childPosition).getFullname());
        doctor_pro.setText(mGroups.get(groupPosition).get(childPosition).getSpec());
        point.setMax(5);
        point.setStepSize(.5f);
        point.setRating(Float.parseFloat(mGroups.get(groupPosition).get(childPosition).getPoint()+""));
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
