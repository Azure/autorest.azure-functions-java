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
public class PetFunctions {

	@FunctionName("addPet")
	public HttpResponseMessage addPet(
	@HttpTrigger(name = "req", methods = {HttpMethod.POST}, authLevel = AuthorizationLevel.ANONYMOUS, route = "pet" ) HttpRequestMessage<Optional<String>> request,
	final ExecutionContext context) {
		 return request.createResponseBuilder(HttpStatus.OK).body("Success!").build();
	}

	@FunctionName("updatePet")
	public HttpResponseMessage updatePet(
	@HttpTrigger(name = "req", methods = {HttpMethod.PUT}, authLevel = AuthorizationLevel.ANONYMOUS, route = "pet" ) HttpRequestMessage<Optional<String>> request,
	final ExecutionContext context) {
		 return request.createResponseBuilder(HttpStatus.OK).body("Success!").build();
	}

	@FunctionName("findPetsByStatus")
	public HttpResponseMessage findPetsByStatus(
	@HttpTrigger(name = "req", methods = {HttpMethod.GET}, authLevel = AuthorizationLevel.ANONYMOUS, route = "pet/findByStatus" ) HttpRequestMessage<Optional<String>> request,
	final ExecutionContext context) {
		 return request.createResponseBuilder(HttpStatus.OK).body("Success!").build();
	}

	@FunctionName("findPetsByTags")
	public HttpResponseMessage findPetsByTags(
	@HttpTrigger(name = "req", methods = {HttpMethod.GET}, authLevel = AuthorizationLevel.ANONYMOUS, route = "pet/findByTags" ) HttpRequestMessage<Optional<String>> request,
	final ExecutionContext context) {
		 return request.createResponseBuilder(HttpStatus.OK).body("Success!").build();
	}

	@FunctionName("getPetById")
	public HttpResponseMessage getPetById(
	@HttpTrigger(name = "req", methods = {HttpMethod.GET}, authLevel = AuthorizationLevel.ANONYMOUS, route = "pet/{petId}" ) HttpRequestMessage<Optional<String>> request,
	final ExecutionContext context) {
		 return request.createResponseBuilder(HttpStatus.OK).body("Success!").build();
	}

	@FunctionName("updatePetWithForm")
	public HttpResponseMessage updatePetWithForm(
	@HttpTrigger(name = "req", methods = {HttpMethod.POST}, authLevel = AuthorizationLevel.ANONYMOUS, route = "pet/{petId}" ) HttpRequestMessage<Optional<String>> request,
	final ExecutionContext context) {
		 return request.createResponseBuilder(HttpStatus.OK).body("Success!").build();
	}

	@FunctionName("deletePet")
	public HttpResponseMessage deletePet(
	@HttpTrigger(name = "req", methods = {HttpMethod.DELETE}, authLevel = AuthorizationLevel.ANONYMOUS, route = "pet/{petId}" ) HttpRequestMessage<Optional<String>> request,
	final ExecutionContext context) {
		 return request.createResponseBuilder(HttpStatus.OK).body("Success!").build();
	}
}
