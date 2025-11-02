package com.example.demo.service.impl;

import com.example.demo.dto.UserDto;
import com.example.demo.dto.request.UserRequestDto;
import com.example.demo.entity.Rol;
import com.example.demo.entity.User;
import com.example.demo.exception.DocumentValidationException;
import com.example.demo.repository.RolRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import com.example.demo.validate.DocumentValidator;
import com.example.demo.validate.EmailValidator;
import com.example.demo.validate.PhoneValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repo;
    private final RolRepository rolRepo;
    private final DocumentValidator documentValidator;
    private final EmailValidator emailValidator;
    private final PhoneValidator phoneValidator;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Value("${app.upload.dir}")
    private String uploadDir;

    @Override
    public List<UserDto> getUsers() {
        return repo.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public Optional<UserDto> getUser(Long id) {
        return repo.findById(id).map(this::toDto);
    }

    @Override
    public UserDto create(UserRequestDto r) {
        validateAll(r, null);

        User u = User.builder()
                .name(r.getName())
                .documentType(r.getDocumentType())
                .documentNumber(r.getDocumentNumber().trim())
                .address(emptyToNull(r.getAddress()))
                .cellphone(emptyToNull(r.getCellphone()))
                .email(emptyToNull(r.getEmail()))
                .login(r.getLogin().trim())
                .key(encoder.encode(Objects.requireNonNullElse(r.getPassword(), "")))
                .weight(emptyToNull(r.getWeight()))
                .birthdayDate(r.getBirthdayDate())
                .state(true)
                .dateCreated(LocalDate.now())
                .dateModified(LocalDate.now())
                .build();
        return toDto(repo.save(u));
    }

    @Override
    public UserDto update(Long id, UserRequestDto r) {
        User u = repo.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

        validateAll(r, id);

        u.setName(r.getName());
        u.setDocumentType(r.getDocumentType());
        u.setDocumentNumber(r.getDocumentNumber().trim());
        u.setAddress(emptyToNull(r.getAddress()));
        u.setCellphone(emptyToNull(r.getCellphone()));
        u.setEmail(emptyToNull(r.getEmail()));
        u.setLogin(r.getLogin().trim());
        u.setWeight(emptyToNull(r.getWeight()));
        u.setBirthdayDate(r.getBirthdayDate());
        u.setDateModified(LocalDate.now());

        if (r.getPassword() != null && !r.getPassword().isBlank()) {
            u.setKey(encoder.encode(r.getPassword()));
        }

        return toDto(repo.save(u));
    }

    @Override
    public void delete(Long id) {
        if (!repo.existsById(id)) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado");
        repo.deleteById(id);
    }

    @Override
    public UserDto toggleState(Long id) {
        User u = repo.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));
        u.setState(Boolean.TRUE.equals(u.getState()) ? Boolean.FALSE : Boolean.TRUE);
        u.setDateModified(LocalDate.now());
        return toDto(repo.save(u));
    }

    @Override
    public UserDto uploadImage(Long id, MultipartFile file) {
        if (file == null || file.isEmpty()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Archivo vacío");
        User u = repo.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));
        try {
            Path root = Paths.get(uploadDir, "users");
            Files.createDirectories(root);

            String original = file.getOriginalFilename();
            String ext = (original != null && original.contains(".")) ? original.substring(original.lastIndexOf('.')) : ".jpg";
            Path dest = root.resolve(id + ext);
            Files.copy(file.getInputStream(), dest, StandardCopyOption.REPLACE_EXISTING);

            u.setImage("users/" + id + ext);
            u.setDateModified(LocalDate.now());
            return toDto(repo.save(u));
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "No se pudo guardar la imagen");
        }
    }

    @Override
    public void changePassword(Long id, String newPassword) {
        if (newPassword == null || newPassword.length() < 6)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Contraseña muy corta");
        User u = repo.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));
        u.setKey(encoder.encode(newPassword));
        u.setDateModified(LocalDate.now());
        repo.save(u);
    }

    // ===== Roles =====

    @Override
    public UserDto setRoles(Long idUser, List<Long> roleIds) {
        User u = repo.findById(idUser).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));
        Set<Rol> roles = new HashSet<>(rolRepo.findAllById(roleIds));
        u.setRoles(roles);
        u.setDateModified(LocalDate.now());
        return toDto(repo.save(u));
    }

    @Override
    public UserDto addRole(Long idUser, Long idRol) {
        User u = repo.findById(idUser).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));
        Rol r = rolRepo.findById(idRol).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Rol no encontrado"));
        u.getRoles().add(r);
        u.setDateModified(LocalDate.now());
        return toDto(repo.save(u));
    }

    @Override
    public UserDto removeRole(Long idUser, Long idRol) {
        User u = repo.findById(idUser).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));
        Rol r = rolRepo.findById(idRol).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Rol no encontrado"));
        u.getRoles().remove(r);
        u.setDateModified(LocalDate.now());
        return toDto(repo.save(u));
    }

    // ===== helpers =====

    private void validateAll(UserRequestDto r, Long editingId) {
        List<String> errors = new ArrayList<>();
        errors.addAll(documentValidator.validate(r.getDocumentType(), r.getDocumentNumber()));
        errors.addAll(emailValidator.validate(r.getEmail()));
        errors.addAll(phoneValidator.validate(r.getCellphone()));

        if (editingId == null) {
            if (repo.existsByDocumentTypeAndDocumentNumber(r.getDocumentType(), r.getDocumentNumber().trim()))
                errors.add("El documento ya existe");
            if (r.getEmail() != null && !r.getEmail().isBlank() && repo.existsByEmail(r.getEmail().trim()))
                errors.add("El email ya existe");
            if (repo.existsByLogin(r.getLogin().trim()))
                errors.add("El login ya existe");
        } else {
            if (repo.existsByDocumentTypeAndDocumentNumberAndIdNot(r.getDocumentType(), r.getDocumentNumber().trim(), editingId))
                errors.add("El documento ya existe");
            if (r.getEmail() != null && !r.getEmail().isBlank() && repo.existsByEmailAndIdNot(r.getEmail().trim(), editingId))
                errors.add("El email ya existe");
            if (repo.existsByLoginAndIdNot(r.getLogin().trim(), editingId))
                errors.add("El login ya existe");
        }

        if (!errors.isEmpty()) throw new DocumentValidationException(errors);
    }

    private String emptyToNull(String s) { return (s == null || s.trim().isEmpty()) ? null : s.trim(); }

    private UserDto toDto(User u) {
        return UserDto.builder()
                .id(u.getId())
                .name(u.getName())
                .documentType(u.getDocumentType())
                .documentNumber(u.getDocumentNumber())
                .address(u.getAddress())
                .cellphone(u.getCellphone())
                .email(u.getEmail())
                .login(u.getLogin())
                .image(u.getImage())
                .state(u.getState())
                .dateCreated(u.getDateCreated())
                .dateModified(u.getDateModified())
                .roles(u.getRoles().stream().map(Rol::getName).toList())
                .build();
    }
}
