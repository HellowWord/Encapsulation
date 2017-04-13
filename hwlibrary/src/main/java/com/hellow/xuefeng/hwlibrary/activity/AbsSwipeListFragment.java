package com.hellow.xuefeng.hwlibrary.activity;

/**
 * Created by xuefeng_mac on 16/7/1.
 */
public abstract class AbsSwipeListFragment extends AbsBaseListFragment{

//    private final static String TAG = "AbsSwipeListFragment";
//
//
//
//    private ListView mListView;
//
//    private SwipeToLoadLayout mSwipeToLoadLayout;
//
//    private PullOnEvent mPullOnEvent;
//
//
//    @Override
//    protected int getHeaderLayoutID() {
//        return 0;
//    }
//
//    @Override
//    protected int getFooterLayoutID() {
//        return 0;
//    }
//
//    @Override
//    protected void initContent(Bundle savedInstanceState, View contentView) {
//        super.initContent(savedInstanceState, contentView);
////        mListView = (SimplePullListView) contentView;
//        mListView = (ListView) getList(contentView);
//        mSwipeToLoadLayout=(SwipeToLoadLayout) getSwipe(contentView);
//        mPullOnEvent=new PullOnEvent();
//        mSwipeToLoadLayout.setOnRefreshListener(mPullOnEvent);
//        mSwipeToLoadLayout.setOnLoadMoreListener(mPullOnEvent);
//    }
//
////    protected abstract View getList(View contentView);
//
//
//    protected abstract void onRefresh();
//
//    protected abstract void onLoadMore();
//
//    protected abstract View getSwipe(View contentView);
//
//
//    public ListView getListView(){
//        return mListView;
//    }
//
//
//    public void setSelection(int position) {
//        if (position > mListView.getCount()) {
//            return;
//        }
//        mListView.setSelection(position);
//    }
//
//
//    public class PullOnEvent implements OnRefreshListener, OnLoadMoreListener {
//        @Override
//        public void onRefresh() {
//            mSwipeToLoadLayout.setRefreshing(false);
//            AbsSwipeListFragment.this.onRefresh();
//        }
//
//        @Override
//        public void onLoadMore() {
//
//            AbsSwipeListFragment.this.onLoadMore();
//        }
//    }
}
