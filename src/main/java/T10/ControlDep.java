package T10;

public class ControlDep {
    int a = 0;
    boolean flag = false;

    public void init(){
        a = 1;
        flag = true;
    }

    public void use(){
        if (flag){
            int i = a*a;
        }
    }
}
