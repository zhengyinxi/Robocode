package wang.xin.robocode.server.configs;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.tuple.Triple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.DigestAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.session.jdbc.config.annotation.web.http.EnableJdbcHttpSession;
import org.springframework.util.Assert;
import org.springframework.web.filter.CompositeFilter;
import wang.xin.robocode.server.data.models.OAuthSource;
import wang.xin.robocode.server.data.models.OAuthUser;
import wang.xin.robocode.server.data.repositories.OAuthUserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;

/**
 * Created by Xin on 2016/10/7.
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Configuration
@EnableJdbcHttpSession
@EnableOAuth2Client
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private OAuth2ClientContext oAuth2ClientContext;

    @Autowired
    private OAuthUserRepository oAuthUserRepository;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.antMatcher("/**")
                .authorizeRequests().antMatchers("/", "/login**", "/webjars**").permitAll().anyRequest().authenticated().and()
                .exceptionHandling()/*.authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login"))*/.and()
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/").permitAll().and()
                .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()).and()
                .addFilterBefore(this.ssoFilter(), DigestAuthenticationFilter.class)
                .headers().httpStrictTransportSecurity().disable().and()
        ;
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/h2-console/**").and();
    }

    @Bean
    public FilterRegistrationBean oauth2ClientFilterRegistration(OAuth2ClientContextFilter filter) {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(filter);
        registration.setOrder(-100);
        return registration;
    }

    @Bean
    @ConfigurationProperties("github")
    public ClientResources github() {
        return new ClientResources("github");
    }

    @Bean
    @ConfigurationProperties("facebook")
    public ClientResources facebook() {
        return new ClientResources("facebook");
    }

    @Bean
    @ConfigurationProperties("microsoft")
    public ClientResources microsoft() {
        return new ClientResources("microsoft");
    }

    private Filter ssoFilter() {
        CompositeFilter filter = new CompositeFilter();
        List<Filter> filters = new ArrayList<>();
        filters.add(this.ssoFilter(this.facebook(), "/login/facebook", OAuthSource.Facebook));
        filters.add(this.ssoFilter(this.github(), "/login/github", OAuthSource.GitHub));
        filters.add(this.ssoFilter(this.microsoft(), "/login/microsoft", OAuthSource.Microsoft));
        filter.setFilters(filters);
        return filter;
    }

    private Filter ssoFilter(ClientResources client, String path, OAuthSource source) {
        OAuth2ClientAuthenticationProcessingFilter filter = new OAuth2ClientAuthenticationProcessingFilter(path);
        OAuth2RestTemplate template = new OAuth2RestTemplate(client.getClient(), this.oAuth2ClientContext);
        filter.setRestTemplate(template);
        filter.setTokenServices(new CustomizedUserInfoTokenServices(
                client.getResource().getUserInfoUri(), client.getClient().getClientId(),
                source, this.oAuthUserRepository));
        return filter;
    }

    class ClientResources {

        private final String source;

        @NestedConfigurationProperty
        private AuthorizationCodeResourceDetails client = new AuthorizationCodeResourceDetails();

        @NestedConfigurationProperty
        private ResourceServerProperties resource = new ResourceServerProperties();

        public ClientResources(String source) {
            this.source = source;
        }

        public String getSource() {
            return source;
        }

        public AuthorizationCodeResourceDetails getClient() {
            return client;
        }

        public ResourceServerProperties getResource() {
            return resource;
        }
    }

    class CustomizedUserInfoTokenServices extends UserInfoTokenServices {

        private OAuthSource source;
        private OAuthUserRepository repository;

        public CustomizedUserInfoTokenServices(String userInfoEndpointUrl, String clientId,
                                               OAuthSource source, OAuthUserRepository repository) {
            super(userInfoEndpointUrl, clientId);
            this.source = source;
            this.repository = repository;
        }

        @Override
        public OAuth2Authentication loadAuthentication(String accessToken)
                throws AuthenticationException, InvalidTokenException {
            OAuth2Authentication authentication = super.loadAuthentication(accessToken);
            OAuth2Request request = authentication.getOAuth2Request();
            UsernamePasswordAuthenticationToken token = UsernamePasswordAuthenticationToken.class.cast(authentication.getUserAuthentication());
            Map<String, Object> map = Map.class.cast(token.getDetails());

            String id = map.getOrDefault("id", "").toString();
            Triple<OAuthSource, String, Integer> principal = Triple.of(source, id, null);
            Object credentials = token.getCredentials();
            List<GrantedAuthority> authorities = Lists.newArrayList(token.getAuthorities());

            OAuthUser user = this.repository.findBySourceAndId(source, id);
            if (user != null) {
                Assert.state(user.getUser() != null);
                principal = Triple.of(source, id, user.getUser().getId());
                authorities.add(new SimpleGrantedAuthority("ROLE_SU"));
            }

            token = new UsernamePasswordAuthenticationToken(principal, credentials, authorities);
            token.setDetails(map);
            return new OAuth2Authentication(request, token);
        }
    }
}
