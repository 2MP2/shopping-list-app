package pl.edu.pwr.pastuszek.shoppinglistbackend.logic.service;

import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.edu.pwr.pastuszek.shoppinglistbackend.logic.repositorie.BillRepository;
import pl.edu.pwr.pastuszek.shoppinglistbackend.logic.repositorie.ShoppingListRepository;
import pl.edu.pwr.pastuszek.shoppinglistbackend.logic.service.filter.BillSpecifications;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.dto.mapper.BillDTOMapper;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.dto.request.BillRequestDTO;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.dto.response.BillResponseDTO;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.dto.response.TransactionResponseDTO;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.dto.response.UserResponseDTO;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.entity.Bill;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.entity.User;
import pl.edu.pwr.pastuszek.shoppinglistbackend.security.UserAuthentication;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BillService extends MappedCrudService<Bill, BillRequestDTO, BillResponseDTO> {
    private final ShoppingListRepository shoppingListRepository;

    public BillService(BillRepository repository,
                       BillDTOMapper mapper,
                       BillSpecifications billSpecifications,
                       UserAuthentication userAuthentication,
                       ShoppingListRepository shoppingListRepository
    ) {
        super(repository, LoggerFactory.getLogger(BillService.class), mapper, billSpecifications , userAuthentication);
        this.shoppingListRepository = shoppingListRepository;
    }

    @Override
    protected boolean isValidToList(Map<String, String> params, Pageable pageable) {
        if(userAuthentication.isAdmin()) return true;

        String userString = params.get("user");
        String shoppingListString = params.get("shoppingList");

        if(userString == null && shoppingListString == null) return false;

        if(userString != null){
            UUID userId = UUID.fromString(userString);
            if(! userAuthentication.isCurrentUserHaveThisUUID(userId)) return false;
        }

        if(shoppingListString != null) {
            UUID shoppingListId = UUID.fromString(shoppingListString);
            return userAuthentication.isCurrentUserInOrganization(shoppingListRepository.findOrganizationIdById(shoppingListId));
        }

        return true;
    }

    @Override
    protected boolean isValidToGetOne(UUID id) {
        if(userAuthentication.isAdmin()) return true;
        return userAuthentication.isCurrentUserInOrganization(((BillRepository) repository).findOrganizationIdById(id));
    }

    @Override
    protected boolean isValidToAdd(BillRequestDTO billRequestDTO) {
        UUID userId = UUID.fromString(billRequestDTO.getUserId());
        UUID shoppingListId = UUID.fromString(billRequestDTO.getShoppingListId());

        if(! userAuthentication.isCurrentUserHaveThisUUID(userId)) return false;
        return userAuthentication.isCurrentUserInOrganization(shoppingListRepository.findOrganizationIdById(shoppingListId));
    }

    @Override
    protected boolean isValidToUpdate(Bill entity, BillRequestDTO billRequestDTO) {
        if(userAuthentication.isAdmin()) return true;

        UUID userId = UUID.fromString(billRequestDTO.getUserId());
        UUID shoppingListId = UUID.fromString(billRequestDTO.getShoppingListId());

        if(!userId.equals(entity.getUser().getId())) return false;
        if(!shoppingListId.equals(entity.getShoppingList().getId())) return false;

        if(! userAuthentication.isCurrentUserInOrganization(((BillRepository)repository).findOrganizationIdById(entity.getId()))) return false;
        if(! userAuthentication.isCurrentUserHaveThisUUID(userId)) return false;
        return userAuthentication.isCurrentUserInOrganization(shoppingListRepository.findOrganizationIdById(shoppingListId));
    }

    @Override
    protected boolean isValidToDelete(UUID id) {
        return isValidToGetOne(id);
    }

    public Map<UserResponseDTO, BigDecimal> calculateUserExpenses(UUID shoppingListId) {
        List<Bill> bills = ((BillRepository)repository).getBillsByShoppingListId(shoppingListId);
        // Calculate the total expenses for each user
        Map<UserResponseDTO, BigDecimal> userExpensesMap = bills.stream()
                .collect(Collectors.groupingBy(
                        bill -> {
                            User user = bill.getUser();
                            return UserResponseDTO.builder().id(user.getId()).name(user.getName()).surname(user.getSurname()).build();
                        },
                        Collectors.reducing(BigDecimal.ZERO, Bill::getAmount, BigDecimal::add)
                ));
        // Calculate the total expenses for the entire shopping list
        BigDecimal totalExpenses = bills.stream()
                .map(Bill::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Calculate the average expenses for the entire shopping list
        BigDecimal averageExpenses = totalExpenses.divide(BigDecimal.valueOf(userExpensesMap.size()), 2, RoundingMode.HALF_UP);
        // Adjust each user's expenses by subtracting the average expenses
        userExpensesMap.replaceAll((userId, expenses) -> expenses.subtract(averageExpenses));
        return userExpensesMap;
    }

    public List<TransactionResponseDTO> creatTransactionalList(UUID shoppingListId){
        Map<UserResponseDTO, BigDecimal> userExpensesMap = calculateUserExpenses(shoppingListId);

        Map<UserResponseDTO, BigDecimal> creditors = userExpensesMap.entrySet().stream()
                .filter(entry -> entry.getValue().compareTo(BigDecimal.ZERO) > 0)
                .sorted(Comparator.comparing(Map.Entry::getValue, Comparator.reverseOrder()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

        Map<UserResponseDTO, BigDecimal> debtors = userExpensesMap.entrySet().stream()
                .filter(entry -> entry.getValue().compareTo(BigDecimal.ZERO) < 0)
                .sorted(Comparator.comparing(Map.Entry::getValue))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

        List<TransactionResponseDTO> transactions = new ArrayList<>();

        //add obvious transactions

        for (Map.Entry<UserResponseDTO, BigDecimal> debtorEntry : debtors.entrySet()) {
            BigDecimal debtorAmount = debtorEntry.getValue();
            for (Map.Entry<UserResponseDTO, BigDecimal> creditorEntry : creditors.entrySet()) {
                BigDecimal creditorAmount = creditorEntry.getValue();

                if (debtorAmount.equals(creditorAmount.abs())) {
                    transactions.add(new TransactionResponseDTO(debtorEntry.getKey(), creditorEntry.getKey(), debtorAmount));
                    debtorEntry.setValue(BigDecimal.ZERO);
                    creditorEntry.setValue(BigDecimal.ZERO);
                    break;
                }

                if(creditorAmount.compareTo(debtorAmount.abs()) < 0) break;
            }
        }

        for (Map.Entry<UserResponseDTO, BigDecimal> debtorEntry : debtors.entrySet()) {
            BigDecimal debtorAmount = debtorEntry.getValue();
            for (Map.Entry<UserResponseDTO, BigDecimal> creditorEntry : creditors.entrySet()) {
                BigDecimal creditorAmount = creditorEntry.getValue();

                if(debtorAmount.equals(BigDecimal.ZERO)) break;

                int compare = creditorAmount.compareTo(debtorAmount.abs());

                if(compare > 0){
                    transactions.add(new TransactionResponseDTO(debtorEntry.getKey(), creditorEntry.getKey(), debtorAmount.abs()));
                    debtorEntry.setValue(BigDecimal.ZERO);
                    creditorEntry.setValue(creditorAmount.add(debtorAmount));
                    break;
                }else if(compare < 0){
                    transactions.add(new TransactionResponseDTO(debtorEntry.getKey(), creditorEntry.getKey(), creditorAmount));
                    debtorEntry.setValue(creditorAmount.add(debtorAmount));
                    creditorEntry.setValue(BigDecimal.ZERO);
                }else {
                    transactions.add(new TransactionResponseDTO(debtorEntry.getKey(), creditorEntry.getKey(), debtorAmount.abs()));
                    debtorEntry.setValue(BigDecimal.ZERO);
                    creditorEntry.setValue(BigDecimal.ZERO);
                    break;
                }

            }
        }

        return transactions;
    }

}
