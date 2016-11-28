package project;

import java.sql.*;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

public class Sql {

	private Connection connector;	
	private Statement statement;
	
	public Sql() {
		try {
			Class.forName("org.postgresql.Driver");
			connector = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postGIS","postgres","heslo123");
			statement = connector.createStatement();
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public String skus(){
		return "AHOOOOJ";
	}
	public List<JSONObject> findParks(String city, String dist) throws SQLException, JSONException{
		List<JSONObject> geoJson = new ArrayList<>();
		ResultSet result = statement.executeQuery("WITH parks AS(SELECT p.way, p.name FROM planet_osm_polygon p "
				+ "WHERE leisure = 'park') SELECT ST_AsGeoJSON(ST_Union(ST_Transform(parks.way,4326))) AS result FROM planet_osm_point p, parks "
				+ "WHERE p.name = '"+city+"' AND (p.place = 'city' OR p.place = 'town') "
				+ "AND ST_Distance(p.way,parks.way) <"+dist);
		while(result.next()){
			JSONObject json = new JSONObject();
			json.put("type", "Feature");
			json.put("geometry", new JSONObject(result.getString("result")));
			JSONObject properties = new JSONObject();
			json.put("properties", properties);
			geoJson.add(json);
		}
		return geoJson;
	}
	public List<JSONObject> findLakes(String limit) throws SQLException, JSONException{
		List<JSONObject> geoJson = new ArrayList<>();
		ResultSet result = statement.executeQuery("WITH lakes AS(SELECT p.way FROM planet_osm_polygon p"
				+ " WHERE p.natural = 'water') SELECT ST_AsGeoJSON(ST_Transform(lakes.way,4326)) AS"
				+ " result, ST_Area(lakes.way) AS area FROM lakes ORDER BY  ST_AREA(lakes.way) DESC"
				+ " LIMIT "+limit);


		while(result.next()){
			JSONObject json = new JSONObject();
			json.put("type", "Feature");
			json.put("geometry", new JSONObject(result.getString("result")));
			JSONObject properties = new JSONObject();
			properties.put("area", result.getString("area"));
			json.put("properties", properties);
			geoJson.add(json);
		}
		return geoJson;
	}
	public List<JSONObject> findCaves(String access) throws SQLException, JSONException{
		List<JSONObject> geoJson = new ArrayList<>();
		ResultSet result = statement.executeQuery("WITH caves as(SELECT way,name FROM planet_osm_point p"
				+ " WHERE p.natural = 'cave_entrance' AND access = '"+access+"')"
				+ " SELECT caves.name,ST_AsGeoJSON(ST_Transform(caves.way,4326)) AS result"
				+ " FROM planet_osm_polygon p,caves	WHERE p.boundary = 'national_park' and ST_Within(caves.way,p.way)");
		while(result.next()){
			JSONObject json = new JSONObject();
			json.put("type", "Feature");
			json.put("geometry", new JSONObject(result.getString("result")));
			JSONObject properties = new JSONObject();
			properties.put("name", result.getString("name"));
			json.put("properties", properties);
			geoJson.add(json);
		}
		return geoJson;
	}
}
