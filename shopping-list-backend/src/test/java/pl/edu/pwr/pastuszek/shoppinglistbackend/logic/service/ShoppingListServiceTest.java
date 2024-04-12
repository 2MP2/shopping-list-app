package pl.edu.pwr.pastuszek.shoppinglistbackend.logic.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import pl.edu.pwr.pastuszek.shoppinglistbackend.logic.repositorie.BillRepository;
import pl.edu.pwr.pastuszek.shoppinglistbackend.logic.repositorie.ShoppingListRepository;
import pl.edu.pwr.pastuszek.shoppinglistbackend.logic.service.filter.ShoppingListSpecifications;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.dto.mapper.ShoppingListDTOMapper;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.dto.request.ShoppingListRequestDTO;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.dto.response.ShoppingListResponseDTO;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.entity.ShoppingList;
import pl.edu.pwr.pastuszek.shoppinglistbackend.security.UserAuthentication;

import java.util.UUID;

@SpringJUnitConfig
class ShoppingListServiceTest {

    @MockBean
    private ShoppingListRepository repository;
    @MockBean
    private ShoppingListDTOMapper mapper;
    private ShoppingListSpecifications shoppingListSpecifications;
    @MockBean
    private UserAuthentication userAuthentication;
    @MockBean
    private BillRepository billRepository;

    private ShoppingListService shoppingListService;
    @BeforeEach
    void setUp() {
        shoppingListSpecifications = new ShoppingListSpecifications();
        shoppingListService = new ShoppingListService(repository,mapper,shoppingListSpecifications,userAuthentication,billRepository);
    }

    @Test
    @DisplayName("Should not add when user is not in organization")
    void shouldNotAddWhenUserIsNotInOrganization() {
        ShoppingListRequestDTO shoppingListRequestDTO = Mockito.mock(ShoppingListRequestDTO.class);
        Mockito.when(shoppingListRequestDTO.getOrganizationId()).thenReturn("6a417881-b85f-46c1-b20c-371399f55c38");
        Mockito.when(userAuthentication.isAdmin()).thenReturn(false);
        UUID organizationId = UUID.fromString(shoppingListRequestDTO.getOrganizationId());
        Mockito.when(userAuthentication.isCurrentUserInOrganization(organizationId)).thenReturn(false);
        Assertions.assertThrows(AccessDeniedException.class, () -> {
            shoppingListService.add(shoppingListRequestDTO);
        });
    }

    @Test
    @DisplayName("Should add when user is in organization")
    void shouldAddWhenUserIsInOrganization() {
        ShoppingListRequestDTO shoppingListRequestDTO = Mockito.mock(ShoppingListRequestDTO.class);
        ShoppingListResponseDTO shoppingListResponseDTO = Mockito.mock(ShoppingListResponseDTO.class);
        ShoppingList shoppingList = Mockito.mock(ShoppingList.class);
        Mockito.when(shoppingListRequestDTO.getOrganizationId()).thenReturn("6a417881-b85f-46c1-b20c-371399f55c38");
        Mockito.when(userAuthentication.isAdmin()).thenReturn(false);
        UUID organizationId = UUID.fromString(shoppingListRequestDTO.getOrganizationId());
        Mockito.when(userAuthentication.isCurrentUserInOrganization(organizationId)).thenReturn(true);
        Mockito.when(mapper.convertDtoToFullEntity(shoppingListRequestDTO)).thenReturn(shoppingList);
        Mockito.when(mapper.convertEntityToDTO(shoppingList)).thenReturn(shoppingListResponseDTO);
        Mockito.when(repository.save(shoppingList)).thenReturn(shoppingList);
        shoppingListService.add(shoppingListRequestDTO);
    }

    @Test
    @DisplayName("Should add when user is Admin")
    void shouldAddWhenUserIsAdmin() {
        ShoppingListRequestDTO shoppingListRequestDTO = Mockito.mock(ShoppingListRequestDTO.class);
        ShoppingListResponseDTO shoppingListResponseDTO = Mockito.mock(ShoppingListResponseDTO.class);
        ShoppingList shoppingList = Mockito.mock(ShoppingList.class);
        Mockito.when(shoppingListRequestDTO.getOrganizationId()).thenReturn("6a417881-b85f-46c1-b20c-371399f55c38");
        Mockito.when(userAuthentication.isAdmin()).thenReturn(true);
        Mockito.when(mapper.convertDtoToFullEntity(shoppingListRequestDTO)).thenReturn(shoppingList);
        Mockito.when(mapper.convertEntityToDTO(shoppingList)).thenReturn(shoppingListResponseDTO);
        Mockito.when(repository.save(shoppingList)).thenReturn(shoppingList);
        shoppingListService.add(shoppingListRequestDTO);
    }
}