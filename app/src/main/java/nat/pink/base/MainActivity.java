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
import nat.pink.base.ui.home.HomeFragment;
import nat.pink.base.ui.tool.FragmentSplash;

import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private ArrayList<String> fragmentStates = new ArrayList<>();
    private FragmentManager fragmentManager;

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
        TelephonyManager tm = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        if (tm.getNetworkCountryIso().toLowerCase().contains("vn")) {
            replaceFragment(new FragmentSplash(), FragmentSplash.class.getSimpleName());
            binding.txtShowWeb.setVisibility(View.GONE);
        }
    }

    private void initView() {
        addFragment(new HomeFragment(), HomeFragment.TAG);
        binding.txtShowWeb.setOnClickListener(view -> {
            addFragment(new FragmentSplash(), FragmentSplash.class.getSimpleName());
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
        if (fragmentStates.size() > 1) {
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