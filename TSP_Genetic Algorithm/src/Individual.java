import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static java.lang.Math.sqrt;

/**Route Class defines the route connecting all the cities
 * @author Nenghui Fang
 *
 */

public class Individual implements Comparable<Individual>{

    private double routeWeight;
    private int fitness = 0;
    private ArrayList<City> route;
    private int routeSize = 0;

    public Individual(ArrayList<City> cities) {
        route = new ArrayList<>();
        for (int i = 0; i < cities.size(); i++) {
            route.add(cities.get(i));
        }
        routeSize = cities.size();
        getFitness();
    }

    public Individual(int cities_num){
        route = new ArrayList<>();
        for (int i = 0; i < cities_num; i++) {
            route.add( new City());
        }
        routeSize = cities_num;
    }

    public double getRouteWeight(){

        routeWeight = getTwoCitiesDistance(route.get(0), route.get(route.size()-1));  //calculate the distance between the first cityNode and the last cityNode
        for (int i = 0; i < route.size() - 1 ; i++) {
            routeWeight += getTwoCitiesDistance(route.get(i), route.get(i+1));
        }
        return routeWeight;
    }


    public int getFitness(){
//        if(fitness == 0){    //maybe some problem here
//            fitness = 1.0/getRouteWeight();
//        }
//
        return (int)getRouteWeight();
    }

    public void updateFitness(){
        fitness = (int)getRouteWeight();
    }

    public double getTwoCitiesDistance(City city1, City city2){
        double distance = 0;

        int xd = city1.getX() - city2.getX();
        int yd = city1.getY() - city2.getY();
        distance = sqrt(xd*xd + yd*yd);

//        double tij = Math.nint( rij );
//        if (tij<rij) distance = tij + 1;
//        else distance = tij;
        return distance;
    }

    @Override
    public String toString() {
        String geneString = "{";
        for (int i = 0; i < routeSize; i++) {
            geneString += route.get(i).toString();
        }
        geneString += "}";
        return geneString;
    }

    public ArrayList<City> getRoute() {
        return route;
    }

    public City getCity(int index) {
        return route.get(index);
    }


    public void setCity(int index, City city) {
        route.set(index,city);
    }

    @Override
    public int compareTo(Individual o) {

        int compareQuantity = ((Individual)o).getFitness();

        //ascending order
        return this.fitness - compareQuantity;

        //descending order
        //return (int)(compareQuantity - this.fitness);

    }

}
