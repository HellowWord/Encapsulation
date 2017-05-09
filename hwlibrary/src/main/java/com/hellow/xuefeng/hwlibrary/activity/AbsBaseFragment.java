package com.hellow.xuefeng.hwlibrary.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hellow.xuefeng.hwlibrary.dialog.BaseDialog;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @FileName: com.hiersun.dmbase.activity.AbsBaseFragment.java
 * @author: daincly
 * @date: 2014-9-16 15:19
 * @description:
 */
public abstract class AbsBaseFragment extends Fragment {
    private static final String TAG = "AbsBaseFragment";
    public static final int NO_LAYOUT = 0;

    private View mContentView;
    private Unbinder mUnbinder;


    public Context getContext() {
        return ((AbsBaseActivity) getActivity()).getContext();
    }

    protected AbsBaseActivity getBaseActivity(){
        return (AbsBaseActivity)getActivity();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContentView = inflater.inflate(getLayoutID(), null, false);
        mUnbinder = ButterKnife.bind(this, mContentView);
        return mContentView;
    }

    /**
     * Fragment 初始化
     *
     * @param savedInstanceState 状态存储Bundle 源生
     * @param contentView        当前fragment的布局View
     */
    protected abstract void init(Bundle savedInstanceState, View contentView);

    protected View getContentView(){
        return mContentView;
    }

    protected View findViewById(int id){
        return mContentView.findViewById(id);
    }
    protected AbsBaseFragment findActivityFragmentById(int id) {
        return (AbsBaseFragment) getActivity().getSupportFragmentManager().findFragmentById(id);
    }

    protected AbsBaseFragment findChildFragmentById(int id){
        return (AbsBaseFragment) getChildFragmentManager().findFragmentById(id);
    }

    protected AbsBaseFragment findParentFragment(){


        return (AbsBaseFragment)getParentFragment();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init(savedInstanceState, mContentView);
    }

    @Override
    public void onDestroyView() {
        if (mUnbinder!=null){
            mUnbinder.unbind();
        }
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }


    @Override
    public void onResume() {
        super.onResume();
    }


    /**
     * 获取当前Activity 的布局文件ID
     *
     * @return 布局文件ID
     */
    protected abstract int getLayoutID();

    protected void showDialog(BaseDialog dialog, String tag) {
        dialog.show(getActivity().getSupportFragmentManager(), tag);
    }

    protected BaseDialog getDialog(String tag) {
        Fragment fragment = getActivity().getSupportFragmentManager().getFragment(null, tag);
        return fragment instanceof BaseDialog ? (BaseDialog) fragment : null;
    }

    protected String getTitle(){
        return null;
    }

}
