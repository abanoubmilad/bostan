package abanoubm.bostan;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

public class SectionChooser extends Activity {
    private GridView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_section_chooser);

        lv = (GridView) findViewById(R.id.grid_view);
        lv.setAdapter(new GridBaseAdapter(this, 1226));
        lv.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View arg1,
                                    int position, long arg3) {
                startActivity(new Intent(getApplicationContext(),
                        DisplaySection.class).putExtra("sec", position + 1));

            }
        });
        ((TextView) findViewById(R.id.subhead)).setText(R.string.readall);

        final EditText userInput = (EditText) findViewById(R.id.input);
        findViewById(R.id.search)
                .setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        String str = userInput.getText().toString();
                        if (str.length() > 0) {
                            int section = Integer.parseInt(str) - 1;
                            if (section > 1225 || section < 0)
                                lv.setSelection(1225);
                            else
                                lv.setSelection(section);
                        }

                    }

                });
    }

}
