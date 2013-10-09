package com.example.Activities.test;

import java.util.ArrayList;

import org.json.JSONObject;
import org.junit.BeforeClass;
import org.junit.Test;

import com.jayway.android.robotium.solo.Solo;
import com.example.Activities.*;
import com.example.Activities.UserHomeActivity.UserInfo;
import com.example.moodle.App;
import com.example.moodle.Client;
import com.example.moodle.Course;
import com.example.moodle.R;
import com.example.moodle.User;
import com.example.moodle.test.Constants;

import android.app.Activity;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;

public class TestUserHomeActivity extends ActivityInstrumentationTestCase2{

	
	private static String className = "com.example.Activities.UserHomeActivity";
	private static Class aClass;
	private Solo solo;
	static{
		try{
			//Get the UserHomeActivity class, if such a class exists
			aClass = Class.forName(className);
		} catch (ClassNotFoundException e){
			System.out.println("no class");
		throw new RuntimeException(e); } }
	
	public TestUserHomeActivity() {
		//pass the UserHomeActivity to the super class
		super(UserHomeActivity.class);

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
	
	@BeforeClass
	public void setUpClass(){

	}

	
	//test what happens when the courses imageButton is clicked
	@Test
	public void testCoursesClicked(){
		solo.waitForActivity("UserHomeActivity"); 
		//Courses has the index 2
		solo.clickOnImageButton(2);
			
		//Wait until next activity starts
		Activity current = solo.getActivityMonitor().waitForActivity();
		Class<? extends Activity> cu = current.getClass();	
		
		//User u1 is enrolled in the courses with the following short names
		String[] courses = new String[] {"DS2022","SE2012"};
		ArrayList<Course> actual = User.getInstance().getCourses();
		
		for (int i=0;i<2;i++){
			assertEquals(courses[i],actual.get(i).getModuleCode());
		}
		//the CoursesActivity should start
		try {
			Class next = Class.forName("com.example.Activities.CoursesActivity");
			
			//The next activity should be the CoursesActivity
			assertEquals(next, cu);
			} catch (ClassNotFoundException e) {
				
				//if CoursesActivity class doesn't exist, fail
				fail(e.toString());
			}
	}
	
	//test what happens when the profile imageButton is clicked
	public void testProfileClicked(){
		System.out.println(solo.getCurrentActivity().toString());
		//profile has the index 1
		solo.clickOnImageButton(1);
		//Wait until next activity starts

		solo.getActivityMonitor().waitForActivity();
			solo.waitForActivity("ProfileActivity",500);

			System.out.println(solo.getCurrentActivity().toString());
			/*Activity current = solo.getCurrentActivity();
			Class cu = current.getClass();
				
			//the ProfileActivity should start
			try {
				Class next = Class.forName("com.example.Activities.ProfileActivity");
				//The next activity should be the UserHomeActivity
				assertEquals(next, cu);
				assertEquals("u1@gmail.com", ((UserInfo)current.getIntent().getExtras().get("userInfo")).getEmail());
			} catch (ClassNotFoundException e) {
				//if ProfileActivity class doesn't exist, fail
				fail(e.toString());
			}
			*/
			solo.assertCurrentActivity(solo.getCurrentActivity().toString(), ProfileActivity.class);
			assertEquals("u1@gmail.com", ((UserInfo)solo.getCurrentActivity().getIntent().getExtras().get("userInfo")).getEmail());
			
	}
	
	@Override
	protected void tearDown(){
		//finish all the opened activities at the end
		solo.finishOpenedActivities();
	}
	
}
