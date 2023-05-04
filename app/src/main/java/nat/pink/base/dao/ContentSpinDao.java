package nat.pink.base.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import nat.pink.base.model.ObjectsContentSpin;

@Dao
public interface ContentSpinDao {

    @Query("SELECT * FROM contentspin")
    List<ObjectsContentSpin> getAll();

    @Query("SELECT * FROM contentspin WHERE date = :time")
    List<ObjectsContentSpin> getContentSpinByTime(long time);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ObjectsContentSpin objectsContentSpin);

    @Query("DELETE FROM contentspin WHERE date = :time")
    void deleteContents(long time);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<ObjectsContentSpin> objectsContentSpin);
}
