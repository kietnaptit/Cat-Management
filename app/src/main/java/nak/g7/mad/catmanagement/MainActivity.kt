package nak.g7.mad.catmanagement

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import nak.g7.mad.catmanagement.adapter.CatRecyclerAdapter
import nak.g7.mad.catmanagement.adapter.ImageAdapter
import nak.g7.mad.catmanagement.db.DBHandler
import nak.g7.mad.catmanagement.model.Cat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val dbHandler = DBHandler(this)
        var editCatID = 0

        //Xử lí các trường
        val nameField = findViewById<EditText>(R.id.inNameField)
        val priceField = findViewById<EditText>(R.id.inPriceField)
        val desField = findViewById<EditText>(R.id.inDesField)
        val btnAdd = findViewById<Button>(R.id.btnAdd)
        val btnUpdate = findViewById<Button>(R.id.btnUpdate)

        //Xử lí phần pick ảnh
        val imgSpinner = findViewById<Spinner>(R.id.catImageSpinner)

        //Pick ảnh bằng tên
//        val imageNames = resources.getStringArray(R.array.cat_images)
//        val imgAdapter: ArrayAdapter<String> = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, imageNames)
//        imgAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//        imgSpinner.setAdapter(imgAdapter)

        //Pick ảnh bằng ảnh trực tiếp
        val imageNames = arrayOf<Int>(R.drawable.cat1, R.drawable.cat2, R.drawable.cat3, R.drawable.cat4)
        val imgAdapter = ImageAdapter(this, imageNames)
        imgSpinner.adapter = imgAdapter

        //Xử lí phần hiển thị danh sách mèo
        val catListView = findViewById<RecyclerView>(R.id.catList)
        val catList : List<Cat> = dbHandler.getAlLCats()
        var catAdapter = CatRecyclerAdapter(catList){ cat ->
            editCatID = cat.id
            nameField.setText(cat.name)
            priceField.setText(cat.price.toString())
            desField.setText(cat.des)
        }

        val catLayoutManager = LinearLayoutManager(applicationContext)
        catListView.layoutManager = catLayoutManager
        catListView.itemAnimator = DefaultItemAnimator()
        catListView.adapter = catAdapter

        btnAdd.setOnClickListener {
            val id : Int = 123456
            val name = nameField.text.toString()
            val price = Integer.parseInt(priceField.text.toString()).toLong()
            val des = desField.text.toString()
            val selectedPosition = imgSpinner.getSelectedItemPosition()
//            val imageID = resources.getIdentifier(imageNames[selectedPosition], "drawable", packageName)
            val imageID = imageNames[selectedPosition]
            val cat = Cat(id, name,  price, des, imageID)
            if(dbHandler.addNewCat(cat)){
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }else{
                val rootView = findViewById<View>(android.R.id.content)
                val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(rootView.getWindowToken(), 0)
                Toast.makeText(this, "Some error occured", Toast.LENGTH_SHORT).show()
            }

        }

        btnUpdate.setOnClickListener {
            val id : Int = 123456
            val name = nameField.text.toString()
            val price = Integer.parseInt(priceField.text.toString()).toLong()
            val des = desField.text.toString()
            val selectedPosition = imgSpinner.getSelectedItemPosition()
//            val imageID = resources.getIdentifier(imageNames[selectedPosition], "drawable", packageName)
            val imageID = imageNames[selectedPosition]
            val cat = Cat(id, name,  price, des, imageID)
            if(dbHandler.editACat(cat, editCatID)){
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }else{
                val rootView = findViewById<View>(android.R.id.content)
                val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(rootView.getWindowToken(), 0)
                Toast.makeText(this, "Some error occured", Toast.LENGTH_SHORT).show()
            }
        }

        catAdapter.setOnDeleteClickListener { cat ->
            if(dbHandler.deleteACat(cat.id)){
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }else{
                val rootView = findViewById<View>(android.R.id.content)
                val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(rootView.getWindowToken(), 0)
                Toast.makeText(this, "Some error occured", Toast.LENGTH_SHORT).show()
            }

        }



    }
}