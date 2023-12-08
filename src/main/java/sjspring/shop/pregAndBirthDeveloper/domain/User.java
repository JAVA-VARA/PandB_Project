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

    @Column(name = "nick_Name", unique = true)
    private String nickName;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "hp", unique = true)
    private String hp;

    @Column(name = "password")
    private String password;

    @Column(name = "baby_due")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date babyDue;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER , cascade = CascadeType.REMOVE)
    @OrderBy("id asc")
    @JsonManagedReference
    private List<Comment> commentList = new ArrayList<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonManagedReference
    @OrderBy("boardNo asc")
    private List<Board> boardList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    @JsonManagedReference
    private List<ScrapArticle> scrapedArticles = new ArrayList<>();
//    @OneToMany
//    private List<Board> scrapedArticles = new ArrayList<>();

//    @ManyToMany
//    @JoinTable( // JoinTable은 테이블과 테이블 사이에 별도의 조인 테이블을 만들어 양 테이블간의 연관관계를 설정 하는 방법
//            name = "account_authority",
//            joinColumns = {@JoinColumn(name = "user_no", referencedColumnName = "user_no")},
//            inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "authority_name")})
//    private Set<Authority> authorities;



    @Builder
    public User(String name, String email, String nickName, String hp,String password, Date babyDue){
        this.name = name;
        this.nickName = nickName;
        this.email = email;
        this.hp = hp;
        this.password = password;
//        this.babyDue = babyDue;
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

    public User update(String name, String nickName, String hp, Date babyDue){
        this.name = name;
        this.nickName = nickName;
        this.hp = hp;
        this.babyDue = babyDue;

        return this;
    }

    public User update(String nickName, String hp, Date babyDue){
        this.nickName = nickName;
        this.hp = hp;
        this.babyDue = babyDue;

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


