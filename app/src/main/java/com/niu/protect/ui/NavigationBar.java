package com.niu.protect.ui;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.niu.protect.action.MyOnClickListener;

import com.niu.protect.R;


public class NavigationBar extends LinearLayout {
    private View bgview;
    private MyOnClickListener leftlistener;
    private MyOnClickListener leftlistener2;
    private MyOnClickListener leftlistener3;
    private MyOnClickListener leftlistener4;
    private ImageButton nav_left_btn;
    private Button nav_left_btn2;
    private ImageButton nav_left_btn3;
    private ImageButton nav_left_btn4;
    private Button nav_right_btn1;
    private ImageButton nav_right_btn2;
    private Button nav_right_btn3;
    private ImageButton nav_right_btn4;
    private MyOnClickListener rightlistener1;
    private MyOnClickListener rightlistener2;
    private MyOnClickListener rightlistener3;
    private MyOnClickListener rightlistener4;
    private TextView titletxt;

    public NavigationBar(Context context) {
        super(context);
    }

    public NavigationBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.navigationbar, this);
        View findViewById = findViewById(R.id.bgview);
        this.bgview = findViewById;
        findViewById.setBackgroundColor(getResources().getColor(R.color.colorAccent, null));
        this.titletxt = (TextView) findViewById(R.id.nav_title);
        ImageButton imageButton = (ImageButton) findViewById(R.id.nav_btn_left);
        this.nav_left_btn = imageButton;
        imageButton.setVisibility(View.INVISIBLE);
        this.nav_left_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickleft((ImageButton) v);
            }
        });
        Button button = (Button) findViewById(R.id.nav_btn_left2);
        this.nav_left_btn2 = button;
        button.setVisibility(View.INVISIBLE);
        this.nav_left_btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickleft2((Button) v);
            }
        });
        ImageButton imageButton2 = (ImageButton) findViewById(R.id.nav_btn_left3);
        this.nav_left_btn3 = imageButton2;
        imageButton2.setVisibility(View.INVISIBLE);
        this.nav_left_btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickleft3((ImageButton) v);
            }
        });
        ImageButton imageButton3 = (ImageButton) findViewById(R.id.nav_btn_left4);
        this.nav_left_btn4 = imageButton3;
        imageButton3.setVisibility(View.INVISIBLE);
        this.nav_left_btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickleft4((ImageButton) v);
            }
        });
        Button button2 = (Button) findViewById(R.id.nav_btn_right1);
        this.nav_right_btn1 = button2;
        button2.setVisibility(View.INVISIBLE);
        this.nav_right_btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickright1((Button) v);
            }
        });
        ImageButton imageButton4 = (ImageButton) findViewById(R.id.nav_btn_right2);
        this.nav_right_btn2 = imageButton4;
        imageButton4.setVisibility(View.INVISIBLE);
        this.nav_right_btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickright2((ImageButton) v);
            }
        });
        Button button3 = (Button) findViewById(R.id.nav_btn_right3);
        this.nav_right_btn3 = button3;
        button3.setVisibility(View.INVISIBLE);
        this.nav_right_btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickright3((Button) v);
            }
        });
        ImageButton imageButton5 = (ImageButton) findViewById(R.id.nav_btn_right4);
        this.nav_right_btn4 = imageButton5;
        imageButton5.setVisibility(View.INVISIBLE);
        this.nav_right_btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickright4((ImageButton) v);
            }
        });
    }

    public void showBarleftbtn(boolean show) {
        if (show) {
            this.nav_left_btn2.setVisibility(View.VISIBLE);
        } else {
            this.nav_left_btn2.setVisibility(View.INVISIBLE);
        }
    }

    public void setShuaxinIcon(boolean isok) {
    }

    public void setShareIcon(boolean isok) {
    }

    public void titleSize(int type) {
        if (type == 1) {
            this.titletxt.setTextSize(18.0f);
        } else {
            this.titletxt.setTextSize(18.0f);
        }
    }

    public void titleColor(int type) {
        if (type == 1) {
            this.titletxt.setTextColor(Color.parseColor("#333333"));
        } else {
            this.titletxt.setTextColor(Color.parseColor("#ffffffff"));
        }
    }

    public void backColor(int type) {
        if (type == 1) {
            this.bgview.setBackgroundColor(Color.parseColor("#f9f9f9"));
        } else {
            this.bgview.setBackgroundColor(Color.parseColor("#00000000"));
        }
    }

    public void setTitle(String str) {
        this.titletxt.setText(str);
    }

    public void showLeftBtn(Boolean show) {
        if (show.booleanValue()) {
            this.nav_left_btn.setVisibility(View.VISIBLE);
        } else {
            this.nav_left_btn.setVisibility(View.INVISIBLE);
        }
    }

    public void showLeftbtn(int type) {
        if (type == 0) {
            this.nav_left_btn.setVisibility(View.VISIBLE);
            this.nav_left_btn2.setVisibility(View.INVISIBLE);
            this.nav_left_btn3.setVisibility(View.INVISIBLE);
            this.nav_left_btn4.setVisibility(View.INVISIBLE);
        } else if (type == 1) {
            this.nav_left_btn2.setVisibility(View.VISIBLE);
            this.nav_left_btn.setVisibility(View.INVISIBLE);
            this.nav_left_btn3.setVisibility(View.INVISIBLE);
            this.nav_left_btn4.setVisibility(View.INVISIBLE);
        } else if (type == 2) {
            this.nav_left_btn3.setVisibility(View.VISIBLE);
            this.nav_left_btn.setVisibility(View.INVISIBLE);
            this.nav_left_btn2.setVisibility(View.INVISIBLE);
            this.nav_left_btn4.setVisibility(View.INVISIBLE);
        } else if (type == 4) {
            this.nav_left_btn3.setVisibility(View.INVISIBLE);
            this.nav_left_btn.setVisibility(View.INVISIBLE);
            this.nav_left_btn2.setVisibility(View.INVISIBLE);
            this.nav_left_btn4.setVisibility(View.VISIBLE);
        }
    }

    public void showRightbtn(int type) {
        if (type == 0) {
            this.nav_right_btn1.setVisibility(View.VISIBLE);
            this.nav_right_btn2.setVisibility(View.INVISIBLE);
            this.nav_right_btn3.setVisibility(View.INVISIBLE);
            this.nav_right_btn4.setVisibility(View.INVISIBLE);
        } else if (type == 1) {
            this.nav_right_btn2.setVisibility(View.VISIBLE);
            this.nav_right_btn1.setVisibility(View.INVISIBLE);
            this.nav_right_btn3.setVisibility(View.INVISIBLE);
            this.nav_right_btn4.setVisibility(View.INVISIBLE);
        } else if (type == 2) {
            this.nav_right_btn2.setVisibility(View.INVISIBLE);
            this.nav_right_btn1.setVisibility(View.VISIBLE);
            this.nav_right_btn3.setVisibility(View.VISIBLE);
            this.nav_right_btn4.setVisibility(View.INVISIBLE);
        } else if (type == 3) {
            this.nav_right_btn2.setVisibility(View.INVISIBLE);
            this.nav_right_btn1.setVisibility(View.INVISIBLE);
            this.nav_right_btn3.setVisibility(View.INVISIBLE);
            this.nav_right_btn4.setVisibility(View.INVISIBLE);
        } else if (type == 4) {
            this.nav_right_btn2.setVisibility(View.INVISIBLE);
            this.nav_right_btn1.setVisibility(View.INVISIBLE);
            this.nav_right_btn3.setVisibility(View.VISIBLE);
            this.nav_right_btn4.setVisibility(View.INVISIBLE);
        } else if (type == 5) {
            this.nav_right_btn2.setVisibility(View.INVISIBLE);
            this.nav_right_btn1.setVisibility(View.INVISIBLE);
            this.nav_right_btn3.setVisibility(View.INVISIBLE);
            this.nav_right_btn4.setVisibility(View.VISIBLE);
        }
    }

    public void setRightImage(int imageid) {
        this.nav_right_btn2.setImageResource(imageid);
    }

    public void setRightText(String txt) {
        showRightbtn(0);
        this.nav_right_btn1.setText(txt);
    }

    public void setLeftBtn(String str) {
        this.nav_left_btn2.setText(str);
    }

    public void clickleft(ImageButton btn) {
        this.leftlistener.onClick(btn);
    }

    public void setLeftOnClickListener(MyOnClickListener listener) {
        this.leftlistener = listener;
    }

    public void clickleft2(Button btn) {
        this.leftlistener2.onClick(btn);
    }

    public void setLeft2OnClickListener(MyOnClickListener listener) {
        this.leftlistener2 = listener;
    }

    public void clickleft3(ImageButton btn) {
        this.leftlistener3.onClick(btn);
    }

    public void setLeft3OnClickListener(MyOnClickListener listener) {
        this.leftlistener3 = listener;
    }

    public void clickleft4(ImageButton btn) {
        this.leftlistener4.onClick(btn);
    }

    public void setLeft4OnClickListener(MyOnClickListener listener) {
        this.leftlistener4 = listener;
    }

    public void clickright1(Button btn) {
        this.rightlistener1.onClick(btn);
    }

    public void setRight1OnClickListener(MyOnClickListener listener) {
        this.rightlistener1 = listener;
    }

    public void clickright2(ImageButton btn) {
        this.rightlistener2.onClick(btn);
    }

    public void setRight2OnClickListener(MyOnClickListener listener) {
        this.rightlistener2 = listener;
    }

    public void clickright3(Button btn) {
        this.rightlistener3.onClick(btn);
    }

    public void setRight3OnClickListener(MyOnClickListener listener) {
        this.rightlistener3 = listener;
    }

    public void clickright4(ImageButton btn) {
        this.rightlistener4.onClick(btn);
    }

    public void setRight4OnClickListener(MyOnClickListener listener) {
        this.rightlistener4 = listener;
    }
}
