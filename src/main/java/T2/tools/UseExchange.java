package T2.tools;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Exchanger;

public class UseExchange {
    private static final Exchanger<Set<String>> exchange
            = new Exchanger<>();

    public static void main(String[] args) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Set<String> setA = new HashSet<>();
                try {
                    setA.add("aa");
                    setA = exchange.exchange(setA);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                Set<String> setB = new HashSet<>();
                try {
                    setB.add("bb");
                    setB = exchange.exchange(setB);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
