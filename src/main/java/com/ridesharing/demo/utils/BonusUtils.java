package com.ridesharing.demo.utils;

import com.ridesharing.demo.database.RidesManager;
import com.ridesharing.demo.model.Ride;
import com.ridesharing.demo.requests.RideRequestDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

@Component
@Slf4j
public class BonusUtils {

    @Autowired
    private RidesManager ridesManager;

    HashMap<String,ArrayList<String>> map = new HashMap<>();
    HashMap<String, Integer> cityLinks = new HashMap<>();
    HashMap<Integer, String> numberedLinks = new HashMap<>();


    private void addEdge(String src, String destination)
    {
        map.get(src).add(destination);
        map.get(destination).add(src);
    }

    public Boolean isCitiesAlreadyLinked(String src, String dest){
        ArrayList<String>connectedCities = map.get(src);
        for(String city : connectedCities){
            if(city.equalsIgnoreCase(dest)){
                return true;
            }
        }
        return false;
    }

    // TBD - wip
    public List<String> bonusCase(RideRequestDto rideRequestDto){
        List<Ride>presentRides = ridesManager.getAllRidesListWithAvailableSeats(rideRequestDto.getSeats());
        for(Ride ride : presentRides){
            if(!isCitiesAlreadyLinked(ride.getSourceLocation(),ride.getDestinationLocation())){
                addEdge(ride.getSourceLocation(), ride.getDestinationLocation());
            }
        }
        Integer count = 0;
        for (var entry : map.entrySet()) {
            cityLinks.put(entry.getKey(),count);
            numberedLinks.put(count,entry.getKey());
            count++;
        }
        int totalVertex = map.size();
        Integer[] pred = new Integer[totalVertex];
        Integer[] dist = new Integer[totalVertex];

        if (BFS(rideRequestDto.getSourceLocation(), rideRequestDto.getDestinationLocation(), totalVertex, pred, dist)) {
            log.debug("Given source and destination are not connected");
            return null;
        }

        LinkedList<Integer> path = new LinkedList<Integer>();
        Integer crawl = cityLinks.get(rideRequestDto.getDestinationLocation());
        path.add(crawl);
        while (pred[crawl] != -1) {
            path.add(pred[crawl]);
            crawl = pred[crawl];
        }

        System.out.println("Shortest path length is: " + dist[cityLinks.get(rideRequestDto.getDestinationLocation())]);

        System.out.println("Path is ::");
        for (int i = path.size() - 1; i >= 0; i--) {
            System.out.print(numberedLinks.get(path.get(i)) + " ");
        }

        return null;
    }

    public Boolean BFS(String src, String dest, Integer totalVertex, Integer[] pred, Integer[] dist){
        LinkedList<Integer> queue = new LinkedList<Integer>();
        boolean[] visited = new boolean[totalVertex];

        for (int i = 0; i < totalVertex; i++) {
            visited[i] = false;
            dist[i] = Integer.MAX_VALUE;
            pred[i] = -1;
        }

        visited[cityLinks.get(src)] = true;
        dist[cityLinks.get(src)] = 0;
        queue.add(cityLinks.get(src));

        while (!queue.isEmpty()) {
            int u = queue.remove();
            for (int i = 0; i < map.get(numberedLinks.get(u)).size(); i++) {
                if (!visited[cityLinks.get(map.get(numberedLinks.get(u)).get(i))]) {
                    visited[cityLinks.get(map.get(numberedLinks.get(u)).get(i))] = true;
                    dist[cityLinks.get(map.get(numberedLinks.get(u)).get(i))] = dist[u] + 1;
                    pred[cityLinks.get(map.get(numberedLinks.get(u)).get(i))] = u;
                    queue.add(cityLinks.get(map.get(numberedLinks.get(u)).get(i)));

                    if (cityLinks.get(map.get(numberedLinks.get(u)).get(i)).equals(cityLinks.get(dest)))
                        return true;
                }
            }
        }
        return false;
    }

    // TBD - wip
    public List<Ride> aggregateRides(List<String>path, Integer seats){
        return null;
    }
}
