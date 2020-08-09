package simulator;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in) ;
        System.out.println("Enter r,s,n,m,k,t : ");
        int r = scanner.nextInt() ;
        int s = scanner.nextInt() ;
        int n = scanner.nextInt() ;
        int m = scanner.nextInt() ;
        int k = scanner.nextInt() ;
        int t = scanner.nextInt() ;
        Simulator simulator = new Simulator(r,s,t,n,m,k);
        simulator.start();
    }


}
