package generic;

import org.testng.annotations.DataProvider;

import static generic.utility.randomNumber;

public class DataProviderClass {
    @DataProvider(name = "BooksData")
    public static  Object[][] getBooks(){
        return new Object[][] {{"book4145",randomNumber()},
                {"book4245", randomNumber()}, {"book4356", randomNumber()} };
    }

}
