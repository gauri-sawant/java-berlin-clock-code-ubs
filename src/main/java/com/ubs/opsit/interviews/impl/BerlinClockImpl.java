package com.ubs.opsit.interviews.impl;

import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang.text.StrBuilder;
import org.apache.log4j.Logger;

import com.ubs.opsit.interviews.TimeConverter;

/**
 * @author Gauri Sawant
 *
 */
public class BerlinClockImpl implements TimeConverter {

	final static Logger logger = Logger.getLogger(BerlinClockImpl.class);
	private static int FIRSTROWMAXCOL = 4; 
	private static int SECONDROWMAXCOL = 4;
	private static int THIRDROWMAXCOL = 11;
	private static int FOURTHROWMAXCOL = 4;
	private static int HOUR = 24;
	private static int MIN_SECS = 60;
	private static String FIRSTROW = "First Row";
	private static String SECONDROW = "Second Row";
	private static String THIRDROW = "Third Row";
	private static String FOURTHROW = "Fourth Row";
	private static String FIFTHROW = "Fifth Row";
	
	Map<String, StringBuffer> clockDataObj = null;

	void initTimeData() {
		clockDataObj = new TreeMap<String, StringBuffer>();
		clockDataObj.put(FIRSTROW, new StringBuffer());
		clockDataObj.put(SECONDROW, new StringBuffer());
		clockDataObj.put(THIRDROW, new StringBuffer());
		clockDataObj.put(FOURTHROW, new StringBuffer());
		clockDataObj.put(FIFTHROW, new StringBuffer());
	}

	// @Override
	public String convertTime(String aTime) {
		initTimeData();

		String inputData[] = aTime.split(":");
		int hours =0; int mins = 0; int seconds = 0;
		int firstRowVal = 5;
		int thirdRowVal = 5;
		boolean isValidNumber = validateTimeforNumber(inputData); //to check if input in hh:mm:ss only contains numbers
		if(isValidNumber){
			hours = Integer.parseInt(inputData[0]);
			mins = Integer.parseInt(inputData[1]);
			seconds = Integer.parseInt(inputData[2]);
			
			boolean isValidTimeInput = validateInputTime(hours, mins, seconds); //to check if input in hh:mm:ss is within range of the time formats i.e. hr = 24 and min/sec = 60
			
			if (isValidTimeInput) {

				int firstRowHoursCount = hours / firstRowVal;
				int secondRowHoursCount = hours % firstRowVal;
				int thirdRowCount = mins / thirdRowVal;
				int fourthRowCount = mins % thirdRowVal;

				StringBuffer tmp = clockDataObj.get(FIRSTROW);
				if (seconds % 2 != 0) {
					tmp.append("O");
				} else {
					tmp.append("Y");
				}

				tmp = clockDataObj.get(SECONDROW);
				for (int counter = 0; counter < firstRowHoursCount; counter++) {
					tmp.append("R");
				}
				for (int counter = firstRowHoursCount; counter < FIRSTROWMAXCOL; counter++) {
					tmp.append("O");
				}

				clockDataObj.put(SECONDROW, tmp);

				tmp = clockDataObj.get(THIRDROW);
				for (int counter = 0; counter < secondRowHoursCount; counter++) {
					tmp.append("R");
				}
				for (int counter = secondRowHoursCount; counter < SECONDROWMAXCOL; counter++) {
					tmp.append("O");
				}

				clockDataObj.put(THIRDROW, tmp);

				tmp = clockDataObj.get(FOURTHROW);
				for (int counter = 0; counter < thirdRowCount; counter++) {
					if ((counter + 1) % 3 == 0)
						tmp.append("R");
					else
						tmp.append("Y");
				}
				for (int counter = thirdRowCount; counter < THIRDROWMAXCOL; counter++) {
					tmp.append("O");
				}
				clockDataObj.put(FOURTHROW, tmp);

				tmp = clockDataObj.get(FIFTHROW);
				for (int counter = 0; counter < fourthRowCount; counter++) {
					tmp.append("Y");
				}
				for (int counter = fourthRowCount; counter < FOURTHROWMAXCOL; counter++) {
					tmp.append("O");
				}
				clockDataObj.put(FIFTHROW, tmp);

				return displayClock();
			} else {																					
				return "";
			}
		}
		return "";
	}

	private boolean validateTimeforNumber(String[] inputTime) {
		for(String time : inputTime){
			try{
				Integer.parseInt(time);
			}catch(NumberFormatException ne){
				logger.warn("Time defined in input hh:mm:ss should always be a number.");
				return false;
			}
		}
		return true;
	}

	

	private boolean validateInputTime(int hours, int mins, int seconds) {
		
		if(hours<=HOUR && mins<=MIN_SECS && seconds<=MIN_SECS){
			return true;
		}else{
			logger.warn("Please enter correct time as input : hh:mm:ss"); 
			return false;
		}
	}

	String displayClock() {
		return new StrBuilder().appendWithSeparators(new String[]{clockDataObj.get(FIRSTROW).toString(), clockDataObj.get(SECONDROW).toString(),
				clockDataObj.get(THIRDROW).toString(), clockDataObj.get(FOURTHROW).toString(), clockDataObj.get(FIFTHROW).toString()}, System.lineSeparator()).toString();
	}

	

/*	
	 //* Gauri - to test the code without Test 
	 public static void main(String[]args) 
	  { 
	  		BerlinClockImpl time = new BerlinClockImpl();
	  		System.out.println(time.convertTime("06:70:03")); 
	 }*/
	 
}
