package abanoubm.bostan;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Search extends Activity {
    private EditText et;

    private class SearchTask extends AsyncTask<String, Void, Boolean> {
        private ProgressDialog pBar;

        @Override
        protected void onPreExecute() {
            pBar = new ProgressDialog(Search.this);
            pBar.setCancelable(false);
            pBar.show();
        }

        @Override
        protected Boolean doInBackground(String... params) {
            try {
                BostanInfo.searchResults = DB.getInstance(
                        getApplicationContext()).search(params[0]);
                return true;
            } catch (Exception e) {
                return false;
            }

        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (result)
                startActivity(new Intent(getApplicationContext(),
                        DisplaySearchResults.class));
            else
                Toast.makeText(getApplicationContext(), R.string.err_msg_db,
                        Toast.LENGTH_LONG).show();
            pBar.dismiss();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        ((TextView) findViewById(R.id.subhead)).setText(R.string.searchall);

        et = (EditText) findViewById(R.id.sa_edittext);
        TextView search = (TextView) findViewById(R.id.sa_iv);

        search.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                String input = et.getText().toString().trim();
                if (input.length() < 2) {
                    Toast.makeText(getApplicationContext(),
                            " قم بادخال كلمة أو جملة للبحث ", Toast.LENGTH_LONG)
                            .show();
                } else {
                    new SearchTask().execute(input);
                }
            }
        });

    }
}
