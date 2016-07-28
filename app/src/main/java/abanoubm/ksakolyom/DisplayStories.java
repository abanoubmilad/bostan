package abanoubm.ksakolyom;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class DisplayStories extends AppCompatActivity implements CallBack {

    private boolean dualMode;
    private static final String ARG_ID = "id";
    private static final String ARG_DUAL_MODE = "dual";
    private static final String ARG_SELECTION = "sel";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_display_stories);
        dualMode = findViewById(R.id.display_stories_fragment_dual) != null;

        if (savedInstanceState == null) {
            Bundle args = new Bundle();
            args.putBoolean(ARG_DUAL_MODE, dualMode);

            int selection = getIntent().getIntExtra(ARG_SELECTION, 0);

            if (selection == Utility.STORIES_ALL)
                ((TextView) findViewById(R.id.subhead)).setText(getResources().getString(R.string.sub_stories));
            else if (selection == Utility.STORIES_FAV)
                ((TextView) findViewById(R.id.subhead)).setText(getResources().getString(R.string.sub_fav));
            else if (selection == Utility.STORIES_UN_READ)
                ((TextView) findViewById(R.id.subhead)).setText(getResources().getString(R.string.sub_unread));
            else if (selection == Utility.STORIES_READ)
                ((TextView) findViewById(R.id.subhead)).setText(getResources().getString(R.string.sub_read));


            if (selection == Utility.STORIES_ALL) {

                FragmentDisplayStories fragment = new FragmentDisplayStories();
                fragment.setArguments(args);

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.display_stories_fragment, fragment)
                        .commit();
            } else {
                FragmentDisplaySelection fragment = new FragmentDisplaySelection();
                args.putInt(ARG_SELECTION, selection);
                fragment.setArguments(args);

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.display_stories_fragment, fragment)
                        .commit();
            }

        }


    }

    @Override
    public void notify(String id) {
        if (dualMode) {
            Bundle args = new Bundle();
            args.putString(ARG_ID, id);
            args.putBoolean(ARG_DUAL_MODE, true);

            FragmentDisplayStory fragment = new FragmentDisplayStory();
            fragment.setArguments(args);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.display_story_fragment, fragment)
                    .commit();

        } else {
            startActivity(new Intent(this, DisplayStory.class).putExtra(ARG_ID, id));
        }

    }
}
