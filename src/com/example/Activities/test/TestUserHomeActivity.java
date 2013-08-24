package com.example.Activities.test;

import com.jayway.android.robotium.solo.Solo;
import com.example.Activities.UserHomeActivity;
import com.example.moodle.R;

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
	}
	
	//test what happens when the courses imageButton is clicked
	public void testCoursesClicked(){
		//Courses has the index 2
		solo.clickOnImageButton(2);
		
		//Wait until next activity starts
		Activity current = solo.getActivityMonitor().waitForActivity();
		Class cu = current.getClass();
				
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
		//profile has the index 1
		
		solo.clickOnImageButton(1);
		//Wait until next activity starts
			Activity current = solo.getActivityMonitor().waitForActivity();
			Class cu = current.getClass();
				
			//the ProfileActivity should start
			try {
				Class next = Class.forName("com.example.Activities.ProfileActivity");
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
