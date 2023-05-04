package nat.pink.base.ui.home;

import android.content.Context;

import androidx.core.util.Consumer;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import nat.pink.base.base.BaseViewModel;
import nat.pink.base.dao.DatabaseController;
import nat.pink.base.model.ObjectSpin;
import nat.pink.base.model.ObjectSpinDisplay;
import nat.pink.base.model.ObjectsContentSpin;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeViewModel extends BaseViewModel {

    public MutableLiveData<ObjectSpin> reloadData = new MutableLiveData<>();
    public MutableLiveData<List<ObjectsContentSpin>> reloadDataContent = new MutableLiveData<>();
    public MutableLiveData<HashMap<String, List<ObjectSpinDisplay>>> reloadDataListSpin = new MutableLiveData<>();
    private static String content = "";
    public static final String KEY_SUGGEST = "KEY_SUGGEST";
    public static final String KEY_YOUR_SPIN = "KEY_YOUR_SPIN";

    public void getDefaultSpin(Context context) {
        reloadData.postValue(DatabaseController.getInstance(context).getDefaultSpin());
    }

    public void getContentSpinById(Context context, long id) {
        reloadDataContent.postValue(DatabaseController.getInstance(context).getContentSpinByTime(id));
    }

    public void getSpinAndContent(Context context) {
        List<ObjectSpin> objectSpins = DatabaseController.getInstance(context).getAllSpin();

        List<ObjectSpinDisplay> objectSpinDisplays = new ArrayList<>();
        List<ObjectSpinDisplay> yourObjectSpinDisplays = new ArrayList<>();

        List<ObjectsContentSpin> mListContentSpins = DatabaseController.getInstance(context).getAllContentSpin();
        HashMap<String, List<ObjectSpinDisplay>> stringListHashMap = new HashMap<>();
        objectSpins.forEach(objectSpin -> {
            ObjectSpinDisplay objectSpinDisplay = new ObjectSpinDisplay();
            objectSpinDisplay.setObjectSpin(objectSpin);
            List<ObjectsContentSpin> objectsContentSpins = mListContentSpins.stream().filter(objectsContentSpin -> objectsContentSpin.getDate() == objectSpin.getDate()).collect(Collectors.toList());
            content = "";
            objectsContentSpins.forEach(objectsContentSpin -> {
                content += objectsContentSpin.getContent() + ", ";
            });
            objectSpinDisplay.setContent(content);
            if (objectSpin.isSuggest())
                objectSpinDisplays.add(objectSpinDisplay);
            else
                yourObjectSpinDisplays.add(objectSpinDisplay);
        });
        stringListHashMap.put(KEY_SUGGEST, objectSpinDisplays);
        stringListHashMap.put(KEY_YOUR_SPIN, yourObjectSpinDisplays);
        reloadDataListSpin.postValue(stringListHashMap);
    }


    public void updateIsDefault(Context context, long date) {
        DatabaseController.getInstance(context).updateIsDefault(date);
        getSpinAndContent(context);
        getDefaultSpin(context);
    }

    public void updateSpin(Context context, ObjectSpin objectSpin, List<ObjectsContentSpin> objectsContentSpins) {
        DatabaseController.getInstance(context).updateContentSpins(objectSpin, objectsContentSpins);
        getContentSpinById(context, objectSpin.getDate());
        getSpinAndContent(context);
        getDefaultSpin(context);
    }

    public void insertSpin(Context context, ObjectSpin objectSpin, List<ObjectsContentSpin> objectsContentSpins) {
        DatabaseController.getInstance(context).updateContentSpins(objectSpin, objectsContentSpins);
        getSpinAndContent(context);
    }

    public void deleteContentSpin(Context context, ObjectSpin objectSpin){
        DatabaseController.getInstance(context).deleteContentSpins(objectSpin);
        getSpinAndContent(context);
    }

}
