package jungle;

import animal.Animal;

import java.util.ArrayList;

public class Cell {

    private int k ;
    private final int i ;
    private final int j ;
    private int r ;
    private int currentWeight = 0;

    private ArrayList<Animal>[] entities ;
    private int[] countOfEachType ;

    public Cell(int r, int i , int j, int k) {
        this.r = r ;
        this.i = i ;
        this.j = j ;
        this.k = k ;
        initialize(r);
    }

    void initialize(int r){
        entities = new ArrayList[r] ;
        for(int i=0 ; i<r ; i++)
            entities[i] = new ArrayList<>() ;

        countOfEachType = new int[r];
    }

    public synchronized void add(Animal animal){
        int type = animal.getType();
        entities[type-1].add(animal);
        countOfEachType[type-1] += 1;
        currentWeight += type ;
        animal.setCoordinates(i,j);
    }

    public synchronized void remove(Animal animal){
        int type = animal.getType();
        entities[type-1].remove(animal);
        countOfEachType[type-1] -= 1;
        currentWeight -= type ;
    }

    public synchronized void removeAll(int type){
        for(int i=countOfEachType[type-1]-1 ; i>=0 ; i--){
            Animal animal = entities[type - 1].get(i) ;
            animal.kill();
            entities[type - 1].remove(i);
        }
        countOfEachType[type-1] = 0 ;

    }

    public synchronized boolean onlyHasType(int type){
        for(int h=0 ; h<r ; h++)
            if(h != type-1)
                if(countOfEachType[h]>0)
                    return false ;

        return true ;
    }

    public synchronized boolean hasCapacity(int type){
        return type+currentWeight<=k ;
    }

    public synchronized boolean isBalanced(){
        return currentWeight<=k ;
    }

    public synchronized int getCurrentWeight(){
        return currentWeight ;
    }

    public synchronized int getCountOfType(int type){
        return countOfEachType[type-1] ;
    }

    public synchronized void balanceCapacity(){
        if(isBalanced())
            return;
        else {
            for(int i=0 ; i<r ; i++)
                for(int j=0 ; j<entities[i].size() ; j++){
                    Animal animal = entities[i].get(j) ;
                    entities[i].remove(j) ;
                    countOfEachType[i]-- ;
                    animal.kill() ;
                    if(isBalanced())
                        return;
                }
        }
    }



    public int getI() {
        return i;
    }

    public int getJ() {
        return j;
    }
}
