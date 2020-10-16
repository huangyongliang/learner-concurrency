package com.hyl.learnerconcurrency.practice.puzzle;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;

/**
 * <p>
 *
 * @author hyl
 * @version v1.0: ConcurrentPuzzleSolver.java, v 0.1 2020/10/15 1:56 $
 */
public class ConcurrentPuzzleSolver<P,M> {

    private final Puzzle<P,M> puzzle;
    private final ExecutorService exec;
    private final ConcurrentMap<P,Boolean> seen;
    final ValueLatch<Node<P,M>> solution = new ValueLatch<>();

    public ConcurrentPuzzleSolver(Puzzle<P,M> puzzle, ExecutorService exec){
        this.puzzle = puzzle;
        this.exec = exec;
        seen = new ConcurrentHashMap<>();
    }

    public List<M> solve() throws InterruptedException{

        try {
            P p = puzzle.initialPosition();
            exec.execute(newTask(p,null,null));
            // 阻塞直到找到解答
            Node<P,M> solnNode = solution.getValue();
            return (solnNode==null)? null : solnNode.asMoveList();
        }finally {
            exec.shutdown();
        }

    }

    protected Runnable newTask(P p, M m, Node<P,M> n){
        return new SolverTask(p,m,n);
    }


    class SolverTask extends Node<P,M> implements Runnable{

        public SolverTask(P pos, M move, Node<P, M> prev) {
            super(pos, move, prev);
        }

        @Override
        public void run() {
            if (solution.isSet() || seen.putIfAbsent(pos,true)!=null)
                // 已经找到了解答或者已经遍历了这个位置
                return;
            if (puzzle.isGoal(pos))
                solution.setValue(this);
            else
                for (M m: puzzle.legalMoves(pos))
                    exec.execute(newTask(puzzle.move(pos,m),m,this));
        }
    }
}
