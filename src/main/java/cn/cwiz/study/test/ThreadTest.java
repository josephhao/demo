package cn.cwiz.study.test;

public class ThreadTest {

    static class InnerThread extends Thread{

        synchronized void methodA(){
            System.out.println(Thread.currentThread().getName() + " start " +System.currentTimeMillis());
            try {
                Thread.sleep(100000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " end " +System.currentTimeMillis());

        }

        void methodB(){
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " start " +System.currentTimeMillis());

            System.out.println(Thread.currentThread().getName() + " end " +System.currentTimeMillis());

        }

        @Override
        public void run() {
            super.run();
            if( this.getName().equals("A")) {
                methodA();
            }else{
                methodB();
            }
        }
    }


    public static void main(String[] args) {
        Thread inner = new InnerThread();

        Thread threadA = new Thread(inner,"A");
        Thread threadB = new Thread(inner,"B");
        threadA.start();
        threadB.start();

    }
}
