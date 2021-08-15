package com.example.csm.algorithm;


import com.example.csm.model.Input;
import com.example.csm.model.Output;
import com.example.csm.util.MergeSort;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class PriorityBased {
    private List<Integer> cpuQueue;//CPU QUEUE

    //GET OUTPUT BASED ON NON-PREEMPTIVE PRIORITY ALGORITHM
    public Output[] getNonPreemptive(final Input[] input) {
        int[] sort = MergeSort.sort(input);
        int last = input[sort[0]].getaTime();
        cpuQueue = new LinkedList<>();
        LinkedList<Integer> readyQueue = new LinkedList<>();
        cpuQueue.add(last);
        int len = input.length;
        boolean[] completed = new boolean[len];
        int count = 0;
        int i = 0;
        Output[] out = new Output[len];
        while (count < len) {
            for (; i < len; i++) {
                if (input[sort[i]].getaTime() <= last && !completed[sort[i]]) {
                    readyQueue.add(sort[i]);
                } else break;
            }
            if (!readyQueue.isEmpty()) {
                int current = readyQueue.get(0);
                int tmp;
                int highest = 0;
                Iterator<Integer> iterator = readyQueue.listIterator();
                for (int j = 0; j < readyQueue.size(); j++)
                    if (input[(tmp = iterator.next())].getPriority() > input[current].getPriority()) {
                        current = tmp;
                        highest = j;
                    }
                readyQueue.remove(highest);
                out[current] = new Output();
                cpuQueue.add(current);
                completed[current] = true;
                count++;
                out[current].setWaiting(last - input[current].getaTime());
                last = last + input[current].getbTime();
                out[current].setTurnAround(last - input[current].getaTime());
                out[current].setCompletion(out[current].getTurnAround()+input[current].getaTime());
                cpuQueue.add(last);
            } else {
                cpuQueue.add(-1);
                last = input[sort[i]].getaTime();
                cpuQueue.add(last);
            }
        }
        return out;
    }


    //GETTER FOR CPU QUEUE
    public List<Integer> getCpuQueue() {
        return cpuQueue;
    }
}