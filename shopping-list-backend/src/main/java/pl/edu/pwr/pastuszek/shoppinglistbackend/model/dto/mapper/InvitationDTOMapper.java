package pl.edu.pwr.pastuszek.shoppinglistbackend.model.dto.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import pl.edu.pwr.pastuszek.shoppinglistbackend.logic.repositorie.OrganizationRepository;
import pl.edu.pwr.pastuszek.shoppinglistbackend.logic.repositorie.UserRepository;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.dto.request.InvitationRequestDTO;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.dto.response.InvitationResponseDTO;
import pl.edu.pwr.pastuszek.shoppinglistbackend.model.entity.Invitation;

@Component
public class InvitationDTOMapper extends DTOMapper<Invitation, InvitationRequestDTO, InvitationResponseDTO> {
    private final OrganizationRepository organizationRepository;
    private final UserRepository userRepository;
    public InvitationDTOMapper(ModelMapper modelMapper, OrganizationRepository organizationRepository, UserRepository userRepository) {
        super(modelMapper);
        this.organizationRepository = organizationRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Invitation convertDtoToEmptyEntity(InvitationRequestDTO dto) {
        return this.modelMapper.map(dto, Invitation.class);
    }

    @Override
    public Invitation convertDtoToFullEntity(InvitationRequestDTO dto) {
        Invitation invitation = convertDtoToEmptyEntity(dto);
        invitation.setOrganization(
                organizationRepository.findById(invitation.getOrganization().getId())
                        .orElseThrow(()-> new IllegalStateException(
                                "organization with id: " + invitation.getOrganization().getId() + " dose not exists"
                        )));


        String number = invitation.getUser().getNumber();
        if(number!=null){
            invitation.setUser(
                    userRepository.findByNumber(number)
                            .orElseThrow(()-> new IllegalStateException(
                                    "user with number: " + number + " dose not exists"
                            )));
        }else{
            invitation.setUser(
                    userRepository.findByEmail(invitation.getUser().getEmail())
                            .orElseThrow(()-> new IllegalStateException(
                                    "user with id: " + invitation.getUser().getEmail() + " dose not exists"
                            )));
        }


        return invitation;
    }

    @Override
    public InvitationResponseDTO convertEntityToDTO(Invitation entity) {
        return this.modelMapper.map(entity, InvitationResponseDTO.class);
    }
}
