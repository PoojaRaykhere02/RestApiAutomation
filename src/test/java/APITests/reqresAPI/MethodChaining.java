package APITests.reqresAPI;

public class MethodChaining {
    public static void main(String[] args){
//        MethodChaining methodChaining= new MethodChaining();
//        methodChaining.a1().a2().a3();// return value of a1 a2 a3 is the object of same class that why we can use a1().a2().a3() directly
//        methodChaining.a2();
//        methodChaining.a3(); //

        a1().
                a2().
                a3();

    }

    public static MethodChaining a1(){//conver this method to static and instead of this return the object of "class" using new keyword
    System.out.println("this is method a1");
//    return this; //.this represents this class
        return new MethodChaining();
    }

    public static MethodChaining a2(){
        System.out.println("this is method a2");
//        return this;
        return new MethodChaining();
    }

    public static MethodChaining a3(){
        System.out.println("this is method a3");
//        return this;
        return new MethodChaining();
    }

}
