package jungle;

import animal.Animal;
import simulator.Simulator;

import java.util.ArrayList;

public class Jungle {

    int n ;
    int m ;
    int k ;
    int r ;
    int s ;
    Cell[][] jungle ;

    public Jungle(int n, int m, int k,int r , int s) {
        this.n=n;
        this.m=m;
        this.k=k;
        this.r=r;
        this.s=s;
        initialize();
    }

    void initialize(){
        jungle = new Cell[n][m] ;
        for(int i=0 ; i<n ; i++)
            for(int j=0 ; j<m ; j++){
                jungle[i][j] = new Cell(r,i,j,k);
            }
    }


    public void place(ArrayList<Animal>[] animals){
        for(int k=0 ; k<r ; k++)
            for(int t=0 ; t<s ; t++){
                Animal animal = animals[k].get(t) ;
                int i = calculateI(animal,n,r);
                int j = calculateJ(animal,m,s);
                animal.setCoordinates(i,j);
                jungle[i][j].add(animal);
            }
    }


    public boolean doMovement(Animal animal,Cell targetCell){
        int type = animal.getType() ;
        synchronized (targetCell) {
            if(type + targetCell.getCurrentWeight()<=k && targetCell.onlyHasType(type)){
                Cell currentCell = getCell(animal.getI(), animal.getJ());
                currentCell.remove(animal);
                targetCell.add(animal);
                Simulator.logMovement(animal.getType(),currentCell,targetCell);
                return true;
            }
        }
        return false ;
    }

    public void balanceCapacity(){
        for(int i=0 ; i<n ; i++)
            for(int j=0;j<m;j++)
                jungle[i][j].balanceCapacity();
    }

    public void eatAnimals(){
        for(int i=0 ; i<n ; i++)
            for(int j=0;j<m;j++){
                for(int k=0 ; k<r ; k++){
                    int x=jungle[i][j].getCountOfType(k+1);
                    int xy = x*(k+1) ;
                    if(shouldBeKilled(k+1,i,j,xy))
                        jungle[i][j].removeAll(k+1) ;

                }
            }

    }

    public boolean shouldBeKilled(int type, int i,int j , int xy){
        for(int h=0 ; h<r ; h++){
            if(h != type-1){
                int xPrime=getCountOfSpecificTypeIn(h + 1, getNeighborCells(i, j, n, m));
                if(type>(r/2) && h<type-1 )
                    xPrime=getCountOfSpecificTypeIn(h + 1, getClosestNeighborCells(i, j, n, m));

                int xyPrime=xPrime*(h + 1);
                if(xy<xyPrime)
                    return true;
            }
        }
        return false ;
    }

    public int getCountOfSpecificTypeIn(int type ,ArrayList<int[]> cellsIndex){
        int count = 0 ;
        for(int i=0 ; i<cellsIndex.size() ; i++){
            Cell cell = getCell(cellsIndex.get(i)[0],cellsIndex.get(i)[1]);
            count += cell.getCountOfType(type) ;
        }
        return count ;
    }

    static int calculateI(Animal animal,int n ,int r){
        int temp = n/r ;
        int i = animal.getType()*temp ;
        return i-1 ;
    }

    static int calculateJ(Animal animal , int m ,int s){
        int temp = m/s ;
        int j = animal.getIndex()*temp ;
        return j-1 ;
    }

    public void print(){
        for(int i=0 ; i<n ; i++){
            for(int k=0 ; k<r ; k++){
                for(int j=0 ; j<m ; j++){
                    Cell cell = jungle[i][j] ;
                    System.out.print(String.format("%s%-4s%3s",(k+1)+":",cell.getCountOfType(k+1),"|"));
                }
                System.out.println();
            }
            for(int h=0 ; h<9*m ; h++)
                System.out.print("_");
            System.out.println();
        }
    }

    public Cell getCell(int i , int j){
        return jungle[i][j] ;
    }

    public static ArrayList<int[]> getNeighborCells(int i , int j, int n , int m ){
        if(i==0)
            return firstRow(j,m) ;
        else if(i==n-1)
           return lastRow(j,n,m) ;
        else
            return others(i,j,m) ;
    }

    private static ArrayList<int[]> firstRow(int j ,int m){
        ArrayList<int[]> indexes=new ArrayList<>();
        if(j==0){
            int[] A1 = {0,1} ;
            int[] A2 = {1,0} ;
            int[] A3 = {1,1} ;
            indexes.add(A1) ;
            indexes.add(A2) ;
            indexes.add(A3) ;
        }else if(j==m-1){
            int[] A1 = {0,m-2} ;
            int[] A2 = {1,m-1} ;
            int[] A3 = {1,m-2} ;
            indexes.add(A1) ;
            indexes.add(A2) ;
            indexes.add(A3) ;
        }else{
            int[] A1 = {0,j-1} ;
            int[] A2 = {0,j+1} ;
            int[] A3 = {1,j-1} ;
            int[] A4 = {1,j} ;
            int[] A5 = {1,j+1} ;
            indexes.add(A1) ;
            indexes.add(A2) ;
            indexes.add(A3) ;
            indexes.add(A4) ;
            indexes.add(A5) ;
        }
        return indexes ;
    }



    private static ArrayList<int[]> lastRow(int j, int n , int m){
        ArrayList<int[]> indexes=new ArrayList<>();
        if(j==0){
            int[] A1 = {n-1,1} ;
            int[] A2 = {n-2,0} ;
            int[] A3 = {n-2,1} ;
            indexes.add(A1) ;
            indexes.add(A2) ;
            indexes.add(A3) ;
        }else if(j==m-1){
            int[] A1 = {n-1,m-2} ;
            int[] A2 = {n-2,m-1} ;
            int[] A3 = {n-2,m-2} ;
            indexes.add(A1) ;
            indexes.add(A2) ;
            indexes.add(A3) ;
        }else{
            int[] A1 = {n-1,j-1} ;
            int[] A2 = {n-1,j+1} ;
            int[] A3 = {n-2,j-1} ;
            int[] A4 = {n-2,j} ;
            int[] A5 = {n-2,j+1} ;
            indexes.add(A1) ;
            indexes.add(A2) ;
            indexes.add(A3) ;
            indexes.add(A4) ;
            indexes.add(A5) ;
        }
        return indexes ;
    }

    private static ArrayList<int[]> others(int i , int j , int m){
        ArrayList<int[]> indexes=new ArrayList<>();
        if(j==0){
            int[] A1={i-1,0};
            int[] A2={i-1,1};
            int[] A3={i,1};
            int[] A4={i+1,0};
            int[] A5={i+1,1};
            indexes.add(A1);
            indexes.add(A2);
            indexes.add(A3);
            indexes.add(A4);
            indexes.add(A5);
        }else if(j==m-1){
            int[] A1={i-1,m-1};
            int[] A2={i-1,m-2};
            int[] A3={i,m-2};
            int[] A4={i+1,m-1};
            int[] A5={i+1,m-2};
            indexes.add(A1);
            indexes.add(A2);
            indexes.add(A3);
            indexes.add(A4);
            indexes.add(A5);
        }else{
            int[] A1={i, j - 1};
            int[] A2={i, j + 1};
            int[] A3={i - 1, j};
            int[] A4={i + 1, j};
            int[] A5={i - 1, j - 1};
            int[] A6={i - 1, j + 1};
            int[] A7={i + 1, j - 1};
            int[] A8={i + 1, j + 1};
            indexes.add(A1);
            indexes.add(A2);
            indexes.add(A3);
            indexes.add(A4);
            indexes.add(A5);
            indexes.add(A6);
            indexes.add(A7);
            indexes.add(A8);
        }
        return indexes ;
    }

    public static ArrayList<int[]> getClosestNeighborCells(int i , int j, int n , int m){
        if(i==0)
            return firstRowForClosest(j,m) ;
        else if(i==n-1)
            return lastRowForClosest(j,n,m) ;
        else
            return othersForClosest(i,j,m) ;
    }

    private static ArrayList<int[]> firstRowForClosest(int j ,int m){
        ArrayList<int[]> indexes=new ArrayList<>();
        if(j==0){
            int[] A1 = {0,1} ;
            int[] A2 = {1,0} ;
            indexes.add(A1) ;
            indexes.add(A2) ;
        }else if(j==m-1){
            int[] A1 = {0,m-2} ;
            int[] A2 = {1,m-1} ;
            indexes.add(A1) ;
            indexes.add(A2) ;
        }else{
            int[] A1 = {0,j-1} ;
            int[] A2 = {0,j+1} ;
            int[] A3 = {1,j} ;
            indexes.add(A1) ;
            indexes.add(A2) ;
            indexes.add(A3) ;
        }
        return indexes ;
    }

    private static ArrayList<int[]> lastRowForClosest(int j, int n , int m){
        ArrayList<int[]> indexes=new ArrayList<>();
        if(j==0){
            int[] A1 = {n-1,1} ;
            int[] A2 = {n-2,0} ;
            indexes.add(A1) ;
            indexes.add(A2) ;
        }else if(j==m-1){
            int[] A1 = {n-1,m-2} ;
            int[] A2 = {n-2,m-1} ;
            indexes.add(A1) ;
            indexes.add(A2) ;
        }else{
            int[] A1 = {n-1,j-1} ;
            int[] A2 = {n-1,j+1} ;
            int[] A3 = {n-2,j} ;
            indexes.add(A1) ;
            indexes.add(A2) ;
            indexes.add(A3) ;
        }
        return indexes ;
    }

    private static ArrayList<int[]> othersForClosest(int i , int j, int m){
        ArrayList<int[]> indexes=new ArrayList<>();
        if(j==0){
            int[] A1={i-1,0};
            int[] A2={i,1};
            int[] A3={i+1,0};
            indexes.add(A1);
            indexes.add(A2);
            indexes.add(A3);
        }else if(j==m-1){
            int[] A1={i-1,m-1};
            int[] A2={i,m-2};
            int[] A3={i+1,m-1};
            indexes.add(A1);
            indexes.add(A2);
            indexes.add(A3);
        }else{
            int[] A1={i, j - 1};
            int[] A2={i, j + 1};
            int[] A3={i - 1, j};
            int[] A4={i + 1, j};
            indexes.add(A1);
            indexes.add(A2);
            indexes.add(A3);
            indexes.add(A4);
        }
        return indexes ;
    }

    public int getN() {
        return n;
    }

    public int getM() {
        return m;
    }
}
