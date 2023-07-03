package pl.mikorosa.kolos.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import pl.mikorosa.kolos.TempModel
import pl.mikorosa.kolos.TransactionsModel

class DatabaseConnector(context: Context, factory: SQLiteDatabase.CursorFactory?): SQLiteOpenHelper(context, "WebCo", factory, 1) {
    override fun onCreate(db: SQLiteDatabase) {
        val query = ("CREATE TABLE temp ("
                + "id INTEGER PRIMARY KEY, " +
                "liters REAL," +
                "price REAL" + ")")

        db.execSQL(query)

        val query2 = ("CREATE TABLE transactions ("
                + "id INTEGER PRIMARY KEY, " +
                "liters REAL," +
                "price REAL," +
                "total REAL" + ")")

        db.execSQL(query2)
    }

    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
        db.execSQL("DROP TABLE IF EXISTS temp")
        db.execSQL("DROP TABLE IF EXISTS transactions")
        onCreate(db)
    }

    fun insertToTemp(liters: Float, price: Float) {
        val db = this.writableDatabase

        val values = ContentValues()

        values.put("liters", liters)
        values.put("price", price)

        db.insert("temp", null, values)

        db.close()
    }

    fun getFromTemp(): ArrayList<TempModel> {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM temp", null)
        val courseModalArrayList: ArrayList<TempModel> = ArrayList()

        if (cursor.moveToFirst()) {
            do {
                courseModalArrayList.add(
                    TempModel(
                        cursor.getFloat(1),
                        cursor.getFloat(2)
                    )
                )
            } while (cursor.moveToNext())
        }
        cursor.close()
        return courseModalArrayList
    }

    fun removeFromTemp() {
        val db = this.writableDatabase
        db.execSQL("DELETE FROM temp")
        db.close()
    }

    //////

    fun insertToTransactions(liters: Float, price: Float) {
        val total = liters * price

        val db = this.writableDatabase

        val values = ContentValues()

        values.put("liters", liters)
        values.put("price", price)
        values.put("total", total)

        db.insert("transactions", null, values)

        db.close()
    }

    fun getFromTransactions(): ArrayList<TransactionsModel> {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM transactions", null)
        val courseModalArrayList: ArrayList<TransactionsModel> = ArrayList()

        if (cursor.moveToFirst()) {
            do {
                courseModalArrayList.add(
                    TransactionsModel(
                        cursor.getFloat(1),
                        cursor.getFloat(2),
                        cursor.getFloat(3)
                    )
                )
            } while (cursor.moveToNext())
        }
        cursor.close()
        return courseModalArrayList
    }

    fun removeFromTransactions() {
        val db = this.writableDatabase
        db.execSQL("DELETE FROM transactions")
        db.close()
    }






    /////////

    /*fun deleteAll() {
        val db = this.writableDatabase
        db.execSQL("DROP TABLE IF EXISTS access_token")
    }

    fun addAccessToken(token: String, username: String, userId: String){
        val values = ContentValues()

        values.put("token", token)
        values.put("username", username)
        values.put("user_id", userId)

        val db = this.writableDatabase

        db.insert("access_token", null, values)

        db.close()
    }

    fun getAll(): ArrayList<AccessTokenModal> {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM access_token", null)
        val courseModalArrayList: ArrayList<AccessTokenModal> = ArrayList()

        if (cursor.moveToFirst()) {
            do {
                courseModalArrayList.add(
                    AccessTokenModal(
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3)
                    )
                )
            } while (cursor.moveToNext())
        }
        cursor.close()
        return courseModalArrayList
    }

    fun removeAccessToken() {
        val db = this.writableDatabase
        db.execSQL("DELETE FROM access_token")
        db.close()
    }*/
}