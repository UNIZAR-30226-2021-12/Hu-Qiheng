package com.unizar.unozar.core;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.unizar.unozar.core.entities.Player;
import com.unizar.unozar.core.repository.PlayerRepository;

@Service
public class CustomPlayerDetailsService implements UserDetailsService{
  
  @Autowired
  private PlayerRepository playerRepository;
  
  @Override
  public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
    List<SimpleGrantedAuthority> roles = null;
      
    Optional<Player> toFind = playerRepository.findById(id);
    if (toFind.isPresent()){
      Player found = toFind.get();
      roles = Arrays.asList(new SimpleGrantedAuthority("player"));
      return new User(found.getId(), found.getPassword(), roles);
    }
    throw new UsernameNotFoundException("User not found with the id " + id);  
    }
  
}
