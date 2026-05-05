package org.lepigslayer.fission.InventorySystem.InventoryChain;

import org.lepigslayer.fission.InventorySystem.InventoryInstance;

import java.util.ArrayDeque;
import java.util.Deque;

public class PlayerChain {
    private Deque<Deque<InventoryInstance>> chainStack = new ArrayDeque<>();

    public PlayerChain() {
        chainStack.push(new ArrayDeque<>());
    }

    public Deque<InventoryInstance> getChainStack() {
        return chainStack.peek();
    }

    public void push(){
        chainStack.push(new ArrayDeque<>());
    }

    public void pop(){
        if(chainStack.isEmpty() || chainStack.size() == 1)
            throw new IndexOutOfBoundsException("Cannot pop to an empty stack");
        chainStack.pop();
    }
}
