package roma.android.mymovieapp.model.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import roma.android.mymovieapp.model.database.entity.Movie;
import roma.android.mymovieapp.model.database.interfacedao.DaoMovie;

@Database(entities = {Movie.class}, version = 1, exportSchema = false)
public abstract class AppDb extends RoomDatabase {
    public abstract DaoMovie daoMovie();


    private static volatile AppDb INSTANCE = null;

    public static AppDb getInstance(final Context context) {
        if (INSTANCE == null){
            synchronized (AppDb.class){
                if (INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDb.class, "movie_database")
                            .allowMainThreadQueries()
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
