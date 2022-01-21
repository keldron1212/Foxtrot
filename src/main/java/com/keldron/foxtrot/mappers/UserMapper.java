package com.keldron.foxtrot.mappers;

import com.keldron.foxtrot.Utils;
import com.keldron.foxtrot.dto.NewUserDto;
import com.keldron.foxtrot.dto.UpdateUserDto;
import com.keldron.foxtrot.dto.UserDto;
import com.keldron.foxtrot.model.AuthorityRole;
import com.keldron.foxtrot.model.User;
import com.keldron.foxtrot.model.trainings.Training;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class UserMapper {
    public static User toPrivilegedUser(UpdateUserDto dto) {

//        String password = Utils.encodePassword(dto.getPassword());
        String password = dto.getPassword();
        Set<AuthorityRole> authorities = toAuthorities(dto.getAuthorities());

        return new User(dto.getUsername(), password, dto.getName(), dto.getSurname(), authorities);
    }

    public static User toStandardUser(NewUserDto dto) {

        String password = Utils.encodePassword(dto.getPassword());
        Set<AuthorityRole> authorities = Set.of(AuthorityRole.USER);

        return new User(dto.getUsername(), password, dto.getName(), dto.getSurname(), authorities);
    }

    public static UserDto toDto(User user) {
        Set<String> authorities = authoritiesToString(user.getAuthorities());

        Set<Long> trainingsDto = user.getTrainings().stream().map(Training::getId
        ).collect(Collectors.toUnmodifiableSet());

        return new UserDto(user.getUsername(), user.getName(), user.getSurname(), authorities, trainingsDto);
    }

    private static Set<AuthorityRole> toAuthorities(Set<String> authoritiesDto) {
        Set<AuthorityRole> newAuthorities = new HashSet<>();
        for (String authorityDto : authoritiesDto) {
            AuthorityRole authority = AuthorityRole.valueOf(authorityDto);
            newAuthorities.add(authority);
        }
        return newAuthorities;
    }

    private static Set<String> authoritiesToString(Collection<? extends GrantedAuthority> authorities) {
        Set<String> authoritiesDto = new HashSet<>();
        for (GrantedAuthority authorityRole : authorities) {
            String authority = authorityRole.getAuthority();
            authoritiesDto.add(authority);
        }
        return authoritiesDto;
    }
 }
