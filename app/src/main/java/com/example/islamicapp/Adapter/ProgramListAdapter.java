package com.example.islamicapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.islamicapp.CustomView.SKtetViewRobotoBold;
import com.example.islamicapp.CustomView.SKtextViewRobotoRegular;
import com.example.islamicapp.Model.ProgramListModel;
import com.example.islamicapp.Model.ScheduleListEventModel;
import com.example.islamicapp.Model.ScheduleListScheduleModel;
import com.example.islamicapp.R;
import com.google.gson.internal.bind.ReflectiveTypeAdapterFactory;

import java.util.ArrayList;
import java.util.List;

public class ProgramListAdapter extends RecyclerView.Adapter<ProgramListAdapter.MyViewHolder> {
    public static List<ScheduleListEventModel> mEventModel = new ArrayList();
    private static ArrayList<ScheduleListScheduleModel> mSchedule = new ArrayList<>();
    private Context mContext;
    private ArrayList<ProgramListModel> mProgramModel = new ArrayList<>();

    public class MyViewHolder extends RecyclerView.ViewHolder {
        /* access modifiers changed from: private */
        public SKtextViewRobotoRegular mDescription;
        private LinearLayout mProgramContainer;
        /* access modifiers changed from: private */
        public SKtetViewRobotoBold mShowTime;
        public SKtetViewRobotoBold textViewProgramName;

        public MyViewHolder(View view) {
            super(view);
            this.textViewProgramName = (SKtetViewRobotoBold) view.findViewById(R.id.programName);
            this.mDescription = (SKtextViewRobotoRegular) view.findViewById(R.id.programDescription);
            this.mShowTime = (SKtetViewRobotoBold) view.findViewById(R.id.ProgramTime);
            this.mProgramContainer = (LinearLayout) view.findViewById(R.id.containerBackGround);
        }
    }

    public ProgramListAdapter(Context context, List<ScheduleListEventModel> programListModels) {
        this.mContext = context;
        mEventModel = programListModels;
    }

    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.program_list_item, parent, false));
    }

    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.textViewProgramName.setTextColor(ContextCompat.getColor(this.mContext, R.color.white_color));
        holder.mDescription.setTextColor(ContextCompat.getColor(this.mContext, R.color.colorPrimary));
        holder.mShowTime.setTextColor(ContextCompat.getColor(this.mContext, R.color.colorAccent));
        if (mEventModel != null && mEventModel.size() > 0) {
            if (((ScheduleListEventModel) mEventModel.get(position)).getShowTitle() != null && !((ScheduleListEventModel) mEventModel.get(position)).getShowTitle().equals("")) {
                holder.textViewProgramName.setText(((ScheduleListEventModel) mEventModel.get(position)).getShowTitle());
            }
            if (((ScheduleListEventModel) mEventModel.get(position)).getShowDescription() == null || ((ScheduleListEventModel) mEventModel.get(position)).getShowDescription().equals("")) {
                holder.mDescription.setText("Description");
            } else {
                holder.mDescription.setText(((ScheduleListEventModel) mEventModel.get(position)).getShowDescription());
            }
            if (((ScheduleListEventModel) mEventModel.get(position)).getShowTimeStart() != null && !((ScheduleListEventModel) mEventModel.get(position)).getShowTimeStart().equals("")) {
                holder.mShowTime.setText(((ScheduleListEventModel) mEventModel.get(position)).getShowTimeStart());
            }
        }
    }

    public int getItemCount() {
        return mEventModel.size();
    }
}
