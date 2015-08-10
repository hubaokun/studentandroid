package db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;



import hzyj.guangda.student.entity.Area;
import hzyj.guangda.student.entity.City;
import hzyj.guangda.student.entity.Province;

import java.util.ArrayList;
import java.util.List;


public class DBManager {
    private DBHelper helper;
    private SQLiteDatabase db;
    private Context mContext;
    public DBManager(Context context) {
        mContext=context;
        helper = new DBHelper(context);
        //因为getWritableDatabase内部调用了mContext.openOrCreateDatabase(mName, 0, mFactory);
        //所以要确保context已初始化,我们可以把实例化DBManager的步骤放在Activity的onCreate里
        db = helper.getWritableDatabase();
    }
    /**
     * query all persons, return list
     * @return List<Person>
     */
    public List<Area> queryArea(String cityid) {
        ArrayList<Area> arealist = new ArrayList<Area>();
        Cursor c = queryTheCursor(cityid);
        while (c.moveToNext()) {
            Area area = new Area();
            area.zoneid = c.getString(c.getColumnIndex("areaid"));
            area.zonename = c.getString(c.getColumnIndex("area"));
            arealist.add(area);
        }
        c.close();
        return arealist;
    }
    /**
     * @return
     */
    public List<City> queryCity(String provinid) {
        ArrayList<City> citylist = new ArrayList<City>();
        Cursor c = queryAllCity(provinid);
        while (c.moveToNext()) {
            City city = new City();
            city.cityid = c.getString(c.getColumnIndex("cityid"));
            city.cityname = c.getString(c.getColumnIndex("city"));
            city.baiduid = c.getString(c.getColumnIndex("baiduid"));
            citylist.add(city);
        }
        c.close();
        return citylist;
    }
    
    public City queryCityName(String cityname)
    {
        Cursor c = queryCityByName("\""+cityname+"\"");
            City city = new City();
            city.cityid = c.getString(c.getColumnIndex("cityid"));
            city.cityname = c.getString(c.getColumnIndex("city"));
            city.baiduid = c.getString(c.getColumnIndex("baiduid"));
        c.close();
        return city;
    }

    public List<Province> queryProvince()
    {
        ArrayList<Province> provincelist = new ArrayList<Province>();
        Cursor c = queryAllProvince();
        while (c.moveToNext())
        {
        	  Province province = new Province();
              province.provinceId = c.getString(c.getColumnIndex("provinceid"));
              province.provinceName = c.getString(c.getColumnIndex("province"));
              provincelist.add(province);
        }
        c.close();
        return provincelist;
    }

    /**
     * query all persons, return cursor
     * @return	Cursor
     */
    public Cursor queryTheCursor(String cityid) {
        Cursor c = db.rawQuery("SELECT * FROM T_Zone Where cityid=?",  new String[]{cityid});
        return c;
    }
    public Cursor queryAllCity(String provinceid){
        Cursor c = db.rawQuery("SELECT * FROM T_City where provinceid=? ", new String[]{provinceid});
        return c;
    }
    
    public Cursor queryCityByName(String cityname)
    {
    	//Cursor c = db.rawQuery("SELECT * FROM T_City where city like '%123%'", null);
    	Cursor c = db.rawQuery("SELECT * FROM T_City where city='123'", null);
    	return c;
    }



    public Cursor queryAllProvince()
    {
        Cursor c = db.rawQuery("SELECT * FROM T_Province",null);
        return c;
    }
    public void closeDB() {
        db.close();
    }
    
    
    public String getCity(String cityid)
    {
    	String cityname = "";
    	Cursor c = queryCityCursor(cityid);
    	while(c.moveToNext())
    	{
    		cityname = c.getString(c.getColumnIndex("city"));
    	}
    	return cityname;
    }
    
    public String getProvince(String provinceid)
    {
    	String provincename = "";
    	Cursor c = queryProvinceCursor(provinceid);
    	while(c.moveToNext())
    	{
    		provincename = c.getString(c.getColumnIndex("province"));
    	}
    	return provincename;
    }
    
    
    public String getArea(String areaid)
    {
    	String areaname = "";
    	Cursor c = queryAreaCursor(areaid);
    	while(c.moveToNext())
    	{
    		areaname = c.getString(c.getColumnIndex("area"));
    	}
    	return areaname;
    }
    
    public Cursor queryCityCursor(String cityid)
    {
    	Cursor c = db.rawQuery("SELECT * FROM T_City Where cityid=?",  new String[]{cityid});
    	return c;
    }
    
    public Cursor queryProvinceCursor(String provinceid)
    {
    	Cursor c = db.rawQuery("SELECT * FROM T_Province Where provinceid=?",  new String[]{provinceid});
    	return c;
    }
    
    public Cursor queryAreaCursor(String areaid)
    {
    	Cursor c = db.rawQuery("SELECT * FROM T_Zone Where areaid=?",  new String[]{areaid});
    	return c;
    }
}
