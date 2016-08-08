package com.example.moamen.mobile_shopping_project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.sql.Date;

/**
 * Created by MoaMeN on 12/4/2015.
 */
public class DataBase extends SQLiteOpenHelper {

    private static String databaseName = "MyDatabase";
    private String customersdata = "create table customer(CustID integer primary key autoincrement, " +
            " CustName text not null," + " UserName text ," + " Password text ," +
            " Gender text , " + " Birthdate text , " + " Job text,"+ " Phone text );";

    private  String orders = "create table Order_table(OrdID integer primary key autoincrement , " +
            "OrdDate date , " +
            "custid integer REFERENCES customer(CustID), " +
            "Address text);";

    private String OrderDetails = "create table Order_details(OrdDID integer REFERENCES Order_table(OrdID)," +
            "prodid integer REFERENCES product(ProdID)," +
            "quantity integer , primary key(prodid , OrdDID )  );";

    private String Products = "create table product(ProdID integer primary key autoincrement, " +
            "ProdName text , " + " Price integer , " + " quantity integer , " +
            "catid integer REFERENCES category(CatID));";

    private String catrgorys = "create table category(CatID integer primary key autoincrement," +
            "CatName text )";

    SQLiteDatabase ShopDataBase;

    public DataBase(Context context) {
        super(context, databaseName, null, 22);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(customersdata);
        db.execSQL(orders);
        db.execSQL(OrderDetails);
        db.execSQL(Products);
        db.execSQL(catrgorys);

        ContentValues row=new ContentValues();
        row.put("CustName", "moamen");
        row.put("UserName", "moamen");
        row.put("Password", "mo2");
        row.put("Gender", "Male");
        row.put("Birthdate", "18/3/1995");
        row.put("Job", "sales");
        row.put("Phone", "01112530972");
        db.insert("customer", null, row);

        db.execSQL("insert into product (ProdName,Price,quantity,catid) " +
                "values ('Samsung S6','6000','20',1)");
        db.execSQL("insert into product (ProdName,Price,quantity,catid) " +
                "values ('Samsung Note6','5800','20',1)");
        db.execSQL("insert into product (ProdName,Price,quantity,catid) " +
                "values ('Samsung J7','2250','20',1)");
        db.execSQL("insert into product (ProdName,Price,quantity,catid) " +
                "values ('Htc desire 816','2000','20',1)");
        db.execSQL("insert into product (ProdName,Price,quantity,catid) " +
                "values ('Htc One M9','5500','20',1)");
        db.execSQL("insert into product (ProdName,Price,quantity,catid) " +
                "values ('Htc A9','4600','20',1)");
        db.execSQL("insert into product (ProdName,Price,quantity,catid) " +
                "values ('Sony Xperia z5','6600','20',1)");
        db.execSQL("insert into product (ProdName,Price,quantity,catid) " +
                "values ('Sony Xperia z3','4000','25',1)");
        db.execSQL("insert into product (ProdName,Price,quantity,catid) " +
                "values ('Sony Xperia z2','3000','18',1)");
        db.execSQL("insert into product (ProdName,Price,quantity,catid) " +
                "values ('Dell inspiron 15','6600','10',2)");
        db.execSQL("insert into product (ProdName,Price,quantity,catid) " +
                "values ('Hp','6000','15',2)");
        db.execSQL("insert into product (ProdName,Price,quantity,catid) " +
                "values ('Lenovo','6500','15',2)");
        db.execSQL("insert into product (ProdName,Price,quantity,catid) " +
                "values ('Samsung','4000','15',2)");;

        db.execSQL("insert into category (CatID,CatName) " +
                "values (1 ,'Mobiles')");
        db.execSQL("insert into category (CatID,CatName) " +
                "values (2,'Laptops')");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS customer");
        db.execSQL("DROP TABLE IF EXISTS Order_table");
        db.execSQL("DROP TABLE IF EXISTS Order_details");
        db.execSQL("DROP TABLE IF EXISTS product");
        db.execSQL("DROP TABLE IF EXISTS category");

        onCreate(db);
    }

    public Boolean check_pass(String Entered_Name ,String Entered_Pass)
    {
        ShopDataBase  = getReadableDatabase();

        String[] args1 = {"UserName"};
        Cursor CustomerNamePass = ShopDataBase.query("customer", args1,
                "UserName" + "=" + "\"" + Entered_Name + "\"" + " AND " + "Password" + "=" + "\"" +Entered_Pass+ "\"" , null, null, null, null);
        if(CustomerNamePass.getCount()!=0 )
        {
            ShopDataBase.close();
            return true;
        }
        else
            return false;

    }

    public void createNewAccount(String name, String username , String pass , String Gender ,String BDate ,String job ,String phone)
    {
        ShopDataBase = getWritableDatabase();
        ContentValues row=new ContentValues();
        row.put("CustName", name);
        row.put("UserName", username);
        row.put("Password", pass);
        row.put("Gender", Gender);
        row.put("Birthdate", BDate);
        row.put("Job", job);
        row.put("Phone", phone);

        ShopDataBase.insert("customer", null, row);
        ShopDataBase.close();
    }


    public Cursor remember_me() {
        ShopDataBase = getReadableDatabase();
        Cursor Remember_data = ShopDataBase.query("customer", new String[]{"UserName","Password"}, null , null, null, null, null);
        Remember_data.moveToLast();
        ShopDataBase.close();
        return Remember_data;
    }

    public Cursor GetProductsName(int catid)
    {
        ShopDataBase  = getReadableDatabase();

        Cursor ProductsName = ShopDataBase.rawQuery("select * from product where catid=" + catid, null) ;

        if(ProductsName.getCount()!= 0){
            ProductsName.moveToFirst();
            ShopDataBase.close();
            return ProductsName;

        }
        ShopDataBase.close();
        return null;
    }

    public Cursor GetProductsDetails(int ProdID) {
        ShopDataBase = getReadableDatabase();
        Cursor ProductsDetails = ShopDataBase.query("product", new String[]{"ProdName", "Price", "quantity"}, "ProdID="+ProdID, null, null, null, null);
        ProductsDetails.moveToFirst();
        ShopDataBase.close();
        return ProductsDetails;
    }


    public String GetPassword(String phone)
    {
        ShopDataBase=getReadableDatabase();
        Cursor password = ShopDataBase.rawQuery("select Password from customer where Phone" + "=" + "\"" + phone + "\"", null);

        if(password.getCount()!= 0){
            password.moveToFirst();
            ShopDataBase.close();
            return password.getString(0);

        }
        ShopDataBase.close();
        return null;
    }

    public void updateProduct(int prodid, int newquan)
    {
        ShopDataBase=getWritableDatabase();
        ContentValues row = new ContentValues();
        row.put("quantity",newquan);
        ShopDataBase.update("product", row, "ProdID like ?", new String[]{String.valueOf(prodid)});
        ShopDataBase.close();
    }

    public void updateOrders(int prodid, int ordid , int newquan)
    {
        ShopDataBase=getWritableDatabase();
        ContentValues row = new ContentValues();
        row.put("quantity",newquan);
        ShopDataBase.update("Order_details", row, "OrdDID="+ordid+" and prodid ="+prodid, null);
        ShopDataBase.close();
    }

    public void deleteOrders(int prodid, int ordid )
    {
        ShopDataBase=getWritableDatabase();
        ShopDataBase.delete("Order_details", "OrdDID="+ordid+" and prodid ="+prodid, null);
        ShopDataBase.close();
    }

    public int GetCustID(String phone)
    {
        ShopDataBase=getReadableDatabase();
        Cursor ID = ShopDataBase.rawQuery("select CustID from customer where Phone" + "=" + "\"" +phone+ "\"", null);

        if(ID.getCount()!= 0){
            ID.moveToFirst();
            ShopDataBase.close();
            return ID.getInt(0);

        }
        ShopDataBase.close();
        return 0;
    }

    public int InsertNewOrder(String Date, int custid ,String address)
    {
        ShopDataBase = getWritableDatabase();
        ContentValues row=new ContentValues();
        row.put("OrdDate", Date);
        row.put("custid", custid);
        row.put("Address", address );

        ShopDataBase.insert("Order_table", null, row);
        Cursor OrdID = ShopDataBase.rawQuery("select OrdID from Order_table where custid" + "=" + "\"" +custid+ "\"", null);

        if(OrdID.getCount()!= 0){
            OrdID.moveToFirst();
            ShopDataBase.close();
            return OrdID.getInt(0);

        }
        ShopDataBase.close();
        return 0;
    }

    public void InsertNewOrderDetails(int ordid , int prodid , int quan)
    {
        ShopDataBase = getWritableDatabase();
        ContentValues row=new ContentValues();
        row.put("OrdDID", ordid);
        row.put("prodid", prodid);
        row.put("quantity", quan );

        ShopDataBase.insert("Order_details", null, row);
        ShopDataBase.close();
    }


    public Cursor GetOrderQuan(int Ordid)
    {
        ShopDataBase = getReadableDatabase();
        Cursor OrderDetails = ShopDataBase.query("Order_details", new String[]{"prodid","quantity"}, "OrdDID="+Ordid, null, null, null, null);
        OrderDetails.moveToFirst();
        ShopDataBase.close();
        return OrderDetails;
    }

    public Cursor GetOrderName(int ProdID)
    {
        ShopDataBase = getReadableDatabase();
        Cursor OrderDetails = ShopDataBase.query("product", new String[]{"ProdName","Price"}, "ProdID="+ProdID, null, null, null, null);
        OrderDetails.moveToFirst();
        ShopDataBase.close();
        return OrderDetails;
    }

    public Cursor searchname(String name)
    {
        ShopDataBase = getReadableDatabase();
        Cursor searched = ShopDataBase.rawQuery("select ProdName from product where ProdName like?",new String[] {"%"+name+"%"}) ;

        if(searched.getCount()!= 0){
            searched.moveToFirst();
            ShopDataBase.close();
            return searched;
        }
        ShopDataBase.close();
        return null;

    }
}

