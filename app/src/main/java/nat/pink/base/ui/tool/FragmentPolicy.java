package nat.pink.base.ui.tool;

import androidx.lifecycle.ViewModelProvider;

import nat.pink.base.base.BaseFragment;
import nat.pink.base.databinding.FragmentPolicyBinding;
import nat.pink.base.ui.home.HomeViewModel;

public class FragmentPolicy extends BaseFragment<FragmentPolicyBinding, HomeViewModel> {

    @Override
    protected HomeViewModel getViewModel() {
        return new ViewModelProvider(requireActivity()).get(HomeViewModel.class);
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        binding.imgBack.setOnClickListener(view -> {
            backStackFragment();
        });
    }
}
