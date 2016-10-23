package com.codragon.shopping;

import java.util.ArrayList;

import android.R.integer;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



public class LoginDatabase{
	
	private static final String COLUMN_ID = "_id";
	private static final String COLUMN_FOOD = "items";
	private static final String COLUMN_PRICE = "price";
	private static final String COLUMN_ORDERS = "orders";
	private static final String DATABASE_NAME="CUSTOMERS";
	private static final String DATABASE_TABLE = "details";
	private static final int DATABASE_VERSION = 1;
	
	private DbHelper ourHelper;
	private final Context ourContext;
	private SQLiteDatabase ourDatabase; 
	
	
	private static class DbHelper extends SQLiteOpenHelper{ 

		public DbHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			db.execSQL("CREATE TABLE " + DATABASE_TABLE + " (" +
					COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + 
					COLUMN_FOOD + " TEXT, " +
					COLUMN_PRICE + " TEXT, " +
					COLUMN_ORDERS + " TEXT);" 
					);
			
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
			onCreate(db);
		}
		
	}
	
	public LoginDatabase(Context c){
		ourContext = c;
	}
	
	public LoginDatabase open() throws SQLException{
		ourHelper = new DbHelper(ourContext);
		ourDatabase = ourHelper.getWritableDatabase();
		return this;
	}
	
	public void close(){
		ourHelper.close();
	}

	public long createEntry(String name, String price, String orders) {
		// TODO Auto-generated method stub
		ContentValues cv = new ContentValues();
		cv.put(COLUMN_FOOD, name);
		cv.put(COLUMN_ORDERS, orders);
		cv.put(COLUMN_PRICE, price);
		return ourDatabase.insert(DATABASE_TABLE, null, cv);
	}

	


	public String getName(long l)throws SQLException {
		// TODO Auto-generated method stub
		String[] columns = new String[]{COLUMN_ID, COLUMN_FOOD, COLUMN_PRICE, COLUMN_ORDERS};
		Cursor c = ourDatabase.query(DATABASE_TABLE, columns, COLUMN_ID + "=" + l, null, null, null, null);
		if(c!=null){
			c.moveToFirst();
			String name = c.getString(1);
			return name;
		}
		return null;
	}
	
	public String getPrice(long l)throws SQLException {
		// TODO Auto-generated method stub
		String[] columns = new String[]{COLUMN_ID, COLUMN_FOOD, COLUMN_PRICE, COLUMN_ORDERS};
		Cursor c = ourDatabase.query(DATABASE_TABLE, columns, COLUMN_ID + "=" + l, null, null, null, null);
		if(c!=null){
			c.moveToFirst();
			String count = c.getString(2);
			return count;
		}
		return null;
	}
	
	public String getOrders(long l)throws SQLException {
		// TODO Auto-generated method stub
		String[] columns = new String[]{COLUMN_ID, COLUMN_FOOD, COLUMN_PRICE, COLUMN_ORDERS};
		Cursor c = ourDatabase.query(DATABASE_TABLE, columns, COLUMN_ID + "=" + l, null, null, null, null);
		if(c!=null){
			c.moveToFirst();
			String count = c.getString(3);
			return count;
		}
		return null;
	}
	
	public int getUserDataCount() {
        String countQuery = "SELECT * FROM " + DATABASE_TABLE;
        Cursor cursor = ourDatabase.rawQuery(countQuery, null);
        
        int count = cursor.getCount();
        System.out.println("AD"+count);
        cursor.close();
        
        // return count
        return count;
    }
	
	
	
	public void deleteEntry(long lRow1)throws SQLException {
		// TODO Auto-generated method stub
		ourDatabase.delete(DATABASE_TABLE, COLUMN_ID + "=" + lRow1, null);
	}
}

