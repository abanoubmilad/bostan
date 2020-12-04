package abanoubm.bostan;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class Search extends Fragment {
    private EditText et;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.frag_search, container, false);


        et = (EditText) root.findViewById(R.id.sa_edittext);
        TextView search = (TextView) root.findViewById(R.id.sa_iv);

        search.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                String input = et.getText().toString().trim();
                if (input.length() < 2) {
                    Toast.makeText(getActivity(),
                            " قم بادخال كلمة أو جملة للبحث ", Toast.LENGTH_LONG)
                            .show();
                } else {
                    new SearchTask().execute(input);
                }
            }
        });
        return root;
    }

    private class SearchTask extends AsyncTask<String, Void, Boolean> {
        private ProgressDialog pBar;

        @Override
        protected void onPreExecute() {
            pBar = new ProgressDialog(getActivity());
            pBar.setCancelable(false);
            pBar.show();
        }

        @Override
        protected Boolean doInBackground(String... params) {
            try {
                BostanInfo.searchResults = DB.getInstance(
                        getActivity()).search(params[0]);
                return true;
            } catch (Exception e) {
                return false;
            }

        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (result)
                startActivity(new Intent(getActivity(),
                        DisplaySearchResults.class));
            else
                Toast.makeText(getActivity(), R.string.err_msg_db,
                        Toast.LENGTH_LONG).show();
            pBar.dismiss();
        }
    }
}
