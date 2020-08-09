package animal;

import jungle.Cell;
import jungle.Jungle;
import simulator.Simulator;

import java.util.ArrayList;
import java.util.Random;

public class Animal extends Thread{

    int type;
    int index ;
    int weight ;
    int lifeTime ;
    int i;
    int j;
    boolean alive = false;
    Jungle jungle ;
    Random random ;


    public Animal(int type, int index, int i, int j, Jungle jungle) {
        this.type=type;
        this.index = index ;
        this.weight = type ;
        this.lifeTime = type ;
        this.i=i;
        this.j=j ;
        this.jungle = jungle ;
        random = new Random();
    }

    public Animal(int type, int index,Jungle jungle) {
        //in case of using this constructor remember to setCoordinates of the animal immediately
        this.type=type ;
        this.index=index ;
        this.weight = type ;
        this.lifeTime = type ;
        this.jungle = jungle ;
        random = new Random();
    }


    @Override
    public void run() {
       super.run();
       startLiving();
       while(alive){
           move();
           try {
               sleep(1000);
           } catch (InterruptedException e) {
               e.printStackTrace();
           }
       }
    }

    void startLiving(){
        alive = true ;
    }

    synchronized void move(){
        ArrayList<int[]> neighborsIndex = Jungle.getNeighborCells(i,j,jungle.getN(),jungle.getM()) ;
        int r = random.nextInt(neighborsIndex.size()) ;
        int[] targetIndex = neighborsIndex.get(r) ;
        Cell targetCell = jungle.getCell(targetIndex[0],targetIndex[1]);
        jungle.doMovement(this,targetCell) ;
    }

    public synchronized void kill(){
        alive = false ;
        Simulator.logDeath(type,jungle.getCell(i,j));
    }


    public int getType() {
        return type;
    }

    public int getIndex() {
        return index;
    }

    public int getWeight() {
        return weight;
    }

    public void setCoordinates(int i, int j){
        this.i= i ;
        this.j= j ;
    }

    public int getI() {
        return i;
    }

    public int getJ() {
        return j;
    }

    public boolean animalIsAlive(){
        return alive ;
    }
}
