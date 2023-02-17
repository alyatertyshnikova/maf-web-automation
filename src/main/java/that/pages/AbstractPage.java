package that.pages;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import that.composites.AbstractPageComposite;
import that.composites.HeaderMenu;
import that.composites.ShoppingCartProduct;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

import static com.codeborne.selenide.Selenide.page;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class AbstractPage extends AbstractPageComposite {
    HeaderMenu headerMenu;

    @FindBy(className = "cookie-warning-forced-close-button")
    private SelenideElement cookieNotificationButton;

    @FindBy(className = "breadcrumb-wrapper")
    private SelenideElement breadCrumb;

    @FindBy(css = ".search-box-wrapper input")
    private SelenideElement productSearchInput;

    @FindBy(xpath = "//*[contains(text(), 'Go to bag')]")
    private SelenideElement goToBagCartPopUpButton;

    @FindBy(css = "header cx-cart-item")
    private ElementsCollection cartProductsList;

    @FindBy(id = "email")
    private SelenideElement emailInput;

    @FindBy(id = "password")
    private SelenideElement passwordInput;

    @FindBy(xpath = "//button[contains(text(), 'Sign in')]")
    private SelenideElement signInButton;

    public AbstractPage() {
        headerMenu = page(HeaderMenu.class);
    }

    public void clickCookieNotificationCloseButton() {
        cookieNotificationButton.click();
    }

    public Boolean doesTitleContain(String titlePart) {
        new WebDriverWait(getWebDriver(), Duration.ofSeconds(10)).until(ExpectedConditions.titleContains(titlePart));
        return true;
    }

    public Boolean areAllHeaderMenuItemsClickableOrVisible() {
        return headerMenu.areRightHeaderItemsClickable()
                && headerMenu.areTopHeaderItemsClickable()
                && headerMenu.isLogoClickable()
                && headerMenu.isSaleLineVisible();
    }

    public void clickHeaderL1Category(String categoryName) {
        headerMenu.clickL1MenuCategory(categoryName);
    }

    public void clickHeaderL2Category(String l1CategoryName, String l2CategoryName) {
        headerMenu.hoverL1MenuCategory(l1CategoryName);
        headerMenu.clickL2MenuCategory(l2CategoryName);
    }

    // TODO: ???
    public void clickHeaderSandalsL3Category() {
        headerMenu.hoverL1MenuCategory("WOMEN");
        headerMenu.hoverL2MenuCategory("SHOES");
        headerMenu.clickSandalsL3MenuCategory();
    }

    public String getBreadCrumbText() {
        return breadCrumb.getText();
    }

    public void searchProductFromHeader(String searchedProductName) {
        headerMenu.clickSearchButton();
        productSearchInput.sendKeys(searchedProductName, Keys.ENTER);
    }

    public List<ShoppingCartProduct> getCartPopUpProductsFromHeader() {
        return cartProductsList
                .shouldHave(CollectionCondition.sizeGreaterThan(0))
                .stream()
                .map(ShoppingCartProduct::new)
                .collect(Collectors.toList());
    }

    public void clickGoToBagHeaderCartPopUpButton() {
        headerMenu.hoverCartButton();
        goToBagCartPopUpButton.click();
    }

    public void clickHeaderWishlistButton() {
        headerMenu.clickWishlistButton();
    }

    public void login(String email, String password){
        headerMenu.hoverMyAccountButton();
        headerMenu.clickLoginButton();

        emailInput.sendKeys(email);
        passwordInput.sendKeys(password);
        signInButton.click();
    }

    public void goToAccountPage(){
        headerMenu.hoverMyAccountButton();
        headerMenu.clickUserDetailsButton();
    }
}
