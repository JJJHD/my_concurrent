package T7.performance;

import java.util.HashSet;
import java.util.Set;

public class FinenessLock {
    public final Set<String> users = new HashSet<>();
    public final Set<String> queries = new HashSet<>();

    public void addUser(String u){
        synchronized (users){
            users.add(u);
        }
    }

    public void addQuery(String q){
        synchronized (users){
            queries.add(q);
        }
    }
}
