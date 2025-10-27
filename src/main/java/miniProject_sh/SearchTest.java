package miniProject_sh;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.File;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class SearchTest {
	
	WebDriver driver;
	WebDriverWait wait;
	XSSFWorkbook workbook;
	XSSFSheet sheet;
	String[] excelHeadings = {"Flight Name", "Departure Loc", "Arrival Loc", "Departure Time", "Arrival Time", "Duration", "Price"};
	
	public String[] retrieveGoingData() {
		// name depL arrL DepT ArrT Dur price
		
		String[] data = new String[7];
		
//		driver.findElement(By.xpath(""))  driver.findElement(By.xpath("")).getText();
		
		data[0] = driver.findElement(By.xpath("/html/body/div[3]/div[2]/div[2]/div/div[2]/div[3]/div[1]/div[2]/div/p")).getText();
		
		data[1] = driver.findElement(By.xpath("/html/body/div[3]/div[2]/div[2]/div/div[2]/div[3]/div[1]/div[2]/div/div[2]/div[1]/div[2]/div/div[1]/div/div/p")).getText();
		
		data[2] = driver.findElement(By.xpath("/html/body/div[3]/div[2]/div[2]/div/div[2]/div[3]/div[1]/div[2]/div/div[2]/div[1]/div[2]/div/div[3]/div/div/p")).getText();
		
		data[3] = driver.findElement(By.xpath("/html/body/div[3]/div[2]/div[2]/div/div[2]/div[3]/div[1]/div[2]/div/div[2]/div[1]/div[2]/div/div[1]/div/div/div/h5")).getText();
		
		data[4] = driver.findElement(By.xpath("/html/body/div[3]/div[2]/div[2]/div/div[2]/div[3]/div[1]/div[2]/div/div[2]/div[1]/div[2]/div/div[3]/div/div/div/h5")).getText();
		
		data[5] = driver.findElement(By.xpath("/html/body/div[3]/div[2]/div[2]/div/div[2]/div[3]/div[1]/div[2]/div/div[2]/div[1]/div[2]/div/div[2]/div/p[1]")).getText();
		
		data[6] = driver.findElement(By.xpath("/html/body/div[3]/div[2]/div[2]/div/div[2]/div[3]/div[1]/div[2]/div/div[2]/div[2]/h5")).getText();
		
		return data;
	}
	
	public String[] retrieveComingData() {
		
		String[] data = new String[7];
		
		data[0] = driver.findElement(By.xpath("/html/body/div[3]/div[2]/div[2]/div/div[2]/div[3]/div[2]/div[2]/div/p")).getText();
		
		data[1] = driver.findElement(By.xpath("/html/body/div[3]/div[2]/div[2]/div/div[2]/div[3]/div[2]/div[2]/div/div[2]/div[1]/div[2]/div/div[1]/div/div/p")).getText();
		
		data[2] = driver.findElement(By.xpath("/html/body/div[3]/div[2]/div[2]/div/div[2]/div[3]/div[2]/div[2]/div/div[2]/div[1]/div[2]/div/div[3]/div/div/p")).getText();
		
		data[3] = driver.findElement(By.xpath("/html/body/div[3]/div[2]/div[2]/div/div[2]/div[3]/div[2]/div[2]/div/div[2]/div[1]/div[2]/div/div[1]/div/div/div/h5")).getText();
		
		data[4] = driver.findElement(By.xpath("/html/body/div[3]/div[2]/div[2]/div/div[2]/div[3]/div[2]/div[2]/div/div[2]/div[1]/div[2]/div/div[3]/div/div/div/h5")).getText();
		
		data[5] = driver.findElement(By.xpath("/html/body/div[3]/div[2]/div[2]/div/div[2]/div[3]/div[2]/div[2]/div/div[2]/div[1]/div[2]/div/div[2]/div/p[1]")).getText();
		
		data[6] = driver.findElement(By.xpath("/html/body/div[3]/div[2]/div[2]/div/div[2]/div[3]/div[2]/div[2]/div/div[2]/div[2]/h5")).getText();
		
		return data;
	}
	
	public void addDataInExcel(String[] data, int rowNum) {
		XSSFRow row = sheet.createRow(rowNum);
		
		for(int i = 0;i<7;i++) {
			XSSFCell cell = row.createCell(i);
			cell.setCellValue(data[i]);
		}
	}
	
	public void saveExcelFile() throws IOException {
		String fileName =  new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date()) + ".xlsx";
		String fileLocation = System.getProperty("user.dir") + "/retrievedExcelFile/";
		
		FileOutputStream out = new FileOutputStream(new File(fileLocation + fileName));
		
		workbook.write(out);
		
		out.close();
	}
	
	
	
	@BeforeTest
	public void setUp() {
		driver = new ChromeDriver();
		wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		
		workbook = new XSSFWorkbook();
		
		sheet = workbook.createSheet("chrome");
		
		
	}
	
	@Test(priority = 1)
	public void openBrowser() throws InterruptedException {
		driver.get("https://www.ixigo.com/");
		driver.manage().window().maximize();
		
		Thread.sleep(3000);
		
		driver.navigate().refresh();
		
		
		
		
	}
	
	
	@Test(priority = 2)
	public void fillSearchForm() throws InterruptedException {
		
		//Selecting Round trip option
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text()='Round Trip']")));
		driver.findElement(By.xpath("//button[text()='Round Trip']")).click();
		
		Thread.sleep(3000);
		
		//From field
		driver.findElement(By.xpath("/html/body/main/div[2]/div[1]/div[3]/div[2]/div[1]/div[1]/div[1]/div")).click();
		
		driver.findElement(By.xpath("/html/body/main/div[2]/div[1]/div[3]/div[2]/div[1]/div[1]/div[2]/div/div/div[2]/input")).sendKeys("CHE");
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/main/div[2]/div[1]/div[3]/div[2]/div[1]/div[1]/div[3]/div[1]/div[1]/div[2]")));
		driver.findElement(By.xpath("/html/body/main/div[2]/div[1]/div[3]/div[2]/div[1]/div[1]/div[3]/div[1]/div[1]/div[2]")).click();
		
		// To field
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/main/div[2]/div[1]/div[3]/div[2]/div[1]/div[2]/div[2]/div/div/div[2]/input")));
		driver.findElement(By.xpath("/html/body/main/div[2]/div[1]/div[3]/div[2]/div[1]/div[2]/div[2]/div/div/div[2]/input")).sendKeys("DEL");
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/main/div[2]/div[1]/div[3]/div[2]/div[1]/div[2]/div[3]/div[1]/div[1]")));
		driver.findElement(By.xpath("/html/body/main/div[2]/div[1]/div[3]/div[2]/div[1]/div[2]/div[3]/div[1]/div[1]")).click();
		
		//Departure Date
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/main/div[2]/div[1]/div[3]/div[2]/div[2]/div[3]/div/div[1]/div[2]/div[2]/div/div/div[2]/button[11]")));
		driver.findElement(By.xpath("/html/body/main/div[2]/div[1]/div[3]/div[2]/div[2]/div[3]/div/div[1]/div[2]/div[2]/div/div/div[2]/button[11]")).click();
		
		// Arrival Date
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/main/div[2]/div[1]/div[3]/div[2]/div[2]/div[3]/div/div[1]/div[2]/div[2]/div/div/div[2]/button[20]")));
		driver.findElement(By.xpath("/html/body/main/div[2]/div[1]/div[3]/div[2]/div[2]/div[3]/div/div[1]/div[2]/div[2]/div/div/div[2]/button[20]")).click();
		
		//Passengers
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/main/div[2]/div[1]/div[3]/div[2]/div[3]/div[2]/div/div[1]/div[1]/div[2]/div/button[2]")));
		driver.findElement(By.xpath("/html/body/main/div[2]/div[1]/div[3]/div[2]/div[3]/div[2]/div/div[1]/div[1]/div[2]/div/button[2]")).click();
		
		driver.findElement(By.xpath("/html/body/main/div[2]/div[1]/div[3]/div[2]/div[3]/div[2]/div/div[1]/div[2]/div[2]/div/button[2]")).click();
		
		driver.findElement(By.xpath("/html/body/main/div[2]/div[1]/div[3]/div[2]/div[3]/div[2]/div/div[1]/div[5]/div/div[3]")).click();
		
		// Click on Search Button
		driver.findElement(By.xpath("/html/body/main/div[2]/div[1]/div[3]/div[2]/button")).click();
		
		Thread.sleep(6000);
	}
	
	@Test(priority = 3)
	public void validateSearchResult() {
//		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("")));
//		
//		driver.findElement(By.xpath("")).click();
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[3]/div[2]/div[2]/div/div[2]/div[1]/div[1]/div[2]/p[1]")));
		
		String searchResult = driver.findElement(By.xpath("/html/body/div[3]/div[2]/div[2]/div/div[2]/div[1]/div[1]/div[2]/p[1]")).getText();
		
		Assert.assertEquals(searchResult, "MAA - DEL", "searchResult is incorrect");
		
		
	}
	
	@Test(priority = 4, dependsOnMethods="validateSearchResult", enabled = true)
	public void retriveFlightDate() throws IOException {
		// Retrieve Cheapest Flight details 
		String[] goingFlightData = retrieveGoingData();
		
		String[] comingFlightData = retrieveComingData();
		
		addDataInExcel(excelHeadings, 0);
		addDataInExcel(goingFlightData, 1);
		addDataInExcel(comingFlightData, 2);
		
		saveExcelFile();
		
	}
	
	
	@AfterTest
	public void tearDown() throws IOException {
		workbook.close();
		driver.close();
	}
	

}
