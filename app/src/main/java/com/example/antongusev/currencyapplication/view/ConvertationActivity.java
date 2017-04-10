package com.example.antongusev.currencyapplication.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.antongusev.currencyapplication.R;
import com.example.antongusev.currencyapplication.controller.ConvertionController;

public class ConvertationActivity extends AppCompatActivity {

    private ConvertionController convertionController = ConvertionController.getInstance(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.convertation_main);
        convertionController.initial();
        final EditText inputValue = (EditText) findViewById(R.id.inputValue);
        final TextView resultView = (TextView) findViewById(R.id.result);
        final ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, convertionController.getCacheKeySet());
        final Spinner inputCurrency = (Spinner) findViewById(R.id.inputCurrency);
        final Spinner resultCurrency = (Spinner) findViewById(R.id.resultCurrency);
        final Button changeButton = (Button) findViewById(R.id.changeButton);
        inputCurrency.setAdapter(adapter);
        resultCurrency.setAdapter(adapter);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        changeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int inputPosition = inputCurrency.getSelectedItemPosition();
                inputCurrency.setSelection(resultCurrency.getSelectedItemPosition(), true);
                resultCurrency.setSelection(inputPosition, true);
                setValue(inputValue, inputCurrency, resultCurrency, resultView);
            }
        });
        inputValue.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                return setValue(v, inputCurrency, resultCurrency, resultView);
            }
        });
    }

    private boolean setValue(TextView view, Spinner inputSpinner, Spinner outputSpinner, TextView resultView) {
        String result = convertionController.convert(view.getText().toString(), (String) inputSpinner.getSelectedItem(), (String) outputSpinner.getSelectedItem());
        if (result != null)
            resultView.setText(result);
        return true;
    }
}
