package stepDefinitions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Map;

import org.junit.Assert;

import basetest.BaseTest;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pageObjects.CalendarPage;
import utilities.FunCatFacts;
import utilities.DriverManager;

public class CalendarStepDef extends BaseTest {

	String funCatFact;

	@Given("{string} User launches Chrome Browser")
	public void user_launches_chrome_browser(String userType) {
		DriverManager driverManager = new DriverManager(userType);
		calendarPage = new CalendarPage(driverManager.getDriver());
	}

	@When("user launch the url {string}")
	public void user_opens_application(String url) {
		DriverManager.openPage(url);
	}

	@When("User selects a month {string}")
	public void user_selects_month(String month) {
		calendarPage.selectMonth(month);
		assertTrue("Assertion Failed: Updated month '" + month + "' is not displayed", calendarPage.getHeader2Text().contains(month));
	}

	@Then("User validate the days of the month {string}")
	public void user_validate_days_of_month(String month) {
		List<String> actualListOfDays = calendarPage.fetchListOfDaysOfMonth(month);
		List<String> expectedListOfDays = calendarPage.getAllDatesOfMonth(month);
		Assert.assertFalse("Asserion Failed: List of dates from the calender has a duplicate number",
				calendarPage.validateListIsNotContainingDuplicateDate(actualListOfDays));
		Assert.assertEquals(expectedListOfDays, actualListOfDays);
	}

	@When("User adds an event and verify newly added event")
	public void userAddAnEvent(DataTable dataTable) {
		List<Map<String, String>> eventDetails = dataTable.asMaps(String.class, String.class);
		for (Map<String, String> user : eventDetails) {
			String date = user.get("date");
			String title = user.get("title");
			String color = user.get("color");
			calendarPage.clickOnDateGrid(date);
			calendarPage.verifyAddEventFormIsDisplayed();
			calendarPage.addAnEvent(title, "Description", color);
			Assert.assertTrue(calendarPage.validateNewlyAddedEvent(date, title));
			Assert.assertTrue(calendarPage.getColorOfEvent(date, title).contains("bg-" + color));
		}

	}

	@When("User validate the event")
	public void userValidateEvent(DataTable dataTable) {
		List<Map<String, String>> eventDetails = dataTable.asMaps(String.class, String.class);
		for (Map<String, String> user : eventDetails) {
			String date = user.get("date");
			String title = user.get("title");
			String color = user.get("color");
			Assert.assertTrue(calendarPage.validateNewlyAddedEvent(date, title));
			Assert.assertTrue(calendarPage.getColorOfEvent(date, title).contains("bg-" + color));
		}

	}

	@Then("User deletes an event and verify deleted event is not displayed")
	public void userDeletesAnEvent(DataTable dataTable) {
		List<Map<String, String>> eventDetails = dataTable.asMaps(String.class, String.class);
		for (Map<String, String> user : eventDetails) {
			String date = user.get("date");
			String title = user.get("title");
			calendarPage.deleteAnEvent(date, title);
			Assert.assertFalse(calendarPage.validateNewlyAddedEvent(date, title));
		}
	}

	@When("User fetch random Fun Cat Fact using API")
	public void userFetchRandomFunCatFactUsingAPI() {
		funCatFact = FunCatFacts.getFunFact();
		System.out.println(funCatFact);
	}

	@When("User adds the fun cat fact in the header")
	public void userAddsTheFunCatFactInTheHeader() {
		calendarPage.addFunCatFactInTheHeader(funCatFact);
	}

	@Then("User verify header contains fun cat fact")
	public void userVerifyHeaderContainsFunCatFact() {
		Assert.assertTrue("Assertion Failed: Header text does not contains '" + funCatFact + "'.",
				calendarPage.getHeader2Text().contains(funCatFact));
	}

	@When("User changes the text Calendar to {string}")
	public void userChangesTheTextCalendar(String newText) {
		calendarPage.changeElementTextUsingJS(newText);
	}

	@Then("User verify {string} is displayed")
	public void verify(String newText) {
		assertEquals("Assertion Failed: " + newText + " is not displayed in the header ", calendarPage.getDiaryHeader(),
				newText);
	}

}