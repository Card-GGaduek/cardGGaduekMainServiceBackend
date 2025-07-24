package org.cardGGaduekMainService.auth;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.cardGGaduekMainService.exception.ErrorCode;
import org.cardGGaduekMainService.member.domain.MemberVO;
import org.cardGGaduekMainService.member.dto.MemberFindDTO;
import org.cardGGaduekMainService.member.service.MemberService;
import org.cardGGaduekMainService.response.ApiResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final MemberAuthProvider memberAuthProvider;
    private final MemberService memberService;

    // 각 요청이 올때 마다 가로챔.
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");

        try {
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String token = authHeader.substring(7);

                Long id = memberAuthProvider.getMemberIdFromToken(token);

                MemberFindDTO memberById = memberService.findMemberById(id);

//                LoginMember loginMember = LoginMember.builder()
//                        .id(member.getId())
//                        .email(member.getEmail())
//                        .nickname(member.getNickname())
//                        .authority(member.getAuthority())
//                        .build();

                LoginMember loginMember = LoginMember.builder()
                        .id(memberById.getId())
                        .email(memberById.getEmail())
                        .name(memberById.getName())
                        .build();



                List<GrantedAuthority> authorities = Collections.emptyList();
                authorities = List.of(new SimpleGrantedAuthority("ROLE_USER"));

                // Spring Security에서 현재 요청을 인증한 사용자의 정보를 담는 인증 객체(Authentication 객체)
                // JWT 인증에서는 credential 매개변수는 null
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(loginMember, null, authorities);

                // SecurityContextHolder가 인증 객체를 해당 요청의 ThreadLocal저장소에 담아주는 코드 -> 요청이 끝나고 Thread가 반환 되면 인증 객체도 함께 사라져서 stateless 유지
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

            // 필터가 끝났으므로, 다음 필터나 컨트롤러로 요청을 넘김.
//            filterChain.doFilter(request, response);
            super.doFilter(request, response, filterChain);
        } catch (JWTVerificationException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json;charset=UTF-8");

            ApiResponse<Void> errorResponse = ApiResponse.error(ErrorCode.INVALID_TOKEN);

            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(errorResponse);
            response.getWriter().write(json);
        }


    }


}
