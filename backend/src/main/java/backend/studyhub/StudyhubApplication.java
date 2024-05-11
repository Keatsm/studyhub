package backend.studyhub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import backend.studyhub.entities.items.Folder;
import backend.studyhub.entities.items.Link;
import backend.studyhub.entities.items.Note;

@SpringBootApplication
@RestController
public class StudyhubApplication {

	public static void main(String[] args) {
		SpringApplication.run(StudyhubApplication.class, args);
	}

	@GetMapping("/test")
    public Folder hello() {
      Folder folder = new Folder("test");
	  folder.addItem(new Note("test", "hello"));
	  folder.addItem(new Link("exam notes", "https://www.google.com/"));
	  return folder;
    }

}
