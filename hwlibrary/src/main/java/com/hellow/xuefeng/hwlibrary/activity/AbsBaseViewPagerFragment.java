package com.hellow.xuefeng.hwlibrary.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.hellow.xuefeng.hwlibrary.components.viewpager.BaseViewPager;

import java.util.List;

/**
 * @FileName: com.hiersun.dmbase.activity.AbsBaseViewPagerFragment.java
 * @author: daincly
 * @date: 2015-12-08 09:25
 * @description: <功能>
 */
public abstract class AbsBaseViewPagerFragment extends AbsBaseFragment {
    private final static String TAG = "AbsBaseViewPagerFragment";
    private BaseViewPager mViewPager;
    private PagerAdapter mPagerAdapter;

    protected abstract List<AbsBaseFragment> getFragments();

    protected abstract int defCurrentIndex();




    public void setCurrentFragment(int index,boolean smoothScroll) {
        if (index < 0 || index >= mPagerAdapter.getCount()) {
            return;
        }
        mViewPager.setCurrentItem(index,smoothScroll);
    }

    public ViewPager getViewPager(){
        initViewPager();
        return mViewPager;
    }

    @Override
    protected void init(Bundle savedInstanceState, View contentView) {
       initViewPager();
    }

    private void initViewPager(){
        if(mViewPager==null){
            mViewPager = (BaseViewPager) getContentView();
            mViewPager.setCanscroll(isCanScroll());
            mPagerAdapter = new PagerAdapter(getActivity().getSupportFragmentManager(), getFragments());
            mViewPager.setAdapter(mPagerAdapter);
            mViewPager.setCurrentItem(defCurrentIndex());
        }
    }

    protected boolean isCanScroll(){
        return true;
    }

    private static class PagerAdapter extends FragmentPagerAdapter {
        List<AbsBaseFragment> mFList;


        public PagerAdapter(FragmentManager fm, List<AbsBaseFragment> list) {
            super(fm);
            mFList = list;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFList.get(position).getTitle();
        }

        @Override
        public AbsBaseFragment getItem(int position) {
            return mFList.get(position);
        }

        @Override
        public int getCount() {
            return mFList.size();
        }


    }

}
