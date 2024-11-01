# WESTERN GOVERNORS UNIVERSITY 
## D287 – JAVA FRAMEWORKS


## C.  Customize the HTML user interface for your customer’s application. The user interface should include the shop name, the product names, and the names of the parts.
Note: Do not remove any elements that were included in the screen. You may add any additional elements you would like or any images, colors, and styles, although it is not required.

In file: mainscreen.html lines 14, 19, 21, 53 customized the user interface to match my Skateboard shop website.
Added a footer in lines 93 - 95 to match the about.html file.
In section E the sample parts and products will be added.

## D.  Add an “About” page to the application to describe your chosen customer’s company to web viewers and include navigation to and from the “About” page and the main screen.

Created file: AboutController in the controllers folder. Lines 1 - 15 create the controller to link the /about URL to the about.html file.

    @GetMapping
    public String showAboutPage() {
        return "about";

Create file: about.html in the templates folder. Lines 1 - 47 create the about.html page. Added a button underneath the shop title in the about.html file to link back to the mainscreen.html file.

<body>
<div class="container">
  <h1>CJ's Skateboard Shop</h1>
  <div class="container">
    <a class="btn btn-primary mt-3" href="http://localhost:8080">Return Home</a>
  </div>
  <hr>
  <h1 class="mt-5">About Us</h1>
  <p>Welcome to CJ's Skateboard Shop! We're more than just a shop – we’re a community dedicated to all things skateboarding.</p>

  <section class="mt-4">
    <h2>Our Story</h2>
    <p>Founded in 2024, our shop started with a passion for skateboarding and a desire to create a space where skaters of all levels can come together. Whether you're a beginner or a seasoned pro, our mission is to provide the best products and support to help you hit the streets with style and confidence.</p>
  </section>

  <section class="mt-4">
    <h2>Our Mission</h2>
    <p>We believe skateboarding is for everyone, and our mission is to make quality skate products accessible and affordable. From decks and wheels to apparel and accessories, we carefully select products to meet the needs of every skater who walks through our doors or visits us online.</p>
  </section>

  <section class="mt-4">
    <h2>Meet the Team</h2>
    <p>Our team is made up of passionate skaters, builders, and enthusiasts who bring their expertise and love for skateboarding into everything we do. We're here to offer personalized advice, expert product recommendations, and to share the skate culture we love.</p>
  </section>

  <section class="mt-4">
    <h2>Our Commitment to You</h2>
    <p>At CJ's Skateboard Shop, our customers come first. We're dedicated to providing high-quality products, exceptional customer service, and a welcoming environment where all skaters feel at home. If there's anything you need, we’re here to help.</p>
  </section>

  <footer class="mt-5 mb-3">
    <p>&copy; 2024 CJ's Skateboard Shop. All rights reserved.</p>
  </footer>
</div>

</body>


Change file: mainscreen.html. Lines 20 - 22 added a button underneath the shops title to create a link to the about.html file from the mainscreen.html file .

<div class="container">
        <a class="btn btn-primary mt-3" th:href="@{/about}">Learn More About Us</a>
    </div>


## E.  Add a sample inventory appropriate for your chosen store to the application. You should have five parts and five products in your sample inventory and should not overwrite existing data in the database.


Note: Make sure the sample inventory is added only when both the part and product lists are empty. When adding the sample inventory appropriate for the store, the inventory is stored in a set so duplicate items cannot be added to your products. When duplicate items are added, make a “multi-pack” part.


## F.  Add a “Buy Now” button to your product list. Your “Buy Now” button must meet each of the following parameters:
•  The “Buy Now” button must be next to the buttons that update and delete products.
•  The button should decrement the inventory of that product by one. It should not affect the inventory of any of the associated parts.
•  Display a message that indicates the success or failure of a purchase.


## G.  Modify the parts to track maximum and minimum inventory by doing the following:
•  Add additional fields to the part entity for maximum and minimum inventory.
•  Modify the sample inventory to include the maximum and minimum fields.
•  Add to the InhousePartForm and OutsourcedPartForm forms additional text inputs for the inventory so the user can set the maximum and minimum values.
•  Rename the file the persistent storage is saved to.
•  Modify the code to enforce that the inventory is between or at the minimum and maximum value.


## H.  Add validation for between or at the maximum and minimum fields. The validation must include the following:
•  Display error messages for low inventory when adding and updating parts if the inventory is less than the minimum number of parts.
•  Display error messages for low inventory when adding and updating products lowers the part inventory below the minimum.
•  Display error messages when adding and updating parts if the inventory is greater than the maximum.


## I.  Add at least two unit tests for the maximum and minimum fields to the PartTest class in the test package.


## J.  Remove the class files for any unused validators in order to clean your code.