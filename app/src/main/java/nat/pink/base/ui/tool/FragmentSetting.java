package nat.pink.base.ui.tool;

import androidx.lifecycle.ViewModelProvider;

import nat.pink.base.base.BaseFragment;
import nat.pink.base.databinding.FragmentSettingBinding;
import nat.pink.base.ui.home.HomeViewModel;

public class FragmentSetting extends BaseFragment<FragmentSettingBinding, HomeViewModel> {
    @Override
    protected HomeViewModel getViewModel() {
        return new ViewModelProvider(requireActivity()).get(HomeViewModel.class);
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        binding.llPolicy.setOnClickListener(view -> replaceFragment(new FragmentPolicy(),FragmentPolicy.class.getSimpleName()));
        binding.llIntro.setOnClickListener(view -> replaceFragment(new FragmentIntro(),FragmentIntro.class.getSimpleName()));
        binding.imgBack.setOnClickListener(view -> backStackFragment());
    }
}
