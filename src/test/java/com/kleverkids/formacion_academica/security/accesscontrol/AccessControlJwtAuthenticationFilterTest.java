package com.kleverkids.formacion_academica.security.accesscontrol;

import com.essenza.security.AccessControlTokenValidator;
import com.essenza.security.EssenzaSecurityProperties;
import com.essenza.security.JwksKeyResolver;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPublicKey;
import java.time.Clock;
import java.time.Duration;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/** El filtro autentica tokens de access_control: RS256 del servidor de autorizacion y HMAC viejo. */
class AccessControlJwtAuthenticationFilterTest {

    private static final String SECRETO = "secreto-solo-para-pruebas-unitarias-0123456789abcdef";

    private AccessControlJwtAuthenticationFilter filtro;

    private static final KeyPair RSA = generar();

    private static AccessControlJwtAuthenticationFilter filtro(String secretoViejo) {
        EssenzaSecurityProperties props = new EssenzaSecurityProperties();
        props.setIssuer("http://localhost:8000");
        props.setAudiences(List.of("formacion-academica"));
        props.setLegacySecret(secretoViejo);
        RSAPublicKey pub = (RSAPublicKey) RSA.getPublic();
        Base64.Encoder b = Base64.getUrlEncoder().withoutPadding();
        byte[] n = pub.getModulus().toByteArray();
        if (n[0] == 0) {
            n = Arrays.copyOfRange(n, 1, n.length);
        }
        String jwks = "{\"keys\":[{\"kty\":\"RSA\",\"kid\":\"k1\",\"alg\":\"RS256\",\"use\":\"sig\",\"n\":\""
                + b.encodeToString(n) + "\",\"e\":\"" + b.encodeToString(pub.getPublicExponent().toByteArray()) + "\"}]}";
        JwksKeyResolver resolver = new JwksKeyResolver(() -> jwks, Duration.ofMinutes(5), Clock.systemUTC());
        return new AccessControlJwtAuthenticationFilter(new AccessControlTokenValidator(props, resolver));
    }

    @BeforeEach
    void setUp() {
        filtro = filtro(SECRETO);
        SecurityContextHolder.clearContext();
    }

    @AfterEach
    void limpiar() {
        SecurityContextHolder.clearContext();
    }

    private static String token(String secreto, String issuer, Map<String, Object> extra) {
        return Jwts.builder()
                .subject("42")
                .claim("username", "jperez")
                .claim("roles", List.of("Docente"))
                .claim("permissions", List.of("FEEDBACK.VER"))
                .claims(extra)
                .issuer(issuer)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 60_000))
                .signWith(Keys.hmacShaKeyFor(secreto.getBytes(StandardCharsets.UTF_8)))
                .compact();
    }

    private Authentication ejecutar(String token) throws Exception {
        MockHttpServletRequest req = new MockHttpServletRequest();
        req.addHeader("Authorization", "Bearer " + token);
        filtro.doFilter(req, new MockHttpServletResponse(), new MockFilterChain());
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @Test
    void tokenValidoPueblaPrincipalYRoles() throws Exception {
        Authentication auth = ejecutar(token(SECRETO, "access-control-service", Map.of("employeeId", 9)));

        assertThat(auth).isNotNull();
        AccessControlPrincipal p = (AccessControlPrincipal) auth.getPrincipal();
        assertThat(p.userId()).isEqualTo(42L);
        assertThat(p.employeeId()).isEqualTo(9L);
        assertThat(auth.getAuthorities()).extracting(GrantedAuthority::getAuthority)
                .containsExactlyInAnyOrder("ROLE_DOCENTE", "FEEDBACK.VER");
    }

    @Test
    void firmaAjenaEmisorDistintoORefreshNoAutentican() throws Exception {
        assertThat(ejecutar(token("otro-secreto-de-al-menos-treinta-y-dos-bytes!!", "access-control-service", Map.of()))).isNull();
        SecurityContextHolder.clearContext();
        assertThat(ejecutar(token(SECRETO, "otro-emisor", Map.of()))).isNull();
        SecurityContextHolder.clearContext();
        assertThat(ejecutar(token(SECRETO, "access-control-service", Map.of("type", "refresh")))).isNull();
    }

    @Test
    void sinSecretoConfiguradoElTokenViejoNoAutentica() throws Exception {
        filtro = filtro(null);
        assertThat(ejecutar(token(SECRETO, "access-control-service", Map.of()))).isNull();
    }

    @Test
    void tokenDeContextoRs256() throws Exception {
        String t = Jwts.builder().header().keyId("k1").type("at+jwt").and()
                .issuer("http://localhost:8000").subject("42").audience().add("formacion-academica").and()
                .expiration(new Date(System.currentTimeMillis() + 60_000))
                .claim("ctx", Map.of("company_id", 7, "user_application_id", 12))
                .claim("roles", List.of("Docente")).claim("permissions", List.of("FEEDBACK.VER"))
                .signWith(RSA.getPrivate(), Jwts.SIG.RS256).compact();
        Authentication auth = ejecutar(t);
        assertThat(auth).isNotNull();
        assertThat(((AccessControlPrincipal) auth.getPrincipal()).userId()).isEqualTo(42L);
        assertThat(auth.getAuthorities()).extracting(GrantedAuthority::getAuthority)
                .containsExactlyInAnyOrder("ROLE_DOCENTE", "FEEDBACK.VER");
    }

    private static KeyPair generar() {
        try {
            KeyPairGenerator g = KeyPairGenerator.getInstance("RSA");
            g.initialize(2048);
            return g.generateKeyPair();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }
}
