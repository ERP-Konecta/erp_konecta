package com.graduationProject.gpManagementSystem.security;


// import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
// import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {


    private final JwtUtils jwtService;
    private final UserDetailsService userDetailsService;


    //to solve error only
    // public JwtAuthenticationFilter(JwtService jwtService, UserDetailsService userDetailsService) {
    //     this.jwtService = jwtService;
    //     this.userDetailsService = userDetailsService;
    // }

    @Override
    protected void doFilterInternal(
         @SuppressWarnings("null")   HttpServletRequest request,
         @SuppressWarnings("null")   HttpServletResponse response,
         @SuppressWarnings("null")   FilterChain filterChain) throws ServletException, IOException {
         final String authHeader = request.getHeader("Authorization");
         final String jwt;
         final String userEmail;
         if(authHeader == null || !authHeader.startsWith("Bearer ")){
             filterChain.doFilter(request,response);
             return;
         }
         jwt = authHeader.substring(7);
         userEmail = jwtService.extractUserName(jwt);
         if( userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null){
             UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
             if(jwtService.isTokenValid(jwt , userDetails)){
                 UsernamePasswordAuthenticationToken authToken =  new UsernamePasswordAuthenticationToken(
                          userDetails ,
                         null,
                         userDetails.getAuthorities()
                 );
                 authToken.setDetails(
                         new WebAuthenticationDetailsSource().buildDetails(request)
                 );
                 SecurityContextHolder.getContext().setAuthentication(authToken);
             }
         }
         filterChain.doFilter(request, response);

    }
}












// package com.example.demo.config;

// // import jakarta.annotation.Nonnull;
// import jakarta.servlet.FilterChain;
// import jakarta.servlet.ServletException;
// import jakarta.servlet.http.HttpServletRequest;
// import jakarta.servlet.http.HttpServletResponse;
// import lombok.RequiredArgsConstructor;
// // import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
// import org.springframework.security.core.context.SecurityContextHolder;
// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
// import org.springframework.stereotype.Component;
// import org.springframework.web.filter.OncePerRequestFilter;

// import java.io.IOException;

// @Component
// @RequiredArgsConstructor
// public class JwtAuthenticationFilter extends OncePerRequestFilter {


//     private final JwtService jwtService;
//     private final UserDetailsService userDetailsService;


//     //to solve error only
//     // public JwtAuthenticationFilter(JwtService jwtService, UserDetailsService userDetailsService) {
//     //     this.jwtService = jwtService;
//     //     this.userDetailsService = userDetailsService;
//     // }

//     @Override
//     protected void doFilterInternal(
//          @SuppressWarnings("null")   HttpServletRequest request,
//          @SuppressWarnings("null")   HttpServletResponse response,
//          @SuppressWarnings("null")   FilterChain filterChain) throws ServletException, IOException {
//          final String authHeader = request.getHeader("Authorization");
//          final String jwt;
//          final String userEmail;
//          if(authHeader == null || !authHeader.startsWith("Bearer ")){
//              filterChain.doFilter(request,response);
//              return;
//          }
//          jwt = authHeader.substring(7);
//          userEmail = jwtService.extractUserName(jwt);
//          if( userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null){
//              UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
//              if(jwtService.isTokenVaild(jwt , userDetails)){
//                  UsernamePasswordAuthenticationToken authToken =  new UsernamePasswordAuthenticationToken(
//                           userDetails ,
//                          null,
//                          userDetails.getAuthorities()
//                  );
//                  authToken.setDetails(
//                          new WebAuthenticationDetailsSource().buildDetails(request)
//                  );
//                  SecurityContextHolder.getContext().setAuthentication(authToken);
//              }
//          }
//          filterChain.doFilter(request, response);

//     }
// }

