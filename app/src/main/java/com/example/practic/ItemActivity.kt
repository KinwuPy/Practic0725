package com.example.practic

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ItemActivity : AppCompatActivity() {
    companion object {
        val basketItems = mutableListOf<BasketItem>()
    }

    data class BasketItem(
        val title: String,
        val price: String,
        var quantity: Int = 1,
        val imageId: Int = 0
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_item)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val title: TextView = findViewById(R.id.item_list_title_one)
        val text: TextView = findViewById(R.id.item_list_text)
        val price: TextView = findViewById(R.id.item_list_price_one)
        val image: ImageView = findViewById(R.id.item_list_image_one)
        val buttonBuy: Button = findViewById(R.id.button_buy)
        val linkToBasket: TextView = findViewById(R.id.link_to_basket)

        title.text = intent.getStringExtra("itemTitle")
        text.text = intent.getStringExtra("itemText")
        price.text = intent.getStringExtra("itemPrice")  + "₽"

        val imageId = intent.getIntExtra("itemImage", 0)

        if (imageId != 0) {
            image.setImageResource(imageId)
        }

        buttonBuy.setOnClickListener {
            val itemTitle = title.text.toString()
            val itemPrice = price.text.toString().replace("₽", "").trim()

            // Проверяем, есть ли уже такой товар в корзине
            val existingItem = basketItems.find { it.title == itemTitle }

            if (existingItem != null) {
                // Если товар уже есть - увеличиваем количество
                existingItem.quantity++
                Toast.makeText(this, "Количество товара \"$itemTitle\" увеличено", Toast.LENGTH_SHORT).show()
            } else {
                // Если товара нет - добавляем новый
                basketItems.add(
                    BasketItem(
                        title = itemTitle,
                        price = itemPrice,
                        quantity = 1,
                        imageId = imageId
                    )
                )
                Toast.makeText(this, "Товар \"$itemTitle\" добавлен в корзину", Toast.LENGTH_SHORT).show()
            }
        }
    }
}