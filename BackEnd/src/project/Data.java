package project;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.*;
import java.sql.*;

import project.Sql;

@Path("/pdt")
public class Data {
	
	private Sql sql = new Sql();
	@GET
	@Path("/parks/{city}/{dist}")
	@Produces(MediaType.TEXT_HTML)
	public String findParks(@PathParam("city") String city,@PathParam("dist") String dist) {
		JSONArray array = new JSONArray();
		try {
			List<JSONObject> result = sql.findParks(city,dist);
			for(JSONObject json : result) {
				array.put(json);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return array.toString();
	}
	@GET
	@Path("/lakes/{limit}")
	@Produces(MediaType.TEXT_HTML)
	public String findLakes(@PathParam("limit") String limit) {
		JSONArray array = new JSONArray();
		try {
			List<JSONObject> result = sql.findLakes(limit);
			for(JSONObject json : result) {
				array.put(json);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return array.toString();
	}
	@GET
	@Path("/caves/{access}")
	@Produces(MediaType.TEXT_HTML)
	public String findCaves(@PathParam("access") String access) {
		JSONArray array = new JSONArray();
		try {
			List<JSONObject> result = sql.findCaves(access);
			for(JSONObject json : result) {
				array.put(json);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return array.toString();
	}

}
