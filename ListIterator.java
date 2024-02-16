import java.util.HashMap;
import java.util.Random;

public class LanguageModel {
    HashMap<String, List> CharDataMap;
    int windowLength;
    Random randomGenerator;

    public LanguageModel(int windowLength, int seed) {
        this.windowLength = windowLength;
        randomGenerator = new Random(seed);
        CharDataMap = new HashMap<>();
    }

    public LanguageModel(int windowLength) {
        this.windowLength = windowLength;
        randomGenerator = new Random();
        CharDataMap = new HashMap<>();
    }

    public void trainOnText(String text) {

    }

    public void calculateProbabilities(List probs) {
    }

    public char getRandomChar(List probs) {
       
    }

    public String generate(String initialText, int textLength) {
        StringBuilder generatedText = new StringBuilder(initialText);

        while (generatedText.length() < textLength) {
            String currentWindow = generatedText.substring(generatedText.length() - windowLength);
            if (CharDataMap.containsKey(currentWindow)) {
                List probabilities = CharDataMap.get(currentWindow);
                char randomChar = getRandomChar(probabilities);
                generatedText.append(randomChar);
            } else {

                break;
            }
        }

        return generatedText.toString();
    }

    public String toString() {
        StringBuilder str = new StringBuilder();
        for (String key : CharDataMap.keySet()) {
            List keyProbs = CharDataMap.get(key);
            str.append(key).append(" : ").append(keyProbs).append("\n");
        }
        return str.toString();
    }

    public static void main(String[] args) {
        LanguageModel model = new LanguageModel(3, 42);
        model.trainOnText("your_corpus_text");

        System.out.println("Model: \n" + model);

        String generatedText = model.generate("initial", 100);
        System.out.println("Generated Text: \n" + generatedText);
    }
}
