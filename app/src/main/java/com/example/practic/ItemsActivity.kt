package com.example.practic

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.SearchView

class ItemsActivity : AppCompatActivity() {
    private lateinit var adapter: ItemsAdapter
    private lateinit var originalItems: List<Item>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_items)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val itemsList: RecyclerView = findViewById(R.id.itemsList)
        val searchView: SearchView = findViewById(R.id.searchView)

        // Инициализация списка товаров
        originalItems = listOf(
            Item(1, "mis", "Ноутбук MSI GF76 Katana 11UEK-604XR",
                "английская/русская раскладка, 1920x1080, IPS, Intel Core i5-11400H, ядра: 6, RAM 16 ГБ, SSD 512 ГБ, GeForce RTX 3060 для ноутбуков 6 ГБ, без ОС",
                "Ноутбук MSI GF76 Katana 11UEK-604XRU с диагональю экрана 17.3\" создан для решения ресурсоемких задач...",
                81999),
            Item(1, "andor", "Ноутбук ARDOR GAMING NEO N15-I5ND410",
                "английская/русская раскладка, 1920x1080, IPS, Intel Core i5-12450H, ядра: 4 + 4, RAM 16 ГБ, SSD 512 ГБ, GeForce RTX 2050 для ноутбуков 4 ГБ, без ОС",
                "Игровой ноутбук ARDOR GAMING NEO N15-I5ND410 в черном пластиковом корпусе оснащен полноразмерной клавиатурой...",
                56999),
            Item(1, "acer", "Ноутбук Acer Nitro V15 ANV15-41",
                "английская/русская раскладка, 1920x1080, IPS, AMD Ryzen 5 7535HS, ядра: 6, RAM 8 ГБ, SSD 512 ГБ, GeForce RTX 2050 для ноутбуков 4 ГБ, без ОС",
                "Ноутбук Acer Nitro V15 ANV15-41 оснащен клавиатурой с интегрированной подсветкой...",
                59999),
            Item(1, "machenike", "Ноутбук Machenike S15 Quazar D",
                "английская/русская раскладка, 1920x1080, IPS, Intel Core i5-12450H, ядра: 4 + 4, RAM 16 ГБ, SSD 512 ГБ, GeForce RTX 3050 для ноутбуков 4 ГБ, без ОС",
                "Ноутбук основан на платформе S15, отличающейся сбалансированной спецификацией для гейминга...",
                59999),
            Item(1, "maibenben", "Ноутбук MAIBENBEN X17A",
                "английская/русская раскладка, 1920x1080, IPS, AMD Ryzen 5 6600H, ядра: 6, RAM 16 ГБ, SSD 512 ГБ, GeForce RTX 3050 для ноутбуков 4 ГБ, Linux",
                "Ноутбук MAIBENBEN X17A с процессором AMD Ryzen 5 6600H и видеокартой GeForce RTX 3050...",
                62999)
        )

        // Настройка RecyclerView
        itemsList.layoutManager = LinearLayoutManager(this)
        adapter = ItemsAdapter(originalItems.toMutableList(), this)
        itemsList.adapter = adapter

        // Настройка поиска
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterItems(newText.orEmpty())
                return true
            }
        })
    }

    private fun filterItems(query: String) {
        val filteredList = if (query.isEmpty()) {
            originalItems
        } else {
            originalItems.filter {
                it.title.contains(query, ignoreCase = true) ||
                        it.desc.contains(query, ignoreCase = true) ||
                        it.text.contains(query, ignoreCase = true)
            }
        }
        adapter.updateList(filteredList)
    }
}