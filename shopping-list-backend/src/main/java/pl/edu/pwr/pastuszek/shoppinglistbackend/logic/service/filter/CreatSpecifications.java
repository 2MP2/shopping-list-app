package pl.edu.pwr.pastuszek.shoppinglistbackend.logic.service.filter;

import org.springframework.data.jpa.domain.Specification;

import java.util.Map;

public interface CreatSpecifications<T> {
    default Specification<T> creat(Map<String, String> params){
        return Specification.where(null);
    }
}
