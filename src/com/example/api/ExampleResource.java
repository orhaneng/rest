package com.example.api;

import javax.ws.rs.*;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/example")
public class ExampleResource {
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getExample() {
        return "Hello, World2!";
    }

    @GET
    @Path("/{param}")
    public String getExample(@PathParam("param") String param) {
        return "Received parameter: " + param;
    }

    @GET
    @Path("/query")
    public String getExample2(@QueryParam("param") String param) {
        return "Received query parameter: " + param;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postExample2(ExampleRequest request) {
        /*
        {
  "name": "John Doe",
  "age": 25,
  "email": "johndoe@example.com"
}
         */
        // Process the request JSON and return a response
        String responseMessage = "Received JSON: " + request.toString();

        // Build the response with status code 201 (Created)
        return Response.status(Response.Status.CREATED)
                .entity(responseMessage)
                .build();
    }

    @GET
    @Path("/return")
    @Produces(MediaType.APPLICATION_JSON)
    public MyResponse getExample2() {
        MyResponse response = new MyResponse();
        response.setMessage("Hello, World!");
        response.setStatusCode(Response.Status.ACCEPTED.getStatusCode());
        return response;
    }

    private String data = "Initial value";

    @PATCH
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String patchExample(String newValue) {
        data = newValue;
        return "Value updated: " + data;
    }

    static int num = 1;

    @GET
    @Path("/paging")
    @Produces(MediaType.APPLICATION_JSON)
    public ExampleResponse getExamples(@QueryParam("limit") int limit) {
        // Perform pagination logic based on the provided limit

        // Simulating data retrieval
        int totalRecords = 100;
        int totalPages = (int) Math.ceil((double) totalRecords / limit);
        num *= 2;
        // Simulating data based on the limit and current page
        int currentPage = num;
        int startRecord = (currentPage - 1) * limit + 1;
        int endRecord = Math.min(currentPage * limit, totalRecords);

        // Creating a response object
        ExampleResponse response = new ExampleResponse();
        response.setTotalRecords(totalRecords);
        response.setTotalPages(totalPages);
        response.setCurrentPage(currentPage);
        response.setStartRecord(startRecord);
        response.setEndRecord(endRecord);

        return response;
    }

    @GET
    @Path("/cache/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getExample(@PathParam("id") int id) {
        // Simulate retrieving data based on the provided ID
        ExampleData data = retrieveData(id);

        if (data != null) {
            // Build the response with data and cache control settings
            CacheControl cacheControl = new CacheControl();
            cacheControl.setMaxAge(3600); // Cache for 1 hour

            return Response.ok(data)
                    .cacheControl(cacheControl)
                    .build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Data not found")
                    .build();
        }
    }

    private ExampleData retrieveData(int id) {
        // Simulated data retrieval
        // Replace with your actual data retrieval logic
        if (id == 1) {
            return new ExampleData(1, "Example 1");
        } else if (id == 2) {
            return new ExampleData(2, "Example 2");
        } else {
            return null;
        }
    }
}


/*
    /*
    Query Parameters:

Query parameters are appended to the end of the URL and are used to filter or customize the request.
They are specified using the ? symbol followed by key-value pairs separated by &.
Query parameters are optional and can be used to provide additional information to the server.
Example: http://example.com/api/resource?param1=value1&param2=value2
Path Parameters:

Path parameters are part of the URL path itself and are used to identify and retrieve a specific resource.
They are specified within curly braces {} in the URL path.
Path parameters are used to define the structure of the URL and provide essential information to the server.
Example: http://example.com/api/resource/{id}

The choice between using query parameters or path parameters in a REST API depends on the purpose and semantics of the parameters. Here are some considerations to help you decide:

Path Parameters:

Use path parameters when the parameter is essential for identifying or locating a specific resource.
Path parameters are typically used for hierarchical or nested resources where the parameter represents a specific level in the resource hierarchy.
Path parameters make the URL more expressive and meaningful.
Example: /api/users/{userId} where userId is used to identify a specific user resource.
Query Parameters:

Use query parameters when the parameter is optional or used for filtering, sorting, or customizing the result set.
Query parameters are useful for adding additional information to the request without affecting the resource identification.
Query parameters are flexible and can be added or modified without changing the resource URL structure.
Example: /api/products?category=electronics&priceLessThan=100 where category and priceLessThan are used to filter the products based on category and price.
In summary, path parameters are typically used for essential resource identification, while query parameters are used for optional or additional information related to the resource. Consider the nature and purpose of the parameter to determine whether it should be a path parameter or a query parameter in your REST API design.
Here are a few reasons why the PATCH method is useful:
Efficiency: When you only need to update a few fields of a resource, using PATCH instead of PUT or POST can be more efficient. Instead of sending the entire updated representation of the resource, you only send the specific changes.
Granularity: PATCH allows you to apply partial updates, enabling you to modify specific fields or properties of a resource while leaving the rest unchanged. This granularity can be useful when dealing with large or complex resources.
Atomicity: PATCH operations can be atomic, meaning that they are applied as a single, indivisible operation. This ensures that either all the requested changes are applied, or none of them are. This is particularly useful in concurrent or distributed systems where multiple clients may be updating the same resource simultaneously.
Idempotence: When a PATCH request is properly implemented, it should be idempotent. This means that sending the same PATCH request multiple times should have the same effect as sending it once. It allows clients to safely retry the request without causing unintended side effects.
Overall, the PATCH method provides a flexible and efficient way to update resources in a RESTful API by allowing partial updates and ensuring atomicity and idempotence.

 */