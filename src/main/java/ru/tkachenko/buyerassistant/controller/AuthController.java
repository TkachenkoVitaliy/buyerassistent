package ru.tkachenko.buyerassistant.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.tkachenko.buyerassistant.security.entity.ERole;
import ru.tkachenko.buyerassistant.security.entity.RefreshToken;
import ru.tkachenko.buyerassistant.security.entity.Role;
import ru.tkachenko.buyerassistant.security.entity.User;
import ru.tkachenko.buyerassistant.security.exception.TokenRefreshException;
import ru.tkachenko.buyerassistant.security.jwt.JwtUtils;
import ru.tkachenko.buyerassistant.security.payload.request.LogOutRequest;
import ru.tkachenko.buyerassistant.security.payload.request.LoginRequest;
import ru.tkachenko.buyerassistant.security.payload.request.SignupRequest;
import ru.tkachenko.buyerassistant.security.payload.request.TokenRefreshRequest;
import ru.tkachenko.buyerassistant.security.payload.response.JwtResponse;
import ru.tkachenko.buyerassistant.security.payload.response.MessageResponse;
import ru.tkachenko.buyerassistant.security.payload.response.TokenRefreshResponse;
import ru.tkachenko.buyerassistant.security.repository.RoleRepository;
import ru.tkachenko.buyerassistant.security.repository.UserRepository;
import ru.tkachenko.buyerassistant.security.service.RefreshTokenService;
import ru.tkachenko.buyerassistant.security.service.UserDetailsImpl;
import ru.tkachenko.buyerassistant.total.settings.entity.TotalUserSettingsEntity;
import ru.tkachenko.buyerassistant.total.settings.repository.TotalUserSettingsRepository;
import ru.tkachenko.buyerassistant.utils.CurrentDate;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)//TODO for frontend app
@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    TotalUserSettingsRepository totalUserSettingsRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    RefreshTokenService refreshTokenService;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        String jwt = jwtUtils.generateJwtToken(userDetails);

        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());

        return ResponseEntity.ok(new JwtResponse(jwt, refreshToken.getToken(), userDetails.getId(),
                userDetails.getUsername(), userDetails.getEmail(), roles));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user's account
        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));

        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);

                        break;
                    case "mod":
                        Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(modRole);

                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);
        userRepository.save(user);

        CurrentDate currentDate = new CurrentDate();
        TotalUserSettingsEntity newUserSettings = new TotalUserSettingsEntity();
        newUserSettings.setUsername(user.getUsername());
        newUserSettings.setMonth(currentDate.getMonthInt() == 1 ? 12 : currentDate.getMonthInt() - 1);
        newUserSettings.setYear(currentDate.getMonthInt() == 1 ? currentDate.getYearInt() -1 : currentDate.getYearInt());
        totalUserSettingsRepository.save(newUserSettings);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    @PostMapping("/refreshtoken")
    public ResponseEntity<?> refreshtoken(@Valid @RequestBody TokenRefreshRequest request) {
        String requestRefreshToken = request.getRefreshToken();

        return refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String token = jwtUtils.generateTokenFromUsername(user.getUsername());
                    return ResponseEntity.ok(new TokenRefreshResponse(token, requestRefreshToken));
                })
                .orElseThrow(() -> new TokenRefreshException(requestRefreshToken,
                        "Refresh token is not in database!"));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser(@Valid @RequestBody LogOutRequest logOutRequest) {
        refreshTokenService.deleteByUserId(logOutRequest.getUserId());
        return ResponseEntity.ok(new MessageResponse("Log out successful!"));
    }
}
