package ir.mohsenafshar.apps.mytmdb.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ir.mohsenafshar.apps.mytmdb.data.local.entity.MovieItem

@Database(entities = [MovieItem::class], version = 1)
@TypeConverters(DataConverter::class)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}
