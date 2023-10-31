package nak.g7.mad.catmanagement.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import nak.g7.mad.catmanagement.adapter.CatRecyclerAdapter
import nak.g7.mad.catmanagement.model.Cat

class DBHandler(context: Context) {
    private val dbHelper : DBHelper = DBHelper(context)
    //CRUD Create, Read, Update, Delete
    fun getAlLCats() : List<Cat>{
        val db = dbHelper.readableDatabase
        var cats = ArrayList<Cat>()
        var cursor : Cursor? = null
        try{
            val query = "SELECT * FROM ${DBHelper.TABLE_NAME}"
            cursor = db.rawQuery(query, null)
            if(cursor.moveToFirst()){
                do{
                    val id = cursor.getInt(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_ID))
                    val name = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_NAME))
                    val price = cursor.getLong(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_PRICE))
                    val des = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_DES))
                    val imageID = cursor.getInt(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_IMAGE))
                    val cat = Cat(id, name, price, des, imageID)
                    cats.add(cat)
                }while (cursor.moveToNext())
            }
        }catch (e : SQLException){
            e.printStackTrace()
        }finally {
            cursor?.close()
        }
        return cats
    }

    fun addNewCat(cat : Cat) : Boolean{
        var result = false
        val db = dbHelper.writableDatabase
        val values = ContentValues()
        values.put(DBHelper.COLUMN_NAME, cat.name)
        values.put(DBHelper.COLUMN_PRICE, cat.price)
        values.put(DBHelper.COLUMN_DES, cat.des)
        values.put(DBHelper.COLUMN_IMAGE, cat.imageID)
        val i : Long? = db?.insert(DBHelper.TABLE_NAME,null,values)
        if(i?.toInt() != -1){
            result = true
        }
        return result
    }

    fun editACat(cat: Cat, catID: Int) :Boolean{
        var result = false
        val db = dbHelper.writableDatabase
        val values = ContentValues()
        values.put(DBHelper.COLUMN_NAME, cat.name)
        values.put(DBHelper.COLUMN_PRICE, cat.price)
        values.put(DBHelper.COLUMN_DES, cat.des)
        values.put(DBHelper.COLUMN_IMAGE, cat.imageID)
        val i = db?.update(DBHelper.TABLE_NAME, values, DBHelper.COLUMN_ID + "=?", arrayOf(catID.toString()))
        if(i != -1){
            result = true
        }
        return result
    }

    fun deleteACat(catID : Int) :Boolean{
        var result = false
        val db = dbHelper.writableDatabase
        val i = db?.delete(DBHelper.TABLE_NAME, DBHelper.COLUMN_ID + "=?", arrayOf(catID.toString()))
        if(i != -1){
            result = true
        }
        return result
    }
}