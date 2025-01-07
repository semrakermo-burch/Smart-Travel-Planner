package ibu.aisi.smart_travel_planner.core.service;


import ibu.aisi.smart_travel_planner.core.dto.UserDto;
import ibu.aisi.smart_travel_planner.core.model.User;
import ibu.aisi.smart_travel_planner.core.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final ModelMapper mapper;

    public UserService(UserRepository userRepository, ModelMapper mapper) {
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    public UserDto getUserById(Long id) {
        return userRepository.findById(id)
                .map(this::mapToDto)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public UserDto getUserByEmail(String email) {
        User a = userRepository.findByEmail(email).get();
        System.out.println(a);
        UserDto b = mapToDto(a);
        System.out.println(b);
        UserDto userDto = new UserDto();
        userDto.setId(a.getId());
        userDto.setEmail(a.getEmail());
        userDto.setFirstName(a.getFirstName());
        userDto.setLastName(a.getLastName());

        System.out.println("Mapped UserDto manually: " + userDto);
        return userRepository.findByEmail(email)
                .map(this::mapToDto)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    private UserDto mapToDto(User user) {
        return mapper.map(user, UserDto.class);
    }
}

