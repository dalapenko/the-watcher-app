package tech.dalapenko.thewatcher.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import tech.dalapenko.thewatcher.data.local.dbo.GenreDbo
import tech.dalapenko.thewatcher.data.local.dbo.MovieDbo
import tech.dalapenko.thewatcher.data.local.dbo.MovieGenreCrossRefDbo

@Database(
    entities = [
        MovieDbo::class,
        GenreDbo::class,
        MovieGenreCrossRefDbo::class
    ],
    version = 1,
    exportSchema = false
)
abstract class MovieDatabase : RoomDatabase() {

    abstract fun getMovieDao(): MovieDao

    companion object {

        @Volatile
        private var instance: MovieDatabase? = null

        fun getInstance(context: Context): MovieDatabase {
            if (instance == null) {
                synchronized(this) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        MovieDatabase::class.java,
                        "movie_database"
                    ).build()
                }
            }

            return instance!!
        }
    }
}