package com.example.andersentask4contacts

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var contactAdapter: ContactAdapter
    private lateinit var contactsList: List<Contact>
    private lateinit var searchView: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        searchView = findViewById(R.id.searchView)
        recyclerView.addItemDecoration(DividerItemDecoration(this))



        recyclerView.layoutManager = LinearLayoutManager(this)

        contactsList = generateContacts(100).toMutableList()
        contactAdapter = ContactAdapter(contactsList, this)
        recyclerView.adapter = contactAdapter

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                filter(query.orEmpty())
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                filter(newText.orEmpty())
                return false
            }
        })


    }

    private fun generateRandomName(): String {
        val names = listOf("Alice", "Bob", "Charlie", "Olivia", "Emma", "Noah", "Sophia", "William", "James", "Isabella")
        val randomIndex = (0 until names.size).random()
        return names[randomIndex]
    }

    private fun generateRandomSurname(): String {
        val surnames = listOf("Smith", "Johnson", "Williams", "Brown", "Jones", "Miller", "Davis", "Garcia", "Rodriguez", "Wilson")
        val randomIndex = (0 until surnames.size).random()
        return surnames[randomIndex]
    }

    private fun generateContacts(size: Int): List<Contact> {
        val randomList = mutableListOf<Contact>()
        for (i in 0 until size) {
            val randomName = generateRandomName()
            val randomSurname = generateRandomSurname()
            val randomImageUrl = "https://picsum.photos/200?random=$i"
            val contact = Contact(randomName, randomSurname, randomImageUrl)
            randomList.add(contact)
        }
        return randomList
    }


    private fun filter(text: String) {
        val filteredList = contactsList.filter {
           it.name.contains(text, ignoreCase = true) || it.surname.contains(text, ignoreCase = true)
        }
        contactAdapter.updateList(filteredList)

    }
}