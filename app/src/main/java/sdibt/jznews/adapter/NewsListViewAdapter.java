package sdibt.jznews.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import sdibt.jznews.R;
import sdibt.jznews.entity.News;

/**
 * Created by stalin on 12/17/2014.
 */
public class NewsListViewAdapter extends BaseAdapter {

    // 运行上下文
    private Context context;
    // 父列表视图容器
    private LayoutInflater listContainer;
    // 新闻数据集合
    private List<News> newsDataList;
    // 列表项布局
    private int itemViewResource;

    // 列表项组件集合(rectno,title，content)
    static class ListItemView {
        public TextView rectno;
        public TextView title;
    }

    /**
     * 根据父列表构造列表项适配器，加载列表项界面、读取并设置列表项数据
     *
     * @param context          父列表
     * @param newsDataList     列表项数据
     * @param itemViewResource 列表项界面布局
     */
    public NewsListViewAdapter(Context context, List<News> newsDataList, int itemViewResource) {
        this.context = context;
        this.listContainer = LayoutInflater.from(context);
        this.newsDataList = newsDataList;
        this.itemViewResource = itemViewResource;
    }

    public void refresh(ArrayList<News> list) {
        newsDataList = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return newsDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    /**
     * 根据位置和相关设定返回列表项
     *
     * @param position    列表项起始位置
     * @param convertView 返回的每一个列表项
     * @param parent      列表项父容器
     */
    public View getView(int position, View convertView, ViewGroup parent) {
        // 创建自定义列表项组件
        ListItemView listItemView = null;

        if (convertView == null) {

            // 获取列表项布局（this.itemViewResource--）
            convertView = listContainer.inflate(this.itemViewResource, null);
            //初始化列表项各组件(rectno、title)
            listItemView = new ListItemView();
            listItemView.title = (TextView) convertView.findViewById(R.id.news_listitem_title);
            // 设置控件集到convertView
            convertView.setTag(listItemView);
        } else {
            listItemView = (ListItemView) convertView.getTag();
        }

        //依次读取列表项数据中的第position项数据赋于news对象
        News news = newsDataList.get(position);
//        listItemView.rectno.setText(news.getRectno());
        listItemView.title.setText(news.getTitle());
        return convertView;
    }
}