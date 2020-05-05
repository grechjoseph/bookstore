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
                    CREATED:
                        <ol>
                            <li>
                                CONFIRMED (Commits stock to order):
                                    <ol>
                                        <li>
                                            PAID:
                                                <ol>
                                                    <li>SHIPPED</li>
                                                    <li>REFUNDED (Rolls back stock committed to order)</li>
                                                </ol>
                                        </li>
                                        <li>CANCELLED (Rolls back stock committed to order)</li>
                                    </ol>
                            <li>CANCELLED</li>
                        </ol>
                </li>
            </ol>
    </li>
</ol>

<h2>Technologies Used</h2>
<ul>
    <li>Spring Boot Web</li>
    <li>Spring Boot Test</li>
    <li>Spring Boot Data JPA</li>
    <li>Spring Boot Actuator</li>
    <li>Lombok</li>
    <li>Orika Model Mapper</li>
    <li>H2</li>
    <li>Flyway</li>
    <li>Swagger API Documentation</li>
</ul>

<h2>API Documentation</h2>
Swagger API Documentation available at: <a href='http://localhost:8080/swagger-ui.html'>http://localhost:8080/swagger-ui.html</a>.
