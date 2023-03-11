package APITests.coursesAPI;

import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.Test;
import payload.coursesPayload;

public class coursesApiTest {

    int size= 0;

    @Test
    public void getCoursesCount(){
        JsonPath jsonPath = new JsonPath(coursesPayload.coursesPayloadData());
         size= jsonPath.getInt("courses.size()");
        System.out.println("cousesSize= "+ size);
    }

    @Test
    public void getPurchaseAmount(){
        JsonPath jsonPath =new JsonPath(coursesPayload.coursesPayloadData());
        int purchaseAmount= jsonPath.getInt("dashboard.purchaseAmount");
        System.out.println("purchaseAmount= "+ purchaseAmount);
    }

    @Test
    public void getTitleOfFirstCourse(){
        JsonPath jsonPath =new JsonPath(coursesPayload.coursesPayloadData());
        String titleOfFirstCourse= jsonPath.getString("courses[0].title");
        System.out.println("titleOfFirstCourse= "+ titleOfFirstCourse);
    }

    @Test(dependsOnMethods = "getCoursesCount")
    public void getTitleAndRespectivePricesOfAllCourses(){
        JsonPath jsonPath =new JsonPath(coursesPayload.coursesPayloadData());
        for(int i =0;i<= size-1;i++){
            String titleOfAllCourse= jsonPath.getString("courses["+i+"].title");
            System.out.println("titleOfCourse= "+ titleOfAllCourse );
            int priceOfAllCourse= jsonPath.getInt("courses["+i+"].price");
            System.out.println("priceOfCourse= "+ priceOfAllCourse);
            System.out.println("-----------------------------------");
        }
    }

//Print no of copies sold by RPA
    @Test(dependsOnMethods = "getCoursesCount")
    public void getCopiesSoldByRPA(){
        JsonPath jsonPath =new JsonPath(coursesPayload.coursesPayloadData());
        for(int i =0;i<= size;i++){
            String titleOfAllCourse= jsonPath.getString("courses["+i+"].title");
            if(titleOfAllCourse.equalsIgnoreCase("RPA")){
                int RPACourseCopies= jsonPath.getInt("courses["+i+"].copies");
                System.out.println("RPACourseCopies= "+ RPACourseCopies);
                break;
            }
        }
    }

    //Verify if sum of all courses prices matches with purchase amount
    @Test(dependsOnMethods = "getCoursesCount")
    public void sumOfCoursesPriceMatchWithPurchaseAmount(){
        int sum=0;
        JsonPath jsonPath =new JsonPath(coursesPayload.coursesPayloadData());
        for(int i =0;i<= size-1;i++){
            int prices= jsonPath.getInt("courses["+i+"].price");
            int copies= jsonPath.getInt("courses["+i+"].copies");
            sum = sum + (prices * copies);
            }
        int purchaseAmount = jsonPath.getInt("dashboard.purchaseAmount");
        Assert.assertEquals(sum, purchaseAmount);
        }

}
