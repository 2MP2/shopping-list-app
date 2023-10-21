package pl.edu.pwr.pastuszek.shoppinglistbackend.logic.service;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.edu.pwr.pastuszek.shoppinglistbackend.logic.repositorie.BillRepository;
import pl.edu.pwr.pastuszek.shoppinglistbackend.logic.service.filter.BillSpecifications;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.dto.mapper.BillDTOMapper;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.dto.request.BillRequestDTO;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.dto.response.BillResponseDTO;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.entity.Bill;
@Service
public class BillService extends MappedCrudService<Bill, BillRequestDTO, BillResponseDTO> {

    public BillService(BillRepository repository, BillDTOMapper mapper, BillSpecifications billSpecifications) {
        super(repository, LoggerFactory.getLogger(BillService.class), mapper, billSpecifications);
    }
}
