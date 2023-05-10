package nat.pink.base.ui.tool;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.telephony.TelephonyManager;

import androidx.lifecycle.ViewModelProvider;

import nat.pink.base.base.BaseFragment;
import nat.pink.base.databinding.FragmentMainBinding;
import nat.pink.base.ui.home.HomeViewModel;

public class FragmentMain extends BaseFragment<FragmentMainBinding, HomeViewModel> {
    @Override
    protected HomeViewModel getViewModel() {
        return new ViewModelProvider(requireActivity()).get(HomeViewModel.class);
    }

    String countryCodeValue, PACKAGE_NAME,
            HomeURL,
            RegisterURL,
            ContactURL,
            ChangeURL, non;

    @Override
    protected void initData() {
        super.initData();
        TelephonyManager tm = (TelephonyManager) requireActivity().getSystemService(Context.TELEPHONY_SERVICE);
        countryCodeValue = tm.getNetworkCountryIso();
        non = tm.getNetworkOperatorName().toLowerCase();
        PACKAGE_NAME = requireActivity().getPackageName();

        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("sysdata", MODE_PRIVATE);
        HomeURL = sharedPreferences.getString("homeURL", "");
        RegisterURL = sharedPreferences.getString("registerURL", "");
        ContactURL = sharedPreferences.getString("contactURL", "");
        ChangeURL = sharedPreferences.getString("changeURL", "");
        binding.homeRl.setOnClickListener(view -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(HomeURL));
            startActivity(browserIntent);
        });
        binding.supportRl.setOnClickListener(view -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(ContactURL));
            startActivity(browserIntent);
        });
        binding.changeURa.setOnClickListener(view -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(ChangeURL));
            startActivity(browserIntent);
        });
        binding.setupRl.setOnClickListener(view -> {
            addFragment(new FragmentSetting(), FragmentSetting.class.getSimpleName());
        });
    }
}
