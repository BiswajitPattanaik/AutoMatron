package com.cavisson.pagemodel;

import com.automation.cavisson.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
public class TotalScenariosPage extends PageModel{
	@FindBy(TotalScenariosPage.createScenario.using="TotalScenariosPage.createScenario.value")
	WebElement createScenario;
	@FindBy(TotalScenariosPage.deleteScenario.using="TotalScenariosPage.deleteScenario.value")
	WebElement deleteScenario;
	@FindBy(TotalScenariosPage.searchScenarioBox.using="TotalScenariosPage.searchScenarioBox.value")
	WebElement searchScenarioBox;
	@FindBy(TotalScenariosPage.applyButton.using="TotalScenariosPage.applyButton.value")
	WebElement applyButton;
        String scenarioCheckBoxXpath = "TotalScenariosPage.scenarioCheckBox.value";
	private String projectName;
	private String subProjectName;
	private String scenarioName;
	public TotalScenariosPage (CavissonDriver driver,String testCaseName){
		super(driver);
		setTestCaseName(testCaseName);
	}
	public void setScenarioName(String scenarioName){
		this.scenarioName=scenarioName;
	}
        public String getScenarioName(){
		return this.scenarioName;
	}
	public void executeCreateScenario(){
		getDriver().click( createScenario , getTestCaseName());		
	}
        public void executeDeleteScenario(){
		getDriver().click( deleteScenario, getTestCaseName());		
	}
	public void enterScenarioNameToSearch(){
		getDriver().sendKeys( searchScenarioBox , getScenarioName(), getTestCaseName());
	}
        public void applySearch(){
		getDriver().click( applyButton, getTestCaseName());		
	}
	public void selectScenarioToDelete(){
		getDriver().click( getDriver().getBaseDriver().findElement(By.xpath(String.format(scenarioCheckBoxXpath,getScenarioName()))), getTestCaseName());
	}	
}
