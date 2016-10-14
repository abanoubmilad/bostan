package abanoubm.bostan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class SectionsAdapter extends ArrayAdapter<Section> {

    public SectionsAdapter(Context context, ArrayList<Section> verses) {
        super(context, 0, verses);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Section section = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.verse_item, parent, false);
        }
        TextView header = (TextView) convertView.findViewById(R.id.headertv);
        TextView verseText = (TextView) convertView.findViewById(R.id.versetv);

        header.setText(" مقطع " + BostanInfo.getArabicNum(section.getSid()));
        verseText.setText(section.getText());
        return convertView;
    }
}