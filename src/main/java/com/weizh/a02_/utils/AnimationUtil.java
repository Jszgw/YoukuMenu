package com.weizh.a02_.utils;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.RelativeLayout;

import static android.R.attr.animation;

/**
 * Created by weizh_000 on 2016/8/20.
 */
public class AnimationUtil {
    public static int animationCount = 0;

    //逆时针旋转出去
    public static void rotateOut(RelativeLayout rl, int delay) {
        int count = rl.getChildCount();
        for (int i=0;i<count;i++ ){
            rl.getChildAt(i).setEnabled(false);//禁用掉子view，让子view转出去后不可点击，弥补补间动画缺点
        }


        RotateAnimation animation = new RotateAnimation(0f, -180f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 1f);//相对于自身，逆时针旋转180度
        animation.setDuration(500);//设置执行时间
        animation.setFillAfter(true);//动画执行完毕，定格在执行完毕位置
        animation.setStartOffset(delay);//延时执行
//        rl.setAnimation(rotateAnimation);
//        rotateAnimation.startNow();
        rl.startAnimation(animation);//开启动画
        animation.setAnimationListener(new MyAnimationListener());
    }

    //顺时针旋转进来
    public static void rotateIn(RelativeLayout rl,int delay) {
        int count = rl.getChildCount();
        for (int i=0;i<count;i++ ){
            rl.getChildAt(i).setEnabled(true);//使子view可用，弥补补间动画缺点
        }

        RotateAnimation animation = new RotateAnimation(-180f, 0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 1f);//相对于自身，顺时针旋转180度
        animation.setDuration(500);//设置执行时间
        animation.setFillAfter(true);//动画执行完毕，定格在执行完毕位置
        animation.setStartOffset(delay);//延时执行
//        rl.setAnimation(rotateAnimation);
//        rotateAnimation.startNow();
        rl.startAnimation(animation);//开启动画
        animation.setAnimationListener(new MyAnimationListener());
    }

    static class MyAnimationListener implements Animation.AnimationListener {

        @Override
        public void onAnimationStart(Animation animation) {
            animationCount++;
        }

        @Override
        public void onAnimationEnd(Animation animation) {
            animationCount--;
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }
}
