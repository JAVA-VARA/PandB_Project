package sjspring.shop.pregAndBirthDeveloper.config.jwt;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
@ConfigurationProperties("jwt")
public class JwtProperties {
    private String issuer;
    private String secretKey;
}

//@Setter, @Getter 어노테이션을 사용하지 않는 경우 아래와 같이 작성될 수 있다.

/*
* //Getter 메서드
* public String getIssuer() {
*       return issuer;
* }
*
* //Setter 메서드
* public String setIssuer(String issuer){
*       this.issuer = issuer;
* }
*
* */
