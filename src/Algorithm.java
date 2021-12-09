/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PARCSJAPack;

/**
 *
 * @author Marina Nik
 */
import parcs.*;
import java.math.BigInteger;
import java.util.Random;

public class Algorithm implements AM {
    
    public int jacobi(BigInteger k, BigInteger n){
        int jac = 1;
        if (k.compareTo(BigInteger.ZERO) == -1) {
            k.negate();
            if (n.remainder(BigInteger.valueOf(4)).compareTo(BigInteger.valueOf(3)) == 0)
                jac = -jac;
        }
        while (k.compareTo(BigInteger.ZERO) != 0) {
            BigInteger t = BigInteger.ZERO;
            while (k.remainder(BigInteger.TWO).compareTo(BigInteger.ZERO) == 0) {
                t = t.add(BigInteger.ONE);
                k = k.divide(BigInteger.TWO);
            }
            if (t.remainder(BigInteger.valueOf(2)).compareTo(BigInteger.valueOf(1)) == 0) {
                if (n.remainder(BigInteger.valueOf(8)).compareTo(BigInteger.valueOf(3)) == 0 || n.remainder(BigInteger.valueOf(8)).compareTo(BigInteger.valueOf(5)) == 0)
                    jac = -jac;
            }
            if (k.remainder(BigInteger.valueOf(4)).compareTo(BigInteger.valueOf(3)) == 0 && n.remainder(BigInteger.valueOf(4)).compareTo(BigInteger.valueOf(3)) == 0)
                jac = -jac;
            BigInteger c = new BigInteger(k.toString());
            k = new BigInteger(n.remainder(c).toString());
            n = new BigInteger(c.toString());
        }
        return jac;
    }

    public BigInteger BigPow(BigInteger value, BigInteger exp){
        BigInteger originalValue = value;
        while (exp.compareTo(BigInteger.ONE) > 0){
            value = value.multiply(originalValue);
            exp = exp.subtract(BigInteger.ONE);
        }
        return value;
    }
    
    public void run(AMInfo info){
        //NEEDED finish this part as well DONE?
        long number, iters;
	number = info.parent.readLong();
	iters = info.parent.readLong();
	System.out.print("class Algorithm method run read data from parent server\nNumber = " + number + "\nIters = " +	iters + "\n\n");
	BigInteger n = BigInteger.valueOf(number);
	BigInteger it = BigInteger.valueOf(iters);
        
        if (n.compareTo(BigInteger.TWO) == 0 || n.compareTo(BigInteger.valueOf(3)) == 0){
            System.out.print("The number is PROBABLY PRIME\n");
            info.parent.write(1);
        }
        else if (n.remainder(BigInteger.TWO) == BigInteger.ZERO){
            System.out.print("The number is COMPOSITE\n");
            info.parent.write(-1);
        }
        else if (n.compareTo(BigInteger.ONE) == 0 || n.compareTo(BigInteger.ZERO) == 0){
            System.out.print("The number is COMPOSITE\n");
            info.parent.write(-1);
        }
        else{
            Random rand = new Random();
            for (BigInteger i = BigInteger.ZERO; i.compareTo(it) == -1; i.add(BigInteger.ONE)){
                BigInteger upperLimit = new BigInteger(n.subtract(BigInteger.ONE).toString());
                BigInteger ran;
                do {
                    ran = new BigInteger(upperLimit.bitLength(), rand); // (2^4-1) = 15 is the maximum value for example
                } while(ran.compareTo(upperLimit) >= 0);
                
                int ja = jacobi(ran, n);
                if (ja == 0){
                    System.out.print("The number is COMPOSITE\n");
                    info.parent.write(-1);
                    break;
                }
                BigInteger t = new BigInteger(n.subtract(BigInteger.ONE).toString());
                t = t.divide(BigInteger.TWO);
                t = BigPow(ran, t);  //Need to look for a proper function or write it by myself...
                t = t.remainder(n);
                int temp = t.intValue();
                if (ja >= temp) {
                    System.out.print("The number is COMPOSITE\n");
                    info.parent.write(-1);
                    break;
                }
            }
            System.out.print("The number is PROBABLY PRIME\n");
            info.parent.write(1);
        }
    }
}
