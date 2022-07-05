package KDD.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import com.google.common.collect.Table.Cell;

public class ReusableFunction {
	WebDriver driver;
	public  String[][] fetchDataFromExcel(){
		
		Workbook wb=null;
		String[][] data =null;
		try{
			String path=fetchprop("KEYWORD_PATH"); //path to excel file fetched from object properties saved in path variable
			File excel = new File(path); //path variable passed in file
			FileInputStream file = new FileInputStream(excel); //file passed to fileinputstream
			//Above three stesp are File handling
			
			System.out.println(path); //optional-prints the patch from where excel is coming
			String extn=path.substring(path.indexOf(".")+1); //takes the letters upto index "." and next 1
			
			//This below code check if file is xlsx or not and is optional as new libraries can use all formats
			System.out.println(extn);
			if(extn.equals("xlsx")){
				wb=new XSSFWorkbook(file);
			}else{
				wb=new HSSFWorkbook(file);
			}
			
			//Gets the sheet name
			Sheet sheet=wb.getSheet("Sheet1");
			int rownum = sheet.getLastRowNum(); //counts the last row num in the sheet
			System.out.print("Rows : ", +rownum);
			int column = sheet.getRow(0).getLastCellNum(); //counts the col num. We pick teh first row and move to last row number 
			// so we get the number of column 
			
			data = new String[rownum][column]; //return data is stored here
			
			//Below code is to fetch the data returned in rows and columns which is file handling
			for (int i=0;i<rownum;i++){
				Row row=sheet.getRow(i);
				for (int j=0;j<column;j++){
					Cell cell=row.getCell(j);
					String value = cell.toString();
					data[i][j]=value;
				}
			}
		}catch (Exception ex){
			ex.printStackTrace();
		}finally{
			try{
				wb.close();
			} catch (IOException e){
					e.printStackTrace();
				}}
			return data;
	
	}
	public static String fetchprop(String text){ //this is for picking data from object repository and used 
		// in ExecuteTestRunner
		Properties prop=new Properties(); //properties is a system file
		FileInputStream input; 
		try{
			//creating object of the properties file
			input = new FileInputStream(System.getProperty("user.dir")+"\\src\\objects.properties"); 
			prop.load(input);  //load all data from the properties file and this load keeps the data in keyword=value format
		}catch (Exception ex){
		}
		return prop.getProperty(text); //here the text is the variable(leftside) name and it returns its value(right side)
	}
			
	
	
	//defining the methods/keywords to be used in excel whihc are called in executetestrunner
	public void launchApp(){  
		System.setProperty("webdriver.chrome.driver", "\\Drivers\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.get(fetchprop("URL"));
	}
	
	// Defining the locator values to be picked from excel
	// Fetching the values of column of locatortype, locator valye and its any data passed as this is a enter text item where
	// all the element name, its data is used. This may not be case in click where only element path is required
	public void fillText(String locatorBy, String locatorValue, String text){
		switch(locatorBy){
		case "xpath":
			driver.findElement(By.xpath(locatorValue)).sendKeys(text);
			break;
		case "name":
			driver.findElement(By.name(locatorValue)).sendKeys(text);
			}
	}
	
	
		//Fetching only the locator element path as its a click so no requirement of data
		public void click(String locatorBy, String locatorElement){
			switch(locatorBy){
			case "xpath":
				driver.findElement(By.xpath(locatorElement)).click();
				break;
			case "name":
				driver.findElement(By.name(locatorElement)).click();
			}
	}
	
	
}
