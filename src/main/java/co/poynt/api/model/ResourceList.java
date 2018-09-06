package co.poynt.api.model;

import java.util.List;

public class ResourceList<T> {

    private List<T> list;
    private int start;
    private int total;
    private int count;
    public List<T> getList() {
        return list;
    }
    public void setList(List<T> list) {
        this.list = list;
    }
    public int getStart() {
        return start;
    }
    public void setStart(int start) {
        this.start = start;
    }
    public int getTotal() {
        return total;
    }
    public void setTotal(int total) {
        this.total = total;
    }
    public int getCount() {
        return count;
    }
    public void setCount(int count) {
        this.count = count;
    }
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("ResourceList [list=");
        builder.append(list);
        builder.append(", start=");
        builder.append(start);
        builder.append(", total=");
        builder.append(total);
        builder.append(", count=");
        builder.append(count);
        builder.append("]");
        return builder.toString();
    }
    
    
}
