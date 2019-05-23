package com.cwiz.lock;

public class DeadLock {


    public static class DeadLockThreadA extends Thread{
        protected String lockA;
        protected String lockB;
        DeadLockThreadA(String name, String lockA, String lockB){
            super(name);
            this.lockA = lockA;
            this.lockB = lockB;
        }


        @Override
        public void run() {
            synchronized (this.lockA){
                System.out.println(Thread.currentThread().getName() + " got " + this.lockA);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (this.lockB) {
                    System.out.println(Thread.currentThread().getName() + " got " + this.lockB);
                }
                System.out.println(Thread.currentThread().getName() + " released " + this.lockB);
            }
            System.out.println(Thread.currentThread().getName() + " released " + this.lockA);
        }
    }


    public static void main(String[] args) {
        String lockA = new String("lockA");
        String lockB = new String("lockB");
        DeadLockThreadA threadA = new DeadLockThreadA("threadA", lockA, lockB);
        DeadLockThreadA threadB = new DeadLockThreadA("threadB", lockB, lockA);
        threadA.start();
        threadB.start();
    }
}
