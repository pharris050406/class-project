import java.util.ArrayList;
import java.util.Collections;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
public class MagicCarpet {
    private String name;
    private double speed;
    private double magic;
    private String currentCity;
    private ArrayList<String> plainText = new ArrayList<String>();
    private ArrayList<String> DestinationList = new ArrayList<String>();
    private ArrayList<Double> latitude = new ArrayList<Double>();
    private ArrayList<Double> longitude = new ArrayList<Double>();

    public MagicCarpet(String name, double speed, double magic){
        this.speed = speed;
        this.magic = magic;
        this.name = name;
        this.currentCity = "San Diego";
        this.initialiseLocations();
    };


    public void initialiseLocations(){
        try {
            Scanner s;
            s = new Scanner(new File("worldcities.csv"));
            s.nextLine();
            while (s.hasNextLine()){
                plainText.add(s.nextLine());
                // latitude.add(Double.parseDouble(line.split(",")[2]));
                // longitude.add(Double.valueOf(line.split(",")[3]));
            }
            Collections.sort(plainText);
            for(int i = 0; i < plainText.size(); i++){
                plainText.set(i,plainText.get(i).substring(plainText.get(i).indexOf(",")));
            }
            Collections.sort(plainText);
            for(int i = 0; i < plainText.size(); i++){
                DestinationList.add(plainText.get(i).split(",")[1].toLowerCase());
                latitude.add(Double.parseDouble(plainText.get(i).split(",")[2]));
                longitude.add(Double.valueOf(plainText.get(i).split(",")[3]));
            }
            System.out.println(DestinationList);
            s.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    public ArrayList<String> getDestinations(){
        return DestinationList;
    }

    public void travel(String location){
        Scanner s;
        try {
            s = new Scanner(new File("worldcities.csv"));
            System.out.println(location.toLowerCase().trim());
            if(doesCityExist(location)){
            double lat1 = getLatitude(this.currentCity);
            double lon1 = getLongitude(this.currentCity);
            double lat2 = getLatitude(location);
            double lon2 = getLongitude(location);
            s.nextLine();
            System.out.println("Current Lat: " + lat1 + "\nCurrent Lon: " + lon1);
            }else{
                System.out.println("That city is not in our database. Please pick another location.");
                travel(s.nextLine().replace("\n", ""));
            }
            s.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public double getLatitude(String city) {
        try {
            Scanner s = new Scanner(new File("worldcities.csv"));
            int index = Collections.binarySearch(DestinationList, city.toLowerCase());

            if(index < 0){
                System.out.println("That city is not in our database. Please pick another location.");
                    getLatitude(s.nextLine().replace("\n", ""));
            }
            return latitude.get(index);
    
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return -1.0;
        }
    }
    public double getLongitude(String city){
        try {
            Scanner s = new Scanner(new File("worldcities.csv"));
            int index = Collections.binarySearch(DestinationList, city.toLowerCase());

            if(index < 0){
                System.out.println("That city is not in our database. Please pick another location.");
                    getLongitude(s.nextLine().replace("\n", ""));
            }
            return longitude.get(index);
    
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return -1.0;
        }
    }
    public boolean doesCityExist(String city){
        return  Collections.binarySearch(DestinationList, city.toLowerCase()) > -1;
    }
}
