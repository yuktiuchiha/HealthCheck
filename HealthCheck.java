// package healthcheck;
import java.util.*;

import java.util.Random;

// import javax.swing.Popup;

public class HealthCheck {
    
    Population population = new Population();
    Individual fittest = new Individual();
    Individual secondFittest = new Individual();
    int generationCount = 0;

    public static void main(String[] args) {
        
        Random rn = new Random();
        
        HealthCheck demo = new HealthCheck();
        
        //initialize population
        demo.population.initializePopulation(10);

        //calculate fitness of each individual
        demo.population.calculateFitness();

        System.out.println("Generation: " + demo.generationCount + " Fittest: " + demo.population.fittest);

        
        //while population gets an individual with maximaum fitness
        while(demo.population.fitness < 5){
            ++demo.generationCount;

            //do selection
            demo.selection();

            //do crossover
            demo.crossover();

            //do mutation under a random probability
            if(rn.nextInt()%7 < 5){
                demo.mutation();
            }

            //add fittest offspring to population
            demo.addFittestOffspring();

            //calculate new fittest value
            demo.population.calculateFitness();

            System.out.println("Generation: " + demo.generationCount + " Fittest: " + demo.population.fittest);

        }

        System.out.println("/nSolution found in generation " + demo.generationCount);
        System.out.println("Fitness: " + demo.population.getFittest().fitness);
        System.out.print("Genes: ");

        for(int i=0; i<5; i++){
            System.out.print(demo.population.getFittest().genes[i]);
        }

        System.out.println("");

    }    


    // selection
    void selection(){

        //select the most fittest individual
        fittest = population.getFittest();

        //select the second most fittest individual
        secondFittest = population.getSecondFittest();
    }


    // crossover
    void crossover(){

        Random rn = new Random();

        //Select a Random crossover point
        int crossoverPoint = rn.nextInt(population.individuals[0].geneLength);

        //Swap values among parents
        for(int i=0; i<crossoverPoint; i++){
            int temp = fittest.genes[i];
            fittest.genes[i] = secondFittest.genes[i];
            secondFittest.genes[i] = temp;

        }

    }


    //Mutation
    void mutation(){

        Random rn = new Random();

        //Select a random mutation point
        int mutationPoint = rn.nextInt(population.individuals[0].geneLength);

        //Flip values at mutation point
        if (fittest.genes[mutationPoint]==0) {
            fittest.genes[mutationPoint] = 1;
        } else {
            fittest.genes[mutationPoint] = 0;
        }

        mutationPoint = rn.nextInt(population.individuals[0].geneLength);

        if (secondFittest.genes[mutationPoint]== 0) {
            secondFittest.genes[mutationPoint]= 1;
        } else {
            secondFittest.genes[mutationPoint]= 0;
        }

    }


    //get fittest offspring
    Individual getFittestOffspring(){

        if(fittest.fitness > secondFittest.fitness){
            return fittest;
        }
        return secondFittest;
    }


    //Replace least fittest individual from most fittest offspring
    void addFittestOffspring(){

        //Update fite=ness value of offspring
        fittest.calcFitness();
        secondFittest.calcFitness();

        //get index of least fittest individual
        int leastFittestIndex = population.getLeastFittestIndex();

        //Replace least fittest individual from most fittest offspring
        population.individuals[leastFittestIndex] = getFittestOffspring();
    
    }

}

    // System.out.println("hello");

//Individual class
class Individual {

    int fitness = 0;
    int[] genes = new int[5];
    int geneLength = 5;

    public Individual() {
        Random rn = new Random();

        //set genes randomly for each individual
        for(int i=0; i<geneLength; i++){
            genes[i] = Math.abs(rn.nextInt()%2);
        }

        fitness = 0;

    }

    public void calcFitness(){

        fitness = 0;
            for (int i = 0; i < 5; i++) {
                if(genes[i] == 1){
                    ++fitness;
                }
            }
    }
}


//population class
class Population {
    int popSize = 10;
    Individual[] individuals = new Individual[10];

    //initialise population
    public void initializePopulation(int size){

        for(int i=0; i<individuals.length; i++){
            individuals[i] = new Individual();
        }
    }


    //get the fittest individual
    public Individual getFittest() {
            
        int maxFit = Integer.MIN_VALUE;
        int maxFitIndex = 0;

        for (int i = 0; i < individuals.length; i++) {
            if (maxFit<= individuals[i].fitness) {
                maxFit = individuals[i].fitness;
                maxFitIndex = i;
            }
        }

        fittest = individuals[maxFitIndex].fitness;
        return individuals[maxFitIndex];
    }


    //Get second most fit individual
    public Individual getSecondFittest() {

        int maxFit1 =0;
        int maxFit2 =0;
        
        for (int i = 0; i < individuals.length; i++) {
            if(individuals[i].fitness > individuals[maxFit1].fitness){
                maxFit2 = maxFit1;
                maxFit1 = i;
            }
            else if(individuals[i].fitness > individuals[maxFit2].fitness){
                maxFit2 = i;
            }
        }

        return individuals[maxFit2];
    }


    //get index of least fit individual
    public int getLeastFittestIndex() {

        int minFitVal = Integer.MAX_VALUE;
        int minFitIndex = 0;

        for (int i = 0; i < individuals.length; i++) {
            if(minFitVal >= individuals[i].fitness){
                minFitVal = individuals[i].fitness;
                minFitIndex = i;
            }
        }

        return minFitIndex;
    }


    //calculate fitness of each individual
    public void calculateFitness() {

        for (int i = 0; i < individuals.length; i++) {
            individuals[i].calcFitness();
        }

        getFittest();
    }

}
