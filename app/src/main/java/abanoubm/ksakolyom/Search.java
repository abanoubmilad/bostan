package abanoubm.ksakolyom;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Search extends AppCompatActivity {
    private StoryDisplayListAdapter mAdapter;
    private DB mDB;
    private EditText input;
    private static final String ARG_ID = "id";


    private class SearchTask extends
            AsyncTask<String, Void, ArrayList<Story>> {
        private ProgressDialog pBar;

        @Override
        protected void onPreExecute() {
            pBar = new ProgressDialog(Search.this);
            pBar.setCancelable(false);
            pBar.show();
        }

        @Override
        protected ArrayList<Story> doInBackground(String... params) {
            if (mDB == null)
                mDB = DB.getInstant(getApplicationContext());
            return mDB.searchStories(params[0]);
        }

        @Override
        protected void onPostExecute(ArrayList<Story> result) {
            mAdapter.clearThenAddAll(result);
            if (result.size() == 0)
                Toast.makeText(getApplicationContext(),
                        R.string.msg_no_results, Toast.LENGTH_SHORT).show();
            pBar.dismiss();

        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_search);
        ((TextView) findViewById(R.id.subhead)).setText(R.string.subhead_search);

        input = (EditText) findViewById(R.id.input);
        ListView lv = (ListView) findViewById(R.id.list);
        mAdapter = new StoryDisplayListAdapter(getApplicationContext(), new ArrayList<Story>(0));
        lv.setAdapter(mAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View arg1,
                                    int position, long arg3) {

                startActivity(new Intent(getApplicationContext(),
                        DisplayStory.class).putExtra(ARG_ID, mAdapter.getItem(position).getId()));

            }
        });
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SearchTask().execute(input.getText().toString().trim());

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (DB.getInstant(getApplicationContext()).isDirty()) {
            new SearchTask().execute(input.getText().toString().trim());
            DB.getInstant(getApplicationContext()).clearDirty();
        }
    }

}
