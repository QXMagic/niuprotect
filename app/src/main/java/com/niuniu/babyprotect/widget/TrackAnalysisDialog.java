package com.niuniu.babyprotect.widget;

import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.niuniu.babyprotect.ui.map.TrackQueryActivity;
public class TrackAnalysisDialog extends PopupWindow {
    private Button cancelBtn;
    private CheckBox harshAccelCBx;
    private CheckBox harshBreakingCBx;
    private CheckBox speedingCBx;
    private CheckBox stayPointCBx;
    private TextView titleText;

    public TrackAnalysisDialog(TrackQueryActivity parent) {
        super(parent);
        this.speedingCBx = null;
        this.harshBreakingCBx = null;
        this.harshAccelCBx = null;
        this.stayPointCBx = null;
        this.titleText = null;
        this.cancelBtn = null;
        LayoutInflater layoutInflater = (LayoutInflater) parent.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
}
