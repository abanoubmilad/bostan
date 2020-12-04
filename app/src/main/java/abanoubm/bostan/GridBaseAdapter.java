package abanoubm.bostan;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

public class GridBaseAdapter extends BaseAdapter {

    private static int white = Color.WHITE,
            grey;
    private int fieldsCount;
    private Context context;


    public GridBaseAdapter(Context context, int fieldsCount) {
        this.fieldsCount = fieldsCount;
        this.context = context;
        grey = ContextCompat.getColor(context, R.color.light_grey);

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
        if (position % 2 == 0)
            t.setBackgroundColor(white);
        else
            t.setBackgroundColor(grey);


        return convertView;

    }

}