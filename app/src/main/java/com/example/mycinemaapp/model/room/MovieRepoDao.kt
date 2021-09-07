package com.example.mycinemaapp.model.room

import androidx.room.*

@Dao
interface MovieRepoDao {
    @Query("SELECT * FROM MovieEntityRoomDto")
    fun getAll(): List<MovieEntityRoomDto>

    @Query("SELECT * FROM MovieEntityRoomDto WHERE id LIKE :id")
    fun getMovieById(id: Int): List<MovieEntityRoomDto>

    @Query("SELECT * FROM MovieEntityRoomDto WHERE character == :character AND id == :id")
    fun getMovieByCharacterAndId(character:String, id: Int): List<MovieEntityRoomDto>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(movieEntityRoomDto: MovieEntityRoomDto)

    @Update
    fun update(movieEntityRoomDto: MovieEntityRoomDto)

    @Delete
    fun delete(movieEntityRoomDto: MovieEntityRoomDto)

    @Delete
    fun deleteAll(movieEntityRoomDto: MovieEntityRoomDto)
}