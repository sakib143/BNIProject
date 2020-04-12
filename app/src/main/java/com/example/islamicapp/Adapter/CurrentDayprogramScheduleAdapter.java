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
import com.example.islamicapp.R;
import com.google.gson.internal.bind.ReflectiveTypeAdapterFactory;

import java.util.ArrayList;
import java.util.List;

public class CurrentDayprogramScheduleAdapter extends RecyclerView.Adapter<CurrentDayprogramScheduleAdapter.MyViewHolder> {
    public static List<ScheduleListEventModel> mEventModel = new ArrayList();
    private Context mContext;
    private ArrayList<ProgramListModel> mProgramModel = new ArrayList<>();

    public class MyViewHolder extends RecyclerView.ViewHolder {
        /* access modifiers changed from: private */
        public SKtextViewRobotoRegular mDescriptiontextView;
        private LinearLayout mProgramContainer;
        /* access modifiers changed from: private */
        public SKtetViewRobotoBold mProgramTime;
        public SKtetViewRobotoBold textViewProgramName;

        public MyViewHolder(View view) {
            super(view);
            this.textViewProgramName = (SKtetViewRobotoBold) view.findViewById(R.id.programName);
            this.mDescriptiontextView = (SKtextViewRobotoRegular) view.findViewById(R.id.programDescription);
            this.mProgramTime = (SKtetViewRobotoBold) view.findViewById(R.id.ProgramTime);
            this.mProgramContainer = (LinearLayout) view.findViewById(R.id.containerBackGround);
        }
    }

    public CurrentDayprogramScheduleAdapter(Context context, List<ScheduleListEventModel> eventModel) {
        this.mContext = context;
        mEventModel = eventModel;
    }

    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.current_day_program_schedule_item, parent, false);
        itemView.setMinimumHeight(parent.getMeasuredHeight() / 4);
        return new MyViewHolder(itemView);
    }

    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.mDescriptiontextView.setTextColor(ContextCompat.getColor(this.mContext, R.color.colorAccent));
        holder.mProgramTime.setTextColor(ContextCompat.getColor(this.mContext, R.color.colorPrimary));
        if (mEventModel != null && mEventModel.size() > 0) {
            if (((ScheduleListEventModel) mEventModel.get(position)).getShowTitle() != null && !((ScheduleListEventModel) mEventModel.get(position)).getShowTitle().equals("")) {
                holder.textViewProgramName.setText(((ScheduleListEventModel) mEventModel.get(position)).getShowTitle());
            }
            if (((ScheduleListEventModel) mEventModel.get(position)).getShowDescription() == null || ((ScheduleListEventModel) mEventModel.get(position)).getShowDescription().equals("")) {
                holder.mDescriptiontextView.setText("Description");
            } else {
                holder.mDescriptiontextView.setText(((ScheduleListEventModel) mEventModel.get(position)).getShowDescription());
            }
            if (((ScheduleListEventModel) mEventModel.get(position)).getShowTimeStart() != null && !((ScheduleListEventModel) mEventModel.get(position)).getShowTimeStart().equals("")) {
                holder.mProgramTime.setText(((ScheduleListEventModel) mEventModel.get(position)).getShowTimeStart());
            }
            if (((ScheduleListEventModel) mEventModel.get(position)).isSelected()) {
                holder.itemView.setBackgroundColor(ContextCompat.getColor(this.mContext, R.color.selected_color));
            }
        }
    }

    public int getItemViewType(int position) {
        return position;
    }

    public int getItemCount() {
        return mEventModel.size();
    }
}
