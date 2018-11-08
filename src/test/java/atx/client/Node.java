package atx.client;


public class Node {
    @NoteId(id=123,name="test")
    private int id;
    @NoteName
    private String name;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    @NodeShow
    public void show(){
        System.out.println(name);
    }

}
