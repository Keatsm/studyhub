package backend.studyhub.entities.items;

public class Link extends Item {

    private String url;

    public Link(long id, String name, String url) {
        super(id, name);
        this.url = url;
    }

    public Link(String name, String url) {
        super(name);
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String getType() {
        return "link";
    }
    
}
