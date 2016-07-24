package abanoubm.ksakolyom;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class StoryDisplayListAdapter extends Adapter<Story> {

    private int selected = -1;

    public StoryDisplayListAdapter(Context context, ArrayList<Story> stories) {
        super(context, 0, stories);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        Story story = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.item_display_story_list, parent, false);
            holder = new ViewHolder();
            holder.content = (TextView) convertView.findViewById(R.id.content);
            holder.photo = (ImageView) convertView.findViewById(R.id.photo);
            holder.date = (TextView) convertView.findViewById(R.id.date);
            holder.root = convertView.findViewById(R.id.root);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.content.setText(story.getContent().substring(0,Math.max(25,story.getContent().indexOf(' ',25))));
        holder.date.setText(story.getDate());

        Picasso.with(getContext()).load(story.getPhoto()).placeholder(R.mipmap.ic_def).into(holder.photo);


        if (selected == position)
            holder.root.setBackgroundColor(
                    ContextCompat.getColor(getContext(), R.color.colorAccent));
        else
            holder.root.setBackgroundResource(R.drawable.dynamic_bg);

        return convertView;
    }

    private static class ViewHolder {
        TextView content, date;
        ImageView photo;
        View root;
    }

    public void setSelectedIndex(int pos) {
        selected = pos;
        notifyDataSetChanged();
    }
}