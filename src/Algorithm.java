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
    
    public int jacobi(long k, long n){
        int jac = 1;
        if (k < 0L) {
            k = -k;
            if (n % 4L == 3L)
                jac = -jac;
        }
        while (k > 0L) {
            long t = 0L;
            while (k % 2L == 0L) {
                t += 1L;
                k /= 2L;
            }
            if (t % 2L == 1L) {
                if (n % 8L == 3L || n % 8L == 5L)
                    jac = -jac;
            }
            if (k % 4L == 3L && n % 4L == 3L)
                jac = -jac;
            long c = k;
            k = n % c;
            n = c;
        }
        return jac;
    }

    public BigInteger BigPow(BigInteger value, long exp){
	System.out.print("Reached BigPow");
        BigInteger originalValue = value;
        while (exp > 1L){
            value = value.multiply(originalValue);
            exp -= 1L;
        }
	System.out.print("Finished BigPow");
        return value;
    }
    
    @Override
    public void run(AMInfo info){
        //NEEDED finish this part as well DONE?
        long number, iters;
	number = info.parent.readLong();
	iters = info.parent.readLong();
	System.out.print("class Algorithm method run read data from parent server\nNumber = " + number + "\nIters = " +	iters + "\n\n");
        
        if (number < 0L){
            number = -number;
        }
        
        if (number == 2L || number == 3L){
            System.out.print("The number is PROBABLY PRIME\n");
            info.parent.write(1);
        }
        else if (number % 2L == 0L){
            System.out.print("The number is COMPOSITE\n");
            info.parent.write(-1);
        }
        else if (number == 1L || number == 0L){
            System.out.print("The number is COMPOSITE\n");
            info.parent.write(-1);
        }
        else{
            Random rand = new Random();
	    System.out.prinf("Loop reached\n");
            for (int i = 0; i < iters; i++){
                long upperLimit = number - 1L;
                long ran = 2L + ((long)(rand.nextDouble()*((upperLimit - 2L))));
		System.out.prinf("Random number generated\n");
                int ja = jacobi(ran, number);
                if (ja == 0){
                    System.out.print("The number is COMPOSITE\n");
                    info.parent.write(-1);
                    break;
                }
		System.out.prinf("Jacobi calculated\n\n");
                long t = number - 1L;
                t /= 2L;
                BigInteger tempor = new BigInteger(Long.toString(ran));
                tempor = BigPow(tempor, t);
                tempor = tempor.remainder(new BigInteger(Long.toString(number)));
                int temp = tempor.intValue();
		System.out.prinf("Pow calculated\n");
                if (ja >= temp) {
                    System.out.print("The number is COMPOSITE\n");
                    info.parent.write(-1);
                    break;
                }
		System.out.prinf("Iteration " + i + " finished\n");
            }
            System.out.print("The number is PROBABLY PRIME\n");
            info.parent.write(1);
        }
    }
}
