package logger;

import jungle.Cell;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Logger {

    String filePath = "src/logger/log";
    File log ;
    FileWriter writer ;

    public Logger() {
        log= new File(filePath);

        try {
            writer = new FileWriter(log);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized void logMovement(int animalType, Cell formerCell , Cell targetCell){
        try {
            writer.write(java.time.LocalTime.now()+": ");
            writer.write(
            "Animal Type "+animalType+" moved from ["+(formerCell.getI()+1)+","+(formerCell.getJ()+1)+"] to ["+(targetCell.getI()+1)+","+(targetCell.getJ()+1)+" ]\n");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized void logBirth(int animalType, Cell currentCell){
        try {
            writer.write(java.time.LocalTime.now()+": ");
            writer.write(
                    "Animal Type "+animalType+" zayman kard at ["+(currentCell.getI()+1)+","+(currentCell.getJ()+1)+"]\n");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized void logDeath(int animalType, Cell currentCell){
        try {
            writer.write(java.time.LocalTime.now()+": ");
            writer.write(
                    "Animal Type "+animalType+" mord at ["+(currentCell.getI()+1)+","+(currentCell.getJ()+1)+"]\n");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized void logMovement(int animalType, Cell formerCell , Cell targetCell,int time){
        try {
            writer.write(time+": ");
            writer.write(
                    "Animal Type "+animalType+" moved from ["+(formerCell.getI()+1)+","+(formerCell.getJ()+1)+"] to ["+(targetCell.getI()+1)+","+(targetCell.getJ()+1)+" ]\n");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized void logBirth(int animalType, Cell currentCell, int time){
        try {
            writer.write(time+": ");
            writer.write(
                    "Animal Type "+animalType+" reproduced at ["+(currentCell.getI()+1)+","+(currentCell.getJ()+1)+"]\n");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized void logDeath(int animalType, Cell currentCell, int time){
        try {
            writer.write(time+": ");
            writer.write(
                    "Animal Type "+animalType+" died at ["+(currentCell.getI()+1)+","+(currentCell.getJ()+1)+"]\n");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Logger logger = new Logger();
        logger.logMovement(3,new Cell(3,5,1,10),new Cell(3,5,7,10));

    }

}
