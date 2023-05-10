package nat.pink.base.ui.tool;

import android.content.Intent;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProvider;

import nat.pink.base.base.BaseFragment;
import nat.pink.base.databinding.FragmentGtBinding;
import nat.pink.base.ui.home.HomeViewModel;

public class FragmentGT extends BaseFragment<FragmentGtBinding, HomeViewModel> {
    @Override
    protected HomeViewModel getViewModel() {
        return new ViewModelProvider(requireActivity()).get(HomeViewModel.class);
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        binding.trueGt.setOnClickListener( view -> {
            if(binding.check.isChecked()){
                replaceFragment(new FragmentLG(),FragmentLG.class.getSimpleName());
            }else {
                Toast.makeText(requireContext(), "Bạn chưa đồng ý !", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
