package utilities;

import com.github.javafaker.Faker;

import java.util.Random;

public class Utils {

    public static Faker faker = new Faker();

    public static final String BASE_URL = "https://jsonplaceholder.typicode.com";
    public static final String responseError = "Bad Response code!";

    public static String randomEmail(){
        Random rand = new Random();
        int length = rand.nextInt(11-5)+5;
        String CHARSEQ = "abcdefghijklmnopqrstuvwxyz-._1234567890";
        StringBuilder name = new StringBuilder();

        for(int i=0; i<length; i++){
            Character r = CHARSEQ.charAt(rand.nextInt(39));
            if(i==0 || i==length-1){
                while(r == '-' || r == '.' || r == '_'){
                    r = CHARSEQ.charAt(rand.nextInt(39));
                }
            }
            name.append(r);
        }
        return name + "@mail.com";
    }

    public static String randomFullName(){
        return faker.name().firstName() + " " + faker.name().lastName();
    }

    public static String randomUsername(){
        return faker.name().username();
    }

    public static String randomPhoneNumber(){
        return faker.phoneNumber().cellPhone();
    }

    public static String badResponseAPI(String badresponse){ return "Bad response API json element " + badresponse;}
}
