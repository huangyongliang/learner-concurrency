package com.hyl.learnerconcurrency.practice.puzzle;

import java.util.LinkedList;
import java.util.List;

/**
 * 用于谜题解决框架的链表节点
 * <p>
 *
 * @author hyl
 * @version v1.0: Node.java, v 0.1 2020/10/15 1:34 $
 */
public class Node<P,M> {
    final P pos;
    final M move;
    final Node<P,M> prev;

    public Node(P pos, M move, Node<P,M> prev){
        this.pos = pos;
        this.move = move;
        this.prev = prev;
    }

    List<M> asMoveList(){
        List<M> solution = new LinkedList<>();
        for (Node<P,M> n = this; n.move !=null; n = n.prev){
            solution.add(0,n.move);
        }
        return solution;
    }

}
