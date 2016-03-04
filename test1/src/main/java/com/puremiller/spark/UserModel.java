package com.puremiller.spark;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class UserModel {
	private int userID = 1;
	private Map<Integer, User> users = new HashMap<>();

	public int createUser(String name, int age) {
		int id = userID++;
		User newUser = new User();
		newUser.setName(name);
		newUser.setAge(age);
		users.put(id, newUser);
		return id;
	}
	
	public List<User> getAllUsers(){
		return users.keySet().stream().sorted().map((userID)->
			users.get(userID)).collect(Collectors.toList());
		
	}

	@Override
	public String toString() {
		return "UserModel [userID=" + userID + ", users=" + users + "]";
	}
}
