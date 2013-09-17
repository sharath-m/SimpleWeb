package uk.co.malavalli.persistance;

import java.util.HashMap;
import java.util.List;

import uk.co.malavalli.services.user.UserInfo;

public interface DataRepository {

	public HashMap<String, UserInfo> getDb();

	public UserInfo findUser(String userName);

	public List<UserInfo> findAllUsers();

	public UserInfo updateUser(String userName, String newName);

	public void addUser(List<String> userNames);

	public void addUser(String userName);

}