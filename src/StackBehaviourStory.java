package com.cavisson.biswajit; 

import java.util.List;
import org.jbehave.core.Embeddable;
import org.jbehave.core.reporters.Format;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.configuration.MostUsefulConfiguration;
import org.jbehave.core.junit.JUnitStories;
import org.jbehave.core.junit.JUnitStory;
import org.jbehave.core.steps.InjectableStepsFactory;
import org.jbehave.core.steps.InstanceStepsFactory;
import org.jbehave.core.io.LoadFromRelativeFile;
import org.jbehave.core.io.StoryPathResolver;
import org.jbehave.core.model.ExamplesTableFactory;
import org.jbehave.core.parsers.RegexStoryParser;
import java.io.File;
import java.util.Properties;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import org.jbehave.core.embedder.Embedder;
import org.jbehave.core.embedder.EmbedderControls;

public class StackBehaviourStory extends JUnitStories {
    private final static String TMP_TEST_CASE="test_case_name";
    private final static String TEST_CASE="testcase";
    private final static String TEST_CASE_CONF="test_case.conf";
    private final static String TEST_CASE_DIR="testcases";
    private final static String STORY_FILE_NAME="StoryFile";
    private final static String CONTROL_DIR = "controls";
    private final static String CONTROL_STEP_DIR = "steps";
    private final static String MODEL_LIST = "Models.txt";
    private String testCaseName;
    private String testStoryName;
    @Override 
    public Configuration configuration() { System.out.println("Inside the log");
        Properties p = new Properties();
        try{
            if (new File("./"+TMP_TEST_CASE).exists()){
                File proprtiesFile = new File("./"+TMP_TEST_CASE);
                p.load(new FileInputStream(proprtiesFile));
                /*Iterator itr = p.keySet().iterator();
                /String str = "";
                while(itr.hasNext()){
                    str= (String)itr.next();
                    System.out.println("The test property for " + str +  " is " + p.getProperty(str));
                }*/
                if(p.keySet().contains(TEST_CASE)){
                    System.out.println("The test property for " + TEST_CASE +  " is " + p.getProperty(TEST_CASE));    
                    testCaseName=p.getProperty(TEST_CASE);
                }     
		else{
		    System.out.println("testcase name not found in file hence existing");
		    return null;
		}
                System.out.println("./"+ TEST_CASE_DIR + "/" + testCaseName + "/" +TEST_CASE_CONF);
                if(new File("./"+ TEST_CASE_DIR + "/" + testCaseName + "/" +TEST_CASE_CONF).exists()){
                    File proprtiesConfFile = new File("./" + TEST_CASE_DIR + "/" + testCaseName + "/" +TEST_CASE_CONF);
		    p.load(new FileInputStream(proprtiesConfFile));
		    System.out.println("The test property for Story " + STORY_FILE_NAME +  " is " + p.getProperty(STORY_FILE_NAME));
                    testStoryName = p.getProperty(STORY_FILE_NAME); 
                }
            }
            else{
                System.out.println("test_case_name file not found hence returning");
                return null;
            }
 	     return new MostUsefulConfiguration().useStoryPathResolver(new StoryPathResolver() {
			
			@Override
			public String resolve(Class<? extends Embeddable> arg0) {
				return "";
			}
		}).useStoryLoader(new LoadFromRelativeFile(new File("./").toURI().toURL())).useStoryReporterBuilder(
            new StoryReporterBuilder()
            .withDefaultFormats()
            .withFormats(Format.HTML, Format.CONSOLE)
            .withRelativeDirectory("jbehave-report")
).useStoryParser(new RegexStoryParser(new ExamplesTableFactory(new LoadFromRelativeFile(new File("./"+ TEST_CASE_DIR + "/" + testCaseName+ "/").toURI().toURL()), null))).useCompositePaths(getCompositeFilePath()); 
        }catch(Exception e){System.out.println(e.getMessage());}
        return null;
    }

    @Override
    public InjectableStepsFactory stepsFactory() {
        //return new InstanceStepsFactory(configuration(), new ChromeStorySteps());   
        return new InstanceStepsFactory(configuration(), new StackBehaviourSteps());   
    }
    public EmbedderControls createEmbedderControls() {
        return new EmbedderControls()
                .useStoryTimeoutInSecs(600L); //temporarily ensure timeouts are not an issue
    }
    @Override
    public Embedder configuredEmbedder() {
    	// TODO Auto-generated method stub
    	Embedder emb = super.configuredEmbedder();
    	emb.useEmbedderControls(createEmbedderControls());
    	return emb;
    }
    @Override
    protected List<String> storyPaths() {
	// TODO Auto-generated method stub
	ArrayList<String> arr = new ArrayList<String>();
	arr.add("./" + TEST_CASE_DIR + "/" + testCaseName + "/" + testStoryName);
	return arr;
    }
    private List<String> getCompositeFilePath(){
        ArrayList<String> arr = new ArrayList<String>(); 
        try{
	    System.out.println("./" +  CONTROL_DIR + "/" + MODEL_LIST);
            Scanner sc = new Scanner(new File("./" +  CONTROL_DIR + "/" + MODEL_LIST));
	    String stepFile = "";
            while(sc.hasNext()){
	        stepFile = sc.next();
	        if(new File("./" +  CONTROL_DIR + "/" + CONTROL_STEP_DIR + "/" + stepFile + ".step").exists()){
		    arr.add(new String("./" + CONTROL_DIR + "/" + CONTROL_STEP_DIR + "/" + stepFile + ".step"));
                    System.out.println("************** Found A Composite file ************");
	        }
		System.out.println("##### ./" +  CONTROL_DIR + "/" + CONTROL_STEP_DIR + "/" + stepFile + ".step ########");
	    }
	}catch(Exception e){System.out.println("Exception in get StoryPath");}
	System.out.println(arr); 
	return arr;
    }
}
