package com.example.utils;


import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.Context;
import android.util.Log;

public class TestUtils {

	public static void dumpTask(Context content) {
		ActivityManager activityManager = (ActivityManager) content.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningTaskInfo > tasklist = activityManager.getRunningTasks(30);
		for ( RunningTaskInfo taskInfo:tasklist) {
			 String packageName = taskInfo.baseActivity.getPackageName();
			 Log.d("dumpTask", "RunningTasks packageName:"+packageName);
		}
	}
}
