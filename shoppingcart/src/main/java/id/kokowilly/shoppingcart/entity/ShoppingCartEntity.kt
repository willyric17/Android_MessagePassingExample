package id.kokowilly.shoppingcart.entity

import com.google.gson.annotations.SerializedName
import kotlin.random.Random

data class ShoppingCartEntity(
    @SerializedName("id")
    val id: Long = Random.nextLong(0L, Long.MAX_VALUE),
    @SerializedName("items")
    val items: MutableList<ItemEntity> = ArrayList()
)


data class ItemEntity(
    @SerializedName("id")
    val id: Long,
    @SerializedName("name")
    val name: String,
    @SerializedName("quantity")
    val quantity: Long
)