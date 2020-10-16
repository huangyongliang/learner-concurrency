package com.hyl.learnerconcurrency.practice.puzzle;

import java.util.Set;

/**
 * 表示搬箱子之类谜题的抽象类
 * <p>
 *
 * @author hyl
 * @version v1.0: Puzzle.java, v 0.1 2020/10/15 1:31 $
 */
public interface Puzzle<P,M> {
    P initialPosition();
    boolean isGoal(P position);
    Set<M> legalMoves(P position);
    P move(P position, M move);
}
