package sjspring.shop.pregAndBirthDeveloper.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Entity
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EMailMessage {

    @Id
    @Column
    private Long id;

    @Column
    private String title;

    @Column
    private String Sender;

    @Column
    private String recipient;

}
