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
public class Store {

	@FunctionName("getInventory")
	public HttpResponseMessage getInventory(
	@HttpTrigger(name = "req", methods = {HttpMethod.GET}, authLevel = AuthorizationLevel.ANONYMOUS, route = "store/inventory" ) HttpRequestMessage<Optional<String>> request,
	final ExecutionContext context) {
	return null;}

	@FunctionName("placeOrder")
	public HttpResponseMessage placeOrder(
	@HttpTrigger(name = "req", methods = {HttpMethod.POST}, authLevel = AuthorizationLevel.ANONYMOUS, route = "store/order" ) HttpRequestMessage<Optional<String>> request,
	final ExecutionContext context) {
	return null;}

	@FunctionName("getOrderById")
	public HttpResponseMessage getOrderById(
	@HttpTrigger(name = "req", methods = {HttpMethod.GET}, authLevel = AuthorizationLevel.ANONYMOUS, route = "store/order/{orderId}" ) HttpRequestMessage<Optional<String>> request,
	final ExecutionContext context) {
	return null;}

	@FunctionName("deleteOrder")
	public HttpResponseMessage deleteOrder(
	@HttpTrigger(name = "req", methods = {HttpMethod.DELETE}, authLevel = AuthorizationLevel.ANONYMOUS, route = "store/order/{orderId}" ) HttpRequestMessage<Optional<String>> request,
	final ExecutionContext context) {
	return null;}
}
