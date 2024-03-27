package com.example.shopcart;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.database.sqlite.SQLiteDatabaseKt;

import com.shashank.sony.fancytoastlib.FancyToast;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class mydatabase extends SQLiteOpenHelper {
    public static final String dbname="mydatabase";
    Context c;

    public mydatabase( Context context) {

        super(context, dbname, null, 29);
        c=context;
    }


    @Override
    public void onCreate(SQLiteDatabase mydb) {
        mydb.execSQL("create table users(username TEXT ,password TEXT,who TEXT, primary key(username,who))");
        mydb.execSQL("create table buyers(buyer_id integer primary key autoincrement , username text, foreign key(username) references users)");
        mydb.execSQL("create table sellers(seller_id integer primary key autoincrement , username text,foreign key(username) references users)");
        mydb.execSQL("create table user_info(username TEXT, who TEXT, dob TEXT, state TEXT, address TEXT, mobile TEXT ,primary key(username,who),foreign key(username,who) references users)");
        mydb.execSQL("create table card_info(cardnumber INTEGER ,month INTEGER, year INTEGER,cardname TEXT,cvv INTEGER, username TEXT,who TEXT,primary key(cardnumber,username,who),foreign key(username,who) references users)");

        mydb.execSQL("create table products(product_id integer primary key autoincrement,seller_id integer, title text,price integer,category text,foreign key(seller_id) references sellers)");
        mydb.execSQL("create table product_img( product_id integer primary key autoincrement, image BLOB, foreign key(product_id) references products)");
        mydb.execSQL("create table cart(buyer_id integer,product_id integer,quantity integer, primary key(buyer_id,product_id),foreign key(buyer_id) references buyers,foreign key (product_id) references products)");
        mydb.execSQL("create table admin(admin_code text, password text, primary key(admin_code))");
        mydb.execSQL("create table coupon(coupon_code text, sponsors text, discount text, primary key(coupon_code))");
        mydb.execSQL("create table all_transactions(order_id integer primary key autoincrement, buyer_id integer, amount integer, foreign key(buyer_id) references buyers)");
        mydb.execSQL("create table products_sold(buyer_id integer,product_id integer,quantity integer)");
        mydb.execSQL("create table coupon_used(order_id integer primary key autoincrement,couponcode text,foreign key(order_id) references all_transactions)");

        mydb.execSQL("CREATE TRIGGER remove_coupon_trigger AFTER INSERT ON coupon_used " +
                "BEGIN " +
                "   DELETE FROM coupon WHERE coupon_code = NEW.couponcode; " +
                "END;");



    }

    void insertbuyer(String name){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("username",name);
       // contentValues.put("who",type);
        db.insert("buyers",null,contentValues);
    }
    void insertseller(String name){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("username",name);
        //contentValues.put("who",type);
        db.insert("sellers",null,contentValues);
    }
    @SuppressLint("Range")
    int getbuyerid(String name){
        SQLiteDatabase db=this.getWritableDatabase();
        String query="SELECT * FROM buyers WHERE username = ? ";
        String [] selectionargs={name};Cursor cursor=db.rawQuery(query,selectionargs);
        int n=-1;
          if(cursor.moveToFirst())
             n=cursor.getInt(cursor.getColumnIndex("buyer_id"));

          return n;



    }
    @SuppressLint("Range")
    int getsellerid(String name){
        SQLiteDatabase db=this.getWritableDatabase();
        String query="SELECT * FROM sellers WHERE username = ? ";
        String [] selectionargs={name};Cursor cursor=db.rawQuery(query,selectionargs);
        int n=-1;
        if(cursor.moveToFirst())
            n=cursor.getInt(cursor.getColumnIndex("seller_id"));

        return n;



    }

    public void insertsoldprofucts(int buyer,int prod,int qty){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("buyer_id",buyer);
        contentValues.put("product_id",prod);
        contentValues.put("quantity",qty);

        db.insert("products_sold",null,contentValues);
        db.close();
    }

    public void addadmininfo(String code,String pass){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("admin_code",code);
        contentValues.put("password",pass);
        db.insert("admin",null,contentValues);
        db.close();

    }
    int checkadmin(String code,String pass){
        SQLiteDatabase db=this.getWritableDatabase();
        String query="select * from admin where admin_code = ? and password = ?";
        String selectionargs[]={code,pass};
        Cursor cursor=db.rawQuery(query,selectionargs);
        if(cursor.getCount()>0)
            return 1;
        else
            return -1;
    }
    public void addcoupon_used(String c){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("couponcode",c);
        db.insert("coupon_used",null,contentValues);
        db.close();
    }
    public void addtransaction(int buyer_id,int amt){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("buyer_id",buyer_id);
        contentValues.put("amount",amt);

        db.insert("all_transactions",null,contentValues);
        db.close();
    }
    @SuppressLint("Range")
    int checkcoupon(String code){
        SQLiteDatabase db=this.getWritableDatabase();
        String query="select * from coupon where coupon_code = ?";
        String selectionargs[]={code};
        Cursor cursor=db.rawQuery(query,selectionargs);
        int disc=-1;
        if(cursor.getCount()>0) {
            while (cursor.moveToNext()) {
                disc=Integer.parseInt(cursor.getString(cursor.getColumnIndex("discount")));

            }
        }
        return disc;
    }
    public void clearproductsfromcart(int buyer_id){
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = "buyer_id = ?";
        String[] selectionArgs = {String.valueOf(buyer_id)};

        db.delete("cart", selection, selectionArgs);


        db.close();
    }
    @SuppressLint("Range")
    public ArrayList<coupons> getallcoupons(){
        SQLiteDatabase db=this.getWritableDatabase();
        String query="select * from coupon";
        Cursor cursor=db.rawQuery(query,null);
        ArrayList<coupons>arrayList=new ArrayList<>();
        while(cursor.moveToNext()){
            coupons c=new coupons();
            c.setCode(cursor.getString(cursor.getColumnIndex("coupon_code")));
            c.setSponsor(cursor.getString(cursor.getColumnIndex("sponsors")));
            c.setDiscount(cursor.getString(cursor.getColumnIndex("discount")));
            arrayList.add(c);

        }
        return arrayList;
    }
    public void insertcoupons(String code,String sponsor,String disc){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("coupon_code",code);
        contentValues.put("sponsors",sponsor);
        contentValues.put("discount",disc);
        db.insert("coupon",null,contentValues);
        db.close();
    }



    @SuppressLint("Range")
    String getsellername(int id){
        SQLiteDatabase db=this.getWritableDatabase();
        String query="select * from sellers where seller_id = ?";
        String selectionargs[]={String.valueOf(id)};
        Cursor cursor=db.rawQuery(query,selectionargs);
        String username="";
        if(cursor.moveToFirst())
            username=cursor.getString(cursor.getColumnIndex("username"));
        return username;

    }

    @SuppressLint("Range")
    public  ArrayList<products>getprofuctscategory(String categorys){
        ArrayList<products>arrayList=new ArrayList<>();
        SQLiteDatabase db=this.getWritableDatabase();
        String query="SELECT * FROM products natural join product_img WHERE category = ?";
        String [] selectionarg={categorys};
        Cursor cursor=db.rawQuery(query,selectionarg);
        while(cursor.moveToNext()){
            products p=new products();
            p.setTitle(cursor.getString(cursor.getColumnIndex("title")));
            p.setPrice(cursor.getInt(cursor.getColumnIndex("price"))+"");
            p.setCategory(cursor.getString(cursor.getColumnIndex("category")));
            p.setProduct_id(cursor.getInt(cursor.getColumnIndex("product_id")));
            p.setSeller_id(cursor.getInt(cursor.getColumnIndex("seller_id")));
            p.setSeller(getsellername(p.seller_id));
            p.setImg(cursor.getBlob(cursor.getColumnIndex("image")));
            arrayList.add(p);
        }



        return arrayList;
    }
    @SuppressLint("Range")
    public  ArrayList<products> getcartproducts(int id){
        ArrayList<products>arrayList=new ArrayList<>();
        SQLiteDatabase db=this.getWritableDatabase();
        String query="select * from products natural join product_img natural join cart where buyer_id = ?";
        String selectionargs[]={String.valueOf(id)};
        Cursor cursor=db.rawQuery(query,selectionargs);
        while(cursor.moveToNext()){
            products p=new products();
            p.setTitle(cursor.getString(cursor.getColumnIndex("title")));
            p.setPrice(cursor.getInt(cursor.getColumnIndex("price"))+"");
            p.setCategory(cursor.getString(cursor.getColumnIndex("category")));
            p.setProduct_id(cursor.getInt(cursor.getColumnIndex("product_id")));
            p.setSeller_id(cursor.getInt(cursor.getColumnIndex("seller_id")));
            p.setSeller(getsellername(p.seller_id));
            p.setImg(cursor.getBlob(cursor.getColumnIndex("image")));
            p.setQty(cursor.getInt(cursor.getColumnIndex("quantity")));
            arrayList.add(p);

        }
        return arrayList;
    }

    @SuppressLint("Range")
    public ArrayList<products>getallproducts(){
        ArrayList<products>arrayList=new ArrayList<>();
        SQLiteDatabase db=this.getWritableDatabase();
        String query="SELECT * FROM products natural join product_img";
        Cursor cursor=db.rawQuery(query,null);
        while(cursor.moveToNext()){
            products p=new products();
            p.setTitle(cursor.getString(cursor.getColumnIndex("title")));
            p.setPrice(cursor.getInt(cursor.getColumnIndex("price"))+"");
            p.setCategory(cursor.getString(cursor.getColumnIndex("category")));
            p.setProduct_id(cursor.getInt(cursor.getColumnIndex("product_id")));
            p.setSeller_id(cursor.getInt(cursor.getColumnIndex("seller_id")));
            p.setSeller(getsellername(p.seller_id));
            p.setImg(cursor.getBlob(cursor.getColumnIndex("image")));
            arrayList.add(p);

        }



        return arrayList;
    }
    public int checkproductincart(int buyer_id,int prod_id,int qty){
        SQLiteDatabase db=this.getWritableDatabase();
        String query="select * from cart where buyer_id = ? and product_id = ?";
        String selectionargs[]={String.valueOf(buyer_id),String.valueOf(prod_id)};
        Cursor cursor=db.rawQuery(query,selectionargs);
        if(cursor.getCount()>0)
            return -1;
        else
        {
            insertproductintocart(buyer_id,prod_id,qty);
            return 1;
        }
    }
    public void insertproductintocart(int buyer_id,int prod_id,int qty){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("buyer_id",buyer_id);
        contentValues.put("product_id",prod_id);
        contentValues.put("quantity",qty);
        db.insert("cart",null,contentValues);

    }
    @SuppressLint("Range")
    public ArrayList<products> getproducts(String sellerName){
        ArrayList<products>arrayList=new ArrayList<>();
        SQLiteDatabase db=this.getWritableDatabase();
        int id=getsellerid(sellerName);

        String query="SELECT * FROM products natural join product_img WHERE seller_id = ?";
        String[] selectionArgs = {String.valueOf(id)};
        Cursor cursor = db.rawQuery(query, selectionArgs);
        while (cursor.moveToNext()){
            products p=new products();
            p.setTitle(cursor.getString(cursor.getColumnIndex("title")));
            p.setPrice(cursor.getInt(cursor.getColumnIndex("price"))+"");
            p.setCategory(cursor.getString(cursor.getColumnIndex("category")));
            p.setProduct_id(cursor.getInt(cursor.getColumnIndex("product_id")));
            p.setSeller_id(cursor.getInt(cursor.getColumnIndex("seller_id")));
            p.setSeller(getsellername(p.seller_id));
            p.setImg(cursor.getBlob(cursor.getColumnIndex("image")));
            arrayList.add(p);

        }
        /*for(int i=0;i<arrayList.size();i++){
            products p=arrayList.get(i);
            arrayList.remove(i);
            String query2="SELECT * FROM product_img WHERE seller = ? AND title = ? ";
            String[] selectionArgs2 = {p.getSeller(),p.getTitle()};
            Cursor cursor2 = db.rawQuery(query2, selectionArgs2);
            if (cursor2.moveToFirst()) {
                p.setImg(cursor2.getBlob(cursor2.getColumnIndex("image")));
                arrayList.add(i,p);
            }



        }*/


        return arrayList;

    }

    public long insertprod(String seller,String title,int price, String category,byte img[]){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        int id=getsellerid(seller);
        contentValues.put("seller_id",id);
        contentValues.put("title",title);
        contentValues.put("price",price);
        contentValues.put("category",category);



        long ans=db.insert("products",null,contentValues);
        Bitmap originalBitmap = BitmapFactory.decodeByteArray(img, 0, img.length);

// Create a ByteArrayOutputStream to store the compressed image data
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

// Compress the image (e.g., JPEG format with 70% quality)
        originalBitmap.compress(Bitmap.CompressFormat.JPEG, 50, outputStream);

// Get the compressed image data as a byte array
        byte[] compressedImageByteArray = outputStream.toByteArray();


        ContentValues contentValues1=new ContentValues();

        contentValues1.put("image",compressedImageByteArray);
        long ans1=db.insert("product_img",null,contentValues1);
        return ans1;
    }
    public void deleteCardInfo(String cardname,String cardnumber, String user, String who) {
        SQLiteDatabase db = this.getWritableDatabase();

        String whereClause = "cardname = ? AND username = ? AND who = ? AND cardnumber = ?";
        String[] whereArgs = {cardname, user, who,cardnumber};

        db.delete("card_info", whereClause, whereArgs);

        db.close();
    }
    @SuppressLint("Range")
    public ArrayList<visacard> getcards(String name, String who){
        ArrayList<visacard>arrayList=new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM card_info WHERE username = ? AND who = ?";
        String[] selectionArgs = {name, who};

        Cursor cursor = db.rawQuery(query, selectionArgs);
        while(cursor.moveToNext()){
            visacard temp=new visacard();
            temp.setCardname(cursor.getString(cursor.getColumnIndex("cardname")));

            temp.setCardnumber(cursor.getInt(cursor.getColumnIndex("cardnumber")));

            temp.setYear(cursor.getInt(cursor.getColumnIndex("year")));

            temp.setMonth(cursor.getInt(cursor.getColumnIndex("month")));

            //temp.setCvv(cursor.getInt(cursor.getColumnIndex("cvv")));


            temp.setUser(cursor.getString(cursor.getColumnIndex("username")));
            temp.setWho(cursor.getString(cursor.getColumnIndex("who")));
            arrayList.add(temp);
        }



        return arrayList;

    }
    public long addcardinfo(int cardnumber,int month,int year,String cardname,String user,String who,String cvv){
        ContentValues contentValues=new ContentValues();
        contentValues.put("cardnumber",cardnumber);
        contentValues.put("month",month);
        contentValues.put("year",year);
        contentValues.put("cvv",Integer.parseInt(cvv));
        contentValues.put("cardname",cardname);
        contentValues.put("username",user);
        contentValues.put("who",who);
        SQLiteDatabase db=this.getWritableDatabase();
        long result=db.insert("card_info",null,contentValues);
        return result;
    }

    public void deletetable(){
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS users " );
        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase mydb, int i, int i1) {
        mydb.execSQL("drop table if exists users");
        mydb.execSQL("drop table if exists user_info");
        mydb.execSQL("drop table if exists card_info");
        mydb.execSQL("drop table if exists products");
        mydb.execSQL("drop table if exists product_img");
        mydb.execSQL("drop table if exists buyers");
        mydb.execSQL("drop table if exists sellers");
        mydb.execSQL("drop table if exists cart");
        mydb.execSQL("drop table if exists admin");
        mydb.execSQL("drop table if exists coupon");
        mydb.execSQL("drop table if exists products_sold");
        mydb.execSQL("drop table if exists all_transactions");
        mydb.execSQL("drop table if exists coupon_used");

        onCreate(mydb);

    }

    public userinfo getuserinfos(String name,String who){
        userinfo u=null;
        SQLiteDatabase db = this.getReadableDatabase();


        String query = "SELECT username, dob, state, address, mobile " +
                "FROM user_info " +
                "WHERE username = ? AND who = ?";

        Cursor cursor = db.rawQuery(query, new String[]{name, who});
        if (cursor.moveToFirst()) {

            @SuppressLint("Range") String username = cursor.getString(cursor.getColumnIndex("username"));

            @SuppressLint("Range") String dob =cursor.getString(cursor.getColumnIndex("dob"));
            @SuppressLint("Range") String state =cursor.getString(cursor.getColumnIndex("state"));
            @SuppressLint("Range") String address =cursor.getString(cursor.getColumnIndex("address"));
            @SuppressLint("Range") String mobile =cursor.getString(cursor.getColumnIndex("mobile"));

            u=new userinfo(username,who,dob,state,address,mobile);

        }




        return u;





    }
    public int saveinfp(String name,String who,String dob,String state,String address, String mobile){

        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("username",name);
        contentValues.put("dob",dob);
        contentValues.put("state",state);
        contentValues.put("address",address);
        contentValues.put("who",who);
        contentValues.put("mobile",mobile);
        Cursor cursor=db.rawQuery("Select * from user_info where username = ? and who = ?",new String[]{name,who});
        if(cursor.getCount()>0){
            String whereClause = "username = ? AND who = ?";
            String[] whereArgs = {name, who};

            int rowsUpdated = db.update("user_info", contentValues, whereClause, whereArgs);
            return rowsUpdated;




        }else{
            db.insert("user_info",null,contentValues);
            return 2;
        }

    }
    public boolean insertdata(String username,String password,String w){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("username",username);
        contentValues.put("password",password);
        contentValues.put("who",w);
        long result=db.insert("users",null,contentValues);
        if(result==-1)
            return false;
        else
            return true;
    }

    public  boolean checkusername(String username,String w){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery("Select * from users where username = ? and who = ?",new String[]{username,w});
        if(cursor.getCount()>0)
            return true;
        else
            return false;
    }

    public boolean checkinfo(String username,String pass,String w){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery("Select * from users where username = ? and password = ? and who = ?",new String[]{username,pass,w});
        if(cursor.getCount()>0)
            return true;
        else
            return false;

    }

    public void removefromcart(int id, int product_id) {
        SQLiteDatabase db=this.getWritableDatabase();
        String whereclaus="buyer_id = ? and product_id = ?";
        String selectionargs[]={String.valueOf(id),String.valueOf(product_id)};
        db.delete("cart",whereclaus,selectionargs);
        db.close();

    }

    public void incrementQuantity(int buyerId, int productId,int f) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();


        int currentQuantity = getCurrentQuantity(db, buyerId, productId);


        values.put("quantity", currentQuantity + f);


        String selection = "buyer_id = ? AND product_id = ?";
        String[] selectionArgs = { String.valueOf(buyerId), String.valueOf(productId) };

        db.update("cart", values, selection, selectionArgs);


        db.close();
    }

    @SuppressLint("Range")
    public int getTotalQuantitySold(int productId) {
        SQLiteDatabase db = this.getReadableDatabase();
        int totalQuantitySold = 0; // Default value if not found

        // Define the columns you want to retrieve
        String[] columns = {"SUM(quantity) AS total_quantity"};

        // Define the selection criteria
        String selection = "product_id = ?";
        String[] selectionArgs = {String.valueOf(productId)};

        Cursor cursor = db.query("products_sold", columns, selection, selectionArgs, null, null, null);

        // Check if the cursor has data
        if (cursor.moveToFirst()) {
            // Extract the total quantity from the cursor
            totalQuantitySold = cursor.getInt(cursor.getColumnIndex("total_quantity"));
        }

        // Close the cursor and database
        cursor.close();
        db.close();

        // Return the total quantity sold
        return totalQuantitySold;
    }

    @SuppressLint("Range")
    private int getCurrentQuantity(SQLiteDatabase db, int buyerId, int productId) {
        int currentQuantity = 0;

        String[] columns = { "quantity" };
        String selection = "buyer_id = ? AND product_id = ?";
        String[] selectionArgs = { String.valueOf(buyerId), String.valueOf(productId) };

        Cursor cursor = db.query("cart", columns, selection, selectionArgs, null, null, null);

        if (cursor.moveToFirst()) {
            currentQuantity = cursor.getInt(cursor.getColumnIndex("quantity"));
        }

        cursor.close();
        return currentQuantity;
    }

    // nested subqueries

    @SuppressLint("Range")
    public String getBuyerWithHighestTotalTransactionAmount() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT buyers.username AS buyer_username " +
                "FROM buyers " +
                "WHERE buyers.buyer_id = (SELECT all_transactions.buyer_id " +
                "FROM all_transactions " +
                "GROUP BY all_transactions.buyer_id " +
                "ORDER BY SUM(all_transactions.amount) DESC " +
                "LIMIT 1)";

        Cursor cursor = db.rawQuery(query, null);

        String result = null;
        if (cursor.moveToFirst()) {
            result = cursor.getString(cursor.getColumnIndex("buyer_username"));
        }

        cursor.close();
        db.close();

        return result;
    }
    @SuppressLint("Range")
    public String getSellerWithMaxQuantitySold() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT sellername " +
                "FROM ( " +
                "   SELECT sellers.username AS sellername, " +
                "          SUM(products_sold.quantity) AS total_quantity_sold " +
                "   FROM sellers " +
                "   NATURAL JOIN products " +
                "   NATURAL JOIN products_sold " +
                "   GROUP BY sellers.seller_id, sellers.username " +
                ") AS seller_totals " +
                "WHERE total_quantity_sold = ( " +
                "   SELECT MAX(total_quantity) " +
                "   FROM ( " +
                "       SELECT SUM(products_sold.quantity) AS total_quantity " +
                "       FROM sellers " +
                "       NATURAL JOIN products " +
                "       NATURAL JOIN products_sold " +
                "       GROUP BY sellers.seller_id " +
                "   ) " +
                ")";

        Cursor cursor = db.rawQuery(query, null);

        String result = null;
        if (cursor.moveToFirst()) {
            result = cursor.getString(cursor.getColumnIndex("sellername"));
        }

        cursor.close();
        db.close();

        return result;
    }

    @SuppressLint("Range")
    public String getCategoryWithMaxCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT category " +
                "FROM products_sold " +
                "NATURAL JOIN products " +
                "GROUP BY category " +
                "HAVING COUNT(*) = ( " +
                "   SELECT MAX(category_count) " +
                "   FROM ( " +
                "       SELECT COUNT(*) AS category_count " +
                "       FROM products_sold " +
                "       NATURAL JOIN products " +
                "       GROUP BY category " +
                "   ) " +
                ")";

        Cursor cursor = db.rawQuery(query, null);

        String result = null;
        if (cursor.moveToFirst()) {
            result = cursor.getString(cursor.getColumnIndex("category"));
        }

        cursor.close();
        db.close();

        return result;
    }

    @SuppressLint("Range")
    public int getTotalOrdersPlaced() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT COUNT(*) AS total_orders FROM all_transactions";

        Cursor cursor = db.rawQuery(query, null);

        int result = 0;
        if (cursor.moveToFirst()) {
            result = cursor.getInt(cursor.getColumnIndex("total_orders"));


        }

        cursor.close();
        db.close();

        return result;
    }

    @SuppressLint("Range")
    public int getTotalTransactionAmount() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT SUM(amount) AS total_amount FROM all_transactions";

        Cursor cursor = db.rawQuery(query, null);

        int result = 0;
        if (cursor.moveToFirst()) {
            result = cursor.getInt(cursor.getColumnIndex("total_amount"));
        }

        cursor.close();
        db.close();

        return result;
    }

    @SuppressLint("Range")
    public int getBiggestTransactionAmount() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT MAX(amount) AS biggest_transaction FROM all_transactions";

        Cursor cursor = db.rawQuery(query, null);

        int result = 0;
        if (cursor.moveToFirst()) {
            result = cursor.getInt(cursor.getColumnIndex("biggest_transaction"));
        }

        cursor.close();
        db.close();

        return result;
    }
}
