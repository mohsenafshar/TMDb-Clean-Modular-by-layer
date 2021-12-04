package ir.mohsenafshar.apps.mytmdb.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ir.mohsenafshar.apps.mytmdb.data.local.entity.MovieItem
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Query("SELECT * FROM movie_item LIMIT :pageSize OFFSET :pageIndex")
    fun getMovieList(pageIndex: Int, pageSize: Int = 20): Flow<List<MovieItem>>

//    suspend fun getTopRated(pageNo: Int): List<MovieItem>
//    suspend fun getDetail(id: Long): MovieDetail

    @Insert
    suspend fun insertAll(vararg movies: MovieItem)

}