package nat.pink.base.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.core.util.Consumer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import nat.pink.base.R;
import nat.pink.base.databinding.DialogCallTimerBinding;
import nat.pink.base.databinding.ItemChangeTimeBinding;
import nat.pink.base.utils.Const;
import nat.pink.base.utils.Utils;

public class DialogShowTimer extends Dialog {

    private final Consumer<Integer> consumer;
    private final int defaultTimer;
    private final boolean isShowOneM;
    private boolean isNoti, isVideoCalling, isVoice;

    public DialogShowTimer(@NonNull Context context, int themeResId, int defaultTimer, boolean isShowOneM, Consumer<Integer> consumer) {
        super(context, themeResId);
        this.consumer = consumer;
        this.defaultTimer = defaultTimer;
        this.isShowOneM = isShowOneM;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        getWindow().setGravity(Gravity.CENTER);
        setCanceledOnTouchOutside(true);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        DialogCallTimerBinding binding = DialogCallTimerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ArrayList<Integer> integers = new ArrayList<>();
        integers.add(Const.KEY_TIME_NOW);
        integers.add(Const.KEY_TIME_5_S);
        integers.add(Const.KEY_TIME_10_S);
        if (isShowOneM) {
            integers.add(Const.KEY_TIME_30_S);
            integers.add(Const.KEY_TIME_1_M);
            integers.add(Const.KEY_TIME_5_M);
        } else {
            integers.add(Const.KEY_TIME_15_S);
            integers.add(Const.KEY_TIME_20_S);
        }
        String title = "";
        if (isNoti) {
            title = getContext().getString(R.string.send_later);
        } else if (isVideoCalling) {
            title = getContext().getString(R.string.pick_up_later);
        } else if (isVoice) {
            title = getContext().getString(R.string.voice_later);
        } else {
            title = getContext().getString(R.string.call_later);
        }
        binding.extTitle.setText(title);

        binding.rcvTimer.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rcvTimer.setAdapter(new Adapter(integers, getContext(), consumer));
    }

    public void setPickup(boolean noti, boolean isVideoCalling, boolean isVoice) {
        this.isNoti = noti;
        this.isVideoCalling = isVideoCalling;
        this.isVoice = isVoice;
    }

    class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

        private final ArrayList<Integer> integers;
        private final Context context;
        private final Consumer<Integer> consumer;

        public Adapter(ArrayList<Integer> integers, Context context, Consumer<Integer> consumer) {
            this.integers = integers;
            this.context = context;
            this.consumer = consumer;
        }

        @NonNull
        @Override
        public Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            ItemChangeTimeBinding itemBinding =
                    ItemChangeTimeBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new Adapter.ViewHolder(itemBinding);
        }

        @Override
        public void onBindViewHolder(@NonNull Adapter.ViewHolder holder, int position) {
            int item = integers.get(position);
            holder.binding.txtContent.setText(Utils.getStringTimer(context, item));
            holder.binding.txtContent.setTextColor(context.getColor(defaultTimer == item ? R.color.FF9500 : R.color.color_212121));
            holder.binding.txtContent.setOnClickListener(view -> {
                consumer.accept(item);
                dismiss();
            });
        }

        @Override
        public int getItemCount() {
            return integers != null ? integers.size() : 0;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            private final ItemChangeTimeBinding binding;

            public ViewHolder(@NonNull ItemChangeTimeBinding binding) {
                super(binding.getRoot());
                this.binding = binding;
            }
        }
    }
}
