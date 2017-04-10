package com.example.antongusev.currencyapplication.model;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

/**
 * Created by antongusev on 05.04.17.
 */
@Root
public class ValCurs {

    @ElementList(name = "ValCurs", inline = true)
    private List<Valute> valuteList;


    public List<Valute> getValuteList() {
        return valuteList;
    }

    public void setValuteList(List<Valute> valuteList) {
        this.valuteList = valuteList;
    }
}
