package data;

import java.io.Serializable;

/**
 * @author zhengxiong
 *
 *         The DnaCount class for count for the DNA charaters. Used for merge the data and select
 *         the characters.
 */
public class DnaCount implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = -6162772431408036109L;

  private int[] count;

  /**
   * constructor
   */
  public DnaCount() {
    count = new int[4];
  }

  /**
   * @return counter
   */
  public int[] getCount() {
    return this.count;
  }

  /**
   * @param count
   *          add a new count
   */
  public void addCount(DnaCount count) {
    int[] nums = count.getCount();

    for (int i = 0; i < this.count.length; i++) {
      this.count[i] += nums[i];
    }
  }

  /**
   * @param index
   *          add count by one
   */
  public void addDna(int index) {
    this.count[index]++;
    // System.out.println("-" + this.count[index]);
  }

  /**
   * @return selected dna character number
   */
  public int getDna() {
    int max = -1, dna = -1;

    for (int i = 0; i < this.count.length; i++) {
      // System.out.print(this.count[i] + "\t");

      if (this.count[i] > max) {
        max = this.count[i];
        dna = i;
      }
    }

    // System.out.println("\t" + dna);
    return dna;
  }
}