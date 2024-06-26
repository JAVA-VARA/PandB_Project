package sjspring.shop.pregAndBirthDeveloper.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;


@Table(name = "users")
@NoArgsConstructor(access =  AccessLevel.PROTECTED)
@Getter
@Entity
//UserDetails를 상속받아 인증 객체로 사용.
public class User implements UserDetails {
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_no", updatable = false)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "nick_name", unique = true)
    private String nickname;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "hp", unique = true)
    private String hp;

    @Column(name = "password")
    private String password;

    @Column(name = "baby_due")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date babyDue;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY , cascade = CascadeType.REMOVE)
    @OrderBy("id asc")
    @JsonManagedReference
    private List<Comment> commentList = new ArrayList<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JsonManagedReference
    @OrderBy("boardNo asc")
    private List<Board> boardList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    @JsonManagedReference
    private List<ScrapArticle> scrapedArticles = new ArrayList<>();

    @Builder
    public User(String name, String email, String nickname, String hp,String password, Date babyDue){
        this.name = name;
        this.nickname = nickname;
        this.email = email;
        this.hp = hp;
        this.password = password;
        this.babyDue = babyDue;
    }

    public void mappingCommentToUser(Comment comment){
        if(this.commentList == null){
            this.commentList = new ArrayList<>();
        }
        this.commentList.add(comment);
    }

    public void mappingBoardToUser(Board board){
        if(this.boardList == null){
            this.boardList = new ArrayList<>();
        }
        this.boardList.add(board);
    }

//    public void scrapedArticleToUser(Board board){
//        if(this.scrapedArticles == null){
//            this.scrapedArticles = new ArrayList<>();
//        }
//        this.scrapedArticles.add(board);
//    }

    public void updatePassword(String email, String password){
        this.email = email;
        this.password = password;
    }

    public User update(String name){
        this.name = name;

        return this;
    }

    public User update(String name, String nickname, String hp, Date babyDue){
        this.name = name;
        this.nickname = nickname;
        this.hp = hp;
        this.babyDue = babyDue;

        return this;
    }

    public User update(String nickname, String hp, Date babyDue){
        this.nickname = nickname;
        this.hp = hp;
        this.babyDue = babyDue;

        return this;
    }

    public User update(String nickname, String hp, Date babyDue, String password){
        this.nickname = nickname;
        this.hp = hp;
        this.babyDue = babyDue;
        this.password = password;
        return this;
    }

    public void deleteCommentInUser(Comment comment){
        this.commentList.remove(comment);
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("guest"));
        authorities.add(new SimpleGrantedAuthority("user"));
        authorities.add(new SimpleGrantedAuthority("admin"));

        return authorities;
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


