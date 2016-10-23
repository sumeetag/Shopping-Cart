package com.codragon.shopping;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseOpenHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "customer";
	private static final int DATABASE_VERSION = 1;

	private static final String TABLE_NAME = "customer";
	private static final String COLUMN_ID = "_id";
	private static final String COLUMN_FOOD = "items";
	private static final String COLUMN_PRICE = "price";
	private static final String COLUMN_ORDERS = "orders";
	String[] selectionArgs;
	private SQLiteDatabase database;

	private final Context context;

	// database path
	private static String DATABASE_PATH = "/data/data/com.codragon.shopping/databases/";
	private static SQLiteDatabase db;

	/** constructor */
	public DatabaseOpenHelper(Context ctx) {

		/*
		 * super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
		 * 
		 * this.context = ctx; DATABASE_PATH =
		 * context.getFilesDir().getParentFile().getPath() + "/databases/";
		 */

		super(ctx, DATABASE_NAME, null, 1);// 1? its Database Version
		// DATABASE_PATH = "/data/data/" + ctx.getPackageName() + "/databases/";
		this.context = ctx;

	}

	/**
	 * Creates a empty database on the system and rewrites it with your own
	 * database.
	 * */
	public void create() throws IOException {

		/*
		 * boolean check = checkDataBase();
		 * 
		 * SQLiteDatabase db_Read = null;
		 * 
		 * // Creates empty database default system path db_Read =
		 * this.getWritableDatabase(); db_Read.close(); try { if (!check) {
		 * copyDataBase(); } } catch (IOException e) { throw new
		 * Error("Error copying database"); }
		 */

		boolean mDataBaseExist = checkDataBase();
		if (!mDataBaseExist) {
			this.getReadableDatabase();
			// this.close();
			try {
				// Copy the database from assests
				copyDataBase();
				// Log.e(TAG, "createDatabase database created");
			} catch (IOException mIOException) {
				throw new Error("ErrorCopyingDataBase");
			}
		}
	}

	/**
	 * Check if the database already exist to avoid re-copying the file each
	 * time you open the application.
	 * 
	 * @return true if it exists, false if it doesn't
	 */
	private boolean checkDataBase() {

		SQLiteDatabase checkDB = null;
		try {
			String myPath = DATABASE_PATH + DATABASE_NAME;
			checkDB = SQLiteDatabase.openDatabase(myPath, null,
					SQLiteDatabase.OPEN_READWRITE);
		} catch (SQLiteException e) {
			// database does't exist yet.
		}

		if (checkDB != null) {
			checkDB.close();
		}
		return checkDB != null ? true : false;

	}

	/**
	 * Copies your database from your local assets-folder to the just created
	 * empty database in the system folder, from where it can be accessed and
	 * handled. This is done by transfering bytestream.
	 * 
	 */
	private void copyDataBase() throws IOException {

		/*
		 * 
		 * // Open your local db as the input stream InputStream myInput =
		 * context.getAssets().open(DATABASE_NAME);
		 * 
		 * // Path to the just created empty db String outFileName =
		 * DATABASE_PATH + DATABASE_NAME;
		 * 
		 * // Open the empty db as the output stream OutputStream myOutput = new
		 * FileOutputStream(outFileName);
		 * 
		 * // transfer bytes from the inputfile to the outputfile byte[] buffer
		 * = new byte[1024]; int length; while ((length = myInput.read(buffer))
		 * > 0) { myOutput.write(buffer, 0, length); }
		 * 
		 * // Close the streams myOutput.flush(); myOutput.close();
		 * myInput.close();
		 */

		InputStream mInput = context.getAssets().open(DATABASE_NAME);
		String outFileName = DATABASE_PATH + DATABASE_NAME;
		OutputStream mOutput = new FileOutputStream(outFileName);
		byte[] mBuffer = new byte[1024];
		int mLength;
		while ((mLength = mInput.read(mBuffer)) > 0) {
			mOutput.write(mBuffer, 0, mLength);
		}
		mOutput.flush();
		mOutput.close();
		mInput.close();

	}

	/** open the database */
	public void open() throws SQLException {
		/*
		 * String myPath = DATABASE_PATH + DATABASE_NAME; database =
		 * SQLiteDatabase.openDatabase(myPath, null,
		 * SQLiteDatabase.OPEN_READWRITE);
		 */

		String myPath = DATABASE_PATH + DATABASE_NAME;
		database = SQLiteDatabase.openDatabase(myPath, null,
				SQLiteDatabase.OPEN_READWRITE);

	}

	/** close the database */
	@Override
	public synchronized void close() {
		if (database != null)
			database.close();
		super.close();
	}

	// insert a user into the database

	// updates a user
	public void updateUser(long l, String food, String price, String order) {
		ContentValues args = new ContentValues();
		args.put(COLUMN_FOOD, food);
		args.put(COLUMN_ORDERS, order);
		args.put(COLUMN_PRICE, price);
		database.update(TABLE_NAME, args, COLUMN_ID + "=" + l, null);
	}

	// retrieves a particular user

	public int getId(String food) throws SQLException {
		// TODO Auto-generated method stub
		String[] columns = new String[] { COLUMN_ID, COLUMN_FOOD, COLUMN_PRICE, COLUMN_ORDERS};
		Cursor c = database.query(TABLE_NAME, columns, COLUMN_FOOD + "= '"
				+ food + "'", null, null, null, null);
		if (c != null) {
			c.moveToFirst();
			String Id = c.getString(0);
			int id = Integer.parseInt(Id);
			c.close();
			return id;
		}
		return 0;
	}
	
	

	public ArrayList<String> getFood1(String l) throws SQLException {
		// TODO Auto-generated method stub
		ArrayList<String> foo = new ArrayList<String>();
		// selectionArgs = new String[] {"%" + table_no + "%"};
		String[] columns = new String[] { COLUMN_ID, COLUMN_FOOD, COLUMN_PRICE, COLUMN_ORDERS};
		// String query = " SELECT food_item FROM users WHERE table_no=?";
		// Cursor c = database.query(TABLE_NAME, columns, COLUMN_TABLE +"= '" +
		// table_no +"'", null, null, null, null);
		Cursor c = database.query(TABLE_NAME, columns,
				COLUMN_ORDERS + " NOT LIKE ?",
				new String[] { "%" + l + "%" }, null, null, null, null);
		// Cursor c = database.rawQuery(query, selectionArgs);
		System.out.println(c);
		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
			// c.move(1);
			String food = c.getString(1);
			foo.add(food);
		}
		c.close();
		return foo;
	}
	

	public String getFood(long l) throws SQLException {
		// TODO Auto-generated method stub
		String[] columns = new String[] { COLUMN_ID, COLUMN_FOOD, COLUMN_PRICE, COLUMN_ORDERS};
		Cursor c = database.query(TABLE_NAME, columns, COLUMN_ID + "=" + l,
				null, null, null, null);
		if (c != null) {
			c.moveToFirst();
			String food = c.getString(1);
			c.close();
			return food;
		}
		return null;
	}
/*
	public ArrayList<String> getFood1(String table_no) throws SQLException {
		// TODO Auto-generated method stub
		ArrayList<String> foo = new ArrayList<String>();
		// selectionArgs = new String[] {"%" + table_no + "%"};
		String[] columns = new String[] { COLUMN_ID, COLUMN_CATEGORY,
				COLUMN_FOOD, COLUMN_RATE, COLUMN_TABLE, COLUMN_ORDERS,
				COLUMN_RATING, COLUMN_NOF };
		// String query = " SELECT food_item FROM users WHERE table_no=?";
		// Cursor c = database.query(TABLE_NAME, columns, COLUMN_TABLE +"= '" +
		// table_no +"'", null, null, null, null);
		Cursor c = database.query(TABLE_NAME, columns,
				COLUMN_TABLE + " LIKE ?",
				new String[] { "%" + table_no + "%" }, null, null, null, null);
		// Cursor c = database.rawQuery(query, selectionArgs);
		System.out.println(c);
		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
			// c.move(1);
			String food = c.getString(2);
			foo.add(food);
		}
		c.close();
		return foo;
	}*/

	public String getOrders(long l) throws SQLException {
		// TODO Auto-generated method stub
		String[] columns = new String[] { COLUMN_ID, COLUMN_FOOD, COLUMN_PRICE, COLUMN_ORDERS};
		Cursor c = database.query(TABLE_NAME, columns, COLUMN_ID + "=" + l,
				null, null, null, null);
		if (c != null) {
			c.moveToFirst();
			String order = c.getString(3);
			c.close();
			return order;
		}
		return null;
	}


	public String getPrice(long l) throws SQLException {
		// TODO Auto-generted method stub
		String[] columns = new String[] { COLUMN_ID, COLUMN_FOOD, COLUMN_PRICE, COLUMN_ORDERS};
		Cursor c = database.query(TABLE_NAME, columns, COLUMN_ID + "=" + l,
				null, null, null, null);
		if (c != null) {
			c.moveToFirst();
			String price = c.getString(2);
			c.close();
			return price;
		}
		return null;
	}
	
	public void updateOrders(long l) {
		ContentValues args = new ContentValues();
		args.put(COLUMN_ORDERS, "0");
		database.update(TABLE_NAME, args, COLUMN_ID + "=" + l, null);
	}
	
	public int getUserDataCount() {
        String countQuery = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = database.rawQuery(countQuery, null);
        
        int count = cursor.getCount();
        System.out.println("AD"+count);
        cursor.close();
        
        // return count
        return count;
    }
	@Override
	public void onCreate(SQLiteDatabase arg0) {
		// TODO Auto-generated method stub

	}

	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
		onCreate(db);
	}
	/*
	 * @Override public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2)
	 * { // TODO Auto-generated method stub
	 * 
	 * }
	 */

}
