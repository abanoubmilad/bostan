package abanoubm.bostan;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Main extends AppCompatActivity {
    private DrawerLayout nav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView footer = (TextView) findViewById(R.id.footer);
        footer.setText(String.format(
                getResources().getString(R.string.copyright),
                new SimpleDateFormat("yyyy", Locale.getDefault())
                        .format(new Date())));


        ListView lv = (ListView) findViewById(R.id.list);
        nav = (DrawerLayout) findViewById(R.id.drawer_layout);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getApplicationContext(), R.layout.homemenu_item, R.id.menuItem,
                BostanInfo.menuItems);
        lv.setAdapter(adapter);

        ((TextView) findViewById(R.id.subhead))
                .setText(R.string.readcharacters);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment, new CharacterChooser())
                .commit();


        View header = getLayoutInflater().inflate(R.layout.menu_header, lv, false);
        lv.addHeaderView(header, null, false);


        lv.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                nav.closeDrawers();
                --position;
                switch (position) {
                    case 0:
                        ((TextView) findViewById(R.id.subhead)).setText(R.string.readall);


                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment, new SectionChooser())
                                .commit();
                        break;
                    case 1:
                        ((TextView) findViewById(R.id.subhead))
                                .setText(R.string.readcharacters);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment, new CharacterChooser())
                                .commit();

                        break;
                    case 2:
                        ((TextView) findViewById(R.id.subhead)).setText(R.string.searchall);

                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment, new Search())
                                .commit();
                        break;
                    case 3:
                        try {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("fb://profile/1363784786"))
                                    .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                        } catch (Exception e) {
                            startActivity(
                                    new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/abanoubmiladhanna/"))
                                            .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                        }
                        break;
                }
            }
        });
        findViewById(R.id.nav_menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nav.openDrawer(Gravity.RIGHT);

            }
        });
    }

    @Override
    public void onBackPressed() {
        if (nav.isDrawerOpen(Gravity.RIGHT)) {
            nav.closeDrawer(Gravity.RIGHT);
        } else {
            super.onBackPressed();
        }
    }
}
