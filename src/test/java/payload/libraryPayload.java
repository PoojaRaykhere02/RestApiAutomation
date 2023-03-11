package payload;

public class libraryPayload {

    public static String addBook(String isbn, String aisle){
        return "{\n" +
                "\n" +
                "\"name\":\"Learn Appium Automation with Java\",\n" +
                "\"isbn\":\""+isbn+"\",\n" +
                "\"aisle\":\""+aisle+"\",\n" +
                "\"author\":\"John fo\"\n" +
                "}\n";
    }
}
