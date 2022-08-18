package com.example.todo


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.room.Room
import kotlinx.android.synthetic.main.activity_create_card.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class CreateCard : AppCompatActivity() {
    private lateinit var database: myDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_card)
        database = Room.databaseBuilder(
            applicationContext, myDatabase::class.java, "To_Do"
        ).build()
        save_button.setOnClickListener {
            if (create_title.text.toString().trim { it <= ' ' }.isNotEmpty()
                && create_priority.text.toString().trim { it <= ' ' }.isNotEmpty()
            ) {
                if(create_priority.text.toString().toLowerCase()=="high" || create_priority.text.toString().toLowerCase()=="low" || create_priority.text.toString().toLowerCase()=="medium"){
                    var title = create_title.getText().toString()
                    var priority = create_priority.getText().toString()
                    DataObject.setData(title, priority)
                    GlobalScope.launch {
                        database.dao().insertTask(Entity(0, title, priority))

                    }

                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }
                else{
                    Toast.makeText(this, "Kindly Enter Correct Priority", Toast.LENGTH_SHORT).show()
                }

            }
            else{
                Toast.makeText(this, "Kindly Fill Both Fields", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

