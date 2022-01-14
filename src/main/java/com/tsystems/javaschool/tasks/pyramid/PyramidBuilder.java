package com.tsystems.javaschool.tasks.pyramid;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class PyramidBuilder {

    /**
     * Builds a pyramid with sorted values (with minumum value at the top line and maximum at the bottom,
     * from left to right). All vacant positions in the array are zeros.
     *
     * @param inputNumbers to be used in the pyramid
     * @return 2d array with pyramid inside
     * @throws {@link CannotBuildPyramidException} if the pyramid cannot be build with given input
     */
    public int[][] buildPyramid(List<Integer> inputNumbers) {
                                                     // TODO : Implement your solution here
CannotBuildPyramidException exception=new CannotBuildPyramidException();
        if(inputNumbers.contains(null))throw exception;                  // if list contains null value-we can not build pyramid
        if (inputNumbers.size()==Integer.MAX_VALUE - 1) throw exception; //this case means: Requested array size exceeds VM limit (limit of heap size)

        Collections.sort(inputNumbers);                                 //sort elements for easily manipulated
                /*Each level in pyramid has roots. Every level corresponds amount roots. First level=main root and this value is min
                * Second level has 2 roots , Third has 3 roots etc. List size has 6 elements:6-1-2-3=0 this is flat (even)  pyramid
                * First step: subtract the count from the list size . If result=0, all roots are locating in levels, else we have uneven pyramid-throw exception
                * */
        int count=1;
        int size=inputNumbers.size();
        for (int i = 0; i <inputNumbers.size() ; i++) {
            size=size-count;
            if (size==0||size<0) break;
            count++;
        }
        if (size>0||size<0)throw exception;
        int high=count;            //high of array correspond count (# of levels). Count contains elements of roots in lowest level
        int length=count+count-1; //length of array is sum( lowest level plus level above. If we have lowest level=4, our length is 4+3=7 elements in length)
        int [][]mass=new int[high][length];


        int c=inputNumbers.size()-1;  //this is marker for index of list, we will insert elements from the end of the list IN the end of the array. Because pyramid always has basis
        int bound=0;                 //border with left and right of the pyramid. In lowest level, bound=0. In level-1, bound=1 etc
     for (int j = high-1; j >=0 ; j--) {
         for (int i = mass[j].length - 1-bound; i >= 0+bound; i = i - 2) {
             int temp=inputNumbers.get(c); //all elements (for exclusively first root) are locating through 1 element, for exclusively borders
             mass[j][i] = inputNumbers.get(c);
             c--;
             if(c==-1)break; //this means  list  has not elements
         }
         bound++;//if we go up on one level, border moves away
     }
        return mass ;
    }
}
