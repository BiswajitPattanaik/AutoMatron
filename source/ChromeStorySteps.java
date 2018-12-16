package com.cavisson.biswajit;

import com.automation.cavisson.*;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Named;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
//import org.jbehave.core.annotations.And;
//import org.jbehave.core.junit.Assert;
import java.util.*;
import java.text.SimpleDateFormat;
import java.io.File;
import java.io.FileInputStream;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.remote.*;
import org.openqa.selenium.*;
import java.util.concurrent.TimeUnit;



public class ChromeStorySteps {
    private final String PROPERTY_FILE = "my.properties";
    private final String ROOT_TAG = "Application";
    private String chromeExecutablePath = "";
    private String chromeBinaryPath = "";
    private boolean headlessFlag = false;
    private String baseDir;
    private String etcDir;
    private String imageDir;
    private String logDir;
    private String suiteName = "";
    private String testRunId = "";
    private CavissonDriver driver ;
    private Properties p;

    @Given("a chrometest with testcaseName $testsuitename")
    public void givenStartChromeTest(@Named("testsuitename") String testsuitename) {  
        System.out.println("test case name = "+testsuitename);
        this.suiteName = testsuitename ;
        setUp(); 
	driver = instantiateDriver();
	p = new Properties();
	if (new File("./" + PROPERTY_FILE ).exists()){
                File proprtiesFile = new File("./" + PROPERTY_FILE);
		try{
                	p.load(new FileInputStream(proprtiesFile));
		}catch(Exception e){System.out.println("Exception in Property File Load for XML");}
	}
	
        try{
          //  Thread.sleep(2000);
        }catch(Exception e){}
    }
    @Given("open Netstorm ProductUI for $netstormBoxUI")
    public void openURL(@Named("netstormBoxUI") String netstormBoxUI){
        driver.get( netstormBoxUI , suiteName);
    }
    @Then("sendkeys $text for element $element - $casename - $pagename")
    public void sendKeys(@Named("text") String text , @Named("element") String element, @Named("casename")String caseName,@Named("pagename") String pageName){
	String using = p.getProperty(ROOT_TAG + "." + pageName + "." + element.split("-")[0] + "." + "using");
	System.out.println("Element using is "+ ROOT_TAG + "." + pageName + "." + element.split("-")[0] + "." + "using"); 
	String value = checkString(p.getProperty(ROOT_TAG + "." + pageName + "." + element.split("-")[0] + "." + "value"),element,pageName);  
	driver.sendKeys(locateElement(using,value),text,caseName); 
    }
    @Then("click element $element - $casename - $pagename")
    public void click(@Named("element") String element, @Named("casename")String caseName,@Named("pagename") String pageName){
	System.out.println("Element using is "+ ROOT_TAG + "." + pageName + "." + element.split("-")[0] + "." + "using");
	String using = p.getProperty(ROOT_TAG + "." + pageName + "." + element.split("-")[0] + "." + "using");  			       
	String value = checkString(p.getProperty(ROOT_TAG + "." + pageName + "." + element.split("-")[0] + "." + "value"),element,pageName);  
	driver.click(locateElement(using,value),caseName); 
    }
    @Then("submit element $element - $casename - $pagename")
    public void submit(@Named("element") String element, @Named("casename")String caseName,@Named("pagename") String pageName){
	String using = p.getProperty(ROOT_TAG + "." + pageName + "." + element.split("-")[0] + "." + "using");  			        
	String value = checkString(p.getProperty(ROOT_TAG + "." + pageName + "." + element.split("-")[0] + "." + "value"),element,pageName);  	               driver.submit(locateElement(using,value),caseName);	
    } 
    @Then("assert text $text for element $element - $casename - $pagename")
    public void assertByValue(@Named("text") String text,@Named("element") String element, @Named("casename")String caseName,@Named("pagename") String pageName){
	String using = p.getProperty(ROOT_TAG + "." + pageName + "." + element.split("-")[0] + "." + "using");  			        
	String value = checkString(p.getProperty(ROOT_TAG + "." + pageName + "." + element.split("-")[0] + "." + "value"),element,pageName); 			       driver.assertByValue(locateElement(using,value),text,caseName); 
    } 
    @Then("select $text for element $element - $casename - $pagename")
    public void select(@Named("text") String text,@Named("element") String element, @Named("casename")String caseName,@Named("pagename") String pageName){
	String using = p.getProperty(ROOT_TAG + "." + pageName + "." + element.split("-")[0] + "." + "using");  			        
	String value = checkString(p.getProperty(ROOT_TAG + "." + pageName + "." + element.split("-")[0] + "." + "value"),element,pageName);  	
	driver.assertByValue(locateElement(using,value),text,caseName); 
    }   
    @Then("quit chrometest")
    public void quitTest(){
        driver.quit();
    }

    private By locateElement(String name , String value){
        try{
            switch(name){
               case "id":
               return By.id(value);
               //break;
               case "name":
               return By.name(value);
               //break;
               case "className":
               return By.className(value);
               //break;
               case "xpath":
               return By.xpath(value);
               //break;
               case "cssSelector":
               return By.cssSelector(value);
               //break;
               default:
               return By.id(value);
               //break;
                }
            }catch(Exception e){System.out.println("[ ERROR ] Some error occured with the entered element name and value");e.printStackTrace();}
        return null;
    }
    private String checkString(String value,String string, String pageName){
        String propertyName = string.split("-")[0];
        String parameterProperty = ROOT_TAG + "." + pageName + "." + propertyName + "." + "parameterized";
        if(p.containsKey(parameterProperty)?p.getProperty(parameterProperty).equals("true"):false){
	    System.out.println(" Parameterized value found in the PropertyValue ");
            String pattern = p.getProperty(ROOT_TAG + "." + pageName + "." + propertyName + "." + "pattern");
	    int patternCount = charCount(pattern,'-');
	    int stringPatternCount = charCount(string,'-');
            if(patternCount == (stringPatternCount - 1) ){
	        System.out.println("Pattern Matching success");
	        String stringArr[] = string.split("-");
                System.out.println(stringArr.length);
	        String tokenArr[] = pattern.split("-");
                System.out.println(tokenArr.length);
		String resultString= "";
	       	for(int i=0;i < tokenArr.length ;i++){
		    resultString = evaluate(value, tokenArr[i] , stringArr[i+1]);
		    System.out.println("Inside For Loop Tokenizer " + resultString);
		}
		System.out.println("[ DEBUG ] ResultString "+ resultString + " [DEBUG]");
	        return resultString;       
	    }
	    else{
                System.out.println(" Parameterized value found but not in correct manner");
		return null;
	    }
	    
	}
	else{
	    System.out.println(" No Parameterized value found in scenario hence continueing as usual ");
	    return p.getProperty(ROOT_TAG + "." + pageName + "." + propertyName + "." + "value");
	}
    }


    private String evaluate(String string, String token,String replaceVal){
        try{
	char charToken = token.charAt(1);
	String result = "";
        //System.out.println("Inside the loop " + charToken + " " + token + " " + replaceVal + string);
        result = string.replace(token,replaceVal);
        //System.out.println("Inside Case");
	return result;
	}catch(Exception e){e.printStackTrace();return "";}
    }
    private int charCount(String string,char ch){
        int charLength = 0;
        for (int i=0 ; i < string.length() ; i++){
            if(string.charAt(i) == ch){
                charLength++;
            }
        }
	return charLength;	
    }
    private void setUp(){
        Properties p = new Properties();
	try{
            if (new File("./etc/chromeTest.properties").exists()){
                p.load(new FileInputStream(new File("./etc/chromeTest.properties")));
                Iterator itr = p.keySet().iterator();
                String str = "";
                while(itr.hasNext()){
                    str= (String)itr.next();
                    System.out.println("The test 4j property for " + str +  " is " + p.getProperty(str));
                }
                chromeBinaryPath = p.getProperty("executableBinary");
                baseDir = p.getProperty("baseDir");
                etcDir = baseDir + "/etc"; 
                logDir = baseDir + "/logs"; 
                imageDir = baseDir + "/images";
                chromeExecutablePath = etcDir + "/" + p.getProperty("driverPath"); 
                testRunId = generateTestRunId();
	        makeTestRunDir();
            }
        }catch(Exception e){
            e.printStackTrace();
	}

    }
    private void makeTestRunDir(){
        File logFile = new File(logDir+"/"+testRunId);
        if (!logFile.exists()){
                try{
                        logFile.mkdirs();
                }
                catch(Exception e){
                    e.printStackTrace();
                }
        }
        File imageFile = new File(imageDir+"/"+testRunId);
        if (!imageFile.exists()){
                try{
                        imageFile.mkdirs();
                }
                catch(Exception e){
		        e.printStackTrace();
                }
        }
    } 
    private String generateTestRunId(){
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("ddMMyy_hhmmss");
        String strDate= formatter.format(date);
        return strDate;
    }
    
    private CavissonDriver instantiateDriver(){
        System.setProperty("webdriver.chrome.driver", chromeExecutablePath);
        System.setProperty("webdriver.chrome.logfile", baseDir+"/logs/chrome_debug.log");
        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        capabilities.setCapability("acceptInsecureCerts", true);
        ChromeOptions options = new ChromeOptions();
        options.setBinary(chromeBinaryPath);
	
	//Need To configure out headless flag from test_case.conf

        if(headlessFlag){
            options.addArguments("--headless");
            options.addArguments("--disable-gpu");
            options.addArguments("--disable-logging");
            System.out.println("this is not executed for headless mode ");
        }
        options.addArguments("window-size=1368x720");
        options.addArguments("--start-maximized");
        options.addArguments("--disable-notifications");
        options.addArguments("disable-geolocation");
        options.addArguments("ignore-certificate-errors");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        System.out.println("***********************************"+options+"***********************");
        capabilities.setCapability(ChromeOptions.CAPABILITY, options);
        WebDriver driver = new ChromeDriver(capabilities);
        CavissonDriver cav = null;
        try{
                cav = new CavissonDriver(driver,etcDir,imageDir,logDir,suiteName,testRunId);
        }catch(Exception e){e.printStackTrace();}
        try{
        //Thread.sleep(10000);
        }catch(Exception e){e.printStackTrace();}
        return cav;
    } 
}
