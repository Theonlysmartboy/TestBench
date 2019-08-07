package testbench;

import java.util.Random;

/**
 *
 * @author TheOnlySmartBoy
 */
public class TestBench {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        for(int i=0; i<20;i++){
            
        }
        
    }

    public static String passwordGen() {
        String pass = "N.2";
        final String alphabet = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ@#$%^&*_()/|!.?abcdefghijklmnopqrstuvwxyz";
        final int N = alphabet.length();

        Random r = new Random();

        for (int i = 0; i < 10; i++) {
            pass += alphabet.charAt(r.nextInt(N));
        }
        System.out.println("");   
        return pass;
    }

}
