package com.example.antongusev.currencyapplication.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.math.BigDecimal;

/**
 * Created by antongusev on 05.04.17.
 */
@Root(name = "Valute")
public class Valute {

    @Element(name = "CharCode")
    private String charCode;

    @Element(name = "Nominal")
    private BigDecimal nominal;

    private BigDecimal value;

    private BigDecimal coast;

    public BigDecimal getNominal() {
        return nominal;
    }

    public void setNominal(BigDecimal nominal) {
        this.nominal = nominal;
    }

    public String getCharCode() {
        return charCode;
    }

    public void setCharCode(String charCode) {
        this.charCode = charCode;
    }

    public BigDecimal getBigDecimalValue() {
        return value;
    }

    @Element(name = "Value")
    public String getValue() {
        return this.value.toString();
    }

    @Element(name = "Value")
    public void setValue(String value) {
        this.value = new BigDecimal(value.replaceAll(",", "."));
        this.coast = this.value.divide(nominal);
    }

    public BigDecimal getCoast() {
        return coast;
    }
}
