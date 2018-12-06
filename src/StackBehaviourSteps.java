package com.cavisson.biswajit;

import java.util.Stack;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Named;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
//import org.jbehave.core.annotations.And;
//import org.jbehave.core.junit.Assert;

public class StackBehaviourSteps {
	public Stack stack;
    @Given("I have an empty stack")
    public void givenIHaveAnEmptyStack() {  stack= new Stack();System.out.println("Stack created"); }

    @When("I push an item $item")
    public void whenIPushAnItem(@Named("item") String item) { stack.push(item); System.out.println("Pushed"+item);}

    @Then("I should count $expected $name $pass")
    public void thenIShouldCount(@Named("expected") int expected,@Named("name") String name,@Named("pass") String pass) {
        int actual = stack.size();
        System.out.println("count"+actual);
        System.out.println(" name " + name + " Pass " + pass);
        if (actual != expected) 
            throw new RuntimeException("expected:"+expected+";actual:"+actual);
    }
    @Then("say Hello")
    public void sayHello() {
    	System.out.println("*********** SAYING HELLO**************");
    }
    @Then("say Bye")
    public void sayBye() {
    	System.out.println("*********** SAYING BYE**************");
    }

}
