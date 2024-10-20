package hr.omega.purchase_agreement_service.feature.user;

import hr.omega.purchase_agreement_service.authentication.JwtService;
import hr.omega.purchase_agreement_service.validation.ValidationException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserResourceService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  @Lazy private final AuthenticationManager authenticationManager;
  private final UserMapper userMapper;
  private final JwtService jwtService;

  public Optional<User> findByUsername(String username) {
    return userRepository.findByUsername(username);
  }

  public void register(UserRequest request) {
    User user = userMapper.toEntity(request);
    if (!findByUsername(user.getUsername()).isEmpty())
      throw new ValidationException("user.usernameAlreadyExists");
    user.setPassword(passwordEncoder.encode(request.getPassword()));
    userRepository.save(user);
  }

  public LoginResponse login(UserRequest request) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
    LoginResponse response = new LoginResponse();
    response.setToken(jwtService.generateToken(request.getUsername()));
    response.setExpiresIn(jwtService.getExpirationTime());
    return response;
  }
}
