package pl.edu.pwr.pastuszek.shoppinglistbackend.logic.service;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.edu.pwr.pastuszek.shoppinglistbackend.logic.repositorie.BaseRepository;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.entity.Bill;
@Service
public class BillService extends SimpleCrudService<Bill> {
    public BillService(BaseRepository<Bill> repository) {
        super(repository, LoggerFactory.getLogger(BillService.class));
    }
}
