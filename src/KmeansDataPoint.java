

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import data.DataPoint;

/**
 * @author zhengxiong
 *
 *         DataPoint handler class
 */
public class KmeansDataPoint {

  private DataPoint[] data;

  private DataPoint[] centroids;

  private int clusterNumber;

  private int dimension;

  // private List<List<DataPoint>> groupInfo;

  /**
   * @param clusterNumber
   *          clusters
   * @param file
   *          input
   */
  public KmeansDataPoint(int clusterNumber, String file) {
    this.clusterNumber = clusterNumber;
    this.centroids = new DataPoint[this.clusterNumber]; // new ArrayList<DataPoint>();
    this.dimension = -1;
    readData(file);
  }

  /**
   * @param file
   *          input
   */
  private void readData(String file) {

    ArrayList<DataPoint> buff = new ArrayList<DataPoint>();

    try {

      FileReader fileReader = new FileReader(file);
      BufferedReader buffReader = new BufferedReader(fileReader);

      String line;
      while ((line = buffReader.readLine()) != null) {

        String[] strs = line.trim().split(",");

        if (this.dimension != strs.length) {
          if (this.dimension == -1) {
            this.dimension = strs.length;
          } else {
            System.out.println("Data Dimension Error...");
            continue;
          }
        }

        List<Double> values = new ArrayList<Double>();
        for (int i = 0; i < strs.length; i++) {
          values.add(Double.parseDouble(strs[i]));
        }

        DataPoint newPoint = new DataPoint(values);
        buff.add(newPoint);
      }

    } catch (IOException e) {
      e.printStackTrace();
    }

    this.data = new DataPoint[buff.size()];
    for (int i = 0; i < buff.size(); i++) {
      this.data[i] = buff.get(i);
    }
  }

  /**
   * centroid initialize
   */
  public void clusterInitialize() {
    List<DataPoint> newData = new ArrayList<DataPoint>();
    for (int i = 0; i < this.data.length; i++) {
      newData.add(this.data[i]);
    }

    Collections.shuffle(newData);
    for (int i = 0; i < this.clusterNumber; i++) {
      this.centroids[i] = newData.get(i);
    }
  }

  /**
   * @param p1
   *          point1
   * @param p2
   *          point2
   * @return distance
   */
  private double calculateDistance(DataPoint p1, DataPoint p2) {
    double dist = 0;

    for (int i = 0; i < p1.size(); i++) {
      dist += Math.pow(p1.getValue(i) - p2.getValue(i), 2);
    }
    return Math.sqrt(dist);
  }

  /**
   * @param start
   * @param end
   */
  public void updateGroup(int start, int end) {
    for (int i = start; i < end; i++) {
      double min = Double.MAX_VALUE;
      int clusterNumber = -1;

      for (int j = 0; j < this.centroids.length; j++) {
        double dist = calculateDistance(this.data[i], this.centroids[j]);

        if (dist < min) {
          min = dist;
          clusterNumber = j;
        }
      }

      this.data[i].setGroup(clusterNumber);
    }
  }

  /**
   * @param start
   * @param end
   * @return
   */
  public DataPoint[] updateCentroid(int start, int end) {
    DataPoint[] newCentroids = new DataPoint[this.clusterNumber];

    List<Double> zeros = new ArrayList<Double>();
    for (int i = 0; i < this.dimension; i++) {
      zeros.add(0.0);
    }

    for (int i = 0; i < this.clusterNumber; i++) {
      newCentroids[i] = new DataPoint(zeros);
    }

    for (int i = start; i < end; i++) {
      int group = this.data[i].getGroup();
      newCentroids[group].addDataPoint(this.data[i]);
      newCentroids[group].addCnt();
    }

    return newCentroids;
  }

  /**
   * @param newCentroids
   */
  public void divide(DataPoint[] newCentroids) {
    for (int i = 0; i < this.clusterNumber; i++) {
      newCentroids[i].divide(newCentroids[i].getCnt());
    }
  }

  /**
   * @param newCentroids
   */
  public void setCentroid(DataPoint[] newCentroids) {
    this.centroids = newCentroids;
  }

  /**
   * @return
   */
  public DataPoint[] getCentroid() {
    return this.centroids;
  }

  /**
   * @param newCentroids
   * @return
   */
  public double calculateDiff(DataPoint[] newCentroids) {
    double diff = 0.0;

    for (int i = 0; i < this.clusterNumber; i++) {
      diff += calculateDistance(this.centroids[i], newCentroids[i]);
    }

    // diff /= (double) this.clusterNumber;
    return diff;
  }

  /**
   * @return
   */
  public int getDataSize() {
    return this.data.length;
  }

  /**
   * @return
   */
  public DataPoint[] getData() {
    return this.data;
  }

  /**
   * @return
   */
  public int getClusterNumber() {
    return this.clusterNumber;
  }
}
