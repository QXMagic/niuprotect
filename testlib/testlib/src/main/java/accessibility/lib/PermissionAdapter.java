package accessibility.lib;

import android.view.View;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import java.util.List;

import accessibility.PermissionBean;

public class PermissionAdapter extends BaseMultiItemQuickAdapter<PermissionBean, BaseViewHolder> {

    /* renamed from: com.zlfc.child.mvp.adapter.PermissionAdapter$肌緭 */
    /* loaded from: E:\apk\monitor\监控\classes5.dex */
    public class ViewOnClickListenerC3237 implements View.OnClickListener {
        public ViewOnClickListenerC3237() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            ModelManager.m22789(PermissionAdapter.this.getContext());
        }
    }

    public PermissionAdapter(@Nullable List<PermissionBean> list) {
        super(list);
//        addItemType(1, R$layout.permission_item);
//        addItemType(2, R$layout.permission_video_item);
    }

    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    /* renamed from: 旞莍癡, reason: contains not printable characters and merged with bridge method [inline-methods] */
    public void convert(BaseViewHolder baseViewHolder, PermissionBean permissionBean) {
//        if (baseViewHolder.getItemViewType() == 1) {
//            BaseViewHolder text = baseViewHolder.setText(R$id.tvPermissionName, permissionBean.getPermissionName()).setText(R$id.tvTips, permissionBean.getMeaning());
//            int i = R$id.ivHelper;
//            text.setGone(i, ObjectUtils.isNotEmpty((CharSequence) permissionBean.getDescriptor())).addOnClickListener(R$id.llRoot, i);
//            SwitchButton switchButton = (SwitchButton) baseViewHolder.getView(R$id.sbStatue);
//            if (permissionBean.getId() == -1) {
//                baseViewHolder.setGone(R$id.tvPermissionStatue, false);
//            } else {
//                int i2 = R$id.tvPermissionStatue;
//                baseViewHolder.setGone(i2, true);
//                if (permissionBean.isOpen()) {
//                    baseViewHolder.setText(i2, "已开启").setTextColor(i2, ContextCompat.getColor(this.mContext, R$color.theme_color_primary));
//                } else {
//                    baseViewHolder.setText(i2, "已关闭").setTextColor(i2, Color.parseColor("#D9D9D9"));
//                }
//            }
//            switchButton.setEnabled(true);
//            switchButton.setChecked(permissionBean.isOpen());
//            switchButton.setEnabled(false);
//            return;
//        }
//        baseViewHolder.getView(R$id.flVideo).setOnClickListener(new ViewOnClickListenerC3237());
    }
}
