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

    private final static String PAGING_NEXT = "next", PAGING_PREVIOUS = "previous";

    public static ArrayList<Story> parseStories(String response, Context context) {
        Log.i("respone", response);

        if (response == null)
            return null;
        try {
            JSONObject obj = new JSONObject(response);

            JSONArray array;

            try {
                array = obj.getJSONArray("data");
            } catch (JSONException e) {
                return new ArrayList<>(0);
            }

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
            obj = obj.getJSONObject("paging");

            updatePagingURL(context, PAGING_PREVIOUS, obj.getString(PAGING_PREVIOUS));
            updatePagingURL(context, PAGING_NEXT, obj.getString(PAGING_NEXT));
            return stories;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static ArrayList<Story> parseStories(String response, Context context, String pagingType) {
        //Log.i("respone", response);
        if (response == null)
            return null;

        try {
            JSONObject obj = new JSONObject(response);

            JSONArray array;

            try {
                array = obj.getJSONArray("data");
            } catch (JSONException e) {
                return new ArrayList<>(0);
            }

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

            obj = obj.getJSONObject("paging");
            updatePagingURL(context, pagingType, obj.getString(pagingType));
            return stories;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static ArrayList<Story> getPagingStories(Context context) {
        return parseStories(HTTPClient.getStories(), context);
    }

    public static ArrayList<Story> getNextPagingStories(Context context) {
        String pagingURL = getPagingURL(context, PAGING_NEXT);
        if (pagingURL.length() != 0)
            return parseStories(HTTPClient.getStories(pagingURL), context, PAGING_NEXT);
        else
            return new ArrayList<>(0);
    }

    public static ArrayList<Story> getPreviousPagingStories(Context context) {
        String pagingURL = getPagingURL(context, PAGING_PREVIOUS);
        if (pagingURL.length() != 0)
            return parseStories(HTTPClient.getStories(pagingURL), context, PAGING_PREVIOUS);
        else
            return new ArrayList<>(0);
    }

    public static String getPagingURL(Context context, String pageType) {
        return context.getSharedPreferences("paging",
                Context.MODE_PRIVATE).getString(pageType, "");
    }


    public static boolean isHoldingData(Context context) {
        return context.getSharedPreferences("paging",
                Context.MODE_PRIVATE).getString(PAGING_PREVIOUS, "").length() > 0;
    }

    public static void updatePagingURL(Context context, String pageType, String paging) {
        context.getSharedPreferences("paging",
                Context.MODE_PRIVATE).edit().putString(pageType, paging).commit();
    }

    public static boolean doesHaveNext(Context context) {
        return context.getSharedPreferences("paging",
                Context.MODE_PRIVATE).getBoolean("hasnext", true);
    }

    public static void setHaveNext(Context context, boolean flag) {
        context.getSharedPreferences("paging",
                Context.MODE_PRIVATE).edit().putBoolean("hasnext", flag).commit();
    }

    public static boolean doesHavePrevious(Context context) {
        return context.getSharedPreferences("paging",
                Context.MODE_PRIVATE).getBoolean("hasprevious", false) ||
                new SimpleDateFormat("yyyy-mm-dd").format(
                new Date()).compareTo(context.getSharedPreferences("paging",
                Context.MODE_PRIVATE).getString("lastdate", "")) == 1;
    }

    public static void setHavePrevious(Context context, boolean flag) {
        context.getSharedPreferences("paging",
                Context.MODE_PRIVATE).edit().putBoolean("hasprevious", flag).commit();
    }


    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
