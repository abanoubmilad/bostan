package abanoubm.kstkolyom;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class DisplayStory extends AppCompatActivity {
    private static final String ARG_DATE = "date";
    private static final String ARG_DUAL_MODE = "dual";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_display_story);

        if (savedInstanceState == null) {
            Bundle arguments = new Bundle();
            arguments.putString(ARG_DATE, getIntent().getStringExtra(ARG_DATE));
            arguments.putBoolean(ARG_DUAL_MODE, false);

            FragmentDisplayStory fragment = new FragmentDisplayStory();
            fragment.setArguments(arguments);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.display_story_fragment, fragment)
                    .commit();

        }
    }
}
