package T3;

import java.util.concurrent.atomic.AtomicReference;

public class UseAtomicReference {
    static AtomicReference<UserInfo> usrRef = new AtomicReference<>();

    public static void main(String[] args) {
        UserInfo user = new UserInfo("Mark",15);
        usrRef.set(user);

        UserInfo updateUser = new UserInfo("Bill",17);
        usrRef.compareAndSet(user,updateUser);
        System.out.println(usrRef.get().getName());
        System.out.println(usrRef.get().getAge());
        System.out.println(user.getName());
        System.out.println(user.getAge());
    }

    static class UserInfo{
        private String name;
        private int age;

        public UserInfo(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public int getAge() {
            return age;
        }
    }
}
