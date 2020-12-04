package abanoubm.bostan;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Arrays;

public class CharacterChooser extends Fragment {
    private ListView lv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.frag_chooser, container, false);

        lv = (ListView) root.findViewById(R.id.list);
        lv.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View view,
                                    int position, long arg3) {
                startActivity(new Intent(getActivity(),
                        CharSectionChooser.class).putExtra("pos",
                        position));

            }
        });


        new GetTask().execute();
        return root;
    }

    private class GetTask extends AsyncTask<Void, Void, ArrayAdapter<String>> {
        private ProgressDialog pBar;

        @Override
        protected void onPreExecute() {
            pBar = new ProgressDialog(getActivity());
            pBar.setCancelable(false);
            pBar.show();
        }

        @Override
        protected ArrayAdapter<String> doInBackground(Void... params) {
            return new ArrayAdapter<String>(getActivity(),
                    R.layout.char_item, R.id.chapitem_tv,
                    new ArrayList<String>(Arrays.asList(BostanInfo.characters)));
        }

        @Override
        protected void onPostExecute(ArrayAdapter<String> result) {
            lv.setAdapter(result);

            pBar.dismiss();
        }
    }
}
