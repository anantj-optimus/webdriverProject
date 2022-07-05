package KDD.testRunner;

import KDD.utils.ReusableFunction;

// create object of reusable class
public class ExecuteTestRunner {
	public static void main(String[] args){
		ReusableFunction func= new ReusableFunction();
		String[][] keywordsData=func.fetchDataFromExcel(); // all excel data will be imported here in keywordsData 
		// object from where we will use which data to particularly pick
		
		int rownum=keywordsData.length; //all row number will be fetched here
		int colnum =keywordsData[0].length; // fetch all column in first row
		
		String TC_Name=keywordsData[0][0];
		String TC_Step_No=keywordsData[0][1];
		String TC_Step_Name=keywordsData[0][2];
		String TC_Func=keywordsData[0][3];
		String TC_location=keywordsData[0][4];
		String TC_LV=keywordsData[0][5];
		String TC_Data=keywordsData[0][6];
		String TC_Execute=keywordsData[0][7];
		
		
		for(int counter=1;counter<=rownum;counter++) {
			String function=keywordsData[counter][3];   // data of colum function will come -launchAppl
			String locatorBy=keywordsData[counter][4]; 	// data of colum Locator will come - NA
			// a seprate function fetchprp is written to fetch data from object file defined in resuable function file 
			// returns null as no data in locator value
			String locatorElement=func.fetchprop(keywordsData[counter][5]); 			
			String content_param=keywordsData[counter][6];
			String execute = keywordsData[counter][7];
			
			if(execute.equals("Y")){
			
			//here the function that we have fetched from above String function=keywordsData[column][3];
			// is matched as per the methods/keywords that  are to perform
			switch(function){  
			case "launchAppl":
				//this method does not use anything from the values fetched from columns as its a fixed method w/o any input
				//its code is written in function only and data is passed from function code as defined in reusable file
				func.launchApp();
				break;
			case "fillText": 
				//fillText method uses locatorBy, locatorElement and content_param from the 
				//above column function where the column values are fetched as it requires the same
				func.fillText(locatorBy, locatorElement, content_param);
				break;
			case"click":
				func.click(locatorBy, locatorElement);
				break;
					
			}
			
			
		}
			
		}

}
