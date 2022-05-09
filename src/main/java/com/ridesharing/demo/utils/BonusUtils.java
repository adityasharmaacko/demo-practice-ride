package com.ridesharing.demo.utils;

import com.ridesharing.demo.database.RidesManager;
import com.ridesharing.demo.model.Ride;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
@Slf4j
public class BonusUtils {

    @Autowired
    private RidesManager ridesManager;

    HashMap<String,ArrayList<String>>map = new HashMap<>();
    HashMap<String, Integer> cityLinks = new HashMap<>();

    /**
     * create links b/w two rides
     * @param src
     * @param destination
     */
    private void addEdge(String src, String destination)
    {
        map.get(src).add(destination);
    }

    /**
     * check whether two cities are already linked or not
     * @param src
     * @param dest
     * @return
     */
    public Boolean isCitiesAlreadyLinked(String src, String dest){
        ArrayList<String>connectedCities = map.get(src);
        for(String city : connectedCities){
            if(city.equalsIgnoreCase(dest)){
                return true;
            }
        }
        return false;
    }

    /**
     * create map of given cities links
     * @param minSeats
     */
    public void createMap(int minSeats){
        List<Ride>listOfValidRides = ridesManager.getAllRidesListWithAvailableSeats(minSeats);
        for(Ride listOfValidRide : listOfValidRides){
            map.put(listOfValidRide.getSourceLocation(), new ArrayList<>());
            map.put(listOfValidRide.getDestinationLocation(), new ArrayList<>());
        }
        ArrayList<String>listOfUniqueCities = uniqueCities(listOfValidRides);
        for(int i=0;i<listOfUniqueCities.size();i++){
            cityLinks.put(listOfUniqueCities.get(i),i);
        }
        for (Ride listOfValidRide : listOfValidRides) {
            addEdge(listOfValidRide.getSourceLocation(), listOfValidRide.getDestinationLocation());
        }
    }

    /**
     * get the list of unique cities in database
     * @param listOfValidRides
     * @return
     */
    public ArrayList<String> uniqueCities(List<Ride>listOfValidRides){
        ArrayList<String>uniqueList = new ArrayList<>();
        for (Ride listOfValidRide : listOfValidRides) {
            if (isUniqueCity(listOfValidRide.getSourceLocation(), uniqueList)) {
                uniqueList.add(listOfValidRide.getSourceLocation());
            }
            if (isUniqueCity(listOfValidRide.getDestinationLocation(), uniqueList)) {
                uniqueList.add(listOfValidRide.getDestinationLocation());
            }
        }
        return uniqueList;
    }

    // helper function - unique city
    public boolean isUniqueCity(String city, ArrayList<String>arr){
        for (String s : arr) {
            if (Objects.equals(s, city)) {
                return false;
            }
        }
        return true;
    }

    // helper function - print an ArrayList
    public void printArrayList(ArrayList<String>arr){
        for(String s: arr){
            System.out.println(s);
        }
    }

    /**
     * get the shortest possible routes with available seats for disconnected rides
     * @param source
     * @param destination
     * @param pred
     * @return
     */
    public boolean BFS(String source, String destination, String pred[]){
        LinkedList<String>queue = new LinkedList<>();

        HashMap<String, Integer>vis = new HashMap<>();

        for(var entry : map.entrySet()){
            vis.put(entry.getKey(),0);
        }

        for(int i=0;i<map.size();i++){
            pred[i] = "";
        }
        queue.add(source);

        while(!queue.isEmpty()){
            String front = queue.remove();
            ArrayList<String>tmp = map.get(front);
            for (String s : tmp) {
                if (vis.get(s) == 0) {
                    vis.put(s, 1);
                    queue.add(s);
                    pred[cityLinks.get(s)] = front;
                    if (Objects.equals(s, destination)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    /**
     * return the list of all disconnected rides b/w given source and destination
     * @param source
     * @param destination
     * @param minSeats
     * @return
     */
    public ArrayList<Ride> getRoute(String source, String destination, int minSeats){
        createMap(minSeats);
        String[] pred = new String[map.size()];

        if(!BFS(source, destination, pred)){
            log.error("no disconnected route present b/w source and destination");
            return null;
        }
        ArrayList<String>path = new ArrayList<>();
        path.add(destination);
        String crawl = destination;
        while(!Objects.equals(pred[cityLinks.get(crawl)], "")){
            path.add(pred[cityLinks.get(crawl)]);
            crawl = pred[cityLinks.get(crawl)];
        }
//        System.out.println("=========== printing path =============");
//        printArrayList(path);
        ArrayList<Ride>listOfAllRides = ridesManager.getAllAvailableRides();
        ArrayList<Ride>rides = new ArrayList<>();
        for(int i=path.size()-1;i>0;i--){
            String src = path.get(i);
            String dest = path.get(i-1);
            System.out.println("src:: "+src + " dest: "+dest);
            for(Ride ride : listOfAllRides){
                if(Objects.equals(ride.getSourceLocation(), src) &&
                        Objects.equals(ride.getDestinationLocation(), dest) &&
                            ride.getAvailableSeats() >= minSeats){
                    rides.add(ride);
                    break;
                }
            }
        }
        return rides;
    }

}
