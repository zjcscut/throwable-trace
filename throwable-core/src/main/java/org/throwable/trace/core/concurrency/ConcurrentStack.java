package org.throwable.trace.core.concurrency;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @author zhangjinci
 * @version 2016/11/19 16:03
 * @function 非阻塞线程安全栈
 */
public class ConcurrentStack<Z> {

    private AtomicReference<Node<Z>> top = new AtomicReference<>();

    private static class Node<Z> {
        public final Z item;
        public Node<Z> next;

        public Node(Z item) {
            this.item = item;
        }
    }

    public void push(Z item) {
        Node<Z> newHead = new Node<>(item);
        Node<Z> oldHead;
        do {
            oldHead = top.get();
            newHead.next = oldHead;
        } while (!top.compareAndSet(oldHead, newHead));
    }

    public Z pop() {
        Node<Z> newHead;
        Node<Z> oldHead;
        do {
            oldHead = top.get();
            if (oldHead == null) {
                return null;
            }
            newHead = oldHead.next;
        } while (!top.compareAndSet(oldHead, newHead));
        return oldHead.item;
    }
}
