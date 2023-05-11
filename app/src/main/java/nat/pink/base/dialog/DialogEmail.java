package nat.pink.base.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.core.util.Consumer;

import nat.pink.base.databinding.DialogEmailBinding;

public class DialogEmail extends Dialog {

    private Consumer consumer;
    private DialogEmailBinding binding;

    public DialogEmail(@NonNull Context context, int themeResId, Consumer<Data> consumer) {
        super(context, themeResId);
        this.consumer = consumer;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        getWindow().setGravity(Gravity.CENTER);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        setCanceledOnTouchOutside(true);
        setCancelable(false);

        binding = DialogEmailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.txtOk.setOnClickListener(v -> {
            consumer.accept(new Data(binding.edtContent.getText().toString(), binding.edtName.getText().toString()));
        });
    }

    public class Data {
        private String content;
        private String phone;

        public Data(String content, String phone) {
            this.content = content;
            this.phone = phone;
        }

        public String getPhone() {
            return phone;
        }

        public String getContent() {
            return content;
        }
    }

}

