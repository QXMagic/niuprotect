package com.niuniu.babyprotect.ui.mine;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.view.ViewCompat;
import androidx.fragment.app.FragmentActivity;

import com.bumptech.glide.Glide;
import com.niuniu.babyprotect.BabyApplication;
import com.niuniu.babyprotect.broadcastReceiver.BroadcastManager;
import com.niuniu.babyprotect.manager.MineDevicePolicyManager;
import com.niuniu.babyprotect.manager.SharedPreManager;
import com.niuniu.babyprotect.manager.UserInfoManager;
import com.niuniu.babyprotect.manager.UserInstallWhiteAppListManager;
import com.niuniu.babyprotect.manager.UserProtectManager;
import com.niuniu.babyprotect.manager.WebSocketManager;
import com.niuniu.babyprotect.model.UserInfo;
import com.niuniu.babyprotect.network.NetTools;
import com.niuniu.babyprotect.network.ResultCallBackListener;
import com.niuniu.babyprotect.network.StudentBaseUrl;
import com.niuniu.babyprotect.stomon.StoToolManager;
import com.niuniu.babyprotect.tools.ILog;
import com.niuniu.babyprotect.tools.SharedPreUtil;
import com.niuniu.babyprotect.tools.SystemUtil;
import com.niuniu.babyprotect.tools.Tools;
import com.niuniu.babyprotect.ui.base.BaseActivity;
import com.niuniu.babyprotect.ui.login.ForgetActivity;
import com.niuniu.babyprotect.ui.login.PhoneCodeLoginActivity;
import com.niuniu.babyprotect.ui.setting.FeedBackActivity;
import com.niuniu.babyprotect.ui.setting.SchoolAdminActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import atmp.consts.Constants;
import im.niu.protect.R;
public class MineActivity extends BaseActivity {
    TextView loginoutbtn;
    TextView tvController;
    TextView txt1;
    TextView txt2;
    TextView txt3;
    ImageView userImage;
    UserInfo userInfo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        changeTitle("我的");
        showBack();
        this.userInfo = UserInfoManager.getInstance().getUserInfo(this);
        this.userImage = (ImageView) findViewById(R.id.userImage);
        this.txt1 = (TextView) findViewById(R.id.txt1);
        this.txt2 = (TextView) findViewById(R.id.txt2);
        this.txt3 = (TextView) findViewById(R.id.txt3);
        this.tvController = (TextView) findViewById(R.id.tv_parentInfo);
        TextView textView = (TextView) findViewById(R.id.tv_aboutus);
        textView.setText("关于我们        " + SystemUtil.getVersionNameStr(this) + "");
        TextView textView2 = (TextView) findViewById(R.id.loginoutbtn);
        this.loginoutbtn = textView2;
        textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                outAction();
            }
        });
        LinearLayout setpwdbtn = (LinearLayout) findViewById(R.id.setpwdbtn);
        setpwdbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplication(), ForgetActivity.class);
                startActivity(intent);
            }
        });
        LinearLayout feedbackbtn = (LinearLayout) findViewById(R.id.feedbackbtn);
        feedbackbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplication(), FeedBackActivity.class);
                startActivity(intent);
            }
        });
        LinearLayout usermsg = (LinearLayout) findViewById(R.id.usermsg);
        usermsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplication(), MineDetailInfoActivity.class);
                startActivity(intent);
            }
        });
        LinearLayout schooladminbtn = (LinearLayout) findViewById(R.id.schooladminbtn);
        schooladminbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplication(), SchoolAdminActivity.class);
                startActivity(intent);
            }
        });
        String info = (String) SharedPreUtil.getParam(BabyApplication.getInstance(), SharedPreManager.KEY_PARENT_SCHOOL, "暂无策略");
        this.tvController.setText(info);
        getUserInfo();
        reView();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        reView();
    }

    public void reView() {
        if (!Tools.objIsNullStr(this.userInfo.getImageUrl())) {
            Glide.with((FragmentActivity) this).load(this.userInfo.getImageUrl()).into(this.userImage);
        }
        long timea = System.currentTimeMillis();
        long etime = this.userInfo.getExpireTimeStamp();
        if (timea > etime) {
            this.txt3.setText("已过期");
            this.txt3.setTextColor(Constants.CATEGORY_MASK);
        } else {
            String timeStr = Tools.timeFormat(new Date(etime), "yyyy-MM-dd");
            TextView textView = this.txt3;
            textView.setText("到期时间:" + timeStr);
            this.txt3.setTextColor(ViewCompat.MEASURED_STATE_MASK);
        }
        this.txt1.setText(this.userInfo.getName());
        this.txt2.setText(this.userInfo.getGrade());
        if (!this.userInfo.isBindTeacher() && !this.userInfo.isBindParent()) {
            this.loginoutbtn.setVisibility(View.VISIBLE);
        } else {
            this.loginoutbtn.setVisibility(View.GONE);
        }
//        ISNav.getInstance().init(new ImageLoader() {
//            @Override
//            public void displayImage(Context context, String path, ImageView imageView) {
//                Glide.with(context).load(path).into(imageView);
//            }
//        });
    }

    public void getUserInfo() {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("studentId", this.userInfo.getId());
        NetTools.getInstance().getAsynHttp(this, StudentBaseUrl.members_getMemberInfo, parameters, new ResultCallBackListener() {
            @Override
            public void onResponse(JSONObject msg) {
                if (msg != null) {
                    ILog.log(msg);
                    try {
                        String data = msg.getString("data");
                        UserInfoManager.getInstance().saveUser(_context, data);
                        reView();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void exit() {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("studentId", this.userInfo.getId());
        NetTools.getInstance().getAsynHttp(this, StudentBaseUrl.user_exit, parameters, new ResultCallBackListener() {
            @Override
            public void onResponse(JSONObject msg) {
                if (msg != null) {
                    outAction();
                }
            }
        });
    }

    public void outAction() {
        UserProtectManager.getInstance().setProtect(-2);
//        PushAgent mPushAgent = PushAgent.getInstance(this);
//        mPushAgent.deleteAlias(Tools.getUsername(this), "username", new UTrack.ICallBack() {
//            @Override
//            public void onMessage(boolean isSuccess, String message) {
//            }
//        });
        StoToolManager.getInstance(this).cleanAppBlack();
        StoToolManager.getInstance(this).noAppLive(getPackageName());
        StoToolManager.getInstance(this).openGps(false);
        StoToolManager.getInstance(this).disSetTime(false);
        StoToolManager.getInstance(this).disRestoreFactory(false);
        UserInfoManager.getInstance().saveUser(this._context, null);
        Tools.saveToken(this._context, null);
        Tools.saveTeacher(this._context, null);
        Tools.saveParentHoliday(this._context, null);
        Tools.saveParentSchool(this._context, null);
        Tools.saveParentModel(this._context, null, 1);
        Tools.saveParentModel(this._context, null, 2);
        MineDevicePolicyManager.getInstance(this).onRemoveActivate();
        SharedPreUtil.clearSaveApp(this, UserInstallWhiteAppListManager.PRE_NAME);
        BroadcastManager.sendAccessibilityStop(this);
        WebSocketManager.getInstance().onDestroy();
        Intent intent = new Intent();
        intent.setClass(this._context, PhoneCodeLoginActivity.class);
        this._context.startActivity(intent);
        finish();
    }
}
