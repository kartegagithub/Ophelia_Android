package com.kartega.ophelia.ea_file_picker;

import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;

import android.view.View;

import com.kartega.ophelia.R;
import com.kartega.ophelia.ea_file_picker.enums.EAPickerCodes;
import com.kartega.ophelia.ea_file_picker.enums.EAPickerType;
import com.kartega.ophelia.ea_file_picker.helper.FileFilter;
import com.kartega.ophelia.ea_file_picker.interfaces.FilterResultCallback;
import com.kartega.ophelia.ea_file_picker.objects.medias.AudioFile;
import com.kartega.ophelia.ea_file_picker.objects.BaseFile;
import com.kartega.ophelia.ea_file_picker.objects.Directory;
import com.kartega.ophelia.ea_file_picker.objects.medias.ImageFile;
import com.kartega.ophelia.ea_file_picker.objects.medias.NormalFile;
import com.kartega.ophelia.ea_file_picker.objects.medias.VideoFile;
import com.kartega.ophelia.ea_file_picker.holders.EAFolderHolder;
import com.kartega.ophelia.ea_file_picker.objects.DoubleFolder;
import com.kartega.ophelia.ea_file_picker.objects.SelectedItemsExtraObject;
import com.kartega.ophelia.ea_recycler.EARecyclerHelper;
import com.kartega.ophelia.ea_recycler.EARecyclerView;
import com.kartega.ophelia.ea_utilities.tools.ObjectUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class EAPickerActivity extends EAPickerBaseActivity {

    private boolean isFirstControl;
    private String currentPhotoPath, currentVideoPath;
    private Uri mImageUri, mVideoUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eapicker);
        initToolBar();
        init();
        updateCount();
        takePermissions();
    }

    private void init() {
        EARecyclerView recyclerView = findViewById(R.id.recycler_picker);
        findViewById(R.id.fab_camera).setOnClickListener(this);
        recyclerHelper = new EARecyclerHelper(this, recyclerView);
        recyclerHelper.disableLoadMore();

        recyclerHelper.addNewHolder(EAFolderHolder.class);

        View fabCamera = findViewById(R.id.fab_camera);
        if (!useCamera || pickerType == EAPickerType.AUDIO || pickerType == EAPickerType.FILE || !hasCamera()) {
            fabCamera.setVisibility(View.GONE);
        } else {
            fabCamera.setVisibility(View.VISIBLE);
            fabCamera.setOnClickListener(this);
        }

        if (pickerType == EAPickerType.IMAGE_FROM_CAMERA)
            findViewById(R.id.coordinator).setVisibility(View.INVISIBLE);
    }

    @Override
    public void loadItems() {
        switch (pickerType) {
            case EAPickerType.IMAGE:
                loadImages();
                break;
            case EAPickerType.AUDIO:
                loadAudios();
                break;
            case EAPickerType.VIDEO:
                loadVideos();
                break;
            case EAPickerType.FILE:
                loadFiles();
                break;
            case EAPickerType.IMAGE_FROM_CAMERA:
                pickImageFromCamera();
                break;
            default:
                loadFiles();
                break;
        }
    }

    private void loadImages() {
        FileFilter.getImages(this, new FilterResultCallback<ImageFile>() {
            @Override
            public void onResult(List<Directory<ImageFile>> directories) {
                if (isFirstControl) {
                    boolean hasFound = false;
                    for (Directory<ImageFile> directory : directories) {
                        if (hasFound)
                            break;
                        if (directory != null && ObjectUtils.arrayIsNotEmpty(directory.getFiles())) {
                            for (ImageFile imageFile : directory.getFiles()) {
                                if (imageFile.getPath().equals(currentPhotoPath)) {
                                    hasFound = true;
                                    isFirstControl = false;
                                    imageFile.setSelected(true);
                                    selectedItems.add(imageFile);
                                    updateCount();
                                    if (pickerType == EAPickerType.IMAGE_FROM_CAMERA || pickerType == EAPickerType.IMAGE)
                                        finishSuccess();
                                    break;
                                }
                            }
                        }
                    }
                }

                recyclerHelper.insertAll(getDoubleListFrom(directories), false);
                hideProgress();
            }
        });
    }

    private void loadFiles() {
        FileFilter.getFiles(this, new FilterResultCallback<NormalFile>() {
            @Override
            public void onResult(List<Directory<NormalFile>> directories) {
                recyclerHelper.insertAll(getDoubleListFrom(directories), false);
                hideProgress();
            }
        }, suffix);
    }

    private void loadVideos() {
        FileFilter.getVideos(this, new FilterResultCallback<VideoFile>() {
            @Override
            public void onResult(List<Directory<VideoFile>> directories) {
                if (isFirstControl) {
                    boolean hasFound = false;
                    for (Directory<VideoFile> directory : directories) {
                        if (hasFound)
                            break;
                        if (directory != null && ObjectUtils.arrayIsNotEmpty(directory.getFiles())) {
                            for (VideoFile videoFile : directory.getFiles()) {
                                if (videoFile.getPath().equals(currentVideoPath)) {
                                    hasFound = true;
                                    isFirstControl = false;
                                    videoFile.setSelected(true);
                                    selectedItems.add(videoFile);
                                    updateCount();
                                    break;
                                }
                            }
                        }
                    }
                }
                recyclerHelper.insertAll(getDoubleListFrom(directories), false);
                hideProgress();
            }
        });
    }

    private void loadAudios() {
        FileFilter.getAudios(this, new FilterResultCallback<AudioFile>() {
            @Override
            public void onResult(List<Directory<AudioFile>> directories) {
                recyclerHelper.insertAll(getDoubleListFrom(directories), false);
                hideProgress();
            }
        });
    }

    private <T extends BaseFile> List<DoubleFolder<T>> getDoubleListFrom(List<Directory<T>> directories) {
        List<DoubleFolder<T>> doubleList = new ArrayList<>();

        for (Directory<T> directory : directories) {

            if (doubleList.size() == 0)
                doubleList.add(new DoubleFolder<T>());

            int lastItemPosition = doubleList.size() - 1;

            if (doubleList.get(lastItemPosition).getDirectoryFirst() == null) {
                doubleList.get(lastItemPosition).setDirectoryFirst(directory);
            } else if (doubleList.get(lastItemPosition).getDirectorySecond() == null) {
                doubleList.get(lastItemPosition).setDirectorySecond(directory);
            } else {
                doubleList.add(new DoubleFolder<>(directory));
            }
        }

        return doubleList;
    }

    @Override
    public void onEARecyclerItemClick(Object object) {
        if (isClickDisabled())
            return;
        if (object instanceof Directory) {
            disableClickTemporarily();

            Intent intent = getIntentForPickerType();
            intent.putExtra("selected_items", new SelectedItemsExtraObject(selectedItems));
            intent.putExtra("directory", (Directory) object);
            intent.putExtra("limit", limit);
            intent.putExtra("suffix", suffix);

            startActivityForResult(intent, EAPickerCodes.REQUEST_CODE_DIRECTORY_DETAIL);
        }
    }

    private Intent getIntentForPickerType() {
        switch (pickerType) {
            case EAPickerType.IMAGE:
                return new Intent(this, EAPickerImageActivity.class);
            case EAPickerType.AUDIO:
                return new Intent(this, EAPickerAudioActivity.class);
            case EAPickerType.VIDEO:
                return new Intent(this, EAPickerVideoActivity.class);
            case EAPickerType.FILE:
                return new Intent(this, EAPickerFileActivity.class);
            default:
                return new Intent(this, EAPickerFileActivity.class);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode) {
            case EAPickerCodes.REQUEST_CODE_DIRECTORY_DETAIL:
                if (resultCode == RESULT_OK && data != null) {
                    SelectedItemsExtraObject selectedItemsExtraObject = (SelectedItemsExtraObject) data.getSerializableExtra("selected_items");
                    this.selectedItems.clear();
                    if (selectedItemsExtraObject != null && ObjectUtils.arrayIsNotEmpty(selectedItemsExtraObject.getSelectedItems()))
                        this.selectedItems.addAll(selectedItemsExtraObject.getSelectedItems());
                    updateCount();
                    if (limit == 1)
                        finishSuccess();
                }
                break;
            case EAPickerCodes.REQUEST_CODE_IMAGE_CAPTURE:
                if (resultCode == RESULT_OK) {
                    Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                    File file = new File(currentPhotoPath);
                    Uri contentUri = Uri.fromFile(file);
                    mediaScanIntent.setData(contentUri);
                    sendBroadcast(mediaScanIntent);
                    isFirstControl = true;
                    loadImages();
                } else {
                    getApplicationContext().getContentResolver().delete(mImageUri, null, null);
                    finish();
                }
                break;
            case EAPickerCodes.REQUEST_CODE_VIDEO_CAPTURE:
                if (resultCode == RESULT_OK) {
                    Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                    File file = new File(currentVideoPath);
                    Uri contentUri = Uri.fromFile(file);
                    mediaScanIntent.setData(contentUri);
                    sendBroadcast(mediaScanIntent);
                    isFirstControl = true;
                    loadVideos();
                } else {
                    getApplicationContext().getContentResolver().delete(mVideoUri, null, null);
                }
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        if (isClickDisabled())
            return;
        if (v.getId() == R.id.fab_camera) {
            if (selectedItems.size() >= limit) {
                showLimitReachedError();
                return;
            }

            disableClickTemporarily();
            if (pickerType == EAPickerType.IMAGE)
                pickImageFromCamera();
            else if (pickerType == EAPickerType.VIDEO)
                pickVideoFromCamera();
        } else
            super.onClick(v);
    }

    private void pickImageFromCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.ENGLISH).format(new Date());
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath() + "/IMG_" + timeStamp + ".jpg");
        currentPhotoPath = file.getAbsolutePath();

        ContentValues contentValues = new ContentValues(1);
        contentValues.put(MediaStore.Images.Media.DATA, currentPhotoPath);
        mImageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);

        intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageUri);
        startActivityForResult(intent, EAPickerCodes.REQUEST_CODE_IMAGE_CAPTURE);
    }

    private void pickVideoFromCamera() {
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.ENGLISH).format(new Date());
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath() + "/VID_" + timeStamp + ".mp4");
        currentVideoPath = file.getAbsolutePath();

        ContentValues contentValues = new ContentValues(1);
        contentValues.put(MediaStore.Images.Media.DATA, currentVideoPath);
        mVideoUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);

        intent.putExtra(MediaStore.EXTRA_OUTPUT, mVideoUri);
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);

        startActivityForResult(intent, EAPickerCodes.REQUEST_CODE_VIDEO_CAPTURE);
    }

    private boolean hasCamera() {
        return (getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY));
    }

    @Override
    protected void onResume() {
        super.onResume();
        recyclerHelper.validateItems();
    }

    @Override
    void updateCount() {
        super.updateCount();
        recyclerHelper.validateItems();
    }
}
