import java.util.HashMap;
import java.util.Random;

public class LanguageModel {
    HashMap<String, List> CharDataMap;
    int windowLength;
    private Random randomGenerator;

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

    public void train(String fileName) {
        try {
            FileInputStream fis = new FileInputStream(fileName);
            Scanner scanner = new Scanner(fis);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                // Process the line for training
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void calculateProbabilities(List probs) {
        double total = 0;
     for (int i = 0; i < probs.getSize(); i++) {
        CharData charData = probs.get(i);
        total += charData.getCount();
     }
     double cumulativeProbability = 0;
     for (int i = 0; i < probs.getSize(); i++) {
        CharData charData = probs.get(i);
        double probability = charData.getCount() / total;
        cumulativeProbability += probability;
        charData.setProbability(probability);
        charData.setCumulativeProbability(cumulativeProbability);
        }
    }

    public char getRandomChar(List probs) {
        double randomValue = randomGenerator.nextDouble();
        for (int i = 0; i < probs.getSize(); i++) {
            CharData charData = probs.get(i);
            if (randomValue <= charData.getCumulativeProbability()) {
                return charData.getChar();
            }
        }
        return ' ';
    }

    public String generate(String initialText, int textLength) {
        StringBuilder generatedText = new StringBuilder(initialText);
        for (int i = 0; i < textLength; i++) {
            String window = generatedText.substring(Math.max(0, generatedText.length() - windowLength));
            List probs = CharDataMap.get(window);
            if (probs != null) {
                char nextChar = getRandomChar(probs);
                generatedText.append(nextChar);
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
            str.append(key + " : " + keyProbs.toString() + "\n");
        }
        return str.toString();
    }

    public static void main(String[] args) {
        LanguageModel model = new LanguageModel(3);
        model.train("path/to/your/text/file.txt");
        model.calculateProbabilities(); 
        String generatedText = model.generate("The", 100);
        System.out.println(generatedText);
    }
}
