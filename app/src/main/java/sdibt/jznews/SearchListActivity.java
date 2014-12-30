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
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

import sdibt.jznews.entity.News;
import sdibt.jznews.dao.NewsDaoImpl;
import sdibt.jznews.adapter.NewsListViewAdapter;


public class SearchListActivity extends ActionBarActivity {

    String searchTarget;
    char searchMethod;
    Message msg;

    TextView search_fail;
    private ProgressBar search_progressBar;
    // 构造一个存储列表项内容的ArrayList<>结构
    public static ArrayList<News> searchList;
    private ListView search_list_view;
    private NewsListViewAdapter searchListViewAdapter;
    private NewsDaoImpl ndi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_list);
        initView();
        initData();
        downloadRun.start();
    }


    private void initView() {
        search_progressBar = (ProgressBar) findViewById(R.id.search_progressBar);
        search_list_view = (ListView) findViewById(R.id.search_list_view);
        search_fail = (TextView) findViewById(R.id.search_fail);
        search_fail.setVisibility(View.GONE);
        search_progressBar.setVisibility(View.VISIBLE);
        search_list_view.setVisibility(View.GONE);
    }

    private void initData() {
        //get the way of the search
        searchList = new ArrayList<News>();
        Intent intent = getIntent();
        Bundle data = intent.getExtras();
        searchTarget = data.getString("search_target");
        searchMethod = data.getChar("search_method");
        Log.i("内容", searchTarget);
        Log.i("方法", String.valueOf(searchMethod));
    }

    private void initList() {
        searchListViewAdapter = new NewsListViewAdapter(this, searchList, R.layout.news_list_item);
        //load the adapter
        search_list_view.setAdapter(searchListViewAdapter);
        search_list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(view.getContext(), ContentActivity.class);

                News news = SearchListActivity.searchList.get(position);
                intent.putExtra("news_rectno", news.getRectno());
                view.getContext().startActivity(intent);
            }
        });
    }

    /**
     * download thread
     */
    Thread downloadRun = new Thread(new Runnable() {
        @Override
        public void run() {
            ndi = new NewsDaoImpl();
            try {
                switch (searchMethod) {
                    case 'i':
                        searchList = ndi.getAllNewsRectnoByIssue(searchTarget);
                        break;
                    case 'a':
                        //@Todo
                        searchList = ndi.getNewsByAuthor(searchTarget);
                        Log.i("searchTarget", searchTarget);
                        Log.i("show data of array", searchList.toString());
                        break;
                    case 't':
                        //@Todo can't get the array
                        searchList = ndi.getNewsByTitle(searchTarget);
                        break;
                    default:
                        throw new Exception();
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.err.println("err method");
            }

            if (searchList != null) {
                msg = new Message();
                msg.what = 1;
                mHandler.sendMessage(msg);
            } else {
                msg = new Message();
                msg.what = 0;
                mHandler.sendMessage(msg);
            }
        }
    });

    //recive the thread's message
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                search_progressBar.setVisibility(View.GONE);
                search_list_view.setVisibility(View.VISIBLE);
                initList();
                downloadRun.interrupt();
            } else {
                search_progressBar.setVisibility(View.GONE);
                search_fail.setVisibility(View.VISIBLE);
            }
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search_list, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_list_back:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
