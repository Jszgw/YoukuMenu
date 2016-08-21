package com.weizh.a02_;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.weizh.a02_.utils.AnimationUtil;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton ibtnHome, ibtnMenu;
    private RelativeLayout rlInner, rlMiddle, rlOut;
    private boolean isLevel3Display = true;
    private boolean isLevel2Display = true;
    private boolean isLevel1Display = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        ibtnHome = (ImageButton) findViewById(R.id.ibtn_home);
        ibtnMenu = (ImageButton) findViewById(R.id.ibtn_menu);
        rlInner = (RelativeLayout) findViewById(R.id.rl_inner);
        rlMiddle = (RelativeLayout) findViewById(R.id.rl_middle);
        rlOut = (RelativeLayout) findViewById(R.id.rl_out);

        ibtnHome.setOnClickListener(this);
        ibtnMenu.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (AnimationUtil.animationCount > 0) {
            return;
        }
        switch (view.getId()) {
            case R.id.ibtn_home:
                if (isLevel2Display) {
                    int delay = 0;
                    if (isLevel3Display) {
                        //如果第3级菜单显示，转出去
                        AnimationUtil.rotateOut(rlOut, 0);
                        isLevel3Display = false;
                        delay += 200;
                    }
                    //第2级菜单显示，转出去
                    AnimationUtil.rotateOut(rlMiddle, delay);
                } else {
                    //如果第2级菜单隐藏，转进来
                    AnimationUtil.rotateIn(rlMiddle,0);
                }
                isLevel2Display = !isLevel2Display;
                break;
            case R.id.ibtn_menu:
                if (isLevel3Display) {
                    //如果第三级菜单显示，转出去
                    AnimationUtil.rotateOut(rlOut, 0);
                } else {
                    //如果第三级菜单隐藏，转进来
                    AnimationUtil.rotateIn(rlOut,0);
                }
                isLevel3Display = !isLevel3Display;
                break;
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (AnimationUtil.animationCount > 0) {
            return true;
        }
        if (event.getKeyCode() == KeyEvent.KEYCODE_MENU) {
            if (isLevel1Display) {
                int delay=0;
                if (isLevel3Display) {
                    //如果第三级菜单显示，转出去
                    AnimationUtil.rotateOut(rlOut, 0);
                    isLevel3Display = false;
                    delay+=200;
                }

                if (isLevel2Display) {
                    //如果第二级菜单显示，转出去
                    AnimationUtil.rotateOut(rlMiddle, delay);//第二级菜单也转出去
                    isLevel2Display = false;
                    delay+=200;
                }

                AnimationUtil.rotateOut(rlInner, delay);//第一级菜单转出去
                isLevel1Display = false;
            } else {
                //顺时针转进来
                AnimationUtil.rotateIn(rlInner, 0);
                AnimationUtil.rotateIn(rlMiddle, 200);
                AnimationUtil.rotateIn(rlOut, 400);
                isLevel1Display = isLevel2Display = isLevel3Display = true;
            }
            return true;//消费了事件
        }
        return super.onKeyDown(keyCode, event);
    }
}
