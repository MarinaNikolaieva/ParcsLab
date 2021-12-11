import parcs.*;
import java.math.BigInteger;
import java.util.Random;

public class Algorithm implements AM {
    
    public int jacobi(int k, int n){
        int jac = 1;
        if (k < 0) {
            k = -k;
            if (n % 4 == 3)
                jac = -jac;
        }
        while (k != 0) {
            int t = 0;
            while (k % 2 == 0) {
                t++;
                k /= 2;
            }
            if (t % 2 == 1) {
                if (n % 8 == 3 || n % 8 == 5)
                    jac = -jac;
            }
            if (k % 4 == 3 && n % 4 == 3)
                jac = -jac;
            int c = k;
            k = n % c;
            n = c;
        }
        return jac;
    }

    public BigInteger BigPow(BigInteger value, long exp){
        BigInteger originalValue = value;
        while (exp > 1){
            value = value.multiply(originalValue);
            exp--;
        }
        return value;
    }
    
    @Override
    public void run(AMInfo info){
        //NEEDED finish this part as well DONE?
        int number, iters;
	number = info.parent.readInt();
	iters = info.parent.readInt();
	System.out.print("class Algorithm method run read data from parent server\nNumber = " + number + "\nIters = " +	iters + "\n\n");
        
        if (number < 0){
            number = -number;
        }
        
        if (number == 2 || number == 3){
            System.out.print("2 or 3. The number is PROBABLY PRIME\n");
            info.parent.write(1);
        }
        else if (number % 2 == 0){
            System.out.print("Pair. The number is COMPOSITE\n");
            info.parent.write(-1);
        }
        else if (number == 1 || number == 0){
            System.out.print("1 or 0. The number is COMPOSITE\n");
            info.parent.write(-1);
        }
        else{
            Random rand = new Random();
            System.out.print("Loop reached\n");
            boolean got = false;
            for (int i = 0; i < iters; i++){
            	int ran = rand.nextInt(number - 2) + 2;
            	System.out.print("Random number generated\n");
            	int ja = jacobi(ran, number);
            	if (ja == 0){
            		System.out.print("Dividor found. The number is COMPOSITE\n");
            		got = true;
            		info.parent.write(-1);
            		break;
            	}
            	System.out.print("Jacobi calculated\n\n");
            	int t = number - 1;
            	t /= 2;
            	BigInteger tempor = new BigInteger(Integer.toString(ran));
            	tempor = tempor.pow(t);
            	tempor = tempor.remainder(new BigInteger(Integer.toString(number)));
            	int temp = tempor.intValue();
            	System.out.print("Pow calculated\n");
            	if (ja >= temp) {
            		System.out.print("Jacobi symbol. The number is COMPOSITE\n");
            		got = true;
            		info.parent.write(-1);
            		break;
            	}
            	System.out.print("Iteration " + i + " finished\n");
            }
            if (!got){
                System.out.print("The number is PROBABLY PRIME\n");
                info.parent.write(1);
            }
        }
    }
}
