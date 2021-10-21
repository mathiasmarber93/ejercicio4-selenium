package Yelp.stepsdefinitions;

import Yelp.pageobject.MainPage;
import Yelp.steps.UserYelp;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import net.serenitybdd.core.Serenity;
import net.thucydides.core.annotations.Steps;


public class UserStepsDefinitions {

    @Steps(shared = true)
    UserYelp user;

    @Steps(shared = true)
    MainPage mainPage;

    @Given("^user navigates to https://www.yelp.com/$")
    public void userNavigatesToPedidosYa(){
        user.navigatesTo();
        Serenity.takeScreenshot();
    }

    @And("^selects find Restaurants$")
    public void userSearch(){
        user.searchFor();
    }

    @Given("^User search restaurant (.*)$")
    public void userSearchRestaurant(String restaurant){
        user.searchRestaurantForSushi(restaurant);
        Serenity.takeScreenshot();
    }

    @When("^filters by (.*)$")
    public void userFilterByDistance(String distance){
        user.filterByDistance(distance);
        Serenity.takeScreenshot();
    }

    @And("^selects the first search sushi result$")
    public void userSelectFirstResult(){
        user.clickOnFirstResult();
        Serenity.takeScreenshot();
    }

    @Then("^User views sushi restaurant information$")
    public void userShowRestaurantInformation(){
        user.showRestaurantInformation();
        Serenity.takeScreenshot();
    }

}
