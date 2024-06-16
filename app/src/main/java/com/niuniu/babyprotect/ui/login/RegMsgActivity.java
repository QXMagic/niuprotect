package com.niuniu.babyprotect.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.core.view.ViewCompat;
import com.baidu.platform.comapi.map.MapBundleKey;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.google.gson.Gson;
import im.niu.protect.R;
import com.niuniu.babyprotect.accessibility.auto.device.SystemDeviceInfo;
import com.niuniu.babyprotect.model.JsonBean;
import com.niuniu.babyprotect.network.NetTools;
import com.niuniu.babyprotect.network.ResultCallBackListener;
import com.niuniu.babyprotect.network.StudentBaseUrl;
import com.niuniu.babyprotect.tools.ILog;
import com.niuniu.babyprotect.tools.Tools;
import com.niuniu.babyprotect.tools.data.GetJsonDataUtil;
import com.niuniu.babyprotect.ui.base.BaseActivity;
import com.niuniu.babyprotect.ui.main.MainActivity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;
public class RegMsgActivity extends BaseActivity {
    String classStr;
    TextView classTxt;
    String local1Str;
    String local2Str;
    String local3Str;
    TextView localTxt;
    EditText nickNameTxt;
    Button regBtn;
    ImageView sex1Icon;
    ImageView sex2Icon;
    LinearLayout sexbtn1;
    LinearLayout sexbtn2;
    int sex = 1;
    List<String> classList = new ArrayList();
    private List<JsonBean> options1Items = new ArrayList();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_msg);
        changeTitle("完善资料");
        this.sexbtn1 = (LinearLayout) findViewById(R.id.sexbtn1);
        this.sexbtn2 = (LinearLayout) findViewById(R.id.sexbtn2);
        this.sex1Icon = (ImageView) findViewById(R.id.sex1Icon);
        this.sex2Icon = (ImageView) findViewById(R.id.sex2Icon);
        this.nickNameTxt = (EditText) findViewById(R.id.nickNameTxt);
        this.localTxt = (TextView) findViewById(R.id.localTxt);
        this.regBtn = (Button) findViewById(R.id.regBtn);
        this.classTxt = (TextView) findViewById(R.id.classTxt);
        this.sexbtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sex = 1;
                refSexView();
            }
        });
        this.sexbtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sex = 0;
                refSexView();
            }
        });
        this.classTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getClassName();
            }
        });
        this.localTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPickerView();
            }
        });
        this.regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginAction();
            }
        });
        this.classList.add("一年级");
        this.classList.add("二年级");
        this.classList.add("三年级");
        this.classList.add("四年级");
        this.classList.add("五年级");
        this.classList.add("六年级");
        this.classList.add("七年级");
        this.classList.add("八年级");
        this.classList.add("九年级");
        this.classList.add("高一");
        this.classList.add("高二");
        this.classList.add("高三");
        refSexView();
        initJsonData();
    }

    public void getClassName() {
        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                String opt1tx = classList.size() > 0 ? classList.get(options1) : "";
                classStr = opt1tx;
                classTxt.setText(classStr);
            }
        }).setTitleText("选择班级").setDividerColor(-12303292).setTextColorCenter(ViewCompat.MEASURED_STATE_MASK).setTitleSize(16).setDividerColor(getResources().getColor(R.color.line_color, null)).setCancelColor(R.color.darkgray).setContentTextSize(16).setItemVisibleCount(6).setLineSpacingMultiplier(2.5f).setSubCalSize(16).build();
        pvOptions.setPicker(this.classList);
        pvOptions.show();
    }

    public void refSexView() {
        if (this.sex == 1) {
            this.sex1Icon.setBackground(getResources().getDrawable(R.drawable.view_round4));
            this.sex2Icon.setBackground(getResources().getDrawable(R.drawable.view_round5));
            return;
        }
        this.sex1Icon.setBackground(getResources().getDrawable(R.drawable.view_round5));
        this.sex2Icon.setBackground(getResources().getDrawable(R.drawable.view_round4));
    }

    public void loginAction() {
        if (this.nickNameTxt.getText() == null || this.nickNameTxt.getText().toString().equals("")) {
            Tools.showAlert3(getApplication(), "昵称不能为空");
        } else if (this.localTxt.getText() == null || this.localTxt.getText().toString().equals("")) {
            Tools.showAlert3(getApplication(), "请选择地区");
        } else {
            String str = this.classStr;
            if (str == null || str.toString().equals("")) {
                Tools.showAlert3(getApplication(), "请选择年级");
                return;
            }
            Map<String, String> parameters = new HashMap<>();
            parameters.put(MapBundleKey.OfflineMapKey.OFFLINE_CITYNAME, this.nickNameTxt.getText().toString());
            parameters.put("nickName", this.nickNameTxt.getText().toString());
            parameters.put("province", this.local1Str);
            parameters.put("city", this.local2Str);
            parameters.put("district", this.local3Str);
            parameters.put("sex", this.sex + "");
            parameters.put("grade", this.classStr);
            parameters.put("mobileBrand", SystemDeviceInfo.getBrand());
            parameters.put("mobileModel", SystemDeviceInfo.getModel());
            parameters.put("systemVersion", SystemDeviceInfo.getPhoneOs());
            showLoadText("保存中....");
            NetTools.getInstance().postAsynHttp(this, StudentBaseUrl.students_update, parameters, new ResultCallBackListener() {
                @Override
                public void onResponse(JSONObject msg) {
                    dissLoad();
                    if (msg != null) {
                        ILog.log(msg);
                        startActivity(new Intent(RegMsgActivity.this, MainActivity.class));
                        finish();
                    }
                }
            });
        }
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
                local1Str = opt1tx;
                local2Str = opt2tx;
                local3Str = opt3tx;
                String tx = opt1tx + " " + opt2tx + " " + opt3tx;
                localTxt.setText(tx);
            }
        }).setTitleText("城市选择").setDividerColor(-12303292).setTextColorCenter(ViewCompat.MEASURED_STATE_MASK).setTitleSize(16).setDividerColor(getResources().getColor(R.color.line_color, null)).setCancelColor(R.color.darkgray).setContentTextSize(16).setItemVisibleCount(6).setLineSpacingMultiplier(2.5f).setSubCalSize(16).build();
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

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
