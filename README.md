<h1>Book Store</h1>
<h2>Description</h2>
BookStoreApplication is a microservice providing endpoints to manage Authors, their Books, and Purchase Orders made to purchase those Books, including Stock Management.

<h2>Features</h2>
<h3>Author</h3>
<ol>
    <li>Create Author</li>
    <li>Get Author by ID</li>
    <li>Get Authors</li>
    <li>Update Author (first name, last name)</li>
    <li>Delete Author (Soft delete)</li>
</ol>

<h3>Book</h3>
<ol>
    <li>Get Book by ID</li>
    <li>Get Books</li>
    <li>Update Book (name, price, stock)</li>
    <li>Delete Book (Soft delete)</li>
    <li>Create Book for Author</li>
    <li>Get Books by Author</li>
</ol>

<h3>Purchase Order</h3>
<ol>
    <li>Create Purchase Order</li>
    <li>Get Purchase Order by ID</li>
    <li>Get Purchase Orders</li>
    <li>Update Purchase Order Order Entries</li>
    <li>
        Update Purchase Order Status with the below, possible flows:
            <ol>
                <li>
                    CREATED [Order has been submitted]:
                        <ol>
                            <li>
                                CONFIRMED [Order's stock is available. Commits stock to order]:
                                    <ol>
                                        <li>
                                            PAID [Payment has come in for the Order]:
                                                <ol>
                                                    <li>SHIPPED [Order has been shipped]</li>
                                                    <li>REFUNDED [Order has been refunded. Rolls back stock committed to order]</li>
                                                </ol>
                                        </li>
                                        <li>CANCELLED [Order has been Cancelled by User. Rolls back stock committed to order]</li>
                                    </ol>
                            <li>CANCELLED [Order has been Cancelled by System]</li>
                        </ol>
                </li>
            </ol>
    </li>
</ol>

<h3>Forex</h3>
When a price is returned, this is returned in the applications' base currency (default EUR). To also display the prices in another currency, pass header "display-currency" with a currency code as a value (Eg: GBP) to have also the converted prices in that currency, in addition to the base currency price.

<h2>Technologies Used</h2>
<ul>
    <li>Spring Boot Web</li>
    <li>Spring Boot Test</li>
    <li>Spring Boot Data JPA</li>
    <li>Spring Boot Actuator</li>
    <li>Micrometer Core & Prometheus</li>
    <li>Lombok</li>
    <li>Orika Model Mapper</li>
    <li>H2</li>
    <li>Flyway</li>
    <li>Swagger API Documentation</li>
    <li>Feign</li>
</ul>

<h2>Running the Application</h2>
To run the application:
<ol>
    <li>Run command mvn clean install. This creates the target/book-store-application.jar file.</li>
    <li>Run the JAR file using the command <b>java -jar target/book-store-application.jar.</b></li>
</ol>

To build and run a Docker image:
<ol>
    <li>Run command mvn clean install. This creates the target/book-store-application.jar file.</li>
    <li>Build the Docker image using the command <b>docker build -t bookstore .</b> (where 'bookstore' is the name of the image).</li>
    <li>Run the Docker image using the command <b>docker run -p 1234:8080 bookstore</b> (where 1234 is the local port to redirect the container port to, and 'bookstore' is the image name).</li>
</ol>

<h2>API Documentation</h2>
Swagger API Documentation is available on startup at: <a href='http://localhost:8080/swagger-ui.html'>http://localhost:8080/swagger-ui.html</a>.
