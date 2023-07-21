
![Logo](https://i.ibb.co/Nj9NSnH/logo.png)



# Soniak Brew Company

Global company named Soniak Bew web application which provides simple and easy access to the database using swagger API. It provides features to manage projects and employees, updates projects status, provide information about the most expensive project and many more!


## Authors
- Martyna Świerszcz
- Oleksandr Gneushev
- Rafał Papała
- Paweł Skóra


## Usage/Examples
To have an access to basic CRUD method for employees you can use EmployeeController which provides several methods that allows you to play around with.

For example you can fetch all sales employees list using:

```java
   public Response getAllDeliveryEmployees(@QueryParam("token") String token) {
        try {
            if (AuthSwitch.isTokenNeeded) {
                if (!authService.isHr(token) & !authService.isAdmin(token)) {
                    throw new FailedToVerifyTokenException();
                }
            }
            return Response.ok(employeeService.getAllDeliveryEmployees()).build();
        } catch (FailedToGetAllDeliverymanEmployeesException e) {
            System.err.println(e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (TokenExpiredException | FailedToVerifyTokenException e) {
            System.err.println(e.getMessage());
            return Response.status(Response.Status.FORBIDDEN).entity(e.getMessage()).build();
        }
    }
```

The same way you may register new user to the service using AuthController:

```java
public Response registerUser(Login login) {
        try {
            authService.registerUser(login);
            return Response.ok().build();
        } catch (FailedToCheckIfUserIsRegisteredException e) {
            System.err.println(e.getMessage());

            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (FailedToRegisterNewUserException e) {
            System.err.println(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }
```


## Tech Stack

**Server**: Java, Dropwizard, MySQL

**Client*: NodeJS, Express, Axios, TypeScript

