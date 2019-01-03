package com.fedex.ci.sabt.util;

import java.io.File;

public class TestConstants {
	public static final String SABT_TEST_DATA_FILE_NAME = "data" + File.separator + "SABT_Test_Data.xlsx";
	public static final String SABT_TEMP_DATA_DIR = "data" + File.separator;
	public static final String SABT_USERS_SRC_FILE = "data" + File.separator + "users" + File.separator + "users.txt";
	public static final String SABT_SCREEN_SHOTS_DIR = "data" + File.separator + "out" + File.separator + "screenShots"  + File.separator;
	public static final String SABT_USERS_FILE = "C:" + File.separator + "pTemp" + File.separator + "sabt" + File.separator + "user.txt";
	
	public static final String SHEET_LOGIN = "login";
	public static final String SHEET_HOME = "home";
	public static final String SHEET_ACCOUNT_NUMBER_SEARCH = "accountNumberSearch";
	public static final String SHEET_ACCOUNT_INFO_SEARCH = "accountInfoSearch";
	public static final String SHEET_PAGES = "pages";
	public static final String SHEET_ACCOUNT_ACTIVITIES = "accountActivities";
	
	public static final long SLEEP_SHORT_PAUSE = 500;
	public static final long SLEEP_MEDIUM_PAUSE = 1000;
	public static final long SLEEP_LONG_PAUSE = 7500;
}
