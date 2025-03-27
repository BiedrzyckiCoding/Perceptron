import java.security.spec.RSAOtherPrimeInfo;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        //read a training set and a test set using FileReader methods.
        FileReader.DataSet trainData = FileReader.readCSV("resources/perceptron.data");
        FileReader.DataSet testData  = FileReader.readCSV("resources/perceptron.test.data");

        //initialize a Perceptron
        //automatically set the number of weights to match the dimension of our training data's feature vectors.
        int dimension = trainData.data.get(0).size();
        ArrayList<Double> initialWeights = new ArrayList<>();
        Random rand = new Random();
        for (int i = 0; i < dimension; i++) {
            initialWeights.add(rand.nextDouble()); //random [0,1]
        }
        System.out.println("initial weights: " + initialWeights);
        double initialBias = rand.nextDouble(); //random [0,1]
        double learningRate = 0.01; //small learning rate

        Perceptron perceptron = new Perceptron(initialWeights, initialBias, learningRate);

        //train the perceptron on the training data
        int epochs = 50;
        perceptron.train(trainData.data, trainData.labels, epochs);

        //evaluate on the test set
        int correctCount = 0;
        for (int i = 0; i < testData.data.size(); i++) {
            ArrayList<Double> x = testData.data.get(i);
            int label = testData.labels.get(i);
            int prediction = perceptron.output(x);
            System.out.println(prediction);
            if (prediction == label) {
                correctCount++;
            }
        }
        double accuracy = 100.0 * correctCount / testData.data.size();
        System.out.println("Accuracy on test set: " + accuracy + "%");

        Scanner scanner = new Scanner(System.in);
        System.out.println("\nEnter a custom input vector of size " + dimension +
                " (7.0,3.2,4.7,1.4), separated by a coma:");

        //read the entire line
        String line = scanner.nextLine().trim();
        //split by whitespace
        String[] parts = line.split(",");

        //parse into a vector
        ArrayList<Double> userVector = new ArrayList<>();
        for (String p : parts) {
            userVector.add(Double.parseDouble(p));
        }

        //make sure user gave correct dimensionality
        if (userVector.size() != dimension) {
            System.out.println("Error: Expected " + dimension + " values, but got " + userVector.size());
        } else {
            //compute perceptron output
            int userPrediction = perceptron.output(userVector);
            System.out.println("Perceptron output for your input: " + userPrediction);
        }

        //Gui gui = new Gui();
    }
}
