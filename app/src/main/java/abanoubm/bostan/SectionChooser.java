package abanoubm.bostan;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.GridView;

import androidx.fragment.app.Fragment;

public class SectionChooser extends Fragment {
    private GridView lv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.frag_sections, container, false);

        lv = (GridView) root.findViewById(R.id.grid_view);
        lv.setAdapter(new GridBaseAdapter(getActivity(), 1226));
        lv.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View arg1,
                                    int position, long arg3) {
                startActivity(new Intent(getActivity(),
                        DisplaySection.class).putExtra("sec", position + 1));

            }
        });

        final EditText userInput = (EditText) root.findViewById(R.id.input);
        root.findViewById(R.id.search)
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
        return root;
    }

}
