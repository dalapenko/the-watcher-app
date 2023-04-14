package tech.dalapenko.thewatcher.data.local

import androidx.room.*
import tech.dalapenko.thewatcher.data.local.dbo.GenreDbo
import tech.dalapenko.thewatcher.data.local.dbo.MovieDbo
import tech.dalapenko.thewatcher.data.local.dbo.MovieGenreCrossRefDbo
import tech.dalapenko.thewatcher.data.local.dbo.MovieWithGenreDbo

@Dao
interface MovieDao {

    @Transaction
    @Query("SELECT * FROM movies ORDER BY popularity DESC")
    suspend fun getPopular(): List<MovieWithGenreDbo>

    @Transaction
    @Query("SELECT * FROM movies ORDER BY voteAverage DESC, voteCount DESC")
    suspend fun getTopRated(): List<MovieWithGenreDbo>

    @Transaction
    @Query("SELECT * FROM movies ORDER BY releaseDate DESC, voteAverage DESC, voteCount DESC")
    suspend fun getNowPlaying(): List<MovieWithGenreDbo>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(vararg movie: MovieDbo)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGenre(vararg genre: GenreDbo)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieWithGenre(vararg movieGenreCrossRef: MovieGenreCrossRefDbo)

    @Query("DELETE FROM movies")
    suspend fun deleteAll()
}