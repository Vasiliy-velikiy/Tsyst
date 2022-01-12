package com.tsystems.javaschool.tasks;

import com.tsystems.javaschool.tasks.pyramid.PyramidBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class Main {
    public static void main(String[] args) {
        String statement="22 + 3 - 2 * (2 * 5 + 2) * 4";
        String[]decimal=statement.split("");


        for (String s:decimal){
            System.out.println(s);
        }



    }
}
