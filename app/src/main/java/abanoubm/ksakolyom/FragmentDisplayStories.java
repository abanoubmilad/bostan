package abanoubm.ksakolyom;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class FragmentDisplayStories extends Fragment {
    private ListView lv;
    private int previousPosition = 0;
    private boolean isDualMode = false;
    private StoryDisplayListAdapter mAdapter;
    private DB mDB;
    private static final String ARG_DUAL_MODE = "dual";
    private ProgressBar previous, next;
    private boolean loading_previous = false, loading_next = false, paging_allowed = false;

    private int displayType = 0;

    private class GetAllTask extends AsyncTask<Void, Void, ArrayList<Story>> {
        @Override
        protected void onPreExecute() {
        }

        @Override
        protected ArrayList<Story> doInBackground(Void... params) {
            if (mDB == null)
                mDB = DB.getInstant(getActivity());

            if (displayType == 0 || Utility.isHoldingData(getContext()))
                return mDB.getStories(displayType);

            ArrayList<Story> stories = Utility.getPagingStories(getContext());
            if (stories != null)
                mDB.addStories(stories);

            return stories;
        }

        @Override
        protected void onPostExecute(ArrayList<Story> stories) {
            if (stories != null) {
                mAdapter.clearThenAddAll(stories);
                if (stories.size() == 0) {
                    getActivity().finish();
                    Toast.makeText(getActivity(),
                            R.string.msg_no_stories, Toast.LENGTH_SHORT).show();
                } else {
                    if (previousPosition < stories.size())
                        lv.setSelection(previousPosition);
                    if (isDualMode) {
                        lv.performItemClick(lv.findViewWithTag(mAdapter.getItem(previousPosition)),
                                previousPosition, mAdapter.getItemId(previousPosition));
                    }
                }
                paging_allowed = displayType == 0;
            }


        }
    }

    private class GetPreviousPagingTask extends AsyncTask<Void, Void, ArrayList<Story>> {

        @Override
        protected void onPreExecute() {
            previous.setVisibility(View.VISIBLE);
        }

        @Override
        protected ArrayList<Story> doInBackground(Void... params) {
            if (mDB == null)
                mDB = DB.getInstant(getActivity());

            ArrayList<Story> stories = Utility.getPreviousPagingStories(getContext());
            if (stories != null)
                mDB.addStories(stories);

            return stories;
        }

        @Override
        protected void onPostExecute(ArrayList<Story> stories) {
            if (stories != null) {
                mAdapter.addAll(stories);
                loading_previous = false;
            }
            previous.setVisibility(View.GONE);

        }
    }

    private class GetNextPagingTask extends AsyncTask<Void, Void, ArrayList<Story>> {

        @Override
        protected void onPreExecute() {
            next.setVisibility(View.VISIBLE);
        }

        @Override
        protected ArrayList<Story> doInBackground(Void... params) {
            if (mDB == null)
                mDB = DB.getInstant(getActivity());

            ArrayList<Story> stories = Utility.getNextPagingStories(getContext());
            if (stories != null)
                mDB.addStories(stories);

            return stories;
        }

        @Override
        protected void onPostExecute(ArrayList<Story> stories) {
            if (stories != null) {
                mAdapter.addAll(stories);
                loading_next = false;
            }
            next.setVisibility(View.GONE);

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (DB.getInstant(getActivity()).isDirty()) {
            new GetAllTask().execute();
            DB.getInstant(getActivity()).clearDirty();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            isDualMode = arguments.getBoolean(ARG_DUAL_MODE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_display_stories, container, false);
        lv = (ListView) root.findViewById(R.id.list);


        previous = (ProgressBar) root.findViewById(R.id.previous);
        next = (ProgressBar) root.findViewById(R.id.next);

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


        lv.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (!paging_allowed)
                    return;
                if (firstVisibleItem + visibleItemCount >= totalItemCount && !loading_next) {
                    loading_next = true;
                    new GetNextPagingTask().execute();
                    Log.i("nexxxxxxxxxxxxxxxt", "a7eeeeeeeeeeeeeeeeeht777t");
                } else if (firstVisibleItem == 1 && !loading_previous) {
                    loading_previous = true;
                    new GetPreviousPagingTask().execute();
                    Log.i("previiiiiiiiiiious", "a7eeeeeeeeeeeeeeehfooo2");
                }
            }
        });
        Spinner spin = (Spinner) root.findViewById(R.id.spin);
        spin.setAdapter(new ArrayAdapter<>(getActivity(),
                R.layout.item_string, getResources().getTextArray(R.array.display_menu)));

        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                if (displayType != position) {
                    paging_allowed = paging_allowed && position == 0;
                    displayType = position;
                    new GetAllTask().execute();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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
