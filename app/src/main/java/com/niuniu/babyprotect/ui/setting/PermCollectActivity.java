package com.niuniu.babyprotect.ui.setting;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.niuniu.babyprotect.BabyApplication;
import im.niu.protect.R;
import com.niuniu.babyprotect.accessibility.auto.bean.CheckBoxModel;
import com.niuniu.babyprotect.accessibility.auto.bean.PageInfoModel;
import com.niuniu.babyprotect.adapter.PremCollectionAdapter;
import com.niuniu.babyprotect.databinding.ActivityPermCollectBinding;
import com.niuniu.babyprotect.manager.AutoSettingManager;
import com.niuniu.babyprotect.manager.UserProtectManager;
import com.niuniu.babyprotect.model.PermisstionStepBean;
import com.niuniu.babyprotect.repository.PermisstionSettingRepository;
import com.niuniu.babyprotect.tools.ILog;
import com.niuniu.babyprotect.tools.ToastUtil;
import com.niuniu.babyprotect.tools.Tools;
import com.niuniu.babyprotect.ui.base.BaseActivity;
import com.niuniu.babyprotect.ui.main.MainActivity;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
public class PermCollectActivity extends BaseActivity {
    ActivityPermCollectBinding binding;
    int mLocation;
    PremCollectionAdapter mPremCollectionAdapter;
    List<PageInfoModel> mVivoInfos;
    List<PermisstionStepBean> permisstionStepBeans;
    RecyclerView rc;
    int upStepPermissSize;
    List<PermisstionStepBean> upStepPermisstionStepBeans;
    List<PageInfoModel> checkBoxModels = new ArrayList();
    int mPosition = -1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityPermCollectBinding inflate = ActivityPermCollectBinding.inflate(getLayoutInflater());
        this.binding = inflate;
        setContentView(inflate.getRoot());
        changeTitle("自动授权中心(自动操作)");
        Type type = new TypeToken<ArrayList<PermisstionStepBean>>() {
        }.getType();
        String permissionData = getIntent().getStringExtra("data");
        List<PermisstionStepBean> list = (List) new Gson().fromJson(permissionData, type);
        this.upStepPermisstionStepBeans = list;
        if (list != null) {
            this.upStepPermissSize = list.size();
        }
        this.permisstionStepBeans = new ArrayList();
        showBack();
        initUi();
        initAdapter();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    void initAdapter() {
        this.mPremCollectionAdapter = new PremCollectionAdapter(this, this.checkBoxModels);
        this.rc.setLayoutManager(new LinearLayoutManager(this));
        this.rc.setAdapter(this.mPremCollectionAdapter);
        this.mPremCollectionAdapter.setOnclickListener(new PremCollectionAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, int location) {
                mPosition = position;
                mLocation = location;
                AutoSettingManager.getInstance().setAutoSettingFinish(PermCollectActivity.this, 1);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
        if (!AutoSettingManager.getInstance().isNeedAutoSetting() && this.mPosition != -1) {
            showAutoSetting();
        }
        checkAllQxSetted();
    }

    private void loadData() {
        this.mVivoInfos = BabyApplication.getInstance().getAutoSettingSteps();
        this.checkBoxModels.clear();
        if (this.mVivoInfos != null) {
            for (int i = 0; i < this.mVivoInfos.size(); i++) {
                PageInfoModel model = this.mVivoInfos.get(i);
                if (model.getParentPage() == 0) {
                    this.checkBoxModels.add(model);
                }
            }
            this.mPremCollectionAdapter.notifyDataSetChanged();
        }
    }

    private void initUi() {
        this.binding.btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean allClick = true;
                if (checkBoxModels != null) {
                    int size = checkBoxModels.size();
                    for (int i = 0; i < size; i++) {
                        PageInfoModel mPageInfoModel = checkBoxModels.get(i);
                        List<CheckBoxModel> checkBoxModels = mPageInfoModel.getCheckBoxModels();
                        int j = 0;
                        while (true) {
                            if (j < checkBoxModels.size()) {
                                if (checkBoxModels.get(j).getClickedStatus() == 1) {
                                    j++;
                                } else {
                                    allClick = false;
                                    break;
                                }
                            } else {
                                break;
                            }
                        }
                    }
                    if (!allClick) {
                        ToastUtil.show("还有未开启权限，请开启");
                        return;
                    }
                    UserProtectManager.getInstance().setProtect(1);
                    upStepPermisstionStepBeans.addAll(permisstionStepBeans);
                    PermisstionSettingRepository permisstionSettingRepository = PermisstionSettingRepository.getInstance();
                    PermCollectActivity permCollectActivity = PermCollectActivity.this;
                    permisstionSettingRepository.setPermissionStep(permCollectActivity, permCollectActivity.upStepPermisstionStepBeans, null);
                    PermisstionSettingRepository.getInstance().setPermissionResult(PermCollectActivity.this, null);
                    Tools.saveQxSet(_context, 1);
                    AutoSettingManager.getInstance().setAutoSettingFinish(PermCollectActivity.this, 3);
                    Intent intent = new Intent(PermCollectActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
        this.rc = this.binding.rcContainer;
        this.binding.tvPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("android.intent.action.DIAL");
                Uri data = Uri.parse("tel:400-811-3985");
                intent.setData(data);
                startActivity(intent);
            }
        });
    }

    private void checkAllQxSetted() {
        boolean allClick = true;
        this.permisstionStepBeans.clear();
        List<PageInfoModel> list = this.checkBoxModels;
        if (list != null) {
            int size = list.size();
            for (int i = 0; i < size; i++) {
                PageInfoModel mPageInfoModel = this.checkBoxModels.get(i);
                List<CheckBoxModel> checkBoxModels = mPageInfoModel.getCheckBoxModels();
                int j = 0;
                while (true) {
                    if (j < checkBoxModels.size()) {
                        PermisstionStepBean permisstionStepBean = new PermisstionStepBean();
                        permisstionStepBean.setStepNo(this.upStepPermissSize + 1 + i);
                        permisstionStepBean.setPermissionName(checkBoxModels.get(j).getClickCheckBoxItemText());
                        if (checkBoxModels.get(j).getClickedStatus() != 1) {
                            allClick = false;
                            permisstionStepBean.setStatus(-1);
                            break;
                        }
                        permisstionStepBean.setStatus(1);
                        this.permisstionStepBeans.add(permisstionStepBean);
                        j++;
                    }
                }
            }
            if (allClick) {
                this.binding.btnStart.setClickable(true);
                this.binding.btnStart.setBackgroundResource(R.drawable.selector_btn);
            }
        }
    }

    private void showAutoSetting() {
        AlertDialog dialog = new AlertDialog.Builder(this).setTitle("提示").setMessage("是否按要求设定").setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                checkClickStatus();
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        }).create();
        dialog.show();
    }

    public void checkClickStatus() {
        ILog.d("checkClickStatus", "-mPosition-" + this.mPosition);
        ILog.d("checkClickStatus", "-mLocation-" + this.mLocation);
        int i = this.mPosition;
        if (i != -1) {
            this.checkBoxModels.get(i).getCheckBoxModels().get(this.mLocation).setClickedStatus(1);
            this.mPremCollectionAdapter.notifyDataSetChanged();
        }
        checkAllQxSetted();
    }
}
