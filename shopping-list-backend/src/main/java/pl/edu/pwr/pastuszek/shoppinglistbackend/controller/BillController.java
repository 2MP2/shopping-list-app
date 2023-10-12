package pl.edu.pwr.pastuszek.shoppinglistbackend.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.edu.pwr.pastuszek.shoppinglistbackend.logic.service.BillService;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.dto.request.BillRequestDTO;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.dto.response.BillResponseDTO;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("bill")
@AllArgsConstructor
public class BillController {
    private final BillService billService;

    @GetMapping
    public List<BillResponseDTO> getBillList() {
        return this.billService.list();
    }

    @GetMapping("{id}")
    public BillResponseDTO getBillById(@PathVariable("id") UUID id) {
        return this.billService.getOne(id);
    }

    @PostMapping
    public BillResponseDTO addBill(@RequestBody BillRequestDTO billRequestDTO) {
        return this.billService.add(billRequestDTO);
    }

    @PutMapping("{id}")
    public BillResponseDTO updateBill(@PathVariable("id") UUID id, @RequestBody BillRequestDTO billRequestDTO){
        return this.billService.update(id, billRequestDTO);
    }

    @DeleteMapping("{id}")
    public void deleteBillById(@PathVariable("id") UUID id){
        billService.delete(id);
    }
}
