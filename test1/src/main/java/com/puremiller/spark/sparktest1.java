package com.puremiller.spark;

import static spark.Spark.get;
import static spark.Spark.post;

import java.io.IOException;
import java.io.StringWriter;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class sparktest1 {
	private static final int HTTP_BAD_REQUEST = 400;
	//comment for transparency
	
	public static String dataToJson(Object data) {
		System.out.println("Data received by dataToJson " + data); //DEBUG
		ObjectMapper mapper=new ObjectMapper();
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		StringWriter writer= new StringWriter();
		try {
			mapper.writeValue(writer, data);
			System.out.println("Data in mapper.writeValueAsString(data) " + mapper.writeValueAsString(data)); //DEBUG
			System.out.println("Data in writer.toString" + writer.toString()); //DEBUG //!!!!!/
			return writer.toString();
		} catch (IOException e) {
			throw new RuntimeException("IOException from a StringWriter!");
		}
	}

	public static void main(String[] args) {
		UserModel userModel = new UserModel();

		post("/posts", (request, response) -> {
			try {
				ObjectMapper mapper = new ObjectMapper();
				System.out.println("Data of request.body()" + request.body()); //DEBUG
				User newUser = mapper.readValue(request.body(), User.class);
//				if (!newUser.isValid()) {
//					response.status(HTTP_BAD_REQUEST);
//					return "";
//				}
				int id = userModel.createUser(newUser.getName(), newUser.getAge());
				response.status(200);
				response.type("application/json");
				return id;

			} catch (JsonParseException exception) {
				System.out.println("Json object could not be parsed!");
				System.out.println(exception);
				response.status(HTTP_BAD_REQUEST);
			}

			return "";
		});
		
		get("/posts", (request, response) -> {
			response.status(200);
			response.type("application/json");
			System.out.println("userModel.getAllUsers()" + userModel.getAllUsers()); //DEBUG
			return dataToJson(userModel.getAllUsers());
		});

	}
}
