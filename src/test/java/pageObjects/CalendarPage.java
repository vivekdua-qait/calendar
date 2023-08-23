package pageObjects;

import java.text.SimpleDateFormat;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.apache.http.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import basetest.BaseTest;
import utilities.DriverManager;

public class CalendarPage extends BaseTest {
	
	public WebDriver driver;
	
	public CalendarPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//div[contains(@class,'h-screen')]/header//button//span[text()='chevron_right']")
	WebElement headerRightArrow;

	@FindBy(xpath = "//div[contains(@class,'h-screen')]/header//button//span[text()='chevron_left']")
	WebElement headerLeftArrow;

	@FindBy(xpath = "//div[contains(@class,'grid-cols')]//p[contains(@class,'text-center')]")
	List<WebElement> eachDateGrid;

	@FindBy(css = "div[id='root'] form")
	WebElement addEventForm;

	@FindBy(css = "input[name='title']")
	WebElement eventTitle;

	@FindBy(css = "input[name='description']")
	WebElement eventDescription;

	@FindBy(css = "button[type='submit']")
	WebElement saveButton;

	@FindBy(xpath = "//span[text()='delete']")
	WebElement deleteIcon;

	@FindBy(xpath = "//div[contains(@class,'grid-cols')]")
	WebElement dateGridparent;

	@FindBy(css = "span[class*='red']")
	WebElement colorIcon;

	@FindBy(css = "header h1")
	WebElement diaryHeader;

	@FindBy(css = "header h2")
	WebElement diaryHeader2;

	public WebElement getDynamicDateGrid(String date) {
		return dateGridparent.findElement(By.xpath("//p[text()='" + date + "']/../following-sibling::div"));
	}

	public WebElement getDynamicColorIcon(String color) {
		return dateGridparent.findElement(By.xpath("//span[contains(@class,'" + color + "')]"));
	}

	public WebElement getDynamicAddedEventGrid(String date, String title) {
		return dateGridparent.findElement(By.xpath("//div[contains(@class,'grid-cols')]//p[text()='" + date
				+ "']/parent::header/following-sibling::div/div[text()='" + title + "']"));

	}

	public void selectMonth(String month) {
		int monthNum = getNumberFromMonthName(month, Locale.ENGLISH);
		Month currentMonth = Month.of(8);
		Month givenMonth = Month.of(monthNum);
		int difference = currentMonth.compareTo(givenMonth);
		if (difference < 0) {
			clickRightArrow(difference);
		} else if (difference > 0) {
			clickLeftArrow(difference);
		} else if (difference == 0) {
			// do nothing
		}
	}

	public int getNumberFromMonthName(String monthName, Locale locale) throws ParseException {
		DateTimeFormatter dtFormatter = DateTimeFormatter.ofPattern("MMMM").withLocale(locale);
		TemporalAccessor temporalAccessor = dtFormatter.parse(monthName);
		return temporalAccessor.get(ChronoField.MONTH_OF_YEAR);
	}

	public String getDiaryHeader() {
		return diaryHeader.getText();
	}

	public void clickRightArrow(int numberOfTimes) {
		while (numberOfTimes < 0) {
			headerRightArrow.click();
			numberOfTimes++;
		}
	}

	public void clickLeftArrow(int numberOfTimes) {
		while (numberOfTimes > 0) {
			headerLeftArrow.click();
			numberOfTimes--;
		}
	}

	public int getMinimumDayOfMonth(String month) {
		int monthNum = getNumberFromMonthName(month, Locale.ENGLISH) - 1;
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.MONTH, monthNum);
		return cal.getActualMinimum(Calendar.DATE);
	}

	public int getMaximunDayOfMonth(String month) {
		int monthNum = getNumberFromMonthName(month, Locale.ENGLISH) - 1;
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.MONTH, monthNum);
		return cal.getActualMaximum(Calendar.DATE);
	}

	public List<String> getAllDatesOfMonth(String month) {
		Calendar cal = Calendar.getInstance(); // or pick another time zone if necessary
		int maxDay = getMaximunDayOfMonth(month);
		SimpleDateFormat df = new SimpleDateFormat("dd");
		List<String> listOfDates = new ArrayList<>();
		for (int i = 0; i < maxDay; i++) {
			cal.set(Calendar.DAY_OF_MONTH, i + 1);
			listOfDates.add(df.format(cal.getTime()).toString());
		}
		return listOfDates;

	}

	public List<String> fetchListOfDaysOfMonth(String month) {
		int maxDay = getMaximunDayOfMonth(month);
		int minDay = getMinimumDayOfMonth(month);
		List<String> listofcurrentMonth = new ArrayList<>();

		for (WebElement date : eachDateGrid) {
			listofcurrentMonth.add(date.getText());
		}

		for (WebElement date : eachDateGrid) {
			if (!date.getText().equalsIgnoreCase("0" + minDay)) {
				listofcurrentMonth.remove(date.getText());
			} else {
				break;
			}
		}

		try {
			listofcurrentMonth = listofcurrentMonth.subList(0, maxDay);
		} catch (IndexOutOfBoundsException e) {
			// TODO: handle exception
			System.out.println("Maximun day of the month is not visible");
		}
		return listofcurrentMonth;
	}

	public boolean validateListIsNotContainingDuplicateDate(List<String> listofdates) {
		Set<String> seen = new HashSet<>();
		for (String num : listofdates) {
			if (seen.contains(num)) {
				System.out.println("Duplicate number: " + num);
				return true; // Duplicate found
			}
			seen.add(num);
		}
		return false; // No duplicates found
	}

	public void clickOnDateGrid(String date) {
		getDynamicDateGrid(date).click();
	}

	public void verifyAddEventFormIsDisplayed() {
		addEventForm.isDisplayed();
	}

	public void enterEventTitle(String title) {
		eventTitle.isDisplayed();
		eventTitle.sendKeys(title);
	}

	public void enterEventDescription(String description) {
		eventDescription.isDisplayed();
		eventDescription.sendKeys(description);
	}

	public void clickOnSaveButton() {
		saveButton.isDisplayed();
		saveButton.click();
	}

	public void addAnEvent(String title, String description, String color) {
		enterEventTitle(title);
		enterEventDescription(description);
		selectEventColor(color);
		clickOnSaveButton();
	}

	public void selectEventColor(String color) {
		if (!color.equals("indigo")) {
			getDynamicColorIcon(color).click();
			;
		}
	}

	public boolean validateNewlyAddedEvent(String date, String title) {
		try {
			getDynamicAddedEventGrid(date, title).isDisplayed();
		} catch (NoSuchElementException e) {
			// TODO Auto-generated catch block
			return false;
		}
		return true;
	}

	public String getColorOfEvent(String date, String title) {
		return getDynamicAddedEventGrid(date, title).getAttribute("class");
	}

	public void deleteAnEvent(String date, String title) {
		clickOnEventTitle(date, title);
		clickOnDeleteIcon();
	}

	public void clickOnDeleteIcon() {
		deleteIcon.click();
	}

	public void clickOnEventTitle(String date, String title) {
		getDynamicAddedEventGrid(date, title).isDisplayed();
		getDynamicAddedEventGrid(date, title).click();
	}

	public String getHeader2Text() {
		return diaryHeader2.getText();
	}
	public void addFunCatFactInTheHeader(String funFact) {
		String existingText = getHeader2Text();
		DriverManager.executeJavascript("document.getElementsByTagName('h2')[0].innerText = '"+existingText+ ", " +funFact+"'");
	}

	public void changeElementTextUsingJS(String newText) {
		DriverManager.executeJavascript("document.getElementsByTagName('h1')[0].innerText = '" + newText + "'");
	}
}