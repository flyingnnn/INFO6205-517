import org.omg.CORBA.INITIALIZE;

import java.util.*;
import java.util.logging.Logger;

/**Population Class defines all the possible routes
 * @author Nenghui Fang
 *
 */
public class Population {

    //create a logger
    private final static Logger LOG = Logger.getLogger(Population.class.getName());

    private ArrayList<Individual> routes;

    private int populationSize;
    private Individual bestIndividual;

    Population(ArrayList<City> cities,int populationSize){
        routes = new ArrayList<>();

        for (int i = 0; i < populationSize; i++) {
            Collections.shuffle(cities);  //shuffle the input cities to randomly create different routes

            routes.add(new Individual(cities));
        }

        this.populationSize = populationSize;
    }

    Population(){
        routes = new ArrayList<>();
    }


    public ArrayList<Individual> getRoutes() {
        return routes;
    }



    public Population evolve(Population parentPop){

        Population nextGenPop = new Population();
        int populationSpace = TSP.POPULATION_SIZE;
        Population survivailPop = populationCutoff(parentPop);
        for (int i = 0; i < survivailPop.populationSize; i++) {
            nextGenPop.routes.add(survivailPop.routes.get(i));
            populationSpace--;
        }

        while(populationSpace > 0) {
            Individual father = selectCandidate(survivailPop);
            Individual mother = selectCandidate(survivailPop);

            ArrayList<Individual> child = crossover(father, mother);

            if (Math.random() < TSP.MUTATE_RATE) {
                mutate(child.get(0));
                nextGenPop.routes.add(child.get(0));
                populationSpace--;
            }


            if (TSP.FECUNDITY == 2) {

                if (Math.random() < TSP.MUTATE_RATE) {
                    mutate(child.get(1));
                    if(populationSpace > 0) {
                        nextGenPop.routes.add(child.get(1));
                        populationSpace--;
                    }
                }

            }
        }
        return nextGenPop;
    }


    public ArrayList<Individual> crossover(Individual father, Individual mother){

        ArrayList<Individual> child = new ArrayList<>(TSP.FECUNDITY);

        child.add(0,new Individual(TSP.CITIES_NUM));

        if(TSP.FECUNDITY == 2){
            child.add(1, new Individual(TSP.CITIES_NUM));
        }
        //get random start and end position of father, make sure start <= end
        Random random = new Random();
        int index1 = random.nextInt(TSP.CITIES_NUM);
        int index2 = random.nextInt(TSP.CITIES_NUM);
        int startPos = Math.min(index1, index2);
        int endPos = Math.max(index1, index2);

        //generated first child from parents
        // add the sub routes from father to child
        for (int i = startPos; i < endPos; i++) {
                child.get(0).setCity(i, father.getCity(i));
            }
        // add the remaining cities from mother
        for (int i = 0; i < TSP.CITIES_NUM; i++) {
            if (!child.get(0).getRoute().contains(mother.getCity(i))) {
                // Loop to find a spare position in the child's route
                for (int ii = 0; ii < TSP.CITIES_NUM; ii++) {
                    // Spare position found, add city
                    if (child.get(0).getCity(ii) == null) {
                        child.get(0).setCity(ii, mother.getCity(i));
                        break;
                    }
                }
            }
        }

        //generate another child if needed
        if(TSP.FECUNDITY ==2){  //two children generated from parents, we need to have another child
            //copy other parts except startPos to endPos from father to child
            for (int i = 0; i < startPos; i++) {
                child.get(1).setCity(i, father.getCity(i));
            }
            for (int i = endPos; i < TSP.CITIES_NUM; i++) {
                child.get(1).setCity(i, father.getCity(i));
            }

            // add the remaining cities from mother
            for (int i = 0; i < TSP.CITIES_NUM; i++) {
                if (!child.get(1).getRoute().contains(mother.getCity(i))) {
                    // Loop to find a spare position in the child's route
                    for (int ii = 0; ii < TSP.CITIES_NUM; ii++) {
                        // Spare position found, add city
                        if (child.get(1).getCity(ii) == null) {
                            child.get(1).setCity(ii, mother.getCity(i));
                            break;
                        }
                    }
                }
            }
        }
        return child;
    }


    //mutate through swap two random city node
    public Individual mutate(Individual individual) {
        Random random = new Random();
        int index1 = random.nextInt(TSP.CITIES_NUM);
        int index2 = random.nextInt(TSP.CITIES_NUM);
        City city1 = individual.getCity(index1);
        City city2 = individual.getCity(index2);

        individual.setCity(index1, city2);
        individual.setCity(index2, city1);
        individual.updateFitness();
        return individual;
    }

    public Population populationCutoff(Population pop){
        Population selectedPop = new Population();

        //sort the arrayList based on fitness
        Collections.sort(pop.routes);

        //select the topest candidate in this generation based on fitness
        selectedPop.routes = new ArrayList<Individual>(pop.routes.subList((int)(TSP.CUTOFF_RATE * TSP.POPULATION_SIZE), TSP.POPULATION_SIZE));
        bestIndividual = pop.routes.get(TSP.POPULATION_SIZE-1);
        selectedPop.populationSize = selectedPop.routes.size();
        return selectedPop;
    }


    public Individual selectCandidate(Population pop){
        Random random = new Random();

        return pop.getRoute(random.nextInt(pop.populationSize));
    }

    public Individual getRoute(int index){

        return routes.get(index);
    }

    public void addRoute(Individual route){

        routes.add(route);
    }

    public Individual getBestIndividual(){

        if(bestIndividual == null) {
            Collections.sort(routes);
            bestIndividual = routes.get(routes.size() - 1);
        }
        return bestIndividual;
    }

    public int getPopulationSize() {
        return populationSize;
    }

    @Override
    public String toString() {
        return "Population" + routes.toString() ;
    }
}
