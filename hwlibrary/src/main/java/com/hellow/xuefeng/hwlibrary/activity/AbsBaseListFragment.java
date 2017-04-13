package com.hellow.xuefeng.hwlibrary.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.hellow.xuefeng.hwlibrary.debug.L;

import java.util.ArrayList;
import java.util.List;


/**
 * @FileName: com.hiersun.dmbase.activity.AbsBaseListFragment.java
 * @author: daincly
 * @date: 2014-9-30 10:19
 * @description:
 */
public abstract class AbsBaseListFragment extends AbsPolyBaseFragment {
    private static final String TAG = "AbsBaseListFragment";
    private ItemListener mItemListener;

    /**
     * 设置ListView header 布局
     *
     * @return 布局ID
     */
    protected abstract int getHeaderLayoutID();

    /**
     * 设置ListView footer 布局
     *
     * @return 布局ID
     */

    protected abstract int getFooterLayoutID();

    /**
     * ListView header 的初始化方法
     * 如果没有设置header布局 此方法不会被调用
     *
     * @param header header 的 view
     */
    protected void initHeader(View header) {
    }

    /**
     * ListView footer 的初始化方法
     * 如果没有设置footer布局 此方法不会被调用
     *
     * @param footer
     */
    protected void initFooter(View footer) {
    }


    /**
     * 单一类型列表不用重写此方法.多类型列表项需要重写此方法.
     * 根据position返回item类型.
     * 参见getAbsViewTypeCount();
     *
     * @param position 当前List的position
     * @return 返回列表类型 需要自定义.
     */
    protected int getAbsItemViewType(int position) {
        return 0;
    }

    /**
     * 单一类型列表不用重写此方法.多类型列表项需要重写此方法.
     * 设置ListView Item 的类型总数
     * 参见getAbsItemViewType(int position);
     *
     * @return 返回item 类型总数
     */
    protected int getAbsViewTypeCount() {
        return 1;
    }

    /**
     * 设置 ListView Item 的实体
     * 单一类型列表不用关注type,多类型列表项需要根据类型返回不同类型的实体.
     *
     * @return 返回的实体需要继承AbsListItem.
     */
    protected abstract AbsListItem getListItem(int type);

    protected abstract View getList(View contentView);


//    protected abstract AbsAdapterItem getAbsAdapterItem();


    private ListAdapter mListAdapter;
    private AdapterView mListView;

    @Override
    protected void initContent(Bundle savedInstanceState, View contentView) {
//        mListView = (AdapterView)contentView;
        mListView= (AdapterView) getList(contentView);
        if (mListView instanceof  ListView) {
            if (getHeaderLayoutID() != NO_LAYOUT) {
                View headerView = LayoutInflater.from(getContext()).inflate(getHeaderLayoutID(), null, false);
                initHeader(headerView);
                ((ListView)mListView).addHeaderView(headerView);
            }

            if (getFooterLayoutID() != NO_LAYOUT) {
                View footerView = LayoutInflater.from(getContext()).inflate(getFooterLayoutID(), null, false);
                initFooter(footerView);
                ((ListView)mListView).addFooterView(footerView);
            }
        }
        mListAdapter = new ListAdapter();
        if (mListView==null){
            L.e("hahahahhaha");
        }
        mListView.setAdapter(mListAdapter);
        mItemListener = new ItemListener();
        mListView.setOnItemClickListener(mItemListener);
        mListView.setOnItemLongClickListener(mItemListener);
        init(savedInstanceState);

    }

    protected abstract void init(Bundle savedInstanceState);

    /**
     * 设置ListView 数据
     * 初始化或者更新加载数据
     * 注意 翻页或者下拉刷新时 需要调用此方法
     *
     * @param list 数据
     */
    public void setData(List list) {
        if (mListAdapter == null) {
            L.e("setData mListAdapter is null!");
            return;
        }
        if (list == null) {
            L.e("setData list is null!");
            return;
        }
        mListAdapter.setList(list);
        upDate();
    }

    /**
     * ListView的数据条数
     * @return 数据条数
     */
    public int getCount(){
        if (mListAdapter == null) {
            L.e("getCount mListAdapter is null!");
            return 0;
        }
        return mListAdapter.getCount();
    }

    /**
     * 向ListView中添加单条数据
     * @param index 位置
     * @param obj 数据
     */
    public void addData(int index,Object obj){
        if (mListAdapter == null) {
            L.e("addData mListAdapter is null!");
            return;
        }
        mListAdapter.addData(index, obj);
        upDate();
    }

    public int getPageCount(){
        if (mListAdapter == null) {
            L.e("addData mListAdapter is null!");
            return 0;
        }
        return mListAdapter.addCount();

    }

    /**
     * 更新ListView 数据
     * 保留原数据添加数据时使用,一般为翻页
     *
     * @param list 数据
     */
    public void addData(List list) {
        if (mListAdapter == null) {
            L.e("addData mListAdapter is null!");
            return;
        }
        if (list == null) {
            L.e("addData list is null!");
            return;
        }
        mListAdapter.addList(list);
        upDate();
    }

    /**
     * 移除ListView的单条数据
     * @param obj 数据
     */
    public void removeData(Object obj){
        if (mListAdapter == null) {
            L.e("removeData mListAdapter is null!");
            return;
        }
        mListAdapter.removeData(obj);
        upDate();
    }

    /**
     * 返回ListView 当前数据
     *
     * @return list 数据
     */
    public List getData() {
        if (mListAdapter == null) {
            L.e("getData mListAdapter is null!");
            return null;
        }
        if (mListAdapter.mList == null) {
            L.e("getData mListAdapter.mList is null!");
            return null;
        }
        return mListAdapter.mList;
    }


    /**
     * 更新ListView adapter
     */
    public void upDate() {
        if (mListAdapter == null) {
            L.e("upDate mListAdapter is null!");
            return;
        }
        if (getData() == null || getData().isEmpty()) {
            setCurrentViewStatus(FRAGMENT_STATUS_EMPTY);
        } else {
            setCurrentViewStatus(FRAGMENT_STATUS_CONTENT);
        }
        mListAdapter.notifyDataSetChanged();

    }


    private class ListAdapter extends BaseAdapter {

        private List mList;

        private int mAddCount;

        private ListAdapter() {
            mList = new ArrayList();
        }

        private void setList(List list) {
            mAddCount = 0;
            mList = list;
        }

        private void addList(List list) {
            mList.addAll(list);
            mAddCount++;
        }

        private int addCount(){
            return mAddCount;
        }


        @Override
        public int getCount() {
            return mList.size();
        }

        private void addData(int index,Object obj){
            mList.add(index,obj);
        }

        private void removeData(Object obj){
            mList.remove(obj);
        }


        @Override
        public Object getItem(int position) {
            if (position > getCount() - 1) {
                return null;
            }

            return mList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }


        @Override
        public int getItemViewType(int position) {

            return getAbsItemViewType(position);
        }


        @Override
        public int getViewTypeCount() {
            return getAbsViewTypeCount();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            AbsListItem item = null;
            if(convertView!=null&&convertView.getTag()!=null&&convertView.getTag() instanceof AbsListItem){
                item = (AbsListItem) convertView.getTag();
            }else{
                int type = getItemViewType(position);
                item = getListItem(type);
                convertView = LayoutInflater.from(getContext()).inflate(item.getItemLayout(), null, false);
                item.init(convertView);
                convertView.setTag(item);
            }
            item.bindData(getItem(position));
            return convertView;

        }
    }

    public static abstract class AbsListItem<D> {

        public abstract int getItemLayout();

        public abstract void init(View contentView);

        public abstract void bindData(D t);

    }

    protected void onItemClick(AbsListItem listItem, int position) {
    }

    protected void onItemLongClick(AbsListItem listItem, int position) {
    }

    private class ItemListener implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

        public ItemListener() {
            super();
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            AbsListItem item = (AbsListItem) view.getTag();
            AbsBaseListFragment.this.onItemClick(item, position);
        }

        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
            AbsListItem item = (AbsListItem) view.getTag();
            AbsBaseListFragment.this.onItemLongClick(item, position);
            return true;
        }
    }

}
