package com.microsoft.azure.stencily;

import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.HttpMethod;
import com.microsoft.azure.functions.HttpRequestMessage;
import com.microsoft.azure.functions.HttpResponseMessage;
import com.microsoft.azure.functions.HttpStatus;
import com.microsoft.azure.functions.annotation.AuthorizationLevel;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;
import java.util.Optional;

/**
 * Azure Functions with HTTP Trigger.
 */
public class UserFunctions {

	@FunctionName("createUser")
	public HttpResponseMessage createUser(
	@HttpTrigger(name = "req", methods = {HttpMethod.POST}, authLevel = AuthorizationLevel.ANONYMOUS, route = "user" ) HttpRequestMessage<Optional<String>> request,
	final ExecutionContext context) {
		 return request.createResponseBuilder(HttpStatus.OK).body("Success!").build();
	}

	@FunctionName("createUsersWithArrayInput")
	public HttpResponseMessage createUsersWithArrayInput(
	@HttpTrigger(name = "req", methods = {HttpMethod.POST}, authLevel = AuthorizationLevel.ANONYMOUS, route = "user/createWithArray" ) HttpRequestMessage<Optional<String>> request,
	final ExecutionContext context) {
		 return request.createResponseBuilder(HttpStatus.OK).body("Success!").build();
	}

	@FunctionName("createUsersWithListInput")
	public HttpResponseMessage createUsersWithListInput(
	@HttpTrigger(name = "req", methods = {HttpMethod.POST}, authLevel = AuthorizationLevel.ANONYMOUS, route = "user/createWithList" ) HttpRequestMessage<Optional<String>> request,
	final ExecutionContext context) {
		 return request.createResponseBuilder(HttpStatus.OK).body("Success!").build();
	}

	@FunctionName("loginUser")
	public HttpResponseMessage loginUser(
	@HttpTrigger(name = "req", methods = {HttpMethod.GET}, authLevel = AuthorizationLevel.ANONYMOUS, route = "user/login" ) HttpRequestMessage<Optional<String>> request,
	final ExecutionContext context) {
		 return request.createResponseBuilder(HttpStatus.OK).body("Success!").build();
	}

	@FunctionName("logoutUser")
	public HttpResponseMessage logoutUser(
	@HttpTrigger(name = "req", methods = {HttpMethod.GET}, authLevel = AuthorizationLevel.ANONYMOUS, route = "user/logout" ) HttpRequestMessage<Optional<String>> request,
	final ExecutionContext context) {
		 return request.createResponseBuilder(HttpStatus.OK).body("Success!").build();
	}

	@FunctionName("getUserByName")
	public HttpResponseMessage getUserByName(
	@HttpTrigger(name = "req", methods = {HttpMethod.GET}, authLevel = AuthorizationLevel.ANONYMOUS, route = "user/{username}" ) HttpRequestMessage<Optional<String>> request,
	final ExecutionContext context) {
		 return request.createResponseBuilder(HttpStatus.OK).body("Success!").build();
	}

	@FunctionName("updateUser")
	public HttpResponseMessage updateUser(
	@HttpTrigger(name = "req", methods = {HttpMethod.PUT}, authLevel = AuthorizationLevel.ANONYMOUS, route = "user/{username}" ) HttpRequestMessage<Optional<String>> request,
	final ExecutionContext context) {
		 return request.createResponseBuilder(HttpStatus.OK).body("Success!").build();
	}

	@FunctionName("deleteUser")
	public HttpResponseMessage deleteUser(
	@HttpTrigger(name = "req", methods = {HttpMethod.DELETE}, authLevel = AuthorizationLevel.ANONYMOUS, route = "user/{username}" ) HttpRequestMessage<Optional<String>> request,
	final ExecutionContext context) {
		 return request.createResponseBuilder(HttpStatus.OK).body("Success!").build();
	}
}
