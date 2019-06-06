package util;

import java.util.ArrayList;
import java.util.List;

import data.DataPoint;
import data.DnaPoint;

/**
 * @author zhengxiong
 *
 *         utility
 */
public class Util {

  /**
   * @param result
   * @param clusterNumber
   */
  public static void printResult(DataPoint[] result, int clusterNumber) {
    for (int i = 0; i < clusterNumber; i++) {
      List<DataPoint> list = new ArrayList<DataPoint>();

      for (int j = 0; j < result.length; j++) {
        if (result[j].getGroup() == i) {
          list.add(result[j]);
        }
      }

      System.out.println("Cluster: " + (i + 1) + "\tNum: " + list.size());
      for (int j = 0; j < list.size(); j++) {
        System.out.print("Point<" + (j + 1) + "> : ");
        for (int k = 0; k < list.get(j).size(); k++) {
          System.out.print("<" + list.get(j).getValue(k) + ">");
        }
        System.out.println();
      }

      System.out.println();
    }
  }

  /**
   * @param ch
   * @return
   */
  public static int char2num(char ch) {
    switch (ch) {
      case 'A':
        return 0;
      case 'G':
        return 1;
      case 'C':
        return 2;
      case 'T':
        return 3;
      default:
        System.out.println("Convert error!");
        return -1;
    }
  }

  /**
   * @param num
   * @return
   */
  public static char num2char(int num) {
    switch (num) {
      case 0:
        return 'A';
      case 1:
        return 'G';
      case 2:
        return 'C';
      case 3:
        return 'T';
      default:
        System.out.println("Convert error!");
        return 'X';
    }
  }

  /**
   * @param result
   * @param clusterNumber
   */
  public static void printDnaResult(DnaPoint[] result, int clusterNumber) {
    for (int i = 0; i < clusterNumber; i++) {
      List<DnaPoint> list = new ArrayList<DnaPoint>();

      for (int j = 0; j < result.length; j++) {
        if (result[j].getGroup() == i) {
          list.add(result[j]);
        }
      }

      System.out.println("Cluster: " + (i + 1) + "\tNum: " + list.size());
      for (int j = 0; j < list.size(); j++) {
        System.out.print("Point<" + (j + 1) + "> : ");
        for (int k = 0; k < list.get(j).size(); k++) {
          System.out.print(num2char(list.get(j).getValue(k)) + " ");
        }
        System.out.println();
      }

      System.out.println();
    }
  }
}
