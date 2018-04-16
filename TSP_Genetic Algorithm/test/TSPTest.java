import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class TSPTest {

     ArrayList<City> cities = new ArrayList<>();
     Individual individual = new Individual(2);
     Population population;

     TSPTest(){
         for (int i = 0; i < 10; i++) {
             cities.add(new City((int)(Math.random()*100), (int)(Math.random()*100)));
         }

         individual.getRoute().set(0,new City(0,0));
         individual.getRoute().set(1,new City(10,0));
         population = new Population(cities, 100);
     }

    @Test
    void testCity(){

        //Test the size of cities list is initialized correctly
        assertEquals(cities.size(), 10);
        //Test the value of cities list is initialized correctly
        for (int i = 0; i < 10; i++) {
            assertEquals(cities.get(i).getX() < 100, true);
            assertEquals(cities.get(i).getY() < 100, true);
        }
    }

    @Test
    void testFitnessMethod(){

         //test the FitnessMethod got the right calculation based on geometry route length
         assertEquals(individual.getRoute().size(), 2);
         assertEquals(individual.getFitness(),20);
    }

    @Test
    void testPopulation(){

        //Test the population is initialized properly
        assertEquals(population.getRoutes().size(), 100);

        //Test the routes in the population contains all the cityNodes
        for (int i = 0; i < 10; i++) {
            assertEquals(population.getRoutes().get(i).getRoute().contains(cities.get(i)),true);
        }
    }

    @Test
    void testCrossover() {
         //Test the crossover yields proper child
         Individual father = new Individual(cities);
         Collections.shuffle(cities);
         Individual mother = new Individual(cities);
         ArrayList<Individual> child = population.crossover(father,mother);
         assertEquals(child.size(), 2);
    }

    @Test
    void testMutate() {
        population = new Population(cities, 100);
    }

    @Test
    void testPopulationCutoff() {
        for (int i = 0; i < 10; i++) {
            cities.add(new City((int)(Math.random()*100), (int)(Math.random()*100)));
        }
        population = new Population(cities, 100);

        //Test if the cutoff method returns num of Individule Corectly based on cutoff rate
        Population suvivalPop = population.populationCutoff(population);

        assertEquals(suvivalPop.getPopulationSize(), 100 * (1-TSP.CUTOFF_RATE));

    }

    @Test
    void testSelectCandidate() {
         //test the selected candidate is in the population
         Individual individual1 = population.selectCandidate(population);

         assertEquals(population.getRoutes().contains(individual1),true);

    }

    @Test
    void testGetBestIndividual() {

        //test the best candidate has the smallest fitness value according to the fitness function
        Individual best = population.getBestIndividual();
        for (int i = 0; i < population.getRoutes().size(); i++) {
            assertEquals(best.getFitness() <= population.getRoutes().get(i).getFitness(), true);
        }
    }
}