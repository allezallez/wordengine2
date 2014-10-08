package wordengine.statsservice;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.TreeSet;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public class StatsService {
  // constants
  private static final String LARGE_DICT_FILENAME = "../../../resources/LARGE";
  private static final int MAX_WORD_LENGTH = 90;
  //private static final int MIN_WORD_LENGTH = 1;
  private static final int TOP_N_WORDS = 10;
  private static final int ALPHABET_SIZE = 26;

  //private final HashMap<Integer, ArrayList<Word>> dictionary = new HashMap<Integer, ArrayList<Word>>();
  private final ArrayList<ArrayList<Word>> masterDictionary = new ArrayList<ArrayList<Word>>();

//  public static void main(String[] args) {
//    StatsService test = new StatsService();

//    ArrayList<Double> stats = (ArrayList<Double>) test.getLetterStats(" A A A");

//    for (char i = 'A'; i <= 'Z'; i++) {
//      System.out.println(i + ": " + stats.remove(0));
//    }
//  }

  public StatsService() {
    for (int i = 0; i < MAX_WORD_LENGTH; i++) {
      //dictionary.put(i, new ArrayList<Word>());
      masterDictionary.add(new ArrayList<Word>());
    }

    String[] aLine;

    try {
      BufferedReader br = new BufferedReader(new FileReader(LARGE_DICT_FILENAME));
      while (br.ready()) {                  //input whole file
        aLine = br.readLine().split("\\s+");           //splits at whitespace
        //dictionary.get(aLine[0].length()).add(new Word(aLine[0], Integer.parseInt(aLine[2])));
        masterDictionary.get(aLine[0].length()).add(new Word(aLine[0], Integer.parseInt(aLine[2])));
      }
    } catch(IOException e) {
      System.out.println(e);
    }

    System.out.println("done loading");
  }

  @WebMethod
  public List<String> getTopNWords(String partialRequest) {
    Word partial = new Word(partialRequest, 0);

    //top Words will hold most likely Words among remaining Words
    //init topWords with very unlikely blanks
    TreeSet<Word> topWords = new TreeSet<Word>();
    for (int i = 0; i < TOP_N_WORDS; i++) {
      topWords.add(new Word(" ", 0, ((double) i / (double) 999999999)));
    }

    predictiveNWords(partial, 'A', topWords);

    ArrayList<String> topNWords = new ArrayList<String>();
    for (Word cw: topWords) {
      topNWords.add(cw.getWord());
    }
    return topNWords;
  }

  @WebMethod
  public List<Double> getLetterStats(String partialRequest) {
    Word partial = new Word(partialRequest, 0);
    ArrayList<Double> letterProbs = new ArrayList<Double>();

    for (char letter = 'A'; letter <= 'Z'; letter++) {    //for each letter in alphabet
      letterProbs.add(predictiveLetter(new ArrayList<Character>(), partial, letter));
    }
    return letterProbs;
  }

  private void predictiveNWords(Word partial, char letter, TreeSet<Word> topWords) {
    HashSet<String> tops = new HashSet<String>();
    //ArrayList<Word> vocab = dictionary.get(partial.getLength());
    ArrayList<Word> vocab = masterDictionary.get(partial.getLength());
    ArrayList<Word> compatibleWords = new ArrayList<Word>();
    ArrayList<Character> guesses = new ArrayList<Character>();

    Word tempWord;
    long sumCount = 0;

    for (Word cw: vocab) {        //for every Word in the vocab
      if (compatible(cw, guesses, partial)) {    //check compatible
        sumCount += cw.getCount();     //add its count to the sum
        compatibleWords.add(cw);
      }
    }

    for (Word cw: compatibleWords) {     //recalc all posterior prob's
      // note: this is extremely not threadsafe, the "words" are shared amongst all users of the data structu
      cw.setPosterior((double) cw.getCount() / (double) sumCount);
      if (!tops.contains(cw.getWord())) {
        tempWord = topWords.last();
        if (cw.getPosterior() >= tempWord.getPosterior()) {
          topWords.remove(tempWord);
          topWords.add(cw);
        }
      }
    }
  }

  private double predictiveLetter(ArrayList<Character> guesses, Word partial, char letter) {
    double sumProb = 0;
    long sumCount = 0;

    //ArrayList<Word> vocab = dictionary.get(partial.getLength());
    ArrayList<Word> vocab = masterDictionary.get(partial.getLength());
    ArrayList<Word> compatibleWords = new ArrayList<Word>();

    for (Word cw: vocab) {        //for every Word in the vocab
      if (compatible(cw, guesses, partial)) {    //check compatible
        sumCount += cw.getCount();     //add its count to the sum
        compatibleWords.add(cw);
      }
    }

    for (Word cw: compatibleWords) {     //recalc all posterior prob's
      cw.setPosterior((double) cw.getCount() / (double) sumCount);
      for (int i = 0; i < partial.getLength(); i++) {
        if ((partial.letterAt(i) == ' ') && (cw.letterAt(i) == letter)) {
          sumProb += cw.getPosterior();
          break;
        }
      }
    }
    return sumProb;
  }

  /* method checks if given Word is compatible with partial Word*/
  private boolean compatible(Word w, ArrayList<Character> guesses, Word partial) {
    for (int i = 0; i < partial.getLength(); i++) {
      if ((partial.letterAt(i) != ' ') &&
          (partial.letterAt(i) != w.letterAt(i))) {
        return false;
      } else {
        for (Character guess : guesses) {
          if (((char) guess) == w.letterAt(i))
            return false;
        }
      }
    }
    return true;
  }
}
