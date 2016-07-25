package abanoubm.ksakolyom;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class FragmentDisplaySelection extends Fragment {
    private ListView lv;
    private int previousPosition = 0;
    private boolean isDualMode = false;
    private StoryDisplayListAdapter mAdapter;
    private static final String ARG_DUAL_MODE = "dual";
    private static final String ARG_SELECTION = "sel";
    private int selection;
    private View msg;

    private class GetAllTask extends AsyncTask<Void, Void, ArrayList<Story>> {
        @Override
        protected void onPreExecute() {
        }

        @Override
        protected ArrayList<Story> doInBackground(Void... params) {
            return DB.getInstant(getActivity()).getStories(selection);
        }

        @Override
        protected void onPostExecute(ArrayList<Story> stories) {
            if (stories != null) {
                mAdapter.addAll(stories);
                if (stories.size() == 0) {
                    msg.setVisibility(View.VISIBLE);
                } else {
                    if (previousPosition < stories.size())
                        lv.setSelection(previousPosition);
                    if (isDualMode) {
                        lv.performItemClick(lv.findViewWithTag(mAdapter.getItem(previousPosition)),
                                previousPosition, mAdapter.getItemId(previousPosition));
                    }
                }
            }


        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            isDualMode = arguments.getBoolean(ARG_DUAL_MODE);
            selection = arguments.getInt(ARG_SELECTION);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_display_stories, container, false);
        lv = (ListView) root.findViewById(R.id.list);

        msg = root.findViewById(R.id.msg);

        mAdapter = new StoryDisplayListAdapter(getActivity(), new ArrayList<Story>(0));
        lv.setAdapter(mAdapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View arg1,
                                    int position, long arg3) {
                if (isDualMode)
                    mAdapter.setSelectedIndex(position);

                previousPosition = lv.getFirstVisiblePosition();

                ((CallBack) getActivity()).notify((mAdapter.getItem(position).getId()));
            }
        });
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        new GetAllTask().execute();

    }

}
