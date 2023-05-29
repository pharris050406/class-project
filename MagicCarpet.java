import java.util.ArrayList;
import java.util.Collections;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class MagicCarpet {
    private double speed;
    private String currentCity;
    private ArrayList<String> plainText = new ArrayList<String>();
    private ArrayList<String> DestinationList = new ArrayList<String>();
    private ArrayList<Float> latitude = new ArrayList<Float>();
    private ArrayList<Float> longitude = new ArrayList<Float>();
    private FileInputStream inputFile;

    public MagicCarpet(){
        Scanner s = new Scanner(System.in);
        System.out.println("Enter a speed for your magic carpet in km/h");
        this.speed = s.nextDouble();
        this.currentCity = "San Diego";
        try {
            inputFile = new FileInputStream("worldcities.csv");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        this.initialiseLocations();
    };

    // reads from the .csv file and inputs the corresponding values into corresponding lists so they're easier to mess with in java
    public void initialiseLocations(){
            Scanner s;
            s = new Scanner(inputFile, "UTF-8");
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
                latitude.add(Float.parseFloat(plainText.get(i).split(",")[2]));
                longitude.add(Float.parseFloat(plainText.get(i).split(",")[3]));
            }
            s.close();
    }
    public ArrayList<String> getDestinations(){
        return DestinationList;
    }
    // travels from the current city to the selected city
    public void travel(String location){
            System.out.println(location.toLowerCase().trim());
            if(doesCityExist(location)){
            float lat1 = getLatitude(this.currentCity);
            float lon1 = getLongitude(this.currentCity);
            float lat2 = getLatitude(location);
            float lon2 = getLongitude(location);
            double dist = distFrom(lat1, lon1, lat2, lon2);
            System.out.println("Current Lat: " + lat1 + "\nCurrent Lon: " + lon1);
            System.out.println("City Lat:" + lat2 + "\nCity Lon: " + lon2);
            System.out.println("Distance to travel: " + dist + " km");
            System.out.println("Travel time: " + dist/this.speed + " hours");
            System.out.println("Welcome to " + location + "!");
            this.currentCity = location;
            }else{
                Scanner s = new Scanner(System.in);
                System.out.println("That city is not in our database. Please pick another location.");
                travel(s.nextLine());
                s.close();
            }
    }
    // i seriously do not know why i changed the 'getlatitude' function to be different from the 'getlongitude' function. i don't even remember changing either of them. 
    // i could've even just made them both into one function.
    // they work though so it's whatever just straight chillin
    public float getLatitude(String city) {
            Scanner s = new Scanner(inputFile);
            int index = Collections.binarySearch(DestinationList, city.toLowerCase());

            if(index < 0){
                System.out.println("That city is not in our database. Please pick another location.");
                    getLatitude(s.nextLine().replace("\n", ""));
            }
            s.close();
            return latitude.get(index);
    }
    public float getLongitude(String city){
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
            return Float.MIN_VALUE;
        }
    }
    // checks if the city exists. used to reprompt the user. 
    public boolean doesCityExist(String city){
        return  Collections.binarySearch(DestinationList, city.toLowerCase()) > -1;
    }

    // found this on stack overflow ! returns distance between two coordinates in meters (ish)
public static double distFrom(double lat1, double lng1, double lat2, double lng2) 
{
    // mHager 08-12-2012
    // http://en.wikipedia.org/wiki/Haversine_formula
    // Implementation
    double EARTH_RADIUS = 6731;

    // convert to radians
    lat1 = Math.toRadians(lat1);
    lng1 = Math.toRadians(lng1);
    lat2 = Math.toRadians(lat2);
    lng2 = Math.toRadians(lng2);

    double dlon = lng2 - lng1;
    double dlat = lat2 - lat1;

    double a = Math.pow((Math.sin(dlat/2)),2) + Math.cos(lat1) * Math.cos(lat2) * Math.pow(Math.sin(dlon/2),2);

    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));

    return EARTH_RADIUS * c/10;
}   

}
