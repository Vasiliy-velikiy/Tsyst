package com.tsystems.javaschool.tasks.subsequence;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Subsequence {

    /**
     * Checks if it is possible to get a sequence which is equal to the first
     * one by removing some elements from the second one.
     *
     * @param x first sequence
     * @param y second sequence
     * @return <code>true</code> if possible, otherwise <code>false</code>
     */
    @SuppressWarnings("rawtypes")
    public boolean find(List x, List y) {
        // TODO: Implement the logic here
        if(x==null || y==null)throw new IllegalArgumentException();
        if(x.size()>y.size())return false; //definitely false if list x> list y
        if(x.size()==0) return true;       //definitely true if list x empty

        List listForComparison=new ArrayList(y);//this list for remove elements which not equal


        for (int i = 0; i <x.size();) {   //cycle for iteration list x
            for (int j = i; j <y.size() ; j++) { //cycle for iteration list y
                if (x.get(i)!=y.get(j)) {    //if element from x not equal element from y , remove this element from listForComparison
                    listForComparison.remove(y.get(j));
                    if(j==y.size()-1){   //if j went to the end, stop cycle
                        i++;
                        break;
                    }
                }
                else {
                    if (i==x.size()-1){ //if i went to the end, continue cycle, because list y contains other elements
                        continue;
                    }
                    i++;
                }
            }
        }
        if (x.equals(listForComparison))return true;  //if listForComparison contains elements from list x-> it is true
        else
        return false;
    }
}
