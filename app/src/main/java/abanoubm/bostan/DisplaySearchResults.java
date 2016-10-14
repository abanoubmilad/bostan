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

public class DisplaySearchResults extends Activity {
	private ListView lv;

	private class DisplayTask extends AsyncTask<Void, Void, SectionsAdapter> {
		private ProgressDialog pBar;

		@Override
		protected void onPreExecute() {
			pBar = new ProgressDialog(DisplaySearchResults.this);
			pBar.setCancelable(false);
			pBar.show();
		}

		@Override
		protected SectionsAdapter doInBackground(Void... params) {
			return new SectionsAdapter(getApplicationContext(),
					BostanInfo.searchResults);
		}

		@Override
		protected void onPostExecute(SectionsAdapter result) {
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
			public void onItemClick(AdapterView<?> parent, View arg1,
					int position, long arg3) {
				Section section = (Section) parent.getItemAtPosition(position);
				Intent intent = new Intent(getApplicationContext(),
						DisplaySection.class).putExtra("sec", section.getSid());
				startActivity(intent);

			}
		});
		((TextView) findViewById(R.id.subhead)).setText(R.string.results);

		if (BostanInfo.searchResults == null
				|| BostanInfo.searchResults.size() == 0) {
			Toast.makeText(getApplicationContext(), "ﻻ توجد مقاطع متوافقة",
					Toast.LENGTH_SHORT).show();
			finish();
		} else {
			Toast.makeText(
					getApplicationContext(),
					BostanInfo.getArabicNum(BostanInfo.searchResults.size())
							+ " مقطع/مقاطع ", Toast.LENGTH_SHORT).show();
			new DisplayTask().execute();
		}

	}
}
