package com.example.demo.bootstrap;

import com.example.demo.domain.InhousePart;
import com.example.demo.domain.OutsourcedPart;
import com.example.demo.domain.Part;
import com.example.demo.domain.Product;
import com.example.demo.repositories.OutsourcedPartRepository;
import com.example.demo.repositories.PartRepository;
import com.example.demo.repositories.ProductRepository;
import com.example.demo.service.OutsourcedPartService;
import com.example.demo.service.OutsourcedPartServiceImpl;
import com.example.demo.service.ProductService;
import com.example.demo.service.ProductServiceImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 *
 *
 *
 *
 */
@Component
public class BootStrapData implements CommandLineRunner {

    private final PartRepository partRepository;
    private final ProductRepository productRepository;

    private final OutsourcedPartRepository outsourcedPartRepository;

    public BootStrapData(PartRepository partRepository, ProductRepository productRepository, OutsourcedPartRepository outsourcedPartRepository) {
        this.partRepository = partRepository;
        this.productRepository = productRepository;
        this.outsourcedPartRepository=outsourcedPartRepository;
    }

    @Override
    public void run(String... args) throws Exception {

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
            trucks.setMinInv(1);
            trucks.setMaxInv(100);
            partRepository.save(trucks);

            InhousePart wheels = new InhousePart();
            wheels.setPartId(2);
            wheels.setName("Wheels");
            wheels.setPrice(30.99);
            wheels.setInv(20);
            wheels.setMinInv(1);
            wheels.setMaxInv(100);
            partRepository.save(wheels);

            InhousePart gripTape = new InhousePart();
            gripTape.setPartId(3);
            gripTape.setName("Grip Tape");
            gripTape.setPrice(15.99);
            gripTape.setInv(10);
            gripTape.setMinInv(1);
            gripTape.setMaxInv(100);
            partRepository.save(gripTape);

            InhousePart deck = new InhousePart();
            deck.setPartId(4);
            deck.setName("Deck");
            deck.setPrice(40.99);
            deck.setInv(20);
            deck.setMinInv(1);
            deck.setMaxInv(100);
            partRepository.save(deck);

            InhousePart hardware = new InhousePart();
            hardware.setPartId(5);
            hardware.setName("Hardware");
            hardware.setPrice(5.99);
            hardware.setInv(50);
            hardware.setMinInv(1);
            hardware.setMaxInv(100);
            partRepository.save(hardware);
        }

        System.out.println("Started in Bootstrap");
        System.out.println("Number of Products"+productRepository.count());
        System.out.println(productRepository.findAll());
        System.out.println("Number of Parts"+partRepository.count());
        System.out.println(partRepository.findAll());
    }
}
