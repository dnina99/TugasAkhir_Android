package umn.ac.id.tugasakhir_android.ui.menu_list;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MenuListViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public MenuListViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is dashboard fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}