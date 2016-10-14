package abanoubm.bostan;

import abanoubm.bostan.R;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class GridBaseAdapter extends BaseAdapter {

	private int fieldsCount;
	private Context context;

	public static final int cyanLight = Color.rgb(29, 202, 255),
			cyanDark = Color.argb(220, 50,136,209);

	public GridBaseAdapter(Context context, int fieldsCount) {
		this.fieldsCount = fieldsCount;
		this.context = context;
	}

	@Override
	public int getCount() {
		return fieldsCount;
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.grid_item, parent, false);
		}
		TextView t = (TextView) convertView.findViewById(R.id.item_text);
		t.setText(BostanInfo.getArabicNum(position + 1));
		if (position / 2 % 2 == 0) 
			t.setBackgroundColor(cyanLight);
		else 
			t.setBackgroundColor(cyanDark);
		

		return convertView;

	}

}