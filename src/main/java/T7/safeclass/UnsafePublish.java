package T7.safeclass;

import java.util.ArrayList;
import java.util.List;

public class UnsafePublish {
    private List<Integer> list = new ArrayList<>(3);
    public UnsafePublish(){
        list.add(1);
        list.add(2);
        list.add(3);
    }

    public List<Integer> getList() {
        return list;
    }

    public synchronized int getList(int index){
        return list.get(index);
    }

    public synchronized void set(int index,int val){
        list.set(index,val);
    }
}
