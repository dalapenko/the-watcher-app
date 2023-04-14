package tech.dalapenko.thewatcher.data.local

import androidx.room.*
import tech.dalapenko.thewatcher.data.local.dbo.MovieDbo

@Dao
interface MovieDao {

    @Query("SELECT * FROM movie_table ORDER BY popularity DESC")
    suspend fun getPopular(): List<MovieDbo>

    @Query("SELECT * FROM movie_table ORDER BY voteAverage DESC, voteCount DESC")
    suspend fun getTopRated(): List<MovieDbo>

    @Query("SELECT * FROM movie_table ORDER BY releaseDate DESC, voteAverage DESC, voteCount DESC")
    suspend fun getNowPlaying(): List<MovieDbo>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(vararg movie: MovieDbo)

    @Query("DELETE FROM movie_table")
    suspend fun deleteAll()
}