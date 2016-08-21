# YoukuMenu
1.目录结构
2.MainActivity.java
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

3.AnimationUtil.java
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

4.activity_main.xml
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/activity_main"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context="com.weizh.a02_.MainActivity">

    <RelativeLayout
        android:id="@+id/rl_inner"
        android:layout_width="120dp"
        android:layout_height="60dp"
        android:layout_alignParentBottom="true"
        android:layout_centerHorizontal="true"
        android:background="@drawable/level1">
        <ImageButton
            android:id="@+id/ibtn_home"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:src="@drawable/icon_home"
            android:background="@android:color/transparent"
            android:paddingBottom="7dp"
            android:layout_centerInParent="true"
            />
    </RelativeLayout>

    <RelativeLayout
        android:id="@+id/rl_middle"
        android:layout_width="220dp"
        android:layout_height="110dp"
        android:layout_alignParentBottom="true"
        android:layout_centerHorizontal="true"
        android:background="@drawable/level2">
        <ImageButton
            android:id="@+id/ibtn_menu"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:src="@drawable/icon_menu"
            android:layout_marginTop="3dp"
            android:background="@android:color/transparent"
            android:layout_centerHorizontal="true"
            />
        <ImageButton
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:src="@drawable/icon_search"
            android:background="@android:color/transparent"
            android:layout_alignParentBottom="true"
            android:layout_marginLeft="10dp"
            android:layout_marginBottom="10dp"
            />
        <ImageButton
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:src="@drawable/icon_myyouku"
            android:background="@android:color/transparent"
            android:layout_alignParentBottom="true"
            android:layout_alignParentRight="true"
            android:layout_marginRight="10dp"
            android:layout_marginBottom="10dp"
            />
    </RelativeLayout>

    <RelativeLayout
        android:id="@+id/rl_out"
        android:layout_width="340dp"
        android:layout_height="170dp"
        android:background="@drawable/level3"
        android:layout_alignParentBottom="true"
        android:layout_centerHorizontal="true">

        <ImageButton
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:src="@drawable/channel1"
            android:background="@android:color/transparent"
            android:layout_marginLeft="11dp"
            android:layout_alignParentBottom="true"
            android:layout_alignParentLeft="true"
             />
        <ImageButton
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:src="@drawable/channel2"
            android:background="@android:color/transparent"
            android:layout_marginLeft="35dp"
            android:layout_marginBottom="55dp"
            android:layout_alignParentBottom="true"
            android:layout_alignParentLeft="true"
             />
        <ImageButton
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:src="@drawable/channel3"
            android:background="@android:color/transparent"
            android:layout_marginLeft="75dp"
            android:layout_marginBottom="100dp"
            android:layout_alignParentBottom="true"
            android:layout_alignParentLeft="true"
             />
        <ImageButton
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:src="@drawable/channel4"
            android:background="@android:color/transparent"
            android:layout_marginTop="5dp"
            android:layout_centerHorizontal="true"
             />
        <ImageButton
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:src="@drawable/channel5"
            android:background="@android:color/transparent"
            android:layout_marginTop="25dp"
            android:layout_alignParentRight="true"
            android:layout_marginRight="75dp"
             />
        <ImageButton
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:src="@drawable/channel6"
            android:background="@android:color/transparent"
            android:layout_marginRight="35dp"
            android:layout_marginBottom="55dp"
            android:layout_alignParentBottom="true"
            android:layout_alignParentRight="true"
            />
        <ImageButton
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:src="@drawable/channel7"
            android:background="@android:color/transparent"
            android:layout_marginRight="11dp"
            android:layout_alignParentBottom="true"
            android:layout_alignParentRight="true"
            />

    </RelativeLayout>

</RelativeLayout>

5.效果图


源码地址：https://github.com/Jszgw/YoukuMenu
