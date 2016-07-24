package abanoubm.kstkolyom;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class FragmentDisplayStory extends Fragment {

    private String date;
    private Story mStory = null;
    private boolean dualMode;
    private static final String ARG_DATE = "date";
    private static final String ARG_DUAL_MODE = "dual";

    private TextView content, dateView;
    private ImageView photo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            date = arguments.getString(ARG_DATE);
            dualMode = arguments.getBoolean(ARG_DUAL_MODE);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_display_story, container, false);

        content = (TextView) root.findViewById(R.id.content);
        dateView = (TextView) root.findViewById(R.id.date);
        photo = (ImageView) root.findViewById(R.id.photo);

        new GetTask().execute();
        return root;
    }

    private class GetTask extends AsyncTask<Void, Void, Story> {

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected Story doInBackground(Void... params) {
            return DB.getInstant(getActivity()).getStory(date);
        }


        @Override
        protected void onPostExecute(Story story) {
            if (story != null) {
                mStory = story;
                content.setText(mStory.getContent());
                dateView.setText(mStory.getDate());
                content.setText(mStory.getContent());
                Picasso.with(getContext()).load(mStory.getPhoto()).placeholder(R.mipmap.ic_def).into(photo);
            }
        }
    }

}