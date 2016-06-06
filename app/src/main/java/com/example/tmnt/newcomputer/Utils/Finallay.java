package com.example.tmnt.newcomputer.Utils;

import android.os.Environment;

import java.io.File;

/**
 * 要使用的常量
 * @author tmnt
 *
 */
public class Finallay {
	public static final String DB_NAME = "xinxijishu.db";//表名
	public static final String TABLE_NAME = "copy";//文件夹名
	public static final String TABLE_NAME2 = "quiz";
	public static final String TABLE_NAME3 = "quizResult";
 	public static final int DB_VERSION = 1;

	public static final String T01_COLUMN_ID = "_id";
	public static final String TO1_COLUMN_NAME = "name";
	public static final String TO1_COLUMN_CONTENT = "content";
     
	public static  String prefsFileName = "userInfo";
	
	public static final int BACK_LAST_DIALOG_YES_KEY = 0x101;	
	public static final int BACK_LAST_DIALOG_NO_KEY = 0x102;	

	//数据库表的存放位置
	public static final String FILE_PAPER_PATH =Environment.getExternalStorageDirectory()
			+File.separator+"transrules";
	public static final String FILE_PATH = FILE_PAPER_PATH
			+File.separator+"transrules.db";
	public static final String TEP_FILE_PATH = Environment.getExternalStorageDirectory()
			+File.separator+"transrules"+File.separator+"a.db";
	public static final String SDCADR = Environment.getExternalStorageDirectory().getAbsolutePath();
}
