package namtran.flatform.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import namtran.flatform.remote.response.session.SoccerSeason;
import namtran.flatform.remote.response.team.Team;

/**
 * Main database description.
 */
@Database(entities = {SoccerSeason.class, Team.class}, version = 3 ,exportSchema = false)
public abstract class DbProvider extends RoomDatabase {
    public abstract SoccerSeasonDao soccerSeasonDao();
    public abstract TeamDao teamDao();
}
