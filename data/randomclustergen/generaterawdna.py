import sys
import csv
import numpy
import getopt
import math
import random

def usage():
    print '$> python generaterawdata.py <required args> [optional args]\n' + \
        '\t-c <#>\t\tNumber of clusters to generate\n' + \
        '\t-p <#>\t\tNumber of points per cluster\n' + \
        '\t-o <file>\tFilename for the output of the raw data\n' + \
        '\t-v [#]\t\tMaximum coordinate value for points\n' + \
        '\t-d [#]\t\tDimension of each DNA (length)\n'

       
       

def euclideanDistance(p1, p2):
    '''
    Takes two 2-D points and computes the Euclidean distance between them.
    '''
    num = 0
    for i in range(len(p1)) :
        if p1[i] != p2[i] :
            num += 1
    return num
        

def tooClose(point, points, minDist):
    '''
    Computes the euclidean distance between the point and all points
    in the list, and if any points in the list are closer than minDist,
    this method returns true.
    '''
    for pair in points:
        if euclideanDistance(point, pair) < minDist:
                return True

    return False

def handleArgs(args):
    # set up return values
    numClusters = -1
    numPoints = -1
    output = None
    maxValue = 10
    length = 10

    try:
        optlist, args = getopt.getopt(args[1:], 'c:p:v:o:d:')
    except getopt.GetoptError, err:
        print str(err)
        usage()
        sys.exit(2)

    for key, val in optlist:
        # first, the required arguments
        if   key == '-c':
            numClusters = int(val)
        elif key == '-p':
            numPoints = int(val)
        elif key == '-o':
            output = val
        # now, the optional argument
        elif key == '-v':
            maxValue = float(val)
        elif key == '-d':
            length = int(val)

    # check required arguments were inputted  
    if numClusters < 0 or numPoints < 0 or \
            maxValue < 1 or \
            output is None or length < 3:
        usage()
        sys.exit()
    return (numClusters, numPoints, output, \
            maxValue, length)

def drawOrigin(maxValue):
    return numpy.random.uniform(0, maxValue, 2)

def drawDna(length):
    return [random.choice(['A', 'G', 'C', 'T']) for i in range(length)]

def generateDna(cluster, variance):
    data = []
    for i in range(len(cluster)):
        if random.uniform(0, 1) < (variance / len(cluster)) :
            data.append(random.choice(['A', 'G', 'C', 'T']))
        else :
            data.append(cluster[i])
    return data

# start by reading the command line
numClusters, \
numPoints, \
output, \
maxValue, \
length = handleArgs(sys.argv)

writer = csv.writer(open(output, "w"))

# step 1: generate each 2D centroid
centroids_radii = []
minDistance = 5
for i in range(0, numClusters):
    centroid_radius = drawDna(length) # drawOrigin(maxValue)
    # is it far enough from the others?
    while (tooClose(centroid_radius, centroids_radii, minDistance)):
        centroid_radius = drawOrigin(maxValue)
    centroids_radii.append(centroid_radius)

# step 2: generate the points for each centroid
points = []
minClusterVar = 1
maxClusterVar = length / 2
for i in range(0, numClusters):
    # compute the variance for this cluster
    variance = numpy.random.uniform(minClusterVar, maxClusterVar)
    cluster = centroids_radii[i]
    for j in range(0, numPoints):
        # generate a 2D point with specified variance
        # point is normally-distributed around centroids[i]
        line = generateDna(cluster, variance)
        # write the points out
        writer.writerow(line)
