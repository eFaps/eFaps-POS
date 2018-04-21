/*
 * Copyright 2003 - 2018 The eFaps Team
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package org.efaps.pos.service;

import java.util.List;

import org.efaps.pos.entity.User;
import org.efaps.pos.respository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService
    implements UserDetailsService
{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(final UserRepository _userRepository,
                       final PasswordEncoder _passwordEncoder)
    {
        this.userRepository = _userRepository;
        this.passwordEncoder = _passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(final String _username)
        throws UsernameNotFoundException
    {
        return this.userRepository.findById(_username).orElseThrow(() -> new UsernameNotFoundException(_username));
    }

    public PasswordEncoder getPasswordEncoder()
    {
        return this.passwordEncoder;
    }

    public List<User> getUsers()
    {
        return this.userRepository.findAll();
    }
}
