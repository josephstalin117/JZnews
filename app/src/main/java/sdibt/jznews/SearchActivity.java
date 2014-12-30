package sdibt.jznews;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;


public class SearchActivity extends ActionBarActivity {

    RadioButton searchRadioTitle;
    RadioButton searchRadioAuthor;
    RadioButton searchRadioIssue;
    RadioGroup searchGroupMethod;
    Button seachButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initRadioView();
        initButtonView();

    }

    private void initRadioView() {
        //register the radio button
        searchRadioTitle = (RadioButton) findViewById(R.id.search_radio_title);
        searchRadioAuthor = (RadioButton) findViewById(R.id.search_radio_author);
        searchRadioIssue = (RadioButton) findViewById(R.id.search_radio_issue);

        searchGroupMethod = (RadioGroup) findViewById(R.id.search_radio_group);

    }

    private void initButtonView() {
        //register the button
        seachButton = (Button) findViewById(R.id.search_button);
        seachButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText searchEditText = (EditText) findViewById(R.id.search_text);
                String searchTarget = searchEditText.getText().toString();
                char searchMethod = '0';
                switch (searchGroupMethod.getCheckedRadioButtonId()) {
                    case R.id.search_radio_title:
                        searchMethod = 't';
                        break;
                    case R.id.search_radio_author:
                        searchMethod = 'a';
                        break;
                    case R.id.search_radio_issue:
                        searchMethod = 'i';
                        break;
                    default:
                        break;
                }

                if (searchTarget.length() == 0) {
                    Toast.makeText(SearchActivity.this, "请输入您需要搜索的内容", Toast.LENGTH_SHORT).show();
                } else {
                    Log.i("内容", searchTarget);
                    Log.i("目标", String.valueOf(searchMethod));
                    Intent intent = new Intent(v.getContext(), SearchListActivity.class);
                    intent.putExtra("search_target", searchTarget);
                    intent.putExtra("search_method", searchMethod);
                    v.getContext().startActivity(intent);
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_back:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
