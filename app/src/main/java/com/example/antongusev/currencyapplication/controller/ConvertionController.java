package com.example.antongusev.currencyapplication.controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.StyleableRes;
import android.util.Log;

import com.example.antongusev.currencyapplication.R;
import com.example.antongusev.currencyapplication.model.ValCurs;
import com.example.antongusev.currencyapplication.model.Valute;
import com.example.antongusev.currencyapplication.task.DownloadTask;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.simpleframework.xml.strategy.Type;
import org.simpleframework.xml.strategy.Visitor;
import org.simpleframework.xml.strategy.VisitorStrategy;
import org.simpleframework.xml.stream.HyphenStyle;
import org.simpleframework.xml.stream.InputNode;
import org.simpleframework.xml.stream.NodeMap;
import org.simpleframework.xml.stream.OutputNode;
import org.simpleframework.xml.stream.Style;

import java.io.Reader;
import java.io.StringReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;

/**
 * Created by antongusev on 05.04.17.
 */

public class ConvertionController {

    public static final String TAG = "ConversionController";

    private static ConvertionController instance;

    private Context context;

    private final Map<String, Valute> cache = new HashMap();

    private ConvertionController(Context context) {
        this.context = context;
    }

    private ConvertionController() {
    }

    public static ConvertionController getInstance(Context context) {
        if (instance == null) {
            instance = new ConvertionController(context);
        }
        return instance;
    }

    public String convert(String value, String charCodeInput, String charCodeOutput) {
        if (value == null || value.isEmpty())
            return null;
        Valute inputValute = cache.get(charCodeInput);
        Valute outputValute = cache.get(charCodeOutput);
        return inputValute.getCoast().multiply(new BigDecimal(value)).divide(outputValute.getCoast(), 2, RoundingMode.HALF_UP).toString();
    }

    public void initial() {
        try {
            String prefs = null;
            new DownloadTask().execute(context);
            SharedPreferences sharedPreferences = context.getSharedPreferences(context.getString(R.string.currency_application_prefs), Context.MODE_PRIVATE);
            while (prefs == null) {
                prefs = sharedPreferences.getString(context.getString(R.string.rates), null);
            }
            Serializer serializer = new Persister();
            Reader reader = new StringReader(prefs);
            ValCurs valCurs = serializer.read(ValCurs.class, reader, false);
            Valute rubles = new Valute();
            rubles.setCharCode("RUB");
            rubles.setNominal(new BigDecimal(1));
            rubles.setValue("1");
            valCurs.getValuteList().add(rubles);
            for (Valute valute : valCurs.getValuteList()) {
                cache.put(valute.getCharCode(), valute);
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }

    public Object[] getCacheKeySet() {
        return cache.keySet().toArray();
    }

}
