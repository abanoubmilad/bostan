package abanoubm.ksakolyom;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class DisplayStories extends AppCompatActivity implements CallBack {

    private boolean dualMode;
    private static final String ARG_ID = "id";
    private static final String ARG_DUAL_MODE = "dual";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_display_stories);
        ((TextView) findViewById(R.id.subhead)).setText(R.string.subhead_display_stories);
        dualMode = findViewById(R.id.display_stories_fragment_dual) != null;

        if (savedInstanceState == null) {
            Bundle args = new Bundle();
            args.putBoolean(ARG_DUAL_MODE, dualMode);
            FragmentDisplayStories fragment = new FragmentDisplayStories();
            fragment.setArguments(args);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.display_stories_fragment, fragment)
                    .commit();
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
