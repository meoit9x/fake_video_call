package nat.pink.base.model;

import android.view.View;

public class ObjectSpinDisplay {

    public ObjectSpinDisplay() {
    }

    private String content;
    private boolean isCheck;
    private ObjectSpin objectSpin;
    private View view;

    public enum TYPE_SELECT {
        TYPE_MENU, TYPE_ONCLICK
    }

    private TYPE_SELECT typeSelect;

    public TYPE_SELECT getTypeSelect() {
        return typeSelect;
    }

    public void setTypeSelect(TYPE_SELECT typeSelect) {
        this.typeSelect = typeSelect;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public ObjectSpin getObjectSpin() {
        return objectSpin;
    }

    public void setObjectSpin(ObjectSpin objectSpin) {
        this.objectSpin = objectSpin;
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }
}
