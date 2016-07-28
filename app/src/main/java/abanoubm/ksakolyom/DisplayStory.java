package abanoubm.ksakolyom;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class DisplayStory extends AppCompatActivity {
    private static final String ARG_ID= "id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_display_story);

        if (savedInstanceState == null) {
            Bundle arguments = new Bundle();
            arguments.putString(ARG_ID, getIntent().getStringExtra(ARG_ID));

            FragmentDisplayStory fragment = new FragmentDisplayStory();
            fragment.setArguments(arguments);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.display_story_fragment, fragment)
                    .commit();

        }
    }
}
