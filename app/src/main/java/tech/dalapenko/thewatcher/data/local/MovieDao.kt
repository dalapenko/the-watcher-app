package tech.dalapenko.thewatcher.data.local

import androidx.room.*
import tech.dalapenko.thewatcher.data.model.Movie

@Dao
interface MovieDao {

    @Query("SELECT * FROM movie_table ORDER BY popularity DESC")
    suspend fun getPopular(): List<Movie>

    @Query("SELECT * FROM movie_table ORDER BY voteAverage DESC, voteCount DESC")
    suspend fun getTopRated(): List<Movie>

    @Query("SELECT * FROM movie_table ORDER BY releaseDate DESC, voteAverage DESC, voteCount DESC")
    suspend fun getNowPlaying(): List<Movie>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(vararg movie: Movie)

    @Query("DELETE FROM movie_table")
    suspend fun deleteAll()
}