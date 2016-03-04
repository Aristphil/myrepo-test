package com.puremiller.spark;

import lombok.Data;

@Data
class User {
	private String name;
	private int age;

//	public boolean isValid() {
//		return name != null && !name.isEmpty() && age > 35;
//	}
}
