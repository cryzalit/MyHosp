package com.opylypiv.myhosp;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
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

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.group_view, null);
        }


        Map<String, Drawable> dayMap = new HashMap<>();
        dayMap.put("anesthesiologist", mContext.getResources().getDrawable(R.drawable.anesthesiologist));
        dayMap.put("bacteriologist", mContext.getResources().getDrawable(R.drawable.bacteriologist));
        dayMap.put("cardiologist", mContext.getResources().getDrawable(R.drawable.cardiologist));
        dayMap.put("CEO", mContext.getResources().getDrawable(R.drawable.ceo));
        dayMap.put("dermatologist", mContext.getResources().getDrawable(R.drawable.dermatologist));
        dayMap.put("dermatovenereologist", mContext.getResources().getDrawable(R.drawable.dermatovenereologist));
        dayMap.put("endocrinologist", mContext.getResources().getDrawable(R.drawable.endocrinologist));
        dayMap.put("endoscopist", mContext.getResources().getDrawable(R.drawable.endoscopist));
        dayMap.put("gynecologist", mContext.getResources().getDrawable(R.drawable.ginekologist));
        dayMap.put("infectious", mContext.getResources().getDrawable(R.drawable.infectious));
        dayMap.put("medical_director", mContext.getResources().getDrawable(R.drawable.ceo));
        dayMap.put("narcologist", mContext.getResources().getDrawable(R.drawable.narcologist));
        dayMap.put("nephrologist", mContext.getResources().getDrawable(R.drawable.nephrologist));
        dayMap.put("neurologist", mContext.getResources().getDrawable(R.drawable.neurologist));
        dayMap.put("ophthalmologist", mContext.getResources().getDrawable(R.drawable.ophthalmologist));
        dayMap.put("otolaryngologist", mContext.getResources().getDrawable(R.drawable.otolaryngologist));
        dayMap.put("physiotherapist", mContext.getResources().getDrawable(R.drawable.physiotherapist));
        dayMap.put("radiologist", mContext.getResources().getDrawable(R.drawable.radiologist));
        dayMap.put("rheumatologist", mContext.getResources().getDrawable(R.drawable.rheumatologist));
        dayMap.put("surgeon", mContext.getResources().getDrawable(R.drawable.surgeon));
        dayMap.put("urologist", mContext.getResources().getDrawable(R.drawable.urologist));

        TextView textGroup = convertView.findViewById(R.id.textGroup);
        ImageView imagegroup = convertView.findViewById(R.id.imagegroup);
        textGroup.setText(mGroups.get(groupPosition).get(0).getSpec());
        imagegroup.setImageDrawable(dayMap.get(mGroups.get(groupPosition).get(0).codespec));

        return convertView;


    }


    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild,
                             View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.child_view, null);
        }
        LinearLayout child_item = convertView.findViewById(R.id.child_item);

        TextView doctor_fullname = convertView.findViewById(R.id.name_doctor);
        TextView doctor_pro = convertView.findViewById(R.id.profession_doctor);
        RatingBar point = convertView.findViewById(R.id.ratingbar_doctor);

        doctor_fullname.setText(mGroups.get(groupPosition).get(childPosition).getFullname());
        doctor_pro.setText(mGroups.get(groupPosition).get(childPosition).getSpec());
        point.setMax(5);
        point.setStepSize(.1f);
        point.setRating((mGroups.get(groupPosition).get(childPosition).getPoint()));

        Log.d("point", mGroups.get(groupPosition).get(childPosition).getPoint() + "");

        child_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext, DoctorProfile.class);
                intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("id", mGroups.get(groupPosition).get(childPosition).getId());
                intent.putExtra("idhosp", mGroups.get(groupPosition).get(childPosition).getIdhosp());
                intent.putExtra("name", mGroups.get(groupPosition).get(childPosition).getFullname());
                intent.putExtra("spec", mGroups.get(groupPosition).get(childPosition).getSpec());
                intent.putExtra("photo", mGroups.get(groupPosition).get(childPosition).getPhotoURL());
                intent.putExtra("point", mGroups.get(groupPosition).get(childPosition).getPoint() + "");

                mContext.startActivity(intent);
            }
        });
        return convertView;

    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
