package abanoubm.bostan;

import abanoubm.bostan.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
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
		prompt();
	}

	void prompt() {
		LayoutInflater li = LayoutInflater.from(getApplicationContext());
		View view = li.inflate(R.layout.prompts, null);
		final AlertDialog ad = new AlertDialog.Builder(SectionChooser.this)
				.setCancelable(true).create();
		ad.setView(view, 0, 0, 0, 0);
		ad.show();
		final EditText userInput = (EditText) view.findViewById(R.id.input);
		((TextView) view.findViewById(R.id.dis))
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						ad.cancel();
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
