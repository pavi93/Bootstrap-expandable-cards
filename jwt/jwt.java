
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.stereotype.Component;

import com.greenscart.constants.GreensCartConstants;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JWTUtils {

	public String secret = "sfweafasgawedasgadsg";

	public String generateJWT(String userId, String role, String isAdmin) {
		String token = Jwts.builder().setSubject(GreensCartConstants.JWT_SUBJECT).setExpiration(getExpiration())
				.claim("isAdmin", isAdmin).claim("userId", userId).claim("role", role)
				.signWith(SignatureAlgorithm.HS512, secret).compact();

		return token;
	}

	public Claims readJWT(String jwt) {
		System.out.println("INTO Read JWT");
		System.out.println(jwt);
		Jws<Claims> jwsClaims = Jwts.parser().setSigningKey(secret).parseClaimsJws(jwt);
		System.out.println(jwsClaims);
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(jwt).getBody();

	}
	
	public boolean validateJWT(Claims claim) {
		return checkExpiry(claim.getExpiration());
		
	}

	private boolean checkExpiry(Date expiration) {
		boolean flag = false;
		int diff = expiration.compareTo(new Date());
		if(diff>0)
			flag = true;
		
		return flag;
	}

	private Date getExpiration() {
		Date exp = new Date(System.currentTimeMillis() + Long.parseLong("360000000"));
		System.out.println(exp);
		System.out.println(Date.from(LocalDate.now().plusDays(2).atStartOfDay(ZoneId.systemDefault()).toInstant()));
		return exp;
	}

}