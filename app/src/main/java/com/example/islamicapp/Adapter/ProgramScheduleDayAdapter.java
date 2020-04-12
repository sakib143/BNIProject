package com.example.islamicapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.example.islamicapp.CustomView.SKtetViewBold;
import com.example.islamicapp.Interface.DaySelectionCallBack;
import com.example.islamicapp.R;
import java.util.ArrayList;

public class ProgramScheduleDayAdapter extends RecyclerView.Adapter<ProgramScheduleDayAdapter.MyViewHolder> {
    private Context mContext;
    private String mDay;
    /* access modifiers changed from: private */
    public DaySelectionCallBack mDaySelectionCallBack;
    /* access modifiers changed from: private */
    public ArrayList<String> mWeakDays = new ArrayList<>();

    public class MyViewHolder extends RecyclerView.ViewHolder {
        /* access modifiers changed from: private */
        public SKtetViewBold mDayButton;
        /* access modifiers changed from: private */
        public SKtetViewBold mDaySelectedButton;

        public MyViewHolder(View view) {
            super(view);
            this.mDayButton = (SKtetViewBold) view.findViewById(R.id.buttonDay);
            this.mDaySelectedButton = (SKtetViewBold) view.findViewById(R.id.buttonDaySelected);
        }
    }

    public ProgramScheduleDayAdapter(String day, Context context, ArrayList<String> weakDays, DaySelectionCallBack daySelectionCallBack) {
        this.mContext = context;
        this.mWeakDays = weakDays;
        this.mDaySelectionCallBack = daySelectionCallBack;
        this.mDay = day;
    }

    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.single_day_item, parent, false));
    }

    public void onBindViewHolder(MyViewHolder holder, final int position) {
        switch (position) {
            case 0:
                if (!((String) this.mWeakDays.get(position)).equalsIgnoreCase(this.mDay)) {
//                    holder.mDayButton.setBackgroundResource(R.drawable.gradient_one);
                    holder.mDayButton.setText((CharSequence) this.mWeakDays.get(position));
//                    holder.mDayButton.setVisibility(0);
                    holder.mDayButton.setTextColor(ContextCompat.getColor(this.mContext, R.color.white_color));
//                    holder.mDaySelectedButton.setVisibility(8);
                    break;
                } else {
//                    holder.mDaySelectedButton.setBackgroundResource(R.drawable.gradient_red);
                    holder.mDaySelectedButton.setText((CharSequence) this.mWeakDays.get(position));
//                    holder.mDayButton.setVisibility(8);
//                    holder.mDaySelectedButton.setVisibility(0);
                    holder.mDaySelectedButton.setTextColor(ContextCompat.getColor(this.mContext, R.color.selected_color));
                    break;
                }
            case 1:
                if (!((String) this.mWeakDays.get(position)).equalsIgnoreCase(this.mDay)) {
//                    holder.mDayButton.setBackgroundResource(R.drawable.gradient_one);
                    holder.mDayButton.setText((CharSequence) this.mWeakDays.get(position));
//                    holder.mDayButton.setVisibility(0);
                    holder.mDayButton.setTextColor(ContextCompat.getColor(this.mContext, R.color.white_color));
//                    holder.mDaySelectedButton.setVisibility(8);
                    break;
                } else {
//                    holder.mDaySelectedButton.setBackgroundResource(R.drawable.gradient_red);
                    holder.mDaySelectedButton.setText((CharSequence) this.mWeakDays.get(position));
//                    holder.mDayButton.setVisibility(8);
//                    holder.mDaySelectedButton.setVisibility(0);
                    holder.mDaySelectedButton.setTextColor(ContextCompat.getColor(this.mContext, R.color.selected_color));
                    break;
                }
            case 2:
                if (!((String) this.mWeakDays.get(position)).equalsIgnoreCase(this.mDay)) {
//                    holder.mDayButton.setBackgroundResource(R.drawable.gradient_one);
                    holder.mDayButton.setText((CharSequence) this.mWeakDays.get(position));
//                    holder.mDayButton.setVisibility(0);
                    holder.mDayButton.setTextColor(ContextCompat.getColor(this.mContext, R.color.white_color));
//                    holder.mDaySelectedButton.setVisibility(8);
                    break;
                } else {
//                    holder.mDaySelectedButton.setBackgroundResource(R.drawable.gradient_red);
                    holder.mDaySelectedButton.setText((CharSequence) this.mWeakDays.get(position));
//                    holder.mDayButton.setVisibility(8);
//                    holder.mDaySelectedButton.setVisibility(0);
                    holder.mDaySelectedButton.setTextColor(ContextCompat.getColor(this.mContext, R.color.selected_color));
                    break;
                }
            case 3:
                if (!((String) this.mWeakDays.get(position)).equalsIgnoreCase(this.mDay)) {
//                    holder.mDayButton.setBackgroundResource(R.drawable.gradient_one);
                    holder.mDayButton.setText((CharSequence) this.mWeakDays.get(position));
//                    holder.mDayButton.setVisibility(0);
                    holder.mDayButton.setTextColor(ContextCompat.getColor(this.mContext, R.color.white_color));
//                    holder.mDaySelectedButton.setVisibility(8);
                    break;
                } else {
//                    holder.mDaySelectedButton.setBackgroundResource(R.drawable.gradient_red);
                    holder.mDaySelectedButton.setText((CharSequence) this.mWeakDays.get(position));
//                    holder.mDayButton.setVisibility(8);
//                    holder.mDaySelectedButton.setVisibility(0);
                    holder.mDaySelectedButton.setTextColor(ContextCompat.getColor(this.mContext, R.color.selected_color));
                    break;
                }
            case 4:
                if (!((String) this.mWeakDays.get(position)).equalsIgnoreCase(this.mDay)) {
//                    holder.mDayButton.setBackgroundResource(R.drawable.gradient_one);
                    holder.mDayButton.setText((CharSequence) this.mWeakDays.get(position));
                    holder.mDayButton.setTextColor(ContextCompat.getColor(this.mContext, R.color.white_color));
//                    holder.mDayButton.setVisibility(0);
//                    holder.mDaySelectedButton.setVisibility(8);
                    break;
                } else {
//                    holder.mDaySelectedButton.setBackgroundResource(R.drawable.gradient_red);
                    holder.mDaySelectedButton.setText((CharSequence) this.mWeakDays.get(position));
//                    holder.mDayButton.setVisibility(8);
//                    holder.mDaySelectedButton.setVisibility(0);
                    holder.mDaySelectedButton.setTextColor(ContextCompat.getColor(this.mContext, R.color.selected_color));
                    break;
                }
            case 5:
                if (!((String) this.mWeakDays.get(position)).equalsIgnoreCase(this.mDay)) {
//                    holder.mDayButton.setBackgroundResource(R.drawable.gradient_one);
                    holder.mDayButton.setText((CharSequence) this.mWeakDays.get(position));
//                    holder.mDayButton.setVisibility(0);
                    holder.mDayButton.setTextColor(ContextCompat.getColor(this.mContext, R.color.white_color));
//                    holder.mDaySelectedButton.setVisibility(8);
                    break;
                } else {
//                    holder.mDaySelectedButton.setBackgroundResource(R.drawable.gradient_red);
                    holder.mDaySelectedButton.setText((CharSequence) this.mWeakDays.get(position));
//                    holder.mDayButton.setVisibility(8);
//                    holder.mDaySelectedButton.setVisibility(0);
                    holder.mDaySelectedButton.setTextColor(ContextCompat.getColor(this.mContext, R.color.selected_color));
                    break;
                }
            case 6:
                if (!((String) this.mWeakDays.get(position)).equalsIgnoreCase(this.mDay)) {
//                    holder.mDayButton.setBackgroundResource(R.drawable.gradient_one);
                    holder.mDayButton.setText((CharSequence) this.mWeakDays.get(position));
//                    holder.mDayButton.setVisibility(0);
                    holder.mDayButton.setTextColor(ContextCompat.getColor(this.mContext, R.color.white_color));
//                    holder.mDaySelectedButton.setVisibility(8);
                    break;
                } else {
//                    holder.mDaySelectedButton.setBackgroundResource(R.drawable.gradient_red);
                    holder.mDaySelectedButton.setText((CharSequence) this.mWeakDays.get(position));
//                    holder.mDayButton.setVisibility(8);
//                    holder.mDaySelectedButton.setVisibility(0);
                    holder.mDaySelectedButton.setTextColor(ContextCompat.getColor(this.mContext, R.color.selected_color));
                    break;
                }
        }
        holder.mDayButton.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                ProgramScheduleDayAdapter.this.mDaySelectionCallBack.daySelected(((String) ProgramScheduleDayAdapter.this.mWeakDays.get(position)).toLowerCase(), position);
            }
        });
        holder.mDaySelectedButton.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                ProgramScheduleDayAdapter.this.mDaySelectionCallBack.daySelected(((String) ProgramScheduleDayAdapter.this.mWeakDays.get(position)).toLowerCase(), position);
            }
        });
    }

    public int getItemCount() {
        return this.mWeakDays.size();
    }
}
