//package wordengine.statsservice;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.TreeSet;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public class StatsService {
  // constants
  private static final String LARGE_DICT_FILENAME = "../../../resources/BETAPOSLARGE";
  private static final int MAX_WORD_LENGTH = 50;
  private static final int TOP_N_WORDS = 5;
  private static final int ALPHABET_SIZE = 26;

  private static final HashSet<String> partsOfSpeech = new HashSet<String>(Arrays.asList(
        "NOUN", "VERB", "ADJ", "ADV", "PRON", "DET", "ADP", "NUM", "CONJ", "PART", ".", "X"));

  private final ArrayList<HashMap<String,ArrayList<Word>>> masterDictionary =
      new ArrayList<HashMap<String,ArrayList<Word>>>();

  public static void main(String[] args) throws InterruptedException {
    long start = System.currentTimeMillis();
    StatsService test = new StatsService();
    long stop = System.currentTimeMillis();
    System.out.println((stop - start) + " loading time in ms");

    start = System.currentTimeMillis();
    for (int i = 0; i < args.length; i+=2) {
      List<String> words = test.getTopNWords(args[i], args[i+1]);
      System.out.println(test.getRandomWord(words));
      stop = System.currentTimeMillis();
      System.out.println((stop - start) + " calculation time in ms");
    }


  }

  public StatsService() {
    for (int i = 0; i <= MAX_WORD_LENGTH; i++) {
      HashMap<String, ArrayList<Word>> partOfSpeechSplits = 
          new HashMap<String, ArrayList<Word>>(20, 0.95F);
      for (String part: partsOfSpeech) {
        partOfSpeechSplits.put(part, new ArrayList<Word>());
      }
      partOfSpeechSplits.put("ALL", new ArrayList<Word>());
      masterDictionary.add(partOfSpeechSplits);
    }

    String[] currentSplitLine;
    Word currentNewWord;

    try {
      BufferedReader br = new BufferedReader(new FileReader(LARGE_DICT_FILENAME));
      while (br.ready()) {                  //input whole file
        currentSplitLine = br.readLine().split("\\s+");           //splits at whitespace
        currentNewWord = new Word(currentSplitLine[0]);
        long lineTotal = 0;

        if ((currentSplitLine.length >= 3) && ((currentSplitLine.length % 2) == 1)
            && (currentSplitLine[0].length() <= MAX_WORD_LENGTH)) {
          for (int i = 1; i < currentSplitLine.length; i+=2) {
            lineTotal += Long.parseLong(currentSplitLine[i+1]);
          }
          currentNewWord.addPartOfSpeech("ALL", lineTotal);
          masterDictionary
            .get(currentSplitLine[0].length())
            .get("ALL")
            .add(currentNewWord);

          for (int i = 1; i < currentSplitLine.length; i+=2) {
            long currentCount = Long.parseLong(currentSplitLine[i+1]);
            if (((double) currentCount / (double) lineTotal) > 0.1) {
              currentNewWord.addPartOfSpeech(currentSplitLine[i], currentCount);
              masterDictionary
                .get(currentSplitLine[0].length())
                .get(currentSplitLine[i])
                .add(currentNewWord);
            }
          }
        } else {
        }
      }
    } catch(IOException e) {
      System.out.println(e);
    }

    // sort every dictionary
    for (int i = 0; i < MAX_WORD_LENGTH; i++) {
      for (String part: partsOfSpeech) {
        for (Word cw: masterDictionary.get(i).get(part)) {
          cw.setSortBy(part);
        }
        if (masterDictionary.get(i).get(part).size() > 0) {
          Collections.sort(masterDictionary.get(i).get(part));
        }
      }
      for (Word cw: masterDictionary.get(i).get("ALL")) {
        cw.setSortBy("ALL");
      }
      if (masterDictionary.get(i).get("ALL").size() > 0) {
        Collections.sort(masterDictionary.get(i).get("ALL"));
      }
    }
  }

  public String getRandomWord(List<String> words) {
    Random random = new Random();
    return words.get(random.nextInt(words.size()));
  }

  @WebMethod
  public List<String> getTopNWords(String partialRequest, String part) {
    Word partial = new Word(partialRequest);

    ArrayList<Word> topWords = new ArrayList<Word>();
    for (int i = 0; i < TOP_N_WORDS; i++) {
      topWords.add(new Word(("" + ((char) (i + 65))), i));
    }
    predictiveNWords(partial, topWords, part);

    ArrayList<String> topNWords = new ArrayList<String>();
    for (Word cw: topWords) {
      topNWords.add(cw.getWord());
    }
    return topNWords;
  }

  private void predictiveNWords(Word partial, ArrayList<Word> topWords, String part) {
    ArrayList<Word> vocab = masterDictionary.get(partial.getLength()).get(part);
    topWords.clear();

    int i = 0;
    for (Word cw: vocab) {        //for every Word in the vocab
      if (compatible(cw, partial)) {
        topWords.add(cw);
        i++;
        if (i == TOP_N_WORDS) {
          break;
        }
      }
    }
  }

  /* method checks if given Word is compatible with partial Word*/
  private boolean compatible(Word w, Word partial) {
    for (int i = 0; i < partial.getLength(); i++) {
      if ((partial.letterAt(i) != ' ') &&
          (partial.letterAt(i) != w.letterAt(i))) {
        return false;
      }
    }
    return true;
  }
}
