import java.util.ArrayList;
import java.util.Collections;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
public class MagicCarpet {
    private String color;
    private double speed;
    private double magic;
    private ArrayList<String> DestinationList = new ArrayList<String>();
    private ArrayList<Double> latitude = new ArrayList<Double>();
    private ArrayList<Double> longitude = new ArrayList<Double>();

    public void initialiseLocations(){
        Scanner s;
        try {
            s = new Scanner(new File("worldcities_copy.csv"));
            s.nextLine();
            while (s.hasNextLine()){
                String line = s.nextLine();
                DestinationList.add(line.split(",")[1].toLowerCase());
                latitude.add(Double.parseDouble(line.split(",")[2]));
                longitude.add(Double.valueOf(line.split(",")[3]));
            }
            Collections.sort(DestinationList);
            s.close();    
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    public ArrayList<String> getDestinations(){
        return DestinationList;
    }
    public MagicCarpet(String color, double speed, double magic){
        this.speed = speed;
        this.magic = magic;
        this.color = color;
        this.initialiseLocations();
    };

    public void travel(String location){
        int index = Collections.binarySearch(DestinationList, location.toLowerCase());
        System.out.println(longitude.get(index));
        System.out.println(latitude.get(index));
        }    
    }