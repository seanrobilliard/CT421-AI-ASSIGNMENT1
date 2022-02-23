import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Knapsack {




        static int stringLength = 24;
        static int populationSize = 100;
        static int iterations = 10;
        static double mutationRate = 0.5;
        static int mutation = 1;
        static int knapsackCapacity = 6404180;




      static ArrayList<Integer > weights =new ArrayList<Integer>();
      static ArrayList<Integer > values =new ArrayList<Integer>();
      private static Integer[] bestString;



        static double crossoverRate = 0.9;
        // 2d array for population of individuals all with 30 for their length.
        public static Integer[][] population = new Integer[populationSize][stringLength];
        //public static Integer[][] children = new Integer[populationSize][stringLength];

        public static void main(String[] args) throws FileNotFoundException {

            populateWeights();
            populateValues();
            printWandV();
            intilizePopulation(population); //init population
            System.out.print("generation 1 :"); //print out info about first generation
            intilizePopulation(population);
            //print2D(population);

            //set up 10 iterations of generations
            for (int x = 0; x <= iterations; x++) {
                //finds position in array of best 2 individuals for next population
                Integer[] Parents = findParents();

                for (int i = 0; i < populationSize; i++) {
                    if (i == Parents[0] || i == Parents[0])
                        continue; //Don't let parents die to keep population fitness increasing
                    if (Math.random() < crossoverRate) {
                        Integer child[] = crossOver(population[Parents[0]], population[Parents[1]]); //child is the crossover between parents
                        if (Math.random() < mutationRate) {  //at random mutate child
                            mutateChild(child);
                            if (findFitness(population[i]) < findFitness(child)) population[i] = child;


                        }

                    }


                }
                System.out.print("generation " +x + " :");  //print out info regarding generations
                averageFitness(population);

                  System.out.println("Best string =");
                  print1D(bestString);
                System.out.println("With value =  " +findFitness(bestString)  + "with weight = "  +findWeight(bestString))  ;



            }

        }

    private static void printWandV() {

            for (int i =0 ; i< 24 ;i++)
            {

                System.out.println("Weight = " + weights.get(i)+ "Values = " + values.get(i) );
            }
    }

    private static void populateWeights() throws FileNotFoundException {  //poulate weights list by reading in from txt file 

        File myObj = new File("weights.txt");
        Scanner myReader = new Scanner(myObj);
        while (myReader.hasNextLine()) {
            String data = myReader.nextLine();
            weights.add(Integer.valueOf(data));
        }
        myReader.close();
    }



    private static void populateValues() throws FileNotFoundException {  //populate values list by reading in from txt file

        File myObj = new File("values.txt");
        Scanner myReader = new Scanner(myObj);
        while (myReader.hasNextLine()) {
            String data = myReader.nextLine();
            values.add(Integer.valueOf(data));
        }
        myReader.close();
    }







    private static void averageFitness(Integer[][] population) {  //average fitness calculation method
            int fitnessAv=0;
            int weightAv=0;
            int average =0;

            int waverage=0;
            int best = 0;

            for (int i = 0; i < populationSize; i++) {
                weightAv= findWeight(population[i]);
                fitnessAv = findFitness(population[i]);

                 if ( fitnessAv > best ) bestString = population[i];

                waverage+= weightAv;
                average += fitnessAv;
            }


            waverage = waverage/populationSize;
            average=average/populationSize;

            System.out.println("Average fitness = "  +average  + " Average Weight  "  +waverage);

        }





        private static void mutateChild(Integer[] child) {
            for (Integer i = 0; i < mutation; i++) {

                int range = (23 - 1) + 1;
                int gene = (int) (Math.random() * range) + 1;
                child[gene] = (child[gene] == 0 ? 1 : 0);
            }

        }

        private static Integer[] crossOver(Integer[] parent1, Integer[] parent2) {

            Integer child[] = new Integer[stringLength];
            int range = (24 - 1) + 1;
            int crossoverlocation = (int) (Math.random() * range) + 1;
            for (int i = 0; i < crossoverlocation; i++) child[i] = parent1[i];
            for (int i = crossoverlocation; i < stringLength; i++) child[i] = parent2[i];
            return child;

        }


        private static Integer[] findParents() {
//survivl of the fittest the best survive

            Integer parents[] = new Integer[2];
            parents[0] = 0;
            parents[1] = 0;


            //for parent 1 find highest fitness individual and make this the parent

            for (Integer i = 0; i < populationSize; i++) {
                if (findFitness(population[i]) > findFitness(population[parents[0]])) parents[0] = i;
            }


            //for parent 2 find highest fitness individual that is not parent 1  and make this the parent

            for (Integer i = 0; i < populationSize; i++) {
                if (i == parents[0]) continue;
                if (findFitness(population[i]) > findFitness(population[parents[1]])) parents[1] = i;
            }
            return parents;


        }


        //Create a random piopulation of 100 strings of 30 length
        private static void intilizePopulation(Integer[][] population) {

            for (int i = 0; i < populationSize; i++) {
                for (int j = 0; j < stringLength; j++) {
                    population[i][j] = (Math.random() < 0.5 ? 1 : 0);

                }

            }


        }

        public static int findWeight(Integer test [] )
        {
            int totalWeight=0;
            for (Integer i = 0; i < stringLength; i++) {
                if (test[i] ==1 ) {
                    int weight = weights.get(i);
                    totalWeight += weight;
                }
            }
            return totalWeight;
        }




        //fitness is equal to the number of 1's in the string
        public static Integer findFitness(Integer gen[]) {
            Integer scr = 0;
            int totalWeight = findWeight(gen);
            for (Integer i = 0; i < stringLength; i++) {

                if (gen[i] ==1 ) {

                    int value = values.get(i);
                    scr += value;

                }


            }

            if (totalWeight > knapsackCapacity)
            {
                scr =0;
                return scr;

            }
            return scr;
        }


        public static void print2D(Integer[][] mat) {
            for (int x = 0; x < 100; x++) {
                for (int i = 0; i < 30; i++) {

                    System.out.print(mat[x][i]);
                }
                System.out.println("");

            }

        }


    public static void print1D(Integer[] mat) {

        for (int i = 0; i < 24; i++) {

            System.out.print(mat[i]);
        }
        System.out.println("");

    }
    }

