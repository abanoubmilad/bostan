package abanoubm.ksakolyom;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class SearchDates extends AppCompatActivity {
    private StoryDisplayListAdapter mAdapter;
    private DB mDB;
    private static final String ARG_ID = "id";
    private String targetDay;

    private class SearchTask extends
            AsyncTask<Void, Void, ArrayList<Story>> {
        private ProgressDialog pBar;

        @Override
        protected void onPreExecute() {
            pBar = new ProgressDialog(SearchDates.this);
            pBar.setCancelable(false);
            pBar.show();
        }

        @Override
        protected ArrayList<Story> doInBackground(Void... params) {
            if (mDB == null)
                mDB = DB.getInstant(getApplicationContext());
            return mDB.searchDates(targetDay);
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
        setContentView(R.layout.act_search_dates);

        ((TextView) findViewById(R.id.subhead)).setText(getResources().getString(R.string.sub_search_dates));

        final TextView date = (TextView) findViewById(R.id.date);
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

        Calendar cal = Calendar.getInstance();
        final DatePickerDialog picker_date = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        targetDay = Utility.produceDate(dayOfMonth, monthOfYear + 1, year);
                        date.setText(targetDay);
                        new SearchTask().execute();

                    }

                }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH));

        findViewById(R.id.pick_date)
                .setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        picker_date.show();

                    }
                });
    }

}
