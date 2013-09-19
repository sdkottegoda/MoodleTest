package com.example.Activities.test;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

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
		} catch (ClassNotFoundException e)
		{
			throw new RuntimeException(e); 
		} 
	}
	
	public TestCoursesActivity() {
		//pass the UserHomeActivity to the super class
		super(aClass);
		User u=User.getInstance();
		try {
			u.addCourse(new JSONObject(Constants.course));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	protected void setUp(){
		//initiate a solo object before running the test class
		solo = new Solo(getInstrumentation(),getActivity());
		User u=User.getInstance();
		try {
			u.addCourse(new JSONObject(Constants.course));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void testCourseClicked(){
		
		
		
		//click on DS
		//solo.clickInList(1);
		
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
