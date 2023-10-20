package pl.edu.pwr.pastuszek.shoppinglistbackend.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import pl.edu.pwr.pastuszek.shoppinglistbackend.logic.service.BillService;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.dto.request.BillRequestDTO;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.dto.response.BillResponseDTO;
import pl.edu.pwr.pastuszek.shoppinglistbackend.security.annotation.ForLoggedIn;

import java.util.UUID;

@ForLoggedIn
@RestController
@RequestMapping("bill")
@AllArgsConstructor
public class BillController {
    private final BillService billService;

    @GetMapping
    public Page<BillResponseDTO> getBillList(Pageable pageable) {
        return this.billService.list(pageable);
    }

    @GetMapping("{id}")
    public BillResponseDTO getBillById(@PathVariable("id") UUID id) {
        return this.billService.getOne(id);
    }

    @PostMapping
    public BillResponseDTO addBill(@Valid @RequestBody BillRequestDTO billRequestDTO) {
        return this.billService.add(billRequestDTO);
    }

    @PutMapping("{id}")
    public BillResponseDTO updateBill(@Valid @PathVariable("id") UUID id, @RequestBody BillRequestDTO billRequestDTO){
        return this.billService.update(id, billRequestDTO);
    }

    @DeleteMapping("{id}")
    public void deleteBillById(@PathVariable("id") UUID id){
        billService.delete(id);
    }
}
