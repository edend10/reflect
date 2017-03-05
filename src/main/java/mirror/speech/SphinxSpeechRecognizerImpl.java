package mirror.speech;

import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;
import edu.cmu.sphinx.api.SpeechResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SphinxSpeechRecognizerImpl implements SpeechRecognizer {

    private static final Logger LOGGER = LoggerFactory.getLogger(SphinxSpeechRecognizerImpl.class);

    public SphinxSpeechRecognizerImpl() {

    }

    @Override
    public void recognize() {
        Configuration configuration = new Configuration();
        configuration.setAcousticModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us");
        configuration.setDictionaryPath("resource:/edu/cmu/sphinx/models/en-us/cmudict-en-us.dict");
        configuration.setLanguageModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us.lm.bin");
        System.out.println("sample rate: " + configuration.getSampleRate());
        try {
            LiveSpeechRecognizer recognizer = new LiveSpeechRecognizer(configuration);
            System.out.println("starting");
            recognizer.startRecognition(true);
            long startTime = System.currentTimeMillis();
            while(true) {
                SpeechResult result = recognizer.getResult();
                String utterance = result.getHypothesis();
                System.out.println(utterance);
                if (utterance.contains("")) {
                    System.out.println("mirror!!!!!");
                    System.out.println("stopping");
                    recognizer.stopRecognition();
                    System.out.println("starting");
                    recognizer.startRecognition(true);
                    startTime = System.currentTimeMillis();
                } else if (utterance.contains("stop")) {
                    break;
                }

                long now = System.currentTimeMillis();
                if (now - startTime > 5000) {
                    System.out.println("stopping");
                    recognizer.stopRecognition();
                    System.out.println("starting");
                    recognizer.startRecognition(true);
                }
            }
            recognizer.stopRecognition();
        } catch (Exception e) {
            LOGGER.error("Speech recognizer error:", e);
        }
    }
}
