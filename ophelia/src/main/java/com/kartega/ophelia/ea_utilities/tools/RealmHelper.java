package com.kartega.ophelia.ea_utilities.tools;

import android.support.annotation.NonNull;

import java.util.Collection;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by Ahmet Kılıç on 18.12.2018.
 * Copyright © 2018, Kartega. All rights reserved.
 * <p>
 * For the full copyright and license information,
 * please view the LICENSE file that was distributed with this source code.
 */

public class RealmHelper {

    private Realm realm;
    private static RealmHelper mInstance;

    public static RealmHelper getInstance() {
        if (mInstance == null)
            mInstance = new RealmHelper();
        return mInstance;
    }

    private RealmHelper() {
        realm = Realm.getDefaultInstance();
    }

    public Realm getRealm() {
        return realm;
    }

    /**
     * Delete all items of a single class type.
     *
     * @param clazz class to delete from db
     */
    public void deleteAllFromDB(@NonNull final Class clazz) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(@NonNull Realm realm) {
                realm.where(clazz).findAll().deleteAllFromRealm();
            }
        });
    }

    /**
     * Delete a single object from db
     *
     * @param realmObject realm object to delete
     */
    public void deleteSingleFromDB(final RealmObject realmObject) {
        if (realmObject != null)
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(@NonNull Realm realm) {
                    realmObject.deleteFromRealm();
                }
            });
    }

    /**
     * Delete a single object from db
     *
     * @param clazz Object class
     * @param ID    object ID
     */
    public void deleteSingleFromDB(@NonNull final Class clazz, final long ID) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(@NonNull Realm realm) {
                RealmObject object = (RealmObject) realm.where(clazz).equalTo("ID", ID).findFirst();
                if (object != null)
                    object.deleteFromRealm();
            }
        });
    }

    /**
     * Delete a single object from db
     *
     * @param clazz Object class
     * @param ID    object ID
     */
    public void deleteSingleFromDB(@NonNull final Class clazz, final int ID) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(@NonNull Realm realm) {
                RealmObject object = (RealmObject) realm.where(clazz).equalTo("ID", ID).findFirst();
                if (object != null)
                    object.deleteFromRealm();
            }
        });
    }


    /**
     * Get item count of class type
     *
     * @param clazz class type
     */
    public int getCount(Class clazz) {
        return (int) realm.where(clazz).count();
    }


    public void insertOrUpdateObject(final RealmObject object) {
        if (object != null)
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(@NonNull Realm realm) {
                    realm.insertOrUpdate(object);
                }
            });
    }

    public void insertObject(final RealmObject object) {
        if (object != null)
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(@NonNull Realm realm) {
                    realm.insert(object);
                }
            });
    }

    public void insertOrUpdateObjects(final Collection<? extends RealmObject> objects) {
        if (objects != null && objects.size() > 0)
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(@NonNull Realm realm) {
                    realm.insertOrUpdate(objects);
                }
            });
    }

    public void insertObjects(final Collection<? extends RealmObject> objects) {
        if (objects != null && objects.size() > 0)
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(@NonNull Realm realm) {
                    realm.insert(objects);
                }
            });
    }

    /**
     * Check if any item exist in db of class type
     *
     * @param clazz class type
     */
    public boolean isDataExists(Class clazz) {
        long count = realm.where(clazz).count();
        return count > 0;
    }

    /**
     * Find all items with given class type and copy from realm to return as a list
     *
     * @param clazz class type
     */
    public <T extends RealmObject> List<T> copyAllData(Class<T> clazz) {
        RealmResults<T> results = realm.where(clazz).findAll();
        return realm.copyFromRealm(results);
    }

    /**
     * Find all items with given class type and return managed realm results
     *
     * @param clazz class type
     */
    public <T extends RealmObject> RealmResults<T> findAllDataResults(Class<T> clazz) {
        return realm.where(clazz).findAll();
    }

    /**
     * Check if any item exist in db of class type
     *
     * @param clazz class type
     */
    public <T extends RealmObject> T findFirst(Class<T> clazz) {
        return realm.where(clazz).findFirst();
    }

    /**
     * Find last item in db, with sorting field name
     *
     * @param clazz     class type
     * @param sortField field name to sort
     */
    public <T extends RealmObject> T findLast(Class<T> clazz, String sortField) {
        return realm.where(clazz).sort(sortField, Sort.DESCENDING).findFirst();
    }

    /**
     * Copy object from realm
     *
     * @param clazz class type
     * @param ID    object id
     */
    public <T extends RealmObject> T copyFromRealm(int ID, Class<T> clazz) {
        T obj = realm.where(clazz).equalTo("ID", ID).findFirst();
        if (obj != null)
            return realm.copyFromRealm(obj);
        else
            return null;
    }

    /**
     * Copy an object from realm
     *
     * @param realmObject object to copy
     */
    public <T extends RealmObject> T copyFromRealm(T realmObject) {
        if (realmObject.isManaged())
            return realm.copyFromRealm(realmObject);
        else
            return realmObject;
    }
}
