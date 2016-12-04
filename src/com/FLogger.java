package com;

import com.constants.Constant;
import com.log.LogManager;
import com.utils.CommUtil;
import com.utils.TimeUtil;

/**
 * 日志工具类
 * @author yunfeng.cheng
 * @version 2015/10/31
 */
public class FLogger {
	
	private static FLogger instance;
	
	public static synchronized FLogger getInstance(){
		if(instance == null){
			instance = new FLogger();
		}
		return instance;
	}
	
	/**
	 * 写调试日志
	 * @param level
	 * @param logMsg
	 */
	public static void debug(String logMsg){
		FLogger.writeLog("Debug",Constant.DEBUG,logMsg);
	}
	
	/**
	 * 写普通日志
	 * @param level
	 * @param logMsg
	 */
	public static void info(String logMsg){
		FLogger.writeLog("Info",Constant.INFO,logMsg);
	}
	
	/**
	 * 写警告日志
	 * @param level
	 * @param logMsg
	 */
	public static void warn(String logMsg){
		FLogger.writeLog("Warn",Constant.WARN,logMsg);
	}
	
	/**
	 * 写错误日志
	 * @param level
	 * @param logMsg
	 */
	public static void error(String logMsg){
		FLogger.writeLog("Error",Constant.ERROR,logMsg);
	}
	
	/**
	 * 写严重错误日志
	 * @param level
	 * @param logMsg
	 */
	public static void fatal(String logMsg){
		FLogger.writeLog("Fatal",Constant.FATAL,logMsg);
	}
	
	/**
	 * 写系统日志
	 * @param level
	 * @param logMsg
	 */
	public static void writeLog(int level,String logMsg){
		FLogger.writeLog("System",level,logMsg);
	}
	
	/**
	 * 写日志
	 * @param logFile
	 * @param iLevel
	 * @param logMsg
	 */
	public static void writeLog(String logFileName, int level, String logMsg){
		if(logMsg != null && Constant.CFG_LOG_LEVEL.indexOf(""+level) >= 0){
			StringBuffer sb = new StringBuffer(logMsg.length() + 100);
			sb.append(TimeUtil.getFullDateTime());
			sb.append(" [");
			sb.append(Thread.currentThread().getName());
			sb.append("] ");
			sb.append(Constant.LOG_DESC_MAP.get(String.valueOf(level)));
			sb.append(": ");
			sb.append(logMsg);
			sb.append("\n");
			LogManager.getInstance().addLog(logFileName, sb);
			
			//错误信息同时打印到控制台
			if(Constant.ERROR == level || Constant.FATAL == level){
				try{
					System.out.print(new String(sb.toString().getBytes(Constant.CFG_CHARSET_NAME),Constant.CFG_CHARSET_NAME));
				}catch(Exception e){
					System.out.print(CommUtil.getExpStack(e));
				}
			}
		}
	}
	
}