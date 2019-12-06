package T8.vo;

public interface ITaskProcesser<T,R> {
    TaskResult<R> taskExecute(T data);
}
