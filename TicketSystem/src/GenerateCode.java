import java.util.Random;

/**Eamonn Kearney 2020*/
public class GenerateCode {

    //For generating random IDs

    public static String RandomBlankNumber(){

        // 8 digits, each random 0-9
        //Returns as a string so that it can be combined with the blank type code.
        Random r = new Random();
        long numbers = 10000000 + (long)(r.nextFloat() * 89990000);
        return String.valueOf(numbers);

    }

    public static int RandomID(){
        //For use as IDs throughout the system
        // 5 digits, each random 0-9
        Random r = new Random();
        return (int) (10000 + (r.nextFloat() * 89990));
    }

}
