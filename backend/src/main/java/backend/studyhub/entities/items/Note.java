package backend.studyhub.entities.items;

public class Note extends Item {
    private String content;

    public Note(String name, String content) {
        super(name);
        this.content = content;
    }

    public Note(long id, String name, String content) {
        super(id, name);
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String getType() {
        return "note";
    }

    
}
