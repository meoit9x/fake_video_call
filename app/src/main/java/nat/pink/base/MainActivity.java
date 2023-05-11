package nat.pink.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import nat.pink.base.databinding.ActivityMainBinding;
import nat.pink.base.dialog.DialogEmail;
import nat.pink.base.dialog.DialogLoading;
import nat.pink.base.model.ObjectLocation;
import nat.pink.base.network.RequestAPI;
import nat.pink.base.network.RetrofitClient;
import nat.pink.base.ui.home.HomeFragment;
import nat.pink.base.ui.home.HomeViewModel;
import nat.pink.base.ui.tool.FragmentSplash;
import nat.pink.base.utils.Const;
import nat.pink.base.utils.PreferenceUtil;
import retrofit2.Retrofit;

import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private ArrayList<String> fragmentStates = new ArrayList<>();
    private FragmentManager fragmentManager;
    private HomeViewModel homeViewModel;
    protected RequestAPI requestAPI;
    private DialogLoading dialogLoading;
    private DialogEmail dialogEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        fragmentManager = getSupportFragmentManager();
        initView();
        initData();
    }

    private void initData() {
        homeViewModel = new HomeViewModel();
        Retrofit retrofit = RetrofitClient.getInstance(this, Const.URL_REQUEST);
        requestAPI = retrofit.create(RequestAPI.class);
        if (PreferenceUtil.getBoolean(this, Const.FIRST_APP, true)) {
            dialogEmail = new DialogEmail(this, R.style.MaterialDialogSheet, item -> {
                dialogLoading.show();
                homeViewModel.checkLocation(requestAPI, this, item.getPhone(), item.getContent(), result -> {
                    dialogLoading.dismiss();
                    PreferenceUtil.saveBoolean(this, Const.FIRST_APP, false);
                    if (result instanceof ObjectLocation) {
                        ObjectLocation objectLocation = (ObjectLocation) result;
                        PreferenceUtil.saveFirstApp(this, objectLocation);
                        if (objectLocation.getLct().equals("true")) {
                            replaceFragment(new FragmentSplash(), FragmentSplash.TAG);
                            binding.txtShowWeb.setVisibility(View.GONE);
                            dialogEmail.dismiss();
                        } else
                            replaceFragment(new HomeFragment(), HomeFragment.TAG);
                    }
                });
            });
            dialogEmail.show();
        }
        ObjectLocation objectLocation = PreferenceUtil.getFirstApp(this);
        if (objectLocation != null) {
            if (objectLocation.getLct().equals("true")) {
                replaceFragment(new FragmentSplash(), FragmentSplash.TAG);
                binding.txtShowWeb.setVisibility(View.GONE);
            } else {
                replaceFragment(new HomeFragment(), HomeFragment.TAG);
            }
            return;
        }
        replaceFragment(new HomeFragment(), HomeFragment.TAG);
    }

    private void initView() {

        dialogLoading = new DialogLoading(this, R.style.MaterialDialogSheet);
        binding.txtShowWeb.setOnClickListener(view -> {
            addFragment(new FragmentSplash(), FragmentSplash.TAG);
        });
    }

    public void replaceFragment(Fragment fragment, String tag) {
        fragmentManager.beginTransaction()
                .replace(R.id.content, fragment, tag)
                .addToBackStack(tag)
                .commit();
        if (!fragmentStates.contains(tag))
            fragmentStates.add(tag);
    }

    public void addFragment(Fragment fragment, String tag) {
        fragmentManager.beginTransaction()
                .add(R.id.content, fragment, tag)
                .addToBackStack(tag)
                .commit();
        if (!fragmentStates.contains(tag))
            fragmentStates.add(tag);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (fragmentStates.size() > 1 && !fragmentStates.get(fragmentStates.size() - 2).contains(FragmentSplash.TAG)) {
            getSupportFragmentManager().popBackStack(fragmentStates.get(fragmentStates.size() - 1), FragmentManager.POP_BACK_STACK_INCLUSIVE);
            fragmentStates.remove(fragmentStates.size() - 1);
            return;
        }
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        for (Fragment item : getSupportFragmentManager().getFragments()) {
            item.onActivityResult(requestCode, resultCode, data);
        }
    }
}