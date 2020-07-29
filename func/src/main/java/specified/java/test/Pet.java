package specified.java.test;

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
public class Pet {

	@FunctionName("addPet")
	public HttpResponseMessage addPet(
	@HttpTrigger(name = "req", methods = {HttpMethod.POST}, authLevel = AuthorizationLevel.ANONYMOUS, route = "pet" ) HttpRequestMessage<Optional<String>> request,
	final ExecutionContext context) {
	return null;}

	@FunctionName("updatePet")
	public HttpResponseMessage updatePet(
	@HttpTrigger(name = "req", methods = {HttpMethod.PUT}, authLevel = AuthorizationLevel.ANONYMOUS, route = "pet" ) HttpRequestMessage<Optional<String>> request,
	final ExecutionContext context) {
	return null;}

	@FunctionName("findPetsByStatus")
	public HttpResponseMessage findPetsByStatus(
	@HttpTrigger(name = "req", methods = {HttpMethod.GET}, authLevel = AuthorizationLevel.ANONYMOUS, route = "pet/findByStatus" ) HttpRequestMessage<Optional<String>> request,
	final ExecutionContext context) {
	return null;}

	@FunctionName("findPetsByTags")
	public HttpResponseMessage findPetsByTags(
	@HttpTrigger(name = "req", methods = {HttpMethod.GET}, authLevel = AuthorizationLevel.ANONYMOUS, route = "pet/findByTags" ) HttpRequestMessage<Optional<String>> request,
	final ExecutionContext context) {
	return null;}

	@FunctionName("getPetById")
	public HttpResponseMessage getPetById(
	@HttpTrigger(name = "req", methods = {HttpMethod.GET}, authLevel = AuthorizationLevel.ANONYMOUS, route = "pet/{petId}" ) HttpRequestMessage<Optional<String>> request,
	final ExecutionContext context) {
	return null;}

	@FunctionName("updatePetWithForm")
	public HttpResponseMessage updatePetWithForm(
	@HttpTrigger(name = "req", methods = {HttpMethod.POST}, authLevel = AuthorizationLevel.ANONYMOUS, route = "pet/{petId}" ) HttpRequestMessage<Optional<String>> request,
	final ExecutionContext context) {
	return null;}

	@FunctionName("deletePet")
	public HttpResponseMessage deletePet(
	@HttpTrigger(name = "req", methods = {HttpMethod.DELETE}, authLevel = AuthorizationLevel.ANONYMOUS, route = "pet/{petId}" ) HttpRequestMessage<Optional<String>> request,
	final ExecutionContext context) {
	return null;}
}
