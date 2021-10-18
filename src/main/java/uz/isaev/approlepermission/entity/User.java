package uz.isaev.approlepermission.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import uz.isaev.approlepermission.entity.template.AbsEntity;
import uz.isaev.approlepermission.enums.Permission;

import javax.persistence.*;
import java.util.*;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "Users")
public class User extends AbsEntity implements UserDetails {
        @Column(nullable = false)
        private String fullName;

        @Column(nullable = false, unique = true)
        private String username;

        @Column(nullable = false)
        private String password;

        @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.REMOVE)
        private Role role;

        private boolean enabled;

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
                Set<Permission> permissionList = this.role.getPermissionSet();
                Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
//                for (Permission permission : permissionList) {
//                        grantedAuthorities.add(new GrantedAuthority() {
//                                @Override
//                                public String getAuthority() {
//                                        return permission.name();
//                                }
//                        });
//                }

                for (Permission permission : permissionList) {
                        grantedAuthorities.add(new SimpleGrantedAuthority(permission.name()));
                }

                return grantedAuthorities;
        }

        @Override
        public boolean isAccountNonExpired() {
                return true;
        }

        @Override
        public boolean isAccountNonLocked() {
                return true;
        }

        @Override
        public boolean isCredentialsNonExpired() {
                return true;
        }

        @Override
        public boolean isEnabled() {
                return this.enabled;
        }
}
