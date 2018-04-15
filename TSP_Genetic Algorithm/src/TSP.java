import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;
import java.util.logging.Logger;

public class TSP {

    /***************************static parameters used ***********************************/
    public static final double CROSSOVER_RATE = 0.3;
    public static final double MUTATE_RATE=0.1;

    public static final double CUTOFF_RATE = 0.5; // cutoff rate for each generation
    public static final int FECUNDITY = 2; //number of offspring per mating pair
    public static final int CITIES_NUM = 5;  // initial city num
    public static final int POPULATION_SIZE = 10;  //initial population size
    public static final int MAX_GENERATION=100; //stop evolve when max generation meets
    public static final int MAX_EUCLID = 5000; // Coordinate space(max X and max Y)for City Node
    public static final String OUTPUT_FILENAME = "cities_data";
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

        System.out.println(pop.toString());

        while(evoleTimes < MAX_GENERATION){
            pop = pop.evolve(pop);
            evoleTimes++;
            System.out.println("Generation:" + evoleTimes + " Best candidate: " + pop.getBestIndividual().getFitness());
        }

    }


    public void writeCitiesDataToFile() {
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(OUTPUT_FILENAME));
            for (int i = 0; i < CITIES_NUM; i++) {
//                for (int j = 0; j < CITIES_NUM-1; j++) {
//                    bw.write(matrix[i][j] + " ");
//                }
//                bw.write(matrix[i][CITIES_NUM - 1] + "\n");
            }
        } catch (IOException e) {
            System.err.println("\tERROR: Input file not found");
        } finally {
            try {
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
