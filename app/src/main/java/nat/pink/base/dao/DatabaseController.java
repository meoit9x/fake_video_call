package nat.pink.base.dao;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import nat.pink.base.R;
import nat.pink.base.model.ObjectSpin;
import nat.pink.base.model.ObjectsContentSpin;
import nat.pink.base.utils.Const;

public class DatabaseController {

    private static DatabaseController databaseController;
    private AppDatabase appDatabase;
    private Context context;
    private List<ObjectsContentSpin> objectsContentSpins = new ArrayList<>();

    public static DatabaseController getInstance(Context context) {
        if (databaseController == null) {
            databaseController = new DatabaseController(context);
        }
        return databaseController;
    }

    public DatabaseController(Context context) {
        appDatabase = AppDatabase.getInstance(context);
        this.context = context;
    }

    public List<ObjectsContentSpin> getContentSpinByTime(long id) {
        if (appDatabase != null) {
            return appDatabase.getContentSpinDao().getContentSpinByTime(id);
        }
        return new ArrayList<>();
    }

    public List<ObjectsContentSpin> getAllContentSpin() {
        if (appDatabase != null) {
            return appDatabase.getContentSpinDao().getAll();
        }
        return new ArrayList<>();
    }

    public List<ObjectSpin> getAllSpin() {
        if (appDatabase != null) {
            return appDatabase.getImageDao().getAll();
        }
        return new ArrayList<>();
    }

    public ObjectSpin getDefaultSpin() {
        if (appDatabase != null) {
            return appDatabase.getImageDao().getSpinDefault();
        }
        return new ObjectSpin();
    }

    public void initDefaultDatabase() {
        initDataStep1();

    }

    private void initDataStep1() {
        ObjectSpin objectSpin = new ObjectSpin();
        long date = System.currentTimeMillis();
        objectSpin.setDate(date);
        objectSpin.setDefault(true);
        objectSpin.setTypespin(Const.TYPE_SPIN_ALL);
        objectSpin.setSuggest(true);
        objectSpin.setTitle(context.getString(R.string.title_default_1));
        appDatabase.getImageDao().insert(objectSpin);

        List<String> strings = new ArrayList<>();
        strings.add(context.getString(R.string.content_default_1));
        strings.add(context.getString(R.string.content_default_2));
        strings.add(context.getString(R.string.content_default_3));
        strings.add(context.getString(R.string.content_default_4));
        strings.add(context.getString(R.string.content_default_5));
        strings.add(context.getString(R.string.content_default_6));
        for (int i = 0; i < strings.size(); i++) {
            ObjectsContentSpin objectsContentSpin = new ObjectsContentSpin();
            objectsContentSpin.setId(date + i);
            objectsContentSpin.setContent(strings.get(i));
            objectsContentSpin.setDate(date);
            objectSpin.setSuggest(true);
            objectSpin.setDate(date);
            objectSpin.setDefault(true);
            objectsContentSpins.add(objectsContentSpin);
        }
        appDatabase.getContentSpinDao().insertAll(objectsContentSpins);
    }

    public void updateIsDefault(long date) {
        if (appDatabase != null) {
            appDatabase.getImageDao().updateIsDefaultFull();
            appDatabase.getImageDao().updateIsDefault(date);
        }
    }

    public void updateContentSpins(ObjectSpin objectSpin, List<ObjectsContentSpin> objectsContentSpins) {
        if (appDatabase != null) {
            appDatabase.getContentSpinDao().deleteContents(objectSpin.getDate());
            appDatabase.getContentSpinDao().insertAll(objectsContentSpins);
            appDatabase.getImageDao().insert(objectSpin);
        }
    }

    public void deleteContentSpins(ObjectSpin objectSpin){
        if (appDatabase != null) {
            appDatabase.getContentSpinDao().deleteContents(objectSpin.getDate());
            appDatabase.getImageDao().deleteByTime(objectSpin.getDate());
        }
    }

}
