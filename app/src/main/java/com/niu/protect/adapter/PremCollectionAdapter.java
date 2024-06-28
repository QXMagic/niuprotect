package com.niu.protect.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.niu.protect.R;
import com.niu.protect.accessibility.auto.app.OpenSettingApp;
import com.niu.protect.accessibility.auto.bean.CheckBoxModel;
import com.niu.protect.accessibility.auto.bean.PageInfoModel;
import com.niu.protect.databinding.RcPermCollectionBinding;

import java.util.List;

public class PremCollectionAdapter extends RecyclerView.Adapter<PremCollectionAdapter.ViewHolder> {
    RcPermCollectionBinding binding;
    private List<PageInfoModel> datas;
    Activity mContext;
    OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(int i, int i2);
    }

    public void setOnclickListener(OnItemClickListener onclickListener) {
        this.mOnItemClickListener = onclickListener;
    }

    public PremCollectionAdapter(Activity context, List<PageInfoModel> data) {
        this.datas = data;
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RcPermCollectionBinding inflate = RcPermCollectionBinding.inflate(LayoutInflater.from(this.mContext), parent, false);
        this.binding = inflate;
        return new ViewHolder(inflate.getRoot());
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        position = holder.getBindingAdapterPosition();
        holder.tv_perm_title.setText(this.datas.get(position).getMainPageInfo());
        List<CheckBoxModel> checkBoxs = this.datas.get(position).getCheckBoxModels();
        if (checkBoxs != null && checkBoxs.size() > 0) {
            holder.permCheckBoxContainer.removeAllViews();
            for (int i = 0; i < checkBoxs.size(); i++) {
                final int location = i;
                View view = LayoutInflater.from(this.mContext).inflate(R.layout.layout_perm_checkbox, (ViewGroup) null);
                TextView tvPermTitle = (TextView) view.findViewById(R.id.tv_per_name);
                tvPermTitle.setText(checkBoxs.get(i).getClickCheckBoxItemText());
                TextView tvPermStatus = (TextView) view.findViewById(R.id.tv_per_status);
                if (checkBoxs.get(i).getClickedStatus() == 1) {
                    tvPermStatus.setText("已授权");
                    tvPermStatus.setTextColor(Color.GREEN);
                } else {
                    tvPermStatus.setText("未授权,点击可设置");
                    tvPermStatus.setTextColor(Color.GRAY);
                }
                view.setPadding(0, 10, 0, 0);
                holder.permCheckBoxContainer.addView(view);
                int finalPosition = position;
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        OpenSettingApp.gotoSetting(mContext, ((PageInfoModel) datas.get(finalPosition)).getGotoSettingType());
                        if (mOnItemClickListener != null) {
                            mOnItemClickListener.onItemClick(finalPosition, location);
                        }
                    }
                });
            }
        }
        int finalPosition1 = position;
        holder.tv_perm_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view2) {
                OpenSettingApp.gotoSetting(mContext, ((PageInfoModel) datas.get(finalPosition1)).getGotoSettingType());
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.datas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout permCheckBoxContainer;
        private TextView tv_perm_title;

        public ViewHolder(View itemView) {
            super(itemView);
            this.tv_perm_title = binding.tvPermTitle;
            this.permCheckBoxContainer = binding.llCheckBoxContainer;
        }
    }
}
