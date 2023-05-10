package nat.pink.base.ui.tool;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Handler;

import androidx.lifecycle.ViewModelProvider;

import nat.pink.base.base.BaseFragment;
import nat.pink.base.databinding.FragmentSplashBinding;
import nat.pink.base.ui.home.HomeViewModel;

public class FragmentSplash extends BaseFragment<FragmentSplashBinding, HomeViewModel> {

    @Override
    protected HomeViewModel getViewModel() {
        return new ViewModelProvider(requireActivity()).get(HomeViewModel.class);
    }

    @Override
    protected void initData() {
        super.initData();
        new Handler().postDelayed(() -> {
            SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("sysdata", MODE_PRIVATE);
            String phonepass = sharedPreferences.getString("phonepass", "0");
            if (phonepass.equals("1")) {
                addFragment(new FragmentMain(), FragmentMain.class.getSimpleName());
            } else {
                addFragment(new FragmentLG(), FragmentLG.class.getSimpleName());
            }

        }, 1000);
    }
}
