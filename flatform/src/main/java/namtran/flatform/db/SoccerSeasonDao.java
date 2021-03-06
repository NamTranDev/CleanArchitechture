package namtran.flatform.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import io.reactivex.Flowable;
import namtran.flatform.remote.response.session.SoccerSeason;

/**
 * Interface for database access for User related operations.
 */
@Dao
public interface SoccerSeasonDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<SoccerSeason> season);

    @Query("SELECT * FROM soccerseason")
    Flowable<List<SoccerSeason>> fetchSoccerSeason();
}
