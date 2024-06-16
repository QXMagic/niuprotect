package com.niuniu.babyprotect.ui.setting;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.provider.Settings;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.viewpager.widget.ViewPager;
import com.baidu.platform.comapi.map.NodeType;
import com.hjq.permissions.OnPermissionCallback;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import im.niu.protect.R;
import com.niuniu.babyprotect.accessibility.OpenAccessibilitySettingHelper;
import com.niuniu.babyprotect.accessibility.StatusUseAccessibilityService;
import com.niuniu.babyprotect.action.QxOnClickListener;
import com.niuniu.babyprotect.adapter.QxViewPagerAdapter;
import com.niuniu.babyprotect.broadcastReceiver.DeviceReceiver;
import com.niuniu.babyprotect.manager.UserProtectManager;
import com.niuniu.babyprotect.model.QxInfo;
import com.niuniu.babyprotect.tools.ILog;
import com.niuniu.babyprotect.tools.Tools;
import com.niuniu.babyprotect.ui.base.BaseActivity;
import com.niuniu.babyprotect.widget.MyViewPager;
import java.util.ArrayList;
import java.util.List;
public class OpenQxOppoActivity extends BaseActivity {
    public static int OVERLAY_PERMISSION_REQ_CODE = NodeType.E_STREET_POI;
    private static final String TAG = "TestFloatWinActivity";
    Button btn_enter_auto;
    private DevicePolicyManager dpm;
    private QxViewPagerAdapter mAdapter;
    private ComponentName mDeviceAdminSample;
    List<QxInfo> qxInfoList = new ArrayList();
    private MyViewPager view_pager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_qxoppo);
        changeTitle("授权中心");
        Button button = (Button) findViewById(R.id.btn_enter_auto);
        this.btn_enter_auto = button;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OpenQxOppoActivity.this, PermCollectActivity.class);
                startActivity(intent);
                finish();
            }
        });
        Tools.saveQxSet(this._context, 2);
        initViews();
        getLayoutInflater();
        LayoutInflater lf = LayoutInflater.from(this);
        for (int i = 0; i < 11; i++) {
            QxInfo qxInfo = new QxInfo();
            qxInfo.setIsok(0);
            qxInfo.setView(lf.inflate(R.layout.item_qxview, (ViewGroup) null));
            if (i == 0) {
                qxInfo.setTitle("获取后台定位权限");
                qxInfo.setDes("请选择始终允许");
            } else if (i == 1) {
                qxInfo.setTitle("激活设备管理器");
                qxInfo.setDes("设备管理器 ➜ 激活");
            } else if (i == 2) {
                qxInfo.setTitle("辅助功能");
                qxInfo.setDes("选择3985学生端 ➜ 打开开关");
            }
            this.qxInfoList.add(qxInfo);
        }
        this.mAdapter.add(this.qxInfoList, this);
        refDot();
        Tools.saveAutoSet(this, 0);
        UserProtectManager.getInstance().setProtect(-2);
        Tools.saveLocTask(this, -1);
        Tools.saveStep5(this, -1);
        Tools.saveStep6(this, -1);
        Tools.saveStep7(this, -1);
        Tools.saveStep8(this, -1);
        Tools.saveStep9(this, -1);
        Tools.saveStep10(this, -1);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == 4) {
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void openLock() {
        this.dpm = (DevicePolicyManager) getSystemService("device_policy");
        ComponentName componentName = new ComponentName(getApplicationContext(), DeviceReceiver.class);
        this.mDeviceAdminSample = componentName;
        if (this.dpm.isAdminActive(componentName)) {
            nextAction();
        }
        Intent intent = new Intent("android.app.action.ADD_DEVICE_ADMIN");
        intent.putExtra("android.app.extra.DEVICE_ADMIN", this.mDeviceAdminSample);
        intent.putExtra("android.app.extra.ADD_EXPLANATION", "开启后就可以使用锁屏功能了...");
        startActivityForResult(intent, 0);
    }

    private void initViews() {
        this.view_pager = (MyViewPager) findViewById(R.id.view_pager);
        QxViewPagerAdapter qxViewPagerAdapter = new QxViewPagerAdapter();
        this.mAdapter = qxViewPagerAdapter;
        this.view_pager.setAdapter(qxViewPagerAdapter);
        this.view_pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                refDot();
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        this.mAdapter.setQxOnClickListener(new QxOnClickListener() {
            @Override
            public void onClick(int pos) {
                if (pos == 0) {
                    localAction();
                } else if (pos == 1) {
                    openLock();
                } else if (pos == 2) {
                    Tools.saveAutoSet(OpenQxOppoActivity.this, 1);
                    xfkAction();
                } else if (pos == 3) {
                    Tools.saveStep10(OpenQxOppoActivity.this, -3);
                    Intent intent = new Intent("android.settings.SETTINGS");
                    _context.startActivity(intent);
                } else if (pos == 4) {
                    if (!isIgnoringBatteryOptimizations()) {
                        requestIgnoreBatteryOptimizations();
                    } else {
                        nextAction();
                    }
                } else if (pos == 5) {
                    if (Tools.getStep5(OpenQxOppoActivity.this) == -1) {
                        new AlertDialog.Builder(OpenQxOppoActivity.this).setTitle("提示").setMessage("自动设置，请勿操作").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Tools.saveStep5(OpenQxOppoActivity.this, 0);
                                OPPO();
                            }
                        }).show();
                    } else {
                        OPPO();
                    }
                } else if (pos == 6) {
                    if (Tools.getStep6(OpenQxOppoActivity.this) == -1) {
                        new AlertDialog.Builder(OpenQxOppoActivity.this).setTitle("提示").setMessage("自动设置，请勿操作").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Tools.saveStep6(OpenQxOppoActivity.this, 0);
                                int version = Build.VERSION.SDK_INT;
                                if (version == 28) {
                                    OPPO();
                                    return;
                                }
                                Intent intent2 = getpManager();
                                if (intent2 != null) {
                                    intent2.putExtra("type", "110");
                                    _context.startActivity(intent2);
                                }
                            }
                        }).show();
                        return;
                    }
                    int version = Build.VERSION.SDK_INT;
                    if (version == 28) {
                        OPPO();
                        return;
                    }
                    Intent intent2 = getpManager();
                    if (intent2 != null) {
                        intent2.putExtra("type", "110");
                        _context.startActivity(intent2);
                    }
                } else if (pos == 7) {
                    askForPermission();
                } else if (pos == 8) {
                    if (Tools.getStep8(OpenQxOppoActivity.this) == -1) {
                        new AlertDialog.Builder(OpenQxOppoActivity.this).setTitle("提示").setMessage("自动设置，请勿操作").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Tools.saveStep8(OpenQxOppoActivity.this, 0);
                                Intent intent3 = new Intent("android.settings.USAGE_ACCESS_SETTINGS");
                                _context.startActivity(intent3);
                            }
                        }).show();
                        return;
                    }
                    _context.startActivity(new Intent("android.settings.USAGE_ACCESS_SETTINGS"));
                } else if (pos == 9) {
                    if (Tools.getStep9(OpenQxOppoActivity.this) == -1) {
                        new AlertDialog.Builder(OpenQxOppoActivity.this).setTitle("提示").setMessage("自动设置，请勿操作").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Tools.saveStep9(OpenQxOppoActivity.this, 0);
                                OPPO();
                            }
                        }).show();
                    } else {
                        OPPO();
                    }
                } else if (pos == 10) {
                    UserProtectManager.getInstance().setProtect(1);
                    Tools.saveAutoSet(OpenQxOppoActivity.this, 0);
                    Tools.saveQxSet(_context, 1);
                    Intent backHome = new Intent("android.intent.action.MAIN");
                    backHome.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    backHome.addCategory("android.intent.category.HOME");
                    startActivity(backHome);
                    finish();
                }
            }
        });
    }

    public Intent getpManager() {
        PackageManager pm = getPackageManager();
        Intent mainIntent = new Intent("android.intent.action.MAIN", (Uri) null);
        mainIntent.addCategory("android.intent.category.LAUNCHER");
        List<ResolveInfo> activities = pm.queryIntentActivities(mainIntent, 0);
        for (ResolveInfo info : activities) {
            String packName = info.activityInfo.packageName;
            if (packName.equals("com.coloros.phonemanager")) {
                Intent launchIntent = new Intent();
                launchIntent.setComponent(new ComponentName(packName, info.activityInfo.name));
                return launchIntent;
            }
        }
        return null;
    }

    public void refDot() {
        LinearLayout dotview = (LinearLayout) findViewById(R.id.dotview);
        for (int i = 0; i < this.qxInfoList.size(); i++) {
            QxInfo qxInfo = this.qxInfoList.get(i);
            int tag = i + 1000;
            LinearLayout dview = (LinearLayout) dotview.findViewWithTag(tag + "");
            if (dview != null) {
                if (qxInfo.getIsok() == 0) {
                    dview.setBackgroundResource(R.drawable.view_round9);
                } else if (qxInfo.getIsok() == 1) {
                    dview.setBackgroundResource(R.drawable.view_round8);
                } else if (qxInfo.getIsok() == -1) {
                    dview.setBackgroundResource(R.drawable.view_round10);
                }
                if (i == this.view_pager.getCurrentItem()) {
                    dview.setBackgroundResource(R.drawable.view_round11);
                }
            }
        }
    }

    public void nextAction() {
        QxInfo qxInfo = this.qxInfoList.get(this.view_pager.getCurrentItem());
        qxInfo.setIsok(1);
        this.mAdapter.notifyDataSetChanged();
        MyViewPager myViewPager = this.view_pager;
        myViewPager.setCurrentItem(myViewPager.getCurrentItem() + 1);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (this.view_pager.getCurrentItem() == this.qxInfoList.size() - 1) {
            return;
        }
        if (Tools.getStep5(this) == 1) {
            nextAction();
            Tools.saveStep5(this, -1);
        } else if (Tools.getStep5(this) == -2) {
            QxInfo qxInfo = this.qxInfoList.get(this.view_pager.getCurrentItem());
            qxInfo.setIsok(-1);
            Tools.showAlert3(this, "自动设置失败，请手动设置");
            Tools.saveStep5(this, -3);
        } else if (Tools.getStep5(this) == -3) {
            new AlertDialog.Builder(this).setTitle("提示").setMessage("是否按要求设定").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Tools.saveStep5(OpenQxOppoActivity.this, 1);
                    nextAction();
                }
            }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Tools.saveStep5(OpenQxOppoActivity.this, -1);
                    QxInfo qxInfo2 = qxInfoList.get(view_pager.getCurrentItem());
                    qxInfo2.setIsok(-1);
                    refDot();
                }
            }).show();
        }
        if (Tools.getStep6(this) == 1) {
            nextAction();
            Tools.saveStep6(this, -1);
        } else if (Tools.getStep6(this) == -2) {
            Tools.showAlert3(this, "自动设置失败，请手动设置");
            Tools.saveStep6(this, -3);
            QxInfo qxInfo2 = this.qxInfoList.get(this.view_pager.getCurrentItem());
            qxInfo2.setIsok(-1);
        } else if (Tools.getStep6(this) == -3) {
            new AlertDialog.Builder(this).setTitle("提示").setMessage("是否按要求设定").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Tools.saveStep6(OpenQxOppoActivity.this, 1);
                    nextAction();
                }
            }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Tools.saveStep6(OpenQxOppoActivity.this, -1);
                    QxInfo qxInfo3 = qxInfoList.get(view_pager.getCurrentItem());
                    qxInfo3.setIsok(-1);
                    refDot();
                }
            }).show();
        }
        if (Tools.getStep7(this) == 1) {
            nextAction();
            Tools.saveStep7(this, -1);
        } else if (Tools.getStep7(this) == -2) {
            Tools.showAlert3(this, "自动设置失败，请手动设置");
            Tools.saveStep7(this, -3);
            QxInfo qxInfo3 = this.qxInfoList.get(this.view_pager.getCurrentItem());
            qxInfo3.setIsok(-1);
        } else if (Tools.getStep7(this) == -3) {
            new AlertDialog.Builder(this).setTitle("提示").setMessage("是否按要求设定").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Tools.saveStep7(OpenQxOppoActivity.this, 1);
                    nextAction();
                }
            }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Tools.saveStep7(OpenQxOppoActivity.this, -1);
                    QxInfo qxInfo4 = qxInfoList.get(view_pager.getCurrentItem());
                    qxInfo4.setIsok(-1);
                    refDot();
                }
            }).show();
        }
        if (Tools.getStep8(this) == 1) {
            nextAction();
            Tools.saveStep8(this, -1);
        } else if (Tools.getStep8(this) == -2) {
            Tools.showAlert3(this, "自动设置失败，请手动设置");
            Tools.saveStep8(this, -3);
            QxInfo qxInfo4 = this.qxInfoList.get(this.view_pager.getCurrentItem());
            qxInfo4.setIsok(-1);
        } else if (Tools.getStep8(this) == -3) {
            new AlertDialog.Builder(this).setTitle("提示").setMessage("是否按要求设定").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Tools.saveStep8(OpenQxOppoActivity.this, 1);
                    nextAction();
                }
            }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Tools.saveStep8(OpenQxOppoActivity.this, -1);
                    QxInfo qxInfo5 = qxInfoList.get(view_pager.getCurrentItem());
                    qxInfo5.setIsok(-1);
                    refDot();
                }
            }).show();
        }
        if (Tools.getStep9(this) == 1) {
            nextAction();
            Tools.saveStep9(this, -1);
        } else if (Tools.getStep9(this) == -2) {
            Tools.showAlert3(this, "自动设置失败，请手动设置");
            Tools.saveStep9(this, -3);
            QxInfo qxInfo5 = this.qxInfoList.get(this.view_pager.getCurrentItem());
            qxInfo5.setIsok(-1);
        } else if (Tools.getStep9(this) == -3) {
            new AlertDialog.Builder(this).setTitle("提示").setMessage("是否按要求设定").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Tools.saveStep9(OpenQxOppoActivity.this, 1);
                    nextAction();
                }
            }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Tools.saveStep9(OpenQxOppoActivity.this, -1);
                    QxInfo qxInfo6 = qxInfoList.get(view_pager.getCurrentItem());
                    qxInfo6.setIsok(-1);
                    refDot();
                }
            }).show();
        }
        if (Tools.getStep10(this) == 1) {
            nextAction();
            Tools.saveStep10(this, -1);
        } else if (Tools.getStep10(this) == -2) {
            Tools.showAlert3(this, "自动设置失败，请手动设置");
            Tools.saveStep10(this, -3);
            QxInfo qxInfo6 = this.qxInfoList.get(this.view_pager.getCurrentItem());
            qxInfo6.setIsok(-1);
        } else if (Tools.getStep10(this) == -3) {
            new AlertDialog.Builder(this).setTitle("提示").setMessage("是否按要求设定").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Tools.saveStep10(OpenQxOppoActivity.this, -1);
                    nextAction();
                }
            }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Tools.saveStep10(OpenQxOppoActivity.this, -1);
                    QxInfo qxInfo7 = qxInfoList.get(view_pager.getCurrentItem());
                    qxInfo7.setIsok(-1);
                    refDot();
                }
            }).show();
        }
        refDot();
        int lct = Tools.getLocTask(this);
        if (lct == 2) {
            Tools.saveLocTask(this, -1);
            new AlertDialog.Builder(this).setTitle("提示").setMessage("是否按要求设定").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    nextAction();
                }
            }).setNegativeButton("取消", (DialogInterface.OnClickListener) null).show();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Tools.saveAutoSet(this, 0);
    }

    public void OPPO() {
        Intent intent = new Intent();
        intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setData(Uri.parse("package:" + getPackageName()));
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        ILog.d(TAG, "------------------------" + OpenAccessibilitySettingHelper.isAccessibilitySettingsOn(this, StatusUseAccessibilityService.class.getName()));
        if (OpenAccessibilitySettingHelper.isAccessibilitySettingsOn(this, StatusUseAccessibilityService.class.getName()) || OpenAccessibilitySettingHelper.isAccessibilitySettingsOnByService(this)) {
            this.btn_enter_auto.setVisibility(View.VISIBLE);
        }
    }

    public void xfkAction() {
        if (!OpenAccessibilitySettingHelper.isAccessibilitySettingsOn(this, StatusUseAccessibilityService.class.getName())) {
            OpenAccessibilitySettingHelper.jumpToSettingPage(this);
            return;
        }
        Toast.makeText(this, "权限已开启", Toast.LENGTH_SHORT).show();
        nextAction();
    }

    public void localAction() {
        XXPermissions.with(this).permission(Permission.ACCESS_BACKGROUND_LOCATION).request(new OnPermissionCallback() {
            @Override
            public void onGranted(List<String> permissions, boolean all) {
                nextAction();
            }

            @Override
            public void onDenied(List<String> permissions, boolean never) {
                if (never) {
                    Toast.makeText(OpenQxOppoActivity.this, "被永久拒绝授权，请手动授予权限", Toast.LENGTH_SHORT).show();
                    XXPermissions.startPermissionActivity((Activity) OpenQxOppoActivity.this, permissions);
                    return;
                }
                Toast.makeText(OpenQxOppoActivity.this, "获取权限失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public boolean isIgnoringBatteryOptimizations() {
        PowerManager powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
        if (powerManager == null || Build.VERSION.SDK_INT < 23) {
            return false;
        }
        boolean isIgnoring = powerManager.isIgnoringBatteryOptimizations(getPackageName());
        return isIgnoring;
    }

    public void requestIgnoreBatteryOptimizations() {
        try {
            Intent intent = new Intent("android.settings.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS");
            intent.setData(Uri.parse("package:" + getPackageName()));
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void askForPermission() {
        if (!Settings.canDrawOverlays(this)) {
            if (Tools.getStep7(this) == -1) {
                new AlertDialog.Builder(this).setTitle("提示").setMessage("自动设置，请勿操作").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Tools.saveStep7(OpenQxOppoActivity.this, 0);
                        OPPO();
                    }
                }).setNegativeButton("取消", (DialogInterface.OnClickListener) null).show();
                return;
            } else {
                OPPO();
                return;
            }
        }
        Toast.makeText(this, "权限已开启", Toast.LENGTH_SHORT).show();
        nextAction();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == OVERLAY_PERMISSION_REQ_CODE) {
            if (!Settings.canDrawOverlays(this)) {
                Toast.makeText(this, "权限授予失败，无法开启悬浮窗", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "权限授予成功！", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
