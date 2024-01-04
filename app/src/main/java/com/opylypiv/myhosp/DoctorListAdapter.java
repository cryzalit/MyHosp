package com.opylypiv.myhosp;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.getstream.avatarview.AvatarView;

public class DoctorListAdapter extends BaseExpandableListAdapter {

    private final ArrayList<ArrayList<Doctor>> mGroups;
    private final Context mContext;
    ImageView imageprofile;
    StorageReference pathReference;


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
            convertView = inflater.inflate(R.layout.group_item, null);
        }


        Map<String, Drawable> dayMap = new HashMap<>();
        dayMap.put("anesthesiologist", mContext.getResources().getDrawable(R.drawable.anesthesiologist));
        dayMap.put("bacteriologist", mContext.getResources().getDrawable(R.drawable.bacteriologist));
        dayMap.put("CEO", mContext.getResources().getDrawable(R.drawable.ceo));
        dayMap.put("dermatologist", mContext.getResources().getDrawable(R.drawable.dermatologist));
        dayMap.put("dentist", mContext.getResources().getDrawable(R.drawable.dentist));
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
        dayMap.put("pediatrician", mContext.getResources().getDrawable(R.drawable.pediatrician));
        dayMap.put("physiotherapist", mContext.getResources().getDrawable(R.drawable.physiotherapist));
        dayMap.put("radiologist", mContext.getResources().getDrawable(R.drawable.radiologist));
        dayMap.put("rheumatologist", mContext.getResources().getDrawable(R.drawable.rheumatologist));
        dayMap.put("physician", mContext.getResources().getDrawable(R.drawable.physician));
        dayMap.put("surgeon", mContext.getResources().getDrawable(R.drawable.surgeon));
        dayMap.put("urologist", mContext.getResources().getDrawable(R.drawable.urologist));

        TextView textGroup = convertView.findViewById(R.id.namaorganization);
        ImageView imagegroup = convertView.findViewById(R.id.imagegroup);
        LinearLayout grouplayout = convertView.findViewById(R.id.groplayout);

        textGroup.setText(mGroups.get(groupPosition).get(0).getSpec());
        imagegroup.setImageDrawable(dayMap.get(mGroups.get(groupPosition).get(0).codespec));

        if (isExpanded) {
            grouplayout.setBackgroundResource(R.drawable.drawablelist_selected);
            textGroup.setTextColor(Color.parseColor("#048c4a"));

        } else {
            grouplayout.setBackgroundResource(R.drawable.drawableitem);
            textGroup.setTextColor(Color.parseColor("#172175"));

        }
        return convertView;
    }


    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild,
                             View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.doctor_item, null);
        }
        LinearLayout child_item = convertView.findViewById(R.id.child_item);
        TextView doctor_fullname = convertView.findViewById(R.id.author_name);
        TextView doctor_pro = convertView.findViewById(R.id.profession_doctor);
        RatingBar point = convertView.findViewById(R.id.ratingbar_doctor);
        AvatarView imageprofile = convertView.findViewById(R.id.profile_image);

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        pathReference = storageRef.child("hosp" + mGroups.get(groupPosition).get(childPosition).getIdhosp() + "/" + mGroups.get(groupPosition).get(childPosition).getId() + ".jpg");
        Picasso picassoInstance = new Picasso.Builder(mContext.getApplicationContext())
                .addRequestHandler(new FireBaseRequestHandler())
                .build();
        picassoInstance.load(pathReference + "").into(imageprofile);
        Log.d("glide", pathReference + "");
        Log.d("imageURL", mGroups.get(groupPosition).get(childPosition).getPhotoURL());

        doctor_fullname.setText(mGroups.get(groupPosition).get(childPosition).getFullname());
        doctor_pro.setText(mGroups.get(groupPosition).get(childPosition).getSpec());
        point.setMax(5);
        point.setStepSize(.1f);
        point.setRating(Float.parseFloat(mGroups.get(groupPosition).get(childPosition).getPoint() + ""));
        Log.d("point", Float.parseFloat(mGroups.get(groupPosition).get(childPosition).getPoint() + "") + "");
        Log.d("point", mGroups.get(groupPosition).get(childPosition).getPoint() + "");


        child_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle2 = new Bundle();
                bundle2.putString("idhosp", mGroups.get(groupPosition).get(childPosition).getIdhosp() + "");
                bundle2.putString("iddoctor", mGroups.get(groupPosition).get(childPosition).getId() + "");
                bundle2.putString("imagereference", pathReference + "");

                MainActivity.navController.navigate(R.id.fragmentDoctorProfile, bundle2);

            }
        });
        return convertView;

    }

    @GlideModule
    public class MyAppGlideModule extends AppGlideModule {

        @Override
        public void registerComponents(Context context, Glide glide, Registry registry) {
            // Register FirebaseImageLoader to handle StorageReference
            registry.append(StorageReference.class, InputStream.class,
                    new FirebaseImageLoader.Factory());
        }
    }


    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }




}

