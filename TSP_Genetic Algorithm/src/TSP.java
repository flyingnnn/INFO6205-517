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
    public static final double MUTATE_RATE=0.2;
    public static final double CUTOFF_RATE = 0.5; // cutoff rate for each generation
    public static final int FECUNDITY = 2; //number of offspring per mating pair
    public static final int CITIES_NUM = 5;  // initial city num
    public static final int POPULATION_SIZE = 10;  //initial population size
    public static final int MAX_GENERATION=100; //stop evolve when max generation meets
    public static final int MAX_EUCLID = 1000; // Coordinate space(max X and max Y)for City Node
    /************************************************************************************/

    //create a logger
    private final static Logger LOG = Logger.getLogger(TSP.class.getName());


    public static void main(String[] args) {

        //initialize city nodes and routes
        Random random = new Random();
        ArrayList<City> cities = new ArrayList<>();
        ArrayList<Individual> routes = new ArrayList<>();
        Population pop;
        int evoleTimes = 0;

        //LOG.info("Initializing City Nodes");
        for (int i = 0; i < CITIES_NUM; i++) {
            cities.add(new City(random.nextInt(MAX_EUCLID), random.nextInt(MAX_EUCLID)));
            //System.out.print(cities.get(i).toString());
        }
        System.out.println();

        LOG.info("Initialize the first generation population");
        pop = new Population(cities, POPULATION_SIZE);

        //System.out.println(pop.toString());

        while(evoleTimes < MAX_GENERATION){
            pop = pop.evolve(pop);
            evoleTimes++;
            System.out.println("Generation:" + evoleTimes + " Fitness of the Best candidate: " + pop.getBestIndividual().getFitness());
        }

    }

}
