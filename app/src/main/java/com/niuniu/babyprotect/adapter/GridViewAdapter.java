package com.niuniu.babyprotect.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import im.niu.protect.R;
import com.niuniu.babyprotect.model.AppInfo;
import com.niuniu.babyprotect.tools.Tools;
import com.niuniu.babyprotect.ui.main.DesktopActivity;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
public class GridViewAdapter extends BaseAdapter implements AdapterView.OnItemClickListener {
    private Context _context;
    private List<AppInfo> dataList = new ArrayList();

    public GridViewAdapter(List<AppInfo> datas, int page, Context context) {
        this._context = context;
        int start = DesktopActivity.item_grid_num * page;
        int end = DesktopActivity.item_grid_num + start;
        while (start < datas.size() && start < end) {
            this.dataList.add(datas.get(start));
            start++;
        }
    }

    @Override
    public int getCount() {
        return this.dataList.size();
    }

    @Override
    public Object getItem(int i) {
        return this.dataList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View itemView, ViewGroup viewGroup) {
        ViewHolder mHolder;
        if (itemView == null) {
            mHolder = new ViewHolder();
            itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_gridview, viewGroup, false);
            mHolder.iv_img = (ImageView) itemView.findViewById(R.id.iv);
            mHolder.tv_text = (TextView) itemView.findViewById(R.id.tv);
            itemView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) itemView.getTag();
        }
        AppInfo appInfo = this.dataList.get(i);
        mHolder.iv_img.setImageDrawable(appInfo.getIco());
        mHolder.tv_text.setText(appInfo.getName());
        return itemView;
    }

    private class ViewHolder {
        private ImageView iv_img;
        private TextView tv_text;

        private ViewHolder() {
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int index, long l) {
        String pagename = "";
        AppInfo appInfo = this.dataList.get(index);
        JSONArray array = Tools.getBlackApp(this._context);
        for (int i = 0; i < array.length(); i++) {
            try {
                pagename = array.getString(i);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (!appInfo.getPackageName().equals(pagename)) {
                continue;
            } else {
                AlertDialog.Builder title = new AlertDialog.Builder(this._context).setTitle("提示");
                title.setMessage(appInfo.getName() + " 已经被禁用").setPositiveButton("确定", (DialogInterface.OnClickListener) null).show();
                return;
            }
        }
        Intent intent = this._context.getPackageManager().getLaunchIntentForPackage(appInfo.getPackageName());
        if (intent != null) {
            intent.putExtra("type", "110");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            this._context.startActivity(intent);
        }
    }
}
