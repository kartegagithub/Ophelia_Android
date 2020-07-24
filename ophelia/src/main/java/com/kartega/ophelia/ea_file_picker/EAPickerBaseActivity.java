package com.kartega.ophelia.ea_file_picker;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.kartega.ophelia.EABaseActivity;
import com.kartega.ophelia.R;
import com.kartega.ophelia.ea_file_picker.enums.EAPickerType;
import com.kartega.ophelia.ea_file_picker.interfaces.PickerInformationListener;
import com.kartega.ophelia.ea_file_picker.objects.BaseFile;
import com.kartega.ophelia.ea_file_picker.objects.Directory;
import com.kartega.ophelia.ea_file_picker.interfaces.PermissionControlsCompleteListener;
import com.kartega.ophelia.ea_file_picker.objects.SelectedItemsExtraObject;
import com.kartega.ophelia.ea_recycler.EARecyclerHelper;
import com.kartega.ophelia.ea_recycler.EARecyclerView;
import com.kartega.ophelia.ea_recycler.interfaces.EARecyclerClickListener;
import com.kartega.ophelia.ea_utilities.interfaces.PermissionListener;
import com.kartega.ophelia.ea_utilities.tools.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ahmet Kılıç on 18.04.2019.
 * Copyright © 2019. All rights reserved.
 * For the full copyright and license information,
 * please view the LICENSE file that was distributed with this source code.
 */
public abstract class EAPickerBaseActivity extends EABaseActivity implements PermissionListener, EARecyclerClickListener, PermissionControlsCompleteListener, PickerInformationListener {

    String[] suffix;
    EARecyclerHelper recyclerHelper;
    Directory<BaseFile> directory;
    List<BaseFile> selectedItems;
    int limit;
    boolean useCamera;
    private TextView tvSelectionCount;
    @EAPickerType
    int pickerType;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getExtras();
    }

    public void initRecyclerView() {
        EARecyclerView recyclerView = findViewById(R.id.recycler_picker);
        recyclerHelper = new EARecyclerHelper(this, recyclerView);
        recyclerHelper.disableLoadMore();
    }

    @SuppressWarnings("all")
    private void getExtras() {
        suffix = getIntent().getStringArrayExtra("suffix");
        limit = getIntent().getIntExtra("limit", 1);
        pickerType = getIntent().getIntExtra("picker_type", EAPickerType.FILE);
        useCamera = getIntent().getBooleanExtra("use_camera", false);
        SelectedItemsExtraObject selectedItemsExtraObject = (SelectedItemsExtraObject) getIntent().getSerializableExtra("selected_items");

        if (selectedItemsExtraObject != null && ObjectUtils.arrayIsNotEmpty(selectedItemsExtraObject.getSelectedItems()))
            selectedItems = selectedItemsExtraObject.getSelectedItems();
        else
            selectedItems = new ArrayList<>();

        if (limit == 1)
            selectedItems = new ArrayList<>();

        directory = (Directory) getIntent().getSerializableExtra("directory");

        if (directory != null && ObjectUtils.arrayIsNotEmpty(directory.getFiles()))
            for (Object item : directory.getFiles()) {
                if (item instanceof BaseFile) {
                    if (selectedItems.contains(item))
                        ((BaseFile) item).setSelected(true);
                    else
                        ((BaseFile) item).setSelected(false);
                }
            }
    }

    void takePermissions() {
        List<String> permissions = new ArrayList<>();
        if (useCamera || pickerType == EAPickerType.IMAGE_FROM_CAMERA) {
            permissions.add(Manifest.permission.CAMERA);
            permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        checkPermissions(permissions, this);
    }

    void initToolBar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        tvSelectionCount = findViewById(R.id.tv_picker_count);
        findViewById(R.id.iv_toolbar_back).setOnClickListener(this);
        if (limit == 1)
            findViewById(R.id.tv_picker_done).setVisibility(View.GONE);
        else
            findViewById(R.id.tv_picker_done).setOnClickListener(this);
        setSupportActionBar(toolbar);
    }

    void updateCount() {
        String countText = String.valueOf(selectedItems.size());
        if (limit > 1)
            countText += " / " + limit;
        else
            tvSelectionCount.setVisibility(View.INVISIBLE);

        tvSelectionCount.setText(countText);
    }

    @Override
    public void onEARecyclerItemClick(Object object) {
        if (object instanceof BaseFile) {

            if (((BaseFile) object).isSelected()) {
                if (!selectedItems.contains(object))
                    selectedItems.add((BaseFile) object);
            } else
                selectedItems.remove(object);

            if (limit == 1)
                finishSuccess();
        }

        updateCount();
    }

    @Override
    public void onEARecyclerItemClick(Object object, int position) {

    }

    @Override
    public void onEARecyclerItemClick(Object object, int position, int type) {

    }

    @Override
    public void onPermissionsGranted() {
        onPermissionControlsCompleted();
    }

    @Override
    public void onPermissionsDenied(List<String> deniedPermissions) {
        if (deniedPermissions.contains(Manifest.permission.CAMERA) && !deniedPermissions.contains(Manifest.permission.READ_EXTERNAL_STORAGE)) {
            useCamera = false;
            onPermissionControlsCompleted();
        } else {
            showToast(R.string.ea_picker_error_permission_required_file_access);
            finishCancelled();
        }
    }

    @Override
    public void onPermissionControlsCompleted() {
        showProgress();
        loadItems();
    }

    public void loadItems() {
        //Override this in picker activities to load items after permission control
    }

    @Override
    public void hideProgress() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                EAPickerBaseActivity.super.hideProgress();
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (isClickDisabled())
            return;
        if (v.getId() == R.id.tv_picker_done) {
            disableClickTemporarily();
            finishSuccess();
        } else if (v.getId() == R.id.iv_toolbar_back) {
            disableClickTemporarily();
            finishCancelled();
        } else
            super.onClick(v);
    }

    void finishCancelled() {
        setResult(RESULT_CANCELED);
        finish();
    }

    void finishSuccess() {
        Intent intent = new Intent();
        intent.putExtra("selected_items", new SelectedItemsExtraObject(selectedItems));
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public boolean isLimitAvailable() {
        return limit > selectedItems.size();
    }

    @Override
    public void showLimitReachedError() {
        showToast(R.string.ea_picker_limit_reached);
    }

    @Override
    public int getLimit() {
        return limit;
    }

    @Override
    public List<BaseFile> getSelectedItems() {
        return selectedItems;
    }
}
