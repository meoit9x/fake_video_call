package nat.pink.base.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Entity(tableName = "contentspin")
public class ObjectsContentSpin implements Serializable {

    @PrimaryKey()
    @NonNull
    @ColumnInfo(name = "id")
    @SerializedName("id")
    private long id;


    @NonNull
    @ColumnInfo(name = "date")
    @SerializedName("date")
    private long date;

    @NonNull
    @ColumnInfo(name = "content")
    @SerializedName("content")
    private String content;

    public ObjectsContentSpin(long id, long date, @NonNull String content) {
        this.id = id;
        this.date = date;
        this.content = content;
    }

    public ObjectsContentSpin() {
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    @NonNull
    public String getContent() {
        return content;
    }

    public void setContent(@NonNull String content) {
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
