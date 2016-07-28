package abanoubm.ksakolyom;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

public class Main extends Activity {
    private MenuItemAdapter mMenuItemAdapter;
    private static final String ARG_SELECTION = "sel";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_main);
        ((TextView) findViewById(R.id.footer)).setText("ksa kolyom " +
                BuildConfig.VERSION_NAME + " @" + new SimpleDateFormat(
                "yyyy", Locale.getDefault())
                .format(new Date()) + " Abanoub M.");

        ListView lv = (ListView) findViewById(R.id.list);

        mMenuItemAdapter = new MenuItemAdapter(getApplicationContext(),
                new ArrayList<>(Arrays.asList(getResources()
                        .getStringArray(R.array.main_menu))));
        lv.setAdapter(mMenuItemAdapter);

        lv.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                switch (position) {
                    case 0:

                        break;
                    case 1:
                        startActivity(new Intent(Main.this, DisplayStories.class).putExtra(ARG_SELECTION, Utility.STORIES_ALL));
                        break;
                    case 2:
                        startActivity(new Intent(Main.this, DisplayStories.class).putExtra(ARG_SELECTION, Utility.STORIES_FAV));
                        break;
                    case 3:
                        startActivity(new Intent(Main.this, DisplayStories.class).putExtra(ARG_SELECTION, Utility.STORIES_UN_READ));
                        break;
                    case 4:
                        startActivity(new Intent(Main.this, DisplayStories.class).putExtra(ARG_SELECTION, Utility.STORIES_READ));
                        break;
                    case 5:
                        startActivity(new Intent(Main.this, Search.class));
                        break;
                    case 6:
                        try {
                            getPackageManager().getPackageInfo(
                                    "com.facebook.katana", 0);
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri
                                    .parse("fb://page/208748925813135")).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                                    | Intent.FLAG_ACTIVITY_NEW_TASK));
                        } catch (Exception e) {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri
                                    .parse("https://www.facebook.com/ksa.kol.yom")).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                                    | Intent.FLAG_ACTIVITY_NEW_TASK));
                        }
                        break;
                    case 7:
                        startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri
                                .parse("https://drive.google.com/open?id=1GuP-wW_0MVH_HyN7Ypb9djHrfcxVGKbTL_g8C_uRw2M")));
                        break;
                    case 8:
                        try {
                            getPackageManager().getPackageInfo(
                                    "com.facebook.katana", 0);
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri
                                    .parse("fb://profile/1363784786")).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                                    | Intent.FLAG_ACTIVITY_NEW_TASK));
                        } catch (Exception e) {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri
                                    .parse("https://www.facebook.com/EngineeroBono")).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                                    | Intent.FLAG_ACTIVITY_NEW_TASK));
                        }
                        break;
                    case 9:
                        Uri uri = Uri.parse("market://details?id=" + getPackageName());
                        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                        try {
                            startActivity(goToMarket);
                        } catch (Exception e) {
                            startActivity(new Intent(Intent.ACTION_VIEW,
                                    Uri.parse("http://play.google.com/store/apps/details?id=" + getPackageName())));
                        }
                        break;
                }
            }
        });

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMenuItemAdapter.recycleIcons();

    }


}