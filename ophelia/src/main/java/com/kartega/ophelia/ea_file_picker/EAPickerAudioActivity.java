package com.kartega.ophelia.ea_file_picker;

import android.os.Bundle;

import com.kartega.ophelia.R;
import com.kartega.ophelia.ea_file_picker.holders.EASingleRowHolder;
import com.kartega.ophelia.ea_utilities.tools.ObjectUtils;

public class EAPickerAudioActivity extends EAPickerBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picker_common);
        initToolBar();
        initRecyclerView();
        updateCount();

        recyclerHelper.addNewHolder(EASingleRowHolder.class);

        if (directory == null || !ObjectUtils.arrayIsNotEmpty(directory.getFiles())) {
            finishCancelled();
        } else
            takePermissions();
    }

    @Override
    public void loadItems() {
        recyclerHelper.insertAll(directory.getFiles(),false);
        hideProgress();
    }
}

