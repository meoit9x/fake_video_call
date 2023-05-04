package nat.pink.base.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import nat.pink.base.model.ObjectSpin;

@Dao
public interface SpinDao {

    @Query("SELECT * FROM spin")
    List<ObjectSpin> getAll();

    @Query("SELECT * FROM spin WHERE isDefault = 1")
    ObjectSpin getSpinDefault();


    @Query("DELETE FROM spin WHERE date = :time")
    void deleteByTime(long time);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ObjectSpin item);

    @Query("UPDATE spin SET isDefault = 0")
    void updateIsDefaultFull();

    @Query("UPDATE spin SET isDefault = 1 WHERE date = :date")
    void updateIsDefault(long date);

}
