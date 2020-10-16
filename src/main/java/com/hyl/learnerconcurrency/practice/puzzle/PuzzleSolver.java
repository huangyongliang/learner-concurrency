package com.hyl.learnerconcurrency.practice.puzzle;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 在解决器中找不到解答
 * <p>
 *
 * @author hyl
 * @version v1.0: PuzzleSolver.java, v 0.1 2020/10/15 2:19 $
 */
public class PuzzleSolver<P,M> extends ConcurrentPuzzleSolver<P,M> {

    private final AtomicInteger taskCount = new AtomicInteger(0);

    public PuzzleSolver(Puzzle<P, M> puzzle, ExecutorService exec) {
        super(puzzle, exec);
    }

    @Override
    protected Runnable newTask(P p, M m, Node<P, M> n) {
        return new CountingSolverTask(p,m,n);
    }

    class CountingSolverTask extends SolverTask{

        public CountingSolverTask(P pos, M move, Node<P, M> prev) {
            super(pos, move, prev);
            taskCount.incrementAndGet();
        }

        @Override
        public void run() {
            try {
                super.run();
            }finally {
                if (taskCount.decrementAndGet() == 0)
                    solution.setValue(null);
            }
        }
    }

}
