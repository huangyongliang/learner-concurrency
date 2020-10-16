package com.hyl.learnerconcurrency.practice.puzzle;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 串行的谜题解答器
 * <p>
 *
 * @author hyl
 * @version v1.0: SequentialPuzzleSolver.java, v 0.1 2020/10/15 1:42 $
 */
public class SequentialPuzzleSolver<P,M> {

    private final Puzzle<P,M> puzzle;
    private final Set<P> seen = new HashSet<>();

    public SequentialPuzzleSolver(Puzzle<P,M> puzzle){
        this.puzzle = puzzle;
    }

    public List<M> solve(){
        P p = puzzle.initialPosition();

        return search(new Node<>(p,null,null));
    }

    private List<M> search(Node<P,M> node){

        if (!seen.contains(node.pos)){
            seen.add(node.pos);
            if (puzzle.isGoal(node.pos))
                return node.asMoveList();

            for (M move : puzzle.legalMoves(node.pos)){
                P pos = puzzle.move(node.pos,move);
                Node<P,M> child = new Node<>(pos,move,node);
                List<M> result = search(child);
                if (result!=null)
                    return result;
            }
        }
        return null;
    }

}
