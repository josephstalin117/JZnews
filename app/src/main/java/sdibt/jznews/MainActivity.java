package sdibt.jznews;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import sdibt.jznews.entity.News;
import sdibt.jznews.dao.NewsDaoImpl;
import sdibt.jznews.adapter.NewsListViewAdapter;


public class MainActivity extends ActionBarActivity {

    private long exitTime = 0;
    private ProgressBar circle_ProgressBar;
    // 构造一个存储列表项内容的ArrayList<>结构
    public static List<News> newsDataList = new ArrayList<News>();
    private ListView listView;
    private NewsListViewAdapter newsListViewAdapter;
    private NewsDaoImpl ndi;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //initialize the view
        initView();
        //initialize the data
        if (isOnline()) {
            downloadRun.start();
        } else {
            Toast.makeText(getApplicationContext(), "貌似您的网络连接不好，请检查网络连接,并重启应用", Toast.LENGTH_SHORT).show();
//            finish();
//            System.exit(0);
        }
    }


    /**
     * 下载线程
     */
    Thread downloadRun = new Thread(new Runnable() {
        @Override
        public void run() {
            ndi = new NewsDaoImpl();
            newsDataList = ndi.getLatestNews();
            if (newsDataList.size() != 0) {
                Message msg = new Message();
                msg.what = 1;
                mHandler.sendMessage(msg);
            }
        }
    });

    //recive the thread's message
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                circle_ProgressBar.setVisibility(View.GONE);
                listView.setVisibility(View.VISIBLE);
                initList();
                downloadRun.interrupt();
            }
        }
    };

    /**
     * 初始化新闻View
     */
    private void initView() {
        circle_ProgressBar = (ProgressBar) findViewById(R.id.mainCircleProgressBar);
        listView = (ListView) findViewById(R.id.listView);
        circle_ProgressBar.setVisibility(View.VISIBLE);
        listView.setVisibility(View.GONE);


    }

    private void initList() {
        newsListViewAdapter = new NewsListViewAdapter(this, newsDataList, R.layout.news_list_item);
        //load the adapter
        listView.setAdapter(newsListViewAdapter);
        //绑定点击事件
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(view.getContext(), ContentActivity.class);
                News news = MainActivity.newsDataList.get(position);
                intent.putExtra("news_rectno", news.getRectno());
                view.getContext().startActivity(intent);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_search:
                Intent intent = new Intent();
                intent.setClass(this, SearchActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {

            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

}
