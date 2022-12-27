import java.io.*;

class Returns{

  int numOdUnderWater;
  int numOfV; //number of vertecis walked through

  Returns(){
    numOdUnderWater = 0;
    numOfV = 0;
  }

  void addUw(int st){
    this.numOdUnderWater+=st;
  }

  void addV(){
    this.numOfV++;
  }

}


class Naloga9 {
    static class SegmentTreeNode {
        int minElevation;
        int maxElevation;
        int numPoints;
        SegmentTreeNode topLeft;
        SegmentTreeNode topRight;
        SegmentTreeNode bottomLeft;
        SegmentTreeNode bottomRight;
    
        SegmentTreeNode(int minElevation, int maxElevation, int numPoints) {
          this.minElevation = minElevation;
          this.maxElevation = maxElevation;
          this.numPoints = numPoints;
        }
    
        SegmentTreeNode(int minElevation, int maxElevation, int numPoints,
                        SegmentTreeNode topLeft, SegmentTreeNode topRight,
                        SegmentTreeNode bottomLeft, SegmentTreeNode bottomRight) {
          this.minElevation = minElevation;
          this.maxElevation = maxElevation;
          this.numPoints = numPoints;
          this.topLeft = topLeft;
          this.topRight = topRight;
          this.bottomLeft = bottomLeft;
          this.bottomRight = bottomRight;
        }
      }
    
  
    static SegmentTreeNode buildSegmentTree(int[][] map, int x1, int y1, int x2, int y2) {

      //System.out.println("x: " + x1 + "," +x2 + " y: " + y1+","+y2);

      // base case: the current node covers a single point
      if (x1 == x2 && y1 == y2) {
        return new SegmentTreeNode(map[x1][y1], map[x1][y1], 1);
      }
  
      // compute the midpoints of the current node
      int xmid = (x1 + x2) / 2;
      int ymid = (y1 + y2) / 2;

      // recursively build the child nodes
      SegmentTreeNode topLeft = buildSegmentTree(map, x1, y1, xmid, ymid);
      SegmentTreeNode topRight = buildSegmentTree(map, x1, ymid+1, xmid, y2);
      SegmentTreeNode bottomLeft = buildSegmentTree(map, xmid+1, y1, x2, ymid);
      SegmentTreeNode bottomRight = buildSegmentTree(map, xmid+1, ymid+1, x2, y2);
  
      // compute the minimum and maximum elevations of the current node
      int minElevation = Math.min(Math.min(topLeft.minElevation, topRight.minElevation),
                                  Math.min(bottomLeft.minElevation, bottomRight.minElevation));
      int maxElevation = Math.max(Math.max(topLeft.maxElevation, topRight.maxElevation),
                                  Math.max(bottomLeft.maxElevation, bottomRight.maxElevation));
      int numPoints = topLeft.numPoints + topRight.numPoints + bottomLeft.numPoints + bottomRight.numPoints;

      if(minElevation == maxElevation){
        topLeft=null;
        topRight=null;
        bottomLeft=null;
        bottomRight=null;
      }
  
      return new SegmentTreeNode(minElevation, maxElevation, numPoints, topLeft,topRight,bottomLeft,bottomRight);
    }
  
    static void querySubmergedPoints(SegmentTreeNode node, int waterTableHeight, Returns returns) {
        // ce sta min in max enaka ali pa ce je max manjsi ali enak gladini so vse potopljene
        if (node.maxElevation <= waterTableHeight) {
          //doda potopljene
          returns.addUw(node.numPoints);
          //doda da smo prehodili se eno polje v drevesu
          returns.addV();
          return;
        }
        
        //ce je min vecji od gladine ni noben potopljen
        if(node.minElevation > waterTableHeight){
          returns.addV();
          return;
        }

        //ce je min manjsi od gladine so vse tocke lahko potopljene...
        if (node.minElevation <= waterTableHeight) {
          returns.addV();
          //ce je gladina vmes med min in max, gremo pogledat manjsa polja
          if (node.maxElevation > waterTableHeight) {
            querySubmergedPoints(node.topLeft, waterTableHeight,returns);
            querySubmergedPoints(node.topRight, waterTableHeight,returns);
            querySubmergedPoints(node.bottomLeft, waterTableHeight,returns);
            querySubmergedPoints(node.bottomRight, waterTableHeight,returns);
          }
        }
      }
  
    public static void main(String[] args) throws IOException {
        // read the input from the input file
        BufferedReader reader = new BufferedReader(new FileReader(args[0]));
        int n = Integer.parseInt(reader.readLine());
        int[][] map = new int[n][n];
        for (int i = 0; i < n; i++) {
          String[] line = reader.readLine().split(",");
          for (int j = 0; j < n; j++) {
            map[i][j] = Integer.parseInt(line[j]);
          }
        }
        int b = Integer.parseInt(reader.readLine());
        int[] waterTableHeights = new int[b];
        for (int i = 0; i < b; i++) {
          waterTableHeights[i] = Integer.parseInt(reader.readLine());
        }
        reader.close();
    
        // build the Segment Tree
        SegmentTreeNode root = buildSegmentTree(map, 0, 0, n-1, n-1);
    
        // write the results to the output file
        BufferedWriter writer = new BufferedWriter(new FileWriter(args[1]));
        for (int waterTableHeight : waterTableHeights) {
          Returns returns = new Returns();
          querySubmergedPoints(root, waterTableHeight,returns);
          writer.write(returns.numOdUnderWater + "," + returns.numOfV);
          writer.newLine();
        }
        writer.close();
    }
    
      
}
  