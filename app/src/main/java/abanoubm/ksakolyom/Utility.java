package abanoubm.ksakolyom;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Utility {
    public final static int STORIES_ALL = 4, STORIES_FAV = 2, STORIES_READ = 3, STORIES_UN_READ = 0;

    public static final String TAG_PAGING = "paging", TAG_NEXT = "next", TAG_PREVIOUS = "previous";

    public static final String TAG_LAST = "last";

    public static ArrayList<Story> parseStories(String response, Context context, String pagingType) {
        Log.i("response", response);
        if (response == null)
            return null;

        try {
            JSONObject obj = new JSONObject(response);

            JSONArray array;

            array = obj.getJSONArray("data");


            int length = array.length();
            ArrayList<Story> stories = new ArrayList<>(length);
            JSONObject subObj;
            String picture;
            for (int i = 0; i < length; i++) {
                subObj = array.getJSONObject(i);
                try {
                    picture = subObj.getString("picture");
                } catch (JSONException e) {
                    picture = "";
                }
                stories.add(new Story(subObj.getString("id"), picture,
                        subObj.getString("message"),
                        subObj.getString("created_time").substring(0, 10)));
            }

            if (length != 0) {
                obj = obj.getJSONObject("paging");

                if (pagingType == null) {
                    updatePagingURL(context, TAG_PREVIOUS, obj.getString(TAG_PREVIOUS));
                    updatePagingURL(context, TAG_NEXT, obj.getString(TAG_NEXT));
                    updateLastPaging(context);
                } else if (pagingType.equals(TAG_NEXT)) {
                    updatePagingURL(context, TAG_NEXT, obj.getString(TAG_NEXT));
                } else {
                    updatePagingURL(context, TAG_PREVIOUS, obj.getString(TAG_PREVIOUS));
                    updateLastPaging(context);
                }
            } else {
                if (pagingType == null) {
                    updatePagingURL(context, TAG_PREVIOUS, "");
                    updatePagingURL(context, TAG_NEXT, "");
                    updateLastPaging(context);
                } else if (pagingType.equals(TAG_NEXT)) {
                    updatePagingURL(context, TAG_NEXT, "");
                } else {
                    updatePagingURL(context, TAG_PREVIOUS, "");
                    updateLastPaging(context);
                }

            }
            return stories;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static ArrayList<Story> getPagingStories(Context context) {
        return parseStories(HTTPClient.getStories(), context, null);
    }

    public static ArrayList<Story> getPagingStories(Context context, String pagingType) {
        String pagingURL = getPagingURL(context, pagingType);
        if (pagingURL.length() != 0)
            return parseStories(HTTPClient.getStories(pagingURL), context, pagingType);
        else
            return new ArrayList<>(0);
    }

    public static String getPagingURL(Context context, String pageType) {
        return context.getSharedPreferences(TAG_PAGING,
                Context.MODE_PRIVATE).getString(pageType, "");
    }


    public static void updatePagingURL(Context context, String tag, String value) {
        context.getSharedPreferences(TAG_PAGING,
                Context.MODE_PRIVATE).edit().putString(tag, value).commit();
    }

    public static void updateLastPaging(Context context) {
        context.getSharedPreferences(TAG_PAGING,
                Context.MODE_PRIVATE).edit().putString(TAG_LAST, new SimpleDateFormat("yyyy-MM-dd").format(
                new Date())).commit();
    }

    public static String getLastPaging(Context context) {
        return context.getSharedPreferences(TAG_PAGING,
                Context.MODE_PRIVATE).getString(TAG_LAST, "");
    }

    public static boolean hasPaging(Context context, String pagingType) {
        if (pagingType.equals(TAG_PREVIOUS))
            return getPagingURL(context, TAG_PREVIOUS).length() != 0;
        else
            return getPagingURL(context, TAG_NEXT).length() != 0 || new SimpleDateFormat("yyyy-MM-dd").format(
                    new Date()).compareTo(getLastPaging(context)) == 1;


    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
