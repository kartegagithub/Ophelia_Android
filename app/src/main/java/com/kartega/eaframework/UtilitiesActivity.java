package com.kartega.eaframework;

import android.os.Bundle;
import android.widget.TextView;


import com.kartega.eaframework.object.Car;
import com.kartega.eaframework.object.Human;
import com.kartega.eaframework.object.InstanceTestItem;
import com.kartega.eaframework.object.TempObject;
import com.kartega.ophelia.EABaseActivity;
import com.kartega.ophelia.ea_utilities.enums.DateStyle;
import com.kartega.ophelia.ea_utilities.tools.TimeUtils;
import com.kartega.ophelia.ea_utilities.views.DatePickerTextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class UtilitiesActivity extends EABaseActivity {

    //Todo CustomViews and vs
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_utilities);

        TextView textView = findViewById(R.id.tv_date);
        DatePickerTextView datePickerTextView = findViewById(R.id.date_time);
        datePickerTextView.setDateStyle(DateStyle.LONG);
        textView.setText(TimeUtils.getFormattedDateString(new Date(), DateStyle.MEDIUM, DateStyle.SHORT));
    }

    @Override
    public void saveLogIfNeeded(int type, String message) {

    }

    private InstanceTestItem getInstanceTestItem() {
        InstanceTestItem item1 = new InstanceTestItem();
        item1.setId(5);
        item1.setName("Ahmet");
        item1.setFilled(true);
        item1.setTempObject(new TempObject("Ece"));

        HashMap<String, TempObject> map = new HashMap<>();
        map.put("Hello", new TempObject("Vestel"));

        item1.setMap(map);

        ArrayList<Human> humans = new ArrayList<>();
        humans.add(new Human("Ayşe", new Car("Audi")));

        item1.setHumans(humans);

        List<String> items = new ArrayList<>();
        items.add("Kılıç");
        item1.setItems(items);

        HashMap<String, HashMap<String, String>> otherMap = new HashMap<>();
        HashMap<String, String> innerMap = new HashMap<>();
        innerMap.put("Hi", "This is inner map");
        otherMap.put("InnerMapTest", innerMap);

        item1.setOtherMap(otherMap);
        return item1;
    }
}
