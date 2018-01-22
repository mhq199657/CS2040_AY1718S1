/**
 * Name         :   Ma Hongqiang
 * Matric No.   :   A0136093H
 * PLab Acct.   :   
 */

import java.util.*;

class Modulo {
    private int value;      //To store the value of the number, must be non-negative and less than the modulus
    private int modulus;    //To store the modulus of the Modulo
    
    // Add more attributes, constructor and methods here if required
    
    /**
     * Constructor class for Modulo
     * Pre-condition: Modulus cannot be zero and value cannot be negative
     */
    Modulo (int value, int modulus) {
        if (modulus == 0) {
            throw new IllegalArgumentException("Modulus cannot be zero.");
        }
        int temp = value %modulus;
        this.value = temp<0? temp + modulus : temp;
        this.modulus = modulus;
    }
    
    /**
     * Returns the value of the Modulo value
     */
    public int getValue(){
        return value;
    }
    /**
     *  Override toString
     */
    public String toString(){
        return value+"";
    }
    /**
     * Returns the value of the Modulus
     */
    public int getModulus(){
        return modulus;
    }
    
    /**
     * Adds two Modulo values and returns a new Modulo value as a result
     * Pre-condition: Modulus must be the same
     */
    public Modulo plus(Modulo other) {
        if (modulus != other.modulus) {
            throw new IllegalArgumentException("Modulus do not match.");
        }
        return new Modulo(this.getValue()+other.getValue(), this.modulus);    
    }
    
    /**
     * Subtracts two Modulo values and returns a new Modulo value as a result
     * Pre-condition: Modulus must be the same
     */
    public Modulo minus(Modulo other) {
        if (modulus != other.modulus) {
            throw new IllegalArgumentException("Modulus do not match.");
        }
        //Write code here
        
        return new Modulo(this.getValue()-other.getValue(), this.modulus);    
    }
    
    /**
     * Multiples two Modulo values and returns a new Modulo value as a result
     * Pre-condition: Modulus must be the same
     */
    public Modulo times(Modulo other) {
        if (modulus != other.modulus) {
            throw new IllegalArgumentException("Modulus do not match.");
        }
        //Write code here
        
        return new Modulo(this.getValue()*other.getValue(), this.modulus);      
    }
    
    
    /**
     * Divides two Modulo values and returns a new Modulo value as a result
     * Pre-condition: Modulus must be the same and division must be valid
     */
    public Modulo divide(Modulo other) {
        if (modulus != other.modulus) {
            throw new IllegalArgumentException("Modulus do not match.");
        }
        //Write code here
        
        return new Modulo(this.getValue()*other.findInverse().getValue(), this.modulus);      
    }
    
    /**
     * Find modular inverse of the Modulo value
     * Pre-condition: Inverse must exist
     */
    public Modulo findInverse() {
        int xx = 0;
        int x = 1;
        int yy = 1;
        int y = 0;
        int a = this.value;
        int b = this.modulus;
        while (b > 0) {
            int q = a / b;
            int t = b; 
            b = a % b; 
            a = t;
            t = xx; 
            xx = x - q * xx; 
            x = t;
            t = yy;
            yy = y - q * yy; 
            y = t;
        }
        if (a > 1) {
            throw new ArithmeticException("Inverse does not exist.");
        } else {
            return new Modulo((x + this.modulus) % this.modulus, this.modulus);
        }
    }
}

public class ModuloTester {
    private static final String PLUS = "PLUS";
    private static final String MINUS = "MINUS";
    private static final String TIMES = "TIMES";
    private static final String DIVIDE = "DIVIDE";
    public static void main(String[] args) {
        //Write code here
        final Scanner sc = new Scanner(System.in);
        int operand1 = -1;
        int operand2 = -1;
        int modulus = -1;
        String operator = "";
        while(sc.hasNext()){
            operand1 = sc.nextInt();
            operator = sc.next().trim();
            operand2 = sc.nextInt();
            modulus = sc.nextInt();
            Modulo m1 = new Modulo(operand1, modulus);
            Modulo m2 = new Modulo(operand2, modulus);
            switch(operator){
                case PLUS:
                    System.out.println(m1.plus(m2));
                break;
                case MINUS:
                    System.out.println(m1.minus(m2));
                break;
                case TIMES:
                    System.out.println(m1.times(m2));
                break;
                case DIVIDE:
                    System.out.println(m1.divide(m2));
                break;
            }
        }
    }
}