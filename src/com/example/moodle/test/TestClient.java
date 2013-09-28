package com.example.moodle.test;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.BeforeClass;
import org.junit.Test;

import android.test.AndroidTestCase;

import com.example.moodle.App;
import com.example.moodle.Client;
import com.example.moodle.Course;
import com.example.moodle.User;

import junit.framework.TestCase;

public class TestClient extends AndroidTestCase{

	/*private static Class aClass;
	static
	{
		try{
			//Get the MainActivity class, if such a class exists
			aClass = Class.forName("Client");
		} catch (ClassNotFoundException e)
		{
			throw new RuntimeException(e); 
		} 
	}
	*/
	
	public TestClient() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	@BeforeClass
	public void setUpClass(){
	}

	@Override
	protected void setUp(){
		App.setDomainUrl(Constants.domainName);
		User user = User.getInstance();
		user.setToken(Constants.token);
		user.setID(3);

	}
	
	@Override
	protected void tearDown(){
		
	}

	//test the makeRequest method
	@Test
	public void testMakeRequest(){
		//get the client instance with the token of u1 and url
		Client client = Client.getInstance();
		JSONObject response = client.makeRequest(Constants.token, Constants.domainName, "moodle_webservice_get_siteinfo","","siteinfoxsl");
		try {
			assertEquals("u1", response.get("username").toString());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			fail(e.toString());
		}
	}
	
	public void testGetCourses(){

		JSONObject response = Client.getInstance().getCourses();
		
		try {
			ArrayList<String> courses=new ArrayList<String>();
			JSONArray coursesArray = response.getJSONArray("courses");
			for (int i=0;i<coursesArray.length();i++){
				courses.add(((JSONObject)coursesArray.get(i)).getString("idnumber"));
			}
			assertEquals("SE2012", courses.get(1));
			assertEquals("DS2022", courses.get(0));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			fail(e.toString());
		}
	}
	
	public void testGetCourseContent(){
		
	}
	
	public void testGetMyProfile(){
		JSONObject response = Client.getInstance().getMyProfile();
		try {
			assertEquals("u1 u", response.getString("fullname"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail(e.toString());
		}
	}
}
