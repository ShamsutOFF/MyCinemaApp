package com.example.mycinemaapp.model.room

import androidx.room.*

@Dao
interface MovieRepoDao {
    @Query("SELECT * FROM MovieEntityRoomDto")
    fun getAll(): List<MovieEntityRoomDto>

    @Query("SELECT * FROM MovieEntityRoomDto WHERE id LIKE :id")
    fun getMovieById(id: Int): List<MovieEntityRoomDto>

    @Query("SELECT * FROM MovieEntityRoomDto WHERE character LIKE :character AND id LIKE :id")
    fun getMovieByCharacterAndId(character:String, id: Int): List<MovieEntityRoomDto>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(movieEntityRoomDto: MovieEntityRoomDto)

    @Query("DELETE FROM MovieEntityRoomDto WHERE character LIKE :character AND id LIKE :id")
    fun delete(character:String, id: Int)

    @Delete
    fun deleteAll(movieEntityRoomDto: MovieEntityRoomDto)
}