package com.example.capiter.module.cart.data.db

import androidx.room.*
import com.example.capiter.module.cart.domain.entity.Cart
import io.reactivex.rxjava3.core.Single

@Dao
interface CartDAO {
    @Query("SELECT * FROM cart")
    fun find(): Single<List<Cart>>

    @Query("SELECT * FROM cart WHERE `product-id` = :productId")
    fun findOne(productId: String): Cart

    @Query("SELECT EXISTS(SELECT * FROM cart WHERE `product-id` = :productId)")
    fun exists(productId: String): Boolean

    @Insert
    fun insert(cart: Cart): Long

    @Update
    fun update(cart: Cart): Int

    @Delete
    fun delete(cart: Cart)

    @Query("DELETE FROM cart")
    fun delete()
}