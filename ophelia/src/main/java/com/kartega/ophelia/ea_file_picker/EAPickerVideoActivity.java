package com.kartega.ophelia.ea_file_picker;

import android.os.Bundle;

import com.kartega.ophelia.R;
import com.kartega.ophelia.ea_file_picker.holders.EADoubleMediaHolder;
import com.kartega.ophelia.ea_file_picker.objects.DoubleMedia;
import com.kartega.ophelia.ea_file_picker.objects.medias.VideoFile;
import com.kartega.ophelia.ea_utilities.tools.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

public class EAPickerVideoActivity extends EAPickerBaseActivity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picker_common);
        initToolBar();
        initRecyclerView();
        updateCount();

        recyclerHelper.addNewHolder(EADoubleMediaHolder.class);

        if (directory == null || !ObjectUtils.arrayIsNotEmpty(directory.getFiles())) {
            finishCancelled();
        } else
            takePermissions();
    }

    @Override
    public void loadItems() {
        List<DoubleMedia> doubleList = new ArrayList<>();
        for (Object item : directory.getFiles()) {
            if (item instanceof VideoFile) {
                if (doubleList.size() == 0)
                    doubleList.add(new DoubleMedia());

                int lastItemPosition = doubleList.size() - 1;

                if (doubleList.get(lastItemPosition).getFirstItem() == null) {
                    doubleList.get(lastItemPosition).setFirstItem((VideoFile) item);
                } else if (doubleList.get(lastItemPosition).getSecondItem() == null) {
                    doubleList.get(lastItemPosition).setSecondItem((VideoFile) item);
                } else {
                    doubleList.add(new DoubleMedia((VideoFile) item));
                }
            }
        }

        recyclerHelper.insertAll(doubleList, false);
        hideProgress();
    }
}
