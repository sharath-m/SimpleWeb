package uk.co.malavalli.services.user;

import java.util.List;

public interface UserService {

	public List<UserInfo> getAllUsers();

	public UserInfo getUser(String userName);

	public void addUser(String userName);

	public void addUsers(List<String> userNames);

	public UserInfo updateUser(String userName, String newName);

}