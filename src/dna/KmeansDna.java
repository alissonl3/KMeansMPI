package dna;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import util.Util;
import data.DnaCount;
import data.DnaPoint;

/**
 * @author zhengxiong
 *
 *         DNA Point handler class
 */
public class KmeansDna {

  private DnaPoint[] data;

  private DnaPoint[] centroids;

  private int clusterNumber;

  private int dimension;

  // private List<List<DataPoint>> groupInfo;

  /**
   * @param clusterNumber
   *          clusters
   * @param file
   *          input
   */
  public KmeansDna(int clusterNumber, String file) {
    this.clusterNumber = clusterNumber;
    this.centroids = new DnaPoint[this.clusterNumber]; // new ArrayList<DataPoint>();
    this.dimension = -1;
    readData(file);
  }

  /**
   * @param file
   *          input
   */
  private void readData(String file) {

    ArrayList<DnaPoint> buff = new ArrayList<DnaPoint>();

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

        List<Integer> values = new ArrayList<Integer>();
        for (int i = 0; i < strs.length; i++) {
          values.add(Util.char2num(strs[i].trim().charAt(0)));
        }

        DnaPoint newPoint = new DnaPoint(values);
        buff.add(newPoint);
      }

    } catch (IOException e) {
      e.printStackTrace();
    }

    this.data = new DnaPoint[buff.size()];
    for (int i = 0; i < buff.size(); i++) {
      this.data[i] = buff.get(i);
    }
  }

  /**
   * centroid initialization
   */
  public void clusterInitialize() {
    List<DnaPoint> newData = new ArrayList<DnaPoint>();
    for (int i = 0; i < this.data.length; i++) {
      newData.add(this.data[i]);
    }
    Collections.shuffle(newData);

    ArrayList<DnaCount> cnts = new ArrayList<DnaCount>();
    for (int i = 0; i < this.dimension; i++) {
      cnts.add(new DnaCount());
    }

    for (int i = 0; i < this.clusterNumber; i++) {
      this.centroids[i] = new DnaPoint(newData.get(i).getValues(), cnts);
    }

    // /////////////////////////
    // for (int i = 0; i < this.clusterNumber; i++) {
    // List<Integer> tmp = this.centroids[i].getValues();
    // for (int j = 0; j < tmp.size(); j++) {
    // System.out.print(tmp.get(j) + "\t");
    // }
    // System.out.println();
    // }
    // /////////////////////////
  }

  /**
   * @param p1
   * @param p2
   * @return
   */
  private int calculateDistance(DnaPoint p1, DnaPoint p2) {
    int dist = 0;

    for (int i = 0; i < p1.size(); i++) {
      if (p1.getValue(i) != p2.getValue(i)) {
        dist++;
      }
    }

    // System.out.println(dist);
    return dist;
  }

  /**
   * @param start
   * @param end
   */
  public void updateGroup(int start, int end) {
    for (int i = start; i < end; i++) {
      int min = Integer.MAX_VALUE;
      int clusterNumber = -1;

      for (int j = 0; j < this.centroids.length; j++) {
        int dist = calculateDistance(this.data[i], this.centroids[j]);

        if (dist < min) {
          min = dist;
          clusterNumber = j;
        }
      }

      // System.out.println(clusterNumber);
      this.data[i].setGroup(clusterNumber);
    }
  }

  /**
   * @param start
   * @param end
   * @return
   */
  public DnaPoint[] updateCentroid(int start, int end) {
    DnaPoint[] newCentroids = new DnaPoint[this.clusterNumber];

    for (int i = 0; i < this.clusterNumber; i++) {

      List<Integer> zeros = new ArrayList<Integer>();
      ArrayList<DnaCount> cnts = new ArrayList<DnaCount>();
      for (int j = 0; j < this.dimension; j++) {
        zeros.add(0);
        cnts.add(new DnaCount());
      }

      newCentroids[i] = new DnaPoint(zeros, cnts);
    }

    for (int i = start; i < end; i++) {
      int group = this.data[i].getGroup();
      newCentroids[group].addDnaPoint(this.data[i]);
    }

    // print(newCentroids);
    return newCentroids;
  }

  /**
   * @param newCentroids
   */
  public void updateCentroidsValues(DnaPoint[] newCentroids) {
    for (int i = 0; i < this.clusterNumber; i++) {
      newCentroids[i].updateValue();
    }

    // print(newCentroids);
  }

  // private void print(DnaPoint[] newCentroids) {
  // // ///////////////////////
  // for (int i = 0; i < this.clusterNumber; i++) {
  // List<Integer> tmp = newCentroids[i].getValues();
  // List<DnaCount> cnts = newCentroids[i].getCounts();
  // for (int j = 0; j < tmp.size(); j++) {
  // System.out.print("Point: " + i + " Dimen:" + j + " value: " + tmp.get(j) + "\t");
  // int[] num = cnts.get(j).getCount();
  // System.out.println("A:" + num[0] + " G:" + num[1] + " C:" + num[2] + " T:" + num[3]);
  // }
  // System.out.println();
  // }
  // // ///////////////////////
  // }

  /**
   * @param newCentroids
   */
  public void setCentroid(DnaPoint[] newCentroids) {
    this.centroids = newCentroids;
  }

  /**
   * @return
   */
  public DnaPoint[] getCentroid() {
    return this.centroids;
  }

  /**
   * @param newCentroids
   * @return
   */
  public int calculateDiff(DnaPoint[] newCentroids) {
    int diff = 0;

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
  public DnaPoint[] getData() {
    return this.data;
  }

  /**
   * @return
   */
  public int getClusterNumber() {
    return this.clusterNumber;
  }
}
