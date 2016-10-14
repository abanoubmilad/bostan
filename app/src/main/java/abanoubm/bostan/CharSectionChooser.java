package abanoubm.bostan;

import abanoubm.bostan.R;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class CharSectionChooser extends Activity {
	private ListView lv;

	private class SearchTask extends AsyncTask<Integer, Void, SectionsAdapter> {
		private ProgressDialog pBar;

		@Override
		protected void onPreExecute() {
			pBar = new ProgressDialog(CharSectionChooser.this);
			pBar.setCancelable(false);
			pBar.show();
		}

		@Override
		protected SectionsAdapter doInBackground(Integer... params) {
			try {
				return new SectionsAdapter(getApplicationContext(), DB
						.getInstance(getApplicationContext()).getSections(
								BostanInfo.charactersNum[params[0]],
								BostanInfo.charactersNum[params[1]]));
			} catch (Exception e) {
                e.printStackTrace();
				return null;
			}

		}

		@Override
		protected void onPostExecute(SectionsAdapter result) {
			if (result != null)
				lv.setAdapter(result);

			else
				Toast.makeText(getApplicationContext(), R.string.err_msg_db,
						Toast.LENGTH_LONG).show();
			pBar.dismiss();
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chooser);

		final int pos = getIntent().getIntExtra("pos", 0);
		lv = (ListView) findViewById(R.id.list);
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View arg1,
					int position, long arg3) {
				Intent intent = new Intent(getApplicationContext(),
						DisplaySection.class).putExtra("sec", position
						+ BostanInfo.charactersNum[pos]);
				startActivity(intent);

			}
		});
		((TextView) findViewById(R.id.subhead))
				.setText(BostanInfo.characters[pos]);

		new SearchTask().execute(pos, pos + 1);

	}
}
