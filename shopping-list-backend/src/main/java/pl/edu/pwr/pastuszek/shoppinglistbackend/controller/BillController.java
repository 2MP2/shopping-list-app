package pl.edu.pwr.pastuszek.shoppinglistbackend.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.edu.pwr.pastuszek.shoppinglistbackend.logic.service.BillService;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.entity.Bill;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("bill")
@AllArgsConstructor
public class BillController {
    private final BillService billService;

    @GetMapping
    public List<Bill> getBillList() {
        return this.billService.list();
    }

    @GetMapping("{id}")
    public Bill getBillById(@PathVariable("id") UUID id) {
        return this.billService.getOne(id);
    }

    @PostMapping
    public Bill addBill(@RequestBody Bill bill) {
        return this.billService.add(bill);
    }

    @PutMapping("{id}")
    public Bill updateBill(@PathVariable("id") UUID id, @RequestBody Bill bill){
        return this.billService.update(id, bill);
    }

    @DeleteMapping("{id}")
    public void deleteBillById(@PathVariable("id") UUID id){
        billService.delete(id);
    }
}
