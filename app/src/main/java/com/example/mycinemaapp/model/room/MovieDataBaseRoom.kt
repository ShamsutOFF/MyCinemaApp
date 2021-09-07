package com.example.mycinemaapp.model.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    version = 1,
    entities = [MovieEntityRoomDto::class]
)
abstract class MovieDataBaseRoom : RoomDatabase() {
    abstract fun MovieRepoDao(): MovieRepoDao
}