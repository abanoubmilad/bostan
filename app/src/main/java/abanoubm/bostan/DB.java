package abanoubm.bostan;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.Html;
import android.text.Spanned;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.zip.ZipInputStream;

public class DB extends SQLiteOpenHelper {

    private static String DB_PATH = "";
    private static String DB_NAME = ".systbdefault";
    private static String assets_DB_NAME = "systbdefault.zip";
    private static String Tb_NAME = "bostan_tb";
    private static DB dbm;
    private final Context mContext;
    private SQLiteDatabase db;

    private DB(Context context) throws IOException {
        super(context, DB_NAME, null, 1);
        DB_PATH = context.getFilesDir().getPath();

        this.mContext = context;

        String mPath = DB_PATH + DB_NAME;

        if (!new File(DB_PATH + DB_NAME).exists())
            unpackZip();

        db = SQLiteDatabase.openDatabase(mPath, null,
                SQLiteDatabase.OPEN_READONLY);
    }

    public static DB getInstance(Context context) throws IOException {
        if (dbm == null)
            dbm = new DB(context);
        return dbm;
    }

    @Override
    public void onCreate(SQLiteDatabase arg0) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {

    }

    private void unpackZip() throws IOException {
        InputStream is;
        ZipInputStream zis;

        is = mContext.getAssets().open(assets_DB_NAME);
        zis = new ZipInputStream(new BufferedInputStream(is));

        while (zis.getNextEntry() != null) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024 * 4];
            int count;

            File dir = new File(DB_PATH);
            dir.mkdirs();

            FileOutputStream fout = new FileOutputStream(DB_PATH + DB_NAME);

            // reading and writing
            while ((count = zis.read(buffer)) != -1) {
                baos.write(buffer, 0, count);
                byte[] bytes = baos.toByteArray();
                fout.write(bytes);
                baos.reset();
            }

            fout.close();
            zis.closeEntry();
        }

        zis.close();

    }

    public ArrayList<Section> search(String text) {
        String selectQuery = "SELECT  _id,secd FROM " + Tb_NAME + " WHERE "
                + "sec" + " like " + "'%" + text + "%'";
        try {
            Cursor c = db.rawQuery(selectQuery, null);
            ArrayList<Section> results = new ArrayList<>(c.getCount());
            if (c.moveToNext()) {
                int idCol = c.getColumnIndex("_id");
                int secdCol = c.getColumnIndex("secd");
                do {
                    results.add(new Section(c.getInt(idCol), c
                            .getString(secdCol)));
                } while (c.moveToNext());
            }
            return results;
        } catch (Exception e) {
            return null;
        }

    }

    public ArrayList<Section> search(String text, int start, int end) {
        String selectQuery = "SELECT  _id,secd FROM " + Tb_NAME + " WHERE "
                + "sec" + " like " + "'%" + text + "%' AND " + start
                + "<= _id  AND " + end + " >= _id";
        try {
            Cursor c = db.rawQuery(selectQuery, null);
            ArrayList<Section> results = new ArrayList<>(c.getCount());
            if (c.moveToNext()) {
                int idCol = c.getColumnIndex("_id");
                int secdCol = c.getColumnIndex("secd");
                do {
                    results.add(new Section(c.getInt(idCol), c
                            .getString(secdCol)));
                } while (c.moveToNext());
            }
            return results;
        } catch (Exception e) {
            return null;
        }

    }

    public ArrayList<Spanned> getSections() {
        String selectQuery = "SELECT secd FROM " + Tb_NAME;

        try {
            Cursor c = db.rawQuery(selectQuery, null);
            ArrayList<Spanned> results = new ArrayList<Spanned>(c.getCount());
            if (c.moveToNext()) {
                int secdCol = c.getColumnIndex("secd");
                int i = 1;
                do {
                    results.add(Html.fromHtml("<font color=\"#0D47A1\">"
                            + BostanInfo.getArabicNum(i) + "</font> "
                            + c.getString(secdCol)));
                    i++;
                } while (c.moveToNext());
            }
            return results;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public ArrayList<Section> getSections(int start, int end) {
        String selectQuery = "SELECT _id,secd FROM " + Tb_NAME + " WHERE "
                + start + "<= _id  AND " + end + " >= _id";
        try {
            Cursor c = db.rawQuery(selectQuery, null);
            ArrayList<Section> results = new ArrayList<>(c.getCount());
            if (c.moveToNext()) {
                int idCol = c.getColumnIndex("_id");
                int secdCol = c.getColumnIndex("secd");
                do {
                    results.add(new Section(c.getInt(idCol), c
                            .getString(secdCol)));
                } while (c.moveToNext());
            }
            return results;
        } catch (Exception e) {
            return null;
        }

    }

    public String getSection(int sec) {
        String selectQuery = "SELECT secd FROM " + Tb_NAME + " WHERE _id="
                + sec + " LIMIT 1";
        try {
            Cursor c = db.rawQuery(selectQuery, null);
            if (c.moveToNext())
                return c.getString(c.getColumnIndex("secd"));
            return null;
        } catch (Exception e) {
            return null;
        }

    }
}