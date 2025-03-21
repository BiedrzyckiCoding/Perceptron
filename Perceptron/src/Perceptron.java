import java.util.ArrayList;

public class Perceptron {
    ArrayList<Double> inputVec;
    ArrayList<Double> weights;
    int bias;
    double learningRate;
    double expectedOutput;

    Perceptron(){

    }

    public int netVal(double input){
        int result = 0;
        if(input >= 0){
            result = 1;
        } else if(input < 0) {
            result = -1;
        }
        return result;
    }

    public int output(ArrayList<Double> inputVec, ArrayList<Double> weights, int bias){
        int sum = 0;
        for (int i = 0; i < inputVec.size(); i++){
            sum += inputVec.get(i)*weights.get(i);
        }
        int net = sum - bias;
        return netVal(net);
    }

    public ArrayList<Double> updateWeights(ArrayList<Double> weights, ArrayList<Double> xVector, double learningRate, double expectedOutput, double predictValue){
        double constant = learningRate * (expectedOutput - predictValue);
        for(int i = 0; i < weights.size(); i++){
            weights.set(i, weights.get(i) + xVector.get(i) * constant);
        }
        return weights;
        bias = bias - constant;

    }
}
