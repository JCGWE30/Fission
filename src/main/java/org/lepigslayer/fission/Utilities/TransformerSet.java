package org.lepigslayer.fission.Utilities;

import it.unimi.dsi.fastutil.ints.Int2IntMap;
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;

import java.util.Map;
import java.util.function.Consumer;

public class TransformerSet<T> {
    private int currentId;
    private Int2ObjectMap<Consumer<T>> idMap = new Int2ObjectOpenHashMap<>();
    private Int2IntMap priorityMap = new Int2IntOpenHashMap();

    private boolean dirty = false;
    private int[] runOrder = new int[0];

    public int add(Consumer<T> consumer) {
        return add(consumer,0);
    }

    public int add(Consumer<T> consumer, int priority) {
        int id = currentId++;
        idMap.put(id,consumer);
        priorityMap.put(id,priority);
        dirty = true;
        return id;
    }

    public void remove(int id){
        if(!idMap.containsKey(id))
            return;
        idMap.remove(id);
        priorityMap.remove(id);
        dirty = true;
    }

    private void sort(){
        runOrder = priorityMap.int2IntEntrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .mapToInt(Map.Entry::getKey)
                .toArray();
    }

    public void apply(T object){
        if(dirty){
            sort();
            dirty = false;
        }

        for (int i : runOrder) {
            idMap.get(i).accept(object);
        }
    }
}
