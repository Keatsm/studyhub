package backend.studyhub;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import backend.studyhub.entities.User;
import backend.studyhub.entities.items.Folder;
import backend.studyhub.entities.items.Link;
import backend.studyhub.entities.items.Note;
import backend.studyhub.services.UserService;
import backend.studyhub.services.WorkspaceService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
	public User hello(@RequestParam(value = "id") long id) {
	  	return UserService.getUser(id);
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

	@PostMapping("/workspace")
	public Map<String, Long> createWorkspace(@RequestHeader(name = "token") String token, @RequestBody Map<String, String> req) {
		Map<String, Long> res = new HashMap<>();
		res.put("workspaceId", WorkspaceService.createWorkspace(UserService.getUserIdFromToken(token), req.get("name"), req.get("description")));
		return res;
	}

	@DeleteMapping("/workspace")
	public void deleteWorkspace(@RequestHeader(name = "token") String token, @RequestParam(value = "workspaceId") long workspaceId) {
		WorkspaceService.deleteWorkspace(UserService.getUserIdFromToken(token), workspaceId);
	}

	@PutMapping("/workspace")
	public void updateWorkspace(@RequestHeader(name = "token") String token, @RequestBody Map<String, String> req) {
		WorkspaceService.updateWorkspace(UserService.getUserIdFromToken(token), Long.parseLong(req.get("workspaceId")), req.get("name"), req.get("description"));
	}

	@PostMapping("/workspace/user")
	public void addUser(@RequestHeader(name = "token") String token, @RequestBody Map<String, String> req) {
		WorkspaceService.addUser(UserService.getUserIdFromToken(token), Long.parseLong(req.get("workspaceId")), Long.parseLong(req.get("userId")));
	}

	@DeleteMapping("/workspace/user")
	public void removeUser(@RequestHeader(name = "token") String token, @RequestParam(value = "workspaceId") long workspaceId, @RequestParam(value = "userId") long userId) {
		WorkspaceService.removeUser(UserService.getUserIdFromToken(token), workspaceId, userId);
	}

	@PostMapping("/workspace/admin")
	public void addAdmin(@RequestHeader(name = "token") String token, @RequestBody Map<String, String> req) {
		WorkspaceService.addAdmin(UserService.getUserIdFromToken(token), Long.parseLong(req.get("workspaceId")), Long.parseLong(req.get("userId")));
	}

	@DeleteMapping("/workspace/admin")
	public void removeAdmin(@RequestHeader(name = "token") String token, @RequestParam(value = "workspaceId") long workspaceId, @RequestParam(value = "userId") long userId) {
		WorkspaceService.removeAdmin(UserService.getUserIdFromToken(token), workspaceId, userId);
	}
	
	
}
