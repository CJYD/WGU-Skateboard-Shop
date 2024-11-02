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

Change file: BootStrapData.java. Lines 42 - 99 added the products and parts to the database. I used an if statement with count() == 0 as a conditional check to see if any previous parts were loaded to the database.
If no products or parts have been loaded it will automatically load in the productRepository and the partRepository.

        List<OutsourcedPart> outsourcedParts=(List<OutsourcedPart>) outsourcedPartRepository.findAll();
        for(OutsourcedPart part:outsourcedParts){
            System.out.println(part.getName()+" "+part.getCompanyName());
        }

        if (productRepository.count() == 0) {
            Product cruiser= new Product(1,"Cruiser SB",60.99,13);
            Product miniCruiser= new Product(2,"Mini Cruiser SB",40.99,16);
            Product doubleKick= new Product(3,"Double Kick SB",70.99,14);
            Product carver= new Product(4,"Carver SB",65.99,18);
            Product cruiserLB= new Product(5,"Cruiser LB",95.99,6);

            productRepository.save(cruiser);
            productRepository.save(miniCruiser);
            productRepository.save(doubleKick);
            productRepository.save(carver);
            productRepository.save(cruiserLB);
        }

        if (partRepository.count() == 0) {
            InhousePart trucks = new InhousePart();
            trucks.setPartId(1);
            trucks.setName("Trucks");
            trucks.setPrice(20.99);
            trucks.setInv(40);
            partRepository.save(trucks);

            InhousePart wheels = new InhousePart();
            wheels.setPartId(2);
            wheels.setName("Wheels");
            wheels.setPrice(30.99);
            wheels.setInv(20);
            partRepository.save(wheels);

            InhousePart gripTape = new InhousePart();
            gripTape.setPartId(3);
            gripTape.setName("Grip Tape");
            gripTape.setPrice(15.99);
            gripTape.setInv(10);
            partRepository.save(gripTape);

            InhousePart deck = new InhousePart();
            deck.setPartId(4);
            deck.setName("Deck");
            deck.setPrice(40.99);
            deck.setInv(20);
            partRepository.save(deck);

            InhousePart hardware = new InhousePart();
            hardware.setPartId(5);
            hardware.setName("Hardware");
            hardware.setPrice(5.99);
            hardware.setInv(50);
            partRepository.save(hardware);
        }

        System.out.println("Started in Bootstrap");
        System.out.println("Number of Products"+productRepository.count());
        System.out.println(productRepository.findAll());
        System.out.println("Number of Parts"+partRepository.count());
        System.out.println(partRepository.findAll());

Change file: AddProductController lines 18,  52 - 112. This controller now handles checking if the same product name exist and if so creating a "multi-pack" product with the modified name.
Added to the method conditional checks to properly add a product or to properly update a product.

        import java.util.HashSet;

            @PostMapping("/showFormAddProduct")
        public String submitForm(@Valid @ModelAttribute("product") Product product, BindingResult bindingResult, Model theModel) {
        theModel.addAttribute("product", product);

        if(bindingResult.hasErrors()){
            ProductService productService = context.getBean(ProductServiceImpl.class);
            Product product2 = new Product();
            try {
                product2 = productService.findById((int) product.getId());
            } catch (Exception e) {
                System.out.println("Error Message " + e.getMessage());
            }
            theModel.addAttribute("parts", partService.findAll());
            List<Part>availParts=new ArrayList<>();
            for(Part p: partService.findAll()){
                if(!product2.getParts().contains(p))availParts.add(p);
            }
            theModel.addAttribute("availparts",availParts);
            theModel.addAttribute("assparts",product2.getParts());
            return "productForm";
        }
        else {
            ProductService repo = context.getBean(ProductServiceImpl.class);
            PartService partService1 = context.getBean(PartServiceImpl.class);

            if (product.getId() != 0) {
                Product existingProduct = repo.findById((int) product.getId());
                existingProduct.setName(product.getName());
                existingProduct.setPrice(product.getPrice());
                existingProduct.setInv(product.getInv());
                existingProduct.setParts(product.getParts());

                if (product.getInv() - existingProduct.getInv() > 0) {
                    for (Part p : existingProduct.getParts()) {
                        int inv = p.getInv();
                        p.setInv(inv - (product.getInv() - existingProduct.getInv()));
                        partService1.save(p);
                    }
                }
                repo.save(existingProduct);
                return "confirmationaddproduct";
            }
            else {
                Product existingProduct = repo.findAll().stream().filter(existing -> existing.getName().equals(product.getName()))
                        .findFirst()
                        .orElse(null);

                if (existingProduct != null) {
                    Product multiPackProduct = new Product();
                    multiPackProduct.setName(product.getName() + " mulit-pack");
                    multiPackProduct.setPrice(existingProduct.getPrice() * product.getInv());
                    multiPackProduct.setInv(product.getInv());
                    multiPackProduct.setParts(new HashSet<>(existingProduct.getParts()));
                    repo.save(multiPackProduct);
                    return "confirmationaddproduct";

                } else {
                    repo.save(product);
                }
                return "confirmationaddproduct";
            }

Change file: InhousePartController lines 41 - 67. The InhousePartController handles checking if the same in-house part name exist and if so creating a "multi-pack" part with the modified name.

        @PostMapping("/showFormAddInPart")
        public String submitForm(@Valid @ModelAttribute("inhousepart") InhousePart part, BindingResult theBindingResult, Model theModel) {
        theModel.addAttribute("inhousepart", part);
        if (theBindingResult.hasErrors()) {
            return "InhousePartForm";
        } else {
            InhousePartService repo = context.getBean(InhousePartServiceImpl.class);
            InhousePart ip = repo.findAll().stream().filter(existing->existing.getName().equals(part.getName()))
                    .findFirst().orElse(null);

            if (ip != null && ip.getName().equals(part.getName())) {
                InhousePart multiPackPart = new InhousePart();
                multiPackPart.setName(ip.getName() + " multi-pack");
                multiPackPart.setPrice(ip.getPrice() * part.getInv());
                multiPackPart.setInv(part.getInv());
                multiPackPart.setPartId(ip.getPartId());

                repo.save(multiPackPart);
                return "confirmationaddpart";
            } else {
                if (ip != null) part.setProducts(ip.getProducts());
                repo.save(part);

                return "confirmationaddpart";
            }
        }

Change file: OutsourcedPartController lines 40 - 65. The OutsourcedPartController handles checking if the same outsourced part name exist and if so creating a "multi-pack" part with the modified name.

        @PostMapping("/showFormAddOutPart")
        public String submitForm(@Valid @ModelAttribute("outsourcedpart") OutsourcedPart part, BindingResult bindingResult, Model theModel) {
        theModel.addAttribute("outsourcedpart", part);
        if (bindingResult.hasErrors()) {
            return "OutsourcedPartForm";
        } else {
            OutsourcedPartService repo = context.getBean(OutsourcedPartServiceImpl.class);
            OutsourcedPart op = repo.findAll().stream().filter(existing -> existing.getName().equals(part.getName()))
                    .findFirst().orElse(null);

            if (op != null && op.getName().equals(part.getName())) {
                OutsourcedPart multiPackPart = new OutsourcedPart();
                multiPackPart.setName(op.getName() + " multi-pack");
                multiPackPart.setPrice(op.getPrice() * part.getInv());
                multiPackPart.setInv(part.getInv());
                multiPackPart.setCompanyName(op.getCompanyName());

                repo.save(multiPackPart);
                return "confirmationaddpart";
            } else {
                if (op != null) part.setProducts(op.getProducts());
                repo.save(part);
                return "confirmationaddpart";
            }
        }

Change file: InhousePartForm lines 7 - 11, 35 - 36. Updated CSS to match throughout.

        <meta name="viewport" content="width=device-width, initial-scale=1">
        link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
        integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous"
        <a href="http://localhost:8080/" class="btn btn-primary btn-sm mb-3">Link
        to Main Screen</a>

Change file: OutsourcedPartForm lines 7 - 11, 34 - 35. Updated CSS to match throughout.

        <meta name="viewport" content="width=device-width, initial-scale=1">
        link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
        integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous"
        <a href="http://localhost:8080/" class="btn btn-primary btn-sm mb-3">Link
        to Main Screen</a>

Change file: productForm lines 8 - 9, 77. Updated CSS to match throughout.

        link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
        integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous"
        <a href="http://localhost:8080/" class="btn btn-primary btn-sm mb-3">Link
        to Main Screen</a>


## F.  Add a “Buy Now” button to your product list. Your “Buy Now” button must meet each of the following parameters:
•  The “Buy Now” button must be next to the buttons that update and delete products.
•  The button should decrement the inventory of that product by one. It should not affect the inventory of any of the associated parts.
•  Display a message that indicates the success or failure of a purchase.

Created file: buynow.html lines 1 - 21. This file creates the page after a successful purchase.

        <!DOCTYPE html>
        <html lang="en">
        <head>
          <meta charset="UTF-8">
          <meta name="viewport" content="width=device-width, initial-scale=1.0">
          <title>Successful Purchase</title>
          <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
                integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
        </head>
        <body>
        <div class="container">
        <h1>CJ's Skateboard Shop</h1>
          <div class="container">
            <a class="btn btn-primary mt-3" href="http://localhost:8080">Return Home</a>
          </div>
        <hr>
        <p>Thank you for shopping with us! Order details will be sent shortly!</p>
        </div>
        
        </body>
        </html>

Created file: unsuccessfulbuynow.html lines 1 - 20. This file creates the page after an unsuccessful purchase.

        <!DOCTYPE html>
        <html lang="en">
        <head>
          <meta charset="UTF-8">
          <meta name="viewport" content="width=device-width, initial-scale=1.0">
          <title>Unsuccessful Purchase</title>
          <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
                integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
        </head>
        <body>
        <div class="container">
        <h1>CJ's Skateboard Shop</h1>
        <hr>
        <p>This item is out-of-stock. Please try again later.</p>
        
        <a href="http://localhost:8080/" class="btn btn-primary btn-sm mb-3">Link
        to Main Screen</a>
        </div>
        </body>
        </html>

Changed file: mainscreen.html lines 85. Added the button next to the update and delete buttons. This button calls the buyNow method.

        <td><a th:href="@{/buyNow(productID=${tempProduct.id})}" class="btn btn-primary btn-sm mb-3">Buy Now</a>

Changed file: AddProductController lines 196 - 209. This added the buyNow method that decrements the inventory by 1 when the user clicks the "Buy Now" button of a specific product.

        @GetMapping("/buyNow")
        public String buyNow(@RequestParam("productID") int theID, Model theModel) {
            ProductService repo = context.getBean(ProductServiceImpl.class);
            Product product3=repo.findById(theID);
            if (product3 != null && product3.getInv() > 0) {
                int inv = product3.getInv();
                product3.setInv(inv - 1);
                repo.save(product3);
                return "buynow";
            }
            else {
                return "unsuccessfulbuynow";
            }
        }

## G.  Modify the parts to track maximum and minimum inventory by doing the following:
•  Add additional fields to the part entity for maximum and minimum inventory.
•  Modify the sample inventory to include the maximum and minimum fields.
•  Add to the InhousePartForm and OutsourcedPartForm forms additional text inputs for the inventory so the user can set the maximum and minimum values.
•  Rename the file the persistent storage is saved to.
•  Modify the code to enforce that the inventory is between or at the minimum and maximum value. 

Create file: @ValidInventoryRange lines 1 - 16. This ensures that minInv and maxInv are withing the range.
        
        package com.example.demo.validators;
        
        import javax.validation.Constraint;
        import javax.validation.Payload;
        import java.lang.annotation.*;
        
        @Documented
        @Constraint(validatedBy = InventoryRangeValidator.class)
        @Target({ElementType.TYPE})
        @Retention(RetentionPolicy.RUNTIME)
        public @interface ValidInventoryRange {
        String message() default "Inventory must be between minimum and maximum values";
        Class<?>[] groups() default {};
        Class<? extends Payload>[] payload() default {};
        }

Create file: InventoryRangeValidator.java lines 1 - 17. This check whether the minInv or maxInv is following the sample data constraints in the BootStrapData.java class.
Also this is where the @ValidInventoryRange gets enforced.

        package com.example.demo.validators;
        
        import com.example.demo.domain.Part;
        import javax.validation.ConstraintValidator;
        import javax.validation.ConstraintValidatorContext;
        
        public class InventoryRangeValidator implements ConstraintValidator<ValidInventoryRange, Part> {

        @Override
        public boolean isValid(Part part, ConstraintValidatorContext context) {
            if (part.getInv() < part.getMinInv() || part.getInv() > part.getMaxInv()) {
                return false;
            }
            else {
                return true;

Change file: Part.java lines 4, 21, 33 - 37, 50 - 51, 59 - 60, 87 - 107. To start you need to import the ValidInventoryRange. Import the ValidInventoryRange annotation as well.
Next convert all the primitive int values to Integer for to allow the field to be set to null if necessary, this helps avoid any unknown errors. The @Min and @Max annotations ensure that the inventory values fall within the specified range.
Then the adjusted constructors to account for the new minInv and maxInv variables to initialize the variables. The new Getter/Setter methods are also adjusted with the new Integer types and with the new min and max variables.

        import com.example.demo.validators.ValidInventoryRange;

        @ValidInventoryRange

        private Integer inv;
        @Min(value = 0, message = "Inventory value must be positive")
        private Integer minInv;
        private Integer maxInv;

        this.minInv = minInv;
        this.maxInv = maxInv;
        this.minInv = minInv;
        this.maxInv = maxInv;

        public Integer getInv() {
        return inv;
        }
        
        public void setInv(Integer inv) {
        this.inv = inv;
        }
        
        public Integer getMinInv() {
        return minInv;
        }
        
        public void setMinInv(Integer minInv) {
        this.minInv = minInv;
        }

        public Integer getMaxInv() {
        return maxInv;
        }
        
        public void setMaxInv(Integer maxInv) {
        this.maxInv = maxInv;
        }

Change file: BootStrapData.java lines 70 - 71, 79 - 80, 88 - 89, 97 - 98, 106 - 107. This modifies the sample inventory and includes the maximum and minimum fields of each product.

        trucks.setMinInv(1);
        trucks.setMaxInv(100);
        
        wheels.setMinInv(1);
        wheels.setMaxInv(100);

        gripTape.setMinInv(1);
        gripTape.setMaxInv(100);

        deck.setMinInv(1);
        deck.setMaxInv(100);

        hardware.setMinInv(1);
        hardware.setMaxInv(100);

Changed file: InhousePartForm lines 28 - 33. Added the additional text input boxes so that the user can set minimum or maximum values.

        <p><input type="text" th:field="*{minInv}" placeholder="Minimum Inventory" class="form-control mb-4 col-4"/></p>
        <p th:if="${#fields.hasErrors('minInv')}" th:errors="*{minInv}">Minimum Inventory Error</p>
    
        <p><input type="text" th:field="*{maxInv}" placeholder="Maximum Inventory" class="form-control mb-4 col-4"/></p>
        <p th:if="${#fields.hasErrors('maxInv')}" th:errors="*{maxInv}">Maximum Inventory Error</p>

Changed file: OutsourcedPartForm lines 28 - 33. Added the additional text input boxes so that the user can set minimum or maximum values.

        <p><input type="text" th:field="*{minInv}" placeholder="Minimum Inventory" class="form-control mb-4 col-4"/></p>
        <p th:if="${#fields.hasErrors('minInv')}" th:errors="*{minInv}">Minimum Inventory Error</p>

        <p><input type="text" th:field="*{maxInv}" placeholder="Maximum Inventory" class="form-control mb-4 col-4"/></p>
        <p th:if="${#fields.hasErrors('maxInv')}" th:errors="*{maxInv}">Maximum Inventory Error</p>

Changed file: application.properties lines 6. Changed the URL from spring-boot-h2-db102 to cjs-skateboard-shop.

        spring.datasource.url=jdbc:h2:file:~/cjs-skateboard-shop


## H.  Add validation for between or at the maximum and minimum fields. The validation must include the following:
•  Display error messages for low inventory when adding and updating parts if the inventory is less than the minimum number of parts.
•  Display error messages for low inventory when adding and updating products lowers the part inventory below the minimum.
•  Display error messages when adding and updating parts if the inventory is greater than the maximum.

Changed file: InventoryRangeValidator lines 9 - 18 were commented out to allow the new validator to work. Lines 19 - 38 are the new validation messages.
These display error messages if the user does not input inventory correctly.

        /* @Override
        public boolean isValid(Part part, ConstraintValidatorContext context) {
        if (part.getInv() < part.getMinInv() || part.getInv() > part.getMaxInv()) {
        return false;
        }
        else {
        return true;
        }
        }
        */

        @Override
        public boolean isValid(Part part, ConstraintValidatorContext context ) {
        boolean isValid = true;

        if (part.getInv() < part.getMinInv()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Inventory cannot be lower than the minimum inventory.")
                    .addPropertyNode("inv")
                    .addConstraintViolation();
            isValid = false;
        }
        if (part.getInv() > part.getMaxInv()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Inventory cannot be higher than the maximum inventory.")
                    .addPropertyNode("inv")
                    .addConstraintViolation();
            isValid = false;
        }
        return isValid;
        }

## I.  Add at least two unit tests for the maximum and minimum fields to the PartTest class in the test package.


## J.  Remove the class files for any unused validators in order to clean your code.