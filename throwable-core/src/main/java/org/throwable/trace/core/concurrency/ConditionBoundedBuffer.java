package org.throwable.trace.core.concurrency;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author zhangjinci
 * @version 2016/11/19 15:08
 * @function 显式条件变量构造有界缓存
 * 注意put、take方法会一直阻塞直到满足条件
 */
public class ConditionBoundedBuffer<Z> {

    private final Lock lock = new ReentrantLock();

    //notFull --> count < items.length
    private final Condition notFull = lock.newCondition();

    //notFull --> count > 0
    private final Condition notEmpty = lock.newCondition();

    //max item num
    private final int SIZE = 1000;
    @SuppressWarnings("unchecked")
    private final Z[] items = (Z[]) new Object[SIZE];

    private int tail, head, count;

    public void put(Z z) throws InterruptedException {
        lock.lock();
        try {
            while (count == items.length) {
                notFull.await();
            }
            items[tail] = z;   //取出第一个节点的值
            if (++tail == items.length) {
                tail = 0;
            }
            ++count;
            notEmpty.signal();
        } finally {
            lock.unlock();
        }
    }

    public Z take() throws InterruptedException {
        lock.lock();
        try {
            while (count == 0) {
                notEmpty.await();
            }
            Z z = items[head];  //取出第一个节点的值
            items[head] = null; //把第一个节点的值设置为null
            if (++head == items.length) {
                head = 0;
            }
            notFull.signal();
            return z;
        } finally {
            lock.unlock();
        }
    }
}
