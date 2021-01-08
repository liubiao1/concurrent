package com.liubiao.concurrent.pattern;

import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;

/**
 * wait notify应用： 生产者消费者模式
 * <p>
 * 要点
 * 1、与前面的保护性暂停中的 GuardObject 不同，不需要产生结果和消费结果的线程一一对应
 * 2、消费队列可以用来平衡生产和消费的线程资源
 * 3、生产者仅负责产生结果数据，不关心数据该如何处理，而消费者专心处理结果数据
 * 4、消息队列是有容量限制的，满时不会再加入数据，空时不会再消耗数据
 * JDK 中各种阻塞队列，采用的就是这种模式
 *
 * @author mc0710
 */
@Slf4j
public class Pattern2 {

    public static void main(String[] args) {
        MessageQueue messageQueue = new MessageQueue(2);
        for (int i = 0; i < 6; i++) {
            int id = i;
            new Thread(() -> {
                log.info("开始生产咯~~~");
                messageQueue.produce(new Message(id, new Object()));
                log.info("编号为" + id + "的消息生产完成");
            }, "生产者t" + i).start();
        }

        new Thread(() -> {
            Message message = messageQueue.consume();
            log.info("编号为" + message.getId() + "的消息消费完成");
        }, "消费者").start();
    }

}

/**
 * 消息
 */
class Message {
    private int id;
    private Object message;

    public Message(int id, Object message) {
        this.id = id;
        this.message = message;
    }

    public int getId() {
        return id;
    }

    public Object getMessage() {
        return message;
    }
}

/**
 * 消息队列
 */
@Slf4j
class MessageQueue {
    private LinkedList<Message> queue;
    /**
     * 消息队列的大小
     */
    private int capacity;

    public MessageQueue(int capacity) {
        this.capacity = capacity;
        queue = new LinkedList<>();
    }

    public Message consume() {
        synchronized (queue) {
            while (queue.isEmpty()) {
                log.info("队列为空，请等待");
                try {
                    queue.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();

                }
            }
            // 不为空时，消费第一个
            Message message = queue.removeFirst();
            queue.notifyAll();
            return message;
        }
    }

    public void produce(Message message) {
        synchronized (queue) {
            while (capacity == queue.size()) {
                log.info("队列已满，请等待");
                try {
                    queue.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            queue.addLast(message);
            queue.notifyAll();
        }
    }

}