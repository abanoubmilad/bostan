package abanoubm.ksakolyom;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class FragmentDisplayStory extends Fragment {

    private String date;
    private Story mStory = null;
    private boolean dualMode;
    private static final String ARG_DATE = "date";
    private static final String ARG_DUAL_MODE = "dual";
    private boolean isFav = false;

    private TextView content, dateView;
    private ImageView photo, fav, check;

    private DB mDB;

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

        fav = (ImageView) root.findViewById(R.id.fav);
        check = (ImageView) root.findViewById(R.id.check);

        fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new UpdateFavTask().execute();
            }
        });
        new GetTask().execute();
        return root;
    }

    private class GetTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected Void doInBackground(Void... params) {
            if (mDB == null)
                mDB = DB.getInstant(getActivity());
            mStory = mDB.getStory(date);
            if (mStory.getRead().equals("0"))
                mDB.markAsRead(mStory.getId());
            return null;
        }


        @Override
        protected void onPostExecute(Void story) {
            content.setText(mStory.getContent());
            dateView.setText(mStory.getDate());
            content.setText(mStory.getContent());
            Picasso.with(getContext()).load(mStory.getPhoto()).placeholder(R.mipmap.ic_def).into(photo);

            if (mStory.getRead().equals("2")) {
                fav.setBackgroundResource(R.mipmap.ic_fav);
                isFav = true;
            } else if (mStory.getRead().equals("1")) {
                check.setVisibility(View.VISIBLE);
            }

        }
    }

    private class UpdateFavTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            fav.setClickable(false);
        }

        @Override
        protected Void doInBackground(Void... params) {
            isFav = !isFav;
            if (isFav)
                DB.getInstant(getActivity()).markAsFav(mStory.getId());
            else
                DB.getInstant(getActivity()).markAsRead(mStory.getId());
            return null;
        }


        @Override
        protected void onPostExecute(Void story) {
            if (isFav) {
                fav.setBackgroundResource(R.mipmap.ic_fav);
                Toast.makeText(getActivity(),
                        R.string.msg_added_fav, Toast.LENGTH_SHORT).show();
            } else
                fav.setBackgroundResource(R.mipmap.ic_add);

            fav.setClickable(true);


        }

    }


}