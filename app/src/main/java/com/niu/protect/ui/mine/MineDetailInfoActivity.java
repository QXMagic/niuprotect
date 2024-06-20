package com.niu.protect.ui.mine;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.view.ViewCompat;
import androidx.fragment.app.FragmentActivity;

import com.baidu.platform.comapi.map.MapBundleKey;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.niu.protect.manager.UserInfoManager;
import com.niu.protect.manager.UserInstallWhiteAppListManager;
import com.niu.protect.model.JsonBean;
import com.niu.protect.model.UserInfo;
import com.niu.protect.network.NetTools;
import com.niu.protect.network.ResultCallBackListener;
import com.niu.protect.network.StudentBaseUrl;
import com.niu.protect.stomon.StoToolManager;
import com.niu.protect.tools.ILog;
import com.niu.protect.tools.Tools;
import com.niu.protect.tools.data.GetJsonDataUtil;
import com.niu.protect.ui.base.BaseActivity;
import com.niu.protect.ui.login.LoginActivity;
import com.niu.protect.ui.setting.FeedBackActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import atmp.consts.Constants;
import com.niu.protect.R;
public class MineDetailInfoActivity extends BaseActivity {
    TextView addrTxt;
    TextView classTxt;
    LinearLayout loginOutbtn;
    TextView nickNameTxt;
    TextView realNameTxt;
    TextView sexTxt;
    ImageView userImage;
    UserInfo userInfo;
    LinearLayout view0;
    LinearLayout view1;
    LinearLayout view2;
    LinearLayout view3;
    LinearLayout view4;
    LinearLayout view5;
    LinearLayout view6;
    TextView vipTxt;
    List<String> classList = new ArrayList();
    private List<JsonBean> options1Items = new ArrayList();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();
    int REQUEST_CAMERA_CODE = 200;
    int REQUEST_PHOTO_CODE = Constants.COMMAND_PING;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine);
        changeTitle("修改信息");
        showBack();
//        ISNav.getInstance().init(new ImageLoader() {
//            @Override
//            public void displayImage(Context context, String path, ImageView imageView) {
//                Glide.with(context).load(path).into(imageView);
//            }
//        });
        this.userImage = (ImageView) findViewById(R.id.userImage);
        this.view0 = (LinearLayout) findViewById(R.id.userhead);
        this.view1 = (LinearLayout) findViewById(R.id.view1);
        this.view2 = (LinearLayout) findViewById(R.id.view2);
        this.view3 = (LinearLayout) findViewById(R.id.view3);
        this.view4 = (LinearLayout) findViewById(R.id.view4);
        this.view5 = (LinearLayout) findViewById(R.id.view5);
        this.view6 = (LinearLayout) findViewById(R.id.view6);
        this.nickNameTxt = (TextView) findViewById(R.id.nickNameTxt);
        this.realNameTxt = (TextView) findViewById(R.id.realNameTxt);
        this.sexTxt = (TextView) findViewById(R.id.sexTxt);
        this.addrTxt = (TextView) findViewById(R.id.addrTxt);
        this.classTxt = (TextView) findViewById(R.id.classTxt);
        this.vipTxt = (TextView) findViewById(R.id.vipTxt);
        getUserInfo();
        this.userInfo = UserInfoManager.getInstance().getUserInfo(this);
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.loginOut);
        this.loginOutbtn = linearLayout;
        linearLayout.setVisibility(View.GONE);
        this.loginOutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                PushAgent mPushAgent = PushAgent.getInstance(MineDetailInfoActivity.this);
//                mPushAgent.deleteAlias(Tools.getUsername(MineDetailInfoActivity.this), "username", new UTrack.ICallBack() {
//                    @Override
//                    public void onMessage(boolean isSuccess, String message) {
//                    }
//                });
                StoToolManager.getInstance(MineDetailInfoActivity.this).clearAllController();
                UserInfoManager.getInstance().saveUser(_context, null);
                Tools.saveToken(_context, null);
                Tools.saveTeacher(_context, null);
                Tools.saveParentHoliday(_context, null);
                Tools.saveParentSchool(_context, null);
                Tools.saveParentModel(_context, null, 1);
                Tools.saveParentModel(_context, null, 2);
                UserInstallWhiteAppListManager.getInstance().clearOldApps();
                Tools.saveQxSet(_context, 0);
                Intent intent = new Intent();
                intent.setClass(_context, LoginActivity.class);
                _context.startActivity(intent);
                finish();
            }
        });
        this.view0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                avatarClick();
            }
        });
        this.view1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditText inputServer = new EditText(MineDetailInfoActivity.this);
                AlertDialog.Builder builder = new AlertDialog.Builder(MineDetailInfoActivity.this);
                builder.setTitle("修改昵称").setView(inputServer).setNegativeButton("取消", (DialogInterface.OnClickListener) null);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (Tools.objIsNullStr(inputServer.getText())) {
                            return;
                        }
                        userInfo.setNickName(inputServer.getText().toString());
                        upMsg();
                        reView();
                    }
                });
                builder.show();
            }
        });
        this.view2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditText inputServer = new EditText(MineDetailInfoActivity.this);
                AlertDialog.Builder builder = new AlertDialog.Builder(MineDetailInfoActivity.this);
                builder.setTitle("修改真实姓名").setView(inputServer).setNegativeButton("取消", (DialogInterface.OnClickListener) null);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (Tools.objIsNullStr(inputServer.getText())) {
                            return;
                        }
                        userInfo.setName(inputServer.getText().toString());
                        upMsg();
                        reView();
                    }
                });
                builder.show();
            }
        });
        this.view3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList arrayList = new ArrayList();
                arrayList.add("女");
                arrayList.add("男");
                OptionsPickerView pvOptions = new OptionsPickerBuilder(MineDetailInfoActivity.this, new OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int options2, int options3, View v) {
                        userInfo.setSex(options1);
                        upMsg();
                        reView();
                    }
                }).setTitleText("选择性别").setDividerColor(ViewCompat.MEASURED_STATE_MASK).setTextColorCenter(ViewCompat.MEASURED_STATE_MASK).setContentTextSize(16).setTitleBgColor(getResources().getColor(R.color.white, null)).setDividerColor(getResources().getColor(R.color.line_grey, null)).setLineSpacingMultiplier(2.1f).setSubCalSize(15).isCenterLabel(true).build();
                pvOptions.setPicker(arrayList);
                pvOptions.show();
            }
        });
        this.view5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                classList.add("一年级");
                classList.add("二年级");
                classList.add("三年级");
                classList.add("四年级");
                classList.add("五年级");
                classList.add("六年级");
                classList.add("七年级");
                classList.add("八年级");
                classList.add("九年级");
                classList.add("高一");
                classList.add("高二");
                classList.add("高三");
                OptionsPickerView pvOptions = new OptionsPickerBuilder(MineDetailInfoActivity.this, new OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int options2, int options3, View v) {
                        String str = classList.get(options1);
                        userInfo.setGrade(str);
                        upMsg();
                        reView();
                    }
                }).setTitleText("选择性别").setDividerColor(ViewCompat.MEASURED_STATE_MASK).setTextColorCenter(ViewCompat.MEASURED_STATE_MASK).setContentTextSize(16).setTitleBgColor(getResources().getColor(R.color.white, null)).setDividerColor(getResources().getColor(R.color.line_grey, null)).setLineSpacingMultiplier(2.1f).setSubCalSize(15).isCenterLabel(true).build();
                pvOptions.setPicker(classList);
                pvOptions.show();
            }
        });
        this.view4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPickerView();
            }
        });
        this.view6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MineDetailInfoActivity.this, FeedBackActivity.class);
                startActivity(intent);
            }
        });
        reView();
        initJsonData();
    }

    public void reView() {
        if (!Tools.objIsNullStr(this.userInfo.getImageUrl())) {
            Glide.with((FragmentActivity) this).load(this.userInfo.getImageUrl()).into(this.userImage);
        }
        long timea = System.currentTimeMillis();
        long etime = this.userInfo.getExpireTimeStamp();
        if (timea > etime) {
            this.vipTxt.setText("已过期");
            this.vipTxt.setTextColor(Constants.CATEGORY_MASK);
        } else {
            String timeStr = Tools.timeFormat(new Date(etime), "yyyy-MM-dd");
            TextView textView = this.vipTxt;
            textView.setText("到期时间:" + timeStr);
            this.vipTxt.setTextColor(ViewCompat.MEASURED_STATE_MASK);
        }
        this.nickNameTxt.setText(this.userInfo.getNickName());
        this.realNameTxt.setText(this.userInfo.getName());
        if (this.userInfo.getSex() == 1) {
            this.sexTxt.setText("男");
        } else {
            this.sexTxt.setText("女");
        }
        TextView textView2 = this.addrTxt;
        textView2.setText(this.userInfo.getProvince() + " " + this.userInfo.getCity() + " " + this.userInfo.getDistrict());
        this.classTxt.setText(this.userInfo.getGrade());
        if (!this.userInfo.isBindTeacher() && !this.userInfo.isBindParent()) {
            this.loginOutbtn.setVisibility(View.VISIBLE);
        } else {
            this.loginOutbtn.setVisibility(View.GONE);
        }
    }

    public void getUserInfo() {
        UserInfo userInfo = UserInfoManager.getInstance().getUserInfo(this);
        Map<String, String> parameters = new HashMap<>();
        parameters.put("studentId", userInfo.getId());
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

    public void upMsg() {
        Map<String, String> parameters = new HashMap<>();
        parameters.put(MapBundleKey.OfflineMapKey.OFFLINE_CITYNAME, this.userInfo.getName());
        parameters.put("nickName", this.userInfo.getNickName());
        parameters.put("province", this.userInfo.getProvince());
        parameters.put("city", this.userInfo.getCity());
        parameters.put("district", this.userInfo.getDistrict());
        parameters.put("imageUrl", this.userInfo.getImageUrl());
        parameters.put("sex", this.userInfo.getSex() + "");
        parameters.put("grade", this.userInfo.getGrade());
        showLoad();
        NetTools.getInstance().postAsynHttp(this, StudentBaseUrl.students_update, parameters, new ResultCallBackListener() {
            @Override
            public void onResponse(JSONObject msg) {
                dissLoad();
                if (msg != null) {
                    Gson gson = new Gson();
                    UserInfoManager userInfoManager = UserInfoManager.getInstance();
                    MineDetailInfoActivity mineDetailInfoActivity = MineDetailInfoActivity.this;
                    userInfoManager.saveUser(mineDetailInfoActivity, gson.toJson(mineDetailInfoActivity.userInfo).toString());
                    Tools.showAlert3(MineDetailInfoActivity.this, "保存成功");
                }
            }
        });
    }

    public void showPickerView() {
        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                String opt3tx = "";
                String opt1tx = options1Items.size() > 0 ? ((JsonBean) options1Items.get(options1)).getPickerViewText() : "";
                String opt2tx = (options2Items.size() <= 0 || ((ArrayList) options2Items.get(options1)).size() <= 0) ? "" : (String) ((ArrayList) options2Items.get(options1)).get(options2);
                if (options2Items.size() > 0 && ((ArrayList) options3Items.get(options1)).size() > 0 && ((ArrayList) ((ArrayList) options3Items.get(options1)).get(options2)).size() > 0) {
                    opt3tx = (String) ((ArrayList) ((ArrayList) options3Items.get(options1)).get(options2)).get(options3);
                }
                userInfo.setProvince(opt1tx);
                userInfo.setCity(opt2tx);
                userInfo.setDistrict(opt3tx);
                upMsg();
                reView();
            }
        }).setTitleText("城市选择").setDividerColor(ViewCompat.MEASURED_STATE_MASK).setTextColorCenter(ViewCompat.MEASURED_STATE_MASK).setContentTextSize(16).setTitleBgColor(getResources().getColor(R.color.white, null)).setDividerColor(getResources().getColor(R.color.line_grey, null)).setLineSpacingMultiplier(2.5f).setSubCalSize(15).build();
        pvOptions.setPicker(this.options1Items, this.options2Items, this.options3Items);
        pvOptions.show();
    }

    private void initJsonData() {
        String JsonData = new GetJsonDataUtil().getJson(this, "province.json");
        ArrayList<JsonBean> jsonBean = parseData(JsonData);
        this.options1Items = jsonBean;
        for (int i = 0; i < jsonBean.size(); i++) {
            ArrayList<String> cityList = new ArrayList<>();
            ArrayList<ArrayList<String>> province_AreaList = new ArrayList<>();
            for (int c = 0; c < jsonBean.get(i).getCityList().size(); c++) {
                String cityName = jsonBean.get(i).getCityList().get(c).getName();
                cityList.add(cityName);
                ArrayList<String> city_AreaList = new ArrayList<>();
                city_AreaList.addAll(jsonBean.get(i).getCityList().get(c).getArea());
                province_AreaList.add(city_AreaList);
            }
            this.options2Items.add(cityList);
            this.options3Items.add(province_AreaList);
        }
    }

    public ArrayList<JsonBean> parseData(String result) {
        ArrayList<JsonBean> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                JsonBean entity = (JsonBean) gson.fromJson(data.optJSONObject(i).toString(), JsonBean.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return detail;
    }

    public void avatarClick() {
//        ActionSheetDialog dialog = new ActionSheetDialog(this).builder().setTitle("请选择").addSheetItem("相册", null, new ActionSheetDialog.OnSheetItemClickListener() {
//            @Override
//            public void onClick(int which) {
//                showlog("相册");
//                ISListConfig config = new ISListConfig.Builder().multiSelect(false).rememberSelected(false).btnBgColor(-7829368).btnTextColor(-16776961).statusBarColor(Color.parseColor("#3F51B5")).title("图片").titleColor(-1).titleBgColor(Color.parseColor("#3F51B5")).cropSize(1, 1, 500, 500).needCrop(true).needCamera(false).maxNum(1).build();
//                ISNav iSNav = ISNav.getInstance();
//                MineDetailInfoActivity mineDetailInfoActivity = MineDetailInfoActivity.this;
//                iSNav.toListActivity(mineDetailInfoActivity, config, mineDetailInfoActivity.REQUEST_PHOTO_CODE);
//            }
//        }).addSheetItem("拍照", null, new ActionSheetDialog.OnSheetItemClickListener() {
//            @Override
//            public void onClick(int which) {
//                ISCameraConfig config = new ISCameraConfig.Builder().needCrop(true).cropSize(1, 1, 500, 500).build();
//                ISNav iSNav = ISNav.getInstance();
//                MineDetailInfoActivity mineDetailInfoActivity = MineDetailInfoActivity.this;
//                iSNav.toCameraActivity(mineDetailInfoActivity, config, mineDetailInfoActivity.REQUEST_CAMERA_CODE);
//            }
//        });
//        dialog.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == this.REQUEST_CAMERA_CODE && resultCode == -1 && data != null) {
            String path = data.getStringExtra("result");
            uploadImg(path);
        } else if (requestCode == this.REQUEST_PHOTO_CODE && resultCode == -1 && data != null) {
            List<String> pathList = data.getStringArrayListExtra("result");
            for (String path2 : pathList) {
                uploadImg(path2);
            }
        }
    }

    public void uploadImg(String path) {
        showLoad();
        Map<String, String> parameters = new HashMap<>();
        parameters.put("file", path);
        parameters.put("name", "company");
        NetTools.getInstance().postImageAsynHttp(this, false, StudentBaseUrl.fileInfos_upload, parameters, new ResultCallBackListener() {
            @Override
            public void onResponse(JSONObject msg) {
                dissLoad();
                if (msg != null) {
                    showlog(msg.toString());
                    try {
                        JSONObject data = msg.getJSONObject("data");
                        imagePath = data.getString("path");
                        userInfo.setImageUrl(imagePath);
                        upMsg();
                        reView();
                    } catch (Exception e) {
                        e.printStackTrace();
                        Tools.showAlert3(MineDetailInfoActivity.this, e.getMessage());
                    }
                }
            }
        });
    }
}
