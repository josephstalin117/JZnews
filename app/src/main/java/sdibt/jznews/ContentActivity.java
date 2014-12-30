package sdibt.jznews;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import sdibt.jznews.entity.News;
import sdibt.jznews.dao.NewsDaoImpl;

public class ContentActivity extends ActionBarActivity {

    private ProgressBar circle_ProgressBar;
    private TextView news_title;
    private TextView news_content;
    private News news;
    NewsDaoImpl ndi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        this.initView();
        //initialize the data
        downloadRun.start();
    }


    /**
     * 读取新闻数据并显示在新闻详细界面中
     */
    private void initData() {
        ndi = new NewsDaoImpl();
        Intent intent = getIntent();
        Bundle data = intent.getExtras();
        String newsRectno = data.getString("news_rectno");
        //get the new's content
        news = ndi.getNewsByRestno(newsRectno);
        Log.i("新闻的rectno", news.getRectno());
    }

    /**
     * 初始化新闻详细界面
     */
    private void initView() {
        news_title = (TextView) findViewById(R.id.news_title);
        news_content = (TextView) findViewById(R.id.news_content);
        circle_ProgressBar = (ProgressBar) findViewById(R.id.circleProgressBar);
        circle_ProgressBar.setVisibility(View.VISIBLE);
        news_title.setVisibility(View.GONE);
        news_content.setVisibility(View.GONE);
    }


    /**
     * 下载线程
     */
    Thread downloadRun = new Thread(new Runnable() {
        @Override
        public void run() {
            //initialize the data
            initData();
            if (news.getContent() != "") {
                Message msg = new Message();
                msg.what = 1;
                mHandler.sendMessage(msg);
            }
        }
    });


    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                circle_ProgressBar.setVisibility(View.GONE);
                downloadRun.interrupt();
                news_title.setVisibility(View.VISIBLE);
                news_content.setVisibility(View.VISIBLE);
                news_title.setText(news.getTitle());
                news_content.setText(news.getContent());
            }
        }
    };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_content, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_content_back:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
