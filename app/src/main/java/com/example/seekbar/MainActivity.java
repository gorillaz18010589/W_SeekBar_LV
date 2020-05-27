package com.example.seekbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.graphics.Color;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private SeekBar seekBar;
    private EditText editText;
    private TextView txtMsg;
    private int value;
    private String stringValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        seekBar = findViewById(R.id.seekBar);
        editText = findViewById(R.id.edit_text);
        txtMsg = findViewById(R.id.txt_msg);


        //設定使用者輸入完成的事件
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                String progress = editText.getText().toString().trim();
                String msg = checkPassword(progress);
                Toast.makeText(MainActivity.this, "密碼強度:" + msg, Toast.LENGTH_SHORT).show();


                //判斷使用者輸入強度,並且給予Progges強弱度設定值
                if (msg.equals("弱")) {
                    seekBar.setProgress(3);
                } else if (msg.equals("中")) {
                    seekBar.setProgress(5);
                } else if (msg.equals("强")) {
                    seekBar.setProgress(10);
                }

                //將使用者輸入
//                int progressValue = Integer.parseInt(progress);
//                seekBar.setProgress(progressValue);//設定進度條的值為(進度值);
                Toast.makeText(MainActivity.this, "onEditorAction => 密碼強度為:" + msg, Toast.LENGTH_SHORT).show();
                return false;
            }
        });


        //seekBar設定監聽
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {


            //當進度條移動式,設定跟觸碰都算
            @Override
            public void onProgressChanged(
                    SeekBar seekBar, //1.seekBar元件
                    int progress,    //2.max等級依你xml設定為主
                    boolean fromUser //3.是否是使用者觸碰而觸發的(true是/false不是)
            ) {

                //取得進度條設定的值,
                value = seekBar.getProgress();//取得進度條設定的值
                stringValue = String.valueOf(value);
                txtMsg.setText(stringValue);



                //當進度是int 1~10的位置時
                switch (progress) {
                    case 3:
                        Toast.makeText(MainActivity.this, progress + "", Toast.LENGTH_SHORT).show();

                        //設定進度條寬高
                        int left = 300;
                        int height = 180;
                        seekBar.setLayoutParams(new ConstraintLayout.LayoutParams(left, height));
                        Drawable drawable = seekBar.getThumb();

                        //設定顏色跟進度條位置
                        int color = getResources().getColor(R.color.bg_red);
                        int gravityLeft = Gravity.LEFT;
                        setChangeColor(color, gravityLeft);
                        break;
                    case 5:
                        Toast.makeText(MainActivity.this, progress + "", Toast.LENGTH_SHORT).show();
                        int orange = getResources().getColor(R.color.bg_orangef);
                        int left2 = 500;
                        int height2 = 180;
                        seekBar.setLayoutParams(new ConstraintLayout.LayoutParams(left2, height2));
                        int gravityCenter = Gravity.LEFT;
                        setChangeColor(orange, gravityCenter);
                        break;
                    case 10:
                        Toast.makeText(MainActivity.this, progress + "", Toast.LENGTH_SHORT).show();
                        int green = getResources().getColor(R.color.bg_green11);
                        int left3 = 1000;
                        int height3 = 180;
                        seekBar.setLayoutParams(new ConstraintLayout.LayoutParams(left3, height3));
                        int gravityMask = Gravity.LEFT;
                        setChangeColor(green, gravityMask);
                        break;
                }
                shoLog("onProgressChanged=> /seekBar:" + seekBar + "/progress:" + progress + "/fromUser:" + fromUser);
            }

            //第一次觸碰到進度條時
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                shoLog("onStartTrackingTouch");
                Toast.makeText(MainActivity.this, "onStartTrackingTouch", Toast.LENGTH_SHORT).show();

            }

            //當放掉觸碰時
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Toast.makeText(MainActivity.this, "onStopTrackingTouch", Toast.LENGTH_SHORT).show();
            }
        });
    }


    //判斷帳密強度
    public static String checkPassword(String passwordStr) {
        String regexZ = "\\d*";
        String regexS = "[a-zA-Z]+";
        String regexT = "\\W+$";
        String regexZT = "\\D*";
        String regexST = "[\\d\\W]*";
        String regexZS = "\\w*";
        String regexZST = "[\\w\\W]*";

        if (passwordStr.matches(regexZ)) {
            return "弱";
        }
        if (passwordStr.matches(regexS)) {
            return "弱";
        }
        if (passwordStr.matches(regexT)) {
            return "弱";
        }
        if (passwordStr.matches(regexZT)) {
            return "中";
        }
        if (passwordStr.matches(regexST)) {
            return "中";
        }
        if (passwordStr.matches(regexZS)) {
            return "中";
        }
        if (passwordStr.matches(regexZST)) {
            return "强";
        }
        return passwordStr;

    }

    //設定進度條變換顏色
    private void setChangeColor(int color, int gravity) {
        ClipDrawable d = new ClipDrawable(new ColorDrawable(color), gravity
                , ClipDrawable.HORIZONTAL);
        seekBar.setProgressDrawable(d);
    }


    //依分數變換顏色
    private void setProgressBarColour(int score) {
        int color = 0;
        if (score < 30) {
            color = getResources().getColor(R.color.bg_red);
        } else if (score < 70) {
            color = getResources().getColor(R.color.bg_orangef);
        } else {
            color = getResources().getColor(R.color.bg_green11);
        }
        ClipDrawable d = new ClipDrawable(new ColorDrawable(color), Gravity.
                LEFT, ClipDrawable.HORIZONTAL);
        seekBar.setProgressDrawable(d);
        seekBar.setProgress(score);
    }

    private void shoLog(String msg) {
        Log.v("hank", msg);
    }

    public void changeColor(View view) {
        setProgressBarColour(20);
    }
}
