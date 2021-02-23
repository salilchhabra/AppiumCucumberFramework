package stepdef;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import pages.LoginPage;
import pages.ProductDetailsPage;
import pages.ProductsPage;

public class ProductStepDef {

    @Given("^I'm logged in$")
    public void iMLoggedIn() throws InterruptedException {
//        new LoginPage().login("standard_user", "secret_sauce");
        System.out.println("already logged in");
    }

    @Then("^the product is listed with title \"([^\"]*)\" and price \"([^\"]*)\"$")
    public void theProductIsListedWithTitleAndPrice(String title, String price) throws Exception {
        Boolean titleCheck = new ProductsPage().getProductTitle(title).equalsIgnoreCase(title);
        Boolean priceCheck = new ProductsPage().getProductPrice(title, price).equalsIgnoreCase(price);
        Assert.assertTrue("titleCheck = " + titleCheck + ", priceCheck = " + priceCheck,
                titleCheck & priceCheck);
    }

    @When("^I click product title \"([^\"]*)\"$")
    public void iClickProductTitle(String title) throws Exception {
        new ProductsPage().pressProductTitle(title);
    }

    @Then("^I should be on product details page with title \"([^\"]*)\", price \"([^\"]*)\" and description \"([^\"]*)\"$")
    public void iShouldBeOnProductDetailsPageWithTitlePriceAndDescription(String title, String price, String description) throws Exception {
        ProductDetailsPage productDetailsPage = new ProductDetailsPage();
        boolean titleCheck = productDetailsPage.getTitle().equalsIgnoreCase(title);
        boolean descCheck = productDetailsPage.getDesc(description).equalsIgnoreCase(description);
        boolean priceCheck = productDetailsPage.getPrice().equalsIgnoreCase(price);
        Assert.assertTrue("titleCheck = " + titleCheck + ", descCheck = " + descCheck + ", priceCheck = " + priceCheck,
                titleCheck & descCheck & priceCheck);
    }
}
