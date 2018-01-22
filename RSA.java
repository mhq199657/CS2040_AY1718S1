/**
 * Name         :
 * Matric No.   :
 * PLab Acct.   :
 */

import java.util.*;
import java.math.BigInteger;

//Replace this with your solution from the previous Take Home Lab
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
    public static Modulo exp(Modulo m, int power){
        if(power == 1){
            return m;
        }
        if(power%2==0){
            return exp(m.times(m), power/2);
        }else{
            return exp(m.times(m), power/2).times(m);
        }
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



class RSAUser {
    private String name;
    private int N;
    private int privateKey; 
    private int publicKey;
    
    // Add more attributes, constructor and methods here if required
    
    /**
     * Constructor class for RSAUser
     * Pre-condition: prime1 and prime2 are positive primes
     */
    RSAUser (String name, int prime1, int prime2, int publicKey) {
        //Write code here
        this.name = name;
        this.N = prime1*prime2;
        this.publicKey = publicKey;
        this.privateKey = new Modulo(publicKey, (prime1-1)*(prime2-1)).findInverse().getValue();
    }
    
    /**
     * Returns name of the RSAUser
     */
     public String getName(){
         return name;
     }
    
    /**
     * Encrypt an array of integers using RSA
     * Pre-condition: Each integer is non-negative and less than the modulus
     */
    public String encrypt(String message) {
        //Write code here
        int[] intArray = StringToIntArray(message);
        for(int i = 0; i<intArray.length; i++){
            int K = intArray[i];
            int result = Modulo.exp(new Modulo(K, N), publicKey ).getValue();
            intArray[i]=result;
        }
        return IntArrayToString(intArray);
    }
    
    /**
     * Decrypt an array of integers using RSA
     * Pre-condition: Each integer is non-negative and less than the modulus
     */
    public String decrypt(String message) {
        //Write code here
        int[] intArray = StringToIntArray(message);
        for(int i = 0; i<intArray.length; i++){
            int K = intArray[i];
            int result = Modulo.exp(new Modulo(K,N), privateKey).getValue();
            intArray[i]=result;
        }
        return IntArrayToString(intArray);
    }
    
    /**
     * Convert a string to an integer array for RSA
     * Pre-condition: Message only contains capital letters and spaces
     */
    private int[] StringToIntArray(String message) {
        BigInteger value = BigInteger.valueOf(0);
        for(int i = 0; i < message.length(); i++){
            int ch = 0;
            if(message.charAt(i) != ' '){
                ch = (int)message.charAt(i) - 64;
            }
            value = value.multiply(BigInteger.valueOf(27)).add(BigInteger.valueOf(ch));
        }
        ArrayList<Integer> array = new ArrayList<Integer>();
        while(value.compareTo(BigInteger.valueOf(0)) > 0){
            array.add(value.mod(BigInteger.valueOf(N)).intValue());
            value = value.divide(BigInteger.valueOf(N));
        }
        int[] intArray = new int[array.size()];
        for(int i = 0; i < array.size(); i++){
            intArray[i] = array.get(i);
        }
        return intArray;
    }
    
    /**
     * Convert an integer array back to a string
     * Pre-condition: Message contains non-negative numbers less than the modulus
     */
    private String IntArrayToString(int[] array) {
        BigInteger value = BigInteger.valueOf(0);
        for(int i = array.length - 1; i >= 0; i--){
            value = value.multiply(BigInteger.valueOf(N)).add(BigInteger.valueOf(array[i]));
        }
        String message = "";
        while(value.compareTo(BigInteger.valueOf(0)) > 0){
            int ch = value.mod(BigInteger.valueOf(27)).intValue();
            if(ch == 0){
                message = ' ' + message;
            } else{
                message = (char)(ch + 64) + message;
            }
            value = value.divide(BigInteger.valueOf(27));
        }
        return message;
    }
}

public class RSA {
    private static final String ENCRYPT = "ENCRYPT";
    private static final String DECRYPT = "DECRYPT";
    private static HashMap<String, RSAUser> userMap = new HashMap<String, RSAUser>();
    public static void main(String[] args) {
        //Write code here
        final Scanner sc = new Scanner(System.in);
        int P = sc.nextInt();
        sc.nextLine();
        String name = "";
        int prime1 = -1;
        int prime2 = -1;
        int E = -1;
        RSAUser newUser = null;
        while(P>0){
            name = sc.next().trim();
            prime1 = sc.nextInt();
            prime2 = sc.nextInt();
            E = sc.nextInt();
            newUser = new RSAUser(name, prime1, prime2, E);
            userMap.put(name, newUser);
            P--;
        }
        int Q = sc.nextInt();
        sc.nextLine();
        RSAUser currUser = null;
        String mode = "";
        while(Q>0){
            currUser = userMap.get(sc.next().trim());
            mode = sc.next().trim();
            sc.nextLine();
            if(mode.equals(DECRYPT)){
                System.out.println(currUser.decrypt(sc.nextLine().trim()));
            }else{
                System.out.println(currUser.encrypt(sc.nextLine().trim()));
            }
            Q--;
        }

    }
}