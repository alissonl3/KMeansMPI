package data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhengxiong
 *
 *         The Data Point class for Point Kmeans.
 */
public class DataPoint implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 5840159819051935664L;

  private List<Double> values;

  private int group;

  private int cnt;

  /**
   * @param values
   *          d-dimension point data
   */
  public DataPoint(List<Double> values) {
    this.values = new ArrayList<Double>(values);
    this.group = -1;
    this.cnt = 0;
  }

  /**
   * @param group
   *          the cluster index
   */
  public void setGroup(int group) {
    this.group = group;
  }

  /**
   * @return the cluster index
   */
  public int getGroup() {
    return this.group;
  }

  /**
   * @param i
   *          the dimension
   * @returnget i-dimension value
   */
  public double getValue(int i) {
    return this.values.get(i);
  }

  /**
   * @param i
   *          the dimension
   * @param element
   *          the new value
   */
  public void setValue(int i, double element) {
    this.values.set(i, element);
  }

  /**
   * @return the data dimension
   */
  public int size() {
    return this.values.size();
  }

  /**
   * @param point
   *          add a point
   */
  public void addDataPoint(DataPoint point) {
    if (size() != point.size()) {
      System.out.println("add error...");
      return;
    }

    for (int i = 0; i < size(); i++) {
      this.values.set(i, this.values.get(i) + point.getValue(i));
    }
  }

  /**
   * @param num
   *          divide a number
   */
  public void divide(double num) {
    for (int i = 0; i < size(); i++) {
      this.values.set(i, this.values.get(i) / num);
    }
  }
  
  public void addCnt() {
    this.cnt++;
  }

  /**
   * @return
   */
  public void addCnt(int cnt) {
    this.cnt += cnt;
  }
  
  public int getCnt() {
    return this.cnt;
  }
}
