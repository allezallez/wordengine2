package wordengine.statsservice;

public class Word implements Comparable<Word> {

  private int count;         //population count:
  private double posterior;  //posterior odds (will be updated many times)
  private String theWord;   //this Word

  Word() {
    this.count = 0;
    this.theWord = "";
  }

  Word(String Word_, int count_) {
    this.count = count_;
    this.theWord = Word_;
  }

  Word(String Word_, int count_, double posterior_) {
    this.count = count_;
    this.theWord = Word_;
    this.posterior = posterior_;
  }

  public int getLength() { 
    return theWord.length(); 
  }

  public char letterAt(int index) { 
    return theWord.charAt( index ); 
  }

  public void setWord(String newWord) { 
    this.theWord = newWord; 
  }

  public String getWord() { 
    return theWord; 
  }

  public int getCount() { 
    return count; 
  }

  public void setCount(int count_) { 
    this.count = count_; 
  }

  public double getPosterior() { 
    return posterior; 
  }

  public void setPosterior( double posterior_ ) {
    this.posterior = posterior_;
  }

  public void setLetterAt(char letter, int index) {
    String temp = theWord.substring(0, index) + letter + theWord.substring(index + 1);
    this.theWord = temp;
  }

  public int compareTo(Word other) {
    if (this.getPosterior() > other.getPosterior()) 
      return -1;
    if (this.getPosterior() == other.getPosterior()) 
      return 0;
    return 1;
  }
}
