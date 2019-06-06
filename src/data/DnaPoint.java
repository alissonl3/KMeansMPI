package data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhengxiong
 *
 *         Dna Point for DNA Kmeans
 */
public class DnaPoint implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 6127376335026511405L;

  private List<Integer> values;

  private List<DnaCount> cnts;

  private int group;

  /**
   * @param values
   *          set values
   */
  public DnaPoint(List<Integer> values) {
    this.values = new ArrayList<Integer>(values);
    this.group = -1;
  }

  /**
   * @param values
   *          d-dimension values
   * @param cnts
   *          counters
   */
  public DnaPoint(List<Integer> values, List<DnaCount> cnts) {
    this.values = new ArrayList<Integer>(values);
    this.cnts = new ArrayList<DnaCount>(cnts);
    this.group = -1;
  }

  /**
   * @param group
   *          cluster number
   */
  public void setGroup(int group) {
    this.group = group;
  }

  /**
   * @return cluster number
   */
  public int getGroup() {
    return this.group;
  }

  /**
   * @return values
   */
  public List<Integer> getValues() {
    return this.values;
  }

  /**
   * @param i
   *          i-dimension
   * @return i-dimension value
   */
  public int getValue(int i) {
    return this.values.get(i);
  }

  /**
   * @param i
   *          dimension
   * @param element
   *          new value
   */
  public void setValue(int i, int element) {
    this.values.set(i, element);
  }

  /**
   * @return dimension
   */
  public int size() {
    return this.values.size();
  }

  /**
   * @param point
   *          merge the Point
   */
  public void convergeDnaPoint(DnaPoint point) {
    if (size() != point.size()) {
      System.out.println("add error...");
      return;
    }

    List<DnaCount> newCount = point.getCounts();

    for (int i = 0; i < size(); i++) {
      this.cnts.get(i).addCount(newCount.get(i));
    }
  }

  /**
   * @return counterss
   */
  public List<DnaCount> getCounts() {
    return this.cnts;
  }

  /**
   * @param cnts
   *          set counters
   */
  public void setCounts(List<DnaCount> cnts) {
    this.cnts = new ArrayList<DnaCount>(cnts);
  }

  /**
   * @param point
   *          add counters
   */
  public void addDnaPoint(DnaPoint point) {
    if (size() != point.size()) {
      System.out.println("add error ....");
      return;
    }

    for (int i = 0; i < size(); i++) {
      // System.out.print(point.getValue(i));
      this.cnts.get(i).addDna(point.getValue(i));
    }
  }

  /**
   * select dna values
   */
  public void updateValue() {
    for (int i = 0; i < size(); i++) {
      this.values.set(i, this.cnts.get(i).getDna());
    }
  }
}
