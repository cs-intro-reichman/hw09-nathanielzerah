import java.util.HashMap;
import java.util.Random;

public class LanguageModel {

    // The map of this model.
    // Maps windows to lists of charachter data objects.
    HashMap<String, List> CharDataMap;
    
    // The window length used in this model.
    int windowLength;
    
    // The random number generator used by this model. 
	private Random randomGenerator;

    /** Constructs a language model with the given window length and a given
     *  seed value. Generating texts from this model multiple times with the 
     *  same seed value will produce the same random texts. Good for debugging. */
    public LanguageModel(int windowLength, int seed) {
        this.windowLength = windowLength;
        randomGenerator = new Random(seed);
        CharDataMap = new HashMap<String, List>();
    }

    /** Constructs a language model with the given window length.
     * Generating texts from this model multiple times will produce
     * different random texts. Good for production. */
    public LanguageModel(int windowLength) {
        this.windowLength = windowLength;
        randomGenerator = new Random();
        CharDataMap = new HashMap<String, List>();
    }

    /** Builds a language model from the text in the given file (the corpus). */
	public void train(String fileName) {
		In in = new In(fileName); 
        String text = in.readAll(); 
        in.close();

        for (int i = 0; i <= text.length() - windowLength; i++) {
            String window = text.substring(i, i + windowLength);
            char nextChar = i + windowLength < text.length() ? text.charAt(i + windowLength) : ' '; 

            CharDataMap.putIfAbsent(window, new List());
            List charList = CharDataMap.get(window);

            charList.updateCharData(nextChar);
        }
	}

    // Computes and sets the probabilities (p and cp fields) of all the
	// characters in the given list. */
	public void calculateProbabilities(List probs) {				
        double total = 0;
        
        for (int i = 0; i < probs.size(); i++) {
            CharData charData = (CharData) probs.get(i);
            total += charData.frequency;
        }
       
        double cumulativeProbability = 0;
        for (int i = 0; i < probs.size(); i++) {
            CharData charData = (CharData) probs.get(i);
            double probability = charData.frequency / total; 
            cumulativeProbability += probability; 
            charData.probability = probability; 
            charData.cumulativeProbability = cumulativeProbability; 
        }
	}

    // Returns a random character from the given probabilities list.
	public char getRandomChar(List probs) {
        List<CharData> probs = CharDataMap.get(window);
        if (probs == null || probs.isEmpty()) {
            return ' '; 
        }
        double rand = randomGenerator.nextDouble(); 
        double cumulativeProbability = 0.0;
        for (CharData charData : probs) {
            cumulativeProbability += charData.getProbability(); 
            if (rand <= cumulativeProbability) {
                return charData.getCharacter(); 
            }
        }
        return ' ';
	}

    /**
	 * Generates a random text, based on the probabilities that were learned during training. 
	 * @param initialText - text to start with. If initialText's last substring of size numberOfLetters
	 * doesn't appear as a key in Map, we generate no text and return only the initial text. 
	 * @param numberOfLetters - the size of text to generate
	 * @return the generated text
	 */
	public String generate(String initialText, int textLength) {
        StringBuilder generatedText = new StringBuilder(initialText);
        while (generatedText.length() < textLength + initialText.length()) {
            String currentWindow = generatedText.substring(Math.max(0, generatedText.length() - windowLength));
            
            char nextChar = getRandomChar(currentWindow); 
            generatedText.append(nextChar);
        }
        return generatedText.toString();
	}

    /** Returns a string representing the map of this language model. */
	public String toString() {
		StringBuilder str = new StringBuilder();
		for (String key : CharDataMap.keySet()) {
			List keyProbs = CharDataMap.get(key);
			str.append(key + " : " + keyProbs + "\n");
		}
		return str.toString();
	}

    public static void main(String[] args) {
        LanguageModel model = new LanguageModel(3);
    
        model.train("shakespeareinlove.txt");
        model.train("originofspecies.txt");
        
        model.calculateProbabilities();
        
        String generatedText = model.generate("The ", 100); 
        
        System.out.println(generatedText);
    }
}
