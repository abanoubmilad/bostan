package abanoubm.bostan;

import java.util.ArrayList;
import java.util.Arrays;

import abanoubm.bostan.R;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class CharacterChooser extends Activity {
	private ListView lv;

	private class GetTask extends AsyncTask<Void, Void, ArrayAdapter<String>> {
		private ProgressDialog pBar;

		@Override
		protected void onPreExecute() {
			pBar = new ProgressDialog(CharacterChooser.this);
			pBar.setCancelable(false);
			pBar.show();
		}

		@Override
		protected ArrayAdapter<String> doInBackground(Void... params) {
			return new ArrayAdapter<String>(getApplicationContext(),
					R.layout.char_item, R.id.chapitem_tv,
					new ArrayList<String>(Arrays.asList(BostanInfo.characters)));
		}

		@Override
		protected void onPostExecute(ArrayAdapter<String> result) {
			lv.setAdapter(result);

			pBar.dismiss();
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chooser);

		lv = (ListView) findViewById(R.id.list);
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view,
					int position, long arg3) {
					startActivity(new Intent(getApplicationContext(),
							CharSectionChooser.class).putExtra("pos",
							position));
				
			}
		});
		((TextView) findViewById(R.id.subhead))
				.setText(R.string.readcharacters);

		new GetTask().execute();
	}
}
