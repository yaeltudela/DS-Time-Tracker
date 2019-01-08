package com.dstimetracker.devsodin.ds_timetracker;

import com.dstimetracker.devsodin.core.Node;

import java.util.Comparator;

public class NodeComparator {

    public static Comparator<Node> startDateNodeComparator = new Comparator<Node>() {
        @Override
        public int compare(Node node1, Node node2) {
            if (node1.getStartDate() != null && node2.getStartDate() != null) {
                return node1.getStartDate().compareTo(node2.getStartDate());
            } else {
                return 0;
            }
        }
    };


    public static Comparator<Node> endDateNodeComparator = new Comparator<Node>() {
        @Override
        public int compare(Node node1, Node node2) {
            if (node1.getStartDate() != null && node2.getStartDate() != null) {
                return node1.getEndDate().compareTo(node2.getEndDate());
            } else {
                return 0;
            }
        }
    };


    public static Comparator<Node> higherDurationNodeComparator = new Comparator<Node>() {
        @Override
        public int compare(Node o1, Node o2) {
            return 0;
        }
    };

}
