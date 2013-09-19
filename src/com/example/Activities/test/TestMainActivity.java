package com.example.Activities.test;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;

import com.example.Activities.MainActivity;
import com.example.Activities.UserHomeActivity;
import com.example.moodle.R;
import com.example.moodle.User;
import com.example.moodle.test.Constants;
import com.jayway.android.robotium.solo.Solo;

public class TestMainActivity extends ActivityInstrumentationTestCase2{
	
	private static String className = "com.example.Activities.MainActivity";
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
	
	public TestMainActivity() {
		//pass the UserHomeActivity to the super class
		super(aClass);
	}
	
	@Override
	protected void setUp(){
		//initiate a solo object before running the test class
		solo = new Solo(getInstrumentation(),getActivity());
	}
	
	public void testOnClick(){
		Activity current = solo.getCurrentActivity();
		
		//set the username and password and click on the login button
		solo.enterText((EditText)current.findViewById(R.id.username),"u1");		
		solo.enterText((EditText)current.findViewById(R.id.password),"Uu1@1234");	
		solo.clickOnButton("Login");		
		
		//Wait until next activity starts
		current = solo.getActivityMonitor().waitForActivity();
		
		//the token returned should match to that in the database
		assertEquals("32cdc90dade4b960af613c3a70d36b1e",((UserHomeActivity)current).getUser().getToken());
		
		Class cu = current.getClass();
		
		//the UserHomeActivity should start
		try {
			Class next = Class.forName("com.example.Activities.UserHomeActivity");
						
			//The next activity should be the UserHomeActivity
			assertEquals(next, cu);
			
		} catch (ClassNotFoundException e) {
			//if UserHomeActivity class doesn't exist, fail
			fail(e.toString());
		}
			
	}
	
	//test the getSiteInfo function
	public void testGetSiteInfo(){
		Activity currrent = solo.getCurrentActivity();
		User user = User.getInstance();
		user.setToken(Constants.token);
		((MainActivity)currrent).getSiteInfo();
		assertEquals("u1", user.getUsername());
	}
	
	@Override
	protected void tearDown(){
		//finish all the opened activities at the end
		solo.finishOpenedActivities();
	}
	
}
