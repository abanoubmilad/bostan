package abanoubm.ksakolyom;


import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HTTPClient {
    private static OkHttpClient client = new OkHttpClient();

    public static String getStories() {

        Request request = new Request.Builder()
                .url("https://graph.facebook.com/v2.7/208748925813135/feed?" +
                        "fields=picture,message,created_time&" +
                        BuildConfig.F_B_A_T
                )
                .build();
        Response response;
        try {
            response = client.newCall(request).execute();
            return response.body().string();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public static String getStories(String pagingURL) {

        Request request = new Request.Builder()
                .url(pagingURL)
                .build();
        Response response;
        try {
            response = client.newCall(request).execute();
            return response.body().string();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }


}
