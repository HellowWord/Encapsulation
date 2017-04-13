package com.hellow.xuefeng.hwlibrary.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.hellow.xuefeng.hwlibrary.debug.L;
import com.hellow.xuefeng.hwlibrary.dialog.BaseDialog;
import com.zhy.autolayout.AutoLayoutActivity;

import java.lang.ref.WeakReference;
import java.util.Stack;

import butterknife.ButterKnife;
import butterknife.Unbinder;




/**
 * @FileName: com.hiersun.dmbase.activity.AbsBaseActivity.java
 * @author: daincly
 * @date: 2014-9-16 15:16
 * @description:
 */
public abstract class AbsBaseActivity extends AutoLayoutActivity {
    private static final String TAG = "BaseActivity";
    public static final int NO_LAYOUT = 0;
    private static Stack<AbsBaseActivity> mActivities;
    private View contentView;
    private UpDateWindow mUpDateWindow;
    private Unbinder mUnbinder;


    /*
     *
     * 由@InjectView变为了@Bind；
     * ButterKnife.Inject()变为了ButterKnife.Bind()；
     * @InjectResourcesf()分为了@BindString(), @BindColor(), @BindDimen等。
     */

    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        if (mActivities == null) {
            mActivities = new Stack<AbsBaseActivity>();
        }
        mActivities.push(this);
        befInit();
        if (getUpdateWindowID() != NO_LAYOUT) {
            mUpDateWindow = new UpDateWindow(this);
        }
        int id = getLayoutID();
        if (id != NO_LAYOUT) {
            contentView = getLayoutInflater().inflate(id, null);
            mUnbinder = ButterKnife.bind(this, contentView);
            setContentView(contentView);
            init(savedInstanceState, contentView);
        } else {
            L.e("contentView is Null!");
        }

    }

    protected void befInit() {

    }

    protected AbsBaseFragment findFragmentById(int id) {
        return (AbsBaseFragment) getSupportFragmentManager().findFragmentById(id);
    }

    /**
     * 获取当前Application的context
     *
     * @return context
     */
    public Context getContext() {
        return getApplicationContext();
    }

    @Override
    protected void onDestroy() {
        mUnbinder.unbind();
        if (mActivities != null) {
            mActivities.remove(this);
        }
        super.onDestroy();
    }


    /**
     * 获取当前activity 对应的layout R.layout id
     *
     * @return id
     */
    protected abstract int getLayoutID();

    protected abstract void init(Bundle savedInstanceState, View contentView);

    protected String getTag() {
        return TAG;
    }

    protected int getUpdateWindowID() {
        return 0;
    }

    protected void initUpdateWindow(View view) {

    }

    public void showUpdateWindow() {
        if (mUpDateWindow != null) {
            mUpDateWindow.show(getSupportFragmentManager(), "_updateWindow");
        }
    }

    public void closeUpdateWindow() {
        if (mUpDateWindow != null) {
            try{
                mUpDateWindow.dismiss();
            }catch (Exception e){
                Log.e("TAG"," "+e.getMessage());
            }
        }
    }

    public void closeUpdateWindowLoss() {
        if (mUpDateWindow != null) {
            mUpDateWindow.dismissAllowingStateLoss();
        }
    }

    public boolean updateWindowIsShowing() {
        if (mUpDateWindow == null) {
            return false;
        }
        return mUpDateWindow.isShowing();
    }

    public void showDialog(BaseDialog dialog, String tag) {
        dialog.show(getSupportFragmentManager(), tag);
    }



    public static class UpDateWindow extends BaseDialog {
        private WeakReference<AbsBaseActivity> mReference;

        public UpDateWindow() {
        }

        public UpDateWindow(AbsBaseActivity activity) {
            super();
            mReference = new WeakReference<AbsBaseActivity>(activity);
        }

        @Override
        protected boolean cancelable() {
            return false;
        }

        @Override
        protected int getLayoutID() {
            return mReference.get().getUpdateWindowID();
        }

        @Override
        protected void init(Bundle savedInstanceState, View content) {
            mReference.get().initUpdateWindow(content);
        }
    }


    //=====================Activity  管理

    /**
     * 关闭所有指定tag的activity
     * activity 的 tag 可重载getTAG进行设置;
     *
     * @param tag 需要关闭的tag值
     */
    public void finishActivities(String tag) {
        if (mActivities.empty()) {
            return;
        }
        Stack<AbsBaseActivity> temp = new Stack();
        for (AbsBaseActivity activity : mActivities) {
            if(activity!=null&&activity.getTag().equals(tag)){
                temp.push(activity);
            }
        }
        for(AbsBaseActivity activity:temp){
            mActivities.remove(activity);
            activity.finish();
        }
        temp = null;

    }


    /**
     * 返回指定的Activity
     * @param cls 返回的具体activity
     */
    @Deprecated
    public void backToActivity(Class<? extends AbsBaseActivity> cls){
        Intent intent = new Intent(this, cls);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }


    /**
     * 关闭客户端
     *
     * @param isAppExit 标示要不要退出app
     *                  true 标示退出app
     *                  false 标示清空activity堆栈
     */
    public void exit(boolean isAppExit) {
        if (mActivities.empty()) {
            return;
        }
        for (AbsBaseActivity activity : mActivities) {
            if (activity != null && !activity.isFinishing())
                activity.finish();
        }
        mActivities.clear();
        if (isAppExit) {
            System.exit(0);
        }
    }

}
