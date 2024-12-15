package com.example.myapplication.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface CartDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(item: Cart)

    @Update
    fun update(item: Cart)

    @Delete
    fun delete(item: Cart)

    @Query("DELETE FROM cart_table WHERE supplies_name = :suppliesName")
    fun deleteBySuppliesName(suppliesName: String)

    @get: Query("SELECT * FROM cart_table ORDER BY id ASC")
    val allCart: LiveData<List<Cart>>

    @Query("SELECT EXISTS(SELECT 1 FROM cart_table WHERE supplies_name = :suppliesItem)")
    fun isCart(suppliesItem: String): Boolean

    @Query("DELETE FROM cart_table")
    fun clearCart()

}