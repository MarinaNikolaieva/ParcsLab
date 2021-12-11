import parcs.*;
import java.math.BigInteger;
import java.util.Random;

public class Algorithm implements AM {
    
    public int jacobi(int k, int n){  //HERE'S the error!
    	int ka = new Integer(Integer.toString(k));
    	int ne = new Integer(Integer.toString(n));
        int jac = 1;
        if (ka < 0) {
            ka = -ka;
            if (ne % 4 == 3)
                jac = -jac;
        }
        while (ka != 0) {
            int t = 0;
            while (ka % 2 == 0) {
                t++;
                ka /= 2;
            }
            if (t % 2 == 1) {
                if (ne % 8 == 3 || ne % 8 == 5)
                    jac = -jac;
            }
            if (ka % 4 == 3 && ne % 4 == 3)
                jac = -jac;
            int c = new Integer(Integer.toString(ka));
            int temp = new Integer(Integer.toString(ne));
            ka = new Integer(Integer.toString(temp % c));
            ne = new Integer(Integer.toString(c));
        }
        return jac;
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
            	if (temp == number - 1)
            		temp = -1;
            	System.out.print("Pow calculated\n");
            	if (ja != temp) {
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
