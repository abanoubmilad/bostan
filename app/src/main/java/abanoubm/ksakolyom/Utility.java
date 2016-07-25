package abanoubm.ksakolyom;


import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Utility {
    public final static int STORIES_ALL = 0, STORIES_FAV = 1, STORIES_READ = 2, STORIES_UN_READ = 3;

    private final static String PAGING_NEXT = "next", PAGING_PREVIOUS = "previous";

    public static ArrayList<Story> parseStories(String response, Context context) {
        Log.i("respone", response);

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


}
