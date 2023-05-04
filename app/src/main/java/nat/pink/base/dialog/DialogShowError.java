package nat.pink.base.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.core.util.Consumer;

import nat.pink.base.databinding.DialogShowErrorBinding;

public class DialogShowError extends Dialog {
    private Consumer consumer;
    private DialogShowErrorBinding binding;
    private Paint paint;

    public DialogShowError(@NonNull Context context, int themeResId, Consumer consumer) {
        super(context, themeResId);
        this.consumer = consumer;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        getWindow().setGravity(Gravity.CENTER);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        binding = DialogShowErrorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        paint = binding.extOk.getPaint();
        float width = paint.measureText(binding.extOk.getText().toString());

        Shader textShader = new LinearGradient(0f, 0f, width, binding.extOk.getTextSize(), new int[]{
                Color.parseColor("#E35D93"),
                Color.parseColor("#AF39EF"),
                Color.parseColor("#3877FB")
        }, null, Shader.TileMode.REPEAT);
        paint.setShader(textShader);


        binding.extOk.setOnClickListener(v -> {
            consumer.accept(new Object());
            dismiss();
        });
    }


}
