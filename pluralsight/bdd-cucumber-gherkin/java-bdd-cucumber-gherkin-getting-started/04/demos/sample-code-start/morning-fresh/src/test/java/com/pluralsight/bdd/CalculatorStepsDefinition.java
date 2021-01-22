package com.pluralsight.bdd;

import com.pluralsight.bdd.calculator.Calculator;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class CalculatorStepsDefinition {

    private Calculator calculator;
    private Integer result;

    @Given("I have a calculator")
    public void i_have_a_calculator() {
        calculator = new Calculator();
    }

    @When("I add {int} and {int}")
    public void i_add_and(Integer a, Integer b) {
        result = this.calculator.add(a, b);
    }

    @Then("I should get {int}")
    public void i_should_get(Integer expectedResult) {
        assertThat(this.result).isEqualTo(expectedResult);
    }
}
