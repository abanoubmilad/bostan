package abanoubm.bostan;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URLEncoder;
import java.util.List;

public class DisplaySection extends Activity {
    private TextView displayText;
    private int secNum;

    private class FetchSectionTask extends AsyncTask<Integer, Void, String> {
        private ProgressDialog pBar;

        @Override
        protected void onPreExecute() {
            pBar = new ProgressDialog(DisplaySection.this);
            pBar.setCancelable(false);
            pBar.show();
        }

        @Override
        protected String doInBackground(Integer... params) {

            try {
                return DB.getInstance(getApplicationContext()).getSection(
                        params[0].intValue());
            } catch (Exception e) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(String section) {
            if (section != null)
                displayText.setText(section);
            else
                Toast.makeText(getApplicationContext(), R.string.err_msg_db,
                        Toast.LENGTH_SHORT).show();
            pBar.dismiss();
        }
    }

    private String urlEncode(String s) {
        try {
            return URLEncoder.encode(s, "UTF-8");
        } catch (Exception e) {
            return "";
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_section);

        secNum = getIntent().getIntExtra("sec", 1);

        final TextView t = (TextView) findViewById(R.id.subhead);
        t.setText(" مقطع " + BostanInfo.getArabicNum(secNum));

        TextView tw = (TextView) findViewById(R.id.tw_iv);
        TextView fb = (TextView) findViewById(R.id.fb_iv);
        final TextView next = (TextView) findViewById(R.id.next_iv);
        final TextView previous = (TextView) findViewById(R.id.previous_iv);
        final ScrollView scrol = (ScrollView) findViewById(R.id.scrol);

        if (secNum == 1226) {
            next.setVisibility(View.GONE);
        } else if (secNum == 1) {
            previous.setVisibility(View.GONE);
        }

        next.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                secNum++;
                if (secNum == 1226)
                    next.setVisibility(View.GONE);
                else if (secNum == 2)
                    previous.setVisibility(View.VISIBLE);

                t.setText(" مقطع " + BostanInfo.getArabicNum(secNum));
                new FetchSectionTask().execute(secNum);
                scrol.fullScroll(ScrollView.FOCUS_UP);

            }
        });
        previous.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                secNum--;
                if (secNum == 2)
                    previous.setVisibility(View.GONE);
                else if (secNum == 1225)
                    next.setVisibility(View.VISIBLE);

                t.setText(" مقطع " + BostanInfo.getArabicNum(secNum));
                new FetchSectionTask().execute(secNum);
                scrol.fullScroll(ScrollView.FOCUS_UP);

            }
        });

        displayText = (TextView) findViewById(R.id.display);

        new FetchSectionTask().execute(secNum);

        fb.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent shareIntent = new Intent(
                        android.content.Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(android.content.Intent.EXTRA_TEXT,
                        getSelection());
                v.getContext().startActivity(
                        Intent.createChooser(shareIntent, "شير"));
            }
        });
        tw.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                String tweetUrl = String.format(
                        "https://twitter.com/intent/tweet?text=%s",
                        urlEncode(getSelection()));
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri
                        .parse(tweetUrl));

                List<ResolveInfo> matches = getPackageManager()
                        .queryIntentActivities(intent, 0);
                for (ResolveInfo info : matches) {
                    if (info.activityInfo.packageName.toLowerCase().startsWith(
                            "com.twitter")) {
                        intent.setPackage(info.activityInfo.packageName);
                    }
                }

                startActivity(intent);
            }
        });

    }

    private String getSelection() {
        String temp = displayText.getText().toString();

        if (displayText.isFocused()) {
            int selStart = displayText.getSelectionStart();
            int selEnd = displayText.getSelectionEnd();

            temp = displayText
                    .getText()
                    .subSequence(Math.max(0, Math.min(selStart, selEnd)),
                            Math.max(0, Math.max(selStart, selEnd))).toString();
            if (temp.length() < 2)
                temp = displayText.getText().toString();
        }
        return temp;
    }
}
