package tech.dalapenko.thewatcher.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import tech.dalapenko.thewatcher.data.model.Movie

@Dao
interface MovieDao {

    @Query("SELECT * FROM movie_table ORDER BY popularity DESC")
    fun getPopular(): LiveData<List<Movie>>

    @Query("SELECT * FROM movie_table ORDER BY voteAverage DESC, voteCount DESC")
    fun getTopRated(): LiveData<List<Movie>>

    @Query("SELECT * FROM movie_table ORDER BY releaseDate DESC, voteAverage DESC, voteCount DESC")
    fun getNowPlaying(): LiveData<List<Movie>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(vararg movie: Movie)
}