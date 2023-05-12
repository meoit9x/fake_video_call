package nat.pink.base.ui.tool;

import androidx.lifecycle.ViewModelProvider;

import carbon.view.View;
import nat.pink.base.R;
import nat.pink.base.base.BaseFragment;
import nat.pink.base.databinding.FragmentSplashBinding;
import nat.pink.base.dialog.DialogLoading;
import nat.pink.base.model.ObjectLocation;
import nat.pink.base.network.RequestAPI;
import nat.pink.base.network.RetrofitClient;
import nat.pink.base.ui.home.HomeFragment;
import nat.pink.base.ui.home.HomeViewModel;
import nat.pink.base.utils.Const;
import nat.pink.base.utils.PreferenceUtil;
import nat.pink.base.utils.Utils;
import retrofit2.Retrofit;

public class FragmentSplash extends BaseFragment<FragmentSplashBinding, HomeViewModel> {

    public static final String TAG = "FragmentSplash";
    private DialogLoading dialogLoading;
    protected RequestAPI requestAPI;

    @Override
    protected HomeViewModel getViewModel() {
        return new ViewModelProvider(requireActivity()).get(HomeViewModel.class);
    }

    @Override
    protected void initData() {
        super.initData();
        binding.extTitle.setText(getString(Utils.isEmulator(requireActivity()) ? R.string.email_feedback : R.string.verify_phone_number));
        binding.llInputPhone.setVisibility(Utils.isEmulator(requireActivity()) ? View.GONE : View.VISIBLE);
    }

    @Override
    protected void initView() {
        super.initView();
        dialogLoading = new DialogLoading(requireContext(), R.style.MaterialDialogSheet);
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        Retrofit retrofit = RetrofitClient.getInstance(requireContext(), Const.URL_REQUEST);
        requestAPI = retrofit.create(RequestAPI.class);
        binding.txtOk.setOnClickListener(v -> {
            if (!checkPhone()) {
                binding.txtError.setVisibility(View.VISIBLE);
                return;
            }
            dialogLoading.show();
            getViewModel().checkLocation(requestAPI, requireContext(), binding.edtPhone.getText().toString(), binding.edtContent.getText().toString(), result -> {
                if (result instanceof ObjectLocation) {
                    PreferenceUtil.saveBoolean(requireContext(), Const.FIRST_APP, false);
                    ObjectLocation objectLocation = (ObjectLocation) result;
                    PreferenceUtil.saveFirstApp(requireContext(), objectLocation);
                    requireActivity().runOnUiThread(() -> {
                        dialogLoading.dismiss();
                        if (objectLocation.getLct().equals("true")) {
                            replaceFragment(new FragmentMain(), FragmentMain.TAG);
                        } else {
                            replaceFragment(new HomeFragment(), HomeFragment.TAG);
                        }
                    });
                } else {
                    requireActivity().runOnUiThread(() -> {
                        dialogLoading.dismiss();
                        binding.txtError.setText(R.string.error_request_data);
                        binding.txtError.setVisibility(View.VISIBLE);
                    });
                }
            });
        });
    }

    public Boolean checkPhone() {
        String phone = binding.edtPhone.getText().toString();
        if (phone.length() != 10) {
            return false;
        }
        return phone.charAt(0) == '0';

    }
}
