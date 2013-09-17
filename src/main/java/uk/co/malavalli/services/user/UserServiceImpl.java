package uk.co.malavalli.services.user;

import java.util.List;

import uk.co.malavalli.persistance.InMemoryDb;

public class UserServiceImpl implements UserService {

	private final InMemoryDb inMemoryDb;

	// TODO - Impl Facade to handle multiple storage options
	public UserServiceImpl(InMemoryDb inMemoryDb) {
		this.inMemoryDb = inMemoryDb;
	}

	@Override
	public void addUser(String userName) {
		inMemoryDb.addUser(userName);
	}

	@Override
	public void addUsers(List<String> userNames) {
		inMemoryDb.addUser(userNames);
	}

	@Override
	public List<UserInfo> getAllUsers() {
		return inMemoryDb.findAllUsers();
	}

	@Override
	public UserInfo getUser(String userName) {
		return inMemoryDb.findUser(userName);
	}

	@Override
	public UserInfo updateUser(String userName, String newName) {
		return inMemoryDb.updateUser(userName, newName);
	}

	// remove users

}
