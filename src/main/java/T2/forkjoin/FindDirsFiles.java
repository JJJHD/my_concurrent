package T2.forkjoin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

/**
 * 遍历制定目录，寻找特定类型文件
 */
public class FindDirsFiles extends RecursiveAction {

    private File path;

    public FindDirsFiles(File path) {
        this.path = path;
    }

    @Override
    protected void compute() {
        List<FindDirsFiles> subTasks = new ArrayList<>();
        File[] files = path.listFiles();
        if (files != null){
            for (File file : files){
                if (file.isDirectory()){
                    subTasks.add(new FindDirsFiles(file));
                }else {
                    if (file.getAbsolutePath().endsWith("txt")){
                        System.out.println("txt: "+file.getAbsolutePath());
                    }
                }
            }
            if (!subTasks.isEmpty()){
                for (FindDirsFiles subTask:invokeAll(subTasks)){
                    subTask.join();
                }
            }
        }
    }

    public static void main(String[] args) {
        try{
            ForkJoinPool pool = new ForkJoinPool();
            FindDirsFiles task = new FindDirsFiles(new File("/Volumes/Mac/usr/local/Homebrew"));
            pool.execute(task);
            System.out.println("Task is running...");
            task.join();
            System.out.println("Task end");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
