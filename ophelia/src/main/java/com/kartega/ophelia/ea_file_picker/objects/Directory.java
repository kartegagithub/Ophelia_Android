package com.kartega.ophelia.ea_file_picker.objects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vincent Woo
 * Date: 2016/10/10
 * Time: 17:31
 */

public class Directory<T> implements Serializable {
    private String id;
    private String name;
    private String path;
    private int count;
    private List<T> files = new ArrayList<>();

    public Directory() {
        count = 0;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<T> getFiles() {
        return files;
    }

    public void setFiles(List<T> files) {
        this.files = files;
    }

    public void addFile(T file) {
        files.add(file);
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Directory)) return false;

//        Directory directory = (Directory) o;
//        boolean hasId = !TextUtils.isEmpty(id);
//        boolean otherHasId = !TextUtils.isEmpty(directory.id);
//
//        if (hasId && otherHasId) {
//            if (!TextUtils.equals(id, directory.id)) {
//                return false;
//            }
//
//            return TextUtils.equals(name, directory.name);
//        }
//
//        return false;

        Directory directory = (Directory) o;
        return this.path.equals(directory.path);
    }

    @Override
    public int hashCode() {
//        if (TextUtils.isEmpty(id)) {
//            if (TextUtils.isEmpty(name)) {
//                return 0;
//            }
//
//            return name.hashCode();
//        }
//
//        int result = id.hashCode();
//
//        if (TextUtils.isEmpty(name)) {
//            return result;
//        }
//
//        result = 31 * result + name.hashCode();
//        return result;

        return path.hashCode();
    }
}
