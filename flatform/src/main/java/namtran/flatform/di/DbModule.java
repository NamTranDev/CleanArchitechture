package namtran.flatform.di;

import android.app.Application;
import android.arch.persistence.room.Room;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import namtran.flatform.db.DbProvider;
import namtran.flatform.db.SoccerSeasonDao;

@Module
public class DbModule {

    @Singleton
    @Provides
    DbProvider provideDb(Application app) {
        return Room.databaseBuilder(app, DbProvider.class,"clean architechture.db").fallbackToDestructiveMigration()
                .build();
    }

    @Singleton
    @Provides
    SoccerSeasonDao provideUserDao(DbProvider db) {
        return db.soccerSeasonDao();
    }
}
