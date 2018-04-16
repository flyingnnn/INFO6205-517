import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;
import java.util.logging.Logger;

/**Class defines the static Parameters and initialize datasets
 * @author Nenghui Fang  Yuchen He
 *
 */

public class TSP {

    /***************************static parameters used ***********************************/
    public static final double MUTATE_RATE=0.5;
    public static final double CUTOFF_RATE = 0.4; // cutoff rate for each generation
    public static final int FECUNDITY = 2; //number of offspring per mating pair
    public static final int CITIES_NUM = 20;  // initial city num
    public static final int POPULATION_SIZE = 100;  //initial population size
    public static final int MAX_GENERATION=100; //stop evolve when max generation meets
    public static final int MAX_EUCLID = 500; // Coordinate space(max X and max Y)for City Node
    /************************************************************************************/

    //create a logger
    private final static Logger LOG = Logger.getLogger(TSP.class.getName());


    public static void main(String[] args) {

        //initialize city nodes and routes
        ArrayList<City> cities = new ArrayList<>();
        Population pop;
        int evoleTimes = 0;

        //LOG.info("Initializing City Nodes");
        cityNodesInitialization(cities, "MANUAL");  //use String MANUAL to set 20 Cities and RANDOM to randomly set
        LOG.info("Initialize the first generation population");
        pop = new Population(cities, POPULATION_SIZE);

        //System.out.println(pop.toString());

        while(evoleTimes < MAX_GENERATION){
            pop = pop.evolve(pop);
            evoleTimes++;
            System.out.println("Generation:" + evoleTimes + " Fitness of the Best candidate: " + pop.getBestIndividual().getFitness());
        }

    }

    public static void cityNodesInitialization(ArrayList<City> cities,String control){
        Random random = new Random();
        //Manually Initialize 20 CityNodes
        if(control.equals("MANUAL")){
            cities.add(new City(122,390));
            cities.add(new City(10,139));
            cities.add(new City(459,120));
            cities.add(new City(38,200));
            cities.add(new City(138,212));
            cities.add(new City(348,139));
            cities.add(new City(168,412));
            cities.add(new City(209,40));
            cities.add(new City(318,78));
            cities.add(new City(89,59));
            cities.add(new City(428,310));
            cities.add(new City(491,378));
            cities.add(new City(118,333));
            cities.add(new City(431,18));
            cities.add(new City(198,298));
            cities.add(new City(300,450));
            cities.add(new City(132,410));
            cities.add(new City(289,12));
            cities.add(new City(319,248));
            cities.add(new City(90,263));

        }

        if(control.equals("RANDOM")){
            //randomly initialize CityNodes
            for (int i = 0; i < CITIES_NUM; i++) {
                cities.add(new City(random.nextInt(MAX_EUCLID), random.nextInt(MAX_EUCLID)));
                System.out.print(cities.get(i).toString());
            }
        }
    }

}
