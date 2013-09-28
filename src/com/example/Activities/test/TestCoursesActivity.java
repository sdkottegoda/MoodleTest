package com.example.Activities.test;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.moodle.App;
import com.example.moodle.Course;
import com.example.moodle.User;
import com.example.moodle.test.Constants;
import com.jayway.android.robotium.solo.Solo;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;

public class TestCoursesActivity extends ActivityInstrumentationTestCase2<Activity>{
	
	private static String className = "com.example.Activities.CoursesActivity";
	private static Class aClass;
	private Solo solo;
	static
	{
		try{
			//Get the MainActivity class, if such a class exists
			aClass = Class.forName(className);
			try {
				User u=User.getInstance();
				JSONObject obj=new JSONObject(Constants.course);
				u.addCourse(obj);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e)
		{
			throw new RuntimeException(e); 
		} 
	}
	
	public TestCoursesActivity() {
		//pass the UserHomeActivity to the super class
		super(aClass);
		
	}
	
	@Override
	protected void setUp(){
		//initiate a solo object before running the test class
		solo = new Solo(getInstrumentation(),getActivity());
		User user = User.getInstance();
		user.setToken(Constants.token);
		user.setID(3);
		App.setDomainUrl("http://10.0.2.2/Moodle/moodle/");
	}
	
	public void testCourseClicked(){
		
		
		
		//click on the first course
		solo.clickInList(0);
		
		//Wait until next activity starts
		Activity current = solo.getActivityMonitor().waitForActivity();
		Class<? extends Activity> cu = current.getClass();	
		
		try {
			Class next = Class.forName("com.example.Activities.CourseActivity");
			//The next activity should be the UserHomeActivity
			assertEquals(next, cu);
			
		} catch (ClassNotFoundException e) {
			//if ProfileActivity class doesn't exist, fail
			fail(e.toString());
		}	
		
	}
	
	@Override
	protected void tearDown(){
		//finish all the opened activities at the end
		solo.finishOpenedActivities();
	}
}
