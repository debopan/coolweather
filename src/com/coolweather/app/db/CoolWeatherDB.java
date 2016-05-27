package com.coolweather.app.db;

import java.util.ArrayList;
import java.util.List;

import com.coolweather.app.model.City;
import com.coolweather.app.model.County;
import com.coolweather.app.model.Province;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class CoolWeatherDB {
	/**
	* 数据库名
	*/
	public static final String DB_NAME = "cool_weather";
	/**
	* 数据库版本
	*/
	public static final int VERSION = 1;
	private static CoolWeatherDB coolWeatherDB;
	private SQLiteDatabase db;

	/**
	 * 将构造方法私有化
	 */
	private CoolWeatherDB(Context context) {
		CoolWeatherOpenHelper dbHelper = new CoolWeatherOpenHelper(context,
				DB_NAME, null, VERSION);
		db = dbHelper.getWritableDatabase();
	}
	
	public synchronized static CoolWeatherDB getInstance(Context context){
		if(null==coolWeatherDB){
			coolWeatherDB = new CoolWeatherDB(context);
		}
		return coolWeatherDB;
	}
	
	/**
	* 将Province实例存储到数据库。
	*/
	public void saveProvince(Province province) {
		if(null!=province){
			ContentValues contentVal = new ContentValues();
			contentVal.put("province_name", province.getProvinceName());
			contentVal.put("province_code", province.getProvinceCode());
			db.insert("Province", null, contentVal);
		}
	}
	
	/**
	* 将City实例存储到数据库。
	*/
	public void saveCity(City city) {
		if(null!=city){
			ContentValues contentVal = new ContentValues();
			contentVal.put("city_name", city.getCityName());
			contentVal.put("city_code", city.getCityCode());
			contentVal.put("province_id", city.getProvinceId());
			
			db.insert("Province", null, contentVal);
		}
	}
	/**
	* 将County实例存储到数据库。
	*/
	public void saveCounty(County county) {
		if(null!=county){
			ContentValues contentVal = new ContentValues();
			contentVal.put("county_name", county.getCountyName());
			contentVal.put("county_code", county.getCountyCode());
			contentVal.put("city_id", county.getCityId());
			
			db.insert("Province", null, contentVal);
		}
	}
	
	/**
	 * 
	 */
	public List<County> loadCounties(int cityId) {
		List<County> list = new ArrayList<County>();
		Cursor cursor = db.query("County", null, "city_id = ?",
				new String[] { String.valueOf(cityId) },null, null, null);
		if(cursor.moveToFirst()){
			int nameIdx;
			int codeIdx;
			int idIdx;
			int cityIdx;
			do{
				County county = new County();
				nameIdx = cursor.getColumnIndex("county_name");
				codeIdx = cursor.getColumnIndex("county_code");
				cityIdx = cursor.getColumnIndex("city_id");
				idIdx = cursor.getColumnIndex("id");
				county.setId(cursor.getInt(idIdx));
				county.setCountyName(cursor.getString(nameIdx));
				county.setCountyCode(cursor.getString(codeIdx));
				county.setCityId(cursor.getInt(cityIdx));
				list.add(county);
			}while(cursor.moveToNext());
		}
		
		return list;
	}
	
	/**
	 * 从数据库读取全国所有的省份信息。
	 */
	public List<City> loadCities(int provinceId) {
		List<City> list = new ArrayList<City>();
		Cursor cursor = db.query("city", null, "province_id = ?",
				new String[] { String.valueOf(provinceId) },null, null, null);
		if(cursor.moveToFirst()){
			int nameIdx;
			int codeIdx;
			int idIdx;
			int pvIdIdx;
			do{
				City city = new City();
				nameIdx = cursor.getColumnIndex("city_name");
				codeIdx = cursor.getColumnIndex("city_code");
				pvIdIdx = cursor.getColumnIndex("province_id");
				idIdx = cursor.getColumnIndex("id");
				city.setId(cursor.getInt(idIdx));
				city.setCityName(cursor.getString(nameIdx));
				city.setCityCode(cursor.getString(codeIdx));
				city.setProvinceId(cursor.getInt(pvIdIdx));
				list.add(city);
			}while(cursor.moveToNext());
		}
		
		return list;
	}
	
	/**
	 * 从数据库读取全国所有的省份信息。
	 */
	public List<Province> loadProvinces() {
		List<Province> list = new ArrayList<Province>();
		Cursor cursor = db.query("Province", null, null, null, null, null, null);
		if(cursor.moveToFirst()){
			int nameIdx;
			int codeIdx;
			int idIdx;
			do{
				Province province = new Province();
				nameIdx = cursor.getColumnIndex("province_name");
				codeIdx = cursor.getColumnIndex("province_code");
				idIdx = cursor.getColumnIndex("id");
				province.setId(cursor.getInt(idIdx));
				province.setProvinceName(cursor.getString(nameIdx));
				province.setProvinceCode(cursor.getString(codeIdx));
				list.add(province);
			}while(cursor.moveToNext());
		}
		
		return list;
	}
}
