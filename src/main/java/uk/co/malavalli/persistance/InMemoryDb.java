package uk.co.malavalli.persistance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.co.malavalli.common.GuidGenerator;
import uk.co.malavalli.services.user.UserInfo;

public class InMemoryDb implements DataRepository {
	Logger LOG = LoggerFactory.getLogger(getClass());
	private final GuidGenerator guidGenerator;

	public InMemoryDb(GuidGenerator guidGenerator) {
		this.guidGenerator = guidGenerator;
	}

	private final HashMap<String, UserInfo> userMap = new HashMap<String, UserInfo>();

	@Override
	public HashMap<String, UserInfo> getDb() {
		return userMap;
	}

	@Override
	public UserInfo findUser(String userName) {
		UserInfo user = null;
		if (userMap.size() > 0) {
			user = userMap.get(userName);
			LOG.info("Found user {}", user.getUserName());
		}
		return user;
	}

	@Override
	public List<UserInfo> findAllUsers() {
		List<UserInfo> users = new ArrayList<UserInfo>();
		if (userMap.size() > 0) {
			Set<String> keySet = userMap.keySet();
			for (String userKey : keySet) {
				users.add(userMap.get(userKey));
			}
			LOG.info("Found {} users", users.size());
		}
		return users;
	}

	@Override
	public UserInfo updateUser(String userName, String newName) {
		UserInfo user = findUser(userName);
		if (user != null) {
			user.setUserName(newName);
			userMap.put(newName, user);
			userMap.remove(userName);
			LOG.info("User name updated from {} to {}", userName, newName);
			return user;
		}
		LOG.info("{} not updated", userName);
		return user;
	}

	@Override
	public void addUser(List<String> userNames) {
		for (String userName : userNames) {
			addUser(userName);
		}
	}

	@Override
	public void addUser(String userName) {
		userMap.put(userName, createUser(userName));
	}

	private UserInfo createUser(String userName) {
		UserInfo user = new UserInfo();
		user.setUserName(userName);
		user.setUserId(guidGenerator.generate());
		user.setCreatedDate(DateTime.now());
		return user;
	}
}
