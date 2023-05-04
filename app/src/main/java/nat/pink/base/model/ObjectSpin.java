package nat.pink.base.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Entity(tableName = "spin")
public class ObjectSpin implements Serializable {

    @PrimaryKey()
    @NonNull
    @ColumnInfo(name = "date")
    @SerializedName("date")
    private long date;

    @NonNull
    @ColumnInfo(name = "title")
    @SerializedName("title")
    private String title;

    @NonNull
    @ColumnInfo(name = "typespin")
    @SerializedName("typespin")
    private String typespin;

    @NonNull
    @ColumnInfo(name = "suggest")
    @SerializedName("suggest")
    private boolean suggest;

    @NonNull
    @ColumnInfo(name = "isDefault")
    @SerializedName("isDefault")
    private boolean isDefault;

    public ObjectSpin(long date, @NonNull String title, @NonNull String typespin, boolean suggest, boolean isDefault) {
        this.date = date;
        this.title = title;
        this.typespin = typespin;
        this.suggest = suggest;
        this.isDefault = isDefault;
    }

    public ObjectSpin() {
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    @NonNull
    public String getTitle() {
        return title;
    }

    public void setTitle(@NonNull String title) {
        this.title = title;
    }

    @NonNull
    public String getTypespin() {
        return typespin;
    }

    public void setTypespin(@NonNull String typespin) {
        this.typespin = typespin;
    }

    public boolean isSuggest() {
        return suggest;
    }

    public void setSuggest(boolean suggest) {
        this.suggest = suggest;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
    }
}
