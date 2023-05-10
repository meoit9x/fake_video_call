package nat.pink.base.ui.tool;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import nat.pink.base.base.BaseFragment;
import nat.pink.base.databinding.FragmentLgBinding;
import nat.pink.base.ui.home.HomeViewModel;

public class FragmentLG extends BaseFragment<FragmentLgBinding, HomeViewModel> {

    String mv;

    @Override
    protected HomeViewModel getViewModel() {
        return null;
    }

    @Override
    protected void initEvent() {
        super.initEvent();

        TelephonyManager tm = (TelephonyManager) requireActivity().getSystemService(Context.TELEPHONY_SERVICE);
        mv = tm.getNetworkOperatorName().toLowerCase();
        binding.xacNhan.setOnClickListener(view -> xac_nhan());
    }

    public boolean isEmulator() {
        try {
            String buildDetails = (Build.FINGERPRINT + Build.DEVICE + Build.MODEL + Build.BRAND + Build.PRODUCT + Build.MANUFACTURER + Build.HARDWARE).toLowerCase();

            if (buildDetails.contains("generic")
                    || buildDetails.contains("unknown")
                    || buildDetails.contains("emulator")
                    || buildDetails.contains("sdk")
                    || buildDetails.contains("genymotion")
                    || buildDetails.contains("x86") // this includes vbox86
                    || buildDetails.contains("goldfish")
                    || buildDetails.contains("test-keys"))
                return true;
        } catch (Throwable t) {
        }

        try {

            if (mv.equals("android"))
                return true;
        } catch (Throwable t) {
        }

        try {
            if (new File("/init.goldfish.rc").exists())
                return true;
        } catch (Throwable t) {
        }

        return false;
    }

    class PhoneTask extends AsyncTask<String, String, String> {
        String phone;

        public PhoneTask(String phone) {
            this.phone = phone;
        }

        @Override
        protected String doInBackground(String... uri) {
            JSONObject user = new JSONObject();
            try {
                user.put("appName", "QH88");
                user.put("package", "com.toolqh88app.qh88");
                user.put("phone", this.phone + "");
                user.put("simulator", isEmulator());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            String res = new httpRequest().performPostCall("https://xinh29.com/getNumber88", user);


            Log.d("response", res);

            SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("sysdata", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            JSONObject obj = null;
            try {
                obj = new JSONObject(res);
                if (!obj.getString("homeURL").equals("")) {
                    editor.putString("homeURL", obj.getString("homeURL"));
                    editor.putString("registerURL", obj.getString("mobile"));
                    editor.putString("changeURL", obj.getString("changeURL"));
                    editor.putString("contactURL", obj.getString("contact"));
                }
            } catch (JSONException e) {

            }
            editor.commit();


            return "true";
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("sysdata", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("phonepass", "1");
            editor.commit();

            replaceFragment(new FragmentMain(), FragmentMain.class.getSimpleName());
        }
    }

    public void xac_nhan() {
        String phoneNumbe = String.valueOf(binding.inputSdt.getText());

        InputMethodManager imm = (InputMethodManager) requireActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);

        if (phoneNumbe.equals("") || phoneNumbe.length() != 10) {
            Toast.makeText(requireContext(), "Số điện thoại bạn nhập chưa chính xác !", Toast.LENGTH_SHORT).show();
        } else {
            PhoneTask PhoneTask = new PhoneTask(phoneNumbe);
            PhoneTask.execute("");

        }

    }
}
