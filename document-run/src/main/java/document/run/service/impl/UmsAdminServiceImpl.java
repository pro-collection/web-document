package document.run.service.impl;

import document.run.common.utils.JwtTokenUtil;
import document.run.service.UmsAdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UmsAdminServiceImpl {
    private static final Logger logger = LoggerFactory.getLogger(UmsAdminService.class);

    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//    @Autowired

}
