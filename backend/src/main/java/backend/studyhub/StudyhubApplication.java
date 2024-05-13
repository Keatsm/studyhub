package backend.studyhub;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import backend.studyhub.data.DataAccess;
import backend.studyhub.entities.User;
import backend.studyhub.entities.Workspace;
import backend.studyhub.entities.items.Folder;
import backend.studyhub.entities.items.Link;
import backend.studyhub.entities.items.Note;
import backend.studyhub.services.UserService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;


@SpringBootApplication
@RestController
public class StudyhubApplication {

	public static void main(String[] args) {
		SpringApplication.run(StudyhubApplication.class, args);
	}

	@GetMapping("/test_item")
    public Folder hello() {
		Folder folder = new Folder("test");
		folder.addItem(new Note("test", "hello"));
		folder.addItem(new Link("exam notes", "https://www.google.com/"));
		return folder;
    }

	@GetMapping("/test_user")
	public User hello(@RequestParam(value = "name", defaultValue = "Keats") String name) {
	 	User user = new User(name, "keatsm@zoho.com", "123");
		new Workspace("workspace", user);
	  	return user;
	}

	@PostMapping("/user/register")
	public Map<String, String> userRegister(@RequestBody Map<String, String> req) {
		Map<String, String> res = new HashMap<>();
		res.put("token", UserService.userRegister(req.get("name"), req.get("email"), req.get("password")));	
		return res;
	}

	@PostMapping("/user/login")
	public Map<String, String> userLogin(@RequestBody Map<String, String> req) {
		Map<String, String> res = new HashMap<>();
		res.put("token", UserService.userLogin(req.get("email"), req.get("password")));	
		return res;
	}

	@PostMapping("/user/logout")
	public void userLogout(@RequestHeader(name = "token") String token) {
		UserService.userLogout(token);   
	}


	@GetMapping("/user")
	public User getMethodName(@RequestParam long id) {
		return DataAccess.getInstance().getDataStore().getUser(id);
	}
	
}
