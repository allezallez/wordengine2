//package wordengine.statsservice;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

public class Word implements Comparable<Word> {

  private static final HashSet<String> partsOfSpeech = new HashSet<String>(Arrays.asList(
      "ALL", "NOUN", "VERB", "ADJ", "ADV", "PRON", "DET", "ADP", "NUM", "CONJ", "PART", ".", "X"));

  private String sortBy = "ALL";
  private String thisWord;
  private HashMap<String, Long> partOfSpeechAndCounts;

  Word() {
    this.thisWord = "";
    this.partOfSpeechAndCounts = new HashMap<String, Long>();
    initializePartsOfSpeech();
  }

  Word(String Word_) {
    this.thisWord = Word_;
    this.partOfSpeechAndCounts = new HashMap<String, Long>();
    initializePartsOfSpeech();
  }

  Word(String Word_, long count) {
    this.thisWord = Word_;
    this.partOfSpeechAndCounts = new HashMap<String, Long>();
    initializePartsOfSpeech();
  }

  public double getPartOfSpeechPercent(String part) {
    return ((double) this.partOfSpeechAndCounts.get(part) 
        / (double) this.partOfSpeechAndCounts.get("ALL"));
  }

  public HashMap<String, Long> getPartOfSpeechMap() {
    return this.partOfSpeechAndCounts;
  }

  public char letterAt(int index) {
    return this.thisWord.charAt(index);
  }

  public void addPartOfSpeech(String part, long partCount) {
    this.partOfSpeechAndCounts.put(part, partCount);
  }

  public long getPartOfSpeechCount(String part) {
    return this.partOfSpeechAndCounts.get(part);
  }

  public int getLength() { 
    return this.thisWord.length(); 
  }

  public void setWord(String newWord) { 
    this.thisWord = newWord; 
  }

  public String getWord() { 
    return this.thisWord; 
  }

  private void initializePartsOfSpeech() {
    partOfSpeechAndCounts.put("ALL", 0L);
  }

  public void setSortBy(String part) {
    if (!partsOfSpeech.contains(part)) {
      System.out.println("Error, can no sort by " + part);
      return;
    }
    this.sortBy = part;
  }

  public int compareTo(Word o) {
    if (this.getPartOfSpeechCount(sortBy) > o.getPartOfSpeechCount(sortBy)) {
      return -1;
    }
    if (this.getPartOfSpeechCount(sortBy) == o.getPartOfSpeechCount(sortBy)) {
      return 0;
    }
    return 1;
  }
}
