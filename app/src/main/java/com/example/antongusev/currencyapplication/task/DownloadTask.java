package com.example.antongusev.currencyapplication.task;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.example.antongusev.currencyapplication.R;
import com.example.antongusev.currencyapplication.model.ValCurs;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Reader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by antongusev on 05.04.17.
 */

public class DownloadTask extends AsyncTask<Context, Void, Void> {

    public static final String TAG = "DownloadTask";

    @Override
    protected Void doInBackground(Context... params) {
        try {
            Context context = params[0];
            URL url = new URL(context.getResources().getString(R.string.url));
            Log.i(TAG, "Start downloading");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            BufferedInputStream bis = new BufferedInputStream(urlConnection.getInputStream());
            ByteArrayOutputStream buf = new ByteArrayOutputStream();
            int result = bis.read();
            while(result != -1) {
                buf.write((byte) result);
                result = bis.read();
            }
            SharedPreferences sharedPreferences = context.getSharedPreferences(context.getString(R.string.currency_application_prefs), Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(context.getString(R.string.rates), buf.toString("Cp1251"));
            editor.commit();
            Log.i(TAG, "Finish downloading");
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        } finally {
            return null;
        }
    }

}
