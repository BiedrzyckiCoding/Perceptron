import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Gui {

    public Gui() {
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        //prompt for training file path
        System.out.print("Enter the training data file path (e.g. perceptron.data): ");
        String trainPath = sc.nextLine();

        //prompt for test file path
        System.out.print("Enter the test data file path (e.g. perceptron.test.data): ");
        String testPath = sc.nextLine();

        //prompt for learning rate
        System.out.print("Enter the learning rate (e.g. 0.01): ");
        double learningRate = sc.nextDouble();

        //prompt for number of epochs
        System.out.print("Enter the number of epochs (e.g. 1000): ");
        int epochs = sc.nextInt();

        //load training data
        FileReader.DataSet trainData = FileReader.readCSV(trainPath);
        ArrayList<ArrayList<Double>> trainX = trainData.data;
        ArrayList<Integer> trainY = trainData.labels;

        //initialize random weights
        int dimension = trainX.get(0).size();
        ArrayList<Double> initialWeights = new ArrayList<>();
        Random rand = new Random();
        for (int i = 0; i < dimension; i++) {
            initialWeights.add(rand.nextDouble());
        }
        double initialBias = rand.nextDouble();

        //create the Perceptron
        Perceptron perceptron = new Perceptron(initialWeights, initialBias, learningRate);

        //train the Perceptron
        perceptron.train(trainX, trainY, epochs);

        //load test data
        FileReader.DataSet testData = FileReader.readCSV(testPath);
        ArrayList<ArrayList<Double>> testX = testData.data;
        ArrayList<Integer> testY = testData.labels;

        //evaluate on test data
        int correctCount = 0;
        for (int i = 0; i < testX.size(); i++) {
            int pred = perceptron.output(testX.get(i));
            if (pred == testY.get(i)) {
                correctCount++;
            }
        }
        double accuracy = 100.0 * correctCount / testX.size();
        System.out.println("\nAccuracy on test set: " + accuracy + "%");

        //simple UI for manual input
        System.out.println("\nEnter vectors to classify (comma-separated), or type 'quit' to exit.");
        sc.nextLine(); //consume leftover newline

        while (true) {
            System.out.print("Enter a vector: ");
            String line = sc.nextLine();
            if (line.equalsIgnoreCase("quit")) {
                break;
            }
            String[] parts = line.split(",");
            if (parts.length != dimension) {
                System.out.println("Please enter exactly " + dimension + " features.");
                continue;
            }
            //parse the features
            ArrayList<Double> newVec = new ArrayList<>();
            try {
                for (String part : parts) {
                    newVec.add(Double.parseDouble(part.trim()));
                }
                int prediction = perceptron.output(newVec);
                System.out.println("Predicted class: " + prediction);
            } catch (NumberFormatException e) {
                System.out.println("Error: all features must be numeric. Try again.");
            }
        }

        sc.close();
    }
}
