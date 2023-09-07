package sjspring.shop.pregAndBirthDeveloper.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;


@Table(name = "users")
@NoArgsConstructor(access =  AccessLevel.PROTECTED)
@Getter
@Entity
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_no", updatable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

//    @Column(name = "nickName", nullable = false, unique = true)
//    private String nickName;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "hp", nullable = false, unique = true)
    private String hp;

    @Column(name = "password")
    private String password;

//    @Column(name = "babydue")
//    private Date babydue;

    @Builder
    public User(String name, String email,String hp,String password, String auth){
        this.name = name;
        this.email = email;
        this.hp = hp;
        this.password = password;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){
        return List.of(new SimpleGrantedAuthority("user"));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
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
        return true;
    }
}


